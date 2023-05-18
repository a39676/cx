package demo.ai.manager.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.ai.aiChat.mapper.AiChatUserDetailMapper;
import demo.ai.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.ai.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.ai.aiChat.pojo.po.AiChatUserDetail;
import demo.ai.aiChat.pojo.po.AiChatUserDetailExample;
import demo.ai.aiChat.pojo.po.AiChatUserDetailExample.Criteria;
import demo.ai.aiChat.pojo.vo.AiChatUserVO;
import demo.ai.common.service.impl.AiCommonService;
import demo.ai.manager.pojo.dto.GetAiUserListDTO;
import demo.ai.manager.pojo.result.GetAiUserListResult;
import demo.ai.manager.service.AiUserManagerService;

@Service
public class AiUserManagerServiceImpl extends AiCommonService implements AiUserManagerService {

	@Autowired
	private AiChatUserDetailMapper userDetailMapper;
	@Autowired
	private AiChatUserAssociateWechatUidMapper aiChatUserAssociateWechatUidMapper;

	@Override
	public CommonResult blockUserByPk(String aiChatUserPk) {
		if (StringUtils.isBlank(aiChatUserPk)) {
			return new CommonResult();
		}
		Long aiChatUserId = systemOptionService.decryptPrivateKey(aiChatUserPk);
		return blockUser(aiChatUserId);
	}

	private CommonResult blockUser(Long aiChatUserId) {
		CommonResult r = new CommonResult();
		AiChatUserDetail row = new AiChatUserDetail();
		row.setId(aiChatUserId);
		row.setIsBlock(true);
		int updateCount = userDetailMapper.updateByPrimaryKeySelective(row);
		if (updateCount == 1) {
			r.setIsSuccess();
		}
		return r;
	}

	@Override
	public CommonResult unlockUserByPk(String aiChatUserPk) {
		CommonResult r = new CommonResult();
		Long aiChatUserId = systemOptionService.decryptPrivateKey(aiChatUserPk);

		if (aiChatUserId == null) {
			r.setMessage("Wrong pk");
			return r;
		}

		AiChatUserDetail row = new AiChatUserDetail();
		row.setId(aiChatUserId);
		row.setIsBlock(false);
		int updateCount = userDetailMapper.updateByPrimaryKeySelective(row);
		if (updateCount == 1) {
			r.setIsSuccess();
		}
		return r;
	}

	@Override
	public GetAiUserListResult getAiChatUserList(GetAiUserListDTO dto) {
		GetAiUserListResult r = new GetAiUserListResult();

		RowBounds rowBounds = new RowBounds(0, 10);
		AiChatUserDetailExample example = new AiChatUserDetailExample();
		Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(dto.getNickname())) {
			criteria.andNicknameLike("%" + dto.getNickname() + "%");
		}
		if (StringUtils.isNotBlank(dto.getCreateTimeMinStr())) {
			try {
				criteria.andCreateTimeGreaterThan(
						localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getCreateTimeMinStr()));
			} catch (Exception e) {
			}
		}
		if (StringUtils.isNotBlank(dto.getCreateTimeMaxStr())) {
			try {
				criteria.andCreateTimeLessThan(
						localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getCreateTimeMaxStr()));
			} catch (Exception e) {
			}
		}
		if (dto.getIsDelete() != null) {
			criteria.andIsDeleteEqualTo(dto.getIsDelete());
		}
		if (dto.getIsBlock() != null) {
			criteria.andIsBlockEqualTo(dto.getIsBlock());
		}
		if (dto.getIsWarning() != null) {
			criteria.andIsWarningEqualTo(dto.getIsWarning());
		}
		if (dto.getBonusAmountMin() != null) {
			criteria.andBonusAmountGreaterThanOrEqualTo(new BigDecimal(dto.getBonusAmountMin()));
		}
		if (dto.getBonusAmountMax() != null) {
			criteria.andBonusAmountLessThanOrEqualTo(new BigDecimal(dto.getBonusAmountMax()));
		}
		if (dto.getRechargeAmountMin() != null) {
			criteria.andRechargeAmountGreaterThanOrEqualTo(new BigDecimal(dto.getRechargeAmountMin()));
		}
		if (dto.getRechargeAmountMax() != null) {
			criteria.andRechargeAmountLessThanOrEqualTo(new BigDecimal(dto.getRechargeAmountMax()));
		}
		if (dto.getUsedTokensMin() != null) {
			criteria.andUsedTokensGreaterThanOrEqualTo(dto.getUsedTokensMin());
		}
		if (dto.getUsedTokensMax() != null) {
			criteria.andUsedTokensLessThanOrEqualTo(dto.getUsedTokensMax());
		}
		if (StringUtils.isNotBlank(dto.getLastUpdateTimeMaxStr())) {
			try {
				criteria.andLastUpdateLessThanOrEqualTo(
						localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getLastUpdateTimeMaxStr()));
			} catch (Exception e) {
			}
		}
		if (StringUtils.isNotBlank(dto.getLastUpdateTimeMinStr())) {
			try {
				criteria.andLastUpdateGreaterThanOrEqualTo(
						localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getLastUpdateTimeMinStr()));
			} catch (Exception e) {
			}
		}
		if (StringUtils.isNotBlank(dto.getSourceQrCodePk())) {
			Long qrCodeId = systemOptionService.decryptPrivateKey(dto.getSourceQrCodePk());
			List<Long> aiChatUserIdList = new ArrayList<>();
			if (qrCodeId == null) {
				r.setUserList(new ArrayList<>());
				r.setIsSuccess();
				return r;
			}
			List<Long> wechatUserIdList = wechatSdkForInterService.__getWechatUserIdListByQrCodeId(qrCodeId);
			if (wechatUserIdList.isEmpty()) {
				r.setUserList(new ArrayList<>());
				r.setIsSuccess();
				return r;
			}
			AiChatUserAssociateWechatUidExample associateExample = new AiChatUserAssociateWechatUidExample();
			associateExample.createCriteria().andWechatIdIn(wechatUserIdList);
			List<AiChatUserAssociateWechatUidKey> associateList = aiChatUserAssociateWechatUidMapper
					.selectByExample(associateExample);
			for (AiChatUserAssociateWechatUidKey associate : associateList) {
				aiChatUserIdList.add(associate.getAiChatUserId());
			}
			if (!aiChatUserIdList.isEmpty()) {
				criteria.andIdIn(aiChatUserIdList);
			}
		}
		// 若没有排序条件
		if (StringUtils.isBlank(dto.getOrderBy())) {
			Long startId = systemOptionService.decryptPrivateKey(dto.getStartPk());
			if (startId != null) {
				criteria.andIdGreaterThanOrEqualTo(startId);
			}
			// 若有排序条件
		} else {
			example.setOrderByClause(dto.getOrderBy());
		}
		if (StringUtils.isNotBlank(dto.getOrderBy())) {
			if (dto.getIsAesc() != null && !dto.getIsAesc()) {
				example.setOrderByClause(example.getOrderByClause() + " desc");
			}
		} else {
			if (dto.getIsAesc() != null && !dto.getIsAesc()) {
				example.setOrderByClause(example.getOrderByClause() + " desc");
			}
		}
		if (dto.getLimit() != null) {
			if (dto.getLimit() < 0 || dto.getLimit() > 50) {
				dto.setLimit(10);
			}
			rowBounds = new RowBounds(0, dto.getLimit());
		}

		List<AiChatUserDetail> userDetailList = userDetailMapper.selectByExampleWithRowbounds(example, rowBounds);
		if (userDetailList.isEmpty()) {
			r.setUserList(new ArrayList<>());
			r.setIsSuccess();
			return r;
		}

		List<Long> aiChatUserIdList = new ArrayList<>();
		for (AiChatUserDetail user : userDetailList) {
			aiChatUserIdList.add(user.getId());
		}

		AiChatUserAssociateWechatUidExample associateExample = new AiChatUserAssociateWechatUidExample();
		associateExample.createCriteria().andAiChatUserIdIn(aiChatUserIdList);
		List<AiChatUserAssociateWechatUidKey> associateList = aiChatUserAssociateWechatUidMapper
				.selectByExample(associateExample);

		// aiChatUserId : wechatUserId
		Map<Long, Long> associateMap = new HashMap<>();
		for (AiChatUserAssociateWechatUidKey associate : associateList) {
			associateMap.put(associate.getAiChatUserId(), associate.getWechatId());
		}

		List<AiChatUserVO> userVoList = new ArrayList<>();
		AiChatUserVO vo = null;
		Long wechatUserId = null;
		if (userDetailList.isEmpty()) {
			vo = new AiChatUserVO();
			vo.setNickname("Empty result");
			userVoList.add(vo);
			r.setUserList(userVoList);
			r.setIsSuccess();
			return r;
		}

		for (AiChatUserDetail user : userDetailList) {
			vo = new AiChatUserVO();
			vo.setBonusAmount(user.getBonusAmount().doubleValue());
			vo.setCreateTime(localDateTimeHandler.dateToStr(user.getCreateTime()));
			if (user.getLastUpdate() != null) {
				vo.setLastUpdateTime(localDateTimeHandler.dateToStr(user.getLastUpdate()));
			}
			vo.setIsBlock(user.getIsBlock());
			vo.setIsDelete(user.getIsDelete());
			vo.setNickname(user.getNickname());
			vo.setRechargeAmount(user.getRechargeAmount().doubleValue());
			vo.setUsedTokens(user.getUsedTokens());
			vo.setUserPk(systemOptionService.encryptId(user.getId()));
			vo.setIsWarning(user.getIsWarning());
			wechatUserId = associateMap.get(user.getId());
			if (wechatUserId != null) {
				vo.setWechatUserPk(systemOptionService.encryptId(wechatUserId));
			}
			userVoList.add(vo);
		}

		r.setUserList(userVoList);
		r.setIsSuccess();
		return r;
	}
}
