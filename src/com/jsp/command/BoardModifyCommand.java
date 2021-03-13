package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.ex.model.BoardDAO;

public class BoardModifyCommand implements BoardCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//게시글 수정 결과 커맨드
		System.out.println("- 게시글 수정 완료 -");
		
		BoardDAO dao = BoardDAO.getInstance();
		
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int postId = Integer.parseInt(request.getParameter("id"));
		
		//XSS 방지
		title = title.replaceAll("<", "&lt;");
		title = title.replaceAll(">", "&gt;");
		
		content = content.replaceAll("\r\n", "<br>");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		
		int result = dao.updatePost(title, content, category, postId);
		if (result != 1) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('게시글 수정이 실패하였습니다!\n다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('게시글이 성공적으로 수정되었습니다.');");
			out.println("window.location.href='contentView.do?id=" + postId + "';");
			out.println("</script>");
			out.close();
		}
	}
}