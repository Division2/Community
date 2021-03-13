package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.ex.model.BoardDAO;

public class BoardWriteCommand implements BoardCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//글 작성 커맨드
		System.out.println("- 글쓰기 -");
		
		BoardDAO dao = BoardDAO.getInstance();
		
		String category = request.getParameter("category");
		String account = request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		//XSS 방지
		title = title.replaceAll("<", "&lt;");
		title = title.replaceAll(">", "&gt;");
		
		content = content.replaceAll("\r\n", "<br>");
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		
		int result = dao.writePost(account, title, content, category);
		if (result != 1) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('게시글 작성이 실패하였습니다!\n다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('게시글이 성공적으로 등록되었습니다.');");
			out.println("window.location.href='board.do';");
			out.println("</script>");
			out.close();
		}
	}
}