package demo.base.user.pojo.result;

import demo.common.pojo.result.CommonResultCX;

public class NewUserRegistResult extends CommonResultCX {

	private ValidUserRegistResult validUserRegistResult;

	public ValidUserRegistResult getValidUserRegistResult() {
		return validUserRegistResult;
	}

	public void setValidUserRegistResult(ValidUserRegistResult validUserRegistResult) {
		this.validUserRegistResult = validUserRegistResult;
	}

}
