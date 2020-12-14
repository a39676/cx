package demo.finance.cryptoCoin.notice.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.dto.InsertMinuteCryptoCoinPriceNoticeSettingDTO;

public interface CryptoCoinMinuteNoticeService {

	CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertMinuteCryptoCoinPriceNoticeSettingDTO dto);

}
