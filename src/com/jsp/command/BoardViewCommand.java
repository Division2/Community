package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.ex.model.BoardDAO;
import com.jsp.ex.model.BoardDTO;

public class BoardViewCommand implements BoardCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//게시글 상세정보 조회 커맨드
		System.out.println("- 게시글 열람 -");
		
		BoardDAO dao = BoardDAO.getInstance();
		
		int postId = Integer.parseInt(request.getParameter("id"));
		
		ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
		
		//parameter로 받아온 id랑 같은 해당 게시물 열람
		dtos = dao.viewPost(postId);
		
		//읽으면 조회수 증가
		dao.upHit(postId);
		
		//해당 게시글 ID가 DB에 없을 경우 에러 메시지, redirect
		//근데 여기서 redirect 시킨 탓에 controller에서 forward가 안 돼서
		//IllegalStateException 나오는데 어찌 고치징
		if (dtos.isEmpty()) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('존재하지 않는 게시글입니다!');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		request.setAttribute("content", dtos);
	}
}