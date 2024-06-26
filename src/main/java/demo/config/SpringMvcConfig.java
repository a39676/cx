package demo.config;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import demo.config.customComponent.LocalDateTimeDeserializerCustomer;
import toolPack.dateTimeHandle.DateTimeUtilCommon;

@EnableWebMvc // <mvc:annotation-driven />
@Configuration
@EnableScheduling // 开启定时任务支持
public class SpringMvcConfig implements WebMvcConfigurer {

	// 添加 filter 需转至 SecurityConfig

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static_resources/**").addResourceLocations("classpath:/static_resources/");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();

		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);

		JavaTimeModule javaTimeModule = new JavaTimeModule();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeUtilCommon.normalDateTimeFormat);
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DateTimeUtilCommon.normalDateFormat);
		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializerCustomer());
		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
		javaTimeModule.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
		objectMapper.registerModule(javaTimeModule);

		jackson2HttpMessageConverter.setObjectMapper(objectMapper);

		converters.add(jackson2HttpMessageConverter);
	}

//	@Bean(name = "multipartResolver")
//	public CommonsMultipartResolver createMultiparResolver() {
//		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		resolver.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
//		resolver.setMaxUploadSize(30L * 1024 * 1024 * 1024); // 30m
//		resolver.setMaxInMemorySize(40960);
//
//		return resolver;
//	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(1);
		return viewResolver;
	}

}
