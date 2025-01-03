package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig
{
	
	
	 @Autowired
	    private Environment environment;

	    @Bean
	    public Cloudinary cloudinary() {
	        String cloudName = environment.getProperty("cloudinary.cloud.name");
	        String apiKey = environment.getProperty("cloudinary.api.key");
	        String apiSecret = environment.getProperty("cloudinary.api.secret");

	        return new Cloudinary(ObjectUtils.asMap(
	                "cloud_name", cloudName,
	                "api_key", apiKey,
	                "api_secret", apiSecret
	        ));
	
	    }
	        
	        
	
	
	
	
//	@Value("${cloudinary.cloud.name}")				// the details are fetch from environment variable
//	private String cloudName ;
//	
//	@Value("${cloudinary.api.key}")
//	private String apiKey ;
//	
//	@Value("${cloudinary.api.secret}")
//	private String apiSecret ;
//	
//	
//	@Bean
//	public Cloudinary cloudinary()
//	{
//		
//		return new Cloudinary(ObjectUtils.asMap("cloud_name",cloudName,
//				"api_key",apiKey, 
//				"api_secret", apiSecret )
//				);
//	}
}
