package demo.interaction.wechatPay.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class WechatPayOptionService extends CommonService {

	@Value("${optionFilePath.wechatPay}")
	private String optionFilePath;

	private String privateKey;
	private String apiV3Key;
	private String merchantId;
	private String merchantSerialNumber;
	private Long jsApiTicketLivingSecond;

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getApiV3Key() {
		return apiV3Key;
	}

	public void setApiV3Key(String apiV3Key) {
		this.apiV3Key = apiV3Key;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantSerialNumber() {
		return merchantSerialNumber;
	}

	public void setMerchantSerialNumber(String merchantSerialNumber) {
		this.merchantSerialNumber = merchantSerialNumber;
	}

	public Long getJsApiTicketLivingSecond() {
		return jsApiTicketLivingSecond;
	}

	public void setJsApiTicketLivingSecond(Long jsApiTicketLivingSecond) {
		this.jsApiTicketLivingSecond = jsApiTicketLivingSecond;
	}

	@Override
	public String toString() {
		return "WechatPayOptionService [privateKey=" + privateKey + ", apiV3Key=" + apiV3Key + ", merchantId="
				+ merchantId + ", merchantSerialNumber=" + merchantSerialNumber + ", jsApiTicketLivingSecond="
				+ jsApiTicketLivingSecond + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			WechatPayOptionService tmp = new Gson().fromJson(jsonStr, WechatPayOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("Wechat Payoption loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Wechat Payoption loading error: " + e.getLocalizedMessage());
		}
	}
}
