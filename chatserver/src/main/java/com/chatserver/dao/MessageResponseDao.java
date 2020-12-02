package com.chatserver.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.chatserver.entity.MessageEntity;

public class MessageResponseDao {
	private BigInteger messageId;
	private String author_screenName;
	private LocalDateTime createDateTime;
	private String chatroomName;
	private String privateScreenName;
	private String message;

	public MessageResponseDao() {}
	
	public BigInteger getMessageId() {
		return messageId;
	}
	public void setMessageId(BigInteger messageId) {
		this.messageId = messageId;
	}
	public String getAuthor_screenName() {
		return author_screenName;
	}
	public void setAuthor_screenName(String author_screenName) {
		this.author_screenName = author_screenName;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getChatroomName() {
		return chatroomName;
	}
	public void setChatroomName(String chatroomName) {
		this.chatroomName = chatroomName;
	}
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
