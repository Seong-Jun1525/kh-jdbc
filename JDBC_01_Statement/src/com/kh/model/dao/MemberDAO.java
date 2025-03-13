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
	
	// JDBC 객체 선언
	private Connection conn = null;
	private Statement stat = null;
	private ResultSet rset = null;
	
	// DB 정보 : 서버주소, 사용자명, 비밀번호
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	/**
	 * 각각 :으로 구분
	 * 
	 * localhost는 데이터베이스가 로컬시스템에 설치된 것을 의미
	 * 
	 * 1521은 Oracle 데이터베이스의 기본 포트번호
	 * 
	 * xe는 데이터베이스의 인스턴스 이름이다
	 * 
	 * Sql Developer에서 사용자를 생성하고 데이터베이스 접속 계정을 설정할 때 값들을 넣어주면 된다.
	 */
	
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
		
		String sql = "SELECT * FROM MEMBER ORDER BY MEMBERNO";
		
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
						rset.getString("GENDER") != null ? rset.getString("GENDER").charAt(0) : ' ', 
								/** NullPointerException
								 * 발생 원인
								 * 	- rset.getString("GENDER").charAt(0) 에서 
								 * 	- rset.getString("GENDER") 값이 null일 경우 charAt(0)을 하려고 할 때 발생하게 된다.
								 * 	- 즉 값이 null인데 메서드를 호출하려고 해서 오류가 발생하는 것이다.
								 * 
								 * 해결 방안
								 * 	- charAt() 메서드를 호출하기 전에 null 여부를 판단하기 위해서 삼항연산자를 사용한다.
								 * 	- 삼항연산자를 사용하여 null일 경우 임의 값 'X'를 주는 방식으로 해결했다.
								 */
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
		//  => Controller에서 null 값으로 비교하도록 처리했으므로 초기값을 null로 초기화해야 함
		
		// 실행할 쿼리문
		String sql = "SELECT * FROM MEMBER WHERE MEMBERID = '"+ searchId +"'";
//		String sql = String.format("SELECT * FROM MEMBER WHERE MEMBERID = '%s'", searchId);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			
			stat = conn.createStatement();
			
			rset = stat.executeQuery(sql);
			
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
			
			// 조건문이 끝난 시점에 조회된
			// 		데이터가 없다면 m은 null값이다
			// 		데이터가 있다면 m은 객체의 주소값이다
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
	}

	public int updateMember(Member m) {
		int result = 0;
		
//		String sql = "UPDATE MEMBER SET MEMBERPW = '" + m.getMemberPw() 
//		+ "', GENDER = '" + m.getGender() 
//		+ "', EMAIL = '" + m.getEmail()
//		+ "', PHONE = '" + m.getPhone()
//		+ "', ADDRESS = '" + m.getAddress() 
//		+ "', HOBBY = '" + m.getHobby() + "' WHERE MEMBERID = '" + m.getMemberId() + "'";
		
		String sql = String.format(
				"UPDATE MEMBER SET MEMBERPW = '%s', GENDER = '%s', EMAIL = '%s', PHONE = '%s', ADDRESS = '%s', HOBBY = '%s' WHERE MEMBERID = '%s'",
				m.getMemberPw(), m.getGender(), m.getEmail(), m.getPhone(), m.getAddress(), m.getHobby(), m.getMemberId());
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버의 전체 클래스명
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false); // auto commit off
			
			stat = conn.createStatement();
			
			result = stat.executeUpdate(sql);
			
			// 트랜잭션 처리
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
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
		
		return result;
	}

	public int deleteMember(Member m) {
		int result = 0;
		
		String sql = String.format("DELETE FROM MEMBER WHERE MEMBERID = '%s'", m.getMemberId());
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버의 전체 클래스명
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false); // auto commit off
			
			stat = conn.createStatement();
			
			result = stat.executeUpdate(sql);
			
			// DML 수행 후 트랜잭션 처리해야함
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException | SQLException e) {
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

		return result;
	}
}
