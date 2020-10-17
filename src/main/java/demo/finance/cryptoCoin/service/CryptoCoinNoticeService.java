package demo.finance.cryptoCoin.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.pojo.dto.InsertNewCryptoCoinPriceNoticeSettingDTO;

public interface CryptoCoinNoticeService {

	ModelAndView insertNewCryptoCoinPriceNoticeSetting();

	CommonResult insertNewMetalPriceNoticeSetting(InsertNewCryptoCoinPriceNoticeSettingDTO dto);

	void noticeHandler();

}
