package demo.ai.aiChat.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiChat.pojo.result.AiChatBuyMembershipFromWechatResult;
import ai.aiChat.pojo.result.GetAiChatMembershipResult;
import ai.aiChat.pojo.type.AiChatAmountType;
import ai.aiChat.pojo.vo.AiChatUserMembershipDetailSummaryVO;
import ai.aiChat.pojo.vo.AiChatUserMembershipDetailVO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiChat.mapper.AiChatUserMembershipMapper;
import demo.ai.aiChat.pojo.dto.AiChatUserMembershipDetailDTO;
import demo.ai.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.ai.aiChat.pojo.po.AiChatUserMembership;
import demo.ai.aiChat.pojo.po.AiChatUserMembershipExample;
import demo.ai.aiChat.pojo.po.AiChatUserMembershipKey;
import demo.ai.aiChat.service.AiChatMembershipService;
import demo.ai.aiChat.service.AiChatUserService;
import demo.ai.common.service.impl.AiCommonService;
import net.sf.json.JSONObject;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.ioHandle.FileUtilCustom;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDTO;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDecryptDTO;
import wechatSdk.pojo.dto.AiServiceBuyMembershipFromWechatAttachmentDTO;

@Service
public class AiChatMembershipServiceImpl extends AiCommonService implements AiChatMembershipService {

	@Autowired
	private AiChatUserMembershipMapper userMembershipMapper;
	@Autowired
	private AiChatUserService userService;

	private Map<Long, AiChatUserMembershipDetailDTO> getMembershipConfigMap() {
		List<AiChatUserMembershipDetailDTO> membershipList = aiChatOptionService.getMembershipLDetails();
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
		List<AiChatUserMembership> membershipList = userMembershipMapper.selectByExample(example);
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
		if (!refresh && aiChatCacheService.getMembershipCacheMap().containsKey(aiChatUserId)) {
			return aiChatCacheService.getMembershipCacheMap().get(aiChatUserId);
		}

		List<AiChatUserMembership> membershipPoList = findMembershipDetailListByUserId(aiChatUserId);

		AiChatUserMembershipDetailSummaryDTO summaryDTO = new AiChatUserMembershipDetailSummaryDTO();
		summaryDTO.setAiChatUserId(aiChatUserId);

		Map<Long, AiChatUserMembershipDetailDTO> membershipConfigMap = getMembershipConfigMap();
		if (membershipPoList == null) {
			membershipPoList = new ArrayList<>();
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
					membershipDetailVO.setExpiredDatetime(membershipPO.getExpiredTime());
				} else {
					if (membershipDetailVO.getExpiredDatetime().isBefore(membershipPO.getExpiredTime())) {
						membershipDetailVO.setExpiredDatetime(membershipPO.getExpiredTime());
					}
				}
				membershipDetailVO.setChatHistoryCountLimit(membershipDetail.getChatHistoryCountLimit());
				membershipDetailVO.setExpiredTimeStr(localDateTimeHandler
						.dateToStr(membershipDetailVO.getExpiredDatetime(), DateTimeUtilCommon.normalDateFormat));
				summaryDTO.getMembershipMap().put(membershipDetail.getId(), membershipDetailVO);
			}
		}

		for (AiChatUserMembershipDetailVO vo : summaryDTO.getMembershipMap().values()) {
			summaryDTO.setDailyBonus(summaryDTO.getDailyBonus() + vo.getDailyBonus());
			if (summaryDTO.getChatHistoryCountLimit() < vo.getChatHistoryCountLimit()) {
				summaryDTO.setChatHistoryCountLimit(vo.getChatHistoryCountLimit());
			}
		}

		if (membershipPoList.isEmpty()
				&& summaryDTO.getChatHistoryCountLimit() < aiChatOptionService.getChatHistoryCountLimitForFreeUser()) {
			summaryDTO.setChatHistoryCountLimit(aiChatOptionService.getChatHistoryCountLimitForFreeUser());
		}
		aiChatCacheService.getMembershipCacheMap().put(aiChatUserId, summaryDTO);

		return summaryDTO;
	}

	@Override
	public AiChatBuyMembershipFromWechatResult buyMembershipFromWechat(WechatPayJsApiFeedbackDTO completeDTO,
			Long wechatUserId) {
		AiChatBuyMembershipFromWechatResult r = new AiChatBuyMembershipFromWechatResult();
		if (completeDTO == null || completeDTO.getResource() == null
				|| completeDTO.getResource().getDecrypt() == null) {
			r.setMessage("Decrypt error");
			return r;
		}

		WechatPayJsApiFeedbackDecryptDTO decryptDTO = completeDTO.getResource().getDecrypt();
		if (decryptDTO == null) {
			sendTelegramMessage("收到付款失败回调 无法获取 decryptDTO part: " + JSONObject.fromObject(completeDTO));
			r.setMessage("付款异常, 已通知客服, 请稍后 0x6");
			return r;
		}

		try {
			if (!"success".equals(decryptDTO.getTrade_state().toLowerCase())) {
				sendTelegramMessage("收到付款失败回调, 付款失败: " + completeDTO.toString());
				r.setIsSuccess();
				r.setMessage("付款失败 0x00");
				return r;
			}
		} catch (Exception e) {
			sendTelegramMessage("解读付款异常: " + completeDTO.toString());
			r.setMessage("付款异常, 已通知客服, 请稍后 0x1");
			return r;
		}

		AiServiceBuyMembershipFromWechatAttachmentDTO atta = decryptDTO.getAttach();
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

		if (membershipDetail.getEffectiveDays() > 0) {
			insertOrUpdateMembershipRecord(aiChatUserId, membershipDetail);
		}
		if (membershipDetail.getDailyBonus() > 0) {
			userService.recharge(aiChatUserId, AiChatAmountType.BONUS,
					new BigDecimal(membershipDetail.getDailyBonus()));
		}
		if (membershipDetail.getRecharge() > 0) {
			userService.recharge(aiChatUserId, AiChatAmountType.RECHARGE,
					new BigDecimal(membershipDetail.getRecharge()));
		}

		findMembershipDetailSummaryByUserId(aiChatUserId, true);

		savePayResult(completeDTO, aiChatUserId);

		r.setOpenId(decryptDTO.getPayer().getOpenid());
		r.setOutTradeNo(decryptDTO.getOut_trade_no());
		r.setIsSuccess();
		return r;
	}

	private void insertOrUpdateMembershipRecord(Long aiChatUserId, AiChatUserMembershipDetailDTO membershipDetail) {
		AiChatUserMembershipKey key = new AiChatUserMembershipKey();
		key.setAiChatUserId(aiChatUserId);
		key.setMembershipId(membershipDetail.getId());
		AiChatUserMembership memberShipPO = userMembershipMapper.selectByPrimaryKey(key);

		if (memberShipPO == null) {
			memberShipPO = new AiChatUserMembership();
			memberShipPO.setAiChatUserId(aiChatUserId);
			memberShipPO.setMembershipId(membershipDetail.getId());
			LocalDateTime expiredTime = LocalDateTime.now().with(LocalTime.MAX)
					.plusDays(membershipDetail.getEffectiveDays() - 1);
			memberShipPO.setExpiredTime(expiredTime);
			userMembershipMapper.insertSelective(memberShipPO);
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
			userMembershipMapper.updateByPrimaryKeySelective(memberShipPO);
		}
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
		List<AiChatUserMembership> userMemberList = userMembershipMapper.selectByExample(example);
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
		userMembershipMapper.updateByExampleSelective(row, example);
	}

	public CommonResult savePayResult(WechatPayJsApiFeedbackDTO completeDTO, Long aiChatUserId) {
		CommonResult r = new CommonResult();
		String storePrefixPath = aiChatOptionService.getPayResultStorePrefixPath();
		LocalDate today = LocalDate.now();
		Long orderId = Long.parseLong(completeDTO.getResource().getDecrypt().getOut_trade_no());
		String outputFilePath = storePrefixPath + File.separator + today.getYear() + today.getMonthValue()
				+ today.getDayOfMonth() + File.separator + aiChatUserId + File.separator + orderId + ".json";
		File outputFile = new File(outputFilePath);
		File parentFolder = outputFile.getParentFile();
		if (!parentFolder.exists()) {
			if (!parentFolder.mkdirs()) {
				r.setMessage("保存交易数据 JSON 文件失败, 无法创建文件夹: " + parentFolder.getAbsolutePath());
				sendTelegramMessage("保存交易数据 JSON 文件失败, 无法创建文件夹: " + parentFolder.getAbsolutePath() + ", data: "
						+ JSONObject.fromObject(completeDTO).toString());
				return r;
			}
		}

		FileUtilCustom fileUtil = new FileUtilCustom();
		fileUtil.byteToFile(JSONObject.fromObject(completeDTO).toString().getBytes(StandardCharsets.UTF_8),
				outputFilePath);
		if (!outputFile.exists()) {
			r.setMessage("保存交易数据 JSON 文件失败, 无法写入文件: " + parentFolder.getAbsolutePath());
			sendTelegramMessage("保存交易数据 JSON 文件失败, 无法写入文件: " + parentFolder.getAbsolutePath() + ", data: "
					+ JSONObject.fromObject(completeDTO).toString());
		} else {
			r.setIsSuccess();
		}
		return r;
	}

	@Override
	public GetAiChatMembershipResult getMembershipListFromWechat(String tmpKeyStr) {
		GetAiChatMembershipResult r = new GetAiChatMembershipResult();
		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKeyStr);
		if (aiChatUserId == null) {
			r.setMessage("登录已过期, 请重新登录");
			return r;
		}

		AiChatUserMembershipDetailSummaryDTO membershipSummaryDetailDTO = findMembershipDetailSummaryByUserId(
				aiChatUserId, false);
		AiChatUserMembershipDetailSummaryVO membershipSummaryDetailVO = new AiChatUserMembershipDetailSummaryVO();
		membershipSummaryDetailVO.setChatHistoryCountLimit(membershipSummaryDetailDTO.getChatHistoryCountLimit());
		membershipSummaryDetailVO.setDailyBonus(membershipSummaryDetailDTO.getDailyBonus());
		membershipSummaryDetailVO.setDescription(membershipSummaryDetailDTO.getDescription());
		membershipSummaryDetailVO.setMembershipMap(membershipSummaryDetailDTO.getMembershipMap());
		r.setMembershipSummaryDetailVO(membershipSummaryDetailVO);

		Map<Long, AiChatUserMembershipDetailDTO> membershipConfigMap = getMembershipConfigMap();
		List<Long> membershipIdList = new ArrayList<>();
		membershipIdList.addAll(membershipConfigMap.keySet());
		Collections.sort(membershipIdList);

		AiChatUserMembershipDetailVO vo = null;
		AiChatUserMembershipDetailDTO membershipDetail = null;
		AiChatUserMembershipDetailVO summaryVoFromCache = null;
		for (Long id : membershipIdList) {
			vo = new AiChatUserMembershipDetailVO();
			summaryVoFromCache = membershipSummaryDetailDTO.getMembershipMap().get(id);
			membershipDetail = membershipConfigMap.get(id);
			vo.setPk(URLEncoder.encode(systemOptionService.encryptId(id), StandardCharsets.UTF_8));
			vo.setDescription(membershipDetail.getDescription());
			vo.setChatHistoryCountLimit(membershipDetail.getChatHistoryCountLimit());
			vo.setDailyBonus(membershipDetail.getDailyBonus());
			vo.setPrice(membershipDetail.getPrice());
			vo.setIsNormalPlan(membershipDetail.getIsNormalPlan());
			if (summaryVoFromCache != null) {
				vo.setIsValid(true);
				vo.setExpiredDatetime(summaryVoFromCache.getExpiredDatetime());
				vo.setExpiredTimeStr(summaryVoFromCache.getExpiredTimeStr());
			} else {
				vo.setIsValid(false);
			}
			r.getMembershipList().add(vo);
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public AiChatBuyMembershipFromWechatResult __giftMembership(Long wechatUserId, Long membershipId) {
		AiChatBuyMembershipFromWechatResult r = new AiChatBuyMembershipFromWechatResult();

		if (membershipId == null) {
			sendTelegramMessage("赠送会员失败 无对应 membership ID: " + membershipId);
			r.setMessage("赠送异常, 已通知客服, 请稍后 1x2");
			return r;
		}

		AiChatUserMembershipDetailDTO membershipDetail = getMembershipConfigMap().get(membershipId);
		if (membershipDetail == null) {
			sendTelegramMessage("赠送会员失败 无对应 membership level detail, membershipId: " + membershipId);
			r.setMessage("赠送异常, 已通知客服, 请稍后 1x3");
			return r;
		}

		Long aiChatUserId = userService.__getAiChatUserIdByWechatUserId(wechatUserId);
		if (aiChatUserId == null) {
			sendTelegramMessage("赠送会员失败 无对应 AI chat user ID from weChatUserId: " + wechatUserId);
			r.setMessage("赠送异常, 已通知客服, 请稍后 1x4");
			return r;
		}

		AiChatUserMembershipKey key = new AiChatUserMembershipKey();
		key.setAiChatUserId(aiChatUserId);
		key.setMembershipId(membershipId);
		AiChatUserMembership po = userMembershipMapper.selectByPrimaryKey(key);
		if (po != null && !po.getIsDelete() && po.getExpiredTime().isAfter(LocalDateTime.now())) {
			r.setMessage("已经获得赠送");
			return r;
		}

		if (membershipDetail.getEffectiveDays() > 0) {
			insertOrUpdateMembershipRecord(aiChatUserId, membershipDetail);
		}
		if (membershipDetail.getDailyBonus() > 0) {
			userService.recharge(aiChatUserId, AiChatAmountType.BONUS,
					new BigDecimal(membershipDetail.getDailyBonus()));
		}
		if (membershipDetail.getRecharge() > 0) {
			userService.recharge(aiChatUserId, AiChatAmountType.BONUS, new BigDecimal(membershipDetail.getRecharge()));
		}
		if (membershipDetail.getValidDays() != null) {
			/*
			 * TODO 未确定如何实现有效期赠送额度 难在实现过期日清理赠送额度, 赠送额度应独立计算, 并优先使用
			 * 待后续实现"子帐号"再实现此部分逻辑, 目前以简单的赠金额度计算
			 */
		}
		if (membershipDetail.getBonus() > 0) {
			userService.recharge(aiChatUserId, AiChatAmountType.BONUS, new BigDecimal(membershipDetail.getBonus()));
		}

		findMembershipDetailSummaryByUserId(aiChatUserId, true);

		r.setIsSuccess();
		return r;
	}

}
