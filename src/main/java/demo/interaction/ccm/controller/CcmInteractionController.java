package demo.interaction.ccm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureTradingService;
import finance.cryptoCoin.pojo.constant.CryptoCoinBinanceTradingCommonUrl;
import finance.cryptoCoin.pojo.dto.CryptoCoinSymbolGroupSettingDTO;

@Controller
@RequestMapping(value = CryptoCoinBinanceTradingCommonUrl.ROOT)
public class CcmInteractionController {

	@Autowired
	private CryptoCoinBinanceFutureTradingService cryptoCoinBinanceFutureTradingService;

	@GetMapping(value = CryptoCoinBinanceTradingCommonUrl.GET_SYMBOL_GROUP_DATA)
	@ResponseBody
	public CryptoCoinSymbolGroupSettingDTO getSymbolGroupData() {
		return cryptoCoinBinanceFutureTradingService.getSymbolGroupData();
	}
}
