<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>系统升级管理</title>
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
    <li class="active"><a href="">系统升级管理</a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/bbgl/xtlist" method="post" class="breadcrumb form-search" onsubmit="loading('正在载入，请稍等...');">
    <div style="margin-top:8px;">
        <input id="hid_c2" type="hidden" name="c2" value="">
        <input type="hidden" name="c7" value="${condition.c7}">
        <input type="hidden" name="c5" value="${condition.c5}"><%--查询标志--%>
        <input name="c1" class="easyui-textbox" labelPosition="left"
               data-options="prompt:'在此输入关键字...'" style="width:300px" value="${condition.c1}">
        &nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="addRow()" value="添加"/>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="savemb();" value="保存"/>
        <input class="btn btn-primary" type="button" onclick="delZh();" value="删除"/>
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
        url: '${ctx}/bbgl/xtbbList',
        columns: [[
            {field: 'ID',  hidden: false, checkbox: true,align:'center'}
            , {field: 'BBH', title: '版本流水号', width: 40,align:'center'}
            , {field: 'BZ', title: '备注', width: 180,editor: "text",align:'center'}
            , {field: 'SJ', title: '入库时间', width: 50,align:'center'}
            , {field: 'ZT', title: '状态', width: 40,align:'center'}
            , {field: 'CZZ', title: '操作', width: 40,align:'center',
                formatter: function(value,row,index){
                    if(row.ID&&row.ID!=''&&row.STATUS!='1')
                        return "<a href='#' name='opera' class='easyui-linkbutton' onclick='document.location=\"/static/ckfinder/ckfinderCx.jsp?ID="+row.ID+"&BBH="+row.BBH+"\"'>上传</a>";
                    else
                        return "";
            }}
        ]],
        method: 'post',
        pageSize: 20,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        //singleSelect:true,
        onLoadSuccess:function(data){
            $("a[name='opera']").linkbutton({text:'上传文件',plain:false,iconCls:'icon-add'});
        },
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
                $("a[name='opera']").linkbutton({text:'上传文件',plain:false,iconCls:'icon-add'});
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
        var updateStr = JSON.stringify(datagrid.datagrid("getChanges"));
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
            url: "${ctx}/bbgl/bbxtsave",
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
        $('#searchForm').attr("action",'${ctx}/bbgl/bbxtxf').submit();

    }
    function addRow(){
        datagrid.datagrid('appendRow',{
            ID: '',
            BBH: '',
            MC: '',
            SJ:'',
            ZT:''
        });
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
    function delZh(){
        var ttt=datagrid.datagrid('getSelections');
        if(ttt&&ttt.length>0){
        }else return;
        if(!confirm('确实要删除该版本？')) return;
        $('#hid_c2').val(JSON.stringify(ttt));
        $('#searchForm').attr('action','${ctx}/bbgl/xtbbdel').submit();
    }
</script>
</body>
</html>