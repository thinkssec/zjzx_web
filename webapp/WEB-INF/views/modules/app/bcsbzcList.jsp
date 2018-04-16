<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>补充、调整设备主材</title>
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
    <li class="active"><a href="">补充、调整设备主材</a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/bcsbzc/list" method="post" class="breadcrumb form-search" onsubmit="loading('正在载入，请稍等...');">
    <div style="margin-top:8px;">
        <input id="hid_c2" type="hidden" name="c2" value="">
        <input type="hidden" name="c7" value="${condition.c7}">
        <input type="hidden" name="c5" value="${condition.c5}"><%--查询标志--%>
        <input name="c1" class="easyui-textbox" labelPosition="left"
               data-options="prompt:'在此输入关键字...'" style="width:300px" value="${condition.c1}">
        &nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="savemb();" value="保存"/>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="xf();" value="下发"/>

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
        url: '${ctx}/bcsbzc/bcsbzcList',
        columns: [[
            {field: 'ID',  hidden: false, checkbox: true,align:'center'}
            , {field: 'MC', title: '名称', width: 80,align:'center'}
            , {field: 'BM', title: '编码', width: 80,editor: "text",align:'center'}
            , {field: 'GL', title: '归类', width: 80,editor: "text",align:'center'}
            , {field: 'DW', title: '单位', width: 40,editor: "text",align:'center'}
            , {field: 'SL', title: '数量', width: 50,editor: "text",align:'center'}
            , {field: 'JG', title: '价格', width: 80,editor: "text",align:'center'}
            , {field: 'CJMC', title: '厂家名称', width: 80,editor: "text",align:'center'}
            , {field: 'ZT', title: '状态', width: 40,align:'center'}
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
            url: "${ctx}/bcsbzc/bcsbzcsave",
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
    function xf(){
        var ttt=datagrid.datagrid('getSelections');
        if(ttt&&ttt.length>0){
        }else return;
        if(!confirm('确实要下发该项指标？')) return;
        $('#hid_c2').val(JSON.stringify(ttt));
        console.log(JSON.stringify(ttt));
        $('#searchForm').attr("action",'${ctx}/bcsbzc/bcsbzcxf').submit();

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