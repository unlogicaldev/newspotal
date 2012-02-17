package org.unlogical.dev.demo.news.batch;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.unlogical.dev.demo.news.common.cache.CacheManager;
import org.unlogical.dev.demo.news.common.cache.InfinispanCacheManager;
import org.unlogical.dev.demo.news.common.xml.XmlReader;
import org.unlogical.dev.demo.news.web.news.service.NewsService;

import com.mongodb.DBObject;

@Service("newsBatch")
public class NewsBatch {

	@Autowired private XmlReader xmlReader;

	@Autowired private NewsService newsService;

	@Scheduled(cron="0 */5 * * * *")
	public void rssBatch(){
		String rssUrl = "http://www.engadget.com/rss.xml";
		xmlReader.rssRead(rssUrl, "Engadget");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/economy.xml";
		xmlReader.rssRead(rssUrl, "경제_IT");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/stock.xml";
		xmlReader.rssRead(rssUrl, "증권");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/society.xml";
		xmlReader.rssRead(rssUrl, "사회");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/province.xml";
		xmlReader.rssRead(rssUrl, "전국");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/politics.xml";
		xmlReader.rssRead(rssUrl, "정치_북한");
		rssUrl = "http://www.boannews.com/media/news_rss.xml";
		xmlReader.rssRead(rssUrl, "보안");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/international.xml";
		xmlReader.rssRead(rssUrl, "세계");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/culture.xml";
		xmlReader.rssRead(rssUrl, "문화_예술");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/sports.xml";
		xmlReader.rssRead(rssUrl, "스포츠");
		rssUrl = "http://www.yonhapnews.co.kr/RSS/entertainment.xml";
		xmlReader.rssRead(rssUrl, "연예");
	}

	//@Scheduled(cron="30 */1 * * * *")
	public void setCacheBatch(){
		String[] cats = new String[]{"all","Engadget","경제_IT","증권","사회","전국","정치_북한","보안","세계","문화_예술","스포츠","연예"};
		List<DBObject> list = null;
		for(String c:cats){
			try {
				list = newsService.retriveNewsList(c, 0, 30);
				CacheManager.setNewsListCache(c, list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//@Scheduled(cron="40 */1 * * * *")
	public void setInfinispanCacheBatch(){
		String[] cats = new String[]{"all","Engadget","경제_IT","증권","사회","전국","정치_북한","보안","세계","문화_예술","스포츠","연예"};
		List<Map<String,Object>> list = null;
		for(String c:cats){
			try {
				list = newsService.retriveNewsListMap(c, 0, 30);
				System.out.println(list);
				InfinispanCacheManager.setNewsListCache(c, list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
