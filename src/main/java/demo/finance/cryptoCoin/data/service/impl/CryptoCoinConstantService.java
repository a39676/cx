package demo.finance.cryptoCoin.data.service.impl;

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
public class CryptoCoinConstantService extends CommonService {

	@Value("${optionFilePath.cryptoCoin}")
	private String optionFilePath;

	private String defaultCurrency;
	private String apiKey;

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			CryptoCoinConstantService tmp = new Gson().fromJson(jsonStr, CryptoCoinConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("crypto coin constant loading error: " + e.getLocalizedMessage());
		}
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String currency) {
		this.defaultCurrency = currency;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String toString() {
		return "CryptoCoinConstantService [optionFilePath=" + optionFilePath + ", currency=" + defaultCurrency + ", apiKey="
				+ apiKey + "]";
	}

}
