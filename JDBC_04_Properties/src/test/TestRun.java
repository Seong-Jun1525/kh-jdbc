package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestRun {

	/**
	 * Properties 특징
	 * 
	 * - Map 계열의 컬렉션 중 하나 => key-value 형태로 데이터를 저장
	 * - 문자열 형태로 데이터 저장
	 * 
	 * 사용하는 메서드
	 * - 값을 저장할 때 : setProperty(key, value)
	 * - 값을 가져올 때 : getProperty(key)
	 * 
	 * 파일로 저장 시 종류(확장자)
	 * - properties
	 * - xml
	 */
	
	public static void main(String[] args) {
		// Properties 객체에 담긴 데이터를 파일로 저장
		// saveProp();
		
		// JDBC 관련 설정 파일 만들기(저장)
		// saveJdbcSettings();
		
		// JDBC 설정 파일 읽어오기
		// readJdbcSettings();
		
		// readQueryFile();
	}

	/**
	 * 쿼리문을 저장한 파일 내용 읽어오기
	 * 
	 * - xml 형식의 파일 내용 읽기 : loadFromXML(InputStream)
	 * 
	 */
	private static void readQueryFile() {
		Properties prop = new Properties();
		
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
			
			for(Object key : prop.keySet()) System.out.println(key + " : " + prop.getProperty(key+""));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * JDBC 설정 파일 내용 읽어오기
	 * - properties 형식의 파일 내용 읽기 : load(InputStream)
	 */
	private static void readJdbcSettings() {
		Properties prop = new Properties();
		
		try {
			// 파일로부터 데이터를 읽어와서 Properties 객체에 담기
			prop.load(new FileInputStream("resources/settings.properties"));
			
//			String driver = prop.getProperty("driver");
//			String url = prop.getProperty("url");
//			String username = prop.getProperty("username");
//			String password = prop.getProperty("password");
//			System.out.println("driver ==> " + driver);
//			System.out.println("url ==> " + url);
//			System.out.println("username ==> " + username);
//			System.out.println("password ==> " + password);
			
			for(Object key : prop.keySet()) System.out.println(key + " : " + prop.getProperty(key+""));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * JDBC 관련된 설정 정보를 파일에 저장
	 * 
	 * - driver 정보 	: oracle.jdbc.driver.OracleDriver
	 * - url			: jdbc:oracle:thin:@IP주소:포트번호:sid
	 * - username		: 사용자명
	 * - password		: 비밀번호	
	 */
	private static void saveJdbcSettings() {
		Properties prop = new Properties();
		
		prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
		prop.setProperty("url", "jdbc:oracle:thin:@localhost:1521:xe");
		prop.setProperty("username", "C##JDBC");
		prop.setProperty("password", "JDBC");
		
		try {
			prop.store(new FileOutputStream("resources/settings.properties"), "JDBC driver");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Properties 객체를 이용하여 데이터를 파일에 저장
	 * 
	 * 1) Properties 객체 생성
	 * 2) 객체에 데이터 저장
	 * 3) 객체에 저장된 데이터를 파일에 저장(출력) => 파일용 기반 스트림 사용
	 * 		properties 형식 : store(OutputStream) --> 설정들을 저장하기 위한 용도
	 * 		xml 형식 : storeToXML(OutputStream) --> SQL문들을 저장하기 위한 용도
	 */
	private static void saveProp() {
		Properties prop = new Properties();
		
		// 샘플 데이터 추가
		prop.setProperty("C", "INSERT");
		prop.setProperty("R", "SELECT");
		prop.setProperty("U", "UPDATE");
		prop.setProperty("D", "DELETE");
		
		// properties 형식
		try {
			prop.store(new FileOutputStream("test.properties"), "Properties Test");
			// key=value 형태로 파일에 저장됨
			
			// xml 형식
			prop.storeToXML(new FileOutputStream("test.xml"), "XML Test");
			// <entry key="key">value</entry>
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
