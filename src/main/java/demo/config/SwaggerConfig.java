//package demo.config;
//
//import org.springdoc.core.GroupedOpenApi;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//
//import demo.base.system.service.impl.SystemOptionService;
//
//public class SwaggerConfig {
//
//	@Autowired
//	private SystemOptionService constantService;
//
//	@Bean
//	public GroupedOpenApi publicApi() {
//		String envName = constantService.getEnvName();
//		if ("dev".equals(envName)) {
//			return GroupedOpenApi.builder()
//					.group("")
//					.pathsToMatch("/**")
//					.build();
//		} else {
//			return GroupedOpenApi.builder()
//					.group("")
//					.pathsToMatch("/notExists/**")
//					.build();
//		}
//	}
//
//}