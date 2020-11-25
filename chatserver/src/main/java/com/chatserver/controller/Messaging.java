package com.chatserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chatserver.dao.Message;

@Controller
public class Messaging {

	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public Message send(Message message) throws Exception {
	    String time = ""; // new SimpleDateFormat("HH:mm").format(new Date());
	    return new Message(message.getFrom(), message.getText(), time);
	}
}
