package demo.finance.cryptoCoin.notice.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.notice.pojo.dto.NoticeUpdateDTO;
import demo.finance.cryptoCoin.notice.pojo.dto.SearchCryptoCoinConditionDTO;
import demo.finance.cryptoCoin.notice.pojo.result.CryptoCoinSearchResult;

public interface CryptoCoinCommonNoticeService {

	ModelAndView insertNewCryptoCoinPriceNoticeSetting();

	void noticeHandler();

	void deleteOldNotice();

	CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertCryptoCoinPriceNoticeSettingDTO dto);

	CommonResult deleteNotice(String pk);

	CommonResult updateNotice(NoticeUpdateDTO dto);

	CryptoCoinSearchResult searchValidNotices(SearchCryptoCoinConditionDTO dto);

}
