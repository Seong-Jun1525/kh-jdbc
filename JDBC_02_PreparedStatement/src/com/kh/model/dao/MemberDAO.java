package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.model.vo.Member;

public class MemberDAO {
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private ResultSet rset = null;
	
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	private final String USER_NAME = "C##JDBC";
	private final String PASSWORD = "JDBC";
	
	public MemberDAO() {}

	/**
	 * 요청된 회원 정보를 DB에 추가하는 메서드
	 * 
	 * @param m			사용자가 입력한 정보들이 담겨있는 Member 객체
	 * @return 			추가(INSERT) 후 처리된 행 수
	 */
	public int insertMember(Member m) {
		int result = 0;
		
		String sql = "INSERT INTO MEMBER VALUES (SEQ_MNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
		/**
		 * Statement와 PreparedStatement의 차이는 SQL 쿼리문이 완성 되어 있는지 미완성되어 있는지의 차이다
		 */
		
		System.out.println("-".repeat(10));
		System.out.println(sql);
		System.out.println("-".repeat(10));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, m.getMemberId());
			pstat.setString(2, m.getMemberPw());
			pstat.setString(3, m.getGender()+"");
			pstat.setInt(4, m.getAge());
			pstat.setString(5, m.getEmail());
			pstat.setString(6, m.getAddress());
			pstat.setString(7, m.getPhone());
			pstat.setString(8, m.getHobby());
			
			result = pstat.executeUpdate(sql);
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * 회원 전체 목록을 조회하여 반환하는 메서드
	 * 
	 * @return 전체 회원 목록
	 */
	public ArrayList<Member> selectAllList() {
		ArrayList<Member> list = new ArrayList<Member>();
		
		String sql = "SELECT * FROM MEMBER ORDER BY MEMBERNO";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			
			pstat = conn.prepareStatement(sql);
			
			rset = pstat.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(
						rset.getInt("MEMBERNO"),
						rset.getString("MEMBERID"),
						rset.getString("MEMBERPW"),
						rset.getString("GENDER") != null ? rset.getString("GENDER").charAt(0) : ' ',
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
					));
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * 
	 * @param searchId
	 * @return
	 */
	public Member searchById(String searchId) {
		Member m = null;

		String sql = "SELECT * FROM MEMBER WHERE MEMBERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, searchId); 
			/**
			 * ?를 값으로 변경할 때 setXXX => XXX는 데이터 타입이다
			 */
			
			rset = pstat.executeQuery();
			
			if(rset.next()) { // rset.next() next() 메서드로 꼭 값이 있는지 체크 해줘야 한다
				m = new Member(
						rset.getInt("MEMBERNO"),
						rset.getString("MEMBERID"),
						rset.getString("MEMBERPW"),
						rset.getString("GENDER") != null ? rset.getString("GENDER").charAt(0) : ' ',
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
					);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
	}

	public int updateMember(Member m) {
		int result = 0;
		
		String sql = "UPDATE MEMBER SET MEMBERPW = ?, GENDER = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ?, WHERE MEMBERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, m.getMemberPw());
			pstat.setString(2, m.getGender()+"");
			pstat.setString(3, m.getEmail());
			pstat.setString(4, m.getPhone());
			pstat.setString(5, m.getAddress());
			pstat.setString(6, m.getHobby());
			pstat.setString(7, m.getMemberId());
			
			result = pstat.executeUpdate();
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int deleteMember(Member m) {
		int result = 0;
		
		String sql = "DELETE FROM MEMBER WHERE MEMBERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, m.getMemberId());
			
			result = pstat.executeUpdate();
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
