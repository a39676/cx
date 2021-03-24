package demo.finance.cryptoCoin.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.tool.pojo.constant.CryptoCoinDataCompareUrl;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataMutipleCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareRateResult;
import demo.finance.cryptoCoin.tool.service.CryptoDataCompareService;

@Controller
@RequestMapping(value = CryptoCoinDataCompareUrl.ROOT)
public class CryptoCoinDataCompareContoller extends CommonController {

	@Autowired
	private CryptoDataCompareService dataCompareService;

	@GetMapping(value = CryptoCoinDataCompareUrl.CRYPTO_COIN_DAILY_DATA_COMPARETOR)
	@ResponseBody
	public ModelAndView CryptoCoinDailyDataComparetor() {
		return dataCompareService.CryptoCoinDailyDataComparetor();
	}
	
	@PostMapping(value = CryptoCoinDataCompareUrl.CRYPTO_COIN_DAILY_DATA_COMPARETOR)
	@ResponseBody
	public CryptoDataCompareRateResult cryptoCoinDailyDataMutipleComparetor(@RequestBody CryptoCoinDataMutipleCompareDTO dto) {
		return dataCompareService.cryptoCoinDailyDataMutipleComparePoint(dto);
	}
}
