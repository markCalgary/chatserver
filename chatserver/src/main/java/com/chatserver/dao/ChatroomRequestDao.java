package com.chatserver.dao;

import javax.validation.constraints.NotNull;

public class ChatroomRequestDao {

	@NotNull private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
