package com.example.demo.rest.messanger.modals;

// @XmlRootElement not needed as it is child class
public class Link {
	String rel;
	String link;

	public Link() {

	}

	public Link(String rel, String link) {
		super();
		this.rel = rel;
		this.link = link;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
