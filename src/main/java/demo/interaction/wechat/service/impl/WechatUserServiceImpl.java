package demo.interaction.wechat.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.result.GetAiChatAmountResult;
import aiChat.pojo.result.GetAiChatMembershipResult;
import aiChat.pojo.result.GetAiChatUserDetailResult;
import aiChat.pojo.result.GetTmpKeyByOpenIdResult;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.pojo.result.CreateAiChatUserResult;
import demo.aiChat.service.AiChatMembershipService;
import demo.aiChat.service.AiChatUserService;
import demo.interaction.wechat.mapper.WechatUserDetailMapper;
import demo.interaction.wechat.pojo.po.WechatUserDetail;
import demo.interaction.wechat.pojo.po.WechatUserDetailExample;
import demo.interaction.wechat.service.WechatUserService;
import demo.interaction.wechatPay.service.WechatPayService;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDTO;
import wechatSdk.pojo.type.WechatOfficialAccountType;

@Service
public class WechatUserServiceImpl extends WechatCommonService implements WechatUserService {

	@Autowired
	private AiChatUserService aiChatUserService;
	@Autowired
	private AiChatMembershipService aiChatMembershipService;
	@Autowired
	private WechatUserDetailMapper wechatUserDetailMapper;
	@Autowired
	private WechatPayService wechatPayService;

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

	@Override
	public void extendTmpKeyValidity(EncryptDTO dto) {
		Long tmpKey = decryptEncryptDTO(dto, Long.class);
		aiChatUserService.extendTmpKeyValidity(tmpKey);
	}

	@Override
	public Long __getWechatUserIdByOpenId(String openId) {
		if (StringUtils.isBlank(openId)) {
			return null;
		}
		WechatUserDetailExample example = new WechatUserDetailExample();
		example.createCriteria().andOpenIdEqualTo(openId);
		List<WechatUserDetail> wechatUserList = wechatUserDetailMapper.selectByExample(example);
		if (wechatUserList.isEmpty()) {
			return null;
		}
		return wechatUserList.get(0).getId();
	}

	@Override
	public EncryptDTO buyMembershipFromWechat(EncryptDTO encryptedDTO) {
		CommonResult r = new CommonResult();
		WechatPayJsApiFeedbackDTO dto = decryptEncryptDTO(encryptedDTO, WechatPayJsApiFeedbackDTO.class);
		if (dto == null) {
			sendTelegramMessage("收到购买会员信息, 解码失败, text: " + encryptedDTO.getEncryptedStr());
			r.setMessage("付款异常, 已通知客服跟进, 请稍后");
			return encryptDTO(r);
		}

		Long orderId = null;
		try {
			orderId = Long.parseLong(dto.getResource().getDecrypt().getOut_trade_no());
			boolean hadPaySuccess = wechatPayService.hadHandleSuccess(orderId);
			if (hadPaySuccess) {
				r.setIsSuccess();
				r.setMessage("已经处理此付款请求");
				return encryptDTO(r);
			}
		} catch (Exception e) {
			sendTelegramMessage("收到购买会员信息, 解码失败, 无法判断是否付款成功, text: " + encryptedDTO.getEncryptedStr());
			r.setMessage("付款异常, 已通知客服跟进, 请稍后");
			return encryptDTO(r);
		}

		wechatPayService._insertPayHistoryBeforeAiChatHandle(dto);

		String openId = dto.getResource().getDecrypt().getPayer().getOpenid();
		Long wechatUserId = __getWechatUserIdByOpenId(openId);
		r = aiChatMembershipService.buyMembershipFromWechat(dto, wechatUserId);

		if (r.isSuccess()) {
			wechatPayService.updateHandleStatus(orderId, r.isSuccess());
		}

		return encryptDTO(r);
	}

	@Override
	public EncryptDTO getAiChatUserDetail(EncryptDTO encryptedDTO) {
		String tmpKeyStr = decryptEncryptDTO(encryptedDTO, String.class);
		GetAiChatUserDetailResult r = new GetAiChatUserDetailResult();
		GetAiChatMembershipResult aiChatMembershipResult = aiChatMembershipService.getMembershipListFromWechat(tmpKeyStr);
		if(aiChatMembershipResult.isFail()) {
			r.setMessage(aiChatMembershipResult.getMessage());
			return encryptDTO(r);
		}
		r.setMembershipList(aiChatMembershipResult.getMembershipList());
		r.setMembershipSummaryDetailVO(aiChatMembershipResult.getMembershipSummaryDetailVO());
		
		GetAiChatAmountResult amountResult = aiChatUserService.getAiChatAmount(tmpKeyStr);
		r.setAmount(amountResult.getAmount());
		
		r.setSignedUpToday(aiChatUserService.hadDailySignUp(tmpKeyStr));
		r.setIsSuccess();
		return encryptDTO(r);
	}
	
	@Override
	public EncryptDTO dailySignUp(EncryptDTO encryptedDTO) {
		String tmpKeyStr = decryptEncryptDTO(encryptedDTO, String.class);
		CommonResult r = aiChatUserService.dailySignUpFromWechat(tmpKeyStr);
		return encryptDTO(r);
	}
}
