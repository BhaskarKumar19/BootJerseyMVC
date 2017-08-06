package com.example.demo.rest.messanger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Component;

import com.example.demo.rest.messanger.modals.Link;
import com.example.demo.rest.messanger.modals.Message;
import com.example.demo.rest.messanger.services.MessageService;


@Path(value = "/messages")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	private MessageService ms = new MessageService();

	
	static{
		
		System.out.println("IN RESOURCE STATIC BLOCK::::::::::::::::::"); 
	}
	
	
	@GET
	public List<Message> getMessages(@QueryParam("start") int start, @QueryParam("offset") int offset) {
		if (start >= 0 && offset > 0) {
			return ms.getMessagesPaginated(start, offset);
		}
		return ms.getMessages();
	}

	@GET
	@Path(value = "/{msgid}")
	public Message getMessage(@PathParam("msgid") long id, @Context UriInfo uriInfo, @Context SecurityContext sc) {
		
		sc.getUserPrincipal();
		
		Message m = ms.getMessage(id);
		addMessageLink(id, uriInfo, m);
		return m;
	}

	private void addMessageLink(long id, UriInfo uriInfo, Message m) {
		URI uri = uriInfo.getBaseUriBuilder().path(MessageResource.class) // appending
																			// id
																			// to
																			// uri
				.path(Long.toString(id)).build();
		m.addLink(new Link("self", uri.toString()));
	}

	@POST
	public Response addMessage(Message msg, @Context UriInfo uriInfo) {
		Message message = ms.addMessage(msg);
		String msgId = String.valueOf(message.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(msgId) // appending id
																// to uri
				.build();
		return Response.created(uri) // adding location
				.entity(message) // adding body
				.build();

		// return ms.addMessage(msg);
	}

	@PUT
	@Path(value = "/{msgid}")
	public Message updateMessage(Message msg, @PathParam("msgid") int id) {
		msg.setId(id);
		return ms.updateMessage(msg);
	}

	@DELETE
	@Path(value = "/{msgid}")
	public Message deleteMessage(@PathParam("msgid") long id) {
		return ms.deleteMessage(id);
	}

}
