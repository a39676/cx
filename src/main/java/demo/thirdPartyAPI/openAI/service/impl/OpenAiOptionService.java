package demo.thirdPartyAPI.openAI.service.impl;

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
public class OpenAiOptionService extends CommonService {

	@Value("${optionFilePath.openAI}")
	private String optionFilePath;
	private String apiKey;
	private String orgId;
	private Integer maxTokens = 500;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getMaxTokens() {
		return maxTokens;
	}

	public void setMaxTokens(Integer maxTokens) {
		this.maxTokens = maxTokens;
	}

	@Override
	public String toString() {
		return "OpenAiOptionService [apiKey=" + apiKey + ", orgId=" + orgId + ", maxTokens=" + maxTokens + "]";
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
			OpenAiOptionService tmp = new Gson().fromJson(jsonStr, OpenAiOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("Open AI option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Open AI option loading error: " + e.getLocalizedMessage());
		}
	}
}
