<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资产拆分</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/color.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/glyphicons/css/glyphicons.css">
    <script type="text/javascript" src="${ctxStatic}/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/easyui/datagrid-detailview.js"></script>
    <script type="text/javascript" src="${ctxStatic}/easyui/locale/easyui-lang-zh_CN.js"></script>

</head>
<style>
    table th {
        text-align: right;
    }
</style>

<body>
<ul class="nav nav-tabs">
    <li class="active"><a href=""><span class="gicon-cargo">&nbsp;资产拆分</span></a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/gdzc/list" modelAttribute="condition" method="post"
           class="breadcrumb form-search">
    <div style="margin-top:8px;">
        <input name="c4" value="${condition.c4}" type="text" placeholder="关键字…">
        <sys:treeselect id="office" name="c2" value="${condition.c2}" labelName="parent.name"
                        labelValue="${office.parent.name}"
                        title="机构" url="/sys/office/treeData" extId="${office.id}" isAll="true" cssClass="input-small"
                        smallBtn="true" hideBtn="false"/>

        <a class="btn btn-primary" href="#" onclick="doCx2();"><i class="icon-play"></i> 查询</a>
    </div>
</form:form>
<sys:message content="${message}"/>
<table id="datatable1"></table>
<div id="win" class="easyui-window" title="添加/编辑" style="width: 400px; height: 330px;padding: 10px;"
     data-options="iconCls:'icon-add',modal:true,border:true,closed:true,footer:'#footer',
        onResize:function(){
				$(this).window('hcenter');
		}">
    <div style="margin-bottom: 8px">
        <input type="hidden" id="hid_id" value="">
        <input type="hidden" id="hid_zcbm" value="">
        <input type="hidden" id="hid_erpbm" value="">
        <input type="hidden" id="hid_flbm" value="">
        <input id="txt_name" name="txt_name" class="easyui-textbox" style="width: 95%"
               data-options="label:'名称:',required:false">
    </div>
    <div style="margin-bottom: 8px">
        <input id="comb_sydw" class="easyui-combotree" name="comb_sydw" style="width: 95%"
               data-options="label:'使用单位:',
                    valueField: 'id',
                    textField: 'text',
                    editable:true,
                    multiple:false,
                    panelWidth:280,
                    panelHeight:300">
    </div>
    <div style="margin-bottom: 8px">
        <input id="txt_zcyz" name="txt_zcyz" class="easyui-numberbox" style="width: 95%"
               data-options="label:'资产原值:',editable: true">
    </div>
    <div style="margin-bottom: 8px">
        <input id="txt_fzr" name="txt_fzr" class="easyui-textbox" style="width: 95%"
               data-options="label:'负责人:',editable: true">
    </div>
    <div style="margin-bottom: 8px">
        <input id="txt_dd" name="txt_dd" class="easyui-textbox" style="width: 95%"
               data-options="label:'地点:',editable: true">
    </div>

    <div style="margin-bottom: 8px">
        <input id="txt_bz" name="txt_bz" class="easyui-textbox" style="width: 95%"
               data-options="label:'备注:',editable: true">
    </div>

    <div id="footer" style="padding: 5px;">
        <a class="btn btn-info" href="#" onclick="doSave();"><i class="icon-save"></i> 保存</a>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $(".btn").click(function () {
            $(this).button('loading').delay(2000).queue(function () {
                $(this).button('reset');
                $(this).dequeue();
            });
        });
    });
    // 初始化DataGrid对象
    var editingIndex = null;
    var saveFlag = false;
    var unitTreeName = undefined;
    var unitTreeId = undefined;
    var headerHeight = 200;
    function toadd(row) {
        $('#hid_id').val('');
        $('#hid_zcbm').val(row.ZCBM);
        $('#hid_erpbm').val(row.ERPBM);
        $('#hid_flbm').val(row.FLBM);
        $('#comb_sydw').combotree("setValue", '');
        $("#txt_name").textbox('setValue', '');
        $("#txt_zcyz").numberbox('setValue', '');
        $("#txt_fzr").textbox('setValue', '');
        $("#txt_dd").textbox('setValue', '');
        $("#txt_bz").textbox('setValue', '');
        $('#footer').show();
        $('#win').window({title: '添加分部'});
        $('#win').window('open');
    }
    function doEdit(row) {
        var ttt = $("#sub"+row.ZCBM).datagrid("getSelections");
        if (ttt && ttt.length > 0) {
        } else return;
        $('#hid_zcbm').val(row.ZCBM);
        $('#hid_erpbm').val(row.ERPBM);
        $('#hid_flbm').val(row.FLBM);
        $('#comb_sydw').combotree("setValue", ttt[0].SYDWDM);
        $("#txt_name").textbox('setValue', ttt[0].MC);
        $("#txt_zcyz").numberbox('setValue', ttt[0].ZCYZ);
        $("#txt_fzr").textbox('setValue', ttt[0].FZR);
        $("#txt_dd").textbox('setValue', ttt[0].DD);
        $("#txt_bz").textbox('setValue', ttt[0].BZ);
        $('#hid_id').val(ttt[0].ID);
        $('#footer').show();
        $('#win').window({title: '编辑分部'});
        $('#win').window('open');
    }
    function doDelete(row) {
        var ttt = $("#sub"+row.ZCBM).datagrid("getSelections");
        if (ttt && ttt.length > 0) {
        } else return;
        var v_id = ttt[0].ID;
        $.ajax({
            url: "${ctx}/gdzc/cfdel",
            type: "post",
            data: {C1: v_id},
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
                $('#sub'+row.ZCBM).datagrid("reload");
            }
        });
    }

    function doSave() {
        var v_id = $('#hid_id').val();
        var v_zcbm= $('#hid_zcbm').val();
        var v_erpbm= $('#hid_erpbm').val();
        var v_flbm= $('#hid_flbm').val();
        var v_sydw = $("#comb_sydw").combobox('getValue');
        var v_name = $("#txt_name").textbox('getValue');
        var v_zcyz = $("#txt_zcyz").numberbox('getValue');
        var v_fzr = $("#txt_fzr").textbox('getValue');
        var v_dd = $("#txt_dd").textbox('getValue');
        var v_bz = $("#txt_bz").textbox('getValue');
        $.ajax({
            url: "${ctx}/gdzc/cfsave",
            type: "post",
            data: {C1: v_id, C2: v_name, C3: v_sydw, C4: v_zcyz,C5:v_fzr,C6:v_dd,C7:v_bz,C8:v_zcbm,C10:v_erpbm,C11:v_flbm},
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
                $('#sub'+v_zcbm).datagrid("reload");
            }
        });
        $('#win').window('close');
    }
    function doCx2() {
        dgOptions.pageNumber = 1;
        dgOptions.queryParams = getInitParam();
        $('#datatable1').datagrid(dgOptions);
    }
    var dgOptions = {
        rownumbers: true,
        border: true,
        pageSize: 50,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        singleSelect: true,
        url: '${ctx}/gdzc/gdzcList',
        frozenColumns: [[
            {field: 'ZCBM', title: '资产编码(系统内)', width: 100, align: 'center'}
            , {field: 'ERPBM', title: 'ERP编码', width: 100, align: 'center', editor: "text"}
            , {field: 'ZCMC', title: '资产名称', width: 170, editor: "text", align: 'center', sortable: true}
        ]],
        columns: [[
            {field: 'ID', hidden: true}
            , {field: 'DL1', title: '大类1', width: 100, align: 'center', sortable: true}
            , {field: 'DL2', title: '大类2', width: 150, align: 'center', sortable: true}
            , {field: 'LB', title: '类别', width: 100, align: 'center', sortable: true}
            , {field: 'FLBM', title: '分类编码', width: 80, editor: "text", align: 'center'}
            , {field: 'SL', title: '数量', width: 40, editor: "text", align: 'center'}
            , {field: 'ZZPH', title: '执照牌号', width: 100, editor: "text", align: 'center'}
            , {field: 'SYDWMC', title: '使用单位', width: 140, align: 'center'}
            , {field: 'ZGBMMC', title: '主管部门', width: 100, align: 'center'}
            , {field: 'FZRMC', title: '负责人', width: 50, editor: "text", align: 'center'}
            , {field: 'GGXH', title: '规格型号', width: 100, editor: "text", align: 'center'}
            , {field: 'ZBHRQ', title: '资本化日期', width: 80, editor: "text", align: 'center'}
            , {field: 'YSYNX', title: '已使用年限', width: 70, editor: "text", align: 'center'}
            , {field: 'ZCYZ', title: '资产原值', width: 80, editor: "text", align: 'center'}
            , {field: 'ZJNX', title: '折旧年限', width: 60, editor: "text", align: 'center'}
            , {field: 'YXZT', title: '运行状态', width: 100, align: 'center'}
            , {field: 'DD', title: '地点', width: 80, editor: "text", align: 'center'}
            , {field: 'ZT', title: '清查情况', width: 80, align: 'center'}
            , {field: 'BZ', title: '备注', width: 180, editor: "text", align: 'center'}
        ]],
        view: detailview,
        detailFormatter: function (rowIndex, rowData) {
            return '<table id="sub' + rowData.ZCBM + '" class="subCategory" ' +
                    'style="float:left;"></table>';
        },
        onExpandRow: function (index, row) {
            var subCategory = $(this).datagrid('getRowDetail', index).find('table.subCategory');//找到该父类Formatter的子类表格
            var subTl = $(this).datagrid('getRowDetail', index).find('div.subTools');
            subCategory.datagrid({
                url: '${ctx}/gdzc/subList?C1=' + row.ZCBM,
                //fitColumns:true,
                singleSelect: true,
                border: true,
                rownumbers: true,
                loadMsg: '加载中，请稍后...',
                height: 'auto',
                //height:'310px',
                toolbar: [
                    {
                        text: '增加', iconCls: 'icon-add', handler: function () {
                        toadd(row);
                    }
                    },
                    {
                        text: '修改', iconCls: 'icon-edit', handler: function () {
                        doEdit(row);
                    }
                    }, {
                        text: '删除', iconCls: 'icon-remove', handler: function () {
                            doDelete(row);
                        }
                    }],
                columns: [[
                    {field: 'ID', hidden: true},
                    {field: 'SYDWMC', title: '使用单位', width: 150, align: 'left'},
                    {field: 'MC', title: '分部名称', width: 100},
                    {field: 'FZR', title: '负责人', width: 50},
                    {field: 'ZCYZ', title: '资产原值', width: 55, align: 'left'},
                    {field: 'DD', title: '地点', width: 150, align: 'left'},
                    {field: 'BZ', title: '备注', width: 150, align: 'left'},
                    {field: 'TJR', title: '添加人', width: 150, align: 'left'},
                    /*{field: 'SUBMITDATE', title: '添加时间', width: 150, align: 'left'},*/
                ]],
                onResize: function () {
                    $('#datatable1').datagrid('fixDetailRowHeight', index);
                },
                onLoadSuccess: function () {
                    $('#datatable1').datagrid('fixDetailRowHeight', index);
                },
                rowStyler: function(index,row){
                 return 'color:#3300ff;font-weight:bold;';
                 }
            });
            $('#datatable1').datagrid('fixDetailRowHeight', index);
        }
    }
    $(function () {
        $('#datatable1').height(($(window).height() - 120) + 'px');
        $('#comb_sydw').combotree({data: ${dwTree}});
        $('#datatable1').datagrid(dgOptions);
    });

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
            param[$(item).attr("name")] = $(item).val();
            if ($(item).val()) {
                param[$(item).attr("name")] = $(item).val();
            }
        });
        return param;
    }
</script>
</body>
</html>