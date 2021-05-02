package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.ex.model.ReplyDAO;

public class ReplyAddCommand implements ReplyCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//댓글 수정 커맨드
		System.out.println("- 대댓글 작성 -");
		
		ReplyDAO dao = ReplyDAO.getInstance();
		
		int bId = Integer.parseInt(request.getParameter("bId"));
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		//XSS 방지
		content = content.replaceAll("\r\n", "<br>");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		
		int result = dao.writeReply(bId, writer, content);
		if (result != 1) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('댓글 작성이 실패하였습니다!\n다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('댓글이 성공적으로 등록되었습니다.');");
			out.println("window.location.href='contentView.do?id=" + bId + "';");
			out.println("</script>");
			out.close();
		}
	}
}