<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>资产维修计划填报</title>
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
    <li class="active"><a href=""><span class="gicon-cargo">&nbsp;资产维修计划填报</span></a></li>
    <%--<shiro:hasPermission name="cbgl:rgcblist2"><li><a href="${ctx}/cbgl/rgcblist">人工成本发布2</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" action="${ctx}/gdzc/list" modelAttribute="condition" method="post"
           class="breadcrumb form-search">
    <div style="margin-top:8px;">
        <form:select path="c1">
            <form:options items="${ny}" itemLabel="label" itemValue="value" htmlEscape="false"/>
        </form:select>

        <a class="btn btn-primary" href="#" onclick="doCx2();"><i class="icon-play"></i> 查询</a>
        <a class="btn btn-info" href="#" onclick="addRow();"><i class="icon-plus"></i> 新增</a>
        <a class="btn btn-success" href="#" onclick="saveRow();"><i class="icon-pencil"></i> 保存</a>
        <a class="btn btn-danger" href="#" onclick="deteleRow();"><i class="icon-remove-sign"></i> 删除</a>
        <a class="btn btn-warning" href="#" onclick="doTj();"><i class="icon-check"></i> 提交</a>
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
        <input id="txt_zcyz" name="txt_zcyz" class="easyui-textbox" style="width: 95%"
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
<div id="win2" class="easyui-window" title="添加/编辑" style="width: 900px; height: 500px;padding: 10px;"
     data-options="iconCls:'icon-add',modal:true,border:true,closed:true,footer:'#footer2',
        onResize:function(){
				$(this).window('hcenter');
		}">
    <form:form id="searchForm2" action="${ctx}/gdzc/list" modelAttribute="condition" method="post"
               class="breadcrumb form-search">
        <div style="margin-top:8px;">
            <input name="c4" value="${condition.c4}" type="text" placeholder="关键字…">
            <form:select path="c2" class="input-xlarge" style="width:160px">
                <form:option value="" label="请选择"/>
                <form:options items="${dwList}"  itemLabel="label" itemValue="value"  htmlEscape="false"/>
            </form:select>
            <a class="btn btn-primary" href="#" onclick="doCx3();"><i class="icon-play"></i> 查询</a>
        </div>
    </form:form>
    <table id="datatable2"></table>
    <input type="hidden" id="hid_wxdh" value="">
    <div id="footer2" style="padding: 5px;">
        <a class="btn btn-info" href="#" onclick="doAddtoWxd();"><i class="icon-save"></i> 添加到维修单</a>
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
        $("#txt_zcyz").textbox('setValue', '');
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
        $("#txt_zcyz").textbox('setValue', ttt[0].ZCYZ);
        $("#txt_fzr").textbox('setValue', ttt[0].FZR);
        $("#txt_dd").textbox('setValue', ttt[0].DD);
        $("#txt_bz").textbox('setValue', ttt[0].BZ);
        $('#hid_id').val(ttt[0].ID);
        $('#footer').show();
        $('#win').window({title: '编辑分部'});
        $('#win').window('open');
    }
    function doDelete(row) {
        var ttt = $("#sub"+row.WXDH).datagrid("getSelections");
        if (ttt && ttt.length > 0) {
        } else return;
        var v_id = ttt[0].ID;
        $.ajax({
            url: "${ctx}/gdzc/wxmxdel",
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
                $('#sub'+row.WXDH).datagrid("reload");
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
        var v_zcyz = $("#txt_zcyz").textbox('getValue');
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
    function doTj(){
        if(!confirm('提交本月资产维修计划？')) return;
        $.ajax({
            url: "${ctx}/gdzc/tjWxd",
            type: "post",
            data:getInitParam(),
            success: function (result) {
                if (result != "") {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '操作成功' : '操作失败',
                        showType: 'show'
                    });
                    //$.messager.alert("操作提示", result=='1'?'保存成功':'保存失败');
                    saveFlag = false;
                }
                else {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '操作成功' : '操作失败',
                        showType: 'show'
                    });
                    //$.messager.alert("操作提示", "保存失败！");
                    saveFlag = false;
                }
                doCx2();
            }
        });
    }
    function toadd2(row) {
        $('#hid_wxdh').val(row.WXDH);
        $('#footer2').show();
        doCx3();
        $('#win2').window({title: '选择资产'});
        $('#win2').window('open');
    }
    function doCx2() {
        dgOptions.pageNumber = 1;
        dgOptions.queryParams = getInitParam();
        $('#datatable1').datagrid(dgOptions);
    }

    function doCx3() {
        dgOptions2.queryParams = getInitParam2();
        $('#datatable2').datagrid(dgOptions2);
    }
    var dgOptions = {
        rownumbers: true,
        border: true,
        pageSize: 50,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        singleSelect: true,
        url: '${ctx}/gdzc/wxdList',
        columns: [[
            {field: 'WXDH', title: '维修单号', width: 100, align: 'center', sortable: true}
            , {field: 'BXR', title: '报修人', width: 150, align: 'center', sortable: true,
                editor: {
                    type: 'textbox',
                    editable: "true",
                }
            }
            , {field: 'FY', title: '维修费用', width: 100, align: 'center', sortable: true,
                editor: {
                    type: 'numberbox',
                    editable: "true",
                }
            }
            , {field: 'WXNR', title: '维修内容', width: 180, editor: "text", align: 'center',
                editor: {
                    type: 'textbox',
                    editable: "true",
                }
            }
            , {field: 'BZ', title: '备注', width: 140, editor: "text", align: 'center',
                editor: {
                    type: 'textbox',
                    editable: "true",
                }
            }
            , {field: 'NY', title: '提报年月', width: 100, align: 'center'}
            , {field: 'ZT', title: '审核状态', width: 100,  align: 'center',
                formatter: function(value,row,index){
                    if (row.STATUS=='0'){
                        return "已提交审核";
                    } else if(row.STATUS=='1'){
                        return "审核通过";
                    }else if(row.STATUS=='2'){
                        return "审核不通过";
                    }else{
                        return "临时保存";
                    }
                }

            }
            , {field: 'TJR', title: '提交人', width: 140, align: 'center'}
            , {field: 'TJSJ', title: '提交时间', width: 100, align: 'center'}
            , {field: 'DEPTMC', title: '提交单位', width: 100,  align: 'center'}
        ]],
        view: detailview,
        detailFormatter: function (rowIndex, rowData) {
            return '<table id="sub' + rowData.WXDH + '" class="subCategory" ' +
                    'style="float:left;"></table>';
        },
        onExpandRow: function (index, row) {
            var subCategory = $(this).datagrid('getRowDetail', index).find('table.subCategory');//找到该父类Formatter的子类表格
            var subTl = $(this).datagrid('getRowDetail', index).find('div.subTools');
            subCategory.datagrid({
                url: '${ctx}/gdzc/wxmxList?C1=' + row.WXDH,
                //fitColumns:true,
                singleSelect: true,
                border: true,
                rownumbers: true,
                loadMsg: '加载中，请稍后...',
                height: 'auto',
                //height:'310px',
                toolbar: [
                    {
                        text: '添加维修资产', iconCls: 'icon-add', handler: function () {
                        toadd2(row);
                    }
                    },{
                        text: '删除维修资产', iconCls: 'icon-remove', handler: function () {
                            doDelete(row);
                        }
                    }],
                columns: [[
                    {field: 'ZCBM', title: '资产编码', width: 130, align: 'left'},
                    {field: 'ERPBM', title: 'ERP编码', width: 100},
                    {field: 'ZCMC', title: '资产名称', width: 120},
                    {field: 'LB', title: '类别', width: 120},
                    {field: 'FLBM', title: '分类编码', width: 100},
                    {field: 'FZR', title: '负责人', width: 80},
                    {field: 'ZCYZ', title: '资产原值', width: 55, align: 'left'},
                    {field: 'BZ', title: '备注', width: 150}
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
        },
        onClickRow: function (index, row) {
            if (editingIndex != null && editingIndex != index) {
                endEditRow();
            }
        },
        onDblClickCell: function (index, field, value) {
            editRow(index);
        }
    }
    var dgOptions2 = {
        rownumbers: true,
        border: true,
        pageSize: 50,
        height:340,
        pagination: true,
        multiSort: true,
        queryParams: getInitParam(),
        singleSelect: false,
        url: '${ctx}/gdzc/selectZc',
        frozenColumns: [[
            {field: 'ZCBM', title: '资产编码(系统内)', width: 125, align: 'center'}
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
        ]],rowStyler: function(index,row){
            if (row.FLG =='1'){
                return 'color:#3300ff;font-weight:bold;';
            }
        }
    }
    $(function () {
        $('#datatable1').height(($(window).height() - 120) + 'px');
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
    function getInitParam2() {
        var param = {};
        param = {
            "limit": function () {
                return $("#datatable2").datagrid("getPager").pagination("options").pageSize;
            },
            "start": function () {
                return $("#datatable2").datagrid("getPager").pagination("options").pageNumber;
            }
        };
        $("#searchForm2 :input[name]").each(function (i, item) {
            param[$(item).attr("name")] = $(item).val();
            if ($(item).val()) {
                param[$(item).attr("name")] = $(item).val();
            }
        });
        return param;
    }
    function addRow(){
        var ny=$("#searchForm :input[name=c1]").val();
        $('#datatable1').datagrid('insertRow',{index:0,row:{
            WXDH: '',
            BXR: '',
            FY: '',
            WXNR: '',
            BZ:'',
            ZT:'',
            TJR:'',
            TJSJ:'',
            NY:ny
        }});
        $('#datatable1').datagrid('acceptChanges');
        editRow(0);
    }
    function saveRow(){
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
        var updateStr = JSON.stringify($('#datatable1').datagrid("getChanges"));
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
            url: "${ctx}/gdzc/saveWxd",
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
                if(result=="1"){
                    $('#datatable1').datagrid("reload")
                }
            }
        });
    }
    function deteleRow(){
        var ttt=$('#datatable1').datagrid('getSelections');
        if(ttt&&ttt.length>0){
        }else return;
        if(!confirm('确实删除当前维修单？')) return;
        saveFlag = true;
        $.ajax({
            url: "${ctx}/gdzc/deleteWxd",
            type: "post",
            data: {C6:ttt[0].WXDH},
            success: function (result) {
                if (result != "") {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '删除成功' : '删除失败',
                        showType: 'show'
                    });
                    saveFlag = false;
                }
                else {
                    $.messager.show({
                        title: '操作提示',
                        msg: result == '1' ? '删除成功' : '删除失败',
                        showType: 'show'
                    });
                    //$.messager.alert("操作提示", "保存失败！");
                    saveFlag = false;
                }
                if(result=="1"){
                    $('#datatable1').datagrid("reload")
                }
            }
        });
    }
    function doAddtoWxd(){
        var ttt=$('#datatable2').datagrid('getSelections');
        if(ttt&&ttt.length>0){
        }else return;
        var updateStr = JSON.stringify(ttt);
        saveFlag = true;
        var v_wxdh=$('#hid_wxdh').val();
        $.ajax({
            url: "${ctx}/gdzc/saveWxmx",
            type: "post",
            data: {C6:updateStr,C7:v_wxdh},
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
                if(result=="1"){
                    $('#sub'+v_wxdh).datagrid("reload");
                    //$('#datatable1').datagrid("reload")
                }
            }
        });
    }
    function endEditRow() {
        if (editingIndex != null) {
            if ($('#datatable1').datagrid('validateRow', editingIndex)) {
                $('#datatable1').datagrid("endEdit", editingIndex);
                editingIndex = null;
            }
            else {
                //$.messager.alert("操作提示", "第" + (editingIndex + 1) + "行数据校验未通过！");
                return false;
            }
        }
        return true;
    }
    function editRow(index) {
        if (!endEditRow()) return;
        if (index != null) {
            $('#datatable1').datagrid("beginEdit", index);
            editingIndex = index;
        }
    }
</script>
</body>
</html>