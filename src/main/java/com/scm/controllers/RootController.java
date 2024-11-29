package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController 
{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addLoggedlnUserInformation(Model model , Authentication authentication)
	{
		if (authentication == null) 
		{
			return;
		}
		
		String userName = Helper.getEmailOfLoggedInUser(authentication);
		
		// fetch the data from database
		User user= userService.getUserByEmail(userName);
	
			model.addAttribute("loggedlnUser", user);
		
		System.err.println("user profile.........." + user);
		
	}
}
