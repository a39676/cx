package demo.base.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.base.task.pojo.constant.TaskUrl;
import demo.base.task.service.TaskHandlerService;

@Controller
@RequestMapping(value = TaskUrl.ROOT)
public class TaskController {

	@Autowired
	private TaskHandlerService taskHandlerService;
	
	@GetMapping(value = TaskUrl.FIX_RUNNING_TASK_STATUS)
	@ResponseBody
	public String fixRuningEventStatus() {
		taskHandlerService.fixRuningTaskStatus();
		return "done";
	}
	
	@GetMapping(value = TaskUrl.GET_RUNNING_TASK_NAME)
	@ResponseBody
	public String getRunningTaskName() {
		return taskHandlerService.getRunningTaskName();
	}
}
