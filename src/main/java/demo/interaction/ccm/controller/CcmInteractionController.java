package demo.interaction.ccm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureUmSingleUserGroupOrderTradingService;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinSymbolMaxLeverageMainDTO;
import finance.cryptoCoin.pojo.constant.CryptoCoinBinanceTradingCommonUrl;
import finance.cryptoCoin.pojo.dto.CryptoCoinSymbolGroupSettingDTO;

@Controller
@RequestMapping(value = CryptoCoinBinanceTradingCommonUrl.ROOT)
public class CcmInteractionController {

	@Autowired
	private CryptoCoinBinanceFutureUmSingleUserGroupOrderTradingService cryptoCoinBinanceFutureTradingService;
	@Autowired
	private CryptoCoinDataComplexService cryptoCoinDataComplexService;

	@GetMapping(value = CryptoCoinBinanceTradingCommonUrl.GET_SYMBOL_GROUP_DATA)
	@ResponseBody
	public CryptoCoinSymbolGroupSettingDTO getSymbolGroupData() {
		return cryptoCoinBinanceFutureTradingService.getSymbolGroupData();
	}

	@PostMapping(value = CryptoCoinBinanceTradingCommonUrl.SYMBOL_MAX_LEVERAGE_DATA)
	@ResponseBody
	public void receiveSymbolMaxLeverageInfo(@RequestBody CryptoCoinSymbolMaxLeverageMainDTO result) {
		cryptoCoinDataComplexService.receiveSymbolMaxLeverageInfo(result);
	}
}
