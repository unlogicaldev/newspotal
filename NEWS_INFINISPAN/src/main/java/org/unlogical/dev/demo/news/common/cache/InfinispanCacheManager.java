package org.unlogical.dev.demo.news.common.cache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCacheManager;

public class InfinispanCacheManager {

	private static RemoteCacheManager rm;
	private static Cache<String, Map<String,Object>> userCache;
	private static Cache<String, List<Map<String,Object>>> newsCache;
	
	public static void setSessionCache(Map<String,Object> map){
		init();
		userCache.put((String) map.get("userId"), map);
	}
	
	public static Map<String,Object> getSessionCache(String key){
		init();
		return userCache.get(key);
	}
	
	public static void removeSessioinCache(String key){
		init();
		userCache.remove(key);
	}
	
	public static void setNewsListCache(String category, List<Map<String,Object>> list){
		init();
		newsCache.put(category, list);
	}
	
	public static List<Map<String,Object>> getNewsListCache(String category){
		init();
		return newsCache.get(category);
	}

	private static void init(){
		if(rm == null){
			connectCache(propertiesSetting());
		}
	}
	
	/**
	 * properties setting
	 * @return
	 */
	private static Properties propertiesSetting() {
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream("/Users/skynle/git/newspotal/NEWS_INFINISPAN/src/test/java/org/unlogical/dev/demo/news/batch/hotrod-client.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pro;
	}
	


	/**
	 * connect
	 * @param pro
	 */
	private static void connectCache(Properties pro) {
		try{
			rm = new RemoteCacheManager(pro);
			userCache = rm.getCache("USER_INFO");
			newsCache = rm.getCache("NEWS_LIST");
		}catch (Exception e) {
	        e.printStackTrace();
	        String ser = e.getCause().getMessage().split("/")[1].trim();
	        Properties pros;

        	pros = rm.getProperties();
        	System.out.println("@@@@@"+pros.toString());
	        if(ser.indexOf("127.0.0.1") > -1) pros = propertiesSetting();
	        else{  
	        	pros = rm.getProperties();
		        String servers = pros.getProperty("infinispan.client.hotrod.server_list");
		        if(servers.indexOf(";") > -1) ser = ";"+ser;
		        pros.setProperty("infinispan.client.hotrod.server_list", servers.replaceAll(ser, ""));
	        }
	        connectCache(pros);
	    }
	}
}
