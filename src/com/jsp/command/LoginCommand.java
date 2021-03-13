package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.ex.model.MemberDAO;
import com.jsp.ex.model.MemberDTO;

public class LoginCommand implements MemberCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO mdto = new MemberDTO();
		
		mdto.setAccount(request.getParameter("account"));
		mdto.setPassword(request.getParameter("password"));
		
		int result = dao.login(mdto.getAccount(), mdto.getPassword());
		
		if (result != 1) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 비밀번호가 잘못되었습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('환영합니다 " + mdto.getAccount() + "님!')");
			out.println("window.location.href='main.do';");
			out.println("</script>");
			out.close();
			
			HttpSession session = request.getSession();
			session.setAttribute("Account", mdto.getAccount());
		}
	}
}