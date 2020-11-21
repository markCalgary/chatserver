package com.chatserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	
	public void getChatrooms() {
		List <ChatroomEntity> fullList = this.chatroomRepo.findAll();
	}
	
	public void addChatroom(UserEntity inUserObj, String inRoomName) {
		new ChatroomEntity(inRoomName, inUserObj);
	}
}
