<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link rel="stylesheet" href="${ctxStatic}/viewer/viewer.min.css">
    <link rel="stylesheet" href="${ctxStatic}/viewer/css/main.css">
    <script src="${ctxStatic}/webuploader/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/viewer/viewer.min.js" type="text/javascript"></script>
</head>
<style>
    table th {
        text-align: right;
    }
</style>
<%
    List<HashMap> mdList=(List<HashMap>)request.getAttribute("mdList");
%>
<body>
<sys:message content="${message}"/>
<div class="docs-galley">
    <ul id="dowebok" class="docs-pictures clearfix">
        <%
            if(mdList.size()>0)
                for(HashMap m:mdList){

        %>
        <li>
            <input type="button" onclick="document.location='${ctx}/gdzc/delImg?c1=${condition.c1}&c2=<%=m.get("ID")%>';" value="删除">
            <%=m.get("FJMC") %>
            <img src="${pageContext.request.contextPath}/userfiles<%=m.get("PATH") %>" alt="<%=m.get("FJMC") %>">
        </li>
        <%
                }
        %>
    </ul>
</div>
<script type="text/javascript">
    var viewer = $('#dowebok').viewer();
</script>
</body>
</html>