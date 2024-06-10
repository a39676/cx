package demo.interaction.bbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.common.service.HeartBeatService;
import demo.interaction.bbt.service.BbtComplexService;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;
import tool.pojo.constant.CxBbtInteractionUrl;

@Controller
@RequestMapping(value = CxBbtInteractionUrl.ROOT)
public class BbtController extends CommonController {

	@Autowired
	private BbtComplexService bbtComplexService;
	@Autowired
	private HeartBeatService heartBeatService;

	@PostMapping(value = CxBbtInteractionUrl.TEXT_MESSAGE_FORWARD)
	@ResponseBody
	public CommonResult textMessageForwarding(@RequestBody ServiceMsgDTO dto) {
		return bbtComplexService.textMessageForwarding(dto);
	}

	@PostMapping(value = CxBbtInteractionUrl.RECEIVE_CURRENCY_EXCHANGE_RATE_DAILY_DATA)
	@ResponseBody
	public CommonResult receiveCurrencyExchangeRateDailyData(@RequestBody CurrencyExchageRateCollectResult dto) {
		return bbtComplexService.receiveCurrencyExchangeRateDailyData(dto);
	}

	@PostMapping(value = CxBbtInteractionUrl.BBT_HEART_BEAT)
	@ResponseBody
	public String bbtHeartBeat(@RequestBody BaseStrDTO dto) {
		heartBeatService.setBbtLastHeartBeat(dto);
		return "Done";
	}

	@PostMapping(value = CxBbtInteractionUrl.CN_STOCK_MARKET_DATA)
	@ResponseBody
	public String receiveCnStockMarketData(@RequestBody CnStockMarketDataDTO dto) {
		bbtComplexService.receiveCnStockMarketData(dto);
		return "Done";
	}

}
