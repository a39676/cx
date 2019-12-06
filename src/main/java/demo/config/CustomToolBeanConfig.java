package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import dateTimeHandle.DateHandler;
import dateTimeHandle.LocalDateTimeHandler;
import demo.config.costom_component.BaseUtilCustom;
import demo.config.costom_component.SnowFlake;
import httpHandel.HttpUtil;
import ioHandle.FileUtilCustom;
import numericHandel.NumericUtilCustom;
import stringHandle.StringUtilCustom;

@Component
public class CustomToolBeanConfig {
	
	@Bean
	public BaseUtilCustom getBaseUtilCustom() {
		return new BaseUtilCustom();
	}
	
	@Bean
	public SnowFlake getSnowFlake() {
		return new SnowFlake();
	}

	@Bean
	public StringUtilCustom getStringUtilCustom() {
		return new StringUtilCustom();
	}
	
	@Bean
	public NumericUtilCustom getNumericUtilCustom() {
		return new NumericUtilCustom();
	}
	
	@Bean
	public FileUtilCustom getFileUtilCustom() {
		return new FileUtilCustom();
	}
	
	@Bean
	public HttpUtil getHttpUtil() {
		return new HttpUtil();
	}
	
	@Bean
	public DateHandler getDateHandler() {
		return new DateHandler();
	}
	
	@Bean
	public LocalDateTimeHandler getLocalDateTimeHandler() {
		return new LocalDateTimeHandler();
	}
}
