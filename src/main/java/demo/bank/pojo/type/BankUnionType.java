package demo.bank.pojo.type;

public enum BankUnionType {
	
	/** 银联 */
	bank("bank", 1),
	/** 非银联 */
	notBank("notBank", 0),
	;
	
	private String name;
	private Integer code;
	
	BankUnionType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static BankUnionType getType(String typeName) {
		for(BankUnionType t : BankUnionType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static BankUnionType getType(Integer typeCode) {
		for(BankUnionType t : BankUnionType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
