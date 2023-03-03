package demo.thirdPartyAPI.openAI.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;

public abstract class OpenAiCommonService extends CommonService{

	@Autowired
	protected OpenAiUtil util;
	
	protected CommonResult notEnoughtAmount() {
		CommonResult r = new CommonResult();
		r.setMessage("余额不足, 请充值或留意签到活动");
		return r;
	}
	
	protected CommonResult serviceError() {
		CommonResult r = new CommonResult();
		r.setMessage("服务器正在拼命运算, 请稍后再试");
		return r;
	}
	
}
