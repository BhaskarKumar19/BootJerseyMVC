package com.example.demo.rest.messanger.modals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
	int id;
	String author;
	String message;
	Date date;
	List<Link> links = new ArrayList<Link>();

	public Message() {
	}

	public Message(int id, String author, String message, Date date) {
		super();
		this.id = id;
		this.author = author;
		this.message = message;
		this.date = date;
	}

	public Message(int id, String author, String message) {
		super();
		this.id = id;
		this.author = author;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(Link link) {
		this.links.add(link);
	}
}
