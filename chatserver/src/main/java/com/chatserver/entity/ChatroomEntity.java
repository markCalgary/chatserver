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
@Table(name="chatroom", indexes= {@Index(columnList="name")})
public class ChatroomEntity {
	@Id @Column(precision = 14, scale = 0) private BigInteger chatroomId;
	@NotNull @Column(unique=true) private String name;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "create_user_id", nullable = false) 
	private UserEntity createUser;
	@NotNull private LocalDateTime createDateTime;

	public ChatroomEntity(String inName, UserEntity inCreateUser) {
		this.chatroomId = CreateIdUtil.createId(CreateIdUtil.IDTYPE_CHATROOM);
		this.name = inName;
		this.createUser = inCreateUser;
		this.createDateTime = LocalDateTime.now();
	}
	
	public ChatroomEntity() {}
	
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
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	public UserEntity getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}
	
}
