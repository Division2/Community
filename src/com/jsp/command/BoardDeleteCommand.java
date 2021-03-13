package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.ex.model.BoardDAO;

public class BoardDeleteCommand implements BoardCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//게시글 삭제 커맨드
		System.out.println("- 게시글 삭제 -");
		
		BoardDAO dao = BoardDAO.getInstance();
		
		HttpSession session = request.getSession();
		String checkAuth = session.getAttribute("Account").toString();
		
		int postId = Integer.parseInt(request.getParameter("id"));
		
		int checkPostAuth = dao.checkAuth(postId, checkAuth);
		
		//게시글의 ID의 작성자 ID가 세션의 ID와 일치하는지 체크
		if (checkPostAuth != 1) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 작성자만 삭제할 수 있습니다.');");
			out.println("window.location.href='board.do'");
			out.println("</script>");
			out.close();
		}
		else {
			//일치하면 게시글 삭제 진행
			int result = dao.deletePost(postId);
			if (result != 1) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('게시글이 삭제되지 않았습니다!\n다시 시도해주세요.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			else {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('게시글이 삭제되었습니다.')");
				out.println("window.location.href='board.do'");
				out.println("</script>");
				out.close();
			}
		}
	}
}