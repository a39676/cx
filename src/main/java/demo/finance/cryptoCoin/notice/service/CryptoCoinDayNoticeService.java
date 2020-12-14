package demo.finance.cryptoCoin.notice.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.dto.InsertMinuteCryptoCoinPriceNoticeSettingDTO;

public interface CryptoCoinDayNoticeService {

	CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertMinuteCryptoCoinPriceNoticeSettingDTO dto);

	void noticeHandler();

	void deleteOldNotice();

}
