package demo.base.system.service.impl;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class SystemConstantService extends CommonService {

	@Value("${optionFilePath.system}")
	private String optionFilePath;

	private String aesKey = null;
	private String aesInitVector = null;
	private String envName = null;
	private String emailD = null;
	private String emailC = null;
	private Integer maxAttempts = null;
	private String normalWebSiteTitle = null;
	private String normalSubheading = null;
	private String webSiteTitle2 = null;
	private String subheading2 = null;
	private Boolean isJobing = null;
	private Boolean isDebuging = null;
	private String fakeFTPHome = null;
	private String homepageAnnouncementStr = null;

	public String getAesKey() {
		return aesKey;
	}

	public String getAesInitVector() {
		return aesInitVector;
	}

	public String getEnvName() {
		return envName;
	}

	public String getEmailD() {
		return emailD;
	}

	public String getEmailC() {
		return emailC;
	}

	public Integer getMaxAttempts() {
		return maxAttempts;
	}

	public String getNormalWebSiteTitle() {
		return normalWebSiteTitle;
	}

	public String getNormalSubheading() {
		return normalSubheading;
	}

	public String getWebSiteTitle2() {
		return webSiteTitle2;
	}

	public String getSubheading2() {
		return subheading2;
	}

	public Boolean getIsJobing() {
		return isJobing;
	}

	public Boolean getIsDebuging() {
		return isDebuging;
	}

	public String getFakeFTPHome() {
		return fakeFTPHome;
	}

	public String getHomepageAnnouncementStr() {
		return homepageAnnouncementStr;
	}
	
	public void setTmpHomepageAnnouncementStr(String homepageAnnouncementStr) {
		this.homepageAnnouncementStr = homepageAnnouncementStr;
	}

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			SystemConstantService tmp = new Gson().fromJson(jsonStr, SystemConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("system constant loading error: " + e.getLocalizedMessage());
		}
	}

}
