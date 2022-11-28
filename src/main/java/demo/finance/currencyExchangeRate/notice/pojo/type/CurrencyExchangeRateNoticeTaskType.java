package demo.finance.currencyExchangeRate.notice.pojo.type;

public enum CurrencyExchangeRateNoticeTaskType {
	
	DELETE_OLD_NOTICE("deleteOldNotice", 1),
	;
	
	private String name;
	private Integer code;
	
	CurrencyExchangeRateNoticeTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CurrencyExchangeRateNoticeTaskType getType(String typeName) {
		for(CurrencyExchangeRateNoticeTaskType t : CurrencyExchangeRateNoticeTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CurrencyExchangeRateNoticeTaskType getType(Integer typeCode) {
		for(CurrencyExchangeRateNoticeTaskType t : CurrencyExchangeRateNoticeTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
