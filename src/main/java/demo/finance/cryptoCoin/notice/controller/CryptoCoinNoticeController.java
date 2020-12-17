package demo.finance.cryptoCoin.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.notice.pojo.constant.CryptoCoinNoticeUrl;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
import finance.cryptoCoin.pojo.constant.CryptoCoinPriceCommonUrl;

@Controller
@RequestMapping(value = CryptoCoinPriceCommonUrl.ROOT)
public class CryptoCoinNoticeController {

	@Autowired
	private CryptoCoinCommonNoticeService commonNoticeService;
	
	@GetMapping(value = CryptoCoinNoticeUrl.INSERT_CRYPTO_COIN_NOTICE_SETTING)
	public ModelAndView insertNewCryptoCoinPriceNoticeSetting() {
		return commonNoticeService.insertNewCryptoCoinPriceNoticeSetting();
	}
	
	@PostMapping(value = CryptoCoinNoticeUrl.INSERT_CRYPTO_COIN_NOTICE_SETTING)
	@ResponseBody
	public CommonResult insertNewCryptoCoinPriceNoticeSetting(@RequestBody InsertCryptoCoinPriceNoticeSettingDTO dto) {
		return commonNoticeService.insertNewCryptoCoinPriceNoticeSetting(dto);
	}
}
