package demo.finance.cryptoCoin.notice.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinNoticeDTOCheckResult;
import demo.finance.cryptoCoin.notice.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.notice.service.CryptoCoinMinuteNoticeService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoinMinuteNoticeServiceImp extends CryptoCoinCommonService implements CryptoCoinMinuteNoticeService {

	@Autowired
	private CryptoCoinPriceNoticeMapper noticeMapper;

	

}
