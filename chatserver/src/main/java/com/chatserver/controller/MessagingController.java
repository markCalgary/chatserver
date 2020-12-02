package com.chatserver.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatserver.dao.MessageRequestDao;
import com.chatserver.dao.MessageResponseDao;
import com.chatserver.model.SessionModel;
import com.chatserver.service.MessageService;
import com.chatserver.service.SessionService;

import javassist.NotFoundException;

@RestController
@RequestMapping("v001")
public class MessagingController {

	private SimpMessagingTemplate simpMessageTemplate;
	private MessageService messageService;
	private SessionService sessionService;
	
	@Autowired
	public MessagingController (SimpMessagingTemplate inSimpMessagingTemplate, SessionService inSessionService,
			MessageService inMessageService) {
		this.simpMessageTemplate = inSimpMessagingTemplate;
		this.sessionService = inSessionService;
		this.messageService = inMessageService;
	}
	
	@PostMapping("/room/{roomId}/messages")
	public void sendToRoom(@RequestHeader(value="Authorization", required = true) String inSessionId,
			@PathVariable(name="roomId") BigInteger inRoomId, @RequestBody MessageRequestDao req) throws NotFoundException{
		this.messageService.addMessage(this.sessionService.getSession(inSessionId), inRoomId, null, req.getMessage());
	}
	
	@GetMapping("/room/{roomId}/messages")
	public List <MessageResponseDao> getRoomMessages(@PathVariable(name="roomId") BigInteger inRoomId) throws NotFoundException{
		return this.messageService.getMessagesByChatroom(inRoomId);
	}
	
	@PostMapping("/user/{screenName}/messages")
	public void sendToUser(@RequestHeader(value="Authorization", required = true) String inSessionId,
			@PathVariable(name="screenName") String inScreenName, @RequestBody MessageRequestDao req) throws NotFoundException{
		this.messageService.addMessage(this.sessionService.getSession(inSessionId), null, req.getPrivateScreenName(), req.getMessage());
	}

	@GetMapping("/user/messages")
	public List <MessageResponseDao> getPrivateMessages(@RequestHeader(value="Authorization", required = true) String inSessionId) throws NotFoundException{
		return this.messageService.getMessagesByPrivateUser(this.sessionService.getSession(inSessionId).getUserId());
	}
}
