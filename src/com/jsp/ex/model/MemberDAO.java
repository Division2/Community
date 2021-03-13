package com.jsp.ex.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private DataSource dataSRC;
	private static MemberDAO instance;
	
	private MemberDAO() {
		try {
			Context ctx = new InitialContext();
			dataSRC = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MemberDAO getInstance() {
		if (instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}
	
	//로그인
	public int login(String account, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM c_member WHERE account = ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, account);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getString("password").equals(password)) {
					return 1; //로그인 성공 시
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (pstmt != null) {pstmt.close();}
				if (con != null) {con.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return -1;
	}
	
	//회원가입
	public int register(String account, String password, String nickname, String email, int age, Timestamp date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO c_member VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, account);
			pstmt.setString(2, password);
			pstmt.setString(3, nickname);
			pstmt.setString(4, email);
			pstmt.setInt(5, age);
			pstmt.setTimestamp(6, date);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {pstmt.close();}
				if (con != null) {con.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	//회원가입 ID 중복 체크
	public int checkId(String account) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM c_member WHERE account = ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (pstmt != null) {pstmt.close();}
				if (con != null) {con.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return -1;
	}
}