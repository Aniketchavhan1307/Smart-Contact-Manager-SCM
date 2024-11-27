package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserForm 
{
	@NotBlank(message = "UserName is required")
	@Size(min = 2 , message = "Min 2 character is required")
	private String name;
	
	@Email(message = "Invalid Email Address")                       // @Pattern(regexp = ) for custom email validation
	@NotBlank(message = "Email is required")
	
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 6 , message = "Min 6 character is required")
	private String password;
	
	@NotBlank(message = "About is required")
	private String about;
	
	@NotBlank(message = "Invalid phone number")
	@Size(min = 8, max = 12, message = "Phone number must be between 8 and 12 digits")
	private String phoneNumber;
	
	
	
}
