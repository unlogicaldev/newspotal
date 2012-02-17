package org.unlogical.dev.demo.news.common.abs;

import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unlogical.dev.demo.news.common.mongo.MongoTemplate;

import com.google.code.morphia.Morphia;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public abstract class AbstractBaseService<T> {

	@SuppressWarnings("unchecked")
	final protected Logger log = LoggerFactory.getLogger(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
	
	protected DBCollection getDBCollection(String collectionName) {
		return MongoTemplate.getDBCollection(collectionName);
	}
	
	protected Morphia getMorphia() {
		return MongoTemplate.getMorphia();
	}
	
	protected DBObject toDBObject(Object entity){
		return MongoTemplate.getMorphia().toDBObject(entity);
	}
	
	@SuppressWarnings("hiding")
	protected <T> T fromDBObject(Class<T> entityClass, DBObject dbObject){
		return MongoTemplate.getMorphia().fromDBObject(entityClass, dbObject);
	}
}
