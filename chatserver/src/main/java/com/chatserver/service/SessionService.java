package com.chatserver.service;

import java.math.BigInteger;
import java.util.HashMap;

import com.chatserver.model.SessionModel;


public class SessionService {

	private static HashMap<String, SessionModel> sessionList = new HashMap<String, SessionModel>();
	
	public SessionModel getSession (String inSessionId) {
		return sessionList.get(inSessionId);
	}
	
	public SessionModel addSession (BigInteger inUserId, String inUserName, String inScreenName) {
		SessionModel aModel = new SessionModel(inUserId, inUserName, inScreenName);
		sessionList.put(aModel.getSessionId(), aModel);
		return aModel;
	}
	
	public void hitSession (String inSessionId) {
		if (sessionList.get(inSessionId) == null) {return ;}// TODO: Throwable user forbidden error
		sessionList.get(inSessionId).hitSession();
	}
	
	public void removeSession(String inSessionId) {
		sessionList.remove(inSessionId);
	}
}
