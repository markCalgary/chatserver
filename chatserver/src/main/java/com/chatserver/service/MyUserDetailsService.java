package com.chatserver.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatserver.entity.UserEntity;
import com.chatserver.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{

	private UserRepo aUserRepo;
	
	@Autowired
	public MyUserDetailsService(UserRepo inUserRepo) {
		this.aUserRepo = inUserRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("database read...");
		UserEntity aUser = aUserRepo.findByUserNameIgnoreCase(username);
		if (aUser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		User theUser = new User(username, aUser.getPassword(), new ArrayList<>());
		return theUser;
	}

}
