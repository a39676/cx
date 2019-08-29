package demo.account_info.pojo.dto.controllerDTO;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class GetAccountListByConditionParam implements CommonControllerParam {
	
	private Long bankId;
	private Long bankUnionId;
	private Integer accountType;

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

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	@Override
	public GetAccountListByConditionParam fromJson(JSONObject j) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(j.toString(), GetAccountListByConditionParam.class);
	}

	@Override
	public String toString() {
		return "GetAccountListByConditionParam [bankId=" + bankId + ", bankUnionId=" + bankUnionId + ", accountType="
				+ accountType + "]";
	}

	
}
