package demo.finance.cryptoCoin.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.tool.pojo.constant.CryptoCoinDataCompareUrl;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareResult;
import demo.finance.cryptoCoin.tool.service.CryptoDataCompareService;

@Controller
@RequestMapping(value = CryptoCoinDataCompareUrl.ROOT)
public class CryptoCoinDataCompareContoller extends CommonController {

	@Autowired
	private CryptoDataCompareService dataCompareService;
	
	@GetMapping(value = "/testing")
	@ResponseBody
	public CryptoDataCompareResult testing() {
		CryptoCoinDataCompareDTO dto = new CryptoCoinDataCompareDTO();
		dto.setCoinType1("FIL");
		dto.setCoinType2("BTC");
		dto.setCurrencyType(CurrencyType.USD.getCode());
		dto.setStartDateTimeStr("2021-01-01 00:00:00");
		return dataCompareService.cryptoCoinDailyDataComparePoint(dto);
	}
}
