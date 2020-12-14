package demo.finance.cryptoCoin.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.notice.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoinCommonNoticeServiceImp extends CryptoCoinCommonService implements CryptoCoinCommonNoticeService {

	@Autowired
	protected CryptoCoinPriceNoticeMapper noticeMapper;
	
	@Override
	public ModelAndView insertNewCryptoCoinPriceNoticeSetting() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/insertNewCryptoCoinPriceNoticeSetting");
		view.addObject("cryptoCoinType", CryptoCoinType.values());
//		view.addObject("currencyType", CurrencyType.values());
		view.addObject("currencyType", CurrencyType.values());
		return view;
	}
}
