package org.unlogical.dev.demo.news.web.news.service;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

public interface NewsService {

	public List<DBObject> retriveNewsList(String category, int page, int pageCnt) throws Exception;

	public List<Map<String, Object>> retriveNewsListMap(String category, int page, int pageCnt) throws Exception;
}
