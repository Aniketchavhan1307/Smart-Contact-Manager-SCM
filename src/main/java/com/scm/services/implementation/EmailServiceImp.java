package com.scm.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm.services.EmailService;

@Service
public class EmailServiceImp implements EmailService
{
	@Autowired
	private JavaMailSender eMailSender;
	
	@Value("${spring.mail.properties.domain_name}")
	private String domainName ;

	public void sendEmail(String to, String subject, String body)
	{
		SimpleMailMessage mailMessage= new SimpleMailMessage();
		
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		
		mailMessage.setFrom(domainName);
		
		eMailSender.send(mailMessage);
		System.err.println("Email send =================================");
	}

	public void sendEmailWihtHtml() 
	{
		 throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithHtml'");
	}

	public void sendEmailWithAttachment() 
	{
		 throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachment'");
	}

}
