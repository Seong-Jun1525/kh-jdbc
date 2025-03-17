package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.BookController;
import com.kh.model.vo.Book;

public class BookMenu {
	
	private Scanner sc = new Scanner(System.in);
	private BookController bc = new BookController();

	public BookMenu() {}

	public void mainMenu() {
		while(true) {
			System.out.println("1. 전체조회");
			System.out.println("2. 도서추가");
			System.out.println("3. 도서수정");
			System.out.println("4. 도서삭제");
			System.out.println("5. 도서검색");
			System.out.println("9. 프로그램 종료");
			
			System.out.print("메뉴 번호 : ");
			int num = sc.nextInt();
			sc.nextLine();
			
			switch(num) {
			case 1:
				bc.selectAllBook();
				break;
			case 2:
				addBookMenu();
				break;
			case 3:
				updateBookMenu();
				break;
			case 4:
				deleteBookMenu();
				break;
			case 5:
				searchBookMenu();
				break;
			case 9:
				return;
			}
		}
	}

	private void searchBookMenu() {
		System.out.println("도서 검색");
		System.out.print("검색할 도서명 : ");
		String title = sc.nextLine();
		
		bc.selectBookByTitle(title);
	}

	private void deleteBookMenu() {
		System.out.println("도서 삭제");
		System.out.print("삭제할 도서 아이디 : ");
		int bookId = sc.nextInt();
		
		bc.deleteBookById(bookId);
	}

	private void updateBookMenu() {
		System.out.println("도서 변경");
		System.out.print("변경할 도서 아이디 : ");
		int bookId = sc.nextInt();
		sc.nextLine();
		
		System.out.print("변경할 도서 제목 : ");
		String title = sc.nextLine();
		
		System.out.print("변경할 도서 작가 : ");
		String author = sc.nextLine();
		
		System.out.print("변경할 도서 출판사 : ");
		String publisher = sc.nextLine();
		
		System.out.print("변경할 도서 가격 : ");
		int price = sc.nextInt();
		
		bc.updateBook(bookId, title, author, publisher, price);
	}

	private void addBookMenu() {
		System.out.println("도서 추가");
		System.out.print("추가할 도서 제목 : ");
		String title = sc.nextLine();
		
		System.out.print("추가할 도서 작가 : ");
		String author = sc.nextLine();
		
		System.out.print("추가할 도서 출판사 : ");
		String publisher = sc.nextLine();
		
		System.out.print("추가할 도서 가격 : ");
		int price = sc.nextInt();
		
		bc.addBook(title, author, publisher, price);
	}

	public void displayNoData(String string) {
		System.out.println(string);
	}

	public void displayData(ArrayList<Book> bList) {
		for(Book b : bList) System.out.println(b);
	}

	public void displaySuccess(String string) {
		System.out.println(string);
	}

	public void displayFailed(String string) {
		System.out.println(string);
	}

}
