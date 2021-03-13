<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("Account") != null) {
		session.invalidate();
%>
		<jsp:forward page="main.do"/>
<%
	}
%>