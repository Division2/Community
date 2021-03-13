<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("Account") == null) {
%>
	<script type="text/javascript">
		alert('회원만 작성할 수 있습니다.');
	//	history.back();
		window.location.href = "login.do";
	</script>
	<!--
	위 방법처럼 목록으로 돌아가게 하는 방법이 있고
	history.back(); 대신 forward로 login창 보낼 수도 있당 -->

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
	<article>
		<div class="container" role="main">
			<h2>게시글 작성</h2>
			<form name="writePost" action="writePost.do" method="POST">
				<div class="mb-3">
					<select name="category" id="category">
						<option value="notice">공지사항</option>
						<option value="bulletin">자유게시판</option>
					</select>
				</div>
				<div class="mb-3">
					<label for="writer">작성자</label>
					<input type="text" class="form-control" name="writer" id="writer" value="<%=session.getAttribute("Account")%>" readonly>
				</div>
				<div class="mb-3">
					<label for="title">제목</label>
					<input type="text" class="form-control" name="title" id="title" placeholder="제목을 입력해 주세요" required>
				</div>
				<div class="mb-3">
					<label for="content">내용</label>
					<textarea class="form-control" rows="10" name="content" id="content" placeholder="내용을 입력해 주세요" required></textarea>
				</div>
				<div>
					<button class="btn btn-sm btn-primary" type="submit">등록</button>
					<button class="btn btn-sm btn-primary" type="button" onclick="window.location.href='board.do'">목록</button>
				</div>
			</form>
		</div>
	</article>
	<jsp:include page="footer.jsp"/>
</body>
</html>