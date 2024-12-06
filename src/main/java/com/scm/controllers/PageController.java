package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController 
{
	@Autowired
	private UserService service;
	
	@GetMapping("/")
	public String index()
	{
		return "redirect:/home";
	}
	
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
	public String signup(Model model)
	{
		UserForm form = new UserForm();
		
//	       form.setName("Aniket");
//	       form.setAbout("Hello");
		// default data  ........ we can pass default value to signup page with the help of model attribute
		
		model.addAttribute("userForm", form);
		
		return "signup";

	}
	
	
	@RequestMapping(value = "/do-register", method = RequestMethod.POST)
	public String processRegister(@Valid @ModelAttribute UserForm userForm , BindingResult result, HttpSession session)
	{
		System.err.println(userForm);
		
		// validation form ..... userform validation check karna hai 
		//  @valid and bindingResult use karna hai
		
		if (result.hasErrors())
		{
			return "signup";
		}
		
//		User user = User.builder()
//				.name(userForm.getName())
//				.email(userForm.getEmail())
//				.password(userForm.getPassword())
//				.about(userForm.getAbout())
//				.phoneNumber(userForm.getPhoneNumber())	
//				.profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fcommons.wikimedia.org%2Fwiki%2FFile%3ADefault_pfp.jpg&psig=AOvVaw3Qb0xFNtb_pjwwOOS0n4Rk&ust=1732739356012000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCPjE_qnr-okDFQAAAAAdAAAAABAE")
//				.build();
//		
			
	User user = new User();
	
	user.setName(userForm.getName());
	user.setEmail(userForm.getEmail());
	user.setPassword(userForm.getPassword());
	user.setAbout(userForm.getAbout());
	
	user.setEnabled(false);         // this statement will decide the user is de-active / disable
	
	user.setPhoneNumber(userForm.getPhoneNumber());
	user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fcommons.wikimedia.org%2Fwiki%2FFile%3ADefault_pfp.jpg&psig=AOvVaw3Qb0xFNtb_pjwwOOS0n4Rk&ust=1732739356012000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCPjE_qnr-okDFQAAAAAdAAAAABAE");
	
	
		User savedUser = service.saveUser(user);
		
		System.out.println("User saved..."+savedUser);
		
		
		// message = "Registration successful"
		
		// add the message so it display on web page that user signup successfully
		Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
		
		session.setAttribute("message", message);
		
		return "redirect:/login";

	}
	
//	@PostMapping(value = "/do-register")
//	public String processRegister()
//	{
//		System.err.println("Processing...............................");
//		return "signup";
//
//	}

}
