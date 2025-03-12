package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

/**
 * View
 * - 사용자가 보게될 시각적인 요소(화면)
 * - 출력 또는 입력
 *
 */

public class MemberMenu {
	private MemberController mc = new MemberController();
	private Scanner sc = new Scanner(System.in);
	
	public MemberMenu() {}
	
	/**
	 * 메인 메뉴 : 사용자가 보게 될 첫 화면
	 */
	public void mainMenu() {
		while(true) {
			System.out.println("=".repeat(5) + " 회원관리 " + "=".repeat(5));
			System.out.println("1. 회원 추가");
			System.out.println("2. 전체 회원 조회");
			System.out.println("3. 회원 아이디로 검색");
			System.out.println("4. 회원 정보 수정");
			System.out.println("5. 회원 탈퇴");
			System.out.println("9. 종료");
			
			System.out.print(">> 메뉴 번호 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1:
				// 추가 관련 기능
				addMember();
				break;
			case 2:
				// 전체 조회 기능
				// TODO: Controller 객체에게 전체 회원 조회 요청
				mc.selectAll();
				break;
			case 3:
				// 아이디로 검색 기능
				break;
			case 4:
				// 수정 기능
				break;
			case 5:
				// 탈퇴 기능
				break;
			case 9:
				System.out.println("프로그램이 종료됩니다.");
				return;
			}
		}
	}

	/**
	 * 전체 회원 정보 출력
	 * 
	 * @param list
	 */
	public void displayAllMembers(ArrayList<Member> list) {
		// 전체 회원 조회 메뉴
		System.out.println("=".repeat(5) + " 조회된 회원 목록 " + "=".repeat(5));
		
		for(Member m : list) System.out.println(m);
	}

	/**
	 * 조회된 결과가 없을 경우 출력
	 */
	public void displayNoData() {
		System.out.println("=".repeat(5) + " 조회된 회원 정보가 없습니다. " + "=".repeat(5));
	}

	private void addMember() {
		/** 회원 추가 메뉴
		 * 아이디, 비밀번호, 성별, 나이를 입력받아 회원 등록
		 */
		System.out.print("- 아이디 : ");
		String id = sc.nextLine();
		
		System.out.print("- 비밀번호 : ");
		String pw = sc.nextLine();
		
		System.out.print("- 성별(M / F) : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);

		System.out.print("- 나이 : ");
		int age = sc.nextInt();
		sc.nextLine();
		
		// 회원 추가 요청 --> Controller 객체에게 id, pw, gender, age 변수의 값 전달
		mc.insertMember(id, pw, gender, age);
	}
	
	/**
	 * 요청에 대한 성공 시 결과 출력
	 * @param message		결과 메시지
	 */
	public void displaySuccess(String message) {
		System.out.println("* 서비스 요청 성공 : " + message);
	}
	
	/**
	 * 요청에 대한 실패 시 결과 출력
	 * @param message		결과 메시지
	 */
	public void displayFailed(String message) {
		System.out.println("* 서비스 요청 실패 : " + message);
	}
}
