package com.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
	
	int pid;
	 @JsonProperty("name")
	String playerName;
	 public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	@JsonProperty("email")
	String playerEmail;
	 @JsonProperty("password")
	String password;
	
	public Player()
	{
		super();
		this.pid=0;
		this.playerName="";
		this.playerEmail="";
		this.password="";
	}
	public Player(int pid, String playerName, String playerEmail, String password) {
		super();
		this.pid = pid;
		this.playerName = playerName;
		this.playerEmail = playerEmail;
		this.password = password;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPlayerEmail() {
		return playerEmail;
	}
	public void setPlayerEmail(String playerEmail) {
		this.playerEmail = playerEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
