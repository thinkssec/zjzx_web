<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>固定资产调拨_审核</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/glyphicons/css/glyphicons.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/color.css">
    <script type="text/javascript" src="${ctxStatic}/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script src="${ctxStatic}/jqGrid/4.7/js/jquery.jqGrid.js" type="text/javascript"></script>
</head>
<style>
    table th {
        text-align: right;
    }
</style>

<body>

<ul class="nav nav-tabs">
    <li><a href="${ctx}/gdzc/zcdb_sh/dsh" class="gicon-edit">&nbsp;固定资产调拨_待审核</a></li>
    <li class="active"><a href="" class="gicon-check">&nbsp;固定资产调拨_已审核</a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/gdzc/gclist" modelAttribute="condition" method="post"
           class="breadcrumb form-search">
    <div style="margin-top:8px;">
        <input type="hidden" name="c5" id="hid_c5"/>
        <form:select path="c3" class="input-xlarge" style="width:120px">
            <form:option value="1" label="已审核通过"/>
            <form:option value="2" label="已审核不通过"/>
        </form:select>
        <sys:treeselect id="office" name="c2" value="${condition.c2}" labelName="parent.name"
                        labelValue="${office.parent.name}"
                        title="机构" url="/sys/office/treeData" extId="${office.id}" isAll="true" cssClass="input-small"
                        smallBtn="true" hideBtn="false"/>

        <a class="btn btn-primary" href="#" onclick="doCx();"><i class="icon-refresh"></i> 刷新</a>
            <%--<input id="btnImport" class="btn btn-primary" type="button"  value="导入"/>--%>
    </div>
</form:form>
<sys:message content="${message}"/>
<table id="datatable1"></table>
<div id="win" class="easyui-window" title="添加" style="width: 800px; height: 430px; padding: 10px;"
     data-options="iconCls:'icon-add',modal:false,border:true,closed:true,footer:'#footer',
        onResize:function(){
				$(this).window('hcenter');
		}">
    <table id="datatable2"></table>
    <div id="footer" style="padding: 5px;">

    </div>
</div>
<script type="text/javascript">
    $(function() {
        $(".btn").click(function(){
            $(this).button('loading').delay(2000).queue(function() {
                $(this).button('reset');
                $(this).dequeue();
            });
        });
    });
    // 初始化DataGrid对象
    var editingIndex = null;
    var saveFlag = false;
function toHistory(){
    $('#footer').show();
    $('#win').window({ title: '资产信息修改记录' });
    $('#win').window('open');
}
    function doCx() {
        dgOptions.queryParams = getInitParam();
        $('#datatable1').datagrid(dgOptions);
    }
    function doCx2() {
        dgOptions.queryParams = getInitParam();
        dgOptions.queryParams.start=0;
        $('#datatable1').datagrid(dgOptions);
    }
    $("#btnImport").click(function () {
        $.jBox($("#importBox").html(), {
            title: "导入数据", buttons: {"关闭": true},
            bottomText: "导入文件不能超过5M，仅允许导入“xls”格式文件！"
        });
    });
    //console.log(${dwTree });
    var dgOptions = {
        rownumbers: true,
        //fit: true,
        // height:700,
        border: true,
        //method: 'post',
        //toolbar: '#tb',
        pageSize: 50,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        singleSelect: true,
        checkOnSelect: 'true',
        url: '${ctx}/gdzc/zcdb_shlist',
        frozenColumns: [[
            {
                field: 'STATUS', title: '状态', align: 'center', width: 70,
                formatter: function (value, row) {
                    var n = "临时保存";
                    if ("0" == row.STATUS) n = "待审核";
                    if ("1" == row.STATUS) n = "审核通过";
                    if ("2" == row.STATUS) n = "审核不通过";
                    return n;
                },
                styler: function (value, row, index) {

                    if (row.STATUS == '0')
                        return 'background-color:blue;color:white';
                    if (row.STATUS == '1')
                        return 'background-color:green;color:white';
                    if (row.STATUS == '2')
                        return 'background-color:red;color:white';
                }
            }, {field: 'DEPTMC', title: '提交单位', width: 90, align: 'center'}
            , {field: 'SBDATE', title: '提交时间', width: 90, align: 'center'}
            , {field: 'ZCBM', title: '资产编码(系统内)', width: 100,align:'center'}
            , {field: 'ERPBM', title: 'ERP编码', width: 100,align:'center'}

            , {field: 'ZCMC', title: '资产名称', width: 170, align: 'center'}
        ]],
        columns: [[
            {field: 'ID', hidden: true}
            , {field: 'DL1', title: '大类1', width: 100,align:'center'}
            , {field: 'DL2', title: '大类2', width: 150,align:'center'}
            , {field: 'LB', title: '类别', width: 100,align:'center'}
            , {field: 'FLBM', title: '分类编码', width: 80,align:'center'}
            , {field: 'SL', title: '数量', width: 40, align: 'center'}
            , {field: 'ZZPH', title: '执照牌号', width: 100, align: 'center'}
            , {
                field: 'SYDWMC', title: '使用单位', width: 200, align: 'center',
                styler: function (value, row, index) {
                    return 'background-color:red;color:white';
                }
            }
            , {field: 'ZGBMMC', title: '主管部门', width: 100, align: 'center'}
            , {field: 'FZRMC', title: '负责人', width: 50, align: 'center'}
            , {field: 'GGXH', title: '规格型号', width: 100, align: 'center'}
            , {field: 'ZBHRQ', title: '资本化日期', width: 80, align: 'center'}
            , {field: 'YSYNX', title: '已使用年限', width: 70, align: 'center'}
            , {field: 'ZCYZ', title: '资产原值', width: 60, align: 'center'}
            , {field: 'ZJNX', title: '折旧年限', width: 60, align: 'center'}
            , {field: 'YXZT', title: '运行状态', width: 60, align: 'center'}
            , {field: 'DD', title: '地点', width: 80, align: 'center'}
            , {field: 'ZT', title: '清查情况', width: 80,align:'center'}
            , {field: 'BZ', title: '备注', width: 180, align: 'center'}
            , {field: 'YLZD1', title: '预留字段1', width: 100, align: 'center'}
            , {field: 'YLZD2', title: '预留字段2', width: 100, align: 'center'}
            , {field: 'YLZD3', title: '预留字段3', width: 100, align: 'center'}
            , {field: 'YLZD4', title: '预留字段4', width: 100, align: 'center'}
        ]],
        onDblClickCell: function (index, field, value) {
            $('#hid_c5').val($("#datatable1").datagrid('getRows')[index]['ZCBM']);
            var hdivcenter=$("#win").height()-5;
            $('#datatable2').height(hdivcenter + 'px');
            dgOptions2.queryParams = getInitParam();
            $('#datatable2').datagrid(dgOptions2);
            toHistory();
        }
    };
    var dgOptions2 = {
        rownumbers: true,
        border: true,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        singleSelect: true,
        checkOnSelect: 'true',
        url: '${ctx}/gdzc/historyList',
        columns: [[
            {field: 'ID', hidden: true}
            , {field: 'NNN', title: '修改人', width: 100,align:'center'}
            , {field: 'SBDATE', title: '修改时间', width: 100,align:'center'}
            , {field: 'ZCBM', title: '资产编码(系统内)', width: 100,align:'center'}
            , {field: 'ERPBM', title: 'ERP编码', width: 100,align:'center'}
            , {field: 'ZCMC', title: '资产名称', width: 170, align: 'center'}
            , {field: 'DL1', title: '大类1', width: 100,align:'center'}
            , {field: 'DL2', title: '大类2', width: 150,align:'center'}
            , {field: 'LB', title: '类别', width: 100,align:'center'}
            , {field: 'FLBM', title: '分类编码', width: 80,align:'center'}

            , {field: 'SL', title: '数量', width: 40, align: 'center'}
            , {field: 'ZZPH', title: '执照牌号', width: 100, align: 'center'}
            , {
                field: 'SYDWMC', title: '使用单位', width: 100, align: 'center'
            }
            , {field: 'ZGBMMC', title: '主管部门', width: 100, align: 'center'}
            , {field: 'FZRMC', title: '负责人', width: 50, align: 'center'}
            , {field: 'GGXH', title: '规格型号', width: 100, align: 'center'}
            , {field: 'ZBHRQ', title: '资本化日期', width: 80, align: 'center'}
            , {field: 'YSYNX', title: '已使用年限', width: 70, align: 'center'}
            , {field: 'ZCYZ', title: '资产原值', width: 60, align: 'center'}
            , {field: 'ZJNX', title: '折旧年限', width: 60, align: 'center'}
            , {field: 'YXZT', title: '运行状态', width: 60, align: 'center'}
            , {field: 'DD', title: '地点', width: 80, align: 'center'}
            , {field: 'ZT', title: '清查情况', width: 80,align:'center'}
            , {field: 'BZ', title: '备注', width: 180, align: 'center'}
            , {field: 'YLZD1', title: '预留字段1', width: 100, align: 'center'}
            , {field: 'YLZD2', title: '预留字段2', width: 100, align: 'center'}
            , {field: 'YLZD3', title: '预留字段3', width: 100, align: 'center'}
            , {field: 'YLZD4', title: '预留字段4', width: 100, align: 'center'}
        ]]
    };
    $(function () {
        //handleAuthDataRule();
        $('#datatable1').height(($(window).height() - 120) + 'px');
        $('#datatable1').datagrid(dgOptions);
    });

    function editRow(index) {
        if (!endEditRow()) return;
        if (index != null) {
            $("#datatable1").datagrid("beginEdit", index);
            editingIndex = index;
        }
    }
    var unitTreeName = undefined;
    var unitTreeId = undefined;
    function endEditRow() {
        if (editingIndex != null) {
            if ($('#datatable1').datagrid('validateRow', editingIndex)) {
                $("#datatable1").datagrid("endEdit", editingIndex);
                editingIndex = null;
            }
            else {
                //$.messager.alert("操作提示", "第" + (editingIndex + 1) + "行数据校验未通过！");
                return false;
            }
        }
        return true;
    }

    function savemb() {
        if (!endEditRow()) return;
        if (saveFlag) {
            $.messager.show({
                title: '操作提示',
                msg: '数据正在保存，请勿重复提交！',
                showType: 'show'
            });
            //$.messager.alert("操作提示", "数据正在保存，请勿重复提交！");
            return;
        }
        var updateStr = JSON.stringify($("#datatable1").datagrid("getChanges", "updated"));
        //console.log(updateStr);
        if (updateStr == "[]") {
            $.messager.show({
                title: '操作提示',
                msg: '数据未作修改，无需保存！',
                showType: 'show'
            });
            return;
        }
        saveFlag = true;
        $.ajax({
            url: "${ctx}/gdzc/saveZcdbfq",
            type: "post",
            data: {C6: updateStr},
            success: function (result) {
                if (result != "") {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '保存成功' : '保存失败',
                        showType: 'show'
                    });
                    //$.messager.alert("操作提示", result=='1'?'保存成功':'保存失败');
                    saveFlag = false;
                }
                else {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '保存成功' : '保存失败',
                        showType: 'show'
                    });
                    //$.messager.alert("操作提示", "保存失败！");
                    saveFlag = false;
                }
                doCx();
            }
        });
    }
    function shcancleZcdb() {
        var ttt = $("#datatable1").datagrid("getSelections");
        if (ttt && ttt.length > 0) {
        } else return;
        if (!confirm('确定要退回？')) return;
        $.ajax({
            url: "${ctx}/gdzc/delZcdbfq",
            type: "post",
            data: {C6: JSON.stringify(ttt)},
            success: function (result) {
                if (result != "") {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '操作成功' : '操作失败',
                        showType: 'show'
                    });
                }
                else {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '操作成功' : '操作失败',
                        showType: 'show'
                    });
                }
                doCx();
            }
        });
    }
    function toOk() {
        var ttt = $("#datatable1").datagrid("getSelections");
        if (ttt && ttt.length > 0) {
        } else return;
        if (!confirm('确定要通过？')) return;
        $.ajax({
            url: "${ctx}/gdzc/shokZcdb",
            type: "post",
            data: {C6: JSON.stringify(ttt)},
            success: function (result) {
                if (result != "") {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '操作成功' : '操作失败',
                        showType: 'show'
                    });
                }
                else {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '操作成功' : '操作失败',
                        showType: 'show'
                    });
                }
                doCx();
            }
        });
    }
    function fb() {
        $('#searchForm').attr("action", '${ctx}/cbgl/rgcb/glqfb').submit();

    }
    function getInitParam() {
        var param = {};
        param = {
            "limit": function () {
                return $("#datatable1").datagrid("getPager").pagination("options").pageSize;
            },
            "start": function () {
                return $("#datatable1").datagrid("getPager").pagination("options").pageNumber;
            }
        };
        $("#searchForm :input[name]").each(function (i, item) {
            if ($(item).val()) {
                param[$(item).attr("name")] = $(item).val();
            }
        });
        //console.log(param);
        return param;
    }
</script>
</body>
</html>