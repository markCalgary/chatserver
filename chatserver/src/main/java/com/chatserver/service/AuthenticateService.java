package com.chatserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chatserver.dao.AuthenticateResponseDao;
import com.chatserver.entity.UserEntity;
import com.chatserver.model.SessionModel;
import com.chatserver.repository.UserRepo;

@Component
public class AuthenticateService {

	private UserRepo userRepo;
	private SessionService sessionService;
	
	@Autowired
	public AuthenticateService (UserRepo inUserRepo, SessionService inSessionService) {
		this.userRepo = inUserRepo;
		this.sessionService = inSessionService;
	}
	
	public AuthenticateResponseDao loginUser (String inUserName, String inPassword) {
		UserEntity aUser = this.userRepo.findByUserNameIgnoreCaseAndPassword(inUserName, inPassword);
		if (aUser == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		SessionModel aSession = this.sessionService.addSession(aUser.getUserId(), aUser.getUserName(), aUser.getScreenName());
		AuthenticateResponseDao aResp = new AuthenticateResponseDao();
		aResp.setScreenName(aUser.getScreenName());
		aResp.setSessionId(aSession.getSessionId());
		return aResp;
	}
	
	public void logoutUser (String inSessionId){
		this.sessionService.removeSession(inSessionId);
	}
}
