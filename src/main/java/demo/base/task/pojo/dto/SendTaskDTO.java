package demo.base.task.pojo.dto;

public class SendTaskDTO {

	private Long taskId;

	/** {@link TaskType} */
	private String taskFirstName;
	/** {@link TaskType} */
	private Integer taskFirstCode;

	private String taskSecondName;
	private Integer taskSecondCode;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskFirstName() {
		return taskFirstName;
	}

	public void setTaskFirstName(String taskFirstName) {
		this.taskFirstName = taskFirstName;
	}

	public Integer getTaskFirstCode() {
		return taskFirstCode;
	}

	public void setTaskFirstCode(Integer taskFirstCode) {
		this.taskFirstCode = taskFirstCode;
	}

	public String getTaskSecondName() {
		return taskSecondName;
	}

	public void setTaskSecondName(String taskSecondName) {
		this.taskSecondName = taskSecondName;
	}

	public Integer getTaskSecondCode() {
		return taskSecondCode;
	}

	public void setTaskSecondCode(Integer taskSecondCode) {
		this.taskSecondCode = taskSecondCode;
	}

	@Override
	public String toString() {
		return "SendTaskDTO [taskId=" + taskId + ", taskFirstName=" + taskFirstName + ", taskFirstCode=" + taskFirstCode
				+ ", taskSecondName=" + taskSecondName + ", taskSecondCode=" + taskSecondCode + "]";
	}

}
