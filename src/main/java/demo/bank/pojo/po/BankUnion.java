package demo.bank.pojo.po;

public class BankUnion {
    private Long bankUnionId;

	private String bankUnionEname;

	private String bankUnionEnameShort;

	private String bankUnionChineseName;

	private String bankUnionChineseNameShort;

	private Boolean commonBankUnion;

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
		this.bankUnionEname = bankUnionEname == null ? null : bankUnionEname.trim();
	}

	public String getBankUnionEnameShort() {
		return bankUnionEnameShort;
	}

	public void setBankUnionEnameShort(String bankUnionEnameShort) {
		this.bankUnionEnameShort = bankUnionEnameShort == null ? null : bankUnionEnameShort.trim();
	}

	public String getBankUnionChineseName() {
		return bankUnionChineseName;
	}

	public void setBankUnionChineseName(String bankUnionChineseName) {
		this.bankUnionChineseName = bankUnionChineseName == null ? null : bankUnionChineseName.trim();
	}

	public String getBankUnionChineseNameShort() {
		return bankUnionChineseNameShort;
	}

	public void setBankUnionChineseNameShort(String bankUnionChineseNameShort) {
		this.bankUnionChineseNameShort = bankUnionChineseNameShort == null ? null : bankUnionChineseNameShort.trim();
	}

	public Boolean getCommonBankUnion() {
		return commonBankUnion;
	}

	public void setCommonBankUnion(Boolean commonBankUnion) {
		this.commonBankUnion = commonBankUnion;
	}

}