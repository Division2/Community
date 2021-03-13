<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	//DB에서 불러올 때 123<br>123<br> 등으로 표시되는걸 치환
	pageContext.setAttribute("crlf", "\r\n");
	
	int id = Integer.parseInt(request.getParameter("id"));

	if(session.getAttribute("Account") != null) {
		String account = session.getAttribute("Account").toString();
	}
	else {
%>
	<script>
		alert('수정 권한이 없습니다.');
		window.location.href='board.do';
	</script>
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
			<h2>게시글 수정</h2>
			<form name="modifyPost" action="modifyPost.do?id=<%=id %>" method="POST">
				<div class="mb-3">
					<select name="category" id="category">
						<option value="notice">공지사항</option>
						<option value="bulletin">자유게시판</option>
					</select>
				</div>
				<c:forEach items="${dto }" var="dto">
					<!-- URI 게시글번호 수정해서 들어올 때 처리(스크립틀릿에서 같이 처리하고싶은데 dto를 어케 받아와야할지.. -->
					<c:if test="${sessionScope.Account ne dto.name }">
						<script>
							alert('글 작성자만 접근 가능합니다.');
							window.location.href='board.do';
						</script>
					</c:if>
					<div class="mb-3">
						<label for="writer">작성자</label>
						<input type="text" class="form-control" name="writer" id="writer" value="${dto.name }" readonly>
					</div>
					<div class="mb-3">
						<label for="title">제목</label>
						<input type="text" class="form-control" name="title" id="title" value="${dto.title }" required>
					</div>
					<div class="mb-3">
						<label for="content">내용</label>
						
						<textarea class="form-control" rows="10" name="content" id="content" required><c:out escapeXml="false" value="${fn:replace(dto.content, '<br>', crlf)}"/></textarea>
					</div>
				</c:forEach>
				<div>
					<button class="btn btn-sm btn-primary" type="submit">수정</button>
					<button class="btn btn-sm btn-primary" type="button" onclick="window.location.href='contentView.do?id=<%=id %>'">이전</button>
					<button class="btn btn-sm btn-primary" type="button" onclick="window.location.href='board.do'">목록</button>
				</div>
			</form>
		</div>
	</article>
	<jsp:include page="footer.jsp"/>
</body>
</html>