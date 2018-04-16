<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>补充、调整指标管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/color.css">
    <script type="text/javascript" src="${ctxStatic}/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<style>
    .table th, .table td {
        padding: 8px;
        line-height: 20px;
        text-align: center;
        vertical-align: top;
        border-top: 1px solid #ddd;
    }
</style>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="">补充、调整指标管理</a></li>
    <%--<shiro:hasPermission name="cbgl:clf"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/bczb/list" method="post" class="breadcrumb form-search" onsubmit="loading('正在导入，请稍等...');">
    <div style="margin-top:8px;">
        <input name="c1" class="easyui-textbox" labelPosition="left"
               data-options="prompt:'在此输入关键字...'" style="width:300px" value="${condition.c1}">
        <input class="btn btn-primary" type="submit" value="查询"/>
        <input class="btn btn-primary" type="button" onclick="zbhb();" value="合并"/>
        <input class="btn btn-primary" type="button"
               onclick="document.location='${ctx}/bczb/zhzblist'" value="组合指标"/>
    </div>
</form:form>
<sys:message content="${message}"/>
<%--<h3 style="text-align: center;">补充、调整指标管理</h3>--%>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
</table>
<div id="zbzh" class="easyui-window" title="指标组合" style="width:800px;height:450px;padding:10px;"
     data-options="iconCls:'icon-search',modal:true,closed:true,tools:'#tb',
        onResize:function(){
				$(this).window('hcenter');
		}">
    <form:form id="saveForm" action="${ctx}/bczb/zhsave" method="post" class="breadcrumb form-search" onsubmit="loading('正在载入，请稍等...');">
        <div style="margin-bottom:8px">
            <input id="txt_zbmc" class="easyui-textbox" name="conHm['NAME']" style="width:50%" data-options="label:'指标名称:',required:false">
        </div>
        <div style="margin-bottom:8px">
            <input id="comb_fws" class="easyui-combogrid" name="conHm['USERID']" style="width:50%" data-options="label:'编制人员:',editable: false">
        </div>
        <div style="margin-bottom:8px">
            <input id="txt_bz" class="easyui-textbox" name="conHm['BZ']" style="width:95%;height:35px" data-options="label:'备注:',multiline:true">
        </div>
        <input id="hid_bzcbs" name="conHm['BCZBS']" type="hidden" value="">
        <table id="bhtab" class="table table-striped table-bordered table-condensed">

        </table>
    </form:form>
</div>
<div id="tb" style="padding:2px 5px;">
    <a href="javascript:void(0)" class="icon-add" onclick="javascript:saveZh()"></a>

</div>

<script>
    var includeHt={};
    var datagrid = $("#contentTable");
    var bhtab = $("#bhtab");
    var dgOptions = {
        rownumbers: true,
        border: true,
        url: '${ctx}/bczb/bczbList',
        method: 'post',
        pageSize: 50,
        pagination: true,
        multiSort: true,
        showFooter: true,
        queryParams: getInitParam(),
        columns: [[
            {field: 'ID', checkbox: true, hidden: false, align: 'center'}
            , {field: 'ZBMC', title: '名称', width: 300, sortable: true, align: 'center',
                formatter: function(value,row,index){
                    if(row.STATUS=='1')
                        return "<span style=\"color:red\">"+row.ZBMC+"</span>";
                    else if(includeHt[row.ID]&& includeHt[row.ID]!= '0000') {
                        return "<span style=\"color:red\">"+row.ZBMC+"</span>";
                    }else
                        return "<span style=\"color:black\">"+row.ZBMC+"</span>";
                }
            }
            , {field: 'ZBLX', title: '类型', width: 50, align: 'center'}
            , {field: 'ZBBM', title: '编码', width: 60, align: 'center'}
            , {field: 'ZBNR', title: '指标内容', width: 60, align: 'center'}
            , {field: 'DW', title: '单位', width: 80, align: 'center'}
            , {field: 'SL', title: '数量', width: 80, align: 'center'}
            , {field: 'JG', title: '估价', width: 80, sortable: true, align: 'center'}
            , {field: 'ZCJ', title: '主材费', width: 80, sortable: true, align: 'center'}
            , {field: 'SBJ', title: '设备费', width: 80, align: 'center'}
        ]],
        onLoadSuccess: function (data) {
            $.each(data.rows, function (index, item) {
                if (item.STATUS == '1') {
                    $("input[type='checkbox']")[index + 1].disabled = true;
                }else if(includeHt[item.ID]&& includeHt[item.ID]!= '') {
                    $("input[type='checkbox']")[index + 1].disabled = true;
                }
            });
        },
        onClickRow: function (rowIndex, rowData) {
            //根据status值 单击单选行不可用
            if (rowData.STATUS == "1") {
                //datagrid.datagrid('unselectRow', rowIndex);
                //datagrid.datagrid('uncheckRow', rowIndex);
            } else if(includeHt[rowData.ID]&& includeHt[rowData.ID]!= ""){
                //datagrid.datagrid('unselectRow', rowIndex);
                //datagrid.datagrid('uncheckRow', rowIndex);
            }else {
                //addrow();
            }
        }, onCheckAll: function (rows) {
            for( var i=0;i<rows.length;i++){
                if (rows[i].STATUS=='1') {
                    datagrid.datagrid('unselectRow', i);
                    datagrid.datagrid('uncheckRow', i);
                }else if(includeHt[rows[i].ID]&& includeHt[rows[i].ID]!= ''){
                    datagrid.datagrid('unselectRow', i);
                    datagrid.datagrid('uncheckRow', i);
                }else{
                    includeHt[rows[i].ID]=rows[i];
                }
            }
        },onCheck:function(index, row){
            if($("input[type='checkbox']")[index + 1].disabled != true)
            includeHt[row.ID]=row;
        },onUncheck:function(index, row){
            if($("input[type='checkbox']")[index + 1].disabled != true)
            includeHt[row.ID]="";
        },onBeforeSelect:function(index, row){
            if (row.STATUS == "1") return false;
            if(includeHt[row.ID]&& includeHt[row.ID]!= "") return false;
        },onBeforeCheck:function(index, row){
            if (row.STATUS == "1") return false;
            if(includeHt[row.ID]&& includeHt[row.ID]!= "") return false;
        }
    };
    var dgOptions2 = {
        rownumbers: true,
        border: true,
        height:250,
        method: 'post',
        multiSort: true,
        showFooter: true,
        columns: [[
            {field: 'ID', checkbox: true, hidden: false, align: 'center'}
            , {field: 'ZBMC', title: '名称', width: 300, sortable: true, align: 'center'}
            , {field: 'ZBLX', title: '类型', width: 50, align: 'center'}
            , {field: 'ZBBM', title: '编码', width: 60, align: 'center'}
            , {field: 'ZBNR', title: '指标内容', width: 60, align: 'center'}
            , {field: 'DW', title: '单位', width: 80, align: 'center'}
            , {field: 'SL', title: '数量', width: 80, align: 'center'}
            , {field: 'JG', title: '估价', width: 80, sortable: true, align: 'center'}
            , {field: 'ZCJ', title: '主材费', width: 80, sortable: true, align: 'center'}
            , {field: 'SBJ', title: '设备费', width: 80, align: 'center'}
        ]],
        onLoadSuccess: function (data) {
        }
    };
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
    function zbhb(){
        $('#zbzh').window('vcenter');
        $('#zbzh').window('open');
        bhtab.datagrid(dgOptions2);
        bhtab.datagrid('loadData', getInclude());
        bhtab.datagrid('selectAll');
    }
    function getInclude(){
        var tt=[];
        for(var t in includeHt){
            if(includeHt[t]&&includeHt[t]!=''){
                tt[tt.length]=includeHt[t];
            }
        }
        //return tt;
        return {'total':tt.length,'rows':tt};
    }
    function saveZh(){
        var ttt=bhtab.datagrid('getSelections');
        $('#hid_bzcbs').val(JSON.stringify(ttt));
        $('#saveForm').submit();
    }
</script>
</body>

</html>