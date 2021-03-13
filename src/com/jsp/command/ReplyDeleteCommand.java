package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.ex.model.ReplyDAO;

public class ReplyDeleteCommand implements ReplyCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("- 댓글 삭제 -");
		
		ReplyDAO dao = ReplyDAO.getInstance();
		
		HttpSession session = request.getSession();
		String checkAuth = session.getAttribute("Account").toString();
		
		int rId = Integer.parseInt(request.getParameter("rId"));
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		int checkPostAuth = dao.checkAuth(rId, bId, checkAuth);
		if (checkPostAuth != 1) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('댓글 작성자만 삭제할 수 있습니다.');");
			out.println("window.location.href='contentView.do?id=" + bId + "';");
			out.println("</script>");
			out.close();
		}
		else {
			//일치하면 게시글 삭제 진행
			int result = dao.deleteReply(rId, bId);
			if (result != 1) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('댓글이 삭제되지 않았습니다!\n다시 시도해주세요.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			else {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('댓글이 삭제되었습니다.')");
				out.println("window.location.href='contentView.do?id=" + bId + "';");
				out.println("</script>");
				out.close();
			}
		}
	}
}