package org.unlogical.dev.demo.news.common.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mongodb.DBObject;

public class CacheManager {

	private static Map<String,List<DBObject>> newsListCache = new ConcurrentHashMap<String,List<DBObject>>();
	
	public static void setNewsListCache(String category, List<DBObject> list){
		newsListCache.put(category, list);
	}
	
	public static List<DBObject> getNewsListCache(String category){
		return newsListCache.get(category);
	}
}
