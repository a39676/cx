package demo.aiArt.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import demo.aiArt.service.impl.AiArtOptionService;
import demo.aiChat.service.impl.AiChatCommonService;
import demo.base.system.service.impl.RedisOriginalConnectService;

public abstract class AiArtCommonService extends AiChatCommonService {

	@Autowired
	protected AiArtOptionService aiArtOptionService;

	@Autowired
	private RedisOriginalConnectService redisConnectService;

	protected void addJobCounting(Long aiUserId) {
		Integer count = getJobCounting(aiUserId);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayMax = now.with(LocalTime.MAX);
		long minutes = ChronoUnit.MINUTES.between(now, todayMax);
		redisConnectService.setValByName(String.valueOf(aiUserId), String.valueOf(count + 1), minutes,
				TimeUnit.MINUTES);
	}

	protected Integer getJobCounting(Long aiUserId) {
		String countStr = redisConnectService.getValByName(String.valueOf(aiUserId));
		Integer count = 0;
		try {
			count = Integer.parseInt(countStr);
		} catch (Exception e) {
		}
		return count;
	}
}
