<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	if (session.getAttribute("Account") == null) {
%>
	<script type="text/javascript">
		alert('회원만 열람할 수 있습니다.');
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
	<script type="text/javascript">
	<%
		int id = 0;
		if(request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
		}
	%>
		//게시글 수정
		function modifyView() {
			window.location.href='modifyView.do?id=<%=id%>';
		}
		//게시글 삭제
		function deletePost() {
			if (confirm('정말 삭제하시겠습니까?')) {
				window.location.href='deletePost.do?id=<%=id%>';
			}
			else {
				return false;
			}
		}
		
		//대댓글 작성
		function addReply(bId, rId, content) {
			window.location.href="addReply.do?bId=" + bId + "&rId=" + rId + "&content=" + content + "&writer=" + "<%=session.getAttribute("Account")%>";
		}
		//댓글 수정
		function modifyReply(bId, rId, content) {
			if (confirm('정말 수정하시겠습니까?')) {
				window.location.href="modifyReply.do?bId=" + bId + "&rId=" + rId + "&content=" + content;
			}
			else {
				return false;
			}
		}
		//댓글 삭제
		function deleteReply(rId, bId) {
			if (confirm('정말 삭제하시겠습니까?')) {
				window.location.href="deleteReply.do?rId=" + rId + "&bId=" + bId;
			}
			else {
				return false;
			}
		}
		
	    $(document).ready(function() {
	        $("#addReply").on("show.bs.modal", function(e) {
	        	
				//변수에 대입
				var postid = $(e.relatedTarget).data('postid');
				var replyid = $(e.relatedTarget).data('replyid');
				
				//작성 버튼 클릭 시
	        	$('#btnAddReply').click(function() {
	        		//사용자가 입력한 내용을 변수로 담음
	        		var content = $("input#replyAdd").val();
	        		
	        		//자바스크립트 함수 호출(대댓글 작성)
	        		addReply(postid, replyid, content);
	        	});
	        });
	    	
	        $("#modifyReply").on("show.bs.modal", function(e) {
	        	
				//변수에 대입
				var postid = $(e.relatedTarget).data('postid');
				var replyid = $(e.relatedTarget).data('replyid');
	        	var content = $(e.relatedTarget).data('content');
	        	
				//input의 id가 replyModify인 곳에 해당 값을 지정
	            $('input#replyModify').val(content);
				
				//수정 버튼 클릭 시
	        	$('#btnModifyReply').click(function() {
	        		//사용자가 입력한 내용을 변수로 담음
	        		var modifyContent = $("input#replyModify").val();
	        		
	        		//자바스크립트 함수 호출(댓글 수정)
	        		modifyReply(postid, replyid, modifyContent);
	        	});
	        });
	    });
	</script>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<br>
	<article>
		<div class="container" role="main">
			<c:forEach items="${content }" var="dto">
				<div class="container">
					<c:choose>
						<c:when test="${dto.category eq 'notice' }">
							<h2>공지사항</h2>
						</c:when>
						<c:when test="${dto.category eq 'bulletin' }">
							<h2>자유게시판</h2>
						</c:when>
					</c:choose>
					<div class="card">
						<div class="card-body">
							<h4 class="card-title">${dto.title }</h4>
							<p class="card-text"><small class="text-muted">${dto.name} | ${dto.date } | 조회수 ${dto.hit }</small></p>
							<hr>
							<p class="card-text"><c:out escapeXml="false" value="${dto.content }"/></p>
						</div>
					</div>
					<div class="row mt-3">
						<div class="col-auto mr-auto"></div>
						<div class="col-auto">
							<form name="postUpdate" method="POST">
							<!-- 세션의 ID와 게시글 작성자가 같을 경우에만 수정, 삭제 권한을 줌 -->
							<c:if test="${sessionScope.Account eq dto.name }">
								<button class="btn btn-primary" type="button" onclick="modifyView()">수정</button>
								<button class="btn btn-primary" type="button" onclick="deletePost()">삭제</button>
							</c:if>
								<button class="btn btn-primary" type="button" onclick="window.location.href='board.do'">목록</button>
							</form>
						</div>
					</div>
				</div>
			</c:forEach>
			<div class="my-3 p-3 bg-white rounded shadow-sm" style="padding-top: 10px">
				<h6 class="border-bottom pb-2 mb-0">댓글 목록</h6>
				<div id="replyList"></div>
				<c:if test="${reply.isEmpty() }">
					<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">등록된 댓글이 없습니다.</p>
				</c:if>
				<c:forEach items="${reply }" var="dto">
					<div class="media text-muted pt-3">
						<svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder:32x32">
							<title>꾸엑</title>
							<rect width="100%" height="100%" fill="#007bff"></rect>
							<text x="50%" fill="#007bff" dy=".3em">32x32</text>
						</svg>
						<form name="replyUpdate" method="POST">
							<p class="media-body pb-3 mb-0 small lh-125 border-bottom horder-gray">
								<span class="d-block">
									<strong class="text-gray-dark">${dto.name }</strong>
									<span style="padding-left: 7px; font-size: 9pt">
										<c:out value="${dto.date }"/>
										<!-- 대댓글, 댓글 수정은 ajax말고는 modal로 가능하다고 한당.. 나중에 천천히 하는걸로 하자. -->
											<a href="#" data-toggle="modal" data-target="#addReply" data-postid="${dto.bId }" data-replyid="${dto.rId }">답글</a>
										<!-- 세션의 ID와 댓글 작성자가 같을 경우에만 수정, 삭제 권한을 줌 -->
										<c:if test="${sessionScope.Account eq dto.name }">
											<a href="#" data-toggle="modal" data-target="#modifyReply" data-postid="${dto.bId }" data-replyid="${dto.rId }" data-content="${dto.content }">수정</a>
											<a href="javascript:deleteReply(${dto.rId }, ${dto.bId });">삭제</a>
										</c:if>
									</span>
								</span>
								<c:out escapeXml="false" value="${dto.content }"/>
							</p>
						</form>
					</div>
				</c:forEach>
			</div>
			<form name="writeReply" action="writeReply.do" method="POST">
				<input type="hidden" name="bId" id="bId" value="<%=id %>">
				<input type="hidden" name="writer" id="writer" value="${sessionScope.Account }">
				<div class="my-3 p-3 bg-white rounded shadow-sm" style="padding-top: 10px">
					<textarea name="content" id="content" class="form-control" rows="3" placeholder="댓글을 입력해 주세요" required></textarea>
					<button type="submit" class="btn btn-primary" id="btnReplySave" style="width: 100%; margin-top: 0px">등 록</button>
				</div>
			</form>
		</div>
	</article>
	<jsp:include page="footer.jsp"/>
	
	<!-- 대댓글 Modal -->
	<div class="modal fade" id="addReply">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">대댓글 작성</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<label for="reply_text">댓글 내용</label>
					<input type="text" class="form-control" id="replyAdd" name="replyAdd" placeholder="댓글을 입력해주세요.">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="btnAddReply">작성</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 댓글 수정 Modal -->
	<div class="modal fade" id="modifyReply">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">댓글 수정</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<label for="reply_text">댓글 내용</label>
					<input type="text" class="form-control" id="replyModify" name="replyModify">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="btnModifyReply">수정</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>