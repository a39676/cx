package demo.finance.currencyExchangeRate.data.pojo.type;

public enum CurrencyExchangeRateDataTaskType {
	
	SEND_DATA_QUERY("sendDataQuery", 1),
	;
	
	private String name;
	private Integer code;
	
	CurrencyExchangeRateDataTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CurrencyExchangeRateDataTaskType getType(String typeName) {
		for(CurrencyExchangeRateDataTaskType t : CurrencyExchangeRateDataTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CurrencyExchangeRateDataTaskType getType(Integer typeCode) {
		for(CurrencyExchangeRateDataTaskType t : CurrencyExchangeRateDataTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
