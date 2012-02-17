package org.unlogical.dev.demo.news.web.news.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.unlogical.dev.demo.news.common.abs.AbstractBaseService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service("newsService")
public class NewsServiceImpl extends AbstractBaseService<NewsServiceImpl>
		implements NewsService {

	@Override
	public List<DBObject> retriveNewsList(String category, int page, int pageCnt) throws Exception {
		List<DBObject> list = null;
		try{
			if(category.equals("all"))
				list = getDBCollection("News").find(null,getNewsListFields()).sort(new BasicDBObject("_id",-1)).skip(page*pageCnt).limit(pageCnt).toArray();
			else
				list = getDBCollection("News").find(new BasicDBObject("category",category),getNewsListFields()).sort(new BasicDBObject("_id",-1)).skip(page*pageCnt).limit(pageCnt).toArray();
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		return list;
	}
	
	/**
	 *  News fields setting
	 *	@return
	 */
	private DBObject getNewsListFields(){
		DBObject o = new BasicDBObject();
		o.put("title", 1);
		o.put("link", 1);
		o.put("description", 1);
		o.put("category", 1);
		o.put("date", 1);
		o.put("creator", 1);
		o.put("readCnt", 1);
		return o;
	}

}
