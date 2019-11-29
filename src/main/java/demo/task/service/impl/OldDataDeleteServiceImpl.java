package demo.task.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.image.service.ImageInteractionService;
import demo.task.service.OldDataDeleteService;

@Component
public class OldDataDeleteServiceImpl implements OldDataDeleteService {
	
	@Autowired
	private ImageInteractionService imageInteractionService;

//	@Scheduled(cron="0 */30 * * * ?")   //每30分钟执行一次
//	@Scheduled(cron="40 49 23 * * *") // 每天23:49:40执行
	
	
//	/** 清理无效的错误登录记录. */
//	@Scheduled(cron="0 */63 * * * ?")
//	public void cleanAttempts() {
//		usersMapper.cleanAttempts(new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 15)));
//	}
	
	/*
	 * TODO
	 * delete cloudinaryOldData  about auto test
	 */
}
