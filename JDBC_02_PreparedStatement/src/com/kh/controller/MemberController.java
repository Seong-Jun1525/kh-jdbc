package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDAO;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

/**
 * Controller
 * - View로부터 사용자가 요청한 기능에 대해 처리하는 역할
 * - 전달받은 데이터를 가공처리한 후 DAO로 전달하여 호출
 * - DAO로부터 반환 받은 결과에 따라 성공인지, 실패인지 판단 후 응답
 */
public class MemberController {
	private MemberDAO mDAO = new MemberDAO();

	public MemberController() {}
	
	/**
	 * 회원 추가 요청을 처리할 메소드
	 * 
	 * @param id		신규회원아이디
	 * @param pw		신규회원비밀번호
	 * @param gender	성별
	 * @param age		나이
	 */
	public void insertMember(String id, String pw, char gender, int age) {
		// View로부터 전달받은 값들을 DAO에게 바로 전달하지 않고
		// Member 객체에 담아서 전달
		
		// Member 객체 생성
		// - 기본생성자로 생성 후 setter를 이용해 저장
		// - 매개변수가 있는 생성자를 사용하여 저장
		
		Member m = new Member(id, pw, gender, age);
		
		// DAO에게 신규회원정보(Member)를 전달하여 추가 요청
		int result = mDAO.insertMember(m);

		// 회원 추가 성공/실패 출력 (View를 이용)
		if(result > 0) new MemberMenu().displaySuccess("회원 추가 성공!!");
		else new MemberMenu().displayFailed("회원 추가 실패!!");
		
	}

	/**
	 * 전체 회원 정보 조회
	 */
	public void selectAll() {
		ArrayList<Member> list = mDAO.selectAllList();
		
		// * 조회된 결과에 따라 사용자에게 표시(출력)
		if(list.isEmpty()) new MemberMenu().displayNoData();
		else new MemberMenu().displayAllMembers(list);
	}

	/**
	 * 전달된 아이디에 해당하는 회원 정보 조회
	 * @param searchId 회원아이디
	 */
	public void searchById(String searchId) {
		Member m = mDAO.searchById(searchId);
		
		// 조회된 결과가 있는지 없는지에 따라 처리
		if(m == null) new MemberMenu().displayNoData();
		else new MemberMenu().displayMember(m);
	}

	/**
	 * 회원ID를 기준으로 특정 정보 수정 
	 * @param id		변경할 회원아이디
	 * @param pw		변경할 회원비밀번호
	 * @param gender	변경할 성별
	 * @param email 	변경할 이메일
	 * @param phone 	변경할 연락처
	 * @param addr		변경할 주소
	 * @param hobby		변경할 취미
	 */
	public void updateMember(String id, String pw, char gender, String email, String phone, String addr, String hobby) {
		// 전달 받은 데이터를 Member 객체에 담아 DAO에게 전달
		Member m = new Member(id, pw, gender, email, phone, addr, hobby);
		
		int result = mDAO.updateMember(m);
		
		if(result > 0) new MemberMenu().displaySuccess("회원 정보 수정 성공!!");
		else new MemberMenu().displayFailed("회원 정보 수정 실패!!");
	}

	/**
	 * 회원ID를 기준으로 특정 정보 삭제
	 * 
	 * @param id
	 */
	public void deleteMember(String id) {
		Member m = new Member(id);
		
		int result = mDAO.deleteMember(m);
		
		if(result > 0) new MemberMenu().displaySuccess("회원 정보 삭제 성공!!");
		else new MemberMenu().displayFailed("회원 정보 삭제 실패!!");
	}
}
