package org.unlogical.dev.demo.news.batch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.BaseTest;
import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CacheTest extends BaseTest {

	private static RemoteCacheManager rm;
	private static Cache<String, Map<String,Object>> cache;
	private static Cache<String, List<Map<String,Object>>> newsCache;

	@Test
	public void basicTest12(){

		Properties pro = propertiesSetting();
		connectCache(pro);
		DBObject o = new BasicDBObject();
		o.put("a", 123);
		DBObject oo = new BasicDBObject();
		DBObject ooo = new BasicDBObject();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(o.toMap());
		list.add(oo.toMap());
		list.add(ooo.toMap());
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		
		newsCache.put("list", list);
		System.out.println(newsCache.get("list").toString());
		System.out.println(newsCache.get("all").toString());
	}
	@Test
	public void basicTest(){

		Properties pro = propertiesSetting();
		connectCache(pro);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id","skynle1");
		map.put("name","이용혁1");
		map.put("addr","서울시 광진구 능동1");
		map.put("date",new Date());
		
		cache.put("2", map);
		
		map.put("id","skynle2");
		map.put("name","이용혁2");
		map.put("addr","서울시 광진구 능동2");
		map.put("date",new Date());
		
		cache.put("1", map);
		
		System.out.println(cache.get("1"));
		System.out.println(cache.get("1"));
	}
	
	@Test
	public void basicTest1(){
		Properties pro = propertiesSetting();
		connectCache(pro);
		while(true){

			try{
				System.out.println(cache.get("3"));
					System.out.println(cache.get("1"));
					System.out.println(cache.get("2"));
					System.out.println(cache.get("3"));
					System.out.println(cache.get("4"));
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("!!!!!!!!!!!!!"+e.getMessage());
				connectCache(rm.getProperties());
			}
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
			cache = rm.getCache("USER_INFO");
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
