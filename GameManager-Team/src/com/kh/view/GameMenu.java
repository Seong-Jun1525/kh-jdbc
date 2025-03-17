package com.kh.view;

import java.util.Scanner;

import com.kh.controller.GameController;


public class GameMenu {
	private Scanner sc =new Scanner(System.in);
	private GameController gc = new GameController();
	
	public void mainMenu() {
		while(true) {
			System.out.println("1. 전체 조회");
			System.out.println("2. 게임 추가");
			System.out.println("3. 게임 수정(게임 id로 조회 후 수정)");
			System.out.println("4. 게임 삭제(게임 id로 조회 후 삭제)");
			System.out.println("5. 게임 검색(게임 이름으로 키워드 검색)");
			System.out.println("9. 프로그램 종료");
			System.out.print("번호 입력: ");
			int num  = sc.nextInt();
			sc.nextLine();
			
			switch(num) {
			case 1: 
				selectAll();
				break;
			case 2: 
				addGame();
				break;
			case 3: 
				updateGame();
				break;
			case 4: 
				deleteGame();
				break;
			case 5: 
				searchGame();
				break;
			case 9:
				System.out.println("프로그램을 종료합니다.");
				return;
			}
		}
	}
	private void searchGame() {
		System.out.println("게임검색");
		System.out.print("검색할 게임명 : ");
		String title = sc.nextLine();
		
		gc.searchByGame(title);
	}
	private void deleteGame() {
		System.out.println("게임삭제");
		System.out.print("삭제할 게임 ID : ");
		String gameID = sc.nextLine();
		
		gc.deleteGame(gameID);
	}
	private void updateGame() {
		System.out.println("게임수정");
		System.out.print("수정할 게임 ID : ");
		String gameID = sc.nextLine();
		
		System.out.print("수정할 게임명 : ");
		String title = sc.nextLine();

		System.out.print("수정할 장르 : ");
		String genre = sc.nextLine();

		System.out.print("수정할 플랫폼 : ");
		String platform = sc.nextLine();

		System.out.print("수정할 출시일 : ");
		String releaseDate = sc.nextLine();
		
		gc.updateGame(gameID, title, genre, platform, releaseDate);
		
	}
	private void addGame() {
		System.out.println("게임추가");
		
		System.out.print("게임명 : ");
		String title = sc.nextLine();

		System.out.print("장르 : ");
		String genre = sc.nextLine();

		System.out.print("플랫폼 : ");
		String platform = sc.nextLine();

		System.out.print("출시일 : ");
		String releaseDate = sc.nextLine();
		
		gc.addGame(title, genre, platform, releaseDate);
	}
	private void selectAll() {
		System.out.println("전체회원목록");
		gc.selectAll();
	}
	
	
}
