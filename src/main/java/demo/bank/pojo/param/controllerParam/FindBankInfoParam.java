package demo.bank.pojo.param.controllerParam;

import java.util.List;

public class FindBankInfoParam {

	private Long bankUnionId;
	private Long bankId;
	private List<Long> bankIdList;
	private String bankEname;
	private String bankEnameShort;
	private String bankChineseName;
	private String bankChineseNameShort;
	private String fuzzyBankName;
	/** 是否常用/常规银行 */
	private Integer commonBank;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Long bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	public String getBankEname() {
		return bankEname;
	}

	public void setBankEname(String bankEname) {
		this.bankEname = bankEname;
	}

	public String getBankEnameShort() {
		return bankEnameShort;
	}

	public void setBankEnameShort(String bankEnameShort) {
		this.bankEnameShort = bankEnameShort;
	}

	public String getBankChineseName() {
		return bankChineseName;
	}

	public void setBankChineseName(String bankChineseName) {
		this.bankChineseName = bankChineseName;
	}

	public String getBankChineseNameShort() {
		return bankChineseNameShort;
	}

	public void setBankChineseNameShort(String bankChineseNameShort) {
		this.bankChineseNameShort = bankChineseNameShort;
	}

	public Integer getCommonBank() {
		return commonBank;
	}

	public void setCommonBank(Integer commonBank) {
		this.commonBank = commonBank;
	}

	public String getFuzzyBankName() {
		return fuzzyBankName;
	}

	public void setFuzzyBankName(String fuzzyBankName) {
		this.fuzzyBankName = fuzzyBankName;
	}

	public List<Long> getBankIdList() {
		return bankIdList;
	}

	public void setBankIdList(List<Long> bankIdList) {
		this.bankIdList = bankIdList;
	}

	@Override
	public String toString() {
		return "FindBankInfoParam [bankUnionId=" + bankUnionId + ", bankId=" + bankId + ", bankIdList=" + bankIdList
				+ ", bankEname=" + bankEname + ", bankEnameShort=" + bankEnameShort + ", bankChineseName="
				+ bankChineseName + ", bankChineseNameShort=" + bankChineseNameShort + ", fuzzyBankName="
				+ fuzzyBankName + ", commonBank=" + commonBank + "]";
	}

}
