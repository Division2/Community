<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link href="css/section.css" rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Community</title>

<style>
	td, th {
		text-align:center;
	}
</style>
<script>
function PageMove(page){
    location.href = "board.do?page=" + page;
  }
</script>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<br>
	<table width='100%' cellpadding='0' cellspacing='0' class='table'>
		<tr>
			<thead>
				<th>번호</th>
				<th>카테고리</th>
				<th width='50%'>제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>작성일</th>
			</thead>
		</tr>
		<!-- 카테고리 구분 -->
		<c:forEach items="${board }" var="dto">
		<tr>
			<td>${dto.id }</td>
			<c:choose>
				<c:when test="${dto.category eq 'notice' }">
					<td>공지사항</td>
				</c:when>
				<c:when test="${dto.category eq 'bulletin' }">
					<td>자유게시판</td>
				</c:when>
			</c:choose>
			<td>
				<c:forEach begin="1" end="${dto.indent }">-</c:forEach>
				<a href="contentView.do?id=${dto.id }">${dto.title }<c:if test="${dto.replyCount ne 0 }">(${dto.replyCount })</c:if></a>
			</td>
			<td>${dto.name }</td>
			<td>${dto.hit }</td>
			<td>${dto.date }</td>
		</tr>
		</c:forEach>
		<tr>
		<c:if test="${sessionScope.Account ne null}">
			<td style="text-align:right" colspan="6">
				<button class="btn btn-primary" type="submit" onclick="location.href='writeView.do'">글쓰기</button>
			</td>
		</c:if>
		</tr>
	</table>
	<!-- 게시글 페이징 처리(기준 10개) -->
	<nav aria-label="Page navigation">
		<ul class="pagination justify-content-center">
			<!-- 첫 페이지면 Disabled 아니라면 Enabled -->
			<c:choose>
				<c:when test="${paging.pageNo eq paging.firstPageNo }">
					<li class="page-item disabled">
						<a class="page-link" href="javascript:PageMove(${paging.prevPageNo})">Previus</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item">
						<a class="page-link" href="javascript:PageMove(${paging.prevPageNo})">Previus</a>
					</li>
				</c:otherwise>
			</c:choose>
			<!-- 페이지 갯수만큼 버튼 생성 -->
			<c:forEach var="i" begin="${paging.startPageNo }" end="${paging.endPageNo }" step="1">
				<c:choose>
					<c:when test="${i eq paging.pageNo }">
						<li class="page-item disabled">
							<a class="page-link" href="javascript:PageMove(${i})"><c:out value="${i }"/></a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="page-item">
							<a class="page-link" href="javascript:PageMove(${i})"><c:out value="${i }"/></a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!-- 마지막 페이지면 Disabled 아니라면 Enabled -->
			<c:choose>
				<c:when test="${paging.pageNo eq paging.finalPageNo }">
					<li class="page-item disabled">
						<a class="page-link" href="javascript:PageMove(${paging.nextPageNo})">Next</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item">
						<a class="page-link" href="javascript:PageMove(${paging.nextPageNo})">Next</a>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
	<jsp:include page="footer.jsp"/>
</body>
</html>