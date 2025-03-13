package com.kh.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTemplate {
	/**
	 * 매번 반복적으로 사용한 코드들을 메서드로 정의하기 위한 용도(공통 템플릿)
	 * 
	 * => 해당 객체를 매번 생성하지 않고 호출할 수 있도록 함
	 * 			=> 즉, static으로 정의!!!
	 */
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER_NAME = "C##JDBC";
	private static final String PASSWORD = "JDBC";
	
	/**
	 * Connection 객체 생성 메서드
	 * 
	 * @return 생성된 Connection 객체 리턴
	 */
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * Connection 객체 반납
	 * @param conn close 처리할 객체
	 */
	public static void close(Connection conn) {
		try {
			// Connection 객체가 생성되어 있고 닫혀 있지 않을 때만 close 처리
			if(conn != null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Statement 객체 반납
	 * @param stat close 처리할 객체
	 */
	public static void close(Statement stat) { // PreparedStatement은 Statement를 상속받고 있다
		try {
			// Statement 객체가 생성되어 있고 닫혀 있지 않을 때만 close 처리
			if(stat != null && !stat.isClosed()) stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ResultSet 객체 반납
	 * @param rest close 처리할 객체
	 */
	public static void close(ResultSet rest) {
		try {
			// ResultSet 객체가 생성되어 있고 닫혀 있지 않을 때만 close 처리
			if(rest != null && !rest.isClosed()) rest.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * commit 처리
	 * 
	 * @param conn 생성되어 있는 Connection 객체
	 */
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * rollback 처리
	 * 
	 * @param conn 생성되어 있는 Connection 객체
	 */
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
