package demo.tool.textMessageForward.telegram.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import demo.tool.textMessageForward.telegram.pojo.bo.TelegramConstantBO;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class TelegramOptionService extends CommonService {

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
		File optionFile = new File(OptionFilePathConfigurer.TELEGRAM);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.TELEGRAM);
			TelegramOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("telegram constant loaded");
		} catch (Exception e) {
			log.error("telegram constant loading error: " + e.getLocalizedMessage());
		}
	}

}
