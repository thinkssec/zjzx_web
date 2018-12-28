<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <script src="${ctxStatic}/webuploader/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/webuploader/webuploader2.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/webuploader/webuploader2.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/webuploader/demo.css">
</head>
<style>
    table th {
        text-align: right;
    }
</style>
<script>
    var C1='${condition.c1}';
    var ctx='${ctx}';
</script>
<body>
<sys:message content="${message}"/>
<div id="uploader" class="wu-example">
    <div class="queueList">
        <div id="dndArea" class="placeholder">
            <div id="filePicker"></div>
        </div>
    </div>
    <div class="statusBar" style="display: none;">
        <div class="progress">
            <span class="text">0%</span>
            <span class="percentage"></span>
        </div>
        <div class="info"></div>
        <div class="btns">
            <div id="filePicker2"></div>
            <div class="uploadBtn">开始上传</div>
        </div>
    </div>
</div>
<script type="text/javascript">

</script>
<script src="${ctxStatic}/webuploader/demo.js" type="text/javascript"></script>
</body>
</html>