package demo.base.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.type.ArticleTaskType;
import demo.article.article.service.impl.ArticleTaskService;
import demo.base.system.service.impl.RedisHashConnectService;
import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
import demo.common.service.CommonService;

@Service
public class TaskHandlerServiceImpl extends CommonService {

	@Autowired
	private RedisHashConnectService redisHashConnectService;

	private final String RUNNING_TASK_REDIS_KEY = "runningTaskKey";
	private final String RUNNING_TASK_REDIS_TASK_NAME_KEY = "runningTaskNameKey";
	private final String RUNNING_TASK_REDIS_TASK_STATUS_KEY = "runningTaskStatusKey";

	private final Integer RUNNING_TASK_STATU = 1;
	private final Integer NOT_RUNNING_TASK_STATU = 0;

	@Autowired
	private ArticleTaskService articleTaskService;

	public CommonResult startEvent(SendTaskDTO dto) {
		CommonResult r = verifyTaskDTO(dto);
		if (r.isFail()) {
			return r;
		}

		if (existsRuningEvent()) {
			String taskName = redisHashConnectService.getValByName(RUNNING_TASK_REDIS_KEY,
					RUNNING_TASK_REDIS_TASK_NAME_KEY);
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
		redisHashConnectService.setValByName(RUNNING_TASK_REDIS_KEY, RUNNING_TASK_REDIS_TASK_NAME_KEY,
				dto.getTaskFirstName() + dto.getTaskSecondName());
		redisHashConnectService.setValByName(RUNNING_TASK_REDIS_KEY, RUNNING_TASK_REDIS_TASK_STATUS_KEY,
				RUNNING_TASK_STATU.toString());
	}

	private void endTask() {
		redisHashConnectService.setValByName(RUNNING_TASK_REDIS_KEY, RUNNING_TASK_REDIS_TASK_STATUS_KEY,
				NOT_RUNNING_TASK_STATU.toString());
	}

	public boolean existsRuningEvent() {
		String val = redisHashConnectService.getValByName(RUNNING_TASK_REDIS_KEY, RUNNING_TASK_REDIS_TASK_STATUS_KEY);
		return String.valueOf(RUNNING_TASK_STATU).equals(val);
	}

	public void fixRuningEventStatus() {
		redisHashConnectService.deleteValByName(RUNNING_TASK_REDIS_KEY);
	}
}
