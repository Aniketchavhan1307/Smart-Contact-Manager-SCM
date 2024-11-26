package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController 
{
	@RequestMapping("/home")
	public String home()
	{
		return "home";

	}
	
	@RequestMapping("/about")
	public String about()
	{
		
		return "about";

	}
	
	@RequestMapping("/base")
	public String base()
	{
		System.err.println("Hello");
		
		return "base";

	}
	
	@RequestMapping("/services")
	public String services()
	{
		
		return "services";

	}
	
	@RequestMapping("/contact")
	public String contact()
	{
		
		return "contact";

	}
	
	@GetMapping("/login")
	public String login()
	{
		
		return new String("login");

	}
	
	@GetMapping("/signup")
	public String signup()
	{
		
		return "signup";

	}

}
