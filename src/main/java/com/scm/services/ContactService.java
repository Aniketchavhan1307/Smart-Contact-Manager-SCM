package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService 
{
	
	Contact save(Contact contact);
	
	// update contact
	Contact update(Contact contact);
	
	//get Contact
	List<Contact> getAll();
	
	// get contact by id
	Contact getById(String id);
	
	//delete contact
	void delete(String id);
	
	//================== search contact ====================
	
	Page<Contact> searchByName(String name, int page, int size, String sortBy, String direction, User user);

	Page<Contact> searchByEmail(String email, int page, int size, String sortBy, String direction, User user);

	Page<Contact> searchByPhone(String phoneNumber, int page, int size, String sortBy, String direction, User user);
	
	
	// get contact by userId
	List<Contact> getByUserId(String userId);
	
	List<Contact> getByUser(User user);
	
	
	Page<Contact> getByUser(User user, int page, int size,  String sortBy, String direction);
	
	
}
