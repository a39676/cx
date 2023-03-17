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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.mapper.AiChatUserAmountHistoryMapper;
import demo.aiChat.mapper.AiChatUserChatHistoryMapper;
import demo.aiChat.mapper.AiChatUserDetailMapper;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.po.AiChatUserAmountHistory;
import demo.aiChat.pojo.po.AiChatUserChatHistory;
import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.pojo.result.AiChatSendNewMessageResult;
import demo.aiChat.pojo.result.GetAiChatHistoryResult;
import demo.aiChat.service.AiChatMembershipService;
import demo.aiChat.service.AiChatService;
import demo.thirdPartyAPI.openAI.pojo.dto.OpanAiChatCompletionMessageDTO;
import demo.thirdPartyAPI.openAI.pojo.result.OpenAiChatCompletionSendMessageResult;
import demo.thirdPartyAPI.openAI.pojo.type.OpenAiAmountType;
import demo.thirdPartyAPI.openAI.pojo.type.OpenAiChatCompletionFinishType;
import demo.thirdPartyAPI.openAI.pojo.type.OpenAiChatCompletionMessageRoleType;
import net.sf.json.JSONObject;
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
	private FileUtilCustom ioUtil;

	@Override
	public AiChatSendNewMessageResult sendNewChatMessage(Long aiChatUserId, String msg) {
		AiChatSendNewMessageResult r = new AiChatSendNewMessageResult();

		// check amount
		AiChatUserDetail userDetail = detailMapper.selectByPrimaryKey(aiChatUserId);
		if (userDetail == null) {
			r.setMessage("请刷新页面后登录, 或者输入 API key, 如仍旧异常, 请联系管理员");
			return r;
		}
		if (userDetail.getBonusAmount().doubleValue() < 0 && userDetail.getRechargeAmount().doubleValue() < 0) {
			r.setMessage(notEnoughtAmount().getMessage());
			return r;
		}

//		find chat saving limit counting
		AiChatUserMembershipDetailSummaryDTO membershipDetail = membershipService
				.findMembershipDetailSummaryByUserId(aiChatUserId);
		Integer historyCountingLimit = membershipDetail.getChatHistoryCountLimit();

		// find history and cut history with limit
		List<OpanAiChatCompletionMessageDTO> chatHistory = findChatHistoryByAiChatUserId(aiChatUserId,
				historyCountingLimit);

		// send history + new msg, wait feedback
		msg = sanitize(msg);
		OpenAiChatCompletionSendMessageResult apiResult = util.sendChatCompletion(chatHistory, msg);

		// if fail, send fail response
		if (apiResult.isFail()) {
			r.setMessage("运算异常, 正在排查故障");
			sendTelegramMessage(apiResult.getMessage());
			return r;
		}

		// if success, debit amount, refresh history, send feedback
		Integer totalTokens = apiResult.getDto().getUsage().getTotal_tokens();
		debitAmount(userDetail, new BigDecimal(totalTokens));
		refreshChatHistory(aiChatUserId, msg);

		OpanAiChatCompletionMessageDTO feedbackMsgDTO = apiResult.getDto().getChoices().get(0).getMessage();

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
		r.setMsgList(list);
		r.setIsSuccess();
		return r;
	}

	private List<OpanAiChatCompletionMessageDTO> findChatHistoryByAiChatUserId(Long aiChatUserId,
			Integer historyCountingLimit) {
		AiChatUserChatHistory historyPO = chatHistoryMapper.selectByPrimaryKey(aiChatUserId);
		List<String> lines = null;
		if (historyPO == null) {
			lines = new ArrayList<>();
		} else {
			lines = findChatHistoryLines(historyPO.getHistoryFilePath(), historyCountingLimit);
		}

		List<OpanAiChatCompletionMessageDTO> chatHistory = new ArrayList<>();
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

	private CommonResult refreshChatHistory(Long aiChatUserId, String msg) {
		CommonResult r = new CommonResult();

		String fileName = aiChatUserId + ".txt";
		File mainFolder = new File(optionService.getChatStorePrefixPath() + File.separator + aiChatUserId);
		String finalFilePath = mainFolder + File.separator + fileName;

		OpanAiChatCompletionMessageDTO msgDTO = new OpanAiChatCompletionMessageDTO();
		msgDTO.setContent(msg);
		msgDTO.setRole(OpenAiChatCompletionMessageRoleType.USER.getName());
		JSONObject newMsgJson = JSONObject.fromObject(msgDTO);
		Integer chatHistorySaveCountingLimit = optionService.getChatHistorySaveCountingLimit();

		Path path = Paths.get(finalFilePath);

		File targetFile = new File(finalFilePath);
		File paraentFolder = targetFile.getParentFile();

		if (!paraentFolder.exists()) {
			if (!paraentFolder.mkdirs()) {
				r.setMessage("对话记录存储异常, 本次对话可能没有正确存档 01");
				return r;
			}
		}

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

	private CommonResult debitAmount(AiChatUserDetail detail, BigDecimal debitAmount) {
		CommonResult r = new CommonResult();
		if (debitAmount.compareTo(BigDecimal.ZERO) < 1) {
			r.setMessage("输入消耗额异常");
			return r;
		}

		BigDecimal totalAmount = detail.getBonusAmount().add(detail.getRechargeAmount());
		if (totalAmount.compareTo(debitAmount) < 0) {
			return notEnoughtAmount();
		}

		AiChatUserAmountHistory newAmountHistory = null;
		BigDecimal restDebitAmount = new BigDecimal(debitAmount.doubleValue());

		if (detail.getBonusAmount().compareTo(BigDecimal.ZERO) > 0) {
			if (detail.getBonusAmount().compareTo(debitAmount) > -1) {
				restDebitAmount = debitAmount.subtract(detail.getBonusAmount());

				detail.setBonusAmount(detail.getBonusAmount().subtract(debitAmount));
				detailMapper.updateByPrimaryKeySelective(detail);

				newAmountHistory = new AiChatUserAmountHistory();
				newAmountHistory.setId(snowFlake.getNextId());
				newAmountHistory.setAmountType(OpenAiAmountType.BONUS.getCode());
				newAmountHistory.setAmountChange(debitAmount);
				newAmountHistory.setUserId(detail.getId());
				amountHistoryMapper.insertSelective(newAmountHistory);
			}
		}

		if (restDebitAmount.doubleValue() <= 0) {
			r.setIsSuccess();
			return r;
		}

		detail.setRechargeAmount(detail.getRechargeAmount().subtract(restDebitAmount));
		detailMapper.updateByPrimaryKeySelective(detail);

		newAmountHistory = new AiChatUserAmountHistory();
		newAmountHistory.setId(snowFlake.getNextId());
		newAmountHistory.setAmountType(OpenAiAmountType.RECHARGE.getCode());
		newAmountHistory.setAmountChange(debitAmount.subtract(detail.getBonusAmount()));
		newAmountHistory.setUserId(detail.getId());
		amountHistoryMapper.insertSelective(newAmountHistory);
		r.setIsSuccess();
		return r;
	}

}
