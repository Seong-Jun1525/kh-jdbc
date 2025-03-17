package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.GameService;
import com.kh.model.vo.Game;

public class GameController {
	private GameService gs = new GameService();
	
	public GameController() {}

	public void selectAll() {
		ArrayList<Game> gList = gs.selectAll(); 
		
		if(gList == null) System.out.println("게임이 없습니다.");
		else for(Game g : gList) System.out.println(g);
	}

	public void addGame(String title, String genre, String platform, String releaseDate) {
		Game game = new Game(title, genre, platform, releaseDate);
		
		gs.addGame(game);
	}

	public void updateGame(String gameID, String title, String genre, String platform, String releaseDate) {
		System.out.println("수정할 데이터");
		
		
		gs.updateGame(gameID, title, genre, platform, releaseDate);
	}

	public void deleteGame(String gameID) {
		gs.deleteGame(gameID);
	}

	public void searchByGame(String title) {
		ArrayList<Game> gList = gs.searchByGame(title);
		
		if(gList == null) System.out.println("게임이 없습니다.");
		else for(Game g : gList) System.out.println(g);
	}

}
