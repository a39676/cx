package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import demo.config.costom_component.BaseUtilCustom;
import demo.config.costom_component.SnowFlake;
import demo.config.costom_component.Tess;
import mail.service.MailToolService;
import toolPack.dateTimeHandle.DateHandler;
import toolPack.dateTimeHandle.LocalDateTimeAdapter;
import toolPack.dateTimeHandle.LocalDateTimeHandler;
import toolPack.httpHandel.HttpUtil;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.numericHandel.NumericUtilCustom;
import toolPack.qrcode.QrCodeDecode;
import toolPack.qrcode.QrCodeGenerator;
import toolPack.stringHandle.StringUtilCustom;

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
	
	@Bean 
	public MailToolService getMailToolService() {
		return new MailToolService();
	}
	
	@Bean
	public QrCodeGenerator getQrCodeGenerator() {
		return new QrCodeGenerator();
	}
	
	@Bean
	public QrCodeDecode getQrCodeDecode() {
		return new QrCodeDecode();
	}
	
	@Bean
	public LocalDateTimeAdapter getLocalDateTimeAdapter() {
		return new LocalDateTimeAdapter();
	}
	
	@Bean
	@Scope("singleton")
	public Tess getTess() {
		Tess t = new Tess();
		return t;
	}
}
