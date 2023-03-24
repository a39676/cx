package demo.aiChat.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.type.AiChatAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.mapper.AiChatUserAmountHistoryMapper;
import demo.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.aiChat.mapper.AiChatUserDetailMapper;
import demo.aiChat.mapper.SystemUserAssociateAiChatUserMapper;
import demo.aiChat.pojo.po.AiChatUserAmountHistory;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.pojo.po.SystemUserAssociateAiChatUserExample;
import demo.aiChat.pojo.po.SystemUserAssociateAiChatUserKey;
import demo.aiChat.pojo.result.CreateAiChatUserResult;
import demo.aiChat.service.AiChatUserService;

@Service
public class AiChatUserServiceImpl extends AiChatCommonService implements AiChatUserService {

	@Autowired
	private AiChatUserDetailMapper userDetailMapper;
	@Autowired
	private SystemUserAssociateAiChatUserMapper systemUserAssociateMapper;
	@Autowired
	private AiChatUserAssociateWechatUidMapper aiChatUserAssociateWechatUidMapper;
	@Autowired
	private AiChatUserAmountHistoryMapper amountHistoryMapper;

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

		cacheService.getSystemUserIdMatchAiChatUserIdMap().put(systemUserId, newAiChatUserId);
		r.setAiChatUserId(newAiChatUserId);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CreateAiChatUserResult createAiChatUserDetailByWechatUid(Long wechatUserId, String wechatOid) {
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

		cacheService.getOpenIdMatchAiChatUserIdMap().put(wechatOid, newAiChatUserId);

		Long tmpKey = snowFlake.getNextId();
		tmpKeyInsertOrUpdateLiveTime(tmpKey, newAiChatUserId);

		r.setTmpKey(tmpKey);
		r.setAiChatUserId(newAiChatUserId);
		r.setIsSuccess();
		return r;
	}

	@Override
	public Long createNewTmpKey(Long wechatUserId, String openId) {
		Long tmpKey = null;
		Long aiChatUserId = cacheService.getOpenIdMatchAiChatUserIdMap().get(openId);
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
			CreateAiChatUserResult createUserResult = createAiChatUserDetailByWechatUid(wechatUserId, openId);
			return createUserResult.getTmpKey();
		}
		AiChatUserAssociateWechatUidKey associate = associateList.get(0);

		cacheService.getOpenIdMatchAiChatUserIdMap().put(openId, associate.getAiChatUserId());

		tmpKey = snowFlake.getNextId();
		tmpKeyInsertOrUpdateLiveTime(tmpKey, associate.getAiChatUserId());
		return tmpKey;
	}

	@Override
	public void extendTmpKeyValidity(Long tmpKey) {
		log.error("Receive tmp key extend request, tmp key: " + tmpKey);
		if (tmpKey == null) {
			return;
		}
		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKey);
		if (aiChatUserId != null) {
			log.error("Extended, tmp key: " + tmpKey + ", ai chat user id: " + aiChatUserId);
			tmpKeyInsertOrUpdateLiveTime(tmpKey, aiChatUserId);
		}
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
}
