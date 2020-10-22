package demo.finance.cryptoCoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.pojo.constant.CryptoCoinPriceUrl;
import demo.finance.cryptoCoin.pojo.dto.InsertNewCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.service.CryptoCoinNoticeService;
import finance.cryptoCoin.pojo.constant.CryptoCoinPriceCommonUrl;

@Controller
@RequestMapping(value = CryptoCoinPriceCommonUrl.ROOT)
public class CryptoCoinController {

	@Autowired
	private CryptoCoinNoticeService noticeService;
	
	@GetMapping(value = CryptoCoinPriceUrl.INSERT_CRYPTO_COIN_NOTICE_SETTING)
	public ModelAndView insertNewCryptoCoinPriceNoticeSetting() {
		return noticeService.insertNewCryptoCoinPriceNoticeSetting();
	}
	
	@PostMapping(value = CryptoCoinPriceUrl.INSERT_CRYPTO_COIN_NOTICE_SETTING)
	@ResponseBody
	public CommonResult insertNewCryptoCoinPriceNoticeSetting(@RequestBody InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		return noticeService.insertNewCryptoCoinPriceNoticeSetting(dto);
	}
}
