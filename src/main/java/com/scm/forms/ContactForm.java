package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactForm 
{
	@NotBlank(message = "Name is required")
	private String name;
	
	@Email(message = "Invalid Email Address [example@gmail.com]")
	@NotBlank(message = "Email is required" )
	private String email;
	
	@NotBlank(message = "Phone number is required")
	 @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
	private String phoneNumber;
	
	@NotBlank(message = "Address is required")
	private String address;
	
	private String description;
	
	private boolean favorite;
	
	private String websiteLink;
	
	private String linkedinLink;
	
	
	//  create annotation for file validate
	// size
	// resolution
	//type
	
	@ValidFile(message ="Invalid file" )								// this is our custom annotation present in validators package
	private MultipartFile contactImage;
	
	private String picture;
	
}
