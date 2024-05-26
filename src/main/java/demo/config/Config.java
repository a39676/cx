package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class Config {

	private static final String MAIN_FOLDER_PATH = "/home/u2/cx/optionFile";

	@Bean
	public PropertySourcesPlaceholderConfigurer getSystemConfig() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

		properties.setLocation(new FileSystemResource(MAIN_FOLDER_PATH + "/systemConfig.properties"));
		properties.setIgnoreResourceNotFound(true);
		return properties;
	}

}