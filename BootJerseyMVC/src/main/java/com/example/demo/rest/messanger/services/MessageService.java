package com.example.demo.rest.messanger.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.rest.messanger.exception.DataNotFound;
import com.example.demo.rest.messanger.modals.Message;
import com.example.demo.rest.messanger.stubs.DatabaseStub;

public class MessageService {

	private Map<Long, Message> messages = DatabaseStub.getAllMessages();

	public MessageService() {
		messages.put(1L, new Message(1, "Bhaskar", "Hi Welocme to rest tutorial"));
		messages.put(2L, new Message(2, "Bhaskar", "Hi Welocme to rest tutorial"));
		messages.put(3L, new Message(3, "Bhaskar", "Hi Welocme to rest tutorial"));
	}

	public List<Message> getMessages() {
		// why new ArrayList
		return new ArrayList<Message>(messages.values());

	}

	public List<Message> getMessagesPaginated(int start, int offset) {
		// why new ArrayList
		if(start<0 || offset> messages.size()){
			return new ArrayList<Message>(); 
		}
		List<Message> list=new ArrayList<Message>(messages.values());
		return list.subList(start, offset);

	}
	

	public Message getMessage(Long id) {
		Message msg = messages.get(id);
		if (msg == null) {
			throw new DataNotFound("No message associated with id: " + id);
		}
		return msg;
	}
	
	public Message addMessage(Message msg) {
		long id = messages.size() + 1;
		msg.setId((int)id); 
		messages.put(id, msg);
		return messages.get(id);
	}
	
	public Message updateMessage(Message msg) {
		messages.put((long)msg.getId(),msg);
		return messages.get((long)msg.getId());
	}
	
	public Message deleteMessage(long id) {
		return messages.remove(id);
	}
	
	
	

}
