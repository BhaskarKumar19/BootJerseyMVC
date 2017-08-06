package com.example.demo.security.exceptions;

public class AuthMethodNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = -1762938684034272587L;

	public AuthMethodNotSupportedException(String str) {
		super(str);
	}

}
