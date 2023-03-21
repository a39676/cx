package demo.interaction.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.result.GetTmpKeyByOpenIdResult;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.aiChat.pojo.result.CreateAiChatUserResult;
import demo.aiChat.service.AiChatUserService;
import demo.interaction.wechat.mapper.WechatUserDetailMapper;
import demo.interaction.wechat.pojo.po.WechatUserDetail;
import demo.interaction.wechat.pojo.po.WechatUserDetailExample;
import demo.interaction.wechat.service.WechatUserService;
import wechatSdk.pojo.type.WechatOfficialAccountType;

@Service
public class WechatUserServiceImpl extends WechatCommonService implements WechatUserService {

	@Autowired
	private WechatUserDetailMapper wechatUserDetailMapper;
	@Autowired
	private AiChatUserService aiChatUserService;

	private static final String FAKE_UID_PREFIX = "FakeUid_";

	@Override
	public EncryptDTO getTmpKeyByOpenId(EncryptDTO dto) {
		GetTmpKeyByOpenIdResult r = new GetTmpKeyByOpenIdResult();
		String oid = decryptEncryptDTO(dto, String.class);
		if (oid == null) {
			r.setMessage("Oid = null");
			return encryptDTO(r);
		}

		WechatUserDetailExample example = new WechatUserDetailExample();
		example.createCriteria().andOpenIdEqualTo(oid);
		List<WechatUserDetail> wechatUserList = wechatUserDetailMapper.selectByExample(example);
		if (!wechatUserList.isEmpty()) {
			WechatUserDetail detail = wechatUserList.get(0);
			if (detail.getIsBlock()) {
				r.setMessage("Blocked user");
				return encryptDTO(r);
			}

			Long tmpKey = aiChatUserService.createNewTmpKey(detail.getId(), oid);
			r.setTmpKey(tmpKey);
			r.setIsSuccess();
			return encryptDTO(r);
		}

		WechatUserDetail detail = createWechatUserDetailWithOnlyOpenId(oid);
		CreateAiChatUserResult createAiChatUserResult = aiChatUserService
				.createAiChatUserDetailByWechatUid(detail.getId(), oid);
		r.setTmpKey(createAiChatUserResult.getTmpKey());
		r.setIsSuccess();
		return encryptDTO(r);
	}

	private WechatUserDetail createWechatUserDetailWithOnlyOpenId(String oid) {
		WechatUserDetail po = new WechatUserDetail();
		po.setId(snowFlake.getNextId());
		po.setUnionId(FAKE_UID_PREFIX + po.getId());
		po.setOpenId(oid);
		po.setSourceOfficialAccount(WechatOfficialAccountType.SUI_SHOU.getCode());
		wechatUserDetailMapper.insertSelective(po);

		return po;
	}

}
