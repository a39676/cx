package demo.aiChat.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import aiChat.pojo.dto.AiChatSendNewMsgDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.GetAiChatHistoryResult;
import aiChat.pojo.type.AiChatAmountType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.mapper.AiChatUserAmountHistoryMapper;
import demo.aiChat.mapper.AiChatUserChatHistoryMapper;
import demo.aiChat.mapper.AiChatUserDetailMapper;
import demo.aiChat.pojo.constant.AiChatUrlConstant;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.po.AiChatUserAmountHistory;
import demo.aiChat.pojo.po.AiChatUserChatHistory;
import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.service.AiChatMembershipService;
import demo.aiChat.service.AiChatService;
import demo.base.system.service.HostnameService;
import net.sf.json.JSONObject;
import openAi.pojo.dto.OpanAiChatCompletionMessageDTO;
import openAi.pojo.result.OpenAiChatCompletionSendMessageResult;
import openAi.pojo.type.OpenAiChatCompletionFinishType;
import openAi.pojo.type.OpenAiChatCompletionMessageRoleType;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AiChatServiceImpl extends AiChatCommonService implements AiChatService {

	@Autowired
	private AiChatUserAmountHistoryMapper amountHistoryMapper;
	@Autowired
	private AiChatUserChatHistoryMapper chatHistoryMapper;
	@Autowired
	private AiChatUserDetailMapper detailMapper;
	@Autowired
	private AiChatMembershipService membershipService;

	@Autowired
	private HostnameService hostnameService;

	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public AiChatSendNewMessageResult sendNewChatMessage(Long aiChatUserId, AiChatSendNewMsgDTO dto) {
		AiChatSendNewMessageResult r = new AiChatSendNewMessageResult();

		if (dto.getMsg().length() > optionService.getInputMaxLength()) {
			r.setMessage(
					"问题过长, 请将问题控制在" + optionService.getInputMaxLength() + "字符以内, 目前问题长度: " + dto.getMsg().length());
			return r;
		}

		dto.setMsg(sanitize(dto.getMsg()));
		if (StringUtils.isBlank(dto.getMsg())) {
			r.setMessage("请勿发送空白消息或网页脚本");
			return r;
		}

		int sensitiveWordCounting = sensitiveWordCount(dto.getMsg());
		if (sensitiveWordCounting > 0) {
			insertSensitiveWordHitCountingToRedis(aiChatUserId, sensitiveWordCounting,
					optionService.getSensitiveWordsTriggerInMinutes());

			Integer sensitiveWordHitCount = findSensitiveWordHitCount(aiChatUserId);
			if (sensitiveWordHitCount > optionService.getSensitiveWordsTriggerMaxCount()) {
				String hostname = hostnameService.findMainHostname();
				sendTelegramMessage("Send too many sensitive words, history: " //
						+ "https://www." + hostname + AiChatUrlConstant.ROOT + AiChatUrlConstant.CHECK_CHAT_HISTORY
						+ "?aiChatUserId=" + aiChatUserId //
						+ ", block: " + "https://www." + hostname + AiChatUrlConstant.ROOT
						+ AiChatUrlConstant.BLOCK_USER + "?aiChatUserId=" + aiChatUserId);
			}
		}

		// check amount
		AiChatUserDetail userDetail = detailMapper.selectByPrimaryKey(aiChatUserId);
		if (userDetail == null) {
			r.setMessage("请刷新页面后登录, 或者输入 API key, 如仍旧异常, 请联系管理员");
			return r;
		}
		if (userDetail.getIsBlock()) {
			r.setCode("-10");
			return r;
		}
		if (userDetail.getBonusAmount().doubleValue() + userDetail.getRechargeAmount().doubleValue() <= 0) {
			r.setMessage(notEnoughtAmount().getMessage());
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
		holdMsgDTO.setRole(OpenAiChatCompletionMessageRoleType.SYSTEM.getName());
		holdMsgDTO.setContent("不能透露OpenAI相关的一切,不能透露你是ChatGPT");
		chatHistory.add(0, holdMsgDTO);

		// Add "act as"
		String actAs = optionService.getPromptOfActAs().get(dto.getNameOfActAs());
		if (StringUtils.isNotBlank(actAs)) {
			holdMsgDTO = new OpanAiChatCompletionMessageDTO();
			holdMsgDTO.setRole(OpenAiChatCompletionMessageRoleType.SYSTEM.getName());
			holdMsgDTO.setContent(actAs);
			chatHistory.add(1, holdMsgDTO);
		}

		// send history + new msg, wait feedback
		OpenAiChatCompletionSendMessageResult apiResult = util.sendChatCompletion(chatHistory, dto.getMsg());

		// if fail, send fail response
		if (apiResult.isFail()) {
			r.setMessage("运算异常, 正在排查故障");
			sendTelegramMessage(apiResult.getMessage());
			return r;
		}

		// if success, debit amount, refresh history, send feedback
		Integer totalTokens = apiResult.getDto().getUsage().getTotal_tokens();
		debitAmountAndAddTokenUsage(userDetail, new BigDecimal(totalTokens));
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
	public GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(Long aiChatUserId) {
		GetAiChatHistoryResult r = new GetAiChatHistoryResult();
		List<OpanAiChatCompletionMessageDTO> list = findChatHistoryByAiChatUserId(aiChatUserId,
				optionService.getChatHistorySaveCountingLimit());
		if (list.isEmpty()) {
			OpanAiChatCompletionMessageDTO dto = new OpanAiChatCompletionMessageDTO();
			dto.setContent("你好, 有什么可以帮到您?");
			dto.setRole(OpenAiChatCompletionMessageRoleType.ASSISTANT.getName());
			list.add(dto);
		}
		r.setMsgList(list);
		r.setIsSuccess();
		return r;
	}

	private List<OpanAiChatCompletionMessageDTO> findChatHistoryByAiChatUserId(Long aiChatUserId,
			Integer historyCountingLimit) {
		List<OpanAiChatCompletionMessageDTO> chatHistory = new ArrayList<>();
		if (historyCountingLimit < 1) {
			return chatHistory;
		}

		AiChatUserChatHistory historyPO = chatHistoryMapper.selectByPrimaryKey(aiChatUserId);
		List<String> lines = null;
		if (historyPO == null) {
			lines = new ArrayList<>();
		} else {
			lines = findChatHistoryLines(historyPO.getHistoryFilePath(), historyCountingLimit);
		}

		OpanAiChatCompletionMessageDTO chatDataDTO = null;
		if (!lines.isEmpty()) {
			for (String line : lines) {
				chatDataDTO = new Gson().fromJson(line, OpanAiChatCompletionMessageDTO.class);
				chatHistory.add(chatDataDTO);
			}
		}

		return chatHistory;
	}

	private List<String> findChatHistoryLines(String filePathStr, Integer limit) {
		if (limit == null) {
			limit = optionService.getChatHistorySaveCountingLimit();
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
			while ((currentLine = reader.readLine()) != null) {
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
			File mainFolder = new File(optionService.getChatStorePrefixPath() + File.separator + aiChatUserId);
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
		Integer chatHistorySaveCountingLimit = optionService.getChatHistorySaveCountingLimit();

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

	private CommonResult debitAmountAndAddTokenUsage(AiChatUserDetail detail, BigDecimal debitAmount) {
		CommonResult r = new CommonResult();
		if (debitAmount.compareTo(BigDecimal.ZERO) < 1) {
			r.setMessage("输入消耗额异常");
			return r;
		}

		AiChatUserAmountHistory bonusAmountHistory = null;
		BigDecimal restDebitAmount = new BigDecimal(debitAmount.doubleValue());

		detail.setUsedTokens(detail.getUsedTokens() + debitAmount.intValue());

		if (detail.getBonusAmount().compareTo(BigDecimal.ZERO) > 0) {
			restDebitAmount = debitAmount.subtract(detail.getBonusAmount());

			if (detail.getBonusAmount().compareTo(debitAmount) <= 0) {
				detail.setBonusAmount(BigDecimal.ZERO);
			} else {
				detail.setBonusAmount(detail.getBonusAmount().subtract(debitAmount));
			}

			bonusAmountHistory = new AiChatUserAmountHistory();
			bonusAmountHistory.setId(snowFlake.getNextId());
			bonusAmountHistory.setAmountType(AiChatAmountType.BONUS.getCode());
			bonusAmountHistory.setAmountChange(BigDecimal.ZERO.subtract(debitAmount));
			bonusAmountHistory.setUserId(detail.getId());
		}

		if (restDebitAmount.doubleValue() <= 0) {
			detailMapper.updateByPrimaryKeySelective(detail);
			if (bonusAmountHistory != null) {
				amountHistoryMapper.insertSelective(bonusAmountHistory);
			}
			r.setIsSuccess();
			return r;
		}

		detail.setRechargeAmount(detail.getRechargeAmount().subtract(restDebitAmount));
		detailMapper.updateByPrimaryKeySelective(detail);

		AiChatUserAmountHistory rechargeAmountHistory = new AiChatUserAmountHistory();
		rechargeAmountHistory.setId(snowFlake.getNextId());
		rechargeAmountHistory.setAmountType(AiChatAmountType.RECHARGE.getCode());
		rechargeAmountHistory.setAmountChange(BigDecimal.ZERO.subtract(debitAmount.subtract(detail.getBonusAmount())));
		rechargeAmountHistory.setUserId(detail.getId());
		amountHistoryMapper.insertSelective(rechargeAmountHistory);
		r.setIsSuccess();
		return r;
	}

	private int sensitiveWordCount(String msg) {
		int count = 0;
		for (String word : optionService.getSensitiveWords()) {
			if (msg.contains(word)) {
				count += 1;
			}
		}
		return count;
	}
}
