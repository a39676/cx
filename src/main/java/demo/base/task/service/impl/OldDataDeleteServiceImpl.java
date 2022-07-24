package demo.base.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.article.article.service.ArticleBurnService;
import demo.image.service.ImageService;
import demo.interaction.image.service.ImageInteractionService;
import demo.tool.ocr.service.OcrService;
import demo.toyParts.educate.service.EducateTaskService;

@Component
public class OldDataDeleteServiceImpl {

	@Autowired
	private ImageInteractionService imageInteractionService;
	@Autowired
	private ArticleBurnService articleBurnService;
	@Autowired
	private ImageService imgService;
	@Autowired
	private OcrService ocrService;
	@Autowired
	private EducateTaskService educateTaskService;

	/** 清理时间过长的, cloudinary上的 自动测试报告图片. */
	@Scheduled(cron = "19 25 03 * * *")
	public void cleanOldAutoTestUploadImage() {
		imageInteractionService.cleanOldAutoTestUploadImage();
	}

	/** 清理时间过长的阅后即焚信息. */
	@Scheduled(cron = "36 24 04 * * *")
	public void cleanExpiredArticleBurn() {
		articleBurnService.cleanExpiredArticleBurn();
	}

	/** 清理过期的图片 */
	@Scheduled(cron = "24 19 05 * * *")
	public void imageClean() {
		imgService.imageClean();
		ocrService.cleanOldOcrImg();
	}
	
	/** Delete old exercise file */
	@Scheduled(cron = "24 45 03 * * *")
	public void deleteOldExerciseFile() {
		educateTaskService.deleteOldExerciseFile();
	}

}
