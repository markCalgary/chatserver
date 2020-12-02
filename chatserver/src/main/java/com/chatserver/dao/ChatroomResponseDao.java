package com.chatserver.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class ChatroomResponseDao {

	private BigInteger chatroomId;
	private String name;
	private String userCreateScreenName;
	private LocalDateTime createDateTime;
	public BigInteger getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(BigInteger chatroomId) {
		this.chatroomId = chatroomId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserCreateScreenName() {
		return userCreateScreenName;
	}
	public void setUserCreateScreenName(String userCreateScreenName) {
		this.userCreateScreenName = userCreateScreenName;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
}
