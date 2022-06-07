package demo.base.user.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class NewUserRegistResult extends CommonResult {

	private ValidUserRegistResult validUserRegistResult;

	public ValidUserRegistResult getValidUserRegistResult() {
		return validUserRegistResult;
	}

	public void setValidUserRegistResult(ValidUserRegistResult validUserRegistResult) {
		this.validUserRegistResult = validUserRegistResult;
	}

}
