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
import demo.automationTest.service.impl.AutomationTestConstantService;
import demo.common.controller.CommonController;
import demo.interaction.bbt.service.BbtComplexService;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;
import net.sf.json.JSONObject;
import tool.pojo.constant.CxBbtInteractionUrl;

@Controller
@RequestMapping(value = CxBbtInteractionUrl.ROOT)
public class BbtController extends CommonController {

	@Autowired
	private BbtComplexService bbtComplexService;
	@Autowired
	private AutomationTestConstantService automationTestConstantService;

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
		automationTestConstantService.setLastHeartBeat(dto);
		return "Done";
	}

	@PostMapping(value = CxBbtInteractionUrl.CN_STOCK_MARKET_DATA)
	@ResponseBody
	public String receiveCnStockMarketData(@RequestBody CnStockMarketDataDTO dto) {
		bbtComplexService.receiveCnStockMarketData(dto);
		return "Done";
	}
	
	@PostMapping(value = CxBbtInteractionUrl.GET_CRYPTO_COIN_OPTION)
	@ResponseBody
	public JSONObject textMessageForwarding(@RequestBody BaseStrDTO dto) {
		return bbtComplexService.getCryptoCoinOption(dto);
	}
	
	@PostMapping(value = CxBbtInteractionUrl.MAKR_SURE_ALIVE_WITH_CTHULHU)
	@ResponseBody
	public CommonResult workerClone1IsAlive(@RequestBody BaseStrDTO dto) {
		return bbtComplexService.workerClone1IsAlive(dto);
	}
}
