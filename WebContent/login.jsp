<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	if (session.getAttribute("Account") != null) {
%>
		<jsp:forward page="main.do"/>
<%
	}
%>
<!DOCTYPE html>
<html>
<head>
<link href="css/section.css" rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Community</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<br>
	<div class="container">
		<form class="form-signin" action="loginChecked.do" method="POST">
			<h2 style="text-align:center;" class="form-signin-heading">커뮤니티 로그인</h2>
			<label for="inputAccount" class="sr-only">Account</label>
			<input type="text" id="inputAccount" name="account" class="form-control"placeholder="아이디를 입력하세요" required autofocus>
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="inputPassword" name="password" class="form-control"placeholder="비밀번호를 입력하세요" required><br>
			<button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
		</form>
	</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>