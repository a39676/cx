package demo.tool.telegram.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.tool.telegram.pojo.bo.TelegramConstantBO;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class TelegramOptionService extends CommonService {

	@Value("${optionFilePath.telegram}")
	private String optionFilePath;

	private Map<String, TelegramConstantBO> telegramConstantMap = new HashMap<>();

	public Map<String, TelegramConstantBO> getTelegramConstantMap() {
		return telegramConstantMap;
	}

	public void setTelegramConstantMap(Map<String, TelegramConstantBO> telegramConstantMap) {
		this.telegramConstantMap = telegramConstantMap;
	}
	
	public void putTelegramConstantMap(String key, TelegramConstantBO constant) {
		telegramConstantMap.put(key, constant);
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
			TelegramOptionService tmp = new Gson().fromJson(jsonStr, TelegramOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("telegram constant loaded");
		} catch (Exception e) {
			log.error("telegram constant loading error: " + e.getLocalizedMessage());
		}
	}

}
