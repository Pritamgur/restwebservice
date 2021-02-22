package com.MongoDB;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoFactory {
	private static Mongo mongo;
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	public final static String PASSWORD = "user";
	private MongoFactory() {
		
	}
	
	@SuppressWarnings("deprecation")
	public static Mongo getMango() {
		int port_no = 27017;
		System.out.println("Inside getMango");
		String hostname="localhost";
		if(mongo == null)
		{
			try {
				mongo=new Mongo(hostname , port_no);
			}catch(MongoException ex) {
				
			}
		}
	
	return mongo;
	}
	
	/*
	 * public static MongoClientURI getMongoClient() { MongoClientURI uri=null; try
	 * { System.setProperty("jdk.tls.trustNameService", "true"); uri = new
	 * MongoClientURI( "mongodb+srv://User:< "+PASSWORD+
	 * ">@cluster0.pguid.mongodb.net/Players?retryWrites=true&w=majority");
	 * System.out.println(uri); mongoClient = new MongoClient(uri); database =
	 * mongoClient.getDatabase("Players");
	 * 
	 * return uri; }catch(MongoException e) {
	 * System.out.println("Could not connect "+ e.toString()); } return uri; }
	 */
	/*
	 * public static MongoCollection getCollection(String db_collection,Class
	 * classname) { return database.getCollection(db_collection,classname); }
	 */
public static DB getDB(String db_name) {
	return getMango().getDB(db_name) ;
	
}

 public static DBCollection getCollection(String db_name,String db_collection) {
	 return getDB(db_name).getCollection(db_collection);
 }
}
