package demo.finance.cryptoCoin.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.service.CryptoCoinAccountInfoQueryService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;
import finance.cryptoCoin.common.pojo.result.CryptoCoinAccountSummaryResult;

@Controller
@RequestMapping(value = CcmUrlConstant.ROOT)
public class CryptoCoinCcmInteractionController extends CommonController {

	@Autowired
	private CryptoCoinAccountInfoQueryService cryptoCoinAccountInfoQueryService;

	@PostMapping(value = CcmUrlConstant.GET_ACCOUNT_SUMMARY)
	@ResponseBody
	public CryptoCoinAccountSummaryResult getAccountSummary(@RequestBody CryptoCoinInteractionSingleUserCommonDTO dto) {
		return cryptoCoinAccountInfoQueryService.getAccountSummary(dto);
	}

}
