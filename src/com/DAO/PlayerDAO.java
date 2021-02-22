package com.DAO;


import com.POJO.Player;
import com.POJO.PlayerBatting;
import com.POJO.PlayerBowling;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.security.*;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

import com.MongoDB.*;
 
public class PlayerDAO implements IplayerDAO{
	
	public final String db_name= "Players";
	public final String db_collection_player= "Player";
	public final String db_collection_player_batting= "PlayerBatting";
	public final String db_collection_player_bowling= "PlayerBowling";
	PasswordEncrypt passwordencrypt;
	
	@Override
	
	public boolean addPlayer(Player p) {
		// TODO Auto-generated method stub
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection_player);
		DBCollection coll1 = MongoFactory.getCollection(db_name, db_collection_player_batting);
		DBCollection coll2 = MongoFactory.getCollection(db_name, db_collection_player_bowling);
		boolean isInserted =false,isValidation=true;
		System.out.println("Inside PlayerDao addPlayer1");
		
		int pid;
		String pname;
		String pemail;
		String ppass;
		String encryptedpass;
		String salt="";

		
			pid=p.getPid();
			pemail=p.getPlayerEmail();
			ppass=p.getPassword();
			pname=p.getPlayerName();
			System.out.println(ppass);
			if ( passwordencrypt == null)
				passwordencrypt=new PasswordEncrypt();
			//salt=passwordencrypt.getSalt(pid);
			salt=String.valueOf(pid);
			//System.out.println(salt);
			encryptedpass=passwordencrypt.generateSecurePassword(ppass, salt);
			System.out.println(encryptedpass);
			ppass=encryptedpass;
			//DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
			BasicDBObject query = new BasicDBObject();
			/* Set clause */
			//System.out.println(eid + " "+ ename + " " + esalary);
			BasicDBObject query1=  new BasicDBObject();
			BasicDBObject query2=  new BasicDBObject();
			BasicDBObject query3=  new BasicDBObject();
			if(p.getPid() != 0 && p.getPlayerEmail() != null && p.getPassword() != null & p.getPlayerName() != null)
				{
					
					query1.append("id", pid);
					query1.append("name", pname);
					query1.append("id", pid);
					query1.append("email",  String.valueOf(pemail));
					query1.append("password", String.valueOf(ppass));
					System.out.println(query1);
					isValidation=true;
					//System.out.println("Inside PlayerDao addPlayer2");
					query2.append("Pid", pid);
					query2.append("Name", pname);
					query2.append("Matches", 0);
					query2.append("Runs", 0);
					query2.append("Highest", 0);
					query2.append("Fifties", 0);
					query2.append("Hundreds", 0);
					
					query3.append("Pid", pid);
					query3.append("Name", pname);
					query3.append("Matches", 0);
					query3.append("Wickets", 0);
					query3.append("HighestWickets", 0);
				}
			try {
			if(isValidation == true) {

			WriteConcern wr=new WriteConcern(1);
		    //WriteResult  wr1=coll.insert(query);
		  // coll.update(query, update, upsert, multi, aWriteConcern)
		    coll.insert(query1);
		    coll1.insert(query2);
		    coll2.insert(query3);
		    System.out.println(wr.isAcknowledged());
		    
		    //System.out.println(wr1.isUpdateOfExisting());	
			
			//System.out.println(wr1);
		    
		    isInserted=true;
			//System.out.println("Inside PlayerDao addPlayer3");

		}
			}catch(Exception ex) {
			isInserted=false;
		}
	return isInserted;	

	}
	
	public ArrayList<Player> getPlayer(){
		ArrayList<Player> plist= new ArrayList<Player>();
		DBObject dbobject;
		Player player;
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection_player);
		DBCursor cursor=coll.find();
		try {
		while(cursor.hasNext()) {
			dbobject=cursor.next();
			player=new Player();
			player.setPlayerEmail(dbobject.get("email").toString());
			player.setPid(Integer.parseInt(dbobject.get("id").toString()));
			player.setPassword(dbobject.get("password").toString());
			plist.add(player);
		}
		return plist;
		}catch(Exception e) {
			plist=null;
			return plist;
		}
	}
	/* Player Batting  */
	public boolean addPlayerBatting(PlayerBatting pb) {
		boolean isInserted=false;
		int id=pb.getPid();
		String playerName=pb.getPlayerName();
		int match=pb.getMatch();
		int score=pb.getScore();
		int highestScore=pb.getHighestScore();
		int numberofFifty=pb.getNumberofFifty();
		int numberofHunderd=pb.getNumberofHunderd();
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection_player_batting);
		BasicDBObject query=new BasicDBObject();
		query.append("Pid", id);
		query.append("Name", playerName);
		query.append("Matches", match);
		query.append("Runs", score);
		query.append("Highest", highestScore);
		query.append("Fifties", numberofFifty);
		query.append("Hundreds", numberofHunderd);
		System.out.println(query);
		WriteConcern wr=new WriteConcern(1);
	 try {
	    coll.insert(query);
	    System.out.println(wr.isAcknowledged());
	    
	 
	    isInserted=true;
	 }catch(Exception e) {
		 isInserted=false;
		 return isInserted;
	 }
	 return isInserted;
	}

 /* Player Batting  */
	public ArrayList<PlayerBatting> getPlayerBatting(){
		
		PlayerBatting pb;
		ArrayList<PlayerBatting> pblist=new ArrayList<PlayerBatting>();
		DBCollection coll=MongoFactory.getCollection(db_name, db_collection_player_batting);
		try {
		DBCursor cursor=coll.find();
		DBObject dbobject;
		while(cursor.hasNext()) {
			dbobject=cursor.next();
			pb=new PlayerBatting();
			pb.setPid(Integer.parseInt(dbobject.get("Pid").toString()));
			pb.setPlayerName(dbobject.get("Name").toString());
			pb.setMatch(Integer.parseInt(dbobject.get("Matches").toString()));
			pb.setScore(Integer.parseInt(dbobject.get("Runs").toString()));
			pb.setHighestScore(Integer.parseInt(dbobject.get("Highest").toString()));
			pb.setNumberofFifty(Integer.parseInt(dbobject.get("Fifties").toString()));
			pb.setNumberofHunderd(Integer.parseInt(dbobject.get("Hundreds").toString()));
			pblist.add(pb);
			}
		
		}catch(Exception ex) {
			pblist=null;
			return pblist;
		}
		return pblist;
	}
	/* Player Bowling  */
	public boolean addPlayerBowling(PlayerBowling pb) {
		boolean isInserted=false;
		int id=pb.getPid();
		String playerName=pb.getplayerName();
		int match=pb.getMatches();
		int wickets=pb.getWickets();
		int highestWickets=pb.getHighestWicket();
		
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection_player_bowling);
		BasicDBObject query=new BasicDBObject();
		query.append("Pid", id);
		query.append("Name", playerName);
		query.append("Matches", match);
		query.append("Wickets", wickets);
		query.append("HighestWickets", highestWickets);
		
		System.out.println(query);
		WriteConcern wr=new WriteConcern(1);
	 try {
	    coll.insert(query);
	    System.out.println(wr.isAcknowledged());
	    
	 
	    isInserted=true;
	 }catch(Exception e) {
		 isInserted=false;
		 return isInserted;
	 }
	 return isInserted;
	}
	public ArrayList<PlayerBowling> getPlayerBowling(){
		
		PlayerBowling pb;
		ArrayList<PlayerBowling> pblist=new ArrayList<PlayerBowling>();
		//MongoClientURI conn=MongoFactory.getMongoClient();
		//MongoCollection coll=MongoFactory.getCollection(db_collection_player_bowling);
		DBCollection coll=MongoFactory.getCollection(db_name, db_collection_player_bowling);
		try {
		DBCursor cursor=coll.find();

		DBObject dbobject;
		while(cursor.hasNext()) {
			dbobject=cursor.next();
			pb=new PlayerBowling();
			pb.setPid(Integer.parseInt(dbobject.get("Pid").toString()));
			pb.setplayerName(dbobject.get("Name").toString());
			pb.setMatches(Integer.parseInt(dbobject.get("Matches").toString()));
			pb.setWickets(Integer.parseInt(dbobject.get("Wickets").toString()));
			pb.setHighestWicket(Integer.parseInt(dbobject.get("HighestWickets").toString()));
			pblist.add(pb);
			}
		
		}catch(Exception ex) {
			pblist=null;
			return pblist;
		}
		return pblist;
	}
/*public ArrayList<PlayerBowling> getPlayerBowling(){
		
		PlayerBowling pb;
		ArrayList<PlayerBowling> pblist=new ArrayList<PlayerBowling>();
		MongoClientURI conn=MongoFactory.getMongoClient();
		MongoCollection<PlayerBowling> coll=MongoFactory.getCollection(db_collection_player_bowling,PlayerBowling.class);
		
		try {
	
	   pblist.addAll((Collection<? extends PlayerBowling>) coll);
	
		
		}catch(Exception ex) {
			pblist=null;
			return pblist;
		}
		return pblist;
}*/
}
