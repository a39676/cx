package demo.bank.pojo.po;

public class BankInfo {
    private Long bankId;

	private String bankEname;

	private String bankEnameShort;

	private String bankChineseName;

	private String bankChineseNameShort;

	private Integer bankUnionId;

	private Boolean commonBank;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankEname() {
		return bankEname;
	}

	public void setBankEname(String bankEname) {
		this.bankEname = bankEname == null ? null : bankEname.trim();
	}

	public String getBankEnameShort() {
		return bankEnameShort;
	}

	public void setBankEnameShort(String bankEnameShort) {
		this.bankEnameShort = bankEnameShort == null ? null : bankEnameShort.trim();
	}

	public String getBankChineseName() {
		return bankChineseName;
	}

	public void setBankChineseName(String bankChineseName) {
		this.bankChineseName = bankChineseName == null ? null : bankChineseName.trim();
	}

	public String getBankChineseNameShort() {
		return bankChineseNameShort;
	}

	public void setBankChineseNameShort(String bankChineseNameShort) {
		this.bankChineseNameShort = bankChineseNameShort == null ? null : bankChineseNameShort.trim();
	}

	public Integer getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Integer bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	public Boolean getCommonBank() {
		return commonBank;
	}

	public void setCommonBank(Boolean commonBank) {
		this.commonBank = commonBank;
	}

	@Override
	public String toString() {
		return "BankInfo [bankId=" + bankId + ", bankEname=" + bankEname + ", bankEnameShort=" + bankEnameShort
				+ ", bankChineseName=" + bankChineseName + ", bankChineseNameShort=" + bankChineseNameShort
				+ ", bankUnionId=" + bankUnionId + ", commonBank=" + commonBank + "]";
	}

	
}