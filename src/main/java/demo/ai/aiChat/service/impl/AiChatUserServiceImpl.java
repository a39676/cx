package demo.ai.aiChat.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ai.aiChat.pojo.result.AiChatDailySignUpResult;
import ai.aiChat.pojo.result.GetAiChatAmountResult;
import ai.aiChat.pojo.type.AiChatAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiChat.mapper.AiChatUserAmountHistoryMapper;
import demo.ai.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.ai.aiChat.mapper.AiChatUserDetailMapper;
import demo.ai.aiChat.mapper.SystemUserAssociateAiChatUserMapper;
import demo.ai.aiChat.pojo.dto.AiChatUserEditNicknameDTO;
import demo.ai.aiChat.pojo.dto.GetAiChatUserListDTO;
import demo.ai.aiChat.pojo.dto.NewPositiveAiChatUserDTO;
import demo.ai.aiChat.pojo.po.AiChatUserAmountHistory;
import demo.ai.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.ai.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.ai.aiChat.pojo.po.AiChatUserDetail;
import demo.ai.aiChat.pojo.po.AiChatUserDetailExample;
import demo.ai.aiChat.pojo.po.SystemUserAssociateAiChatUserExample;
import demo.ai.aiChat.pojo.po.SystemUserAssociateAiChatUserKey;
import demo.ai.aiChat.pojo.po.AiChatUserDetailExample.Criteria;
import demo.ai.aiChat.pojo.result.CreateAiChatUserResult;
import demo.ai.aiChat.pojo.result.GetAiChatUserListResult;
import demo.ai.aiChat.pojo.vo.AiChatUserVO;
import demo.ai.aiChat.service.AiChatUserService;
import demo.ai.common.service.impl.AiCommonService;
import demo.interaction.wechat.pojo.po.WechatQrcodeDetail;
import demo.interaction.wechat.pojo.vo.WechatQrcodeVO;
import demo.interaction.wechat.service.WechatSdkForInterService;

@Service
public class AiChatUserServiceImpl extends AiCommonService implements AiChatUserService {

	@Autowired
	private AiChatUserDetailMapper userDetailMapper;
	@Autowired
	private SystemUserAssociateAiChatUserMapper systemUserAssociateMapper;
	@Autowired
	private AiChatUserAssociateWechatUidMapper aiChatUserAssociateWechatUidMapper;
	@Autowired
	private AiChatUserAmountHistoryMapper amountHistoryMapper;

	@Autowired
	private WechatSdkForInterService wechatSdkForInterService;

	@Override
	public CreateAiChatUserResult createAiChatUserDetailBySystemUserId(Long systemUserId) {
		CreateAiChatUserResult r = new CreateAiChatUserResult();

		SystemUserAssociateAiChatUserExample associateExample = new SystemUserAssociateAiChatUserExample();
		associateExample.createCriteria().andSystemUserIdEqualTo(systemUserId);
		List<SystemUserAssociateAiChatUserKey> associateList = systemUserAssociateMapper
				.selectByExample(associateExample);
		if (associateList != null && !associateList.isEmpty()) {
			r.setAiChatUserId(associateList.get(0).getAiChatUserId());
			r.setMessage("Already create AI chat profile data");
			return r;
		}

		Long newAiChatUserId = snowFlake.getNextId();
		SystemUserAssociateAiChatUserKey associatePO = new SystemUserAssociateAiChatUserKey();
		associatePO.setAiChatUserId(newAiChatUserId);
		associatePO.setSystemUserId(systemUserId);
		systemUserAssociateMapper.insertSelective(associatePO);

		AiChatUserDetail aiChatUserDetailPO = new AiChatUserDetail();
		aiChatUserDetailPO.setId(newAiChatUserId);
		userDetailMapper.insertSelective(aiChatUserDetailPO);

		recharge(newAiChatUserId, AiChatAmountType.BONUS, new BigDecimal(aiChatOptionService.getBonusForNewUser()));

		aiChatCacheService.getSystemUserIdMatchAiChatUserIdMap().put(systemUserId, newAiChatUserId);
		r.setAiChatUserId(newAiChatUserId);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CreateAiChatUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserId, String wechatOpenId,
			Integer specialBonus) {
		CreateAiChatUserResult r = new CreateAiChatUserResult();

		AiChatUserAssociateWechatUidExample associateExample = new AiChatUserAssociateWechatUidExample();
		associateExample.createCriteria().andWechatIdEqualTo(wechatUserId);
		List<AiChatUserAssociateWechatUidKey> associateList = aiChatUserAssociateWechatUidMapper
				.selectByExample(associateExample);
		if (associateList != null && !associateList.isEmpty()) {
			r.setAiChatUserId(associateList.get(0).getAiChatUserId());
			r.setMessage("Already create AI chat profile data");
			return r;
		}

		Long newAiChatUserId = snowFlake.getNextId();
		AiChatUserAssociateWechatUidKey associatePO = new AiChatUserAssociateWechatUidKey();
		associatePO.setAiChatUserId(newAiChatUserId);
		associatePO.setWechatId(wechatUserId);
		aiChatUserAssociateWechatUidMapper.insertSelective(associatePO);

		AiChatUserDetail aiChatUserDetailPO = new AiChatUserDetail();
		aiChatUserDetailPO.setId(newAiChatUserId);
		userDetailMapper.insertSelective(aiChatUserDetailPO);

		if (specialBonus == null) {
			recharge(newAiChatUserId, AiChatAmountType.BONUS, new BigDecimal(aiChatOptionService.getBonusForNewUser()));
		} else {
			recharge(newAiChatUserId, AiChatAmountType.BONUS, new BigDecimal(specialBonus));
		}

		aiChatCacheService.getOpenIdMatchAiChatUserIdMap().put(wechatOpenId, newAiChatUserId);

		Long tmpKey = snowFlake.getNextId();
		tmpKeyInsertOrUpdateLiveTime(tmpKey, newAiChatUserId);

		r.setTmpKey(tmpKey);
		r.setAiChatUserId(newAiChatUserId);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CreateAiChatUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserId, String wechatOpenId) {
		return createAiChatUserDetailByWechatOpenId(wechatUserId, wechatOpenId, null);
	}

	@Override
	public Long createNewTmpKey(Long wechatUserId, String openId) {
		Long tmpKey = null;
		Long aiChatUserId = aiChatCacheService.getOpenIdMatchAiChatUserIdMap().get(openId);
		if (aiChatUserId != null) {
			tmpKey = snowFlake.getNextId();
			tmpKeyInsertOrUpdateLiveTime(tmpKey, aiChatUserId);
			return tmpKey;
		}

		AiChatUserAssociateWechatUidExample associateExample = new AiChatUserAssociateWechatUidExample();
		associateExample.createCriteria().andWechatIdEqualTo(wechatUserId);
		List<AiChatUserAssociateWechatUidKey> associateList = aiChatUserAssociateWechatUidMapper
				.selectByExample(associateExample);
		if (associateList.isEmpty()) {
			CreateAiChatUserResult createUserResult = createAiChatUserDetailByWechatOpenId(wechatUserId, openId);
			return createUserResult.getTmpKey();
		}
		AiChatUserAssociateWechatUidKey associate = associateList.get(0);

		aiChatCacheService.getOpenIdMatchAiChatUserIdMap().put(openId, associate.getAiChatUserId());

		tmpKey = snowFlake.getNextId();
		tmpKeyInsertOrUpdateLiveTime(tmpKey, associate.getAiChatUserId());
		return tmpKey;
	}

	@Override
	public void extendTmpKeyValidity(Long tmpKey) {
		super.extendTmpKeyValidity(tmpKey);
	}

	@Override
	public Long __getAiChatUserIdByWechatUserId(Long wechatUserId) {
		if (wechatUserId == null) {
			return null;
		}
		AiChatUserAssociateWechatUidExample example = new AiChatUserAssociateWechatUidExample();
		example.createCriteria().andWechatIdEqualTo(wechatUserId);
		List<AiChatUserAssociateWechatUidKey> associate = aiChatUserAssociateWechatUidMapper.selectByExample(example);
		if (associate.isEmpty()) {
			return null;
		}
		return associate.get(0).getAiChatUserId();
	}

	@Override
	public CommonResult recharge(Long aiChatUserId, AiChatAmountType amountType, BigDecimal amount) {
		CommonResult r = new CommonResult();
		if (aiChatUserId == null || amountType == null || amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
			r.setMessage("Param error");
			return r;
		}
		AiChatUserDetail userDetail = userDetailMapper.selectByPrimaryKey(aiChatUserId);
		if (userDetail == null) {
			r.setMessage("Param error");
			return r;
		}

		switch (amountType) {
		case BONUS:
			userDetail.setBonusAmount(userDetail.getBonusAmount().add(amount));
			break;
		case RECHARGE:
			userDetail.setRechargeAmount(userDetail.getRechargeAmount().add(amount));
			break;
		}

		int updateCount = userDetailMapper.updateByPrimaryKeySelective(userDetail);
		if (updateCount < 1) {
			r.setMessage("Update amount failed, AI chat user ID: " + aiChatUserId + ", amount type: "
					+ amountType.getName() + ", amount: " + amount);
			return r;
		}

		AiChatUserAmountHistory amountHistory = new AiChatUserAmountHistory();
		amountHistory.setId(snowFlake.getNextId());
		amountHistory.setUserId(aiChatUserId);
		amountHistory.setAmountChange(amount);
		amountHistory.setAmountType(amountType.getCode());
		updateCount = amountHistoryMapper.insertSelective(amountHistory);
		if (updateCount < 1) {
			r.setMessage("Update amount history failed, AI chat user ID: " + aiChatUserId + ", amount type: "
					+ amountType.getName() + ", amount: " + amount);
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult batchRecharge(List<Long> aiChatUserIdList, AiChatAmountType amountType, BigDecimal amount) {
		CommonResult r = new CommonResult();
		if (amountType == null) {
			return r;
		}

		int updateCount = 0;
		if (AiChatAmountType.BONUS.equals(amountType)) {
			updateCount = userDetailMapper.batchRecharge(aiChatUserIdList, amount, null);
		} else if (AiChatAmountType.RECHARGE.equals(amountType)) {
			updateCount = userDetailMapper.batchRecharge(aiChatUserIdList, null, amount);
		}

		if (aiChatUserIdList.size() == updateCount) {
			r.setIsSuccess();
		} else {
			log.error("AI chat 批量充值SQL执行异常, userIdListSize: " + aiChatUserIdList.size() + ", updateCount: "
					+ updateCount + ", user id List: " + aiChatUserIdList + ", amount type: " + amountType.getName()
					+ ", amount: " + amount);
			sendTelegramMessage("AI chat 批量充值SQL执行异常, userIdListSize: " + aiChatUserIdList.size() + ", updateCount: "
					+ updateCount + ", user id List: " + aiChatUserIdList + ", amount type: " + amountType.getName()
					+ ", amount: " + amount);
			r.setMessage("更新条数不正确");
		}
		return r;
	}

	@Override
	public GetAiChatAmountResult getAiChatAmount(String tmpKeyStr) {
		GetAiChatAmountResult r = new GetAiChatAmountResult();
		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKeyStr);
		if (aiChatUserId == null) {
			r.setMessage("登录已过期, 请重新登录");
			return r;
		}
		AiChatUserDetail po = userDetailMapper.selectByPrimaryKey(aiChatUserId);
		if (po == null) {
			r.setMessage("登录已过期, 请重新登录 0x2");
			return r;
		}
		extendTmpKeyValidity(Long.parseLong(tmpKeyStr));
		r.setAmount(po.getBonusAmount().intValue() + po.getRechargeAmount().intValue());
		r.setIsSuccess();
		return r;
	}

	@Override
	public AiChatDailySignUpResult dailySignUpFromWechat(String tmpKeyStr) {
		AiChatDailySignUpResult r = new AiChatDailySignUpResult();

		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKeyStr);
		if (aiChatUserId == null) {
			r.setMessage("请重新登录");
			return r;
		}
		AiChatUserDetail po = userDetailMapper.selectByPrimaryKey(aiChatUserId);
		if (po == null) {
			r.setMessage("登录已过期 请重新登录 0x3");
			return r;
		}

		if (hadDailySignUp(aiChatUserId)) {
			r.setMessage("今天已经签到, 明天再来吧");
			return r;
		}

		extendTmpKeyValidity(Long.parseLong(tmpKeyStr));

		po.setBonusAmount(po.getBonusAmount().add(new BigDecimal(aiChatOptionService.getDailySignUpBonus())));
		userDetailMapper.updateByPrimaryKeySelective(po);
		addAiChatUserIdDailySigned(po.getId());

		r.setNewAmount(po.getBonusAmount().add(po.getRechargeAmount()).intValue());
		r.setIsSuccess();
		r.setMessage("签到成功, 获得" + aiChatOptionService.getDailySignUpBonus() + "");
		return r;
	}

	@Override
	public Boolean hadDailySignUp(String tmpKeyStr) {
		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKeyStr);
		if (aiChatUserId == null) {
			return true;
		}
		AiChatUserDetail po = userDetailMapper.selectByPrimaryKey(aiChatUserId);
		if (po == null) {
			return true;
		}

		return hadDailySignUp(aiChatUserId);
	}

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
	public List<NewPositiveAiChatUserDTO> __findNewPositiveAiChatUserDtoListInYesterday() {
		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
		AiChatUserDetailExample example = new AiChatUserDetailExample();
		example.createCriteria().andIsBlockEqualTo(false).andIsDeleteEqualTo(false)
				.andCreateTimeBetween(yesterday.with(LocalTime.MIN), yesterday.with(LocalTime.MAX))
				.andUsedTokensGreaterThan(aiChatOptionService.getBonusForNewUser());
		List<AiChatUserDetail> userDetailList = userDetailMapper.selectByExample(example);
		if (userDetailList.isEmpty()) {
			return new ArrayList<>();
		}

		List<Long> aiChatUserIdList = new ArrayList<>();
		for (AiChatUserDetail userDetail : userDetailList) {
			aiChatUserIdList.add(userDetail.getId());
		}

		AiChatUserAssociateWechatUidExample associateExample = new AiChatUserAssociateWechatUidExample();
		associateExample.createCriteria().andAiChatUserIdIn(aiChatUserIdList);
		List<AiChatUserAssociateWechatUidKey> associateList = aiChatUserAssociateWechatUidMapper
				.selectByExample(associateExample);

		List<NewPositiveAiChatUserDTO> dtoList = new ArrayList<>();
		List<Long> wechatUserId = new ArrayList<>();
		NewPositiveAiChatUserDTO dto = null;
		for (AiChatUserAssociateWechatUidKey associate : associateList) {
			wechatUserId.add(associate.getWechatId());
			dto = new NewPositiveAiChatUserDTO();
			dto.setAiChatUserId(associate.getAiChatUserId());
			dto.setWechatUserId(associate.getWechatId());
			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public GetAiChatUserListResult getAiChatUserList(GetAiChatUserListDTO dto) {
		GetAiChatUserListResult r = new GetAiChatUserListResult();

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

	@Override
	public ModelAndView getAiChatUserListView() {
		ModelAndView view = new ModelAndView("aiChatJSP/userList");
		List<WechatQrcodeDetail> qrCodeList = wechatSdkForInterService.__getAllQrCode();
		List<WechatQrcodeVO> qrCodeVoList = new ArrayList<>();
		WechatQrcodeVO vo = null;
		if (!qrCodeList.isEmpty()) {
			for (WechatQrcodeDetail po : qrCodeList) {
				vo = new WechatQrcodeVO();
				vo.setPk(systemOptionService.encryptId(po.getId()));
				vo.setRemark(po.getRemark());
				vo.setSceneName(po.getSceneName());
				qrCodeVoList.add(vo);
			}
		}
		view.addObject("qrCodeList", qrCodeVoList);
		return view;
	}

	@Override
	public CommonResult editNickname(AiChatUserEditNicknameDTO dto) {
		if (StringUtils.isBlank(dto.getUserPk())) {
			return new CommonResult();
		}
		Long aiChatUserId = systemOptionService.decryptPrivateKey(dto.getUserPk());
		if (aiChatUserId == null) {
			return new CommonResult();
		}

		AiChatUserDetail po = userDetailMapper.selectByPrimaryKey(aiChatUserId);

		if (po == null) {
			return new CommonResult();
		}

		po.setNickname(dto.getNickname());
		userDetailMapper.updateByPrimaryKeySelective(po);
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult __giveUserWarningMark(Long aiChatUserId) {
		AiChatUserDetail po = userDetailMapper.selectByPrimaryKey(aiChatUserId);
		if (po == null) {
			return new CommonResult();
		}
		po.setIsWarning(true);
		int updateCount = userDetailMapper.updateByPrimaryKeySelective(po);
		CommonResult r = new CommonResult();
		if (updateCount == 1) {
			r.setIsSuccess();
		}
		return r;
	}

	@Override
	public CommonResult cleanUserWarningMark(String userPk) {
		if (StringUtils.isBlank(userPk)) {
			return new CommonResult();
		}
		Long aiChatUserId = systemOptionService.decryptPrivateKey(userPk);
		if (aiChatUserId == null) {
			return new CommonResult();
		}
		AiChatUserDetail row = new AiChatUserDetail();
		row.setId(aiChatUserId);
		row.setIsWarning(false);
		int updateCount = userDetailMapper.updateByPrimaryKeySelective(row);
		CommonResult r = new CommonResult();
		if (updateCount == 1) {
			r.setIsSuccess();
		} else {
			r.setMessage("ORM update error");
		}
		return r;

	}

	@Override
	public CommonResult __debitAmountAndAddTokenUsage(Long aiUserId, BigDecimal debitAmount) {
		CommonResult r = new CommonResult();
		if (debitAmount.compareTo(BigDecimal.ZERO) < 1) {
			r.setMessage("输入消耗额异常");
			return r;
		}
		
		AiChatUserDetail detail = userDetailMapper.selectByPrimaryKey(aiUserId);
		if(detail == null) {
			r.setMessage("Can NOT found correct AI user ID: " + aiUserId);
			return r;
		}

		AiChatUserAmountHistory bonusAmountHistory = null;
		BigDecimal restDebitAmount = new BigDecimal(debitAmount.doubleValue());

		detail.setUsedTokens(detail.getUsedTokens() + debitAmount.intValue());
		detail.setLastUpdate(LocalDateTime.now());

		if (detail.getBonusAmount().compareTo(BigDecimal.ZERO) > 0) {
			restDebitAmount = debitAmount.subtract(detail.getBonusAmount());

			if (detail.getBonusAmount().compareTo(debitAmount) <= 0) {
				detail.setBonusAmount(BigDecimal.ZERO);
			} else {
				detail.setBonusAmount(detail.getBonusAmount().subtract(debitAmount));
			}

			bonusAmountHistory = new AiChatUserAmountHistory();
			bonusAmountHistory.setId(snowFlake.getNextId());
			bonusAmountHistory.setAmountType(AiChatAmountType.BONUS.getCode());
			bonusAmountHistory.setAmountChange(BigDecimal.ZERO.subtract(debitAmount));
			bonusAmountHistory.setUserId(detail.getId());
		}

		if (restDebitAmount.doubleValue() <= 0) {
			userDetailMapper.updateByPrimaryKeySelective(detail);
			if (bonusAmountHistory != null) {
				amountHistoryMapper.insertSelective(bonusAmountHistory);
			}
			r.setIsSuccess();
			return r;
		}

		detail.setRechargeAmount(detail.getRechargeAmount().subtract(restDebitAmount));
		userDetailMapper.updateByPrimaryKeySelective(detail);

		AiChatUserAmountHistory rechargeAmountHistory = new AiChatUserAmountHistory();
		rechargeAmountHistory.setId(snowFlake.getNextId());
		rechargeAmountHistory.setAmountType(AiChatAmountType.RECHARGE.getCode());
		rechargeAmountHistory.setAmountChange(BigDecimal.ZERO.subtract(debitAmount.subtract(detail.getBonusAmount())));
		rechargeAmountHistory.setUserId(detail.getId());
		amountHistoryMapper.insertSelective(rechargeAmountHistory);
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public AiChatUserDetail __getUserDetail(Long aiUserId) {
		return userDetailMapper.selectByPrimaryKey(aiUserId);
	}
}
