package demo.aiChat.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import demo.aiChat.service.AiChatFromWechatService;
import demo.thirdPartyAPI.openAI.pojo.dto.SendChatingMessageFromWechatDTO;
import demo.thirdPartyAPI.openAI.pojo.vo.OpenAiUserDetailVO;

@Service
public class AiChatFromWechatServiceImpl extends AiChatCommonService implements AiChatFromWechatService {

	public ChatResult sendNewChatFromWechat(SendChatingMessageFromWechatDTO dto) {
		ChatResult r = new ChatResult();
		
		/*
		 * TODO
		 * find detail
		 * check amount
		 * find history
		 * send msg
		 *   if msg handle success
		 *     debit
		 *     update history
		 *     feedback
		 *   else
		 *     error feedback
		 *     
		 */
		
		OpenAiUserDetail userDetail = findUserDetailByWechatUid(dto.getUid());
	}

	private OpenAiUserDetail findUserDetailByWechatUid(String uid) {
		/*
		 * TODO 
		 * 以 wechat uid 查找 openAiUserId 需要做缓存, 放置在 option service
		 * if 无对应用户
		 *   新建用户
		 * else
		 *   查找旧资料
		 * 如何判断前端是不是随便传一个 UID 骗取试用?
		 */
		
		WechatOidUid wechatUser = wechatUserService.findWechatUserByUid(uid);
		if(wechatUser == null) {
			/* 
			 * TODO
			 * create new user
			 * 
			 */
		}
		
		OpenAiUserAssociateWechatUidExample associateExample = new OpenAiUserAssociateWechatUidExample();
		associateExample.createCriteria().andWechatIdEqualTo(wechatUser.getId());
		List<OpenAiUserAssociateWechatUidKey> associateList = openAiUserAssociateWechatUidMapper.selectByExample(associateExample);
		if(associateList == null || associateList.isEmpty()) {
			/*
			 * TODO
			 * create new user ?
			 */
		}
		OpenAiUserAssociateWechatUidKey associate = associateList.get(0);
		
		OpenAiUserDetail openAiUserDetail = detailMapper.selectByPrimaryKey(associate.getOpenAiUserId());
		
		return openAiUserDetail;
	}
	
	@Override
	public OpenAiUserDetailVO findUserDetailVoByWechatUid(String uid) {
		OpenAiUserDetail detail = findUserDetailByWechatUid(uid);
		OpenAiUserDetailVO vo = new OpenAiUserDetailVO();
		vo.setUserPk(systemOptionService.encryptId(detail.getId()));
		vo.setBonusAmount(detail.getBonusAmount().doubleValue());
		vo.setRechargeAmount(detail.getRechargeAmount().doubleValue());
		return vo;
	}
}
