package org.unlogical.dev.demo.news.web.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.unlogical.dev.demo.news.common.abs.AbstractBaseController;
import org.unlogical.dev.demo.news.web.model.entity.Login;
import org.unlogical.dev.demo.news.web.model.entity.User;
import org.unlogical.dev.demo.news.web.user.service.UserService;

import com.mongodb.DBObject;

@Controller
@RequestMapping(value="/user")
public class UserController extends AbstractBaseController<UserController> {

	@Autowired private UserService userService;
	

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String userSignIn(){
		return "user/signIn";
	}

	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public @ResponseBody Model userLogin(@Valid Login login,
			   						     BindingResult result,
										 Model model) throws Exception {
		if(validResult(result, model)){
			DBObject user = userService.retriveUser(login);
			if(user == null){
				model.addAttribute("message", "Fail Sign In.");
				model.addAttribute("result", false);
			}else{
				model.addAttribute("userId", user.get("userId"));
				model.addAttribute("userName", user.get("userName"));
				model.addAttribute("result", true);
			}
		}
		return model;
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String userSignUp(){
		return "user/signUp";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public @ResponseBody Model userSignUp(@Valid User user, BindingResult result, Model model) throws Exception{
		if(validResult(result, model)){
			if(user.getId() == null){
				model.addAttribute("result", userService.createUser(user));
			}else{
				model.addAttribute("result", userService.modifyUser(user));
			}
		}
		return model;
	}
}
