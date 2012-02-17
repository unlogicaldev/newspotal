package org.unlogical.dev.demo.news.web.user.service;

import org.unlogical.dev.demo.news.web.model.entity.Login;
import org.unlogical.dev.demo.news.web.model.entity.User;

import com.mongodb.DBObject;

public interface UserService {

	public DBObject retriveUser(String userId) throws Exception;
	
	public DBObject retriveUser(Login login) throws Exception;
	
	public int createUser(User user) throws Exception;
	
	public User modifyUser(User user) throws Exception;
}
