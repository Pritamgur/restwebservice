package com.controller;

import java.util.ArrayList;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.DAO.*;
import com.POJO.Player;
import com.POJO.PlayerBatting;
import com.POJO.PlayerBowling;
import com.security.PasswordEncrypt;

@RestController
public class APIController {

	public boolean playerExist = false;

	/*
	 * private IplayerDAO pdao; public APIController(IplayerDAO pdao) { super();
	 * this.pdao = pdao; }
	 */
	// @RequestMapping(value="/players/{id}",method=RequestMethod.GET,
	// produces=MediaType.APPLICATION_JSON_VALUE)
	// public ArrayList<>
	@RequestMapping(value="/players/login",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> loginPlayer(@RequestBody Player pl, HttpServletResponse response)
	{
		Player player = new Player();
		HttpHeaders httpheaders = new HttpHeaders();
		boolean isCorrectPass=false;
		int pid;
		String msg="";
		try {
		String email=pl.getPlayerEmail();
		String providedPassword=pl.getPassword();
		
		player= getPlayerByEmail(email);
		if(player==null) 
			throw new PlayerException();
		
		
		pid=player.getPid();
		PasswordEncrypt passwordencrypt=new PasswordEncrypt();
		String salt=String.valueOf(pid);
		System.out.println(pid);
		System.out.println(email);
		System.out.println(providedPassword);
		System.out.println(player.getPassword());

		isCorrectPass=passwordencrypt.verifyUserPassword(providedPassword, player.getPassword(),salt );
		
		System.out.println(isCorrectPass);
		
			if(player == null)
				throw new PlayerException();
			
			if(isCorrectPass == false)
			{
				msg="Incorrect Password";
				httpheaders.add("message", msg);
				httpheaders.add("pid", "0");
				return new ResponseEntity<Void>(httpheaders,HttpStatus.OK);
			}
			
		}
		catch(PlayerException e) {
			 msg = "No User Found";
			httpheaders.add("message", msg);
			httpheaders.add("pid", "0");
			return new ResponseEntity<Void>(httpheaders,HttpStatus.NOT_FOUND);
		}
		msg="Success";
		httpheaders.add("message", msg);
		httpheaders.add("pid", String.valueOf(pid));
		return new ResponseEntity<Void>(httpheaders,HttpStatus.OK);
		
	}
	@RequestMapping(value = "/players/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> registerPlayer(@RequestBody Player pl, HttpServletResponse response)
			throws PlayerException {
		Player player = new Player();
		HttpHeaders httpheaders = new HttpHeaders();
		Random random = new Random();
		PlayerDAO pdao = null;
		boolean isInserted = false;
		if (pl.getPid() == 0) {
			player.setPid(random.nextInt(100000) + 1);
		}
		System.out.println(player.getPid());
		String msg;
		try {
			if (getPlayerByEmail(pl.getPlayerEmail()) != null) {
				throw new PlayerException();
			}
			player.setPlayerName(pl.getPlayerName());
			player.setPlayerEmail(pl.getPlayerEmail());
			player.setPassword(pl.getPassword());

			if (pdao == null) {
				pdao = new PlayerDAO();
			}

			isInserted = pdao.addPlayer(player);
			System.out.println(isInserted);
			if (isInserted == false)
				throw new PlayerException();
		} catch (PlayerException e) {
			msg = "Player already added";
			// e.printStackTrace();
			httpheaders.add("Message", msg);
			httpheaders.add("pid","0");
			return new ResponseEntity<Void>(httpheaders, HttpStatus.FOUND);
		} catch (Exception e) {
			msg = "Server Error";
			e.printStackTrace();
			httpheaders.add("Message", msg);
			httpheaders.add("pid","0");
			return new ResponseEntity<Void>(httpheaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		msg = "player Added successfully with id " + player.getPid();
		// setting cookie //
		Cookie cookie =new Cookie("id", String.valueOf(player.getPid()));
		response.addCookie(cookie);
		httpheaders.add("Message", msg);
		httpheaders.add("pid", String.valueOf(player.getPid()));
		return new ResponseEntity<Void>(httpheaders, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/players", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Player>> getPlayers(@RequestHeader("pid")String pid ) {
		boolean getAll = true;
		Player player = new Player();
		ArrayList<Player> plist = null;
		
		if(pid == null)
			getAll=true;
		else
			getAll=false;
		
		PlayerDAO pdao = null;

		if (pdao == null)
			pdao = new PlayerDAO();

		try {
			plist = pdao.getPlayer();
			if(getAll == false) {
				for(Player p:plist) {
					if(p.getPid() == Integer.parseInt(pid))
					{
						plist.removeAll(plist);
						plist.add(p);
						break;
					}
				}
			}
			if (plist == null)
				throw new PlayerException();
		} catch (PlayerException e) {
			return new ResponseEntity<ArrayList<Player>>(plist, HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<ArrayList<Player>>(plist, HttpStatus.OK);
	}

	public Player getPlayerByEmail(String email) {
		ArrayList<Player> plist = null;
		PlayerDAO pdao = null;

		if (pdao == null)
			pdao = new PlayerDAO();
		plist = pdao.getPlayer();
		for (Player p : plist) {
			if (p.getPlayerEmail().equalsIgnoreCase(email))
				{
					System.out.println(p.getPassword());
					return p;
				}
		}
		return null;
	}

	// Player Batting Info Add Update Get 
	
	  @RequestMapping(value="/players/batting", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE) 
	 public ResponseEntity<Void> addPlayerBattingInfo(@RequestBody PlayerBatting playerbatting) { 
	//	 Get the login id from the cookie //
		
		
		 PlayerDAO pdao=null;
		 boolean isInserted=false;
		 HttpHeaders httpHeader=new HttpHeaders();
		 String msg="";
		 if (pdao == null)
			 pdao=new PlayerDAO();
		 try {
			 isInserted=pdao.addPlayerBatting(playerbatting);
			 isInserted = true;
			 msg= "Batting Added successfully";
		 }catch(Exception e) {
			 isInserted=false;
			 httpHeader.add("message", msg);
			 return new ResponseEntity<Void>(httpHeader, HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 httpHeader.add("message", msg);
		 return new ResponseEntity<Void>(httpHeader, HttpStatus.OK);
	}
	  
	  /* get the batting info*/
	 @RequestMapping(value="/players/batting", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<ArrayList<PlayerBatting>> getPlayerBatting(@RequestHeader("name") String name){
		 ArrayList<PlayerBatting> pblist=null;
		 ArrayList<PlayerBatting> pblist1=null;
		 PlayerBatting pb;
		 PlayerDAO pdao=null;
		 System.out.println("Request header is " + name);
		 HttpHeaders httpheader=new HttpHeaders();
		 String msg="";
		 if(pdao==null)
			 pdao=new PlayerDAO();
		 try {
			 pblist=pdao.getPlayerBatting();
			 
			if(pblist == null)
				throw new PlayerException();
			if(!name.isEmpty()) {
				//System.out.println(name.isEmpty());
				 pblist1=new ArrayList<PlayerBatting>();
				for(PlayerBatting p1:pblist) {
					 if(p1.getPlayerName().equalsIgnoreCase(name))
					 {
						
						 pblist1.add(p1);
					 }
				 }
				
				if(pblist1== null)
					throw new PlayerException();
			}
			
		 }catch(PlayerException e) {
			 msg="No Player in the list";
			 httpheader.add("message", msg);
			 return new ResponseEntity<ArrayList<PlayerBatting>>(pblist,httpheader,HttpStatus.NOT_FOUND);
		 }
		 
		 if(!name.isEmpty() && pblist1!=null) {
			 System.out.println(name);
			 msg="Success: Number of players returned "+ pblist1.size();
			 httpheader.add("message", msg);
			 return new ResponseEntity<ArrayList<PlayerBatting>>(pblist1,httpheader,HttpStatus.OK); 
		 }else {
		 //Return all player
		 msg="Success: Number of players returned "+ pblist.size();
		 httpheader.add("message", msg);
		 
		 return new ResponseEntity<ArrayList<PlayerBatting>>(pblist,httpheader,HttpStatus.OK);
		 }
	}
	 
	 //player bowling
	  @RequestMapping(value="/players/bowling", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE) 
		 public ResponseEntity<Void> addPlayerBowlingInfo(@RequestBody PlayerBowling playerbowling) { 
		//	 Get the login id from the cookie //
						
			 PlayerDAO pdao=null;
			 boolean isInserted=false;
			 HttpHeaders httpHeader=new HttpHeaders();
			 String msg="";
			 if (pdao == null)
				 pdao=new PlayerDAO();
			 try {
				 isInserted=pdao.addPlayerBowling(playerbowling);
				 isInserted = true;
				 msg= "Player Bowling Added successfully";
			 }catch(Exception e) {
				 isInserted=false;
				 httpHeader.add("message", msg);
				 return new ResponseEntity<Void>(httpHeader, HttpStatus.INTERNAL_SERVER_ERROR);
			 }
			 httpHeader.add("message", msg);
			 return new ResponseEntity<Void>(httpHeader, HttpStatus.OK);
		}
	  
	  @RequestMapping(value="/players/bowling", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
		 public ResponseEntity<ArrayList<PlayerBowling>> getPlayerBowling(@RequestHeader("name") String name){
			 ArrayList<PlayerBowling> pblist=null;
			 ArrayList<PlayerBowling> pblist1=null;
			 PlayerBowling pb;
			 PlayerDAO pdao=null;
			 System.out.println("Request header is " + name);
			 HttpHeaders httpheader=new HttpHeaders();
			 String msg="";
			 if(pdao==null)
				 pdao=new PlayerDAO();
			 try {
				 pblist=pdao.getPlayerBowling();
				
				if(pblist == null)
					throw new PlayerException();
				if(!name.isEmpty()) {
					//System.out.println(name.isEmpty());
					 pblist1=new ArrayList<PlayerBowling>();
					for(PlayerBowling p1:pblist) {
						 if(p1.getplayerName().equalsIgnoreCase(name))
						 {
							
							 pblist1.add(p1);
						 }
					 }
					
					if(pblist1== null)
						throw new PlayerException();
				}
				
			 }catch(PlayerException e) {
				 msg="No Player in the list";
				 httpheader.add("message", msg);
				 return new ResponseEntity<ArrayList<PlayerBowling>>(pblist,httpheader,HttpStatus.NOT_FOUND);
			 }
			 
			 if(!name.isEmpty() && pblist1!=null) {
				 System.out.println(name);
				 msg="Success: Number of players returned "+ pblist1.size();
				 httpheader.add("message", msg);
				 return new ResponseEntity<ArrayList<PlayerBowling>>(pblist1,httpheader,HttpStatus.OK); 
			 }else {
			 //Return all player
			 msg="Success: Number of players returned "+ pblist.size();
			 httpheader.add("message", msg);
			 
			 return new ResponseEntity<ArrayList<PlayerBowling>>(pblist,httpheader,HttpStatus.OK);
			 }
	  }
}
