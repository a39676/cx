package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.image.service.ImageInteractionService;
import demo.task.service.OldDataDeleteService;

@Component
public class OldDataDeleteServiceImpl implements OldDataDeleteService {
	
	@Autowired
	private ImageInteractionService imageInteractionService;
	
	/** 清理时间过长的, cloudinary上的 自动测试报告图片. */
	@Scheduled(cron="19 25 03 * * *")
	public void cleanAttempts() {
		imageInteractionService.cleanOldAutoTestUploadImage();
	}
	
}
