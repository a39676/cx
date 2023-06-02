package demo.ai.aiChat.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ai.aiChat.pojo.result.AiChatDailySignUpResult;
import ai.aiChat.pojo.result.GetAiChatAmountResult;
import ai.aiChat.pojo.type.AiServiceAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.dto.AiUserDetailInJsonDTO;
import demo.ai.aiChat.mapper.AiChatUserAmountHistoryMapper;
import demo.ai.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.ai.aiChat.mapper.AiChatUserDetailMapper;
import demo.ai.aiChat.mapper.SystemUserAssociateAiChatUserMapper;
import demo.ai.aiChat.pojo.dto.NewPositiveAiUserDTO;
import demo.ai.aiChat.pojo.po.AiChatUserAmountHistory;
import demo.ai.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.ai.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.ai.aiChat.pojo.po.AiChatUserDetail;
import demo.ai.aiChat.pojo.po.AiChatUserDetailExample;
import demo.ai.aiChat.pojo.po.SystemUserAssociateAiChatUserExample;
import demo.ai.aiChat.pojo.po.SystemUserAssociateAiChatUserKey;
import demo.ai.aiChat.pojo.result.CreateAiUserResult;
import demo.ai.aiChat.service.AiUserService;
import demo.ai.common.service.impl.AiCommonService;
import demo.ai.manager.pojo.dto.AiChatUserEditNicknameDTO;
import demo.interaction.wechat.pojo.po.WechatQrcodeDetail;
import demo.interaction.wechat.pojo.vo.WechatQrcodeVO;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AiUserServiceImpl extends AiCommonService implements AiUserService {

	@Autowired
	private AiChatUserDetailMapper userDetailMapper;
	@Autowired
	private SystemUserAssociateAiChatUserMapper systemUserAssociateMapper;
	@Autowired
	private AiChatUserAssociateWechatUidMapper aiChatUserAssociateWechatUidMapper;
	@Autowired
	private AiChatUserAmountHistoryMapper amountHistoryMapper;
	@Autowired
	private FileUtilCustom fileUtil;

	@Override
	public CreateAiUserResult createAiChatUserDetailBySystemUserId(Long systemUserId) {
		CreateAiUserResult r = new CreateAiUserResult();

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

		recharge(newAiChatUserId, AiServiceAmountType.BONUS, new BigDecimal(aiChatOptionService.getBonusForNewUser()));

		aiChatCacheService.getSystemUserIdMatchAiChatUserIdMap().put(systemUserId, newAiChatUserId);
		r.setAiChatUserId(newAiChatUserId);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CreateAiUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserId, String wechatOpenId,
			Integer specialBonus) {
		CreateAiUserResult r = new CreateAiUserResult();

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
			recharge(newAiChatUserId, AiServiceAmountType.BONUS,
					new BigDecimal(aiChatOptionService.getBonusForNewUser()));
		} else {
			recharge(newAiChatUserId, AiServiceAmountType.BONUS, new BigDecimal(specialBonus));
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
	public CreateAiUserResult createAiChatUserDetailByWechatOpenId(Long wechatUserId, String wechatOpenId) {
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
			CreateAiUserResult createUserResult = createAiChatUserDetailByWechatOpenId(wechatUserId, openId);
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
	public CommonResult recharge(Long aiChatUserId, AiServiceAmountType amountType, BigDecimal amount) {
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

		if (AiServiceAmountType.RECHARGE.equals(amountType)) {
			addRechargeMarkLiveAWeek(aiChatUserId);
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult batchRecharge(List<Long> aiChatUserIdList, AiServiceAmountType amountType, BigDecimal amount) {
		CommonResult r = new CommonResult();
		if (amountType == null) {
			return r;
		}

		int updateCount = 0;
		if (AiServiceAmountType.BONUS.equals(amountType)) {
			updateCount = userDetailMapper.batchRecharge(aiChatUserIdList, amount, null);
		} else if (AiServiceAmountType.RECHARGE.equals(amountType)) {
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

		if (aiChatOptionService.getMaxBonusForDailySignUp() < po.getBonusAmount().intValue()) {
			r.setMessage("已经囤了不少了...先使用一部分再来签到吧");
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
	public List<NewPositiveAiUserDTO> __findNewPositiveAiChatUserDtoListInYesterday() {
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

		List<NewPositiveAiUserDTO> dtoList = new ArrayList<>();
		List<Long> wechatUserId = new ArrayList<>();
		NewPositiveAiUserDTO dto = null;
		for (AiChatUserAssociateWechatUidKey associate : associateList) {
			wechatUserId.add(associate.getWechatId());
			dto = new NewPositiveAiUserDTO();
			dto.setAiChatUserId(associate.getAiChatUserId());
			dto.setWechatUserId(associate.getWechatId());
			dtoList.add(dto);
		}

		return dtoList;
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
		if (detail == null) {
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
			bonusAmountHistory.setAmountType(AiServiceAmountType.BONUS.getCode());
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
		rechargeAmountHistory.setAmountType(AiServiceAmountType.RECHARGE.getCode());
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

	@Override
	public AiUserDetailInJsonDTO getAiUserDetailInJson(Long userId) {
		String pathPrefix = aiChatOptionService.getUserExtraJsonDetailStorePrefixPath();
		try {
			String content = fileUtil.getStringFromFile(pathPrefix + File.separator + userId + ".json");
			AiUserDetailInJsonDTO detailDTO = buildObjFromJsonCustomization(content, AiUserDetailInJsonDTO.class);
			return detailDTO;
		} catch (Exception e) {
			return new AiUserDetailInJsonDTO();
		}
	}

	@Override
	public CommonResult refreshAiUserDetailInJson(AiUserDetailInJsonDTO dto) {
		CommonResult r = new CommonResult();

		String pathPrefix = aiChatOptionService.getUserExtraJsonDetailStorePrefixPath();
		try {
			File parentFolder = new File(pathPrefix);
			if (!parentFolder.exists()) {
				if (!parentFolder.mkdirs()) {
					r.setMessage("Save detail failed, can NOT create folder, error");
					return r;
				}
			}
			JSONObject json = JSONObject.fromObject(dto);
			fileUtil.byteToFile(json.toString(), pathPrefix + File.separator + dto.getUserId() + ".json");
			r.setIsSuccess();
			return r;
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
			return r;
		}
	}

	@Override
	public void tidyAiUserExtraDetail() {
//		String pathPrefix = aiChatOptionService.getUserExtraJsonDetailStorePrefixPath();
//		File parentFolder = new File(pathPrefix);
//		if (!parentFolder.exists()) {
//			parentFolder.mkdirs();
//			return;
//		}
//		File[] files = parentFolder.listFiles();
//		String content;
//		AiUserDetailInJsonDTO dto = null;
//		boolean refreshFlag = false;
//		for (File f : files) {
//			if (!f.getName().endsWith(".json")) {
//				continue;
//			}
//			content = fileUtil.getStringFromFile(f.getAbsolutePath());
//			dto = buildObjFromJsonCustomization(content, AiUserDetailInJsonDTO.class);
//
//			/*
//			 * waiting
//			 */
//			if (refreshFlag) {
//				refreshAiUserDetailInJson(dto);
//			}
//			refreshFlag = false;
//		}
	}

	@Override
	public void updateUsedTokenToDetailInJson() {
		AiChatUserDetailExample example = new AiChatUserDetailExample();
		example.createCriteria().andUsedTokensNotEqualTo(0);
		List<AiChatUserDetail> userPoList = userDetailMapper.selectByExample(example);
		if (userPoList.isEmpty()) {
			return;
		}
		LocalDateTime now = LocalDateTime.now();
		String yyyyMM = String.valueOf("" + now.getYear() + now.getMonthValue());
		for (AiChatUserDetail user : userPoList) {
			updateUsedTokenToDetailInJson(user.getId(), yyyyMM);
		}
	}

	private void updateUsedTokenToDetailInJson(Long aiUserId, String yyyyMM) {
		AiUserDetailInJsonDTO detailDTO = getAiUserDetailInJson(aiUserId);
		AiChatUserDetail userDetail = userDetailMapper.selectByPrimaryKey(aiUserId);
		Integer usedToken = userDetail.getUsedTokens();
		if (usedToken == 0) {
			return;
		}
		Map<String, Double> usedTokenMap = detailDTO.getUsedTokenMap();
		if (usedTokenMap == null) {
			usedTokenMap = new HashMap<>();
		}
		usedTokenMap.put(yyyyMM, usedToken.doubleValue());
		detailDTO.setUsedTokenMap(usedTokenMap);
		refreshAiUserDetailInJson(detailDTO);

		userDetail.setUsedTokens(0);
		userDetailMapper.updateByPrimaryKeySelective(userDetail);
	}
}
