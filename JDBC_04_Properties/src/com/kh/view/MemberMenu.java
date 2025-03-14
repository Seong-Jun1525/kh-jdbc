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
			System.out.println("4. 키워드가 포함된 아이디 검색");
			System.out.println("5. 회원 정보 수정");
			System.out.println("6. 회원 탈퇴");
			System.out.println("7. 회원 전체 삭제");
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
				mc.selectAll();
				break;
			case 3:
				// 아이디로 검색 기능
				searchByIdMenu();
				break;
			case 4:
				// 입력받은 아이디가 포함된 모든 회원 데이터 검색 기능
				searchByKeywordMenu();
				break;
			case 5:
				// 수정 기능
				updateMemberMenu();
				break;
			case 6:
				// 탈퇴 기능
				deleteMemberMenu();
				break;
			case 7:
				// 회원데이터 전체 삭제
				deleteMemberAllMenu();
				break;
			case 9:
				System.out.println("프로그램이 종료됩니다.");
				return;
			}
		}
	}
	
	private void deleteMemberAllMenu() {
		System.out.print("정말 전체 삭제 하시겠습니까? (Y/N) : ");
		char answer = sc.nextLine().toLowerCase().charAt(0);
		
		if(answer == 'y') mc.deleteMemberAll();
		else System.out.println("취소합니다.");
	}

	private void searchByKeywordMenu() {
		System.out.print("조회할 아이디 입력 : ");
		String keyword = sc.nextLine();
		
		mc.searchKeyword(keyword);
	}

	/**
	 * 회원 아이디를 기준으로 회원 정보를 삭제
	 */
	private void deleteMemberMenu() {
		System.out.print("- 회원 아이디 : ");
		String id = sc.nextLine();
		
		mc.deleteMember(id);
	}

	/**
	 * 회원 아이디를 기준으로 회원 정보를 수정
	 */
	private void updateMemberMenu() {
		System.out.print("- 회원 아이디 : ");
		String id = sc.nextLine();
		
		System.out.print("- 변경할 비밀번호 : ");
		String pw = sc.nextLine();
		
		System.out.print("- 변경할 성별(M / F) : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);

		System.out.print("- 변경할 이메일 : ");
		String email = sc.nextLine();

		System.out.print("- 변경할 연락처(- 포함) : ");
		String phone = sc.nextLine();
		
		System.out.print("- 변경할 주소 : ");
		String addr = sc.nextLine();
		
		System.out.print("- 변경할 취미 : ");
		String hobby = sc.nextLine();
		
		mc.updateMember(id, pw, gender+"", email, phone, addr, hobby);
	}
	
	/** 참고하기!
	 * 성별을 입력받는 메소드
	 * @return 입력된 성별 정보
	 */
	private String inputGender() {
		System.out.print("- 변경할 성별(M/F) : ");
		String gender = sc.nextLine();
		
		while(!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F")) {
			System.out.println("※ 성별은 M 또는 F만 입력 가능합니다. 다시 입력해주세요. ※");
			System.out.print("- 변경할 성별(M/F) : ");
			gender = sc.nextLine();
		}
		
		return gender.toUpperCase();
	}

	private void searchByIdMenu() {
		System.out.print("조회할 아이디 입력 : ");
		String searchId = sc.nextLine();
		
		mc.searchById(searchId);
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
		mc.insertMember(id, pw, gender+"", age);
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

	public void displayMember(Member m) {
		System.out.println("=".repeat(5) + " 조회된 회원 정보 " + "=".repeat(5));
		System.out.println(m);
	}
}
