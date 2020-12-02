package com.chatserver.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.chatserver.util.CreateIdUtil;

@Entity
@Table(name="user", indexes= {@Index(columnList="username"), @Index(columnList="screenName")})
public class UserEntity {
	@Id @Column(precision = 14, scale = 0) private BigInteger userId;
	@NotNull @Column(unique=true) private String userName;
	@NotNull private String password;
	@NotNull @Column(unique=true) private String screenName;
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "author")
    private List<MessageEntity> messageAuthorList = new ArrayList<MessageEntity>();

	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "createUser")
    private List<ChatroomEntity> chatroomCreateList = new ArrayList<ChatroomEntity>();
	
	public UserEntity(String inUsername, String inPassword, String inScreenName) {
		this.userId = CreateIdUtil.createId(CreateIdUtil.IDTYPE_USERID);
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
