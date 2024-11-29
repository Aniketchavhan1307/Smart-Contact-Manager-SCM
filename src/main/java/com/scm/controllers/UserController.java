package com.scm.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.helper.Helper;


@Controller
@RequestMapping("/user")
public class UserController 
{
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	// user dashboard page
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String userDashboard() 
	{
		System.err.println("user dashboard..........");
		return "user/dashboard";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String userProfile(Authentication authentication) 
	{
		String userName = Helper.getEmailOfLoggedInUser(authentication);
		logger.info("User info {} :", userName);
		
		System.err.println("user profile.........." + userName);
		return "user/profile";
	}
	
	
	
	// user view contacts
	
	// user edit contact
	
	//user delete contact
}
