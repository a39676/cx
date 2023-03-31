package demo.aiChat.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.aiChat.service.AiChatMembershipService;

@Component
public class AiChatTaskService {

	@Autowired
	private AiChatMembershipService aiChatMembershipService;
	@Autowired
	private AiChatCacheService aiChatCacheService;
	
	@Scheduled(cron = "20 03 00 * * *")
	public void rechargeDailyBonusByMemberShip() {
		aiChatMembershipService.updateDeleteMarkByExpiredTime();
		aiChatMembershipService.rechargeDailyBonusByMemberShip();
	}
	
	@Scheduled(cron = "22 58 23 * * *")
	public void clearMembershipMapInAiChatCache() {
		aiChatCacheService.setMembershipCacheMap(new HashMap<>());
	}
}
