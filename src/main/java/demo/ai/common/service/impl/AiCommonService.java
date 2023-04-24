package demo.ai.common.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiChat.mapper.AiChatApiKeyMapper;
import demo.ai.aiChat.pojo.po.AiChatApiKey;
import demo.ai.aiChat.service.impl.AiChatCacheService;
import demo.ai.aiChat.service.impl.AiChatOptionService;
import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.base.system.service.impl.RedisSetConnectService;
import demo.common.service.ToolCommonService;
import demo.interaction.wechat.service.WechatSdkForInterService;
import demo.thirdPartyAPI.openAI.service.impl.OpenAiUtil;
import demo.tool.other.service.TextFilter;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

public abstract class AiCommonService extends ToolCommonService {

	@Autowired
	protected OpenAiUtil util;
	@Autowired
	protected AiChatOptionService aiChatOptionService;
	@Autowired
	protected AiChatCacheService aiChatCacheService;
	@Autowired
	protected WechatSdkForInterService wechatSdkForInterService;
	
	@Autowired
	private TelegramService telegramService;
	@Autowired
	private RedisOriginalConnectService redisConnectService;
	@Autowired
	private RedisSetConnectService redisSetService;
	@SuppressWarnings("unused")
	@Autowired
	private TextFilter textFilter;

	private final String dailySignUpRedisKey = "aiChatDailySignUp";
	private final String sensitiveWordHitCountingRedisKeyPrefix = "senWordHitCount_";

	@Autowired
	protected AiChatApiKeyMapper apiKeyMapper;

	protected CommonResult notEnoughtAmount() {
		CommonResult r = new CommonResult();
		r.setMessage("余额不足, 请到\"个人中心\"充值或留意签到活动");
		return r;
	}

	protected CommonResult serviceError() {
		CommonResult r = new CommonResult();
		r.setMessage("服务器正在拼命运算, 请稍后再试");
		return r;
	}

	protected void sendTelegramMessage(String msg) {
		telegramService.sendMessageByChatRecordId(TelegramBotType.AI_CHAT_NOTICE, msg, TelegramStaticChatID.MY_ID);
	}

	protected void tmpKeyInsertOrUpdateLiveTime(Long tmpKey, Long aiChatUserId) {
		redisConnectService.setValByName(String.valueOf(tmpKey), String.valueOf(aiChatUserId), 10, TimeUnit.MINUTES);
	}

	protected void extendTmpKeyValidity(Long tmpKey) {
		log.error("Receive tmp key extend request, tmp key: " + tmpKey);
		if (tmpKey == null) {
			return;
		}
		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKey);
		if (aiChatUserId != null) {
			log.error("Extended, tmp key: " + tmpKey + ", ai chat user id: " + aiChatUserId);
			tmpKeyInsertOrUpdateLiveTime(tmpKey, aiChatUserId);
		}
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
//		PolicyFactory filter = textFilter.getArticleFilter();
//		return filter.sanitize(content);
//		return Jsoup.parse(content).text();
		return content.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	protected void insertSensitiveWordHitCountingToRedis(Long aiChatUserId, Integer hitCount, Integer livingMinutes) {
		String key = sensitiveWordHitCountingRedisKeyPrefix + aiChatUserId + "_" + snowFlake.getNextId();
		redisConnectService.setValByName(key, String.valueOf(hitCount), livingMinutes.longValue(), TimeUnit.MINUTES);
	}

	protected Integer findSensitiveWordHitCount(Long aiChatUserId) {
		Set<String> keys = redisConnectService.findKeys(sensitiveWordHitCountingRedisKeyPrefix + aiChatUserId + "_*");
		if (keys.isEmpty()) {
			return 0;
		}
		int total = 0;
		String countStr = null;
		Integer tmpCount = null;
		for (String key : keys) {
			countStr = redisConnectService.getValByName(key);
			try {
				tmpCount = Integer.parseInt(countStr);
				total = total + tmpCount;
			} catch (Exception e) {
			}
		}
		return total;
	}

	protected Long getAiUserIdByApiKey(String apiKey) {
		Long aiUserId = aiChatCacheService.getApiKeyCacheMap().get(apiKey);
		if (aiUserId != null) {
			return aiUserId;
		}

		AiChatApiKey po = null;
		Long apiKeyDecrypt = systemOptionService.decryptPrivateKey(apiKey);

		if (apiKeyDecrypt == null) {
			return null;
		}

		po = apiKeyMapper.selectByPrimaryKey(apiKeyDecrypt);
		if (po == null || po.getIsDelete()) {
			return null;
		}
		aiUserId = po.getAiChatUserId();
		aiChatCacheService.getApiKeyCacheMap().put(apiKey, aiUserId);

		return aiUserId;
	}
}
