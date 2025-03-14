package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

public class MemberDAO {
	// PreparedStatement 및 ResultSet 객체 초기화
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
			e.printStackTrace();
		} finally {
			// 결과에 대한 처리 : 트랜잭션 처리
			// 자원 반납
			JDBCTemplate.close(pstmt);
		}

		return result;
	}
	
	/**
	 * 전달받은 회원데이터 수정
	 * 
	 * @param conn Connection 객체
	 * @param m 수정할 Member 정보
	 * @return DML이므로 int 형 반환
	 */
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	/**
	 * 전달받은 회원 아이디로 회원 삭제
	 * @param conn Connection 객체
	 * @param id 전달받은 회원 아이디
	 * @return DML은 int 형을 반환
	 */
	public int deleteMember(Connection conn, String id) {
		int result = 0;
		
		String sql = "DELETE FROM MEMBER WHERE MEMBERID = ?";
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 결과에 대한 처리 : 트랜잭션 처리
			// 자원 반납
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 전체 회원 목록 삭제
	 * @param conn Connection 객체
	 * @return DML은 int 형을 반환
	 */
	public int deleteMemberAll(Connection conn) {
		int result = 0;
		
		try {
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement("DELETE FROM MEMBER");
			
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
	
	public ArrayList<Member> selectKeyword(Connection conn, String keyword) {
		ArrayList<Member> kList = new ArrayList<Member>();
		
		String sql = "SELECT * FROM MEMBER WHERE MEMBERID LIKE ? ORDER BY ENROLLDATE DESC";	// 쿼리문을 깔끔하게 작성하는 방법
//		String sql = "SELECT * FROM MEMBER WHERE MEMBERID LIKE '%'||?||'%' ORDER BY ENROLLDATE DESC"; // setString 할 때 깔끔하게 작성하는 방법
		// 큰 차이는 없다
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			/** setString() 에 대한 설명
		     * Sets the designated parameter to the given Java {@code String} value.
		     * The driver converts this
		     * to an SQL {@code VARCHAR} or {@code LONGVARCHAR} value
		     * (depending on the argument's
		     * size relative to the driver's limits on {@code VARCHAR} values)
		     * when it sends it to the database.
		     * 
		     * => 해석하자면 자바에서 문자열 값으로 주어진 파리미터로 구현된 설정? 인거 같고
		     * 		그걸 이제 드라이버가 변환한다는 의미인거 같고 이때 VARCHAR 형태 혹은 LONGVARCHAR 형태의 SQL 값으로 변환하는거 같다
		     * => 데이터베이스에서 문자열은 ''로 감싸서 작업하기 때문에 보내는 setString 메서드에서 2번째 매개변수 값 전체에 ''로 감싸게 된다! 		
		     * 
		     *
		     * @param parameterIndex the first parameter is 1, the second is 2, ...
		     * @param x the parameter value
		     * @throws SQLException if parameterIndex does not correspond to a parameter
		     * marker in the SQL statement; if a database access error occurs or
		     * this method is called on a closed {@code PreparedStatement}
		     */
			
			pstmt.setString(1, "%" + keyword + "%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				kList.add(new Member(
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
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return kList;
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
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return m;
	}

	public ArrayList<Member> selectAllMember(Connection conn) {
		ArrayList<Member> list = new ArrayList<Member>();
		
		try {
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER ORDER BY ENROLLDATE DESC");
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
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return list;
	}
	
}
