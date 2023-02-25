package demo.base.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.article.article.service.ArticleBurnService;
import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.OldDataDeleteTaskType;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.CommonTaskService;
import demo.image.service.ImageService;
import demo.interaction.image.service.ImageInteractionService;
import demo.tool.ocr.service.OcrService;
import demo.toyParts.educate.service.EducateOldDataDeleteService;

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
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.OLD_DATA_DELETE);
		dto.setTaskId(snowFlake.getNextId());

		OldDataDeleteTaskType oldDataDeleteTaskType = OldDataDeleteTaskType.CLEAN_OLD_AUTO_TEST_UPLOAD_IMAGE;
		dto.setTaskSecondCode(oldDataDeleteTaskType.getCode());
		dto.setTaskSecondName(oldDataDeleteTaskType.getName());
	}

	public void cleanOldAutoTestUploadImage() {
		imageInteractionService.cleanOldAutoTestUploadImage();
	}

	/** 清理时间过长的阅后即焚信息. */
	@Scheduled(cron = "36 24 04 * * *")
	public void cleanExpiredArticleBurnTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.OLD_DATA_DELETE);
		dto.setTaskId(snowFlake.getNextId());

		OldDataDeleteTaskType oldDataDeleteTaskType = OldDataDeleteTaskType.CLEAN_EXPIRED_ARTICLE_BURN;
		dto.setTaskSecondCode(oldDataDeleteTaskType.getCode());
		dto.setTaskSecondName(oldDataDeleteTaskType.getName());
	}

	public void cleanExpiredArticleBurn() {
		articleBurnService.cleanExpiredArticleBurn();
	}

	/** 清理过期的图片 */
	@Scheduled(cron = "24 19 05 * * *")
	public void imageCleanTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.OLD_DATA_DELETE);
		dto.setTaskId(snowFlake.getNextId());

		OldDataDeleteTaskType oldDataDeleteTaskType = OldDataDeleteTaskType.IMAGE_CLEAN;
		dto.setTaskSecondCode(oldDataDeleteTaskType.getCode());
		dto.setTaskSecondName(oldDataDeleteTaskType.getName());
	}

	public void imageClean() {
		imgService.imageClean();
		ocrService.cleanOldOcrImg();
	}
	
	/** Delete old exercise file */
	@Scheduled(cron = "24 45 03 * * *")
	public void deleteOldExerciseFileTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.OLD_DATA_DELETE);
		dto.setTaskId(snowFlake.getNextId());

		OldDataDeleteTaskType oldDataDeleteTaskType = OldDataDeleteTaskType.DELETE_OLD_EXERCISE_FILE;
		dto.setTaskSecondCode(oldDataDeleteTaskType.getCode());
		dto.setTaskSecondName(oldDataDeleteTaskType.getName());
	}

	public void deleteOldExerciseFile() {
		educateTaskService.deleteOldExerciseFile();
	}

}
