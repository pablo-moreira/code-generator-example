package com.github.cg.example.jsf.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

public class AppConversationCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;
		
	@PostConstruct
    public void initConversation() {
        if (this.conversation.isTransient()) {
        	this.conversation.begin();
        }
    }
}
