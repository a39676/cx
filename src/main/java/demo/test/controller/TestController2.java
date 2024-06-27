package demo.test.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.service.CommonService;
import demo.finance.cryptoCoin.mq.producer.CryptoCoinDailyDataQueryAckProducer;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import demo.test.service.TestService2;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonService {

	@Autowired
	private TestService2 testService2;
	@Autowired
	private CryptoCoinDailyDataQueryAckProducer cryptoCoinDailyDataQueryAckProducer;

	@GetMapping(value = "/t1")
	public ModelAndView testView() {
		return testService2.testView();
	}

	@PostMapping(value = "/t2")
	@ResponseBody
	public String t2(@RequestBody TestDTO dto) {
		return "{\"k\":\"v\"}";
	}

	@GetMapping(value = "/t3")
	@ResponseBody
	public String t3(@RequestParam(value = "coinName", required = false, defaultValue = "BTC") String coinName) {
		CryptoCoinDailyDataQueryDTO dto = new CryptoCoinDailyDataQueryDTO();
		dto.setCoinName(coinName);
		dto.setCurrencyName(CurrencyTypeForCryptoCoin.USDT.getName());
		dto.setDataSourceCode(CryptoCoinDataSourceType.BINANCE.getCode());
		LocalDateTime now = LocalDateTime.now();
		Date startTime = localDateTimeHandler.localDateTimeToDate(now.minusDays(200).with(LocalTime.MIN));
		Date endTime = localDateTimeHandler.localDateTimeToDate(now.with(LocalTime.MAX));

		dto.setStartTime(startTime.getTime());
		dto.setEndTime(endTime.getTime());
		cryptoCoinDailyDataQueryAckProducer.sendCryptoCoinDailyDataQueryForTest(dto);
		return "Done";
	}

}
