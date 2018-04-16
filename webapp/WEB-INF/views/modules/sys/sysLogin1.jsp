<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>简洁时尚的CSS3用户登录界面设计|DEMO_jQuery之家-自由分享jQuery、html5、css3的插件库</title>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/default.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/login/css/styles.css">
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
	<!--[if IE]>
	<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]-->
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#loginForm").validate({
			rules: {
				validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
			},
			messages: {
				username: {required: "请填写用户名."},password: {required: "请填写密码."},
				validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
			},
			errorLabelContainer: "#messageBox",
			errorPlacement: function(error, element) {
				error.appendTo($("#loginError").parent());
			}
		});
	});
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
		alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
</script>
<body>
<div class="htmleaf-container">
	<header class="htmleaf-header">
		<h1>${fns:getConfig('productName')}</h1>
	</header>
	<div class="wrapper">
		<div class="container">
			<h1>用户登录</h1>
			<form id="loginForm" class="form" action="${ctx}/login" method="post">
				<input type="text" id="username" name="username" placeholder="Username" value="${username}">
				<input type="password" placeholder="Password" id="password" name="password">
				<button type="submit" id="login-button">Login</button>
			</form>
		</div>

		<ul class="bg-bubbles">
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>

		</ul>
	</div>

</div>


<script>
	$('#login-button').click(function (event) {
		event.preventDefault();
		$('form').fadeOut(500);
		$('.wrapper').addClass('form-success');
		$('form').submit();
	});
</script>

</body>
</html>