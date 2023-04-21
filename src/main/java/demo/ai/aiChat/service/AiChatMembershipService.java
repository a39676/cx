package demo.ai.aiChat.service;

import java.util.List;

import ai.aiChat.pojo.result.AiChatBuyMembershipFromWechatResult;
import ai.aiChat.pojo.result.GetAiChatMembershipResult;
import demo.ai.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.ai.aiChat.pojo.po.AiChatUserMembership;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDTO;

public interface AiChatMembershipService {

	List<AiChatUserMembership> findMembershipDetailListByUserId(Long aiChatUserId);

	AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryByUserId(Long aiChatUserId, boolean refresh);
	
	AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryFromCacheByUserIdWithoutRefresh(Long aiChatUserId);

	AiChatBuyMembershipFromWechatResult buyMembershipFromWechat(WechatPayJsApiFeedbackDTO dto, Long wechatUserId);

	void rechargeDailyBonusByMemberShip();

	void updateDeleteMarkByExpiredTime();

	GetAiChatMembershipResult getMembershipListFromWechat(String tmpKeyStr);

	AiChatBuyMembershipFromWechatResult __giftMembership(Long wechatUserId, Long membershipId);

}
