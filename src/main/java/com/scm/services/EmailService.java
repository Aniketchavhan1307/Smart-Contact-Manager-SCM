package com.scm.services;

public interface EmailService 
{

	// send email
	void sendEmail(String to, String subject, String body);
	
	void sendEmailWihtHtml();
	
	void sendEmailWithAttachment();
}
