package com.chatserver.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatserver.dao.UserRequestDao;
import com.chatserver.dao.UserResponseDao;
import com.chatserver.service.UserService;

@RestController
@RequestMapping("v001")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController (UserService inUserService) {
		this.userService = inUserService;
	}
	
	@PostMapping("/user")
	private UserResponseDao createUser(@RequestBody @Valid UserRequestDao inUserDao) {
		return this.userService.addUser(inUserDao.getUserName(), inUserDao.getPassword(), inUserDao.getScreenName());
	}
	
	/*
	 * of course this wide open end point would never make it in production but for this coding environment it is
	 * used to display a users screenName so that a message can be sent
	 */
	@GetMapping("/user")
	private List <UserResponseDao> getUsers() {
		return this.userService.getAll();
	}
}