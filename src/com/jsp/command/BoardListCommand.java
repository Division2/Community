package com.jsp.command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.ex.model.BoardDAO;
import com.jsp.ex.model.BoardDTO;

public class BoardListCommand implements BoardCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//게시글 조회 커맨드
		System.out.println("- 게시글 조회(페이징 X) -");
		
		BoardDAO dao = BoardDAO.getInstance();
	
		ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
		dtos = dao.boardView();
		
		request.setAttribute("board", dtos);
	}
}