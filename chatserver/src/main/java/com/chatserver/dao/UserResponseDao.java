package com.chatserver.dao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserResponseDao {
	@NotNull @Size (min=7, max=50) private String userName;
	@NotNull @Size (min=7, max=50) private String screenName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
