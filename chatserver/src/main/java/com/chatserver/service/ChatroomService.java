package com.chatserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.chatserver.dao.ChatroomResponseDao;
import com.chatserver.dao.UserResponseDao;
import com.chatserver.entity.ChatroomEntity;
import com.chatserver.entity.UserEntity;
import com.chatserver.repository.ChatroomRepo;

@Component
public class ChatroomService {

	private ChatroomRepo chatroomRepo;
	@Autowired
	public ChatroomService (ChatroomRepo inChatroomRepo ) {
		this.chatroomRepo = inChatroomRepo;
	}
	
	public List <ChatroomResponseDao> getChatrooms() {
		List <ChatroomEntity> entityList = this.chatroomRepo.findAll();
		List <ChatroomResponseDao> responseList = new ArrayList<ChatroomResponseDao>();
		for (ChatroomEntity anEntity : entityList) {
			responseList.add(entityToDao(anEntity));
		}
		return responseList;
	}
	
	public ChatroomResponseDao addChatroom(UserEntity inUserObj, String inRoomName) {
		if (this.chatroomRepo.findByName(inRoomName)!= null) {
			throw new DataIntegrityViolationException("Room name already exists");
		}
		ChatroomEntity aChatroomEntity = new ChatroomEntity(inRoomName, inUserObj);
		this.chatroomRepo.save(aChatroomEntity);
		return entityToDao(aChatroomEntity);
	}
	
	private ChatroomResponseDao entityToDao(ChatroomEntity anEntity) {
		ChatroomResponseDao aResp = new ChatroomResponseDao();
		aResp.setChatroomId(anEntity.getChatroomId());
		aResp.setCreateDateTime(anEntity.getCreateDateTime());
		aResp.setName(anEntity.getName());
		aResp.setUserCreateScreenName(anEntity.getCreateUser().getScreenName());
		return aResp;
	}
}
