package demo.aiChat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.thirdPartyAPI.openAI.service.impl.OpenAiUtil;

public abstract class AiChatCommonService extends CommonService{

	@Autowired
	protected OpenAiUtil util;
	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	protected AiChatOptionService optionService;
	
	protected CommonResult notEnoughtAmount() {
		CommonResult r = new CommonResult();
		r.setMessage("电量不足, 请充电或留意签到活动");
		return r;
	}
	
	protected CommonResult serviceError() {
		CommonResult r = new CommonResult();
		r.setMessage("服务器正在拼命运算, 请稍后再试");
		return r;
	}
	
}