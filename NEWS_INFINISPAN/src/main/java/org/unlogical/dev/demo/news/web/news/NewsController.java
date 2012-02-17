package org.unlogical.dev.demo.news.web.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.unlogical.dev.demo.news.common.abs.AbstractBaseController;
import org.unlogical.dev.demo.news.common.cache.CacheManager;
import org.unlogical.dev.demo.news.common.cache.InfinispanCacheManager;
import org.unlogical.dev.demo.news.web.news.service.NewsService;

@Controller
@RequestMapping(value="/news")
public class NewsController extends AbstractBaseController<NewsController> {

	@Autowired private NewsService newsService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String main(@CookieValue(required=false, defaultValue="") String NEWS_SESSION, Model model) throws Exception{
		
		model.addAttribute("cList", new String[]{"최신뉴스","Engadget","경제_IT","증권","사회","전국","정치_북한","보안","세계","문화_예술","스포츠","연예"});
		if(!NEWS_SESSION.equals("")){
			model.addAttribute("USER_INFO", InfinispanCacheManager.getSessionCache(NEWS_SESSION));
		}
		return "news/main";
	}
	
	@RequestMapping(value="/auto", method=RequestMethod.GET)
	public String mainAuto(Model model) throws Exception{
		
		model.addAttribute("cList", new String[]{"최신뉴스","Engadget","경제_IT","증권","사회","전국","정치_북한","보안","세계","문화_예술","스포츠","연예"});
		return "news/mainAuto";
	}
	
	@RequestMapping(value="/cache", method=RequestMethod.GET)
	public String mainCache(Model model) throws Exception{
		
		model.addAttribute("cList", new String[]{"최신뉴스","Engadget","경제_IT","증권","사회","전국","정치_북한","보안","세계","문화_예술","스포츠","연예"});
		return "news/mainCache";
	}
	
	@RequestMapping(value="/list/{category}")
	public @ResponseBody Model newsList(@PathVariable String category
										,@RequestParam(required=false,defaultValue="0") int page
										,@RequestParam(required=false,defaultValue="30") int pageCnt
										,Model model) throws Exception {
		try{
			model.addAttribute("list", newsService.retriveNewsList(category, page, pageCnt));
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value="/listcache/{category}")
	public @ResponseBody Model newsListCache(@PathVariable String category
										,@RequestParam(required=false,defaultValue="0") int page
										,@RequestParam(required=false,defaultValue="30") int pageCnt
										,Model model) throws Exception {
		try{
			//model.addAttribute("list", CacheManager.getNewsListCache(category));
			model.addAttribute("list", InfinispanCacheManager.getNewsListCache(category));
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	/*
	String userId = NEWS_SESSION.split("_")[0];
	String sessionId = NEWS_SESSION.split("_")[1];
	Map<String,Object> u = InfinispanCacheManager.getSessionCache(userId);
	if(sessionId.equals(u.get("sessionId"))) model.addAttribute("USER_INFO", u);
	else model.addAttribute("message", "다른곳에서 로그인해부렀음...!!!");
	 */
}
