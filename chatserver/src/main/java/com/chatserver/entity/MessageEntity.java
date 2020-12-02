package com.chatserver.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.chatserver.util.CreateIdUtil;

@Entity
@Table(name="message", indexes= {@Index(columnList="chatroomId"), @Index(columnList="privateUserId")})
public class MessageEntity {
	@Id @Column(precision = 14, scale = 0) private BigInteger messageId;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "author_id", nullable = false) 
	private UserEntity author;
	@NotNull private LocalDateTime createDateTime;
	
	// send to a chatroom and/or private user so no foreign key link because of OR condition
	private BigInteger chatroomId;
	private BigInteger privateUserId;
	@NotNull private String message;
	
	public MessageEntity (UserEntity inAuthor, BigInteger inChatroomId, BigInteger inPrivateUserId, String inMessage) {
		this.messageId = CreateIdUtil.createId(CreateIdUtil.IDTYPE_MESSAGEID);
		this.author = inAuthor;
		this.createDateTime = LocalDateTime.now();
		this.chatroomId = inChatroomId;
		this.message = inMessage;
		this.privateUserId = inPrivateUserId;
	}
	
	public MessageEntity () {}

	public BigInteger getMessageId() {
		return messageId;
	}
	public void setMessageId(BigInteger messageId) {
		this.messageId = messageId;
	}
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserEntity getAuthor() {
		return author;
	}
	public void setAuthor(UserEntity author) {
		this.author = author;
	}
	public BigInteger getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(BigInteger chatroomId) {
		this.chatroomId = chatroomId;
	}
	public BigInteger getPrivateUserId() {
		return privateUserId;
	}
	public void setPrivateUserId(BigInteger privateUserId) {
		this.privateUserId = privateUserId;
	}
}
