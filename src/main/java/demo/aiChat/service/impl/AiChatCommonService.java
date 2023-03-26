package demo.aiChat.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.base.system.service.impl.RedisSetConnectService;
import demo.common.service.ToolCommonService;
import demo.thirdPartyAPI.openAI.service.impl.OpenAiUtil;
import demo.tool.other.service.TextFilter;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

public abstract class AiChatCommonService extends ToolCommonService {

	@Autowired
	protected OpenAiUtil util;
	@Autowired
	protected AiChatOptionService optionService;
	@Autowired
	protected AiChatCacheService cacheService;
	@Autowired
	private TelegramService telegramService;
	@Autowired
	private RedisOriginalConnectService redisConnectService;
	@Autowired
	private RedisSetConnectService redisSetService;
	@Autowired
	private TextFilter textFilter;

	private String dailySignUpRedisKey = "aiChatDailySignUp";

	protected CommonResult notEnoughtAmount() {
		CommonResult r = new CommonResult();
		r.setMessage("电量不足, 请充电或留意签到活动");
		return r;
	}

	protected CommonResult serviceError() {
		CommonResult r = new CommonResult();
		r.setMessage("服务器正在拼命运算, 请稍后再试");
		return r;
	}

	protected void sendTelegramMessage(String msg) {
		telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, msg, TelegramStaticChatID.MY_ID);
	}

	protected void tmpKeyInsertOrUpdateLiveTime(Long tmpKey, Long aiChatUserId) {
		redisConnectService.setValByName(String.valueOf(tmpKey), String.valueOf(aiChatUserId), 10, TimeUnit.MINUTES);
	}

	protected void addAiChatUserIdDailySigned(Long aiChatUserId) {
		redisSetService.add(dailySignUpRedisKey, String.valueOf(aiChatUserId), LocalDateTime.now().with(LocalTime.MAX));
	}

	protected boolean hadDailySignUp(Long aiChatUserId) {
		return redisSetService.isMember(dailySignUpRedisKey, String.valueOf(aiChatUserId));
	}

	protected Long getAiChatUserIdByTempKey(String tmpKeyStr) {
		Long tmpKey = null;
		try {
			tmpKey = Long.parseLong(tmpKeyStr);
		} catch (Exception e) {
			return null;
		}

		return getAiChatUserIdByTempKey(tmpKey);
	}

	protected Long getAiChatUserIdByTempKey(Long tmpKey) {
		String aiChatUserIdStr = redisConnectService.getValByName(String.valueOf(tmpKey));
		try {
			return Long.parseLong(aiChatUserIdStr);
		} catch (Exception e) {
			return null;
		}
	}

	protected String sanitize(String content) {
		PolicyFactory filter = textFilter.getArticleFilter();
		return filter.sanitize(content);
	}

}
