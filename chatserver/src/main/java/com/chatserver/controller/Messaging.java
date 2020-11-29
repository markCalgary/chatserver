package com.chatserver.controller;

import java.math.BigInteger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatserver.dao.MessageDao;
import com.chatserver.service.MessageService;

@RestController
public class Messaging {

	private SimpMessagingTemplate simpMessageTemplate;
	private MessageService messageService;
	
	@Autowired
	public Messaging (SimpMessagingTemplate inSimpMessagingTemplate) {
		this.simpMessageTemplate = inSimpMessagingTemplate;
	}
	
	@PostMapping("/room/{roomId}/messages")
	public void sendToRoom(@PathVariable(name="roomId") BigInteger inRoomId, @RequestBody MessageDao req) {
		this.messageService.addMessage(null, inRoomId, null, req.getMessage());
		
	}
	
	@PostMapping("/user/{screenName}/messages")
	public void sendToUser(@PathVariable(name="screenName") String inScreenName, @RequestBody MessageDao req) {
		System.out.println("abcdef");
	    this.simpMessageTemplate.convertAndSend("/queue/"+"1", req);
	}
}
