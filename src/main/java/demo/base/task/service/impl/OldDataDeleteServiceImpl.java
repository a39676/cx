package demo.base.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.article.article.service.ArticleBurnService;
import demo.base.task.service.CommonTaskService;
import demo.image.service.ImageService;
import demo.interaction.image.service.ImageInteractionService;
import demo.tool.educate.service.EducateOldDataDeleteService;
import demo.tool.ocr.service.OcrService;

@Component
public class OldDataDeleteServiceImpl extends CommonTaskService {

	@Autowired
	private ImageInteractionService imageInteractionService;
	@Autowired
	private ArticleBurnService articleBurnService;
	@Autowired
	private ImageService imgService;
	@Autowired
	private OcrService ocrService;
	@Autowired
	private EducateOldDataDeleteService educateTaskService;

	/** 清理时间过长的, cloudinary上的 自动测试报告图片. */
	@Scheduled(cron = "19 25 03 * * *")
	public void cleanOldAutoTestUploadImageTask() {
		imageInteractionService.cleanOldAutoTestUploadImage();
	}

	/** 清理时间过长的阅后即焚信息. */
	@Scheduled(cron = "36 24 04 * * *")
	public void cleanExpiredArticleBurnTask() {
		articleBurnService.cleanExpiredArticleBurn();
	}

	/** 清理过期的图片 */
	@Scheduled(cron = "24 19 05 * * *")
	public void imageCleanTask() {
		imgService.imageCleanAndDeleteFile();
		ocrService.cleanOldOcrImg();
	}

	/** Delete old exercise file */
	@Scheduled(cron = "24 45 03 * * *")
	public void deleteOldExerciseFileTask() {
		educateTaskService.deleteOldExerciseFile();
	}

}
