<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资产信息维护</title>
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
    <li class="active"><a href="" class="gicon-paperclip">&nbsp;资产信息维护</a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/gdzc/bzlist" modelAttribute="condition" method="post" class="breadcrumb form-search">
    <div style="margin-top:8px;">
        <input name="c4" value="${condition.c4}" type="text" placeholder="关键字…">
        <a class="btn btn-primary" href="#" onclick="doCx2();"><i class="icon-play"></i> 查询</a>
        <a class="btn btn-success" href="#" onclick="savemb();"><i class="icon-inbox"></i> 保存</a>
        <a class="btn btn-info" href="#" onclick="document.location='${ctx}/gdzc/exportData_bz'"><i class="icon-download-alt"></i> 导出数据</a>
    </div>
</form:form>
<sys:message content="${message}"/>
<table id="datatable1"></table>
<div id="winimg" class="easyui-window" title="添加" style="width: 800px; height: 430px; padding: 10px;"
     data-options="iconCls:'icon-add',modal:true,border:true,closed:true,footer:'#footer',
        onResize:function(){
				$(this).window('hcenter');
		}">
    <table id="datatable2"></table>
    <div id="footer" style="padding: 5px;">
        <a class="btn btn-success" href="#" onclick="doUpdImg('${ctx}/gdzc/doImgUpd')"><i class="icon-inbox"></i> 上传</a>
        <a class="btn btn-danger" href="#" onclick="$('#winimg').window('close');"><i class="icon-off"></i> 关闭</a>
    </div>
</div>
<div id="imgUpd" class="easyui-window" title="上传" style="width: 600px; height: 400px; padding: 10px;"
     data-options="iconCls:'icon-circle-arrow-up',modal:true,border:true,closed:true,
        onResize:function(){
				$(this).window('hcenter');
		}">
</div>
<script type="text/javascript">
    var C1='';
    $(function() {
        $(".btn").click(function(){
            $(this).button('loading').delay(2000).queue(function() {
                $(this).button('reset');
                $(this).dequeue();
            });
        });
    });
    function toUpdImg(url){
        $('#footer').show();
        $('#winimg').window({ title: '图片信息' });
        $('#winimg').window('open');

        $('#winimg').html("<iframe id=\"imgList\" Scrolling=\"yes\" Frameborder=\"0\" Src=\"" + url + "\" Style=\"width:98%;height:97%;\"></iframe>");
    }
    function doUpdImg(url){
        var ttt = $("#datatable1").datagrid("getSelections");
        if (ttt && ttt.length > 0) {
        } else return;
        C1=ttt[0].ZCBM;
        url+="?c1="+C1;
        $('#imgUpd').window('open');
        $('#imgUpd').html("<iframe Scrolling=\"yes\" Frameborder=\"0\" Src=\"" + url + "\" Style=\"width:98%;height:97%;\"></iframe>");
    }
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
        pageSize: 50,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        singleSelect:true,
        url: '${ctx}/gdzc/gdzcList_bz',
        frozenColumns:[[
            {field: 'tu', title: '图片', width: 100,align:'center',
                formatter:function(value,rec){
                    var btn = '<a class="btn btn-primary" href="#" onclick="toUpdImg(\'${ctx}/gdzc/imgList?c1='+rec.ZCBM+'\');"><i class="icon-circle-arrow-up"></i> 上传</a>';
                    return btn;
                }
            }
            ,{field: 'ZCBM', title: '资产编码(系统内)', width: 100,align:'center'}
            , {field: 'ERPBM', title: 'ERP编码', width: 100,align:'center'}
            , {field: 'ZCMC', title: '资产名称', width: 170,align:'center',sortable:true}
        ]],
        columns: [[
            {field: 'ID',  hidden: true}

            , {field: 'DL1', title: '大类1', width: 100,align:'center',sortable:true}
            , {field: 'DL2', title: '大类2', width: 150,align:'center',sortable:true}
            , {field: 'LB', title: '类别', width: 100,align:'center',sortable:true}
            , {field: 'FLBM', title: '分类编码', width: 80,align:'center'}

            , {field: 'SL', title: '数量', width: 40,align:'center'}
            , {field: 'ZZPH', title: '执照牌号', width: 100,align:'center'}
            , {field: 'SYDWMC', title: '使用单位', width: 140,align:'center'}
            , {field: 'ZGBMMC', title: '主管部门', width: 100,align:'center'}
            , {field: 'FZRMC', title: '负责人', width: 50,align:'center'}
            , {field: 'GGXH', title: '规格型号', width: 100,align:'center'}
            , {field: 'ZBHRQ', title: '资本化日期', width: 80,align:'center'}
            , {field: 'YSYNX', title: '已使用年限', width: 70,align:'center'}
            , {field: 'ZCYZ', title: '资产原值', width: 80,align:'center'}
            , {field: 'ZJNX', title: '折旧年限', width: 60,align:'center'}
            , {field: 'YXZT', title: '运行状态', width: 60,align:'center'}
            , {field: 'DD', title: '地点', width: 150,editor: "text",align:'center'}
            , {field: 'ZT', title: '清查情况', width: 80,align:'center'}
            , {field: 'BZ', title: '备注', width: 140,editor: "text",align:'center'}
            , {field: 'YLZD1', title: '预留字段1', width: 100,editor: "text",align:'center'}
            , {field: 'YLZD2', title: '预留字段2', width: 100,align:'center'}
            , {field: 'YLZD3', title: '预留字段3', width: 100,align:'center'}
            , {field: 'YLZD4', title: '预留字段4', width: 100,align:'center'}
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

    function doCx(){
        dgOptions.queryParams=getInitParam();
        $('#datatable1').datagrid(dgOptions);
    }
    function doCx2() {
        dgOptions.pageNumber=1;
        dgOptions.queryParams=getInitParam();
        $('#datatable1').datagrid(dgOptions);
    }

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
                //console.log('-----------${fns:toJson(fns:getDictList('gdzc_dw')) }');
                var dw = $('#datatable1').datagrid("getEditor", {index: editingIndex, field: "ZGBMMC"});
                if (dw) {
                    var MC = $(dw.target).combobox("getText");
                    var ID = $(dw.target).combobox("getValue");
                    $('#datatable1').datagrid("getRows")[editingIndex]["ZGBMMC"] = MC;
                }
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