package demo.thirdPartyAPI.wechat.service.impl;

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
public class WechatOptionService extends CommonService {

	@Value("${optionFilePath.wechat}")
	private String optionFilePath;

	private String sdkMainUrl;
	private String appTestId;
	private String appTestSecret;
	private String appId1;
	private String appSecret1;
	private Long accessTokenLivingSecond;

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

	@Override
	public String toString() {
		return "WechatOptionService [sdkMainUrl=" + sdkMainUrl + ", appTestId=" + appTestId + ", appTestSecret="
				+ appTestSecret + ", appId1=" + appId1 + ", appSecret1=" + appSecret1 + ", accessTokenLivingSecond="
				+ accessTokenLivingSecond + "]";
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
			WechatOptionService tmp = new Gson().fromJson(jsonStr, WechatOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("Open AI option loaded");
		} catch (Exception e) {
			log.error("Open AI option loading error: " + e.getLocalizedMessage());
		}
	}
}
