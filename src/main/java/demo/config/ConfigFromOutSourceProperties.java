package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ConfigFromOutSourceProperties {

	private static final String MAIN_FOLDER_PATH = "/home/u2/cx/optionFile";

	@Bean
	public PropertySourcesPlaceholderConfigurer getDatabaseConfig() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

		properties.setLocation(new FileSystemResource(MAIN_FOLDER_PATH + "/databaseConfig.properties"));
		properties.setIgnoreResourceNotFound(false);
		return properties;
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer getRabbitMqConfig() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
		properties.setLocation(new FileSystemResource(MAIN_FOLDER_PATH + "/rabbitMqConfig.properties"));
		properties.setIgnoreResourceNotFound(false);
		return properties;
	}

}