<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>申请机器认证</title>
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
<ul class="nav nav-tabs">
    <li class="active"><a href="">申请机器认证</a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/jqsq/list" method="post" class="breadcrumb form-search" onsubmit="loading('正在载入，请稍等...');">
    <div style="margin-top:8px;">
        <input id="hid_c2" type="hidden" name="c2" value="">
        <input type="hidden" name="c7" value="${condition.c7}">
        <input type="hidden" name="c5" value="${condition.c5}"><%--查询标志--%>
        &nbsp;&nbsp;&nbsp;<%--<input id="btnSubmit" class="btn btn-primary" type="submit" value="同意"/>--%>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="ty();" value="同意"/>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="bty();" value="不同意"/>
    </div>
</form:form>
<sys:message content="${message}"/>
<table id="datatable1"></table>
<script type="text/javascript">
    // 初始化DataGrid对象
    var editingIndex = null;
    var datagrid=$('#datatable1');
    var saveFlag = false;
    $("#btnImport").click(function(){
        $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
            bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
    });
    var dgOptions = {
        rownumbers: true,
        //fit: true,
        // height:700,
        border: true,
        multiSort: true,
        url: '${ctx}/jqsq/jqsqList',
        columns: [[
            {field: 'ID',  hidden: false, checkbox: true,align:'center'}
            , {field: 'SQR', title: '申请人', width: 40,align:'center'}
            , {field: 'SQDW', title: '申请单位', width: 80,align:'center'}
            , {field: 'SQYQSJ', title: '申请油区数据', width: 70,align:'center'}
            , {field: 'SQSJ', title: '手机', width: 50,align:'center'}
            , {field: 'SQYX', title: '邮箱', width: 50,align:'center'}
            , {field: 'SJ', title: '时间', width: 60,align:'center'}
            , {field: 'CPUID', title: 'CPUID', width: 80,align:'center'}
            , {field: 'IP', title: 'IP', width: 40,align:'center'}
        ]],
        method: 'post',
        pageSize: 20,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        //singleSelect:true,
        onClickRow: function (index, row) {
            if (editingIndex != null && editingIndex != index) {
                endEditRow();
            }
        },
        onDblClickCell: function (index, field, value) {
            editRow(index);
        }}
    $(function () {
        $(document).ready(function () {
            datagrid.height(($(window).height() - 120) + 'px');
            datagrid.datagrid(dgOptions);
            fitCoulms();
        });
    });
    function fitCoulms() {
        datagrid.datagrid({
            fitColumns: true
        });
    }
    function editRow(index) {
        if (!endEditRow()) return;
        if (index != null) {
            datagrid.datagrid("beginEdit", index);
            editingIndex = index;
        }
    }
    function endEditRow() {
        if (editingIndex != null) {
            if (datagrid.datagrid('validateRow', editingIndex)) {
                datagrid.datagrid("endEdit", editingIndex);
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
        var updateStr = JSON.stringify(datagrid.datagrid("getChanges", "updated"));
        //console.log(updateStr);
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
            url: "${ctx}/jqsq/jqsqList",
            type: "post",
            data: {C6:updateStr},
            success: function (result) {
                if (result != "") {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '保存成功' : '保存失败',
                        showType: 'show'
                    });
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
    function ty(){
        var ttt=datagrid.datagrid('getSelections');
        if(ttt&&ttt.length>0){
        }else return;
        if(!confirm('确实同意机器申请？')) return;
        $('#hid_c2').val(JSON.stringify(ttt));
        //console.log(JSON.stringify(ttt));
        $('#searchForm').attr("action",'${ctx}/jqsq/jqsqty').submit();
    }

    function bty(){
        var ttt=datagrid.datagrid('getSelections');
        if(ttt&&ttt.length>0){
        }else return;
        if(!confirm('确实不同意机器申请？')) return;
        $('#hid_c2').val(JSON.stringify(ttt));
        //console.log(JSON.stringify(ttt));
        $('#searchForm').attr("action",'${ctx}/jqsq/jqsqbty').submit();
    }

    function getInitParam() {
        var param = {};
        param = {
            "limit": function () {
                return datagrid.datagrid("getPager").pagination("options").pageSize;
            },
            "start": function () {
                return datagrid.datagrid("getPager").pagination("options").pageNumber;
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