package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import demo.config.customComponent.OptionFilePathConfigurer;

@Configuration
public class Config {


	@Bean
	public PropertySourcesPlaceholderConfigurer getSystemConfig() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

		properties.setLocation(new FileSystemResource(OptionFilePathConfigurer.ROOT + "/CxConfig.properties"));
		properties.setIgnoreResourceNotFound(true);
		return properties;
	}

}