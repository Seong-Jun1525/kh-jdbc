package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

/**
 * DAO (Database Access Object)
 * - DB에 직접 접근하여 사용자의 요청에 맞는 SQL문 실행 후 결과 반환
 * 		--> JDBC 사용
 */
public class MemberDAO {
	// DB 정보 : 서버주소, 사용자명, 비밀번호
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
		Connection conn = null;
		Statement stat = null;
		int result = 0;
		
		String sql = "INSERT INTO MEMBER VALUES ("
				+ "SEQ_MNO.NEXTVAL, '" 
				+ m.getMemberId() +"', '" 
				+ m.getMemberPw() +"', '" 
				+ m.getGender() + "', " 
				+ m.getAge() + ", '" 
				+ m.getEmail() + "', '" 
				+ m.getAddress() + "', '" 
				+ m.getPhone() +"', '" 
				+ m.getHobby() + "', DEFAULT)";
		
		System.out.println("-".repeat(10));
		System.out.println(sql);
		System.out.println("-".repeat(10));
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false); // auto commit off
			
			// 3) Statement 객체 생성
			stat = conn.createStatement(); // Statement 객체를 Connection 객체를 통해 생성
			
			// 4, 5) SQL 실행 후 저장
			result = stat.executeUpdate(sql); // SQL문 실행 후 결과 저장
			
			// 6) 트랜잭션 처리
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stat.close();
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
		Connection conn = null;
		Statement stat = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		// DQL문 실행
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD); // 이때 SQLException 발생!
			
			stat = conn.createStatement();
			
			rset = stat.executeQuery(sql);
			
			while(rset.next()) {
				// [1] 데이터를 추출하여 Member 객체에 담기(생성)
				// 컬럼명은 대/소문자 상관x
				// [2] Member 객체를 리스트에 추가
				list.add(new Member(
						rset.getInt("MEMBERNO"),
						rset.getString("MEMBERID"),
						rset.getString("MEMBERPW"),
						rset.getString("GENDER") != null ? rset.getString("GENDER").charAt(0) : 'A', 
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
					));
			}
			
		} catch (ClassNotFoundException | SQLException e) { // Exception 끼리 상속관계가 아닐 때 사용할 수 있는 방법!
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stat.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}
}
