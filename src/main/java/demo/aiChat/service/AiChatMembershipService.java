package demo.aiChat.service;

import java.util.List;

import aiChat.pojo.result.AiChatBuyMembershipFromWechatResult;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.po.AiChatUserMembership;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDTO;

public interface AiChatMembershipService {

	List<AiChatUserMembership> findMembershipDetailListByUserId(Long aiChatUserId);

	AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryByUserId(Long aiChatUserId, boolean refresh);
	
	AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryFromCacheByUserIdWithoutRefresh(Long aiChatUserId);

	AiChatBuyMembershipFromWechatResult buyMembershipFromWechat(WechatPayJsApiFeedbackDTO dto, Long wechatUserId);

	void rechargeDailyBonusByMemberShip();

	void updateDeleteMarkByExpiredTime();

}
