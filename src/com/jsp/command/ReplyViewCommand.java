package com.jsp.command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.ex.model.ReplyDAO;
import com.jsp.ex.model.ReplyDTO;

public class ReplyViewCommand implements ReplyCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//댓글 조회 커맨드
		System.out.println("- 댓글 조회 -");
		
		ReplyDAO dao = ReplyDAO.getInstance();
		
		int postId = Integer.parseInt(request.getParameter("id"));
		
		ArrayList<ReplyDTO> dtos = new ArrayList<ReplyDTO>();
		dtos = dao.replyView(postId);
		
		request.setAttribute("reply", dtos);
	}
}