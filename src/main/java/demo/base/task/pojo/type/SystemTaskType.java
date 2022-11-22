package demo.base.task.pojo.type;

public enum SystemTaskType {
	
	KEEP_DATABASE_CONNECTION_ALIVE("keepDatabaseConnectionAlive", 1),
	CLEAN_ATTEMPTS("cleanAttempts", 2),
	CLEAN_MAIL_RECORD("cleanMailRecord", 3),
	EVALUATION_CACHE_TO_STORE("evaluationCacheToStore", 4),
	EVALUATION_CACHE_STATISTICS("evaluationCacheStatistics", 5),
	REFILL_ARTICLE_LONG_REVIEW_CREATOR_ID("refillArticleLongReviewCreatorId", 6),
	CREATE_FAKE_EVALUATION_STORE("createFakeEvaluationStore",7),
	AUTO_PASS("autoPass",8),
	VISIT_COUNT_REDIS_TO_ORM("visitCountRedisToOrm",9),
	DELETE_EXPIRED_DENY_RECORD("deleteExpiredDenyRecord", 10),
	;
	
	private String name;
	private Integer code;
	
	SystemTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static SystemTaskType getType(String typeName) {
		for(SystemTaskType t : SystemTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static SystemTaskType getType(Integer typeCode) {
		for(SystemTaskType t : SystemTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
