package demo.base.task.pojo.type;

public enum OldDataDeleteTaskType {

	CLEAN_OLD_AUTO_TEST_UPLOAD_IMAGE("cleanOldAutoTestUploadImage", 1),
	CLEAN_EXPIRED_ARTICLE_BURN("cleanExpiredArticleBurn", 2), 
	IMAGE_CLEAN("imageClean", 3),
	DELETE_OLD_EXERCISE_FILE("deleteOldExerciseFile", 4),;

	private String name;
	private Integer code;

	OldDataDeleteTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static OldDataDeleteTaskType getType(String typeName) {
		for (OldDataDeleteTaskType t : OldDataDeleteTaskType.values()) {
			if (t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}

	public static OldDataDeleteTaskType getType(Integer typeCode) {
		for (OldDataDeleteTaskType t : OldDataDeleteTaskType.values()) {
			if (t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
