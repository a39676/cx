package demo.interaction.wechat.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiChat.pojo.result.FindAllApiKeysResult;
import ai.aiChat.pojo.result.GenerateNewApiKeyResult;
import ai.aiChat.pojo.result.GetAiChatAmountResult;
import ai.aiChat.pojo.result.GetAiChatMembershipResult;
import ai.aiChat.pojo.result.GetAiChatUserDetailResult;
import ai.aiChat.pojo.result.GetTmpKeyByOpenIdResult;
import ai.aiChat.pojo.type.AiServiceAmountType;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiChat.pojo.dto.NewPositiveAiUserDTO;
import demo.ai.aiChat.pojo.result.CreateAiUserResult;
import demo.ai.aiChat.service.AiChatFromApiService;
import demo.ai.aiChat.service.AiChatMembershipService;
import demo.ai.aiChat.service.AiUserService;
import demo.ai.aiChat.service.impl.AiChatOptionService;
import demo.interaction.wechat.mapper.WechatQrcodeDetailMapper;
import demo.interaction.wechat.mapper.WechatUserDetailMapper;
import demo.interaction.wechat.mapper.WechatUserFromQrcodeMapper;
import demo.interaction.wechat.mq.producer.SendBonusRechargeTemplateMessageProducer;
import demo.interaction.wechat.pojo.po.WechatQrcodeDetail;
import demo.interaction.wechat.pojo.po.WechatQrcodeDetailExample;
import demo.interaction.wechat.pojo.po.WechatUserDetail;
import demo.interaction.wechat.pojo.po.WechatUserDetailExample;
import demo.interaction.wechat.pojo.po.WechatUserFromQrcode;
import demo.interaction.wechat.service.WechatUserService;
import demo.interaction.wechatPay.service.WechatPayService;
import wechatPayApi.jsApi.pojo.dto.WechatPayJsApiFeedbackDTO;
import wechatSdk.pojo.constant.WechatSdkUrlConstant;
import wechatSdk.pojo.dto.AiServiceDeleteApiKeyDTO;
import wechatSdk.pojo.dto.WechatGetUserOpenIdListDTO;
import wechatSdk.pojo.dto.WechatRecordingUserFromParameterizedQrCodeDTO;
import wechatSdk.pojo.dto.WechatSendTemplateMessageBonusRechargeDTO;
import wechatSdk.pojo.result.GetUserOpenIdListResult;
import wechatSdk.pojo.type.WechatOfficialAccountType;
import wechatSdk.pojo.type.WechatQrCodeSceneType;
import wechatSdk.pojo.type.WechatSdkCommonResultType;

@Service
public class WechatUserServiceImpl extends WechatCommonService implements WechatUserService {

	@Autowired
	private AiChatFromApiService aiChatFromApiService;
	@Autowired
	private AiUserService aiChatUserService;
	@Autowired
	private AiChatMembershipService aiChatMembershipService;
	@Autowired
	private AiChatOptionService aiChatOptionService;
	@Autowired
	private WechatUserDetailMapper wechatUserDetailMapper;
	@Autowired
	private WechatPayService wechatPayService;
	@Autowired
	private WechatQrcodeDetailMapper qrcodeMapper;
	@Autowired
	private WechatUserFromQrcodeMapper userFromQrcodeMapper;

	@Autowired
	private SendBonusRechargeTemplateMessageProducer sendBonusRechargeTemplateMessageProducer;

	private static final String FAKE_UID_PREFIX = "FakeUid_";
	/** Hard code before grow up */
	private static final int MAX_OPEN_ID_LIST_SIZE = 10000;

	@Override
	public EncryptDTO getTmpKeyByOpenId(EncryptDTO dto) {
		GetTmpKeyByOpenIdResult r = new GetTmpKeyByOpenIdResult();
		String oid = decryptEncryptDTO(dto, String.class);
		if (StringUtils.isBlank(oid) || "null" == oid) {
			r.setMessage("Oid = null");
			return encryptDTO(r);
		}

		WechatUserDetailExample example = new WechatUserDetailExample();
		example.createCriteria().andOpenIdEqualTo(oid);
		List<WechatUserDetail> wechatUserList = wechatUserDetailMapper.selectByExample(example);
		WechatUserDetail detail = null;
		if (wechatUserList.isEmpty()) {
			detail = createWechatUserDetailWithOpenIdForSuiShou(oid);
			if (detail == null) {
				r.setMessage("Create user failed");
				return encryptDTO(r);
			}
			CreateAiUserResult createAiChatUserResult = aiChatUserService
					.createAiChatUserDetailByWechatOpenId(detail.getId(), oid);
			r.setTmpKey(createAiChatUserResult.getTmpKey());
			r.setIsSuccess();

		} else {
			detail = wechatUserList.get(0);
			if (detail.getIsBlock()) {
				r.setMessage("Blocked user");
				return encryptDTO(r);
			}

			Long tmpKey = aiChatUserService.createNewTmpKey(detail.getId(), oid);
			r.setTmpKey(tmpKey);
			r.setIsSuccess();
		}

		return encryptDTO(r);
	}

	private WechatUserDetail createWechatUserDetailWithOpenIdForSuiShou(String oid) {
		if (StringUtils.isBlank(oid)) {
			return null;
		}
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
		GetAiChatMembershipResult aiChatMembershipResult = aiChatMembershipService
				.getMembershipListFromWechat(tmpKeyStr);
		if (aiChatMembershipResult.isFail()) {
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

	@Override
	public EncryptDTO recordingWechatUserFromParameterizedQrCode(EncryptDTO encrypedDTO) {
		CommonResult r = new CommonResult();
		WechatRecordingUserFromParameterizedQrCodeDTO dto = decryptEncryptDTO(encrypedDTO,
				WechatRecordingUserFromParameterizedQrCodeDTO.class);
		r = recordingWechatUserFromParameterizedQrCode(dto);
		return encryptDTO(r);
	}

	private CommonResult recordingWechatUserFromParameterizedQrCode(WechatRecordingUserFromParameterizedQrCodeDTO dto) {
		CommonResult r = new CommonResult();
		if (dto == null || StringUtils.isAnyBlank(dto.getOriginOpenId(), dto.getUserOpenId(), dto.getParameter())
				|| "null" == dto.getUserOpenId()) {
			r.setMessage("RecordingWechatUserFromParameterizedQrCode Decrypt error");
			return r;
		}

		String sceneName = dto.getParameter().replaceAll("qrscene_", "");
		String orginOpenId = dto.getOriginOpenId();
		String userOpenId = dto.getUserOpenId();

		WechatUserDetailExample wechatUserExample = new WechatUserDetailExample();
		wechatUserExample.createCriteria().andOpenIdEqualTo(userOpenId);
		List<WechatUserDetail> wechatUserList = wechatUserDetailMapper.selectByExample(wechatUserExample);
		if (!wechatUserList.isEmpty()) {
			r.setCode(WechatSdkCommonResultType.USER_ALREADY_EXISTS.getCode().toString());
			r.setMessage("User exists, userOpenId: " + userOpenId);
			log.error("User exists, userOpenId: " + userOpenId);
			return r;
		}

		WechatOfficialAccountType officialAccountType = null;
		if (wechatOptionService.getOriginOpenId1().equals(orginOpenId)) {
			log.error("New user for sui shou");
			officialAccountType = WechatOfficialAccountType.SUI_SHOU;
		}

		if (officialAccountType == null) {
			r.setMessage("Can NOT find official account detail");
			log.error("Can NOT find official account detail");
			return r;
		}

		WechatQrcodeDetailExample qrCodeExample = new WechatQrcodeDetailExample();
		qrCodeExample.createCriteria().andSourceOfficialAccountEqualTo(officialAccountType.getCode())
				.andSceneNameEqualTo(sceneName);
		List<WechatQrcodeDetail> qrCodeList = qrcodeMapper.selectByExample(qrCodeExample);
		if (qrCodeList.isEmpty()) {
			log.error("Can NOT find QR code, sceneName: " + sceneName);
//			r.setMessage("Can NOT find QR code, sceneName: " + sceneName);
//			return encryptDTO(r);
			// 可能是从原始二维码过来 TODO
		}

		WechatUserDetail newUser = createWechatUserDetailWithOpenIdForSuiShou(userOpenId);
		if (newUser == null) {
			log.error("Get oid failed: " + sceneName + ", open ID: " + userOpenId);
			r.setMessage("Get oid failed: " + sceneName + ", open ID: " + userOpenId);
			return r;
		}

		CreateAiUserResult createAiChatUserResult = aiChatUserService
				.createAiChatUserDetailByWechatOpenId(newUser.getId(), userOpenId);
		if (createAiChatUserResult.isFail()) {
			log.error("Create AI chat user failed: " + createAiChatUserResult.getMessage());
			r.setMessage(createAiChatUserResult.getMessage());
			return r;
		}

		if (!qrCodeList.isEmpty()) {
			WechatQrcodeDetail qrCode = qrCodeList.get(0);
			WechatUserFromQrcode userFromQrCodeRecord = new WechatUserFromQrcode();
			userFromQrCodeRecord.setWechatUserId(newUser.getId());
			userFromQrCodeRecord.setQrcodeId(qrCode.getId());
			userFromQrcodeMapper.insertSelective(userFromQrCodeRecord);

			WechatQrCodeSceneType sceneType = WechatQrCodeSceneType.getType(sceneName);
			if (WechatQrCodeSceneType.FANG_ZHENG_FRANKIE.equals(sceneType)
					|| WechatQrCodeSceneType.FANG_ZHENG_CHANNEL_1.equals(sceneType)
					|| WechatQrCodeSceneType.FANG_ZHENG_CHANNEL_2.equals(sceneType)
					|| WechatQrCodeSceneType.FANG_ZHENG_CHANNEL_3.equals(sceneType)) {
				newUserFromFangZheng(newUser.getId(), 6L);
			}
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public EncryptDTO getUserOpenIdListFromLocal(EncryptDTO encryptedDTO) {
		GetUserOpenIdListResult result = new GetUserOpenIdListResult();

		WechatGetUserOpenIdListDTO dto = decryptEncryptDTO(encryptedDTO, WechatGetUserOpenIdListDTO.class);
		if (dto == null) {
			return encryptDTO(result);
		}

		if (dto.getSize() == null || dto.getSize() > MAX_OPEN_ID_LIST_SIZE) {
			dto.setSize(MAX_OPEN_ID_LIST_SIZE);
		}
		List<String> openIdList = wechatUserDetailMapper.findOpenIdList(dto.getIndex(), dto.getSize());
		if (!openIdList.isEmpty()) {
			String lastOpenId = openIdList.get(openIdList.size() - 1);
			WechatUserDetailExample example = new WechatUserDetailExample();
			example.createCriteria().andOpenIdEqualTo(lastOpenId);
			List<WechatUserDetail> poList = wechatUserDetailMapper.selectByExample(example);
			result.setIndex(poList.get(0).getId());
		}
		result.setOpenIdList(openIdList);
		result.setIsSuccess();
		return encryptDTO(result);
	}

	private void newUserFromFangZheng(Long wechatUserId, Long membershipId) {
		aiChatMembershipService.__giftMembership(wechatUserId, membershipId);
	}

	@Override
	public EncryptDTO generateNewApiKey(EncryptDTO encryptedDTO) {
		GenerateNewApiKeyResult r = new GenerateNewApiKeyResult();
		String tmpKeyStr = decryptEncryptDTO(encryptedDTO, String.class);
		if (tmpKeyStr == null) {
			r.setMessage("Generate API key failed, decrypt error");
			return encryptDTO(r);
		}

		r = aiChatFromApiService.generateNewApiKey(tmpKeyStr);
		return encryptDTO(r);
	}

	@Override
	public EncryptDTO deleteApiKey(EncryptDTO encryptedDTO) {
		CommonResult r = new CommonResult();
		AiServiceDeleteApiKeyDTO dto = decryptEncryptDTO(encryptedDTO, AiServiceDeleteApiKeyDTO.class);
		if (dto == null || StringUtils.isAnyBlank(dto.getTmpKeyStr(), dto.getApiKey())) {
			r.setMessage("Delete API key failed, decrypt error");
			return encryptDTO(r);
		}

		r = aiChatFromApiService.deleteApiKey(dto.getTmpKeyStr(), dto.getApiKey());
		return encryptDTO(r);
	}

	@Override
	public EncryptDTO findAllApiKeysByAiChatUserId(EncryptDTO encryptedDTO) {
		FindAllApiKeysResult r = new FindAllApiKeysResult();
		String tmpKeyStr = decryptEncryptDTO(encryptedDTO, String.class);
		if (tmpKeyStr == null) {
			r.setMessage("Find API key list failed, decrypt error");
			return encryptDTO(r);
		}

		r = aiChatFromApiService.findAllApiKeysByAiChatUserId(tmpKeyStr);
		return encryptDTO(r);
	}

	@Override
	public CommonResult bonusForNewPositiveUserInYesterday() {
		CommonResult r = new CommonResult();
		// 获取缺少 OpenID 的 DTO list
		List<NewPositiveAiUserDTO> aiChatUserDtoList = aiChatUserService
				.__findNewPositiveAiChatUserDtoListInYesterday();
		if (aiChatUserDtoList.isEmpty()) {
			r.setIsSuccess();
			return r;
		}

		// 获取 Wechat user list, 为了获取 OpenID
		List<Long> wechatUserIdList = new ArrayList<>();
		for (NewPositiveAiUserDTO dto : aiChatUserDtoList) {
			wechatUserIdList.add(dto.getWechatUserId());
		}
		WechatUserDetailExample example = new WechatUserDetailExample();
		example.createCriteria().andIdIn(wechatUserIdList);
		List<WechatUserDetail> wechatUserList = wechatUserDetailMapper.selectByExample(example);

		// 组装 Map, Wechat user ID : Wechat user detail
		Map<Long, WechatUserDetail> map = new HashMap<>();
		for (WechatUserDetail wechatUser : wechatUserList) {
			map.put(wechatUser.getId(), wechatUser);
		}

		// 为 DTO list 补充 open ID 属性
		for (NewPositiveAiUserDTO dto : aiChatUserDtoList) {
			dto.setOpenId(map.get(dto.getWechatUserId()).getOpenId());
		}

		for (NewPositiveAiUserDTO dto : aiChatUserDtoList) {
			r = aiChatUserService.recharge(dto.getAiChatUserId(), AiServiceAmountType.BONUS,
					new BigDecimal(aiChatOptionService.getBonusForNewUser()));
			if (r.isSuccess()) {
				WechatSendTemplateMessageBonusRechargeDTO templateMessageDTO = new WechatSendTemplateMessageBonusRechargeDTO();
				templateMessageDTO.setBonusAmountStr(String.valueOf(aiChatOptionService.getBonusForNewUser()));
				templateMessageDTO.setBonusDescription("新用户特惠赠送");
				templateMessageDTO.setManagerCode(wechatOptionService.getManagerCode());
				templateMessageDTO.setReciverOpenId(dto.getOpenId());
				sendBonusRechargeTemplateMessageProducer.send(templateMessageDTO);
			}
		}

		return r;
	}

	@Override
	public GetUserOpenIdListResult getOpenIdListFromWechatSdk() {
		EncryptDTO encryptedResult = postToWechatSdk(wechatOptionService.getManagerCode(),
				WechatSdkUrlConstant.ROOT + WechatSdkUrlConstant.GET_OPEN_ID_LIST_FROM_WECHAT);
		GetUserOpenIdListResult result = decryptEncryptDTO(encryptedResult, GetUserOpenIdListResult.class);
		if (result == null) {
			log.error("Get open ID list from wechat SDK error");
			result = new GetUserOpenIdListResult();
		}
		return result;
	}

	@Override
	public void compareLocalOpenIdListWithWechatOpenIdList() {
		GetUserOpenIdListResult getOpenIdListFromWechatSdkResult = getOpenIdListFromWechatSdk();
		log.error("Get open ID list from wechat SDK result: " + getOpenIdListFromWechatSdkResult.isFail() + ", message"
				+ getOpenIdListFromWechatSdkResult.getMessage());
		if (getOpenIdListFromWechatSdkResult.isFail()) {
			return;
		}
		log.error("Get " + getOpenIdListFromWechatSdkResult.getOpenIdList().size() + " open ID from Wechat");

		List<String> wechatOpenIdList = getOpenIdListFromWechatSdkResult.getOpenIdList();
		if (wechatOpenIdList == null || wechatOpenIdList.isEmpty()) {
			return;
		}
		List<String> localOpenIdList = wechatUserDetailMapper.findOpenIdList(0L, MAX_OPEN_ID_LIST_SIZE);
		log.error("Get " + localOpenIdList.size() + " open ID from local");

		WechatRecordingUserFromParameterizedQrCodeDTO createUserDTO = null;
		for (String openId : wechatOpenIdList) {
			if (!localOpenIdList.contains(openId)) {
				createUserDTO = new WechatRecordingUserFromParameterizedQrCodeDTO();
				createUserDTO.setOriginOpenId(wechatOptionService.getOriginOpenId1());
				createUserDTO.setParameter("_");
				createUserDTO.setUserOpenId(openId);
				log.error("Recording new open ID: " + openId);
				CommonResult subR = recordingWechatUserFromParameterizedQrCode(createUserDTO);
				log.error("Record result: " + subR.isSuccess() + ", message: " + subR.getMessage());
			}
		}
	}

	@Override
	public CommonResult __regOpenIdManual(String openId) {
		WechatRecordingUserFromParameterizedQrCodeDTO dto = new WechatRecordingUserFromParameterizedQrCodeDTO();
		dto.setOriginOpenId(wechatOptionService.getOriginOpenId1());
		dto.setParameter("");
		dto.setUserOpenId(openId);
		return recordingWechatUserFromParameterizedQrCode(dto);
	}
}
