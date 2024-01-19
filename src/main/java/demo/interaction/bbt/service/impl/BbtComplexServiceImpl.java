package demo.interaction.bbt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.currencyExchangeRate.data.service.CurrencyExchangeRateService;
import demo.interaction.bbt.service.BbtCommonService;
import demo.interaction.bbt.service.BbtComplexService;
import demo.tool.textMessageForward.service.TextMessageForwardService;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;

@Service
public class BbtComplexServiceImpl extends BbtCommonService implements BbtComplexService {

	@Autowired
	private TextMessageForwardService msgForwardServcie;
	@Autowired
	private CurrencyExchangeRateService currencyExchangeRate1DayDataService;

	@Override
	public CommonResult textMessageForwarding(ServiceMsgDTO dto) {
		CommonResult r = new CommonResult();
		String keyInput = dto.getKey();
		if (!bbtDynamicKey.isCorrectKey(keyInput)) {
			return r;
		}
		
		log.error("Fowrawd msg: " + dto.toString());
		return msgForwardServcie.textMessageForwarding(dto);
	}
	
	@Override
	public CommonResult receiveCurrencyExchangeRateDailyData(CurrencyExchageRateCollectResult dto) {
		CommonResult r = new CommonResult();
		String keyInput = dto.getKey();
		if (!bbtDynamicKey.isCorrectKey(keyInput)) {
			return r;
		}
		
		log.error("Receive currency exchange rate daily data from BbtController, DTO:" + dto.toString());
		return currencyExchangeRate1DayDataService.receiveDailyData(dto);
	}
}
