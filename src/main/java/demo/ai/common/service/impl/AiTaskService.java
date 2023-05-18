package demo.ai.common.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.ai.aiChat.service.AiChatMembershipService;
import demo.ai.aiChat.service.AiUserService;
import demo.ai.aiChat.service.impl.AiChatCacheService;

@Component
public class AiTaskService {

	@Autowired
	private AiChatMembershipService aiChatMembershipService;
	@Autowired
	private AiChatCacheService aiChatCacheService;
	@Autowired
	private AiUserService aiChatUserService;

	@Scheduled(cron = "20 03 00 * * *")
	public void rechargeDailyBonusByMemberShip() {
		aiChatMembershipService.updateDeleteMarkByExpiredTime();
		aiChatMembershipService.rechargeDailyBonusByMemberShip();
	}

	@Scheduled(cron = "22 58 23 * * *")
	public void clearMembershipMapInAiChatCache() {
		aiChatCacheService.setMembershipCacheMap(new HashMap<>());
	}

	@Scheduled(cron = "21 03 04 * * *")
	public void tidyAiUserExtraDetail() {
		aiChatUserService.tidyAiUserExtraDetail();
	}
}
