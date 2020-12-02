package com.chatserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.chatserver.dao.UserRequestDao;
import com.chatserver.dao.UserResponseDao;
import com.chatserver.entity.UserEntity;
import com.chatserver.repository.UserRepo;

@Component
public class UserService {

	private UserRepo userRepo;
	private SessionService sessionService;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService (UserRepo inUserRepo, SessionService inSessionService, PasswordEncoder inPasswordEncoder) {
		this.userRepo = inUserRepo;
		this.sessionService = inSessionService;
		this.passwordEncoder = inPasswordEncoder;
	}
	
	public UserResponseDao addUser (String inUserName, String inPassword, String inScreenName) {
		if (this.userRepo.findByUserNameIgnoreCase(inUserName) !=null) {
			throw new DataIntegrityViolationException("Username is not unique.  New user not created");
		}
		if (this.userRepo.findByScreenNameIgnoreCase(inScreenName) !=null) {
			throw new DataIntegrityViolationException("Screenname is not unique.  New user not created");
		}
		UserEntity aUserEntity = new UserEntity(inUserName, passwordEncoder.encode(inPassword), inScreenName);
		this.userRepo.save(aUserEntity);
		UserResponseDao aUserDao = new UserResponseDao();
		aUserDao.setUserName(inUserName);
		aUserDao.setScreenName(inScreenName);
		return aUserDao;
	}

	/*
	 * Of course this is a coding/testing environment this type of data availability should be limited or removed
	 * in the real world
	 */
	public List <UserResponseDao> getAll () {
		List <UserEntity> entityList = this.userRepo.findAll();
		List <UserResponseDao> responseList = new ArrayList <UserResponseDao>();
		for (UserEntity anEntity : entityList) {
			UserResponseDao aUserDao = new UserResponseDao();
			aUserDao.setScreenName(anEntity.getScreenName());
			responseList.add(aUserDao);
		}
		return responseList;
	}
}
