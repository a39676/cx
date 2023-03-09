package demo.base.user.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class NewUserRegistResult extends CommonResult {

	private ValidUserRegistResult validUserRegistResult;

	private String newUserPk;

	public ValidUserRegistResult getValidUserRegistResult() {
		return validUserRegistResult;
	}

	public void setValidUserRegistResult(ValidUserRegistResult validUserRegistResult) {
		this.validUserRegistResult = validUserRegistResult;
	}

	public String getNewUserPk() {
		return newUserPk;
	}

	public void setNewUserPk(String newUserPk) {
		this.newUserPk = newUserPk;
	}

	@Override
	public String toString() {
		return "NewUserRegistResult [validUserRegistResult=" + validUserRegistResult + ", newUserPk=" + newUserPk + "]";
	}

}
