package demo.interaction.bbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.interaction.bbt.service.BbtComplexService;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;
import tool.pojo.constant.BbtInteractionUrl;

@Controller
@RequestMapping(value = BbtInteractionUrl.ROOT)
public class BbtController extends CommonController {

	@Autowired
	private BbtComplexService bbtComplexService;

	@PostMapping(value = BbtInteractionUrl.TEXT_MESSAGE_FORWARD)
	@ResponseBody
	public CommonResult textMessageForwarding(@RequestBody ServiceMsgDTO dto) {
		return bbtComplexService.textMessageForwarding(dto);
	}

	@PostMapping(value = BbtInteractionUrl.RECEIVE_CURRENCY_EXCHANGE_RATE_DAILY_DATA)
	@ResponseBody
	public CommonResult receiveCurrencyExchangeRateDailyData(@RequestBody CurrencyExchageRateCollectResult dto) {
		return bbtComplexService.receiveCurrencyExchangeRateDailyData(dto);
	}
}
