<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资产调拨审核</title>
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
    <li class="active"><a href="" class="gicon-edit">&nbsp;资产调拨【待审核】</a></li>
    <li><a href="${ctx}/gdzc/zcdb_sh/ysh" class="gicon-check">&nbsp;资产调拨【已审核】</a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/gdzc/gclist" modelAttribute="condition" method="post"
           class="breadcrumb form-search">
    <div style="margin-top:8px;">
        <sys:treeselect id="office" name="c2" value="${condition.c2}" labelName="parent.name" labelValue="${office.parent.name}"
                        title="机构" url="/sys/office/treeData" extId="${office.id}" isAll="true" cssClass="input-small" smallBtn="true" hideBtn="false"/>
        <a class="btn btn-primary" href="#" onclick="doCx();"><i class="icon-refresh"></i> 刷新</a>
        <a class="btn btn-success" href="#" onclick="savemb();"><i class="icon-inbox"></i> 保存</a>
        <a class="btn btn-info" href="#" onclick="toOk();"><i class="icon-check"></i> 审核通过</a>
        <a class="btn btn-danger" href="#" onclick="toCancle();"><i class="icon-thumbs-down"></i> 审核不通过</a>
            <%--<input id="btnImport" class="btn btn-primary" type="button"  value="导入"/>--%>
    </div>
</form:form>
<sys:message content="${message}"/>
<table id="datatable1"></table>

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
    var okFlag=false;
    //var wid=$('#dataGrid').parent().width();
    //console.log(wid);
    //var wholeHeight=$(window).height();
    //var headerHeight = $('#dataGrid').parent().find('#searchForm').height();
    var headerHeight = 200;
    //console.log(headerHeight);
    function doCx() {
        dgOptions.queryParams = getInitParam();
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
        singleSelect: false,
        checkOnSelect : 'true',
        url: '${ctx}/gdzc/zcdb_shlist',
        frozenColumns: [[
            {field: 'CHECKBOX', hidden: false, checkbox: true, align: 'center'}
            ,{field: 'STATUS', title: '状态',align:'center', width: 60,
                formatter: function (value, row) {
                    var n = "临时保存";
                    if ("0" == row.STATUS) n = "待审核";
                    if ("1" == row.STATUS) n = "审核通过";
                    if ("2" == row.STATUS) n = "审核不通过";
                    return n;
                },
                styler: function (value, row, index) {

                    if(row.STATUS=='0')
                        return 'background-color:blue;color:white';
                    if(row.STATUS=='1')
                        return 'background-color:green;color:white';
                    if(row.STATUS=='2')
                        return 'background-color:red;color:white';
                }
            }, {field: 'DEPTMC', title: '提交单位', width: 90,align:'center'}
            , {field: 'SBDATE', title: '提交时间', width: 110,align:'center'}
            , {field: 'ZCBM', title: '资产编码(系统内)', width: 100,align:'center'}
            , {field: 'ERPBM', title: 'ERP编码', width: 100,align:'center'}
            , {field: 'ZCMC', title: '资产名称', width: 170,align:'center'}


        ]],
        columns: [[
            {field: 'ID', hidden: true}
            , {field: 'DL1', title: '大类1', width: 100,align:'center'}
            , {field: 'DL2', title: '大类2', width: 150,align:'center'}
            , {field: 'LB', title: '类别', width: 100,align:'center'}
            , {field: 'FLBM', title: '分类编码', width: 80,align:'center'}
            , {field: 'SL', title: '数量', width: 40,align:'center'}
            , {field: 'ZZPH', title: '执照牌号', width: 100,align:'center'}
            , {
                field: 'SYDWMC', title: '使用单位', width: 200,align:'center',
                styler: function (value, row, index) {
                    return 'background-color:red;color:white';
                }
            }
            , {field: 'ZGBMMC', title: '主管部门', width: 100,align:'center'}
            , {field: 'FZRMC', title: '负责人', width: 50,align:'center'}
            , {field: 'GGXH', title: '规格型号', width: 100,align:'center'}
            , {field: 'ZBHRQ', title: '资本化日期', width: 80,align:'center'}
            , {field: 'YSYNX', title: '已使用年限', width: 70,align:'center'}
            , {field: 'ZCYZ', title: '资产原值', width: 60,align:'center'}
            , {field: 'ZJNX', title: '折旧年限', width: 60,align:'center'}
            , {field: 'YXZT', title: '运行状态', width: 60,align:'center'}
            , {field: 'DD', title: '地点', width: 80, editor: "text",align:'center'}
            , {field: 'ZT', title: '清查情况', width: 80,align:'center'}
            , {field: 'BZ', title: '备注', width: 180, editor: "text",align:'center'}
            , {field: 'YLZD1', title: '预留字段1', width: 100, editor: "text",align:'center'}
            , {field: 'YLZD2', title: '预留字段2', width: 100, editor: "text",align:'center'}
            , {field: 'YLZD3', title: '预留字段3', width: 100, editor: "text",align:'center'}
            , {field: 'YLZD4', title: '预留字段4', width: 100, editor: "text",align:'center'}
        ]],
        onClickRow: function (index, row) {
            if (editingIndex != null && editingIndex != index) {
                endEditRow();
            }
        },
        onEndEdit: function (index, row) {
            if (unitTreeName != undefined) {
                $("#datatable1").datagrid('getRows')[editingIndex]['SYDWMC'] = unitTreeName;
                $("#datatable1").datagrid('getRows')[editingIndex]['SYDWDM'] = unitTreeId;
                //$("#datatable1").datagrid('getRows')[editingIndex]['ZGBMMC'] = unitTreeName;
            }
        },
        onDblClickCell: function (index, field, value) {
            var ss=$("#datatable1").datagrid('getRows')[index]['STATUS'];
            if(ss=='1'||ss=='0') return;
            editRow(index);
        }
    }
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
    function toCancle(){
        var ttt = $("#datatable1").datagrid("getSelections");
        if (ttt && ttt.length > 0) {
        } else return;
        if (!confirm('确定要退回？')) return;
        $.ajax({
            url: "${ctx}/gdzc/shcancleZcdb",
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
    function toOk(){
        var ttt = $("#datatable1").datagrid("getSelections");
        if (ttt && ttt.length > 0) {
        } else return;
        if(okFlag) return;
        okFlag=true;
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
                okFlag=false;
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