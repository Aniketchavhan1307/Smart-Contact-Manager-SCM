package com.scm.validators;

//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile>
{
	// SIZE
	private static final long MAX_FILE_SIZE = 1024 * 1024 * 2;     // 2MB

	// TYPE
	
	
	//HEIGHT
	
	//WIDTH
	
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) 
	{
		if (file == null || file.isEmpty())
		{
//			context.disableDefaultConstraintViolation();
//			context.buildConstraintViolationWithTemplate("file cannot be empty").addConstraintViolation();
//			
			return true;     // true because when we are updating the contact we need default image if we give false then we have to reEnter/re-upload the image
		}
		
		if (file.getSize() > MAX_FILE_SIZE)
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("file size should be less than 2MB").addConstraintViolation();
			
			return false;
		}
		
		
		
		
		// Resolution
//		try 
//		{
//			BufferedImage image = ImageIO.read(file.getInputStream());
//			
//			// here we can check the size or image resolution and etc 
//		} 
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		
		
		
		
		return true;
		
	}

	

}
