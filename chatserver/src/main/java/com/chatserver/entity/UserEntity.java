package com.chatserver.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="user")
public class UserEntity {
	@Id @GeneratedValue private BigInteger userId;
	@NotNull private String userName;
	@NotNull private String password;
	@NotNull private String screenName;
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "author")
    private List<MessageEntity> messageAuthorList = new ArrayList<MessageEntity>();

	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "createUser")
    private List<ChatroomEntity> chatroomCreateList = new ArrayList<ChatroomEntity>();
	
	public UserEntity(String inUsername, String inPassword, String inScreenName) {
		this.userName = inUsername;
		this.password = inPassword;
		this.screenName = inScreenName;
	}
	
	public UserEntity() {}
	
	
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public List<MessageEntity> getMessageAuthorList() {
		return messageAuthorList;
	}
	public void setMessageAuthorList(List<MessageEntity> messageAuthorList) {
		this.messageAuthorList = messageAuthorList;
	}
	public List<ChatroomEntity> getChatroomCreateList() {
		return chatroomCreateList;
	}
	public void setChatroomCreateList(List<ChatroomEntity> chatroomCreateList) {
		this.chatroomCreateList = chatroomCreateList;
	}
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
