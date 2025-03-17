package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.BookDAO;
import com.kh.model.vo.Book;

public class BookService {

	private Connection conn = null;
	
	public BookService() {
		new JDBCTemplate();
	}

	public ArrayList<Book> selectAllBook() {
		conn = JDBCTemplate.getConnection();
		
		ArrayList<Book> bList = new BookDAO().selectAllBook(conn);
		
		JDBCTemplate.close(conn);
		
		return bList;
	}

	public int insertBook(Book b) {
		conn = JDBCTemplate.getConnection();
		
		int r = new BookDAO().insertBook(conn, b);
		
		if(r > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return r;
	}

	public int updateBook(Book b) {
		conn = JDBCTemplate.getConnection();
		
		int r = new BookDAO().updateBook(conn, b);
		
		if(r > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return r;
	}

	public int deleteBookById(int bookId) {
		conn = JDBCTemplate.getConnection();
		
		int r = new BookDAO().deleteBookById(conn, bookId);
		
		if(r > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return r;
	}

	public ArrayList<Book> selectBookByTitle(String title) {
		conn = JDBCTemplate.getConnection();
		
		ArrayList<Book> bList = new BookDAO().selectBookByTitle(conn, title);
		
		JDBCTemplate.close(conn);
		
		return bList;
	}

}
