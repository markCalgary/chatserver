package com.chatserver.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatserver.entity.MessageEntity;
import com.chatserver.entity.UserEntity;

@Repository
public interface MessageRepo extends JpaRepository <MessageEntity, BigInteger>{

	List <MessageEntity> findByChatroomIdOrderByCreateDateTime(BigInteger chatroomId);
	
	List <MessageEntity> findByPrivateUserIdOrderByCreateDateTime(BigInteger privateUserId);
}
