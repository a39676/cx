package demo.aiChat.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.po.AiChatUserMembership;
import wechatSdk.pojo.dto.BuyMembershipFromWechatDTO;

public interface AiChatMembershipService {

	List<AiChatUserMembership> findMembershipDetailListByUserId(Long aiChatUserId);

	AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryByUserId(Long aiChatUserId);

	CommonResult buyMembershipFromWechat(BuyMembershipFromWechatDTO dto);

}
