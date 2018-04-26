<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>固定资产信息采集</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/icon.css">
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
<div id="importBox" class="hide">
    <form id="importForm" action="${ctx}/gdzc/importGlq" method="post" enctype="multipart/form-data"
          class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
        <input type="hidden" name="c1" value="${condition.c1}">
        <input type="hidden" name="c2" value="${condition.c2}">
        <input type="hidden" name="c3" value="${condition.c3}">
        <input type="hidden" name="c4" value="${condition.c4}">
        <input type="hidden" name="c5" value="${condition.c5}">
        <input type="hidden" name="c7" value="${condition.c7}">
        <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
        <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
        <a href="${ctx}/gdzc/glqTemplate">下载模板</a>
    </form>
</div>
<ul class="nav nav-tabs">
    <li class="active"><a href="">固定资产信息采集</a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/gdzc/list" modelAttribute="condition" method="post" class="breadcrumb form-search">
    <div style="margin-top:8px;">
        <input type="hidden" name="c2" value="${condition.c2}">
        <input type="hidden" name="c7" value="${condition.c7}">
        <input type="hidden" name="c5" value="${condition.c5}"><%--查询标志--%>
        <form:select path="c1" class="input-xlarge" style="width:160px">
            <form:option value="" label="请选择"/>
            <form:options items="${dwList}" htmlEscape="false"/>
        </form:select>
        <label>日期范围：&nbsp;</label><input id="beginDate" name="c3" type="text" readonly="readonly" maxlength="20"
                                         class="input-mini Wdate"
                                         value="${condition.c3}"
                                         onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        <label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="c4" type="text"
                                                                    readonly="readonly" maxlength="20"
                                                                    class="input-mini Wdate"
                                                                    value="${condition.c4}"
                                                                    onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>&nbsp;&nbsp;

        &nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="savemb();" value="保存"/>
        <input class="btn btn-primary" type="button" onclick="document.location='${ctx}/gdzc/exportData'"  value="导出数据"/>
        <input id="btnImport" class="btn btn-primary" type="button"  value="导入"/>
    </div>
</form:form>
<sys:message content="${message}"/>
<table id="datatable1"></table>

<script type="text/javascript">
    // 初始化DataGrid对象
    var editingIndex = null;
    var saveFlag = false;
    //var wid=$('#dataGrid').parent().width();
    //console.log(wid);
    //var wholeHeight=$(window).height();
    //var headerHeight = $('#dataGrid').parent().find('#searchForm').height();
    var headerHeight =200;
    //console.log(headerHeight);
    $("#btnImport").click(function(){
        $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
            bottomText:"导入文件不能超过5M，仅允许导入“xls”格式文件！"});
    });
    var dgOptions = {
        rownumbers: true,
        //fit: true,
       // height:700,
        border: true,
        //method: 'post',
        //toolbar: '#tb',
        pageSize: 20,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        singleSelect:true,
        url: '${ctx}/gdzc/gdzcList',
        columns: [[
            {field: 'ID',  hidden: true}
            , {field: 'ZCBM', title: '资产编码', width: 110}
            , {field: 'DL1', title: '大类1', width: 100}
            , {field: 'DL2', title: '大类2', width: 150,editor: "text"}
            , {field: 'FLBM', title: '分类编码', width: 100,editor: "text"}
            , {field: 'LB', title: '类别', width: 100,editor: "text"}
            , {field: 'ZCMC', title: '资产名称', width: 120,editor: "text"}
            , {field: 'SL', title: '数量', width: 80,editor: "text"}
            , {field: 'ZZPH', title: '执照牌号', width: 100,editor: "text"}
            , {field: 'SYDWMC', title: '使用单位', width: 100,editor: "text"}
            , {field: 'ZGBMMC', title: '主管部门', width: 100,editor: "text"}
            , {field: 'FZRMC', title: '负责人', width: 80,editor: "text"}
            , {field: 'GGXH', title: '规格型号', width: 100,editor: "text"}
            , {field: 'ZBHRQ', title: '资本化日期', width: 80,editor: "text"}
            , {field: 'YSYNX', title: '已使用年限', width: 80,editor: "text"}
            , {field: 'ZCYZ', title: '资产原值', width: 80,editor: "text"}
            , {field: 'ZJNX', title: '折旧年限', width: 80,editor: "text"}
            , {field: 'YXZT', title: '运行状态', width: 80,editor: "text"}
            , {field: 'DD', title: '地点', width: 80,editor: "text"}
            , {field: 'BZ', title: '备注', width: 180,editor: "text"}
            , {field: 'YLZD1', title: '预留字段1', width: 180,editor: "text"}
            , {field: 'YLZD2', title: '预留字段2', width: 180,editor: "text"}
            , {field: 'YLZD3', title: '预留字段3', width: 180,editor: "text"}
            , {field: 'YLZD4', title: '预留字段4', width: 180,editor: "text"}
        ]],
        onClickRow: function (index, row) {
            if (editingIndex != null && editingIndex != index) {
                endEditRow();
            }
        },
        onDblClickCell: function (index, field, value) {
            editRow(index);
        }}
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
        console.log(updateStr);
        if ( updateStr == "[]" ) {
            $.messager.show({
                title: '操作提示',
                msg: '数据未作修改，无需保存！',
                showType: 'show'
            });
            return;
        }
        saveFlag = true;
        $.ajax({
            url: "${ctx}/gdzc/saveGdzc",
            type: "post",
            data: {C6:updateStr},
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
            }
        });
    }
    function fb(){
        $('#searchForm').attr("action",'${ctx}/cbgl/rgcb/glqfb').submit();

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