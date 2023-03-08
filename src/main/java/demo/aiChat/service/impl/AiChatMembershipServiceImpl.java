package demo.aiChat.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.aiChat.mapper.AiChatUserMembershipMapper;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.dto.AiChatUserMembershipLevelDetailDTO;
import demo.aiChat.pojo.po.AiChatUserMembership;
import demo.aiChat.pojo.po.AiChatUserMembershipExample;
import demo.aiChat.service.AiChatMembershipService;

@Service
public class AiChatMembershipServiceImpl extends AiChatCommonService implements AiChatMembershipService {

	@Autowired
	private AiChatUserMembershipMapper mapper;
	

	private Map<Integer, AiChatUserMembershipLevelDetailDTO> getMembershipLevelMap() {
		List<AiChatUserMembershipLevelDetailDTO> membershipList = optionService.getMembershipLevelDetail();
		Map<Integer, AiChatUserMembershipLevelDetailDTO> map = new HashMap<>();
		for (AiChatUserMembershipLevelDetailDTO membership : membershipList) {
			map.put(membership.getLevel(), membership);
		}
		return map;
	}

	@Override
	public List<AiChatUserMembership> findMembershipDetailListByUserId(Long aiChatUserId) {
		AiChatUserMembershipExample example = new AiChatUserMembershipExample();
		example.createCriteria().andAiChatUserIdEqualTo(aiChatUserId).andExpiredTimeGreaterThan(LocalDateTime.now());
		List<AiChatUserMembership> membershipList = mapper.selectByExample(example);
		return membershipList;
	}

	@Override
	public AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryByUserId(Long aiChatUserId) {
		if(optionService.getMembershipCacheMap().containsKey(aiChatUserId)) {
			return optionService.getMembershipCacheMap().get(aiChatUserId);
		}
		
		List<AiChatUserMembership> membershipList = findMembershipDetailListByUserId(aiChatUserId);

		AiChatUserMembershipDetailSummaryDTO summaryDTO = new AiChatUserMembershipDetailSummaryDTO();
		summaryDTO.setAiChatUserId(aiChatUserId);

		Map<Integer, AiChatUserMembershipLevelDetailDTO> membershipLevelMap = getMembershipLevelMap();
		if (membershipList == null || membershipList.isEmpty()) {
			AiChatUserMembershipLevelDetailDTO levelZero = membershipLevelMap.get(0);
			summaryDTO.setLevel(levelZero.getLevel());
			summaryDTO.setDailyBonus(levelZero.getDailyBonus());
			summaryDTO.setChatHistoryCountLimit(levelZero.getChatHistoryCountLimit());
			return summaryDTO;
		}

		AiChatUserMembershipLevelDetailDTO membershipLevelDetail = null;
		for (AiChatUserMembership memberShip : membershipList) {
			membershipLevelDetail = membershipLevelMap.get(memberShip.getMembershipLevel());
			if (membershipLevelDetail != null) {
				if (summaryDTO.getLevel() == null || summaryDTO.getLevel() < membershipLevelDetail.getLevel()) {
					summaryDTO.setLevel(membershipLevelDetail.getLevel());
				}
				if (summaryDTO.getChatHistoryCountLimit() == null
						|| summaryDTO.getChatHistoryCountLimit() < membershipLevelDetail.getChatHistoryCountLimit()) {
					summaryDTO.setChatHistoryCountLimit(membershipLevelDetail.getChatHistoryCountLimit());
				}
				if (summaryDTO.getDailyBonus() == null) {
					summaryDTO.setDailyBonus(0);
				}
				summaryDTO.setDailyBonus(summaryDTO.getDailyBonus() + membershipLevelDetail.getDailyBonus());
			}
		}
		
		optionService.getMembershipCacheMap().put(aiChatUserId, summaryDTO);

		return summaryDTO;
	}

}
