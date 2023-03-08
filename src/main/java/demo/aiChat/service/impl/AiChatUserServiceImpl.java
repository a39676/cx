package demo.aiChat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.aiChat.mapper.AiChatUserDetailMapper;
import demo.aiChat.mapper.SystemUserAssociateAiChatUserMapper;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.pojo.po.SystemUserAssociateAiChatUserExample;
import demo.aiChat.pojo.po.SystemUserAssociateAiChatUserKey;
import demo.aiChat.service.AiChatUserService;
import demo.thirdPartyAPI.wechat.service.WechatUserService;

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
	public CommonResult createAiChatUserDetailBySystemUserId(Long systemUserId) {
		CommonResult r = new CommonResult();

		SystemUserAssociateAiChatUserExample associateExample = new SystemUserAssociateAiChatUserExample();
		associateExample.createCriteria().andSystemUserIdEqualTo(systemUserId);
		List<SystemUserAssociateAiChatUserKey> associateList = systemUserAssociateMapper
				.selectByExample(associateExample);
		if (associateList != null && !associateList.isEmpty()) {
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

		optionService.getSystemUserIdMatchAiChatUserIdMap().put(systemUserId, newAiChatUserId);

		return r;
	}

	@Override
	public CommonResult createAiChatUserDetailByWechatUid(String wechatUid) {
		CommonResult r = new CommonResult();
		
		Long wechatUserLongId = wechatUserService.findWechatLongIdByUid(wechatUid);
		if(wechatUserLongId == null) {
			r.setMessage("Wechat user data NOT ready");
			return r;
		}
		
		AiChatUserAssociateWechatUidExample associateExample = new AiChatUserAssociateWechatUidExample();
		associateExample.createCriteria().andWechatIdEqualTo(wechatUserLongId);
		List<AiChatUserAssociateWechatUidKey> associateList = aiChatUserAssociateWechatUidMapper.selectByExample(associateExample);
		if(associateList != null && !associateList.isEmpty()) {
			r.setMessage("Already create AI chat profile data");
			return r;
		}
		
		Long newAiChatUserId = snowFlake.getNextId();
		AiChatUserAssociateWechatUidKey associatePO = new AiChatUserAssociateWechatUidKey();
		associatePO.setAiChatUserId(newAiChatUserId);
		associatePO.setWechatId(wechatUserLongId);
		aiChatUserAssociateWechatUidMapper.insertSelective(associatePO);

		AiChatUserDetail aiChatUserDetailPO = new AiChatUserDetail();
		aiChatUserDetailPO.setId(newAiChatUserId);
		userDetailMapper.insertSelective(aiChatUserDetailPO);
		
		optionService.getUidMatchAiChatUserIdMap().put(wechatUid, newAiChatUserId);
		
		return r;
	}

}
