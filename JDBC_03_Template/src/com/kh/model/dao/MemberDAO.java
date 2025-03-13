package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

public class MemberDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	/**
	 * 
	 * @param conn	생성되어 있는 Connection 객체
	 * @param m		회원 정보가 저장된 Member 객체
	 * @return		결과 값을 반환
	 */
	public int insertMember(Connection conn, Member m) {
		int result = 0;
		
		// JDBC 드라이버 등록
		// Connection 객체 생성
		// => Service 객체가 완료함
		
		// Statement 객체 생성
		String sql = "INSERT INTO MEMBER VALUES (SEQ_MNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPw());
			pstmt.setString(3, m.getGender());
			pstmt.setInt(4, m.getAge());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getHobby());

			// SQL문 실행, 결과받기
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 결과에 대한 처리 : 트랜잭션 처리
			// 자원 반납
			JDBCTemplate.close(pstmt);
		}
		
		
		
		
		return result;
	}
	
	public int updateMember(Connection conn, Member m) {
		int result = 0;
		
		String sql = "UPDATE MEMBER SET MEMBERPW = ?, GENDER = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE MEMBERID = ?";
		
		try {
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberPw());
			pstmt.setString(2, m.getGender());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getAddress());
			pstmt.setString(6, m.getHobby());
			pstmt.setString(7, m.getMemberId());
			
			result = pstmt.executeUpdate();
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, Member m) {
		int result = 0;
		
		String sql = "DELETE FROM MEMBER WHERE MEMBERID = ?";
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 결과에 대한 처리 : 트랜잭션 처리
			// 자원 반납
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public Member searchMember(Connection conn, String searchId) {
		Member m = null;
		
		String sql = "SELECT * FROM MEMBER WHERE MEMBERID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) { // rset.next() next() 메서드로 꼭 값이 있는지 체크 해줘야 한다
				m = new Member(
						rset.getInt("MEMBERNO"),
						rset.getString("MEMBERID"),
						rset.getString("MEMBERPW"),
						rset.getString("GENDER") != null ? rset.getString("GENDER") : " ",
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
					);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return m;
	}

	public ArrayList<Member> selectAllMember(Connection conn) {
		ArrayList<Member> list = new ArrayList<Member>();
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(
						rset.getInt("MEMBERNO"),
						rset.getString("MEMBERID"),
						rset.getString("MEMBERPW"),
						rset.getString("GENDER") != null ? rset.getString("GENDER") : " ",
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
				));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return list;
	}

	
}
