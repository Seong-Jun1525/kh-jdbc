package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.BookService;
import com.kh.model.vo.Book;
import com.kh.view.BookMenu;

public class BookController {
	private BookService bs = new BookService();

	public BookController() { }

	public void selectAllBook() {
		ArrayList<Book> bList = bs.selectAllBook();
		
		if(bList.isEmpty()) new BookMenu().displayNoData("저장된 데이터가 없습니다.");
		else new BookMenu().displayData(bList);
	}

	public void addBook(String title, String author, String publisher, int price) {
		Book b = new Book(title, author, publisher, price);
		
		int r = bs.insertBook(b);
		
		if(r > 0) new BookMenu().displaySuccess("책 데이터 추가 성공");
		else new BookMenu().displayFailed("책 데이터 추가 실패");
	}

	public void updateBook(int bookId, String title, String author, String publisher, int price) {
		Book b = new Book(bookId, title, author, publisher, price);
		
		int r = bs.updateBook(b);

		if(r > 0) new BookMenu().displaySuccess("책 데이터 변경 성공");
		else new BookMenu().displayFailed("책 데이터 변경 실패");
	}

	public void deleteBookById(int bookId) {
		int r = bs.deleteBookById(bookId);

		if(r > 0) new BookMenu().displaySuccess("책 데이터 삭제 성공");
		else new BookMenu().displayFailed("책 데이터 삭제 실패");
	}

	public void selectBookByTitle(String title) {
		ArrayList<Book> bList = bs.selectBookByTitle(title);
		
		if(bList.isEmpty()) new BookMenu().displayNoData("저장된 데이터가 없습니다.");
		else new BookMenu().displayData(bList);
		
	}

}
