package demo.finance.binance.common.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class BinanceOptionService extends CommonService {

	@Value("${optionFilePath.binance}")
	private String optionFilePath;

	private String apiKey;
	private String secretKey;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getOptionFilePath() {
		return null;
	}

	public void setOptionFilePath(String optionFilePath) {
	}

	@Override
	public String toString() {
		return "BinanceOptionService [optionFilePath=" + optionFilePath + ", apiKey=" + apiKey + ", secretKey="
				+ secretKey + "]";
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
			BinanceOptionService tmp = new Gson().fromJson(jsonStr, BinanceOptionService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("crypto coin option loading error: " + e.getLocalizedMessage());
		}
		log.error("crypto coin option loaded");
	}

}
