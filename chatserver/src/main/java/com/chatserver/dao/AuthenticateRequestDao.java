package com.chatserver.dao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthenticateRequestDao {

	@NotNull @Size(min=7) private String userName;
	@NotNull @Size(min=7) private String password;
	
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

	
}
