package demo.tool.telegram.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.tool.telegram.pojo.po.TelegramConstant;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class TelegramConstantService extends CommonService {

	@Value("${optionFilePath.telegram}")
	private String optionFilePath;

	private Map<String, TelegramConstant> telegramConstantMap = new HashMap<>();

	public Map<String, TelegramConstant> getTelegramConstantMap() {
		return telegramConstantMap;
	}

	public void setTelegramConstantMap(Map<String, TelegramConstant> telegramConstantMap) {
		this.telegramConstantMap = telegramConstantMap;
	}
	
	public void putTelegramConstantMap(String key, TelegramConstant constant) {
		telegramConstantMap.put(key, constant);
	}

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			TelegramConstantService tmp = new Gson().fromJson(jsonStr, TelegramConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("telegram constant loading error: " + e.getLocalizedMessage());
		}
	}

}
