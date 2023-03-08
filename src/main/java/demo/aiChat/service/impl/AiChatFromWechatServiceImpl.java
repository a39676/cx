package demo.aiChat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.aiChat.pojo.result.AiChatSendNewMessageResult;
import demo.aiChat.pojo.vo.OpenAiUserDetailVO;
import demo.aiChat.service.AiChatFromWechatService;
import demo.aiChat.service.AiChatService;
import demo.thirdPartyAPI.openAI.pojo.dto.SendChatingMessageFromWechatDTO;
import demo.thirdPartyAPI.wechat.pojo.po.WechatUserDetail;
import demo.thirdPartyAPI.wechat.service.WechatUserService;

@Service
public class AiChatFromWechatServiceImpl extends AiChatCommonService implements AiChatFromWechatService {

	@Autowired
	private AiChatUserAssociateWechatUidMapper associtateMapper;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private AiChatService aiChatService;

	public AiChatSendNewMessageResult sendNewChatFromWechat(SendChatingMessageFromWechatDTO dto) {
		AiChatSendNewMessageResult r = new AiChatSendNewMessageResult();

		Long aiChatUserId = findAiChatUserIdByWechatUid(dto.getUid());
		
		if(aiChatUserId == null) {
//			TODO 未关注 未创建?
			return r;
		}
		
		return aiChatService.sendNewChatMessage(aiChatUserId, dto.getMsg());
	}

	private Long findAiChatUserIdByWechatUid(String uid) {
		/*
		 * TODO 以 wechat uid 查找 openAiUserId 需要做缓存, 放置在 option service if 无对应用户 新建用户
		 * else 查找旧资料 如何判断前端是不是随便传一个 UID 骗取试用?
		 */

		if(optionService.getUidMatchAiChatUserIdMap().containsKey(uid)) {
			return optionService.getUidMatchAiChatUserIdMap().get(uid);
		}
		
		WechatUserDetail wechatUser = wechatUserService.findWechatUserByUid(uid);
		if (wechatUser == null) {
			/*
			 * TODO create new user
			 * 
			 */
		}

		AiChatUserAssociateWechatUidExample associateExample = new AiChatUserAssociateWechatUidExample();
		associateExample.createCriteria().andWechatIdEqualTo(wechatUser.getId());
		List<AiChatUserAssociateWechatUidKey> associateList = associtateMapper.selectByExample(associateExample);
		if (associateList == null || associateList.isEmpty()) {
			/*
			 * TODO create new user ?
			 */
		}
		AiChatUserAssociateWechatUidKey associate = associateList.get(0);

		Long aiChatUserId = associate.getAiChatUserId();

		optionService.getUidMatchAiChatUserIdMap().put(uid, aiChatUserId);

		return aiChatUserId;
	}

	public OpenAiUserDetailVO findUserDetailVoByWechatUid(String uid) {
		@SuppressWarnings("unused")
		Long aiChatUserId = findAiChatUserIdByWechatUid(uid);

//		TODO find user detail from ai chat user service, may move this function to other service
		OpenAiUserDetailVO vo = new OpenAiUserDetailVO();
//		vo.setUserPk(systemOptionService.encryptId(detail.getId()));
//		vo.setBonusAmount(detail.getBonusAmount().doubleValue());
//		vo.setRechargeAmount(detail.getRechargeAmount().doubleValue());
		return vo;
	}
}
