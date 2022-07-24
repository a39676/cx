package demo.toyParts.educate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import demo.toyParts.educate.math.service.impl.SimpleAlgorithmGenerator;

@Component
public class EduacateCustomBeanConfig {

	@Bean
	public SimpleAlgorithmGenerator getSimpleAlgorithmGenerator() {
		return new SimpleAlgorithmGenerator();
	}

}
