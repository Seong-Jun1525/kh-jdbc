package com.kh.model.vo;

public class Game {
	private String gameId;
	private String title;
	private String genre;
	private String platform;
	private String releaseDate;
	
	public Game() {}
	
	public Game(String gameId, String title, String genre, String platform, String releaseDate) {
		super();
		this.gameId = gameId;
		this.title = title;
		this.genre = genre;
		this.platform = platform;
		this.releaseDate = releaseDate;
	}
	
	public Game(String title, String genre, String platform, String releaseDate) {
		this.title = title;
		this.genre = genre;
		this.platform = platform;
		this.releaseDate = releaseDate;
	}

	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", title=" + title + ", genre=" + genre + ", platform=" + platform
				+ ", releaseDate=" + releaseDate + "]";
	}
}
