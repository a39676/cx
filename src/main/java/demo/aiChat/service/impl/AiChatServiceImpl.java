package demo.aiChat.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.mapper.AiChatUserAmountHistoryMapper;
import demo.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.aiChat.mapper.AiChatUserChatHistoryMapper;
import demo.aiChat.mapper.AiChatUserDetailMapper;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.po.AiChatUserAmountHistory;
import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.service.AiChatMembershipService;
import demo.aiChat.service.AiChatService;
import demo.thirdPartyAPI.openAI.pojo.dto.OpanAiChatCompletionMessageDTO;
import demo.thirdPartyAPI.openAI.pojo.result.OpenAiChatCompletionSendMessageResult;
import demo.thirdPartyAPI.openAI.pojo.type.OpenAiAmountType;
import demo.thirdPartyAPI.wechat.service.WechatUserService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AiChatServiceImpl extends AiChatCommonService implements AiChatService {

	@Autowired
	private AiChatUserAmountHistoryMapper amountHistoryMapper;
	@Autowired
	private AiChatUserAssociateWechatUidMapper aiChatUserAssociateWechatUidMapper;
	@Autowired
	private AiChatUserChatHistoryMapper chatGptHistoryMapper;
	@Autowired
	private AiChatUserDetailMapper detailMapper;
	@Autowired
	private AiChatMembershipService membershipService;
	@Autowired
	private WechatUserService wechatUserService;
	
	@Autowired
	private FileUtilCustom ioUtil;

	public OpenAiChatCompletionSendMessageResult sendNewChatMessage(Long openAiUserId, String msg) {
		OpenAiChatCompletionSendMessageResult r = new OpenAiChatCompletionSendMessageResult();
		AiChatUserDetail userDetail = detailMapper.selectByPrimaryKey(openAiUserId);

		AiChatUserMembershipDetailSummaryDTO membershipDetail = membershipService
				.findMembershipDetailSummaryByUserId(openAiUserId);
		Integer historyCountingLimit = membershipDetail.getChatHistoryCountLimit();

		// find chat saving limit counting
		// check amount
		// find history
		// cut history with limit
		// send history + new msg
		// wait feedback
		// if fail, send fail response
		// if success, debit amount, refresh history, send feedback
	}

	private CommonResult refreshChatHistory(Long aiChatUserId, OpanAiChatCompletionMessageDTO msg) {
		CommonResult r = new CommonResult();

//		TODO
		String fileName = aiChatUserId + ".txt";
		File mainFolder = new File(optionService.getChatStorePrefixPath() + File.separator + aiChatUserId);
		String finalFilePath = mainFolder + File.separator + fileName;
		
		JSONObject json = JSONObject.fromObject(msg);
		ioUtil.byteToFile(json.toString().getBytes(StandardCharsets.UTF_8), finalFilePath, true);
		
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
