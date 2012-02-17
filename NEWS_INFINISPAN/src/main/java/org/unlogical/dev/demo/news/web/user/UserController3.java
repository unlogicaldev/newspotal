package org.unlogical.dev.demo.news.web.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.unlogical.dev.demo.news.common.abs.AbstractBaseController;
import org.unlogical.dev.demo.news.common.cache.InfinispanCacheManager;
import org.unlogical.dev.demo.news.web.model.entity.Login;
import org.unlogical.dev.demo.news.web.model.entity.User;
import org.unlogical.dev.demo.news.web.user.service.UserService;

import com.mongodb.DBObject;

public class UserController3 extends AbstractBaseController<UserController3> {

	@Autowired private UserService userService;
	

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String userSignIn(){
		return "user/signIn";
	}
	
	@RequestMapping(value="/signout", method=RequestMethod.GET)
	public String userSignOut(HttpServletRequest req,HttpServletResponse res, @CookieValue String NEWS_SESSION){
		//req.getSession().invalidate();
		InfinispanCacheManager.removeSessioinCache(NEWS_SESSION);
		Cookie c = new Cookie("NEWS_SESSION","");
		c.setMaxAge(0);
		c.setPath("/");
		res.addCookie(c);
		return "redirect:/news/";
	}

	@RequestMapping(value="/signinAjax", method=RequestMethod.POST)
	public @ResponseBody Model userLoginAjax(@Valid Login login,
			   						     BindingResult result,
			   						     HttpServletRequest req,
			   						     HttpServletResponse res,
										 Model model) throws Exception {
		if(validResult(result, model)){
			DBObject user = procSignIn(login, req, res, model);

			model.addAttribute("userId", user.get("userId"));
			model.addAttribute("userName", user.get("userName"));
			model.addAttribute("result", true);
		}
		if( model.asMap().get("userName") == null ){
			model.addAttribute("userId", login.getUserId());
			model.addAttribute("password", login.getPassword());
			model.addAttribute("message", "Fail Sign In.");
			model.addAttribute("result", false);
		}
		return model;
	}
	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public String userLogin(@Valid Login login,
   						     BindingResult result,
   						     HttpServletRequest req,
   						     HttpServletResponse res,
							 Model model) throws Exception {

		String view = "redirect:/news/";
		try{
			if(log.isDebugEnabled()){
				log.debug("Login : {}", login.toJSON());
			}
			if(validResult(result, model)){
				DBObject user = procSignIn(login, req, res, model);
				if(user == null){
					model.addAttribute("userId", login.getUserId());
					model.addAttribute("password", login.getPassword());
					model.addAttribute("message", "Fail Sign In.");
					model.addAttribute("result", false);
					view = "user/signIn";
				}
			}else{
				model.addAttribute("userId", login.getUserId());
				model.addAttribute("password", login.getPassword());
				model.addAttribute("message", "Fail Sign In.");
				model.addAttribute("result", false);
				view = "user/signIn";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return view;
	}

	@SuppressWarnings("unchecked")
	private DBObject procSignIn(Login login, HttpServletRequest req,HttpServletResponse res, Model model)
			throws Exception {
		DBObject user = userService.retriveUser(login);
		if(user != null){
			//setSessionAttr("USER_INFO", user.toMap(), req);
			user.put("sessionId",req.getSession().getId());
			req.getSession().invalidate();
			InfinispanCacheManager.setSessionCache(user.toMap());
			Cookie c = new Cookie("NEWS_SESSION",(String) user.get("userId")+"_"+user.get("sessionId"));
			c.setPath("/");
			res.addCookie(c);
		}
		if(log.isDebugEnabled()){
			log.debug("User : {}", user);
		}
		return user;
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String userSignUp(){
		return "user/signUp";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String userSignUp(@Valid User user, 
									      BindingResult result, 
									      Model model) throws Exception{
		int r = 0;
		String view = "redirect:/user/signin";
		if(validResult(result, model)){
			if(user.getId() == null){
				r = userService.createUser(user);				
			}else{
				User u = userService.modifyUser(user);
				if(u != null) r = 1;
			}
			if(r < 1){
				model.addAttribute("message", "Fail!!!");
				model.addAttribute("user", user);
				view = "user/signup";
			}
		}
		return view;
	}
}
