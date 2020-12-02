package com.chatserver.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chatserver.model.SessionModel;

@Component
public class SessionService {

	private static final int SESSION_CHECK_MILLISECONDS = 120000; // every 2 minutes
	private static final int TIMEOUT_WINDOW_MINUTES = 20;
	
	private static Hashtable <String, SessionModel> sessionList = new Hashtable<String, SessionModel>();
	
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
	
	@Scheduled(fixedRate = SESSION_CHECK_MILLISECONDS)
	private void expireOldSessions() {
		// separated the iteration from the remove otherwise you get a concurrency error
		Enumeration <String> enu =sessionList.keys();
		ArrayList <String> removeList = new ArrayList <String>();
		while(enu.hasMoreElements()) {
			String aKey = enu.nextElement();
			LocalDateTime window = LocalDateTime.now().minusMinutes(TIMEOUT_WINDOW_MINUTES);
			if (sessionList.get(aKey).getLastHitTime().isBefore(window)) {
				removeList.add(aKey);
			}
		}
		for (String aKey: removeList) {
			sessionList.remove(aKey);
		}
	} 

}
