package demo.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;
	
	@Autowired
	private CryptoCoinCommonNoticeService noticeService;
	
	@GetMapping(value = "/test")
	@ResponseBody
	public String test(@RequestParam(value = "coinType")String coinType, @RequestParam(value = "currencyType")String currencyType, @RequestParam(value = "timeUnit")Integer timeUnit) {
		List<CryptoCoinPriceCommonDataBO> data = noticeService.findHistoryData(CryptoCoinType.getType(coinType), CurrencyType.getType(currencyType), timeUnit, 10);
		JSONObject j = JSONObject.fromObject(data);
		return j.toString();
	}
}
