package demo.finance.cryptoCoin.notice.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;

public interface CryptoCoinDayNoticeService {

	CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertCryptoCoinPriceNoticeSettingDTO dto);

}
