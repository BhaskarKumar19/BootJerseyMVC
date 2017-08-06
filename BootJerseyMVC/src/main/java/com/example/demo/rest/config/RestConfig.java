package com.example.demo.rest.config;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@WebServlet(loadOnStartup = 1)
@ApplicationPath("/api")
public class RestConfig extends ResourceConfig{
	
	@Autowired
	public void JerseyConfig() {
       packages("com.example.demo.rest.messanger");
	   //register(MessageResource.class);
	   System.out.println("called::::::::");
    }
}
