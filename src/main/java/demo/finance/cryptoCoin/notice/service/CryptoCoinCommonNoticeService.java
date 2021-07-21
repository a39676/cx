package demo.finance.cryptoCoin.notice.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinLowPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.notice.pojo.dto.NoticeUpdateDTO;
import demo.finance.cryptoCoin.notice.pojo.dto.SearchCryptoCoinConditionDTO;

public interface CryptoCoinCommonNoticeService {

	ModelAndView cryptoCoinPriceNoticeSettingManager();

	void noticeHandler();

	void deleteOldNotice();

	CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertCryptoCoinPriceNoticeSettingDTO dto);

	CommonResult deleteNotice(String pk);

	CommonResult updateNotice(NoticeUpdateDTO dto);

	ModelAndView searchValidNotices(SearchCryptoCoinConditionDTO dto);

	void deleteNoticeByHitNoData();

	CommonResult insertNewCryptoCoinLowPriceNoticeSetting(InsertCryptoCoinLowPriceNoticeSettingDTO dto);

}
