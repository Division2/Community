package com.jsp.ex.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReplyDAO {
	private DataSource dataSRC;
	private static ReplyDAO instance;
	
	private ReplyDAO() {
		try {
			Context ctx = new InitialContext();
			dataSRC = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ReplyDAO getInstance() {
		if (instance == null) {
			instance = new ReplyDAO();
		}
		return instance;
	}
	
	//댓글 작성
	public int writeReply(int bId, String writer, String content) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO reply(bId, rName, rContent, rDate) VALUES(?, ?, ?, ?)";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bId);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	//댓글 조회
	public ArrayList<ReplyDTO> replyView(int bId) {
		ArrayList<ReplyDTO> dtos = new ArrayList<ReplyDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM reply WHERE bId = ? order by rDate ASC, bId DESC";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				dto.setrId(rs.getInt("rId"));
				dto.setbId(rs.getInt("bId"));
				dto.setName(rs.getString("rName"));
				dto.setContent(rs.getString("rContent"));
				dto.setDate(sdf.format(rs.getTimestamp("rDate")));
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dtos;
	}
	
	//댓글 수정
	public int modifyReply(int rId, String content) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE reply SET rContent = ? WHERE rId = ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, content);
			pstmt.setInt(2, rId);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	//댓글 삭제
	public int deleteReply(int rId, int bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM reply WHERE rId = ? AND bId = ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, rId);
			pstmt.setInt(2, bId);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	//URI수정으로 타 작성자의 댓글 수정·삭제 방지
	public int checkAuth(int rId, int bId, String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		String query = "SELECT * FROM reply WHERE rId = ? AND bId = ? AND rName = ?";
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, rId);
			pstmt.setInt(2, bId);
			pstmt.setString(3, name);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}