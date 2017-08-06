package com.example.demo.security.models;

public class UserCredentials {

	private String email;
	private boolean enabled;
	private String password;
	private String activateToken;

	public UserCredentials(String email, boolean enabled, String password) {
		super();
		this.email = email;
		this.enabled = enabled;
		this.password = password;
	}

	public UserCredentials(String email, boolean enabled, String password, String activateToken) {
		super();
		this.email = email;
		this.enabled = enabled;
		this.password = password;
		this.activateToken = activateToken;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActivateToken() {
		return activateToken;
	}

	public void setActivateToken(String activateToken) {
		this.activateToken = activateToken;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String resetToken;

}
