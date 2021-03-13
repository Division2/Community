package com.jsp.command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.ex.model.BoardDAO;
import com.jsp.ex.model.BoardDTO;

public class BoardModifyViewCommand implements BoardCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//게시글 수정 View 커맨드
		System.out.println("- 게시글 수정 -");
		
		BoardDAO dao = BoardDAO.getInstance();
		
		int postId = Integer.parseInt(request.getParameter("id"));
		
		ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
		//parameter로 받아온 id랑 같은 해당 게시물 열람
		dtos = dao.viewPost(postId);
		
		request.setAttribute("dto", dtos);
	}
}