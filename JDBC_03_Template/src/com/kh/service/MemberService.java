package com.kh.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDAO;
import com.kh.model.vo.Member;

public class MemberService {
	
	/**
	 * [1] Connection 객체 생성
	 * 		- jdbc Driver 등록
	 * 		- Connection 객체 생성
	 * 
	 * [2] DML문 실행 시 트랜잭션 처리
	 * 		- commit, rollback
	 * 
	 * [3] Connection 객체 반납
	 * 		- close
	 */
	Connection conn = null;

	/**
	 * 회원 추가 기능에 대한 메서드
	 * 
	 * @param m 추가할 회원 정보
	 * @return 처리 결과
	 */
	public int insertMember(Member m) {
		int result = 0;
		
		// 1) Connection 객체 생성
		conn = JDBCTemplate.getConnection();

		// 2) DAO 객체에게 전달받은 데이터 및 생성한 Connection 객체 전달 후 결과 저장
		result = new MemberDAO().insertMember(conn, m);
//		System.out.println(result);
		
		// 3) 실행할 SQL문 확인 => (DML, INSERT) => 트랜잭션 처리
		if(result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return result;
	}
	
	public int updateMember(Member m) {
		int result = 0;
		
		conn = JDBCTemplate.getConnection();
		
		result = new MemberDAO().updateMember(conn, m);
		
		// 3) 실행할 SQL문 확인 => (DML, INSERT) => 트랜잭션 처리
		if(result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return result;
	}

	public int deleteMember(Member m) {
		int result = 0;
		
		conn = JDBCTemplate.getConnection();
		
		result = new MemberDAO().deleteMember(conn, m);
		
		// 3) 실행할 SQL문 확인 => (DML, INSERT) => 트랜잭션 처리
		if(result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return result;
	}

	public Member searchById(String searchId) {
		conn = JDBCTemplate.getConnection();
		
		Member m = new MemberDAO().searchMember(conn, searchId);
		
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return m;
	}

	public ArrayList<Member> selectAllList() {
		conn = JDBCTemplate.getConnection();
		
		ArrayList<Member> list = new MemberDAO().selectAllMember(conn);
		
		
		
		return list;
	}

	
	
}
