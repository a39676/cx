package demo.aiChat.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.type.AiChatAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.mapper.AiChatUserMembershipMapper;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.dto.AiChatUserMembershipLevelDetailDTO;
import demo.aiChat.pojo.po.AiChatUserMembership;
import demo.aiChat.pojo.po.AiChatUserMembershipExample;
import demo.aiChat.service.AiChatMembershipService;
import demo.aiChat.service.AiChatUserService;
import wechatSdk.pojo.dto.BuyMembershipFromWechatDTO;

@Service
public class AiChatMembershipServiceImpl extends AiChatCommonService implements AiChatMembershipService {

	@Autowired
	private AiChatUserMembershipMapper mapper;
	@Autowired
	private AiChatUserService userService;

	private Map<Long, AiChatUserMembershipLevelDetailDTO> getMembershipLevelMap() {
		List<AiChatUserMembershipLevelDetailDTO> membershipList = optionService.getMembershipLevelDetail();
		Map<Long, AiChatUserMembershipLevelDetailDTO> map = new HashMap<>();
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
		if (cacheService.getMembershipCacheMap().containsKey(aiChatUserId)) {
			return cacheService.getMembershipCacheMap().get(aiChatUserId);
		}

		List<AiChatUserMembership> membershipList = findMembershipDetailListByUserId(aiChatUserId);

		AiChatUserMembershipDetailSummaryDTO summaryDTO = new AiChatUserMembershipDetailSummaryDTO();
		summaryDTO.setAiChatUserId(aiChatUserId);

		Map<Long, AiChatUserMembershipLevelDetailDTO> membershipLevelMap = getMembershipLevelMap();
		if (membershipList == null || membershipList.isEmpty()) {
			AiChatUserMembershipLevelDetailDTO levelZero = membershipLevelMap.get(0L);
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

		cacheService.getMembershipCacheMap().put(aiChatUserId, summaryDTO);

		return summaryDTO;
	}

	@Override
	public CommonResult buyMembershipFromWechat(BuyMembershipFromWechatDTO dto) {
		CommonResult r = new CommonResult();
		if (dto == null) {
			r.setMessage("Decrypt error");
			return r;
		}

		try {
			if (!"success".equals(dto.getFeedback().getTrade_state().toLowerCase())) {
				sendTelegramMessage("收到付款失败回调: " + dto.toString());
				r.setMessage("付款异常, 已通知客服, 请稍后 0x0");
				return r;
			}
		} catch (Exception e) {
			sendTelegramMessage("解读付款异常: " + dto.toString());
			r.setMessage("付款异常, 已通知客服, 请稍后 0x1");
			return r;
		}

		Long membershipId = systemOptionService.decryptPrivateKey(dto.getMembershipPk());
		if(membershipId == null) {
			sendTelegramMessage("收到付款失败回调: " + dto.toString());
			r.setMessage("付款异常, 已通知客服, 请稍后 0x2");
			return r;
		}
		
		AiChatUserMembershipLevelDetailDTO membershipLevelDetail = getMembershipLevelMap().get(membershipId);
		if (membershipLevelDetail == null) {
			sendTelegramMessage("收到付款失败回调: " + dto.toString());
			r.setMessage("付款异常, 已通知客服, 请稍后 0x3");
			return r;
		}

		Long aiChatUserId = userService.__getAiChatUserIdByOpenId(dto.getFeedback().getPayer().getOpenId());
		AiChatUserMembership memberShipPO = new AiChatUserMembership();
		memberShipPO.setAiChatUserId(aiChatUserId);
		memberShipPO.setMembershipLevel(membershipId);
		mapper.insertSelective(memberShipPO);

		userService.recharge(aiChatUserId, AiChatAmountType.BONUS, new BigDecimal(membershipLevelDetail.getDailyBonus()));
		
		AiChatUserMembershipDetailSummaryDTO membershipDetailSummary = cacheService.getMembershipCacheMap()
				.get(aiChatUserId);
		if (membershipDetailSummary == null) {
			membershipDetailSummary = new AiChatUserMembershipDetailSummaryDTO();
			membershipDetailSummary.setAiChatUserId(aiChatUserId);
			membershipDetailSummary.setChatHistoryCountLimit(membershipLevelDetail.getChatHistoryCountLimit());
			membershipDetailSummary.setDailyBonus(membershipLevelDetail.getDailyBonus());
			cacheService.getMembershipCacheMap().put(aiChatUserId, membershipDetailSummary);
		} else {
			if (membershipDetailSummary.getChatHistoryCountLimit() < membershipLevelDetail.getChatHistoryCountLimit()) {
				membershipDetailSummary.setChatHistoryCountLimit(membershipLevelDetail.getChatHistoryCountLimit());
			}
			membershipDetailSummary
					.setDailyBonus(membershipDetailSummary.getDailyBonus() + membershipLevelDetail.getDailyBonus());
			cacheService.getMembershipCacheMap().put(aiChatUserId, membershipDetailSummary);
		}

		return r;
	}
}
