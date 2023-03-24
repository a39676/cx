package demo.aiChat.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.result.AiChatBuyMembershipFromWechatResult;
import aiChat.pojo.type.AiChatAmountType;
import demo.aiChat.mapper.AiChatUserMembershipMapper;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailDTO;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.po.AiChatUserMembership;
import demo.aiChat.pojo.po.AiChatUserMembershipExample;
import demo.aiChat.pojo.po.AiChatUserMembershipKey;
import demo.aiChat.pojo.vo.AiChatUserMembershipDetailVO;
import demo.aiChat.service.AiChatMembershipService;
import demo.aiChat.service.AiChatUserService;
import net.sf.json.JSONObject;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDTO;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDecryptDTO;
import wechatSdk.pojo.dto.BuyMembershipFromWechatAttachmentDTO;

@Service
public class AiChatMembershipServiceImpl extends AiChatCommonService implements AiChatMembershipService {

	@Autowired
	private AiChatUserMembershipMapper membershipMapper;
	@Autowired
	private AiChatUserService userService;

	private Map<Long, AiChatUserMembershipDetailDTO> getMembershipConfigMap() {
		List<AiChatUserMembershipDetailDTO> membershipList = optionService.getMembershipLDetails();
		Map<Long, AiChatUserMembershipDetailDTO> map = new HashMap<>();
		for (AiChatUserMembershipDetailDTO membership : membershipList) {
			map.put(membership.getId(), membership);
		}
		return map;
	}

	@Override
	public List<AiChatUserMembership> findMembershipDetailListByUserId(Long aiChatUserId) {
		AiChatUserMembershipExample example = new AiChatUserMembershipExample();
		example.createCriteria().andAiChatUserIdEqualTo(aiChatUserId).andIsDeleteEqualTo(false)
				.andExpiredTimeGreaterThan(LocalDateTime.now());
		List<AiChatUserMembership> membershipList = membershipMapper.selectByExample(example);
		return membershipList;
	}

	@Override
	public AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryFromCacheByUserIdWithoutRefresh(
			Long aiChatUserId) {
		return findMembershipDetailSummaryByUserId(aiChatUserId, false);
	}

	@Override
	public AiChatUserMembershipDetailSummaryDTO findMembershipDetailSummaryByUserId(Long aiChatUserId,
			boolean refresh) {
		if (!refresh && cacheService.getMembershipCacheMap().containsKey(aiChatUserId)) {
			return cacheService.getMembershipCacheMap().get(aiChatUserId);
		}

		List<AiChatUserMembership> membershipPoList = findMembershipDetailListByUserId(aiChatUserId);

		AiChatUserMembershipDetailSummaryDTO summaryDTO = new AiChatUserMembershipDetailSummaryDTO();
		summaryDTO.setAiChatUserId(aiChatUserId);

		Map<Long, AiChatUserMembershipDetailDTO> membershipConfigMap = getMembershipConfigMap();
		if (membershipPoList == null || membershipPoList.isEmpty()) {
			AiChatUserMembershipDetailDTO levelZero = membershipConfigMap.get(0L);
			summaryDTO.setLevel(levelZero.getLevel());
			summaryDTO.setDailyBonus(levelZero.getDailyBonus());
			summaryDTO.setChatHistoryCountLimit(levelZero.getChatHistoryCountLimit());
			return summaryDTO;
		}

		AiChatUserMembershipDetailDTO membershipDetail = null;
		AiChatUserMembershipDetailVO membershipDetailVO = null;
		for (AiChatUserMembership membershipPO : membershipPoList) {
			membershipDetail = membershipConfigMap.get(membershipPO.getMembershipId());
			if (membershipDetail != null) {
				membershipDetailVO = summaryDTO.getMembershipMap().get(membershipDetail.getId());
				if (membershipDetailVO == null) {
					membershipDetailVO = new AiChatUserMembershipDetailVO();
					membershipDetailVO.setPk(systemOptionService.encryptId(membershipDetail.getId()));
					membershipDetailVO.setDailyBonus(membershipDetail.getDailyBonus());
					membershipDetailVO.setDescription(membershipDetail.getDescription());
					membershipDetailVO.setExpiredDateTime(membershipPO.getExpiredTime());
				} else {
					if (membershipDetailVO.getExpiredDateTime().isBefore(membershipPO.getExpiredTime())) {
						membershipDetailVO.setExpiredDateTime(membershipPO.getExpiredTime());
					}
				}
				membershipDetailVO.setChatHistoryCountLimit(membershipDetail.getChatHistoryCountLimit());
				membershipDetailVO
						.setExpiredDateTimeStr(localDateTimeHandler.dateToStr(membershipDetailVO.getExpiredDateTime()));
				summaryDTO.getMembershipMap().put(membershipDetail.getId(), membershipDetailVO);
			}
		}

		for (AiChatUserMembershipDetailVO vo : summaryDTO.getMembershipMap().values()) {
			summaryDTO.setDailyBonus(summaryDTO.getDailyBonus() + vo.getDailyBonus());
			if (summaryDTO.getChatHistoryCountLimit() < vo.getChatHistoryCountLimit()) {
				summaryDTO.setChatHistoryCountLimit(vo.getChatHistoryCountLimit());
			}
		}

		cacheService.getMembershipCacheMap().put(aiChatUserId, summaryDTO);

		return summaryDTO;
	}

	@Override
	public AiChatBuyMembershipFromWechatResult buyMembershipFromWechat(WechatPayJsApiFeedbackDTO completeDTO, Long wechatUserId) {
//		TODO
		AiChatBuyMembershipFromWechatResult r = new AiChatBuyMembershipFromWechatResult();
		if (completeDTO == null || completeDTO.getResource() == null || completeDTO.getResource().getDecrypt() == null) {
			r.setMessage("Decrypt error");
			return r;
		}

		WechatPayJsApiFeedbackDecryptDTO decryptDTO = completeDTO.getResource().getDecrypt();
		if(decryptDTO == null) {
			sendTelegramMessage("收到付款失败回调 无法获取 decryptDTO part: " + JSONObject.fromObject(completeDTO));
			r.setMessage("付款异常, 已通知客服, 请稍后 0x6");
			return r;
		}
		
		try {
			if (!"success".equals(decryptDTO.getTrade_state().toLowerCase())) {
				sendTelegramMessage("收到付款失败回调, 付款失败: " + completeDTO.toString());
				r.setMessage("付款异常, 已通知客服, 请稍后 0x0");
				return r;
			}
		} catch (Exception e) {
			sendTelegramMessage("解读付款异常: " + completeDTO.toString());
			r.setMessage("付款异常, 已通知客服, 请稍后 0x1");
			return r;
		}

		BuyMembershipFromWechatAttachmentDTO atta = decryptDTO.getAttach();
		Long membershipId = systemOptionService.decryptPrivateKey(atta.getMembershipPk());
		if (membershipId == null) {
			sendTelegramMessage("收到付款失败回调 无对应 membership ID: " + membershipId);
			r.setMessage("付款异常, 已通知客服, 请稍后 0x2");
			return r;
		}

		AiChatUserMembershipDetailDTO membershipDetail = getMembershipConfigMap().get(membershipId);
		if (membershipDetail == null) {
			sendTelegramMessage("收到付款失败回调 无对应 membership level detail, membershipId: " + membershipId);
			r.setMessage("付款异常, 已通知客服, 请稍后 0x3");
			return r;
		}

		Long aiChatUserId = userService.__getAiChatUserIdByWechatUserId(wechatUserId);
		if (aiChatUserId == null) {
			sendTelegramMessage("收到付款失败回调 无对应 AI chat user ID from weChatUserId: " + wechatUserId);
			r.setMessage("付款异常, 已通知客服, 请稍后 0x4");
			return r;
		}
		AiChatUserMembershipKey key = new AiChatUserMembershipKey();
		key.setAiChatUserId(aiChatUserId);
		key.setMembershipId(membershipId);
		AiChatUserMembership memberShipPO = membershipMapper.selectByPrimaryKey(key);

		if (memberShipPO == null) {
			memberShipPO = new AiChatUserMembership();
			memberShipPO.setAiChatUserId(aiChatUserId);
			memberShipPO.setMembershipId(membershipId);
			LocalDateTime expiredTime = LocalDateTime.now().with(LocalTime.MAX)
					.plusDays(membershipDetail.getEffectiveDays() - 1);
			memberShipPO.setExpiredTime(expiredTime);
			membershipMapper.insertSelective(memberShipPO);
		} else {
			if (memberShipPO.getIsDelete()) {
				memberShipPO.setIsDelete(false);
				LocalDateTime expiredTime = LocalDateTime.now().with(LocalTime.MAX)
						.plusDays(membershipDetail.getEffectiveDays() - 1);
				memberShipPO.setExpiredTime(expiredTime);
			} else {
				LocalDateTime expiredTime = memberShipPO.getExpiredTime().with(LocalTime.MAX)
						.plusDays(membershipDetail.getEffectiveDays());
				memberShipPO.setExpiredTime(expiredTime);
			}
			membershipMapper.updateByPrimaryKeySelective(memberShipPO);
		}

		userService.recharge(aiChatUserId, AiChatAmountType.BONUS, new BigDecimal(membershipDetail.getDailyBonus()));
		userService.recharge(aiChatUserId, AiChatAmountType.RECHARGE, new BigDecimal(membershipDetail.getRecharge()));

		findMembershipDetailSummaryByUserId(aiChatUserId, true);
		
//		TODO 保存交易流水到数据库 + 本地 json 文件
		r.setOpenId(decryptDTO.getPayer().getOpenId());
		r.setOutTradeNo(decryptDTO.getOut_trade_no());
		r.setIsSuccess();
		return r;
	}

	@Override
	public void rechargeDailyBonusByMemberShip() {
		Map<Long, AiChatUserMembershipDetailDTO> membershipMap = getMembershipConfigMap();
		for (AiChatUserMembershipDetailDTO membership : membershipMap.values()) {
			batchRechargeDailyBonusByMemberId(membership);
		}
	}

	private void batchRechargeDailyBonusByMemberId(AiChatUserMembershipDetailDTO membershipDetail) {
		AiChatUserMembershipExample example = new AiChatUserMembershipExample();
		example.createCriteria().andMembershipIdEqualTo(membershipDetail.getId()).andIsDeleteEqualTo(false)
				.andExpiredTimeGreaterThan(LocalDateTime.now());
		List<AiChatUserMembership> userMemberList = membershipMapper.selectByExample(example);
		if (userMemberList.isEmpty()) {
			return;
		}
		List<Long> userIdList = new ArrayList<>();
		for (AiChatUserMembership membership : userMemberList) {
			userIdList.add(membership.getAiChatUserId());
		}
		userService.batchRecharge(userIdList, AiChatAmountType.BONUS, new BigDecimal(membershipDetail.getDailyBonus()));
	}

	@Override
	public void updateDeleteMarkByExpiredTime() {
		AiChatUserMembershipExample example = new AiChatUserMembershipExample();
		example.createCriteria().andIsDeleteEqualTo(false)
				.andExpiredTimeLessThan(LocalDateTime.now().with(LocalTime.MIN));
		AiChatUserMembership row = new AiChatUserMembership();
		row.setIsDelete(true);
		membershipMapper.updateByExampleSelective(row, example);
	}
}
