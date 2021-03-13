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

public class BoardDAO {
	private DataSource dataSRC;
	private static BoardDAO instance;
	
	private BoardDAO() {
		try {
			Context ctx = new InitialContext();
			dataSRC = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BoardDAO getInstance() {
		if (instance == null) {
			instance = new BoardDAO();
		}
		return instance;
	}
	
	//게시글 조회(페이징 없는 메소드 BoardListCommand 사용)
	public ArrayList<BoardDTO> boardView() {
		ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM board ORDER BY bGroup DESC, bStep ASC, bId DESC";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				dto.setId(rs.getInt("bId"));
				dto.setName(rs.getString("bName"));
				dto.setTitle(rs.getString("bTitle"));
				dto.setContent(rs.getString("bContent"));
				dto.setDate(sdf.format(rs.getTimestamp("bDate")));
				dto.setHit(rs.getInt("bHit"));
				dto.setGroup(rs.getInt("bGroup"));
				dto.setStep(rs.getInt("bStep"));
				dto.setIndent(rs.getInt("bIndent"));
				dto.setCategory(rs.getString("category"));
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
	
	//게시글 조회(페이징 있는 메소드 BoardPagingCommand 사용)
	public ArrayList<BoardDTO> pagingView(int startRow, int endRow) {
		ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM board ORDER BY bGroup DESC, bStep ASC, bId DESC LIMIT ?, ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				dto.setId(rs.getInt("bId"));
				dto.setName(rs.getString("bName"));
				dto.setTitle(rs.getString("bTitle"));
				dto.setContent(rs.getString("bContent"));
				dto.setDate(sdf.format(rs.getTimestamp("bDate")));
				dto.setHit(rs.getInt("bHit"));
				dto.setGroup(rs.getInt("bGroup"));
				dto.setStep(rs.getInt("bStep"));
				dto.setIndent(rs.getInt("bIndent"));
				dto.setCategory(rs.getString("category"));
				dto.setReplyCount(getReplyTotalCount(rs.getInt("bId")));
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
	
	//게시글 작성
	public int writePost(String account, String title, String content, String category) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO board(bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, category) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, account);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setInt(5, 0);
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			pstmt.setString(9, category);
			
			result = pstmt.executeUpdate();
			
			if (result == 1) {
				return 1;
			}
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
	
	//댓글이 있는 경우 댓글 테이블 외래키 오류나니까 댓글이 있는지 확인 후 있으면 삭제하는 것도 추가하자
	//게시글 삭제
	public int deletePost(int postId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM board WHERE bId = ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, postId);
			
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
	
	//게시글 수정
	public int updatePost(String title, String content, String category, int postId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE board SET bTitle = ?, bContent = ?, category = ? WHERE bId = ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, category);
			pstmt.setInt(4, postId);
			
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
	
	//게시글 열람
	public ArrayList<BoardDTO> viewPost(int postId) {
		ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM board WHERE bId = ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				dto.setId(rs.getInt("bId"));
				dto.setName(rs.getString("bName"));
				dto.setTitle(rs.getString("bTitle"));
				dto.setContent(rs.getString("bContent"));
				dto.setDate(sdf.format(rs.getTimestamp("bDate")));
				dto.setHit(rs.getInt("bHit") + 1);
				dto.setGroup(rs.getInt("bGroup"));
				dto.setStep(rs.getInt("bStep"));
				dto.setIndent(rs.getInt("bIndent"));
				dto.setCategory(rs.getString("category"));
				dtos.add(dto);
			}
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
		return dtos;
	}
	
	//게시글 열람 시 조회수 증가
	public int upHit(int postId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE board SET bHit = bHit + 1 WHERE bId = ?";
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, postId);
			
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
	
	//URI수정으로 타 작성자의 게시글 삭제 방지
	public int checkAuth(int postId, String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		String query = "SELECT * FROM board WHERE bId = ? AND bName = ?";
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, postId);
			pstmt.setString(2, name);
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
	
	//게시글의 총 갯수
	public int getBoardTotalCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT COUNT(*) FROM board";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);
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
		return 0;
	}
	
	//해당 게시글의 댓글 총 갯수
	public int getReplyTotalCount(int bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String query = "SELECT COUNT(*) FROM reply WHERE bId = ?";
		
		try {
			con = dataSRC.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);
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
		return 0;
	}
}