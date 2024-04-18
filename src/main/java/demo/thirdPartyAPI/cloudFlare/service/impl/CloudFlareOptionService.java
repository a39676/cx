package demo.thirdPartyAPI.cloudFlare.service.impl;

import java.io.File;

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
public class CloudFlareOptionService extends CommonService {

	private String clientKey;
	private String serverKey;

	@Override
	public String toString() {
		return "OpenAiOptionService [clientKey=" + clientKey + ", serverKey=" + serverKey + "]";
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CLOUD_FLARE);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CLOUD_FLARE);
			CloudFlareOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("Cloud Flare option loaded");
		} catch (Exception e) {
			log.error("Cloud Flare option loading error: " + e.getLocalizedMessage());
		}
	}
}
