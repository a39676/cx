package demo.finance.cryptoCoin.notice.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;

public interface CryptoCoinCommonNoticeService {

	ModelAndView insertNewCryptoCoinPriceNoticeSetting();

	void noticeHandler();

	void deleteOldNotice();

	CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertCryptoCoinPriceNoticeSettingDTO dto);

	CommonResult deleteNotice(String pk);

}
