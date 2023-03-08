package demo.aiChat.service;

import java.util.List;

import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.po.AiChatUserMembership;

public interface AiChatMembershipService {

	List<AiChatUserMembership> findMembershipDetailListByUserId(Long aiChatUserId);

	AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryByUserId(Long aiChatUserId);

}
