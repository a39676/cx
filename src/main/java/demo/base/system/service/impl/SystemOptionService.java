package demo.base.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.costom_component.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class SystemOptionService extends CommonService {

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

	public String encryptId(Long id) {
		List<String> encryptIdList = encryptId(Arrays.asList(id));
		if (encryptIdList == null || encryptIdList.isEmpty()) {
			return null;
		} else {
			return encryptIdList.get(0);
		}
	}

	public List<String> encryptId(List<Long> idList) {
		if (idList == null || idList.isEmpty()) {
			return null;
		}

		String keys = getAesKey();
		if (StringUtils.isBlank(keys)) {
			return null;
		}

		String initVector = getAesInitVector();
		if (StringUtils.isBlank(initVector)) {
			return null;
		}

		List<String> encryptResult = new ArrayList<String>();
		try {
			for (Long id : idList) {
				encryptResult.add(encryptUtil.aesEncrypt(keys, initVector, String.valueOf(id)));
			}
		} catch (Exception e) {

		}
		return encryptResult;
	}

	public Long decryptPrivateKey(String inputPk) {
		List<Long> idList = decryptPrivateKey(Arrays.asList(inputPk));
		if (idList == null || idList.isEmpty()) {
			return null;
		} else {
			return idList.get(0);
		}
	}

	public List<Long> decryptPrivateKey(List<String> inputPkList) {
		List<Long> idList = new ArrayList<Long>();
		if (inputPkList == null || inputPkList.isEmpty()) {
			return idList;
		}

		String keys = getAesKey();
		if (StringUtils.isBlank(keys)) {
			return idList;
		}

		String initVector = getAesInitVector();
		if (StringUtils.isBlank(initVector)) {
			return idList;
		}

		Long id = null;
		for (String pk : inputPkList) {
			try {
				id = Long.parseLong(encryptUtil.aesDecrypt(keys, initVector, pk));
				idList.add(id);
			} catch (Exception e) {
				idList.add(null);
			}
		}
		return idList;
	}

	public boolean isDev() {
		return "dev".equals(getEnvName());
	}

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

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public void setAesInitVector(String aesInitVector) {
		this.aesInitVector = aesInitVector;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public void setEmailD(String emailD) {
		this.emailD = emailD;
	}

	public void setEmailC(String emailC) {
		this.emailC = emailC;
	}

	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public void setNormalWebSiteTitle(String normalWebSiteTitle) {
		this.normalWebSiteTitle = normalWebSiteTitle;
	}

	public void setNormalSubheading(String normalSubheading) {
		this.normalSubheading = normalSubheading;
	}

	public void setWebSiteTitle2(String webSiteTitle2) {
		this.webSiteTitle2 = webSiteTitle2;
	}

	public void setSubheading2(String subheading2) {
		this.subheading2 = subheading2;
	}

	public void setIsJobing(Boolean isJobing) {
		this.isJobing = isJobing;
	}

	public void setIsDebuging(Boolean isDebuging) {
		this.isDebuging = isDebuging;
	}

	public void setFakeFTPHome(String fakeFTPHome) {
		this.fakeFTPHome = fakeFTPHome;
	}

	public void setHomepageAnnouncementStr(String homepageAnnouncementStr) {
		this.homepageAnnouncementStr = homepageAnnouncementStr;
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.SYSTEM);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.SYSTEM);
			SystemOptionService tmp = new Gson().fromJson(jsonStr, SystemOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("system constant loaded");
		} catch (Exception e) {
			log.error("system constant loading error: " + e.getLocalizedMessage());
		}
	}

}
