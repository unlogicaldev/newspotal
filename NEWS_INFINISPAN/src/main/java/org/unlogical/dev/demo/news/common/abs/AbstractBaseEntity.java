package org.unlogical.dev.demo.news.common.abs;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.unlogical.dev.demo.news.common.mongo.MongoTemplate;

import com.mongodb.DBObject;

public abstract class AbstractBaseEntity {

	public String toString() {
		return ToStringBuilder.reflectionToString(this,	ToStringStyle.NO_FIELD_NAMES_STYLE);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public String toJSON(){
		return toDBObject().toString();
	}
	
	public DBObject toDBObject(){
		return MongoTemplate.getMorphia().toDBObject(this);
	}
}