package demo.toyParts.educate.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class ExerciesFileSaveResult extends CommonResult {

	private Long exerciesId;
	private String filePath;

	public Long getExerciesId() {
		return exerciesId;
	}

	public void setExerciesId(Long exerciesId) {
		this.exerciesId = exerciesId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "ExerciesFileSaveResult [exerciesId=" + exerciesId + ", filePath=" + filePath + "]";
	}

}
