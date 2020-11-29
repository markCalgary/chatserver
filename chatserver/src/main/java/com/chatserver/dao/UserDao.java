package com.chatserver.dao;

import java.math.BigInteger;

import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class UserDao {
	@NotNull @Size(min=7) private String userName;
	@NotNull @Size(min=7) private String password;
	@NotNull @Size(min=7) private String screenName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
