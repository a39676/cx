package demo.toyParts.educate.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class ExerciseFileSaveResult extends CommonResult {

	private Long exerciseId;
	private String filePath;

	public Long getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(Long exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "ExerciseFileSaveResult [exerciseId=" + exerciseId + ", filePath=" + filePath + "]";
	}

}
