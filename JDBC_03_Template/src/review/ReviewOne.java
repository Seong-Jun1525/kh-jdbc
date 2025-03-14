package review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kh.model.vo.Member;

public class ReviewOne {
	public static void main(String[] args) {
		
		/*
			*---- DataBase Server Info ----*
			 - IP : 192.168.22.55
			 - PORT : 1521
			 - sid  : xe
			 - username : C##DBTEST
			 - password : TEST1234
			*------------------------------*
			
			 - MEMBER 테이블은 JDBC 계정과 동일하다고 가정
			 - Member 클래스도 수업용 프로젝트와 동일하다고 가정
		*/
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			// JDBC Driver 등록.  ojdbcxx.jar파일을 먼저 프로젝트에 등록한 후에 사용 가능.
			// 이때 ClassNotFoundException 이 발생할 수 있으므로 예외처리를 해줘야한다
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			
			// 여기부터는 SQLException 예외처리를 해줘야하는 부분이다.
			// Connection 객체 생성
			// 등록한 사용자 계정명과 비밀번호를 그대로 작성해야한다
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "C##JDBC", "JDBC"); // 실습을 위해서 값 변경..
			
			String query = "SELECT * FROM MEMBER WHERE MEMBERID = ?"; // 쿼리문 -> PreparedStatement 방식으로 작성
			
			
			/**
			 * PreparedStatement를 사용할 경우 쿼리문을 먼저 작성 후
			 * 
			 * PreparedStatement 변수에 Connection 객체로 prepareStatement() 메서드를 호출하고 쿼리문을 전달해야함
			 * 
			 * 이후 변경해야할 값인 ? 부분이 있을 경우
			 * 쿼리문을 실행하기 전에 PreparedStatement 객체의 setXXX(순번, 변경할 값) 메서드를 호출하여 값을 설정한다
			 * 
			 */
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "asd");
			
			/**
			 * DQL(SELECT) 	: PreparedStatement의 executeQuery() 메서드를 호출하여 쿼리문을 실행해야 한다
			 * 						=> 반환값 : ResultSet
			 * 
			 * DML()		: PreparedStatement의 executeUpdate() 메서드를 호출하여 쿼리문을 실행해야 한다
			 * 						=> 반환값 : int
			 * 
			 * PreparedStatement일 경우 쿼리문을 전달하지 않는다!!
			 * 
			 */
			rset = pstmt.executeQuery();
			
			
			/**
			 * DQL : 결과가 ResultSet에 저장되므로 결과가 있는지를 next()메서드를 사용하여 먼저 체크해줘야 한다!
			 * 			결과가 있을 경우 하나하나 추출하여 별도의 객체에 저장 또는 처리해줘야한다
			 * 
			 * DML : 결과가 int 타입이고 변경사항이 있으므로 트랜잭션 처리를 해줘야 한다
			 */
			
			if (rset.next()) {
				
				/**
				 * getXXX()의 매개변수는 해당 컬럼의 순번 혹은 컬럼명으로 작성하고 컬럼명일 경우 대소문자 구분을 하지 않는다
				 */
				
				Member member = new Member(rset.getInt("memberno"),
							       rset.getString("memberid"),
							       rset.getString(3),
							       rset.getString(4),
							       rset.getInt("age"),
							       rset.getString(6),
							       rset.getString(7),
							       rset.getString(8),
							       rset.getString(9),
							       rset.getDate("enrolldate"));
				System.out.println(member);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * 자원 반납은 finally 에서 예외 처리를 해주고 이때 SQLException이 발생할 수 있으므로 예외처리를 해줘야한다
			 * 자원 반납을 할 때는 생성한 것의 역순으로 반납을 해준다
			 */
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
}
