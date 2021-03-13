package com.jsp.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.ex.model.MemberDAO;
import com.jsp.ex.model.MemberDTO;

public class RegisterCommand implements MemberCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO mdto = new MemberDTO();

		mdto.setAccount(request.getParameter("account"));
		mdto.setPassword(request.getParameter("password"));
		mdto.setNickname(request.getParameter("nickname"));
		mdto.setEmail(request.getParameter("email"));
		mdto.setSubscriptionDate(Timestamp.valueOf(LocalDateTime.now()));
		
		int result = dao.register(mdto.getAccount(), mdto.getPassword(), mdto.getNickname(), mdto.getEmail(), mdto.getAge(), mdto.getSubscriptionDate());
		if (result != 1) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('회원가입이 실패하였습니다!\n다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('환영합니다 " + mdto.getAccount() + "님!')");
			out.println("window.location.href='main.do'");
			out.println("</script>");
			out.close();
			
			HttpSession session = request.getSession();
			session.setAttribute("Account", mdto.getAccount());
		}
	}
}