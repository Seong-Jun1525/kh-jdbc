package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestRun {
	/** JDBC용 객체
	 * - Connection				: DB의 연결정보를 담고 있는 객체
	 * - [Prepared]Statement	: 연결된 DB에 SQL문을 전달하여 실행하고 그 결과를 받아주는 객체
	 * - ResultSet				: DQL(SELECT)문 실행 후 조회 결과를 담고 있는 객체 
	 * 
	 * JDBC 과정 (★ 순서 중요 ★)
	 * 		1) jdbc driver 등록 		: 해당 DBMS가 제공하는 클래스 등록
	 * 
	 * 		2) Connection 객체 생성 	: 연결하고자 하는 DB정보를 전달하여 해당 DB와 연결하면서 생성
	 * 			=> DB 정보 : 접속 주소(URL), 사용자명(USERNAME), 비밀번호(PASSWORD)
	 * 
	 * 		3) Statement 객체 생성		: Connection 객체를 이용하여 생성 SQL문을 실행하고 결과를 받을 것임!
	 * 
	 * 		4) SQL문을 DB에 전달하여 실행	: Statement 객체 사용
	 * 
	 * 		5) 실행 결과를 받기(저장)
	 * 			5-1) DQL(SELECT)문 실행 					: ResultSet 객체 (=> 조회된 데이터들이 담겨져 있음)
	 * 			5-2) DML(INSERT, UPDATE, DELETE) 실행		: int (=> 처리된 행 수)
	 * 
	 * 		6) 결과에 대해 처리
	 * 			6-1) ResultSet 객체에 담겨져있는 데이터들을 하나하나 꺼내서 vo객체에 옮겨 담기(저장)  
	 * 			6-2) 트랜잭션 처리 (실행 결과에 따라 COMMIT, ROLLBACK 실행)
	 * 
	 * 		7) 사용 후 JDBC용 객체 반납(close처리)
	 * 			=> 생성 역순으로 close!!
	 * 
	 */

	public static void main(String[] args) {
		// TEST 테이블에 데이터 추가
//		insertTest();
		
		// TEST 테이블에 데이터 조회
		selectTest();
	}

	public static void selectTest() {
		// DQL(SELECT) -> ResultSet 객체 결과 받기 -> 데이터를 하나하나 추출하여 저장
		Connection conn = null;
		Statement stat = null;
		ResultSet rset = null;
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String username = "C##JDBC";
			String pw = "JDBC";
			
			conn = DriverManager.getConnection(url, username, pw);
			
			// 3) Statement 객체 생성
			stat = conn.createStatement();
			
			// 4, 5) 쿼리문 실행 및 결과 받기
			String sql = "SELECT * FROM TEST";
			rset = stat.executeQuery(sql);
			
			// 6) 조회된 결과에 대한 데이터를 하나하나 추출하기
			// 		* 데이터가 있는 지 확인 : rset.next():boolean
			
			while(rset.next()) {
				
				// 데이터를 추출할 때 "컬럼명" 도는 "컬럼순번"을 사용하여 추출
				
				// TNO 컬럼의 값 추출 : 해당 컬럼의 데이터 타입 -> NUMBER
				int tno = rset.getInt("TNO");
				// TNAME 컬럼의 값 추출 : 해당 컬럼의 데이터 타입 -> VARCHAR2
				String tname = rset.getString(2); // 컬럼순번은 조회했을 때 기준
				
				// TDATE 컬럼의 값 추출 : 데이터타입 -> DATE
				Date tdate = rset.getDate(3); // java.sql.date import!!!
				
				System.out.println(tno + ", " + tname + ", " + tdate);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 7) 생성 역순으로 자원 반납
			try {
				rset.close();
				stat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void insertTest() {
		// DML(INSERT) -> int 타입 결과 받기 -> 트랜잭션 처리
		Connection conn = null;
		Statement stat = null;
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("** 오라클 드라이버 등록 완료 **");
			
			// 2) Connection 객체 생성
			// 연결할 DB정보 : url, username, password 
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "C##JDBC", "JDBC");
			System.out.println("** Connection 객체 생성 완료 **");
			conn.setAutoCommit(false); // 자동 COMMIT 기능 끄기 (JDBC 6버전 이후로는 AUTO COMMIT 설정이 되어 있음)
			
			// 3) Statement 객체 생성
			stat = conn.createStatement();
			
			// 4, 5) SQL문 실행 후 결과 받기
			String sql = "INSERT INTO TEST VALUES (2, '임성준', SYSDATE)"; // => 완성된 형태의 SQL문 작성
			
			int result = stat.executeUpdate(sql);
			
			// 6) 트랜잭션 처리(DML 실행 시)
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 7) 자원 반납
			try {
				stat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
