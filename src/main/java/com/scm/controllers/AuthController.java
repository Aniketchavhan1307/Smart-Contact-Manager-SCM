package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController 
{
	@Autowired
	private UserRepo userRepo;
	
	
	
	
	// verify email
	
	@GetMapping("/verify-email")
	public String verifyEmail(@RequestParam("token") String token, HttpSession httpSession)
	{
		User user= userRepo.findByEmailToken(token).orElse(null);
		
		if (user != null)
		{
				if (user.getEmailToken().equals(token)) 
				{
					user.setEmailVarified(true);
					user.setEnabled(true);
					userRepo.save(user);
					
					httpSession.setAttribute("message", Message.builder()
							.content("Your email is verified. Now you can Login !!!")
							.type(MessageType.green)
							.build());
					return "success_page";
				}
				
				httpSession.setAttribute("message", Message.builder()
						.content("Something went wrong email not verified !!!")
						.type(MessageType.red)
						.build());
				
				return "error_page";
		}
		
		httpSession.setAttribute("message", Message.builder()
					.content("Something went wrong email not verified !!!")
					.type(MessageType.red)
					.build());
		
		return "error_page";
	}
}
