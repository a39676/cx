package demo.aiChat.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ai.aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import ai.aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import ai.aiChat.pojo.result.AiChatSendNewMessageResult;
import ai.aiChat.pojo.result.GetAiChatHistoryResult;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.common.service.impl.AiCommonService;
import demo.aiChat.mapper.AiChatUserChatHistoryMapper;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.po.AiChatUserChatHistory;
import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.service.AiChatMembershipService;
import demo.aiChat.service.AiChatService;
import demo.aiChat.service.AiChatUserService;
import net.sf.json.JSONObject;
import openAi.pojo.dto.OpanAiChatCompletionMessageDTO;
import openAi.pojo.dto.OpanAiChatCompletionResponseDTO;
import openAi.pojo.result.OpenAiChatCompletionSendMessageResult;
import openAi.pojo.type.OpenAiChatCompletionFinishType;
import openAi.pojo.type.OpenAiChatCompletionMessageRoleType;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AiChatServiceImpl extends AiCommonService implements AiChatService {

	@Autowired
	private AiChatUserChatHistoryMapper chatHistoryMapper;
	@Autowired
	private AiChatMembershipService membershipService;
	@Autowired
	private AiChatUserService aiChatUserService;

	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public JSONObject sendNewChatMessageFromApi(Long aiChatUserId, AiChatSendNewMsgFromApiDTO dto) {
		JSONObject r = new JSONObject();
		JSONObject errorMsg = new JSONObject();

		int sensitiveWordCounting = 0;
		for (OpanAiChatCompletionMessageDTO msg : dto.getMessages()) {
			sensitiveWordCounting = sensitiveWordCounting + sensitiveWordCount(msg.getContent());
		}
		if (sensitiveWordCounting > 0) {
			insertSensitiveWordHitCountingToRedis(aiChatUserId, sensitiveWordCounting,
					aiChatOptionService.getSensitiveWordsTriggerInMinutes());

			Integer sensitiveWordHitCount = findSensitiveWordHitCount(aiChatUserId);
			if (sensitiveWordHitCount > aiChatOptionService.getSensitiveWordsTriggerMaxCount()) {
				aiChatUserService.__giveUserWarningMark(aiChatUserId);
				sendTelegramMessage("Detected too many sensitive words aiChatUserId=" + aiChatUserId);
			}
		}

		AiChatUserDetail userDetail = aiChatUserService.__getUserDetail(aiChatUserId);
		if (userDetail == null) {
			errorMsg.put("message", "请输入正确 API key");
			r.put("error", errorMsg);
			return r;
		}
		if (userDetail.getIsBlock()) {
			errorMsg.put("message", "Profile error, 请联系管理员");
			r.put("error", errorMsg);
			return r;
		}
		// check amount
		int totalAmount = userDetail.getBonusAmount().intValue() + userDetail.getRechargeAmount().intValue();
		if (totalAmount <= 0) {
			errorMsg.put("message", "余额不足, 请到个人中心购买充值包 签到, 或留意其他活动");
			r.put("error", errorMsg);
			return r;
		}

		// send new msg, wait feedback
		JSONObject apiResult = util.sendChatCompletionFromApi(dto);

		// if fail, send fail response
		if (apiResult.containsKey("error")) {
			errorMsg.put("message", "运算异常, 可能是使用高峰时间段, 正在排查故障");
			r.put("error", errorMsg);
			sendTelegramMessage("AI chat from API failed: " + apiResult.toString());
			return r;
		}

		OpanAiChatCompletionResponseDTO apiFeedbackDTO = null;
		try {
			apiFeedbackDTO = new Gson().fromJson(apiResult.toString(), OpanAiChatCompletionResponseDTO.class);
		} catch (Exception e) {
			errorMsg.put("message", "运算异常, 正在排查故障");
			r.put("error", errorMsg);
			sendTelegramMessage("AI chat from API result convent to DTO failed: " + apiResult.toString());
			return r;
		}

		// if success, debit amount, send feedback
		Integer totalTokens = apiFeedbackDTO.getUsage().getTotal_tokens();
		aiChatUserService.__debitAmountAndAddTokenUsage(userDetail.getId(), new BigDecimal(totalTokens));

		r = JSONObject.fromObject(apiResult);
		return r;
	}

	@Override
	public AiChatSendNewMessageResult sendNewChatMessageWithHistory(Long aiChatUserId,
			AiChatSendNewMsgFromWechatDTO dto) {
		AiChatSendNewMessageResult r = new AiChatSendNewMessageResult();

		if (StringUtils.isBlank(dto.getMsg())) {
			r.setMessage("请勿发送空白消息");
			return r;
		}

		int sensitiveWordCounting = sensitiveWordCount(dto.getMsg());
		if (sensitiveWordCounting > 0) {
			insertSensitiveWordHitCountingToRedis(aiChatUserId, sensitiveWordCounting,
					aiChatOptionService.getSensitiveWordsTriggerInMinutes());

			Integer sensitiveWordHitCount = findSensitiveWordHitCount(aiChatUserId);
			if (sensitiveWordHitCount > aiChatOptionService.getSensitiveWordsTriggerMaxCount()) {
				aiChatUserService.__giveUserWarningMark(aiChatUserId);
				sendTelegramMessage("Detected too many sensitive words aiChatUserId=" + aiChatUserId);
			}
		}

		// check amount
		AiChatUserDetail userDetail = aiChatUserService.__getUserDetail(aiChatUserId);
		if (userDetail == null) {
			r.setMessage("请刷新页面后登录, 或者输入 API key, 如仍旧异常, 请联系管理员");
			return r;
		}
		if (userDetail.getIsBlock()) {
			r.setCode("-10");
			return r;
		}
		int totalAmount = userDetail.getBonusAmount().intValue() + userDetail.getRechargeAmount().intValue();
		if (totalAmount <= 0) {
			r.setUsage(0);
			OpanAiChatCompletionMessageDTO feedbackMsgDTO = new OpanAiChatCompletionMessageDTO();
			feedbackMsgDTO.setContent("余额不足, 请到个人中心购买充值包 签到, 或留意其他活动");
			feedbackMsgDTO.setRole(OpenAiChatCompletionMessageRoleType.ASSISTANT.getName());
			r.setMsgDTO(feedbackMsgDTO);
			r.setFinishType(OpenAiChatCompletionFinishType.STOP);
			r.setIsSuccess();
			return r;
		}

//		find chat saving limit counting
		AiChatUserMembershipDetailSummaryDTO membershipDetail = membershipService
				.findMembershipDetailSummaryFromCacheByUserIdWithoutRefresh(aiChatUserId);
		Integer historyCountingLimit = membershipDetail.getChatHistoryCountLimit();
		if (dto.getChatModeCounter() < historyCountingLimit) {
			historyCountingLimit = dto.getChatModeCounter();
		}

		// find history and cut history with limit
		List<OpanAiChatCompletionMessageDTO> chatHistory = findChatHistoryByAiChatUserId(aiChatUserId,
				historyCountingLimit);
		OpanAiChatCompletionMessageDTO holdMsgDTO = new OpanAiChatCompletionMessageDTO();

		// Add "act as"
		String actAs = aiChatOptionService.getPromptOfActAs().get(dto.getNameOfActAs());
		if (StringUtils.isNotBlank(actAs)) {
			holdMsgDTO = new OpanAiChatCompletionMessageDTO();
			holdMsgDTO.setRole(OpenAiChatCompletionMessageRoleType.SYSTEM.getName());
			holdMsgDTO.setContent(actAs);
			chatHistory.add(0, holdMsgDTO);
		}

		// send history + new msg, wait feedback
		OpenAiChatCompletionSendMessageResult apiResult = util.sendChatCompletionFromUI(chatHistory, dto.getMsg());

		// if fail, send fail response
		if (apiResult.isFail()) {
			r.setMessage("运算异常, 正在排查故障");
			sendTelegramMessage(apiResult.getMessage());
			return r;
		}

		// if success, debit amount, refresh history, send feedback
		Integer totalTokens = apiResult.getDto().getUsage().getTotal_tokens();
		aiChatUserService.__debitAmountAndAddTokenUsage(userDetail.getId(), new BigDecimal(totalTokens));
		CommonResult refreshHistoryResult = refreshChatHistory(aiChatUserId, dto.getMsg(),
				OpenAiChatCompletionMessageRoleType.USER);
		if (refreshHistoryResult.isFail()) {
			r.setMessage(refreshHistoryResult.getMessage());
		}
		OpanAiChatCompletionMessageDTO feedbackMsgDTO = apiResult.getDto().getChoices().get(0).getMessage();
		OpenAiChatCompletionMessageRoleType feedbackMsgRoleType = OpenAiChatCompletionMessageRoleType
				.getType(feedbackMsgDTO.getRole());
		if (feedbackMsgRoleType == null) {
			feedbackMsgRoleType = OpenAiChatCompletionMessageRoleType.ASSISTANT;
		}
		refreshChatHistory(aiChatUserId, feedbackMsgDTO.getContent(), feedbackMsgRoleType);

		r.setUsage(apiResult.getDto().getUsage().getTotal_tokens());
		r.setMsgDTO(feedbackMsgDTO);
		r.setFinishType(
				OpenAiChatCompletionFinishType.getType(apiResult.getDto().getChoices().get(0).getFinish_reason()));
		r.setIsSuccess();
		return r;
	}

	@Override
	public GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(String aiChatUserPk) {
		if (StringUtils.isBlank(aiChatUserPk)) {
			return new GetAiChatHistoryResult();
		}

		Long aiChatUserId = systemOptionService.decryptPrivateKey(aiChatUserPk);
		if (aiChatUserId == null) {
			return new GetAiChatHistoryResult();
		}

		return findChatHistoryByAiChatUserIdToFrontEnd(aiChatUserId);
	}

	@Override
	public GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(Long aiChatUserId) {
		GetAiChatHistoryResult r = new GetAiChatHistoryResult();
		List<OpanAiChatCompletionMessageDTO> sourceList = findChatHistoryByAiChatUserId(aiChatUserId,
				aiChatOptionService.getChatHistorySaveCountingLimit());
		List<OpanAiChatCompletionMessageDTO> resultList = new ArrayList<>();
		if (sourceList.isEmpty()) {
			OpanAiChatCompletionMessageDTO dto = new OpanAiChatCompletionMessageDTO();
			dto.setContent("你好, 有什么可以帮到您? 请直接向我提问~");
			dto.setRole(OpenAiChatCompletionMessageRoleType.ASSISTANT.getName());
			resultList.add(dto);
			r.setMsgList(resultList);
			r.setIsSuccess();
			return r;
		}

		for (OpanAiChatCompletionMessageDTO msgDTO : sourceList) {
			msgDTO.setContent(sanitize(msgDTO.getContent()));
			msgDTO.setContent(msgDTO.getContent().replaceAll("\\n", "<br>"));
			resultList.add(msgDTO);
		}
		r.setMsgList(resultList);
		r.setIsSuccess();
		return r;
	}

	private List<OpanAiChatCompletionMessageDTO> findChatHistoryByAiChatUserId(Long aiChatUserId,
			Integer historyCountingLimit) {
		List<OpanAiChatCompletionMessageDTO> chatDtoHistory = new ArrayList<>();
		if (historyCountingLimit < 1) {
			return chatDtoHistory;
		}

		AiChatUserChatHistory historyPO = chatHistoryMapper.selectByPrimaryKey(aiChatUserId);
		List<String> msgList = null;
		if (historyPO == null) {
			msgList = new ArrayList<>();
		} else {
			msgList = findChatHistoryLines(historyPO.getHistoryFilePath(), historyCountingLimit);
		}

		OpanAiChatCompletionMessageDTO chatDataDTO = null;
		if (!msgList.isEmpty()) {
			for (String line : msgList) {
				if (StringUtils.isNotBlank(line)) {
					chatDataDTO = new Gson().fromJson(line, OpanAiChatCompletionMessageDTO.class);
					chatDtoHistory.add(chatDataDTO);
				}
			}
		}

		return chatDtoHistory;
	}

	private List<String> findChatHistoryLines(String filePathStr, Integer limit) {
		if (limit == null) {
			limit = aiChatOptionService.getChatHistorySaveCountingLimit();
		}

		List<String> lines = new ArrayList<>();
		File targetFile = new File(filePathStr);
		if (!targetFile.exists()) {
			return lines;
		}

		Path path = Paths.get(filePathStr);

		try {
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String currentLine = null;
			while (StringUtils.isNotBlank(currentLine = reader.readLine())) {
				lines.add(currentLine);
				if (lines.size() > limit) {
					lines.remove(0);
				}
			}
		} catch (IOException ex) {
		}
		return lines;
	}

	private CommonResult refreshChatHistory(Long aiChatUserId, String msg,
			OpenAiChatCompletionMessageRoleType roleType) {
		CommonResult r = new CommonResult();

		String finalFilePath = null;
		File targetFile = null;
		AiChatUserChatHistory historyPO = chatHistoryMapper.selectByPrimaryKey(aiChatUserId);
		if (historyPO == null) {

			String fileName = aiChatUserId + ".txt";
			File mainFolder = new File(aiChatOptionService.getChatStorePrefixPath() + File.separator + aiChatUserId);
			finalFilePath = mainFolder + File.separator + fileName;

			targetFile = new File(finalFilePath);
			File paraentFolder = targetFile.getParentFile();

			if (!paraentFolder.exists()) {
				if (!paraentFolder.mkdirs()) {
					r.setMessage("对话记录存储异常, 本次对话可能没有正确存档 01");
					return r;
				}
			}

			historyPO = new AiChatUserChatHistory();
			historyPO.setHistoryFilePath(finalFilePath);
			historyPO.setUserId(aiChatUserId);
			chatHistoryMapper.insertSelective(historyPO);
		} else {
			finalFilePath = historyPO.getHistoryFilePath();
			targetFile = new File(finalFilePath);
		}

		Path path = Paths.get(finalFilePath);

		OpanAiChatCompletionMessageDTO msgDTO = new OpanAiChatCompletionMessageDTO();
		msgDTO.setContent(msg);
		msgDTO.setRole(roleType.getName());
		JSONObject newMsgJson = JSONObject.fromObject(msgDTO);
		newMsgJson.put("createTime", localDateTimeHandler.dateToStr(LocalDateTime.now()));
		Integer chatHistorySaveCountingLimit = aiChatOptionService.getChatHistorySaveCountingLimit();

		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				r.setMessage("对话记录存储异常, 本次对话可能没有正确存档 02");
				return r;
			}
		}

		List<String> lines = new ArrayList<>();

		try {
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null) {
				lines.add(currentLine);
			}
		} catch (IOException ex) {
			r.setMessage("对话记录存储异常, 本次对话可能没有正确存档 03");
			return r;
		}

		if (lines.size() > chatHistorySaveCountingLimit) {
			int gap = lines.size() - chatHistorySaveCountingLimit;
			while (gap > 0) {
				lines.remove(0);
				gap--;
			}
		}
		lines.add(newMsgJson.toString());

		StringBuffer sb = new StringBuffer();
		for (String line : lines) {
			sb.append(line + System.lineSeparator());
		}
		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		r.setIsSuccess();
		return r;
	}

	private int sensitiveWordCount(String msg) {
		int count = 0;
		for (String word : aiChatOptionService.getSensitiveWords()) {
			if (msg.contains(word)) {
				count += 1;
			}
		}
		return count;
	}
}
