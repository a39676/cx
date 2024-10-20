package demo.interaction.bbt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.service.impl.SystemOptionService;
import demo.finance.cnStockMarket.service.CnStockMarketService;
import demo.finance.currencyExchangeRate.data.service.CurrencyExchangeRateService;
import demo.interaction.bbt.service.BbtCommonService;
import demo.interaction.bbt.service.BbtComplexService;
import demo.tool.textMessageForward.service.TextMessageForwardService;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;
import tool.pojo.constant.CxBbtInteractionUrl;
import toolPack.httpHandel.HttpUtil;

@Service
public class BbtComplexServiceImpl extends BbtCommonService implements BbtComplexService {

	@Autowired
	private TextMessageForwardService msgForwardServcie;
	@Autowired
	private CurrencyExchangeRateService currencyExchangeRate1DayDataService;
	@Autowired
	private CnStockMarketService cnStockMarketService;
	@Autowired
	private BbtCacheService bbtCacheService;
	@Autowired
	private SystemOptionService systemOptionService;

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

		log.error("Receive currency exchange rate daily data from BbtController");
		return currencyExchangeRate1DayDataService.receiveDailyData(dto);
	}

	@Override
	public CommonResult receiveCnStockMarketData(CnStockMarketDataDTO dto) {
		CommonResult r = new CommonResult();
		String keyInput = dto.getKey();
		if (!bbtDynamicKey.isCorrectKey(keyInput)) {
			return r;
		}

		return cnStockMarketService.receiveCnStockMarketData(dto);
	}

	@Override
	public CommonResult getBbtIsAlive() {
		CommonResult r = new CommonResult();
		r.setSuccess(bbtCacheService.getIsAlive());
		return r;
	}

	@Override
	public void checkBbtIsAlive() {
		HttpUtil h = new HttpUtil();
		String url = "https://" + systemOptionService.getBbtHost() + CxBbtInteractionUrl.ROOT
				+ CxBbtInteractionUrl.WORKER_PING;
		try {
			String response = h.sendGet(url);
			if (response != null && response.contains("pong")) {
				bbtCacheService.setIsAlive(true);
			} else {
				bbtCacheService.setIsAlive(false);
			}
		} catch (Exception e) {
			bbtCacheService.setIsAlive(false);
		}
	}
}
