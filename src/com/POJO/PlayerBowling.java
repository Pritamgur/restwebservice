package com.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PlayerBowling {
	@JsonProperty("Pid")
	int pid;
	@JsonProperty("Name")
	String playerName;
	@JsonProperty("Matches")
	int matches;
	@JsonProperty("Wickets")
	int wickets;
	@JsonProperty("HighestWickets")
	int highestWicket;
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getplayerName() {
		return playerName;
	}

	public void setplayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getMatches() {
		return matches;
	}

	public void setMatches(int matches) {
		this.matches = matches;
	}

	public int getWickets() {
		return wickets;
	}

	public void setWickets(int wickets) {
		this.wickets = wickets;
	}

	public int getHighestWicket() {
		return highestWicket;
	}

	public void setHighestWicket(int highestWicket) {
		this.highestWicket = highestWicket;
	}

	
	public PlayerBowling() {
		super();
		this.pid=0;
		this.playerName="";
		this.matches=0;
		this.highestWicket=0;
		this.wickets=0;
	}

	public PlayerBowling(int pid,String playerName, int matches, int wickets, int highestWicket) {
		super();
		this.pid=pid;
		this.playerName = playerName;
		this.matches = matches;
		this.wickets = wickets;
		this.highestWicket = highestWicket;
	}


}
