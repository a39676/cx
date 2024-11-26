package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoinAccountInfoQueryService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.pojo.result.CryptoCoinBinanceAccountSummaryResult;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinAccountInfoQueryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinAccountInfoQueryService {

	@Override
	public CryptoCoinBinanceAccountSummaryResult getAccountSummary(CryptoCoinInteractionCommonDTO dto) {
		CryptoCoinBinanceAccountSummaryResult r = new CryptoCoinBinanceAccountSummaryResult();
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_ACCOUNT_SUMMARY;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		try {
			String response = h.sendPostRestful(url, json.toString());
			r = buildObjFromJsonCustomization(response, CryptoCoinBinanceAccountSummaryResult.class);
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
		}
		return r;
	}
}
