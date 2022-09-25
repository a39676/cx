package demo.finance.currencyExchangeRate.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.currencyExchangeRate.notice.pojo.constant.CurrencyExchangeRateNoticeUrl;
import demo.finance.currencyExchangeRate.notice.pojo.dto.InsertCurrencyExchangeRateNoticeSettingDTO;
import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;

@Controller
@RequestMapping(value = CurrencyExchangeRateNoticeUrl.ROOT)
public class CurrencyExchangeRateController {

	@Autowired
	private CurrencyExchangeRateNoticeService noticeService;
	
	@GetMapping(value = CurrencyExchangeRateNoticeUrl.INSERT_NOTICE)
	public ModelAndView currencyExhchangeRateNoticeSettingManager() {
		return noticeService.currencyExhchangeRateNoticeSettingManager();
	}
	
	@PostMapping(value = CurrencyExchangeRateNoticeUrl.INSERT_NOTICE)
	@ResponseBody
	public CommonResult insertNewCryptoCoinPriceNoticeSetting(@RequestBody InsertCurrencyExchangeRateNoticeSettingDTO dto) {
		return noticeService.insertNewCryptoCoinPriceNoticeSetting(dto);
	}
}
