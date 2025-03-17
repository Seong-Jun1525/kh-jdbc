package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Book;

public class BookDAO {
	Properties prop = new Properties();
	
	public BookDAO() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Book> selectAllBook(Connection conn) {
		ArrayList<Book> bList = new ArrayList<Book>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("selectAllBook"));
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				bList.add(new Book(
							rset.getInt("BOOK_ID"),
							rset.getString("TITLE"),
							rset.getString("AUTHOR"),
							rset.getString("PUBLISHER"),
							rset.getInt("PRICE")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return bList;
	}

	public int insertBook(Connection conn, Book b) {
		int r = 0;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("insertBook"));
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getAuthor());
			pstmt.setString(3, b.getPublisher());
			pstmt.setInt(4, b.getPrice());
			
			r = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return r;
	}

	public int updateBook(Connection conn, Book b) {
		PreparedStatement pstmt = null;
		int r = 0;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("updateBook"));
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getAuthor());
			pstmt.setString(3, b.getPublisher());
			pstmt.setInt(4, b.getPrice());
			pstmt.setInt(5, b.getBookId());
			
			r = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return r;
	}

	public int deleteBookById(Connection conn, int bookId) {
		PreparedStatement pstmt = null;
		int r = 0;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("deleteBookById"));
			pstmt.setInt(1, bookId);
			
			r = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			JDBCTemplate.close(pstmt);
		}
		
		return r;
	}

	public ArrayList<Book> selectBookByTitle(Connection conn, String title) {
		ArrayList<Book> bList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("selectBookByTitle"));
			pstmt.setString(1, "%" + title + "%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				bList.add(new Book(
							rset.getInt("BOOK_ID"),
							rset.getString("TITLE"),
							rset.getString("AUTHOR"),
							rset.getString("PUBLISHER"),
							rset.getInt("PRICE")
						));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return bList;
	}

}
