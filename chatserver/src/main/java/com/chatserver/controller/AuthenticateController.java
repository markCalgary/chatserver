package com.chatserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatserver.dao.AuthenticateRequestDao;
import com.chatserver.dao.AuthenticateResponseDao;
import com.chatserver.service.AuthenticateService;

@RestController
@RequestMapping("v001")
public class AuthenticateController {

	private AuthenticateService authenticateService;
	
	@Autowired
	public AuthenticateController(AuthenticateService inAuthenticateService) {
		this.authenticateService = inAuthenticateService;
	}
	
	@PostMapping("/authenticate/login")
	public AuthenticateResponseDao login(@Valid @RequestBody AuthenticateRequestDao inAuthenticateRequestDao) {
		return this.authenticateService.loginUser(inAuthenticateRequestDao.getUserName(), inAuthenticateRequestDao.getPassword());
	}
	
	@PostMapping("/authenticate/logout")
	public void logout(@RequestHeader(value="Authorization", required = true) String inSessionId) {
		this.authenticateService.logoutUser(inSessionId);
	}
}
