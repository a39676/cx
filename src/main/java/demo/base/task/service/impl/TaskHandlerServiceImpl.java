package demo.base.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.type.ArticleTaskType;
import demo.article.article.service.impl.ArticleTaskService;
import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.TaskHandlerService;
import demo.common.service.CommonService;

@Service
public class TaskHandlerServiceImpl extends CommonService implements TaskHandlerService {

	@Autowired
	private TaskOptionService optionService;

	@Autowired
	private ArticleTaskService articleTaskService;

	@Override
	public CommonResult startEvent(SendTaskDTO dto) {
		CommonResult r = verifyTaskDTO(dto);
		if (r.isFail()) {
			return r;
		}

		if (!optionService.getBreakFlag() && existsRuningEvent()) {
			String taskName = optionService.getRunningTaskName();
			r.setMessage("Still running task: " + taskName);
			return r;
		}

		startTask(dto);

		TaskType taskType = TaskType.getType(dto.getTaskFirstCode());
		String secondTaskName = null;

		try {

			if (TaskType.ARTICLE.getCode().equals(dto.getTaskFirstCode())) {
				ArticleTaskType secondTaskType = ArticleTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (ArticleTaskType.DELETE_ARTICLE_BY_VALID_SETTING.getCode().equals(dto.getTaskSecondCode())) {
					articleTaskService.deleteArticleByValidSetting();

				} else if (ArticleTaskType.UPDATE_ARTICLE_HOT_EXPIRED.getCode().equals(dto.getTaskSecondCode())) {
					articleTaskService.updateArticleHotExpired();

				}

			} else if (TaskType.AUTOMATION_TEST.getCode().equals(dto.getTaskFirstCode())) {
//			TODO
			}

		} catch (Exception e) {
			r.setMessage("Task running failed, task type: " + taskType.getName() + ", second task type name: "
					+ secondTaskName + ". error: " + e.getLocalizedMessage());
			return r;
		}

		endTask();

		r.setIsSuccess();
		return r;
	}
	

	private CommonResult verifyTaskDTO(SendTaskDTO dto) {
		CommonResult r = new CommonResult();
		TaskType taskType = TaskType.getType(dto.getTaskFirstCode());
		if (taskType == null) {
			r.setMessage("Can NOT find task type, type code: " + dto.getTaskFirstCode() + ", task name: "
					+ dto.getTaskFirstName());
			return r;
		}

		if (TaskType.ARTICLE.equals(taskType)) {
			ArticleTaskType secondTaskType = ArticleTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				r.setMessage("Can NOT find article task type, type code: " + dto.getTaskSecondCode() + ", task name: "
						+ dto.getTaskSecondName());
				return r;
			}

		} else if (TaskType.AUTOMATION_TEST.equals(taskType)) {
//			TODO
		}

		r.setIsSuccess();
		return r;
	}
	

	private void startTask(SendTaskDTO dto) {
		optionService.setRunningTask(true);
		optionService.setRunningTaskName(dto.getTaskFirstName() + "," + dto.getTaskSecondName());
	}
	

	private void endTask() {
		optionService.setRunningTask(false);
		optionService.setRunningTaskName(null);
	}
	

	@Override
	public boolean existsRuningEvent() {
		return optionService.getRunningTask();
	}
	

	@Override
	public void fixRuningEventStatus() {
		endTask();
	}
	

	@Override
	public boolean setBreakFlag(Integer flag) {
		optionService.setBreakFlag("1".equals(String.valueOf(flag)));
		return optionService.getBreakFlag();
	}

	
	@Override
	public boolean setBreakFlag(boolean flag) {
		optionService.setBreakFlag(flag);
		return optionService.getBreakFlag();
	}
}
