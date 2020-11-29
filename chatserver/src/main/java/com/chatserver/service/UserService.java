package com.chatserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.chatserver.dao.UserDao;
import com.chatserver.entity.UserEntity;
import com.chatserver.repository.UserRepo;

@Component
public class UserService {

	private UserRepo userRepo;
	private SessionService sessionService;
	
	@Autowired
	public UserService (UserRepo inUserRepo, SessionService inSessionService) {
		this.userRepo = inUserRepo;
		this.sessionService = inSessionService;
	}
	
	public UserDao addUser (String inUserName, String inPassword, String inScreenName) {
		if (this.userRepo.findByUserNameIgnoreCase(inUserName) !=null) {
			throw new DataIntegrityViolationException("Username is not unique.  New user not created");
		}
		if (this.userRepo.findByScreenNameIgnoreCase(inScreenName) !=null) {
			throw new DataIntegrityViolationException("Screenname is not unique.  New user not created");
		}
		UserEntity aUserEntity = new UserEntity(inUserName, inPassword, inScreenName);
		this.userRepo.save(aUserEntity);
		UserDao aUserDao = new UserDao();
		aUserDao.setUserName(inUserName);
		aUserDao.setScreenName(inScreenName);
		return aUserDao;
	}
	
	public void logInUser (String inUserName, String inPassword) {
		UserEntity aUserEntity = userRepo.findByUserNameIgnoreCaseAndPassword(inUserName, inPassword);
		if (aUserEntity == null) {return ;} // TODO: throw error will move to another service
		this.sessionService.addSession(aUserEntity.getUserId(), inUserName, aUserEntity.getScreenName());
	}
	
	public void logoutUser (String inSessionId) {
		this.sessionService.removeSession(inSessionId);
	}
	
}
