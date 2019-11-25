package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;

import cloudinary.pojo.constant.CloudinaryConstant;
import cloudinary.service.CloudinaryFunction;
import cloudinary.util.CloudinaryCore;
import demo.base.system.service.impl.SystemConstantService;

@Component
public class CloudinaryConfig {
	
	@Autowired
	private SystemConstantService constantService;

	@Bean
	public CloudinaryFunction getCloudinaryFunction() {
		return new CloudinaryFunction();
	}
	
	@Bean
	public Cloudinary getCloudinaryCore() {
		return new CloudinaryCore().buildCloudinary(
				constantService.getValByName(CloudinaryConstant.cloudinaryNameStoreKey),
				constantService.getValByName(CloudinaryConstant.cloudinaryApiKeyStoreKey), 
				constantService.getValByName(CloudinaryConstant.cloudinaryApiSecretStoreKey));
	}
	
}
