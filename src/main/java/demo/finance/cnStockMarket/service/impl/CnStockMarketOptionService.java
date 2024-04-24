package demo.finance.cnStockMarket.service.impl;

import java.io.File;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import demo.finance.cnStockMarket.pojo.dto.CnStockMarketNoticeSettingDTO;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class CnStockMarketOptionService extends CommonService {

	private Map<String, CnStockMarketNoticeSettingDTO> noticeSettings;

	public Map<String, CnStockMarketNoticeSettingDTO> getNoticeSettings() {
		return noticeSettings;
	}

	public void setNoticeSettings(Map<String, CnStockMarketNoticeSettingDTO> noticeSettings) {
		this.noticeSettings = noticeSettings;
	}

	@Override
	public String toString() {
		return "CnStockMarketOptionService [noticeSettings=" + noticeSettings + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CN_STOCK_MARKET);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CN_STOCK_MARKET);
			CnStockMarketOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("CN stock market option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("CN stock market option loading error: " + e.getLocalizedMessage());
		}
	}
}
