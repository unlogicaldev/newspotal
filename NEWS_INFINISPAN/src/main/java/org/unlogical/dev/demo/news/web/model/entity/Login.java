package org.unlogical.dev.demo.news.web.model.entity;

import javax.validation.constraints.Size;

import org.unlogical.dev.demo.news.common.abs.AbstractBaseEntity;

public class Login extends AbstractBaseEntity {

	@Size(min=3,max=15)
	private String userId;
	@Size(min=1)
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
