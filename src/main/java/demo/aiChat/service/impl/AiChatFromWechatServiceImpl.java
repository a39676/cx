package demo.aiChat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import aiChat.pojo.dto.SendChatingMessageFromWechatDTO;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.aiChat.pojo.dto.AiChatAesOptionDTO;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.aiChat.pojo.result.AiChatSendNewMessageResult;
import demo.aiChat.pojo.vo.OpenAiUserDetailVO;
import demo.aiChat.service.AiChatFromWechatService;
import demo.aiChat.service.AiChatService;
import demo.interaction.wechat.pojo.po.WechatUserDetail;
import demo.interaction.wechat.service.WechatUserService;
import toolPack.encryptHandle.EncryptUtil;

@Service
public class AiChatFromWechatServiceImpl extends AiChatCommonService implements AiChatFromWechatService {

	@Autowired
	private AiChatUserAssociateWechatUidMapper associtateMapper;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private AiChatService aiChatService;
	@Autowired
	private EncryptUtil encryptUtil;

	public AiChatSendNewMessageResult sendNewChatFromWechat(EncryptDTO encryptedDTO) {
		SendChatingMessageFromWechatDTO dto = null;
		AiChatSendNewMessageResult r = null;
		try {
			AiChatAesOptionDTO aesOption = optionService.getAesOptionMap().get("1");
			String decryptStr = encryptUtil.aesDecrypt(aesOption.getAesKey(), aesOption.getAesInitVector(), encryptedDTO.getEncryptedStr());
			dto = new Gson().fromJson(decryptStr, SendChatingMessageFromWechatDTO.class);
		} catch (Exception e) {
			r = new AiChatSendNewMessageResult();
			r.setMessage(e.getLocalizedMessage());
			return r;
		}

		Long aiChatUserId = findAiChatUserIdByWechatOpenId(dto.getUid());
		
		if(aiChatUserId == null) {
//			TODO 未关注 未创建?
			return r;
		}
		
		return aiChatService.sendNewChatMessage(aiChatUserId, dto.getMsg());
	}

	private Long findAiChatUserIdByWechatOpenId(String openId) {
		/*
		 * TODO 以 wechat uid 查找 openAiUserId 需要做缓存, 放置在 option service if 无对应用户 新建用户
		 * else 查找旧资料 如何判断前端是不是随便传一个 UID 骗取试用?
		 */

		if(cacheService.getOpenIdMatchAiChatUserIdMap().containsKey(openId)) {
			return cacheService.getOpenIdMatchAiChatUserIdMap().get(openId);
		}
		
		WechatUserDetail wechatUser = wechatUserService.findWechatUserByOpenId(openId);
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

		cacheService.getOpenIdMatchAiChatUserIdMap().put(openId, aiChatUserId);

		return aiChatUserId;
	}

	public OpenAiUserDetailVO findUserDetailVoByWechatUid(String uid) {
		@SuppressWarnings("unused")
		Long aiChatUserId = findAiChatUserIdByWechatOpenId(uid);

//		TODO find user detail from ai chat user service, may move this function to other service
		OpenAiUserDetailVO vo = new OpenAiUserDetailVO();
//		vo.setUserPk(systemOptionService.encryptId(detail.getId()));
//		vo.setBonusAmount(detail.getBonusAmount().doubleValue());
//		vo.setRechargeAmount(detail.getRechargeAmount().doubleValue());
		return vo;
	}
}
