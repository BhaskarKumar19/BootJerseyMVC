package com.example.demo.rest.messanger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.demo.rest.messanger.modals.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage em=new ErrorMessage(ex.getMessage(), "please visit API documentation", Status.INTERNAL_SERVER_ERROR.getStatusCode());
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(em)
				.build();
	}

}
