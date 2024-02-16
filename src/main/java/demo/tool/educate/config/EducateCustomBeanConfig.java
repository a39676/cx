package demo.tool.educate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import demo.tool.educate.math.service.impl.SimpleAlgorithmGenerator;

@Component
public class EducateCustomBeanConfig {

	@Bean
	public SimpleAlgorithmGenerator getSimpleAlgorithmGenerator() {
		return new SimpleAlgorithmGenerator();
	}

}
