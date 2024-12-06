package com.scm.services.implementation;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helper.AppConstants;
import com.scm.services.ImageService;

@Service
public class ImageServiceImp implements ImageService
{
	private Cloudinary cloudinary;
	
	public ImageServiceImp(Cloudinary cloudinary)     // here we are doing constructor injection so we don't need @autowired annotation this is another way 
	{
		this.cloudinary = cloudinary;
	}

	@Override
	public String uploadImage(MultipartFile contactImage, String filename) 
	{
		// code for uploading image
		//String filename = UUID.randomUUID().toString();
		
		try 
		{
			byte [] data = new byte[contactImage.getInputStream().available()];
			
			contactImage.getInputStream().read(data);
			cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", filename));
		
			return this.getUrlFromPublicId(filename);
		
		} 
		catch (IOException e)
		{
			
			e.printStackTrace();
			return null;
		}
		
		// return "image uploaded successfully"......URL
		
		
	}

	@Override
	public String getUrlFromPublicId(String publicId) 
	{
		return cloudinary.url().transformation
				(new Transformation<>().width(AppConstants.CONTACT_IMAGE_WIDTH).height(AppConstants.CONTACT_IMAGE_HEIGHT).crop(AppConstants.CONTACT_IMAGE_CROP)).
				generate(publicId);
	}

	

}
