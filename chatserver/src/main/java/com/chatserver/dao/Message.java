package com.chatserver.dao;

public class Message {
	private String from;
    private String text;
    
	public Message(String inFrom, String inText, String time) {
		this.from = inFrom;
		this.text = inText;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
    
    
}
