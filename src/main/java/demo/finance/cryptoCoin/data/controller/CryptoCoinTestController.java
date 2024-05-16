package demo.finance.cryptoCoin.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinTestUrl;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;

@Controller
@RequestMapping(value = CryptoCoinTestUrl.ROOT)
public class CryptoCoinTestController extends CommonController {

	@Autowired
	private CryptoCoinDataComplexService cryptoCoinDataComplexService;

	@GetMapping(value = CryptoCoinTestUrl.BIG_MOVE)
	public ModelAndView getBigMoveSummaryByManual() {
		return cryptoCoinDataComplexService.getBigMoveSummaryByManual();
	}
}
