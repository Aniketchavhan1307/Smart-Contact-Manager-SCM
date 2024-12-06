package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.Contact;
import com.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController
{
	// get contact of user
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/contact/{contactId}")
	public Contact getContact(@PathVariable String contactId)
	{
		System.err.println(contactService.getById(contactId));
		return contactService.getById(contactId);
	}
}
