package com.youming.sampledrools.domain;

public class Message {
	
	public enum MessageType {
        HI,
        GOODBYE,
        CHAT
    }
 
    private MessageType messageType;
    private String target;
    
    
	public Message(MessageType messageType, String target) {
		// TODO Auto-generated constructor stub
		this.messageType = messageType;
		this.target = target;
	}
	
	
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
    
    
}
