package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contact")
public class ContactController 
{
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageService imageService;

	//Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	
	// add contact page handler
	@RequestMapping("/add")
	public String addContactView(Model model)
	{
		ContactForm contactForm = new ContactForm();
		model.addAttribute("contactForm", contactForm);
		
		return "user/add_contact";
	}
	
	@RequestMapping(value = "/add" , method = RequestMethod.POST)
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,Authentication authentication, HttpSession httpSession)
	{
		// validating the data 
		
		if (result.hasErrors())
		{
			httpSession.setAttribute("message", Message.builder()
					.content("please correct the following errors" )
					.type(MessageType.red)
					.build());
			return "user/add_contact";
		}
		
		
		// get the user
		 String username = Helper.getEmailOfLoggedInUser(authentication); 
		 
		 User user	= userService.getUserByEmail(username);
		
		
		
	
			// form ---> contact
			Contact contact = new Contact();

			contact.setName(contactForm.getName());
		contact.setFavorite(contactForm.isFavorite());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		
		contact.setUser(user);
		contact.setLinkdinLink(contactForm.getLinkedinLink());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		
		if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty())
		{
			// process the image -----> upload to cloud
			 
			 String filename = UUID.randomUUID().toString(); 		// generating filename or url random

			String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
			 
			
			contact.setPicture(fileURL);
			contact.setCloudinaryImagePublicId(filename);
			
		}
		
		
		
		// process the form data
		contactService.save(contact);
		
		
		// 4. set message to displayed on view
		
		httpSession.setAttribute("message", Message.builder()
				.content("You have successfully added a new contact" )
				.type(MessageType.green)
				.build());
		
		System.out.println(contactForm);
		return "redirect:/user/contact/add";
	}
	
	
	
	
	
	// view Contacts
	// method is by default /user/contant ... this method is responsible for displaying the contacts...
	
	@RequestMapping
	public String viewContacts(  @RequestParam(value ="page", defaultValue = "0") int page,
									@RequestParam(value ="size", defaultValue = AppConstants.PAGE_SIZE +"") int size,
									@RequestParam(value ="sortBy", defaultValue = "name") String sortBy,
									@RequestParam(value ="direction", defaultValue = "asc") String direction,
								Model model , Authentication authentication)
	{
		String username = Helper.getEmailOfLoggedInUser(authentication);
		
		User user = userService.getUserByEmail(username);
		// load all the user contacts
		
		Page<Contact> pageContact	= contactService.getByUser(user, page, size, sortBy, direction);
		
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		
		 model.addAttribute("pageContacts", pageContact);
		 
		 model.addAttribute("contactSearchForm", new ContactSearchForm());
		
	return "user/contacts";
	}
	
	
	
	// Search contact 
	@RequestMapping("/search")
	public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
								@RequestParam(value="size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
								@RequestParam(value="page",defaultValue = "0") int page,
								@RequestParam(value="sortBy",defaultValue ="name") String sortBy,
								@RequestParam(value="direction",defaultValue ="asc") String direction,
								Model model,
								Authentication authentication
									)
	{
		var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
		
		Page<Contact> pageContact = null;
		
		if (contactSearchForm.getField().equalsIgnoreCase("name")) 
		{
			pageContact = contactService.searchByName(contactSearchForm.getValue(), page, size, sortBy, direction , user);
			
		}
		else if(contactSearchForm.getField().equalsIgnoreCase("email"))
		{
			pageContact = contactService.searchByEmail(contactSearchForm.getValue(), page, size, sortBy, direction, user);
		}
		else if(contactSearchForm.getField().equalsIgnoreCase("phone"))
		{
			pageContact = contactService.searchByPhone(contactSearchForm.getValue(), page, size, sortBy, direction, user);
		}
		
		
		model.addAttribute("contactSearchForm", contactSearchForm);
		
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		
		model.addAttribute("pageContacts",pageContact);
		
		
		
		//System.err.println(pageContact);
		
		return "user/search";
	}
	
	
	// delete contact
	
	@RequestMapping("/delete/{contactId}")
	public String deleteContact(@PathVariable("contactId") String contactId, HttpSession session)
	{
		System.err.println("Delete : "+ contactId);
		
		contactService.delete(contactId);
		
		session.setAttribute("message", Message.builder()
				.content("Contact is Deleted Successfully").type(MessageType.green).build() );
		
		return "redirect:/user/contact";
	}
	
	
	//=================================================================
	// update contact from view
	
	@GetMapping("/view/{contactId}")
	public String updateContactFormView(@PathVariable("contactId") String contactId , Model model)
	{
		
		var contact = contactService.getById(contactId);
		
		ContactForm contactForm = new ContactForm();
		
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setFavorite(contact.getFavorite());
		contactForm.setWebsiteLink(contact.getWebsiteLink());
		contactForm.setLinkedinLink(contact.getLinkdinLink());
		contactForm.setPicture(contact.getPicture());
		
	
		model.addAttribute("contactId",contactId);
		model.addAttribute("contactForm", contactForm);
		
		return "user/update_contact_view";
	}
	
	
	@RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
	public String updateContact(@PathVariable("contactId") String contactId , 
								@Valid @ModelAttribute ContactForm contactForm, 
								BindingResult bindingResult,
								Model model , HttpSession session )
	{
		// bindingResult is for checking the validation
		
		if (bindingResult.hasErrors()) 
		{
			return "user/update_contact_view";
		}
		
		
		//update the contact
		var contact = contactService.getById(contactId);
		
		contact.setId(contactId);
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setFavorite(contactForm.isFavorite());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setLinkdinLink(contactForm.getLinkedinLink());
		//contact.setPicture(contactForm.getPicture());
		
		// process image
		
		if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty())
		{
			String fileName = UUID.randomUUID().toString();
			String imageURL = imageService.uploadImage(contactForm.getContactImage(), fileName);
			
			contact.setCloudinaryImagePublicId(fileName);
			contact.setPicture(imageURL);
			contactForm.setPicture(imageURL);
			
		}
		
		
		
	
		
	var updateContact = contactService.update(contact);
	
	session.setAttribute("message", Message.builder().content("Contact Updated Successfully").type(MessageType.green).build());
		
		return "redirect:/user/contact";
	}
	
	
}
