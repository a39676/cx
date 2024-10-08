package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import demo.config.customComponent.BaseUtilCustom;
import demo.config.customComponent.SnowFlake;
import demo.config.customComponent.Tess;
import finance.common.tool.KLineToolUnit;
import mail.service.MailToolService;
import tool.service.TimeBasedOneTimePassword;
import toolPack.complexTool.ChinaMainLandIdNumGenerator;
import toolPack.dateTimeHandle.DateHandler;
import toolPack.dateTimeHandle.LocalDateTimeAdapter;
import toolPack.dateTimeHandle.LocalDateTimeHandler;
import toolPack.encryptHandle.EncryptUtil;
import toolPack.httpHandel.HttpUtil;
import toolPack.imgbb.service.ImgbbUtil;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.numericHandel.NumericUtilCustom;
import toolPack.qrcode.QrCodeDecode;
import toolPack.qrcode.QrCodeGenerator;
import toolPack.stringHandle.RandomChineseNameGenerator;
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
	public RandomChineseNameGenerator getRandomChineseNameGenerator() {
		return new RandomChineseNameGenerator();
	}

	@Bean
	public ChinaMainLandIdNumGenerator getChinaMainLandIdNumGenerator() {
		return new ChinaMainLandIdNumGenerator();
	}

	@Bean
	public EncryptUtil getEncryptUtil() {
		return new EncryptUtil();
	}

	@Bean
	@Scope("singleton")
	public Tess getTess() {
		Tess t = new Tess();
		return t;
	}

	@Bean
	public ImgbbUtil getImgbbUtil() {
		return new ImgbbUtil();
	}
	
	@Bean
	public KLineToolUnit getKLineToolUnit() {
		return new KLineToolUnit();
	}

	@Bean
	public TimeBasedOneTimePassword getTimeBasedOneTimePassword() {
		return new TimeBasedOneTimePassword();
	}
}
