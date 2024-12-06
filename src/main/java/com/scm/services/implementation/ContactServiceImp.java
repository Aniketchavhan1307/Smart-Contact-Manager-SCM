package com.scm.services.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImp implements ContactService 
{
	@Autowired
	private ContactRepo contactRepo;
	
	public Contact save(Contact contact)
	{
		String contactId = UUID.randomUUID().toString();
		contact.setId(contactId);
		
		return contactRepo.save(contact);
	}

	public Contact update(Contact contact) 
	{
		var oldContact = contactRepo.findById(contact.getId()).orElseThrow(()-> 
						new ResourceNotFoundException("contact not found"));
		
		oldContact.setName(contact.getName());
		oldContact.setEmail(contact.getEmail());
		oldContact.setPhoneNumber(contact.getPhoneNumber());
		oldContact.setAddress(contact.getAddress());
		oldContact.setDescription(contact.getDescription());
		oldContact.setFavorite(contact.getFavorite());
		oldContact.setWebsiteLink(contact.getWebsiteLink());
		oldContact.setLinkdinLink(contact.getLinkdinLink());
		oldContact.setPicture(contact.getPicture());
		
		oldContact.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
		
		return contactRepo.save(oldContact);
	}

	public List<Contact> getAll() {
		return contactRepo.findAll();
	}

	public Contact getById(String id) 
	{
		return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id  "+ id));
	}

	public void delete(String id) 
	{
		var contact = getById(id);              //contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id  "+ id));
		
		contactRepo.delete(contact);
	}

	

	public List<Contact> getByUserId(String userId) 
	{
		
		return contactRepo.findByUserId(userId);
	}

	@Override
	public List<Contact> getByUser(User user) 
	{
		return contactRepo.findByUser(user);
	}

	@Override
	public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction)
	{
		Sort sort = direction.equals("desc") ? 
				Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		var pageable = PageRequest.of(page, size, sort);   // PageRequest is a class with page and size and give pageable response
		
		
		return contactRepo.findByUser(user, pageable);
	}
	
	
	

	public Page<Contact> searchByName(String name, int page, int size, String sortBy, String direction, User user) 
	{
		Sort sort = direction.equals("desc") ? 
				Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		var pageable = PageRequest.of(page, size, sort);   // PageRequest is a class with page and size and give pageable response
		
		return contactRepo.findByUserAndNameContaining(user,name, pageable);
	}

	
	public Page<Contact> searchByEmail(String email, int page, int size, String sortBy, String direction, User user) 
	{
		Sort sort = direction.equals("desc") ? 
				Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		var pageable = PageRequest.of(page, size, sort);   // PageRequest is a class with page and size and give pageable response
		
		return contactRepo.findByUserAndEmailContaining(user,email, pageable);
	}

	
	public Page<Contact> searchByPhone(String phoneNumber, int page, int size, String sortBy, String direction, User user) 
	{
		Sort sort = direction.equals("desc") ? 
				Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		var pageable = PageRequest.of(page, size, sort);   // PageRequest is a class with page and size and give pageable response
		
		return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumber, pageable);
	}
	

	

}
