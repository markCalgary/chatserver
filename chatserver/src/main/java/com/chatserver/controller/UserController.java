package com.chatserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatserver.dao.UserDao;
import com.chatserver.service.UserService;

@RestController
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController (UserService inUserService) {
		this.userService = inUserService;
	}
	
	@PostMapping("/user")
	private UserDao createUser(@RequestBody UserDao inUserDao) {
		//TODO: validate user input
		return this.userService.addUser(inUserDao.getUserName(), inUserDao.getPassword(), inUserDao.getScreenName());
	}
}