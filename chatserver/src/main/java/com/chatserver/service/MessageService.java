package com.chatserver.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatserver.entity.ChatroomEntity;
import com.chatserver.entity.MessageEntity;
import com.chatserver.entity.UserEntity;
import com.chatserver.repository.ChatroomRepo;
import com.chatserver.repository.MessageRepo;
import com.chatserver.repository.UserRepo;


@Component
public class MessageService {

	private MessageRepo messageRepo;
	private ChatroomRepo chatroomRepo;
	private UserRepo userRepo;
	
	@Autowired
	public MessageService (MessageRepo inMessageRepo, ChatroomRepo inChatroomRepo, UserRepo inUserRepo) {
		this.messageRepo = inMessageRepo;
		this.chatroomRepo = inChatroomRepo;
		this.userRepo = inUserRepo;
	}
	
	public void addMessage (UserEntity inUserObj, BigInteger inRoomId, String inPrivateScreenName, String inMessage) {
		ChatroomEntity aRoomObj = chatroomRepo.getOne(inRoomId); 
		UserEntity aPrivateUserObj = userRepo.findByScreenNameIgnoreCase(inPrivateScreenName);
		MessageEntity aNewMsgEntity  = new MessageEntity(inUserObj, aRoomObj.getChatroomId(), aPrivateUserObj.getUserId(), inMessage);
		messageRepo.save(aNewMsgEntity);
	}
	
	public void getMessagesByChatroom (UserEntity inUserObj, BigInteger inRoomId) {
		messageRepo.findByChatroomId(inRoomId); 
	}
	
	public void getMessagesByPrivateUser (BigInteger inPrivateUserId) {
		messageRepo.findByPrivateUserId(inPrivateUserId);
	}
}
