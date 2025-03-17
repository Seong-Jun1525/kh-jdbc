package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Game;

public class GameDAO {
	Properties prop = new Properties();
	
	public GameDAO() {
		try {
			
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public ArrayList<Game> selectAllGame(Connection conn) {
		ArrayList<Game> gList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			 pstmt = conn.prepareStatement(prop.getProperty("selectAll"));
			 rset = pstmt.executeQuery();
			
			while(rset.next()) {
				gList.add(new Game(
							rset.getString("GAME_ID"),
							rset.getString("TITLE"),
							rset.getString("GENRE"),
							rset.getString("PLATFORM"),
							rset.getString("RELEASE_DATE")
						));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return gList;
	}


	public int addGame(Connection conn, Game game) {
		PreparedStatement pstmt = null;
		int r = 0;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("insertGame"));
			pstmt.setString(1, game.getTitle());
			pstmt.setString(2, game.getGenre());
			pstmt.setString(3, game.getPlatform());
			pstmt.setString(4, game.getReleaseDate());
			
			r = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return r;
	}


	public int updateGame(Connection conn, Game g) {
		PreparedStatement pstmt = null;
		int r = 0;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("updateGame"));
			pstmt.setString(1, g.getTitle());
			pstmt.setString(2, g.getGenre());
			pstmt.setString(3, g.getPlatform());
			pstmt.setString(4, g.getReleaseDate());
			pstmt.setString(5, g.getGameId());
			
			r = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return r;
	}


	public int deleteGame(Connection conn, String gameID) {
		PreparedStatement pstmt = null;
		int r = 0;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("deleteGame"));
			pstmt.setString(1, gameID);
			
			r = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return r;
	}


	public ArrayList<Game> searchByGame(Connection conn, String title) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Game> gList = new ArrayList<Game>();
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("searchByTitle"));
			pstmt.setString(1, title);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				gList.add(new Game(
						rset.getString("GAME_ID"),
						rset.getString("TITLE"),
						rset.getString("GENRE"),
						rset.getString("PLATFORM"),
						rset.getString("RELEASE_DATE")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return gList;
	}

}
