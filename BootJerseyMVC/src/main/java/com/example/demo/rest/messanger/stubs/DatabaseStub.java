package com.example.demo.rest.messanger.stubs;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.rest.messanger.modals.Message;

public class DatabaseStub {

	private static Map<Long, Message> messages = new HashMap<>();

	public static Map<Long, Message> getAllMessages() {
		return messages;
	}

}
