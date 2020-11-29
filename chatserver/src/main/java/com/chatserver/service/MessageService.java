package com.chatserver.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.chatserver.dao.MessageDao;
import com.chatserver.entity.ChatroomEntity;
import com.chatserver.entity.MessageEntity;
import com.chatserver.entity.UserEntity;
import com.chatserver.model.SessionModel;
import com.chatserver.repository.ChatroomRepo;
import com.chatserver.repository.MessageRepo;
import com.chatserver.repository.UserRepo;


@Component
public class MessageService {

	private MessageRepo messageRepo;
	private ChatroomRepo chatroomRepo;
	private UserRepo userRepo;
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	public MessageService (MessageRepo inMessageRepo, ChatroomRepo inChatroomRepo, UserRepo inUserRepo, SimpMessagingTemplate inSimpMessagingTemplate) {
		this.messageRepo = inMessageRepo;
		this.chatroomRepo = inChatroomRepo;
		this.userRepo = inUserRepo;
		this.simpMessagingTemplate = inSimpMessagingTemplate;
	}
	
	public void addMessage (SessionModel inSessionObj, BigInteger inRoomId, String inPrivateScreenName, String inMessage) {
		ChatroomEntity aRoomObj = chatroomRepo.getOne(inRoomId); 
		UserEntity aPrivateUserObj = userRepo.findByScreenNameIgnoreCase(inPrivateScreenName);
		UserEntity aAuthorObj = userRepo.findById(inSessionObj.getUserId()).get();
		MessageEntity aNewMsgEntity  = new MessageEntity(aAuthorObj, aRoomObj.getChatroomId(), aPrivateUserObj.getUserId(), inMessage);
		messageRepo.save(aNewMsgEntity);
		MessageDao aDao = new MessageDao();
		aDao.setMessageId(aNewMsgEntity.getMessageId());
		aDao.setAuthor_screenName(inSessionObj.getScreenName());
		aDao.setChatroomName(aRoomObj.getName());
		aDao.setCreateDateTime(aNewMsgEntity.getCreateDateTime());
		aDao.setMessage(inMessage);
		aDao.setPrivateScreenName(inPrivateScreenName);
		if (inRoomId != null) this.simpMessagingTemplate.convertAndSend("/topic/"+inRoomId.toString(), aDao);
		if (inPrivateScreenName != null) this.simpMessagingTemplate.convertAndSend("/queue/"+inPrivateScreenName, aDao);
	}
	
	public void getMessagesByChatroom (UserEntity inUserObj, BigInteger inRoomId) {
		messageRepo.findByChatroomId(inRoomId); 
	}
	
	public void getMessagesByPrivateUser (BigInteger inPrivateUserId) {
		messageRepo.findByPrivateUserId(inPrivateUserId);
	}
}
