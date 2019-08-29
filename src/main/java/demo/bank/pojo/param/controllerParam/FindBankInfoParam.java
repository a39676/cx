package demo.bank.pojo.param.controllerParam;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class FindBankInfoParam implements CommonControllerParam {

	private Long bankUnionId;
	private Long bankId;
	private String bankEname;
	private String bankEnameShort;
	private String bankChineseName;
	private String bankChineseNameShort;
	private String fuzzyBankName;
	/** 是否常用/常规银行 */
	private Integer commonBank;

	@Override
	public FindBankInfoParam fromJson(JSONObject j) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(j.toString(), FindBankInfoParam.class);
	}

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

	@Override
	public String toString() {
		return "FindBankInfoParam [bankUnionId=" + bankUnionId + ", bankId=" + bankId + ", bankEname=" + bankEname
				+ ", bankEnameShort=" + bankEnameShort + ", bankChineseName=" + bankChineseName
				+ ", bankChineseNameShort=" + bankChineseNameShort + ", fuzzyBankName=" + fuzzyBankName
				+ ", commonBank=" + commonBank + "]";
	}

}
