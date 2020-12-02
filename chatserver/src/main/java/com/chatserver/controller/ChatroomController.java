package com.chatserver.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatserver.dao.ChatroomRequestDao;
import com.chatserver.dao.ChatroomResponseDao;
import com.chatserver.entity.UserEntity;
import com.chatserver.repository.UserRepo;
import com.chatserver.service.ChatroomService;
import com.chatserver.service.SessionService;

@RestController
@RequestMapping("v001")
public class ChatroomController {

	private ChatroomService chatroomService;
	private SessionService sessionService;
	private UserRepo userRepo;
	
	public ChatroomController (ChatroomService inChatroomService, SessionService inSessionService, UserRepo inUserRepo) {
		this.chatroomService = inChatroomService;
		this.sessionService = inSessionService;
		this.userRepo = inUserRepo;
	}
	
	@PostMapping("/chatroom")
	private ChatroomResponseDao createChatroom (@RequestHeader(value="Authorization", required = true) String inSessionId,
			@Valid @RequestBody ChatroomRequestDao inRequest) {
		UserEntity aUserEntity = this.userRepo.findById(this.sessionService.getSession(inSessionId).getUserId()).get();
		return this.chatroomService.addChatroom(aUserEntity, inRequest.getName());
	}
	
	@GetMapping("/chatroom")
	private List <ChatroomResponseDao> getChatroomAll () {
		return this.chatroomService.getChatrooms();
	}
}
