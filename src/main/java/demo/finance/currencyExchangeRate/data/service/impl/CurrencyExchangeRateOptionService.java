package demo.finance.currencyExchangeRate.data.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchangeRatePairDTO;
import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CurrencyExchangeRateOptionService extends CommonService {

	@Value("${optionFilePath.currencyExchangeRate}")
	private String optionFilePath;

	private String mainUrl;

	private List<CurrencyExchangeRatePairDTO> pairList;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			CurrencyExchangeRateOptionService tmp = new Gson().fromJson(jsonStr,
					CurrencyExchangeRateOptionService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("crypto coin option loading error: " + e.getLocalizedMessage());
		}
		log.error("crypto coin option loaded");
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public List<CurrencyExchangeRatePairDTO> getPairList() {
		return pairList;
	}

	public void setPairList(List<CurrencyExchangeRatePairDTO> pairList) {
		this.pairList = pairList;
	}

	@Override
	public String toString() {
		return "CurrencyExchangeRateOptionService [mainUrl=" + mainUrl + ", pairList=" + pairList + "]";
	}

}
