package demo.base.user.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class __baseSuperAdminRegistResult extends CommonResult {

	private Long newSuperAdminId;

	public Long getNewSuperAdminId() {
		return newSuperAdminId;
	}

	public void setNewSuperAdminId(Long newSuperAdminId) {
		this.newSuperAdminId = newSuperAdminId;
	}

	@Override
	public String toString() {
		return "__baseSuperAdminRegistVO [newSuperAdminId=" + newSuperAdminId + ", getCode()=" + getCode()
				+ ", getResult()=" + getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
