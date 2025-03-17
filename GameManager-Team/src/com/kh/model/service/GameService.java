package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.GameDAO;
import com.kh.model.vo.Game;

public class GameService {
	private Connection conn = null;

	public GameService() {}

	public ArrayList<Game> selectAll() {
		conn = JDBCTemplate.getConnection();
		
		ArrayList<Game> list = new GameDAO().selectAllGame(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	public void addGame(Game game) {
		conn = JDBCTemplate.getConnection();
		int r = new GameDAO().addGame(conn, game);
		
		if(r > 0) {
			System.out.println("커밋 성공");
			JDBCTemplate.commit(conn);
		}
		else {
			System.out.println("커밋 실패");
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
	}

	public void updateGame(String gameID, String title, String genre, String platform, String releaseDate) {
		conn = JDBCTemplate.getConnection();
		Game g = new Game(gameID, title, genre, platform, releaseDate);
		int r = new GameDAO().updateGame(conn, g);
		
		if(r > 0) {
			System.out.println("커밋 성공");
			JDBCTemplate.commit(conn);
		}
		else {
			System.out.println("커밋 실패");
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
	}

	public void deleteGame(String gameID) {
		conn = JDBCTemplate.getConnection();
		int r = new GameDAO().deleteGame(conn, gameID);
		
		if(r > 0) {
			System.out.println("커밋 성공");
			JDBCTemplate.commit(conn);
		}
		else {
			System.out.println("커밋 실패");
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);
	}

	public ArrayList<Game> searchByGame(String title) {
		conn = JDBCTemplate.getConnection();
		
		ArrayList<Game> gList = new GameDAO().searchByGame(conn, title);
		
		JDBCTemplate.close(conn);
		
		return gList;
	}

}
