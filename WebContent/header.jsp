<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<header>
	<nav class="navbar navbar-icon-top navbar-expand-lg navbar-dark bg-dark">
		<a class="navbar-brand" href="main.do">Community</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="main.do">Home</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="board.do">전체게시판</a>
				</li>
			</ul>
			<!-- 세션이 null이면 로그인 회원가입 출력, null이 아니면 회원 ID와 로그아웃을 출력(IF_ELSE) -->
			<c:choose>
				<c:when test="${sessionScope.Account eq null}">
					<button class="btn btn-primary" type="submit" onclick="location.href='login.do'">로그인</button>&nbsp;
					<button class="btn btn-primary" type="submit" onclick="location.href='register.do'">회원가입</button>
				</c:when>
				<c:when test="${sessionScope.Account ne null }">
					<form action="logout.do" method="POST">
						<span style="color:white;"><%=session.getAttribute("Account")%>님 환영합니다.</span>&nbsp;&nbsp;
						<button class="btn btn-primary" type="submit">로그아웃</button>
					</form>
				</c:when>
			</c:choose>
		</div>
	</nav>
</header>