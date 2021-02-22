package com.DAO;

import java.util.ArrayList;

import com.POJO.Player;
import com.POJO.PlayerBatting;

public interface IplayerDAO {
	public boolean addPlayer(Player p);
	public ArrayList<Player> getPlayer();
	public boolean addPlayerBatting(PlayerBatting pb);
	public ArrayList<PlayerBatting> getPlayerBatting();
}
