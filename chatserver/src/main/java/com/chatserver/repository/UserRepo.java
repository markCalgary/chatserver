package com.chatserver.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatserver.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository <UserEntity, BigInteger>{

	UserEntity findByUserNameIgnoreCase(String inScreenName);
	
	UserEntity findByScreenNameIgnoreCase(String inScreenName);
}
