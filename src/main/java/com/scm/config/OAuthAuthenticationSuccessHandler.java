package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
	@Autowired
	private UserRepo repo;

	Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
	
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException 
	{
		logger.info("OAuthAuthenticationSuccessHandler");
	//	response.sendRedirect("/home");           or we can use 
		
		
		//we have to identify the user provider
		
	var	oauth2AuthenticationToken	= (OAuth2AuthenticationToken) authentication;
	
	String authorizedClientRegistrationID= oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
		
	// type casting to default outh2user
	DefaultOAuth2User oauthUser 	= (DefaultOAuth2User) authentication.getPrincipal();

	oauthUser.getAttributes().forEach((key,value)->{
		logger.info("{} => {}", key,value);
	});
	
	User user = new User();
	
	user.setUserId(UUID.randomUUID().toString());
	user.setRoleList(List.of(AppConstants.ROLE_USER));
	user.setEmailVarified(true);
	user.setEnabled(true);
	
	if (authorizedClientRegistrationID.equalsIgnoreCase("google")) 
	{
		// google login
		
			user.setEmail(oauthUser.getAttribute("email").toString());
			user.setProfilePic(oauthUser.getAttribute("picture").toString());
			user.setName(oauthUser.getAttribute("name").toString());
			user.setProviderUserId(oauthUser.getName());
			user.setProvider(Providers.GOOGLE);
			user.setPassword("dummy_password");
			user.setAbout("This account is created using google");
		
		
	}
	else if(authorizedClientRegistrationID.equalsIgnoreCase("github"))
	{
		// github login

		String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                : oauthUser.getAttribute("login").toString().toLowerCase() + "@gmail.com";
	
		String picture = oauthUser.getAttribute("avatar_url").toString();
		String name = oauthUser.getAttribute("login").toString();
		String providerUserId = oauthUser.getName();
		
		user.setEmail(email);
		user.setProfilePic(picture);
		user.setName(name);
		user.setProviderUserId(providerUserId);
		user.setProvider(Providers.GITHUB);
		user.setPassword("dummy_password");
		user.setAbout("This account is created using github");

	}
	else
	{
		logger.info("OAuthAuthenticationSuccessHandler : unknown provider");
	}
	
		
		
		
User user2 = repo.findByEmail(user.getEmail()).orElse(null);
	
	if (user2 == null) 
	{
		repo.save(user);
		logger.info("User saved: {}", user.getEmail());
	} 
	else
	{
		logger.info("User already exists: {}", user.getEmail());
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// data ...we got the data now we have to save it in database

	/*
	 * *	
	 
	DefaultOAuth2User user 	= (DefaultOAuth2User) authentication.getPrincipal();
//	
	// here we are checking the data given by google with oauth2
	
//	logger.info(user.getName());
//
//	user.getAttributes().forEach((key,value)->{
//		logger.info("{} => {}", key,value);
//	});
//	
//	logger.info(user.getAuthorities().toString());
	
	// retriving the data 
	
	String  email = user.getAttribute("email").toString();
	String  name = user.getAttribute("name").toString();
	String  picture = user.getAttribute("picture").toString();

	// create user and save to database
		
	User user2 = new User();
	user2.setEmail(email);
	user2.setName(name);
	user2.setProfilePic(picture);
	user2.setPassword("password");          // this is dummy password no need of password while login with google or github
	user2.setUserId(UUID.randomUUID().toString());  
	user2.setProvider(Providers.GOOGLE);
	user2.setEnabled(true);
	
	user2.setEmailVarified(true);
	user2.setProviderUserId(user.getName());
	user2.setRoleList(List.of(AppConstants.ROLE_USER));
	user2.setAbout("This account is created using google");
	
	User user3 = repo.findByEmail(email).orElse(null);
	
	if (user3 == null) 
	{
		repo.save(user2);
		logger.info("User saved: {}", email);
	} 
	else
	{
		logger.info("User already exists: {}", email);
	}
	
	*/
	
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}
	
	
}
