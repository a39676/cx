package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import cloudinary.service.CloudinaryFunction;

@Component
public class CloudinaryConfig {
	
//	@Autowired
//	private SystemConstantService constantService;

	@Bean
	public CloudinaryFunction getCloudinaryFunction() {
		return new CloudinaryFunction();
	}
	
//	@Bean
//	public Cloudinary getCloudinaryCore() {
//		return new CloudinaryCore().buildCloudinary(
//				constantService.getValByName(CloudinaryConstant.cloudinaryNameStoreKey),
//				constantService.getValByName(CloudinaryConstant.cloudinaryApiKeyStoreKey), 
//				constantService.getValByName(CloudinaryConstant.cloudinaryApiSecretStoreKey));
//	}
	
}
