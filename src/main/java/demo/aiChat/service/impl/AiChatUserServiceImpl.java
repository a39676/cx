package demo.aiChat.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.type.AiChatAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.aiChat.mapper.AiChatUserDetailMapper;
import demo.aiChat.mapper.SystemUserAssociateAiChatUserMapper;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.pojo.po.SystemUserAssociateAiChatUserExample;
import demo.aiChat.pojo.po.SystemUserAssociateAiChatUserKey;
import demo.aiChat.pojo.result.CreateAiChatUserResult;
import demo.aiChat.service.AiChatUserService;
import demo.interaction.wechat.service.WechatUserService;

@Service
public class AiChatUserServiceImpl extends AiChatCommonService implements AiChatUserService {

	@Autowired
	private AiChatUserDetailMapper userDetailMapper;
	@Autowired
	private SystemUserAssociateAiChatUserMapper systemUserAssociateMapper;
	@Autowired
	private AiChatUserAssociateWechatUidMapper aiChatUserAssociateWechatUidMapper;
	@Autowired
	private WechatUserService wechatUserService;

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
	public Long __getAiChatUserIdByOpenId(String openId) {
		Long wechatUserId = wechatUserService.__getWechatUserIdByOpenId(openId);
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
	public CommonResult recharge(Long aiChatUserId, AiChatAmountType amountType,
			BigDecimal amount) {
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
		
		r.setIsSuccess();
		return r;
	}
}
