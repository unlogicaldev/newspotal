package org.unlogical.dev.demo.news.common.mongo;

import java.net.UnknownHostException;

import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongoTemplate {
	
	private static DB db;	
	private static Mongo mongo;	
	private static Morphia morphia;
			 
	/**
	 * @param dbName
	 * @param username
	 * @param password
	 * @param host
	 * @param port
	 * @param autoConnectRetry
	 * @param connectionsPerHost
	 * @param connectTimeout
	 * @param maxWaitTime
	 * @param threadsAllowedToBlockForConnectionMultiplier
	 * @param socketTimeout
	 */
	public MongoTemplate(String dbName,
						  String username, 
						  String password,
						  String host, 
						  int port, 
						  boolean autoConnectRetry, 
						  int connectionsPerHost, 
						  int connectTimeout, 
						  int maxWaitTime, 
						  int threadsAllowedToBlockForConnectionMultiplier, 
						  int socketTimeout) {
			
		if(mongo == null){
			MongoOptions options = new MongoOptions();
			options.autoConnectRetry = autoConnectRetry;
			options.connectionsPerHost = connectionsPerHost;
			options.connectTimeout = connectTimeout;
			options.maxWaitTime = maxWaitTime;
			options.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
			options.socketTimeout = socketTimeout;	
			try {
				ServerAddress addr = new ServerAddress(host,port);
				mongo = new Mongo(addr,options);
				db = mongo.getDB(dbName);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			morphia = new Morphia().mapPackage("org.unlogical.dev.demo.news.web.model.entity");
		}
	}
	
	/**
	 * @param collectionName
	 * @return
	 */
	public static DBCollection getDBCollection(String collectionName){
		return db.getCollection(collectionName);
	}
	
	/**
	 * @return morphia
	 */
	public static Morphia getMorphia(){
		return morphia;
	}
}
