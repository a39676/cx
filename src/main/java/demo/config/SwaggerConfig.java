package demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${envName}")
	private String envName;

	@Bean
	public Docket api() {
		Docket d = null;
		if ("dev".contentEquals(envName)) {
			d = new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(apiInfo())
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any()).build();
		} else {
			d = new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(apiInfo())
					.select()
					.apis(RequestHandlerSelectors.basePackage("demo.movieInteraction.controller"))
					.paths(PathSelectors.any())
					.build();
		}
		return d;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("swagger title").description("swagger description")
				.termsOfServiceUrl("http://www.seekingdreamwork.site").version("1.1")
				.contact(new Contact("Acorn", "http://www.seekingdreamwork.site", "q39676@gmail.com")).build();
	}

}