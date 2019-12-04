package demo.finance.bank.pojo.param.controllerParam;

public class FindBankUnionParam {

	private Long bankUnionId;
	private String bankUnionEname;
	private String bankUnionEnameShort;
	private String bankUnionChineseName;
	private String bankUnionChineseNameShort;
	private String fuzzyBankUnionName;
	private Integer commonBankUnion;

	public String getFuzzyBankUnionName() {
		return fuzzyBankUnionName;
	}

	public void setFuzzyBankUnionName(String fuzzyBankUnionName) {
		this.fuzzyBankUnionName = fuzzyBankUnionName;
	}

	public Long getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Long bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	public String getBankUnionEname() {
		return bankUnionEname;
	}

	public void setBankUnionEname(String bankUnionEname) {
		this.bankUnionEname = bankUnionEname;
	}

	public String getBankUnionEnameShort() {
		return bankUnionEnameShort;
	}

	public void setBankUnionEnameShort(String bankUnionEnameShort) {
		this.bankUnionEnameShort = bankUnionEnameShort;
	}

	public String getBankUnionChineseName() {
		return bankUnionChineseName;
	}

	public void setBankUnionChineseName(String bankUnionChineseName) {
		this.bankUnionChineseName = bankUnionChineseName;
	}

	public String getBankUnionChineseNameShort() {
		return bankUnionChineseNameShort;
	}

	public void setBankUnionChineseNameShort(String bankUnionChineseNameShort) {
		this.bankUnionChineseNameShort = bankUnionChineseNameShort;
	}

	public Integer getCommonBankUnion() {
		return commonBankUnion;
	}

	public void setCommonBankUnion(Integer commonBankUnion) {
		this.commonBankUnion = commonBankUnion;
	}

	@Override
	public String toString() {
		return "FindBankUnionParam [bankUnionId=" + bankUnionId + ", bankUnionEname=" + bankUnionEname
				+ ", bankUnionEnameShort=" + bankUnionEnameShort + ", bankUnionChineseName=" + bankUnionChineseName
				+ ", bankUnionChineseNameShort=" + bankUnionChineseNameShort + ", fuzzyBankUnionName="
				+ fuzzyBankUnionName + ", commonBankUnion=" + commonBankUnion + "]";
	}

}
