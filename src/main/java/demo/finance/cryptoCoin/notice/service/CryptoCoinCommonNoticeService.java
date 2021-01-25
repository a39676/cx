package demo.finance.cryptoCoin.notice.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.notice.pojo.dto.NoticeUpdateDTO;
import demo.finance.cryptoCoin.notice.pojo.dto.SearchCryptoCoinConditionDTO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoinCommonNoticeService {

	ModelAndView cryptoCoinPriceNoticeSettingManager();

	void noticeHandler();

	void deleteOldNotice();

	CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertCryptoCoinPriceNoticeSettingDTO dto);

	CommonResult deleteNotice(String pk);

	CommonResult updateNotice(NoticeUpdateDTO dto);

	ModelAndView searchValidNotices(SearchCryptoCoinConditionDTO dto);

	List<CryptoCoinPriceCommonDataBO> findHistoryData(CryptoCoinType coinType, CurrencyType currencyType,
			Integer timeUnit, Integer timeRange);

}
