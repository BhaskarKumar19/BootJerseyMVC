package com.example.demo.rest.messanger.modals;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	String errorMessage;
	String Documentation;
	int errorCode;

	public ErrorMessage(){
		
	}
	public ErrorMessage(String errorMessage, String documentation, int errorCode) {
		this.errorMessage = errorMessage;
		Documentation = documentation;
		this.errorCode = errorCode;
	}

	public ErrorMessage(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDocumentation() {
		return Documentation;
	}

	public void setDocumentation(String documentation) {
		Documentation = documentation;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
