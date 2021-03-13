package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.ex.model.ReplyDAO;

public class ReplyModifyCommand implements ReplyCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//댓글 수정 커맨드
		System.out.println("- 댓글 수정 -");
		
		ReplyDAO dao = ReplyDAO.getInstance();
		
		HttpSession session = request.getSession();
		String checkAuth = session.getAttribute("Account").toString();
		
		int rId = Integer.parseInt(request.getParameter("rId"));
		int bId = Integer.parseInt(request.getParameter("bId"));
		String content = request.getParameter("content");
		
		//XSS 방지
		content = content.replaceAll("\r\n", "<br>");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		
		int checkPostAuth = dao.checkAuth(rId, bId, checkAuth);
		if (checkPostAuth != 1) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('댓글 작성자만 수정할 수 있습니다.');");
			out.println("window.location.href='contentView.do?id=" + bId + "';");
			out.println("</script>");
			out.close();
		}
		else {
			int result = dao.modifyReply(rId, content);
			if (result != 1) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('댓글이 수정되지 않았습니다!\n다시 시도해주세요.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			else {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('댓글이 수정되었습니다.')");
				out.println("window.location.href='contentView.do?id=" + bId + "';");
				out.println("</script>");
				out.close();
			}
		}
	}
}