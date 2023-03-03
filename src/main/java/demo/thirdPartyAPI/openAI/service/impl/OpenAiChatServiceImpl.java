package demo.thirdPartyAPI.openAI.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.thirdPartyAPI.openAI.mapper.OpenAiUserAmountHistoryMapper;
import demo.thirdPartyAPI.openAI.mapper.OpenAiUserAssociateWechatUidMapper;
import demo.thirdPartyAPI.openAI.mapper.OpenAiUserChatGptHistoryMapper;
import demo.thirdPartyAPI.openAI.mapper.OpenAiUserDetailMapper;
import demo.thirdPartyAPI.openAI.pojo.dto.SendChatingMessageDTO;
import demo.thirdPartyAPI.openAI.pojo.po.OpenAiUserDetail;
import demo.thirdPartyAPI.openAI.pojo.result.ChatResult;
import demo.thirdPartyAPI.openAI.service.OpenAiChatService;

@Service
public class OpenAiChatServiceImpl extends OpenAiCommonService implements OpenAiChatService {

	@Autowired
	private OpenAiUserAmountHistoryMapper openAiUserAmountHistoryMapper;
	@Autowired
	private OpenAiUserAssociateWechatUidMapper openAiUserAssociateWechatUidMapper;
	@Autowired
	private OpenAiUserChatGptHistoryMapper openAiUserChatGptHistoryMapper;
	@Autowired
	private OpenAiUserDetailMapper openAiUserDetailMapper;
	
	public ChatResult sendNewChat(SendChatingMessageDTO dto) {
		ChatResult r = new ChatResult();
	}
	
	private OpenAiUserDetail findUserDetailByWechatUid(String uid) {
		/*
		 * TODO 
		 * 以 wechat uid 查找 openAiUserId 需要做缓存, 放置在 option service
		 * if 无对应用户
		 *   新建用户
		 * else
		 *   查找旧资料
		 */
	}
	
	private CommonResult checkAmount() {
		/*
		 * TODO
		 * check bonus_amount + recharge amount
		 */
	}
	
	private CommonResult debitAmount() {
		
	}
}
