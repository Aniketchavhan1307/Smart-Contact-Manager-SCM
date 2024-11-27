package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user")
public class UserController 
{
	// user dashboard page
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String userDashboard() 
	{
		System.err.println("user dashboard..........");
		return "user/dashboard";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String userProfile() 
	{
		System.err.println("user profile..........");
		return "user/profile";
	}
	
	
	
	// user view contacts
	
	// user edit contact
	
	//user delete contact
}
