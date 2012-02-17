package org.unlogical.dev.demo.news.web.user.service;

import org.springframework.stereotype.Service;
import org.unlogical.dev.demo.news.common.abs.AbstractBaseService;
import org.unlogical.dev.demo.news.web.model.entity.Login;
import org.unlogical.dev.demo.news.web.model.entity.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

@Service("userService")
public class UserServiceImpl extends AbstractBaseService<UserServiceImpl> implements UserService{

	@Override
	public DBObject retriveUser(String userId) throws Exception {
		return getDBCollection("User").findOne(new BasicDBObject("userId",userId));
	}

	@Override
	public DBObject retriveUser(Login login) throws Exception {
		DBObject o = login.toDBObject();
		o.removeField("className");
		return getDBCollection("User").findOne(o);
	}

	@Override
	public int createUser(User user) throws Exception {
		WriteResult wr = getDBCollection("User").insert(user.toDBObject(), WriteConcern.SAFE);
		if(log.isDebugEnabled()){
			log.debug("createUser : {}", wr.toString());
		}
		if(wr.getError() != null) return 0;
		return 1;
	}

	@Override
	public User modifyUser(User user) throws Exception {
		WriteResult wr = getDBCollection("User").save(user.toDBObject(),WriteConcern.SAFE);
		if(log.isDebugEnabled()){
			log.debug("createUser : {}", wr.toString());
		}
		if(wr.getError() != null) return null;
		return user;
	}

}
