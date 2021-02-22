package com.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerBatting {
	@JsonProperty("Pid")
	int pid;
	

	@JsonProperty("Name")
	String playerName;
	 @JsonProperty("Matches")
	 int match;
	 @JsonProperty("Runs")
	 int score;
	 @JsonProperty("Highest")
	 int highestScore;
	 @JsonProperty("Fifties")
	 int numberofFifty;
	 @JsonProperty("Hundreds")
	 int numberofHunderd;
	
	
	public PlayerBatting() {
		super();
		this.pid=0;
		this.playerName="";
		this.score=0;
		this.match=0;
		this.highestScore=0;
		this.numberofFifty=0;
		this.numberofHunderd=0;
	}
	 public int getPid() {
			return pid;
		}


		public void setPid(int pid) {
			this.pid = pid;
		}


	public String getPlayerName() {
		return playerName;
	}


	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public int getMatch() {
		return match;
	}


	public void setMatch(int match) {
		this.match = match;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public int getHighestScore() {
		return highestScore;
	}


	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}


	public int getNumberofFifty() {
		return numberofFifty;
	}


	public void setNumberofFifty(int numberofFifty) {
		this.numberofFifty = numberofFifty;
	}


	public int getNumberofHunderd() {
		return numberofHunderd;
	}


	public void setNumberofHunderd(int numberofHunderd) {
		this.numberofHunderd = numberofHunderd;
	}


	public PlayerBatting(int pid,String playerName, int match, int score, int highestScore, int numberofFifty,
			int numberofHunderd) {
		super();
		this.pid=pid;
		this.playerName = playerName;
		this.match = match;
		this.score = score;
		this.highestScore = highestScore;
		this.numberofFifty = numberofFifty;
		this.numberofHunderd = numberofHunderd;
	}

}
