package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import cloudinary.service.CloudinaryFunction;

@Component
public class CloudinaryConfig {
	
	@Bean
	public CloudinaryFunction getCloudinaryFunction() {
		return new CloudinaryFunction();
	}
	
}
