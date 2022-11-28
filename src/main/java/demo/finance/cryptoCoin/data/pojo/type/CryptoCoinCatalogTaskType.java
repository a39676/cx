package demo.finance.cryptoCoin.data.pojo.type;

public enum CryptoCoinCatalogTaskType {
	
	ADD_SUBSCRIPTION_CATALOG("addSubscriptionCatalog", 1),
	;
	
	private String name;
	private Integer code;
	
	CryptoCoinCatalogTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CryptoCoinCatalogTaskType getType(String typeName) {
		for(CryptoCoinCatalogTaskType t : CryptoCoinCatalogTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CryptoCoinCatalogTaskType getType(Integer typeCode) {
		for(CryptoCoinCatalogTaskType t : CryptoCoinCatalogTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
