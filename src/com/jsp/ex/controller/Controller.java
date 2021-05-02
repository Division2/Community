package com.jsp.ex.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.command.BoardCommand;
import com.jsp.command.BoardDeleteCommand;
import com.jsp.command.BoardModifyCommand;
import com.jsp.command.BoardModifyViewCommand;
import com.jsp.command.BoardPagingCommand;
import com.jsp.command.BoardViewCommand;
import com.jsp.command.BoardWriteCommand;
import com.jsp.command.LoginCommand;
import com.jsp.command.MemberCommand;
import com.jsp.command.RegisterCommand;
import com.jsp.command.ReplyAddCommand;
import com.jsp.command.ReplyCommand;
import com.jsp.command.ReplyDeleteCommand;
import com.jsp.command.ReplyModifyCommand;
import com.jsp.command.ReplyViewCommand;
import com.jsp.command.ReplyWriteCommand;

@WebServlet("*.do")
public class Controller extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request, response);
	}
	
	protected void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String cmd = uri.substring(ctx.length());

		//연결 요청이 main.do로 들어왔을 때 처리
		if (cmd.equals("/main.do")) {
			viewPage = "index.jsp";
		}
		else if(cmd.equals("/board.do")) {
		//	BoardCommand bCmd = new BoardListCommand();
			BoardCommand bCmd = new BoardPagingCommand();
			bCmd.excute(request, response);
			
			viewPage = "board.jsp";
		}
		//연결 요청이 writeView.do로 들어왔을 때 처리
		else if (cmd.equals("/writeView.do")) {
			viewPage = "writeView.jsp";
		}
		//연결 요청이 writePost.do로 들어왔을 때 처리
		else if (cmd.equals("/writePost.do")) {
			BoardCommand bCmd = new BoardWriteCommand();
			bCmd.excute(request, response);
		}
		//연결 요청이 contentView.do로 들어왔을 때 처리
		//댓글도 여기서 처리한당
		else if (cmd.equals("/contentView.do")) {
			BoardCommand bCmd = new BoardViewCommand();
			bCmd.excute(request, response);
			
			ReplyCommand rCmd = new ReplyViewCommand();
			rCmd.excute(request, response);
			
			viewPage = "contentView.jsp";
		}
		//연결 요청이 postModify.do로 들어왔을 때 처리
		else if (cmd.equals("/modifyView.do")) {
			BoardCommand bCmd = new BoardModifyViewCommand();
			bCmd.excute(request, response);
			
			viewPage = "contentModify.jsp";
		}
		//연결 요청이 postModify.do로 들어왔을 때 처리
		else if (cmd.equals("/modifyPost.do")) {
			BoardCommand bCmd = new BoardModifyCommand();
			bCmd.excute(request, response);
		}
		//연결 요청이 postDelete.do로 들어왔을 때 처리
		else if (cmd.equals("/deletePost.do")) {
			BoardCommand bCmd = new BoardDeleteCommand();
			bCmd.excute(request, response);
		}
		//연결 요청이 writeReply.do로 들어왔을 때 처리
		else if (cmd.equals("/writeReply.do")) {
			ReplyCommand rCmd = new ReplyWriteCommand();
			rCmd.excute(request, response);
		}
		//연결 요청이 deleteReply.do로 들어왔을 때 처리
		else if (cmd.equals("/deleteReply.do")) {
			ReplyCommand rCmd = new ReplyDeleteCommand();
			rCmd.excute(request, response);
		}
		//연결 요청이 modifyReply.do로 들어왔을 때 처리
		else if (cmd.equals("/modifyReply.do")) {
			ReplyCommand rCmd = new ReplyModifyCommand();
			rCmd.excute(request, response);
		}
		//연결 요청이 addReply.do(대댓글)로 들어왔을 때 처리
		else if (cmd.equals("/addReply.do")) {
			ReplyCommand rCmd = new ReplyAddCommand();
			rCmd.excute(request, response);
		}
		//연결 요청이 login.do로 들어왔을 때 처리
		else if (cmd.equals("/login.do")) {
			
			viewPage = "login.jsp";
		}
		//연결 요청이 loginChecked.do로 들어왔을 때 처리
		else if (cmd.equals("/loginChecked.do")) {
			MemberCommand mCmd = new LoginCommand();
			mCmd.excute(request, response);
		}
		//연결 요청이 register.do로 들어왔을 때 처리
		else if (cmd.equals("/register.do")) {
			viewPage = "register.jsp";
		}
		//연결 요청이 registerChecked.do로 들어왔을 때 처리
		else if (cmd.equals("/registerChecked.do")) {
			MemberCommand mCmd = new RegisterCommand();
			mCmd.excute(request, response);
		}
		//연결 요청이 logout.do로 들어왔을 때 처리
		else if (cmd.equals("/logout.do")) {
			viewPage = "logout.jsp";
		}
		
		//viewPage가 null이 아닐 때만 forward시켜주자.
		if (viewPage != null) {
			//해당 요청 viewPage에 맞는 jsp View로 forward
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
	}
}