package demo.finance.currencyExchangeRate.notice.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.currencyExchangeRate.notice.pojo.dto.InsertCurrencyExchangeRateNoticeSettingDTO;

public interface CurrencyExchangeRateNoticeService {

	void deleteOldNotice();

	CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertCurrencyExchangeRateNoticeSettingDTO dto);

	void noticeHandler();

	ModelAndView currencyExhchangeRateNoticeSettingManager();

}
