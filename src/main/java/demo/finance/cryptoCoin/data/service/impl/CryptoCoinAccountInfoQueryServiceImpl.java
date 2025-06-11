package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoinAccountInfoQueryService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;
import finance.cryptoCoin.common.pojo.result.CryptoCoinAccountSummaryResult;
import finance.cryptoCoin.common.pojo.type.CryptoExchangeType;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinAccountInfoQueryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinAccountInfoQueryService {

	@Override
	public CryptoCoinAccountSummaryResult getAccountSummary(CryptoCoinInteractionSingleUserCommonDTO dto) {
		CryptoCoinAccountSummaryResult r = new CryptoCoinAccountSummaryResult();

		CryptoExchangeType exchangeType = CryptoExchangeType.getType(dto.getExchangeCode());
		if (exchangeType == null) {
			r.setMessage("Please select exchange");
			return r;
		}

		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_ACCOUNT_SUMMARY;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		try {
			String response = h.sendPostRestful(url, json.toString());
			r = buildObjFromJsonCustomization(response, CryptoCoinAccountSummaryResult.class);
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
		}
		return r;
	}
}
