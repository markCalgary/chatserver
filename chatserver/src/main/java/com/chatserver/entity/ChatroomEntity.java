package com.chatserver.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="chatroom")
public class ChatroomEntity {
	@Id @GeneratedValue private BigInteger chatroomId;
	@NotNull private String name;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "create_user_id", nullable = false) 
	private UserEntity createUser;
	@NotNull private LocalDateTime createDateTime;

	public ChatroomEntity(String inName, UserEntity inCreateUser) {
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
