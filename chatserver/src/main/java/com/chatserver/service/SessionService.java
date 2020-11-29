package com.chatserver.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatserver.entity.UserEntity;
import com.chatserver.model.SessionModel;
import com.chatserver.repository.UserRepo;

@Component
public class SessionService {

	private UserRepo userRepo;
	
	@Autowired
	public SessionService (UserRepo inUserRepo) {
		this.userRepo = inUserRepo;
	}
	
	private static HashMap<String, SessionModel> sessionList = new HashMap<String, SessionModel>();
	
	public SessionModel getSession (String inSessionId) {
		return sessionList.get(inSessionId);
	}
	
	public Boolean sessionExists (String inSessionId) {
		if (sessionList.get(inSessionId)!= null) {
			return true;
		}
		return false;
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
