package demo.finance.cryptoCoin.data.pojo.type;

public enum CryptoCoinTaskType {
	
	CHECK_WEB_SOCKET_STATUS("checkWebSocketStatus", 1),
	CLEAN_OLD_HISTORY_DATA("cleanOldHistoryData", 2),
	;
	
	private String name;
	private Integer code;
	
	CryptoCoinTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CryptoCoinTaskType getType(String typeName) {
		for(CryptoCoinTaskType t : CryptoCoinTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CryptoCoinTaskType getType(Integer typeCode) {
		for(CryptoCoinTaskType t : CryptoCoinTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
