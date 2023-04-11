package demo.interaction.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.interaction.wechat.service.WechatUserService;

@Component
public class WechatTaskService extends CommonTaskService {

	@Autowired
	private WechatUserService wechatUserService;

	@Scheduled(cron = "23 02 10 * * *")
	public void bonusForNewPositiveUserInYesterday() {
		wechatUserService.bonusForNewPositiveUserInYesterday();
	}
}
