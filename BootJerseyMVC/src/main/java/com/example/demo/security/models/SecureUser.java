package com.example.demo.security.models;

public class SecureUser {
	String userName;
	String role;
	boolean active;

	public SecureUser(String userName, boolean active) {
		super();
		this.userName = userName;
		this.active = active;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
