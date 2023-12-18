package demo.tool.zulip.service.impl;

import java.io.File;
import java.util.List;

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
public class ZulipOptionService extends CommonService {

	private String apiKey;
	private String host;
	private String email;
	private List<String> targetUserEmailList;
	private Integer messageLivingMinutes;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.ZULIP);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.ZULIP);
			ZulipOptionService tmp = new Gson().fromJson(jsonStr, ZulipOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("Zulip option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Zulip option loading error: " + e.getLocalizedMessage());
		}
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getTargetUserEmailList() {
		return targetUserEmailList;
	}

	public void setTargetUserEmailList(List<String> targetUserEmailList) {
		this.targetUserEmailList = targetUserEmailList;
	}

	public Integer getMessageLivingMinutes() {
		return messageLivingMinutes;
	}

	public void setMessageLivingMinutes(Integer messageLivingMinutes) {
		this.messageLivingMinutes = messageLivingMinutes;
	}

	@Override
	public String toString() {
		return "ZulipOptionService [apiKey=" + apiKey + ", host=" + host + ", email=" + email + ", targetUserEmailList="
				+ targetUserEmailList + ", messageLivingMinutes=" + messageLivingMinutes + "]";
	}

}
