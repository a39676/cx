package demo.finance.cryptoCoin.data.pojo.type;

public enum CryptoCoinDataTaskType {
	
	SUMMARY_MINUTE_DATA("summaryMinuteData", 3),
	SUMMARY_HISTORY_DATA("summaryHistoryData", 4),
	DELETE_EXPIRED_CACHE_DATA("deleteExpiredCacheData", 5),
	SEND_CRYPTO_COIN_DAILY_DATA_QUERY_MSG("sendCryptoCoinDailyDataQueryMsg", 6),
	RESET_DAILY_DATA_WAITING_QUERY_SET("resetDailyDataWaitingQuerySet", 7),
	;
	
	private String name;
	private Integer code;
	
	CryptoCoinDataTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CryptoCoinDataTaskType getType(String typeName) {
		for(CryptoCoinDataTaskType t : CryptoCoinDataTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CryptoCoinDataTaskType getType(Integer typeCode) {
		for(CryptoCoinDataTaskType t : CryptoCoinDataTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
