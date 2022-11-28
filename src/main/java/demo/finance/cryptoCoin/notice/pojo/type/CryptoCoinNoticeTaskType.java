package demo.finance.cryptoCoin.notice.pojo.type;

public enum CryptoCoinNoticeTaskType {
	
	CRYPTO_COIN_PRICE_NOTICE_HANDLER("cryptoCoinPriceNoticeHandler", 1),
	DELETE_OLD_NOTICE("deleteOldNotice", 2),
	;
	
	private String name;
	private Integer code;
	
	CryptoCoinNoticeTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CryptoCoinNoticeTaskType getType(String typeName) {
		for(CryptoCoinNoticeTaskType t : CryptoCoinNoticeTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CryptoCoinNoticeTaskType getType(Integer typeCode) {
		for(CryptoCoinNoticeTaskType t : CryptoCoinNoticeTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
