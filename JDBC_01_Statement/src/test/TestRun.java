package test;

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
		insertTest();
	}

	public static void insertTest() {
		// DML(INSERT) -> int 타입 결과 반환 -> 트랜잭션 처리
		// 1) jdbc driver 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("** 오라클 드라이버 등록 완료 **");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
