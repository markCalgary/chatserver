package com.chatserver.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.chatserver.util.CreateIdUtil;

public class SessionModel {

	private String sessionId;
	private BigInteger userId;
	private String userName;
	private String screenName;
		
	private LocalDateTime lastHitTime;
	
	public SessionModel(BigInteger inUserId, String inUserName, String inScreenName) {
		this.sessionId = CreateIdUtil.createSessionId();
		this.userId = inUserId;
		this.userName = inUserName;
		this.screenName = inScreenName;
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public LocalDateTime getLastHitTime() {
		return lastHitTime;
	}
	public void setLastHitTime(LocalDateTime lastHitTime) {
		this.lastHitTime = lastHitTime;
	}
	public void hitSession() {
		this.lastHitTime = LocalDateTime.now();
	}
}
