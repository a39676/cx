package demo.finance.account_info.pojo.type;

public enum AccountType {
	
	/** 借记卡 */
	debitAccount("debitAccount", 1, "借记卡"),
	/** 贷记卡 */
	creditAccount("creditAccount", 2, "贷记卡"),
	/** 子账户,占用信用额度(分期付款,等..) */
	stagingAccountSpentQuota("stagingAccountSpentQuota", 3, "子账户占用信用额度"),
	/** 子账户,不占用信用额度(分期付款,等..) */
	stagingAccountNotSpentQuota("stagingAccountNotSpentQuota", 4, "子账户不占用信用额度"),
	;
	
	private String name;
	private Integer code;
	private String description;
	
	AccountType(String name, Integer code, String description) {
		this.name = name;
		this.code = code;
		this.description = description;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}

	public static AccountType getType(String typeName) {
		for(AccountType t : AccountType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static AccountType getType(Integer typeCode) {
		for(AccountType t : AccountType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
