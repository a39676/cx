package demo.finance.currencyExchangeRate.data.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchangeRatePairDTO;
import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CurrencyExchangeRateOptionService extends CommonService {

	private String exchangerateApiApiKey;
	private List<CurrencyExchangeRatePairDTO> pairList;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CURRENCY_EXCHANGE_RATE);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CURRENCY_EXCHANGE_RATE);
			CurrencyExchangeRateOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("crypto coin option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("crypto coin option loading error: " + e.getLocalizedMessage());
		}
	}

	public List<CurrencyExchangeRatePairDTO> getPairList() {
		return pairList;
	}

	public String getExchangerateApiApiKey() {
		return exchangerateApiApiKey;
	}

	public void setExchangerateApiApiKey(String exchangerateApiApiKey) {
		this.exchangerateApiApiKey = exchangerateApiApiKey;
	}

	public void setPairList(List<CurrencyExchangeRatePairDTO> pairList) {
		this.pairList = pairList;
	}

	@Override
	public String toString() {
		return "CurrencyExchangeRateOptionService [exchangerateApiApiKey=" + exchangerateApiApiKey + ", pairList="
				+ pairList + "]";
	}

}
