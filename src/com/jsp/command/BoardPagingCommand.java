package com.jsp.command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.ex.model.BoardDAO;
import com.jsp.ex.model.BoardDTO;
import com.jsp.ex.model.PagingVO;

public class BoardPagingCommand implements BoardCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//게시글 조회 페이징 커맨드
		System.out.println("- 게시글 조회(페이징 O) -");
	    
		BoardDAO dao = BoardDAO.getInstance();
	    
	    ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
	    
	    //게시글 총 갯수
	    int totalCount = dao.getBoardTotalCount();
	    //현재 페이지가 몇 페이지인지
	    int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
	     
	    PagingVO paging = new PagingVO();
	    paging.setPageNo(page); //get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
	    paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
	    paging.setTotalCount(totalCount);
	     
	    page = (page - 1) * 10; //select해오는 기준을 구한다.
	     
	    dtos = dao.pagingView(page, paging.getPageSize());
	     
	    request.setAttribute("board", dtos);
	    request.setAttribute("paging", paging);
	}
}