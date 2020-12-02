package com.chatserver.dao;

public class MessageRequestDao {
	private String privateScreenName;
	private String message;

	public MessageRequestDao() {}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPrivateScreenName() {
		return privateScreenName;
	}
	public void setPrivateScreenName(String privateScreenName) {
		this.privateScreenName = privateScreenName;
	}
}
