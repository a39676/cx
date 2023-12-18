package demo.interaction.wechat.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.costom_component.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class WechatOptionService extends CommonService {

	private String managerCode;
	private String sdkMainUrl;
	private Long accessTokenLivingSecond;
	private String aesKey;
	private String aesInitVector;
	private String appTestId;
	private String appTestSecret;
	private String appId1;
	private String appSecret1;
	private String originOpenId1;

	@Override
	public String toString() {
		return "WechatOptionService [managerCode=" + managerCode + ", sdkMainUrl=" + sdkMainUrl
				+ ", accessTokenLivingSecond=" + accessTokenLivingSecond + ", aesKey=" + aesKey + ", aesInitVector="
				+ aesInitVector + ", appTestId=" + appTestId + ", appTestSecret=" + appTestSecret + ", appId1=" + appId1
				+ ", appSecret1=" + appSecret1 + ", originOpenId1=" + originOpenId1 + "]";
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getSdkMainUrl() {
		return sdkMainUrl;
	}

	public void setSdkMainUrl(String sdkMainUrl) {
		this.sdkMainUrl = sdkMainUrl;
	}

	public String getAppTestId() {
		return appTestId;
	}

	public void setAppTestId(String appTestId) {
		this.appTestId = appTestId;
	}

	public String getAppTestSecret() {
		return appTestSecret;
	}

	public void setAppTestSecret(String appTestSecret) {
		this.appTestSecret = appTestSecret;
	}

	public String getAppId1() {
		return appId1;
	}

	public void setAppId1(String appId1) {
		this.appId1 = appId1;
	}

	public String getAppSecret1() {
		return appSecret1;
	}

	public void setAppSecret1(String appSecret1) {
		this.appSecret1 = appSecret1;
	}

	public Long getAccessTokenLivingSecond() {
		return accessTokenLivingSecond;
	}

	public void setAccessTokenLivingSecond(Long accessTokenLivingSecond) {
		this.accessTokenLivingSecond = accessTokenLivingSecond;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getAesInitVector() {
		return aesInitVector;
	}

	public void setAesInitVector(String aesInitVector) {
		this.aesInitVector = aesInitVector;
	}

	public String getOriginOpenId1() {
		return originOpenId1;
	}

	public void setOriginOpenId1(String originOpenId1) {
		this.originOpenId1 = originOpenId1;
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.WECHAT);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.WECHAT);
			WechatOptionService tmp = new Gson().fromJson(jsonStr, WechatOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("WechatOptionService option loaded");
		} catch (Exception e) {
			log.error("WechatOptionService option loading error: " + e.getLocalizedMessage());
		}
	}

}
