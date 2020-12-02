package com.chatserver.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.chatserver.dao.MessageResponseDao;
import com.chatserver.entity.ChatroomEntity;
import com.chatserver.entity.MessageEntity;
import com.chatserver.entity.UserEntity;
import com.chatserver.model.SessionModel;
import com.chatserver.repository.ChatroomRepo;
import com.chatserver.repository.MessageRepo;
import com.chatserver.repository.UserRepo;

import javassist.NotFoundException;

@Component
public class MessageService {

	private MessageRepo messageRepo;
	private ChatroomRepo chatroomRepo;
	private UserRepo userRepo;
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	public MessageService (MessageRepo inMessageRepo, ChatroomRepo inChatroomRepo, UserRepo inUserRepo,
			SimpMessagingTemplate inSimpMessagingTemplate) {
		this.messageRepo = inMessageRepo;
		this.chatroomRepo = inChatroomRepo;
		this.userRepo = inUserRepo;
		this.simpMessagingTemplate = inSimpMessagingTemplate;
	}
	
	public void addMessage (SessionModel inSessionObj, BigInteger inRoomId, String inPrivateScreenName, String inMessage) 
			throws NotFoundException {
		// validate the roomId
		if (inRoomId == null && inPrivateScreenName == null) {
			throw new DataIntegrityViolationException("Cannot pass in null values for both room and privateScreenName");
		}
		String roomName = null;
		if (inRoomId != null) {
			Optional <ChatroomEntity> aRoomOpt = chatroomRepo.findById(inRoomId);
			if (aRoomOpt.isEmpty()) {
				throw new NotFoundException("RoomId is not valid");
			} else {
				roomName = aRoomOpt.get().getName();
			}
		}
		
		// validate the privateMessageUserId
		BigInteger privateUserId = null;
		if (inPrivateScreenName != null) {
			UserEntity aPrivateUserObj = userRepo.findByScreenNameIgnoreCase(inPrivateScreenName);
			if (aPrivateUserObj != null) {
				privateUserId = aPrivateUserObj.getUserId();
			}
			else {
				throw new NotFoundException("User selected for private message not found");
			}
		}
		UserEntity aAuthorObj = userRepo.findById(inSessionObj.getUserId()).get();
		MessageEntity aNewMsgEntity  = new MessageEntity(aAuthorObj, inRoomId, privateUserId , inMessage);
		messageRepo.save(aNewMsgEntity);
		MessageResponseDao aDao = new MessageResponseDao();
		aDao.setMessageId(aNewMsgEntity.getMessageId());
		aDao.setAuthor_screenName(inSessionObj.getScreenName());
		aDao.setChatroomName(roomName);
		aDao.setCreateDateTime(aNewMsgEntity.getCreateDateTime());
		aDao.setMessage(inMessage);
		aDao.setPrivateScreenName(inPrivateScreenName);
		if (inRoomId != null) this.simpMessagingTemplate.convertAndSend("/room/"+inRoomId.toString()+"/subscribe", aDao);
		if (inPrivateScreenName != null) this.simpMessagingTemplate.convertAndSend("/user/"+inPrivateScreenName+"/subscribe", aDao);
	}
	
	public List <MessageResponseDao> getMessagesByChatroom (BigInteger inRoomId) {
		List <MessageResponseDao> response = new ArrayList<MessageResponseDao>();
		List <MessageEntity> entityList = messageRepo.findByChatroomIdOrderByCreateDateTime(inRoomId);
		for (MessageEntity anEntity : entityList) {
			response.add(convertEntityToDao(anEntity));
		}
		return response;
	}
	
	public List <MessageResponseDao> getMessagesByPrivateUser (BigInteger inPrivateUserId) {
		List <MessageResponseDao> response = new ArrayList<MessageResponseDao>();
		List <MessageEntity> entityList = messageRepo.findByPrivateUserIdOrderByCreateDateTime(inPrivateUserId);
		for (MessageEntity anEntity : entityList) {
			response.add(convertEntityToDao(anEntity));
		}
		return response;
	}
	
	private MessageResponseDao convertEntityToDao (MessageEntity anEntity) {
		MessageResponseDao aDao = new MessageResponseDao();
		aDao.setAuthor_screenName(anEntity.getAuthor().getScreenName());
		aDao.setCreateDateTime(anEntity.getCreateDateTime());
		aDao.setMessage(anEntity.getMessage());
		aDao.setMessageId(anEntity.getMessageId());
		return aDao;
	}
}
