package demo.base.user.pojo.dto;

import java.util.List;

import demo.base.user.pojo.po.UserIp;

public class BatchInsertUserIpDTO {

	private List<UserIp> poList;

	public List<UserIp> getPoList() {
		return poList;
	}

	public void setPoList(List<UserIp> poList) {
		this.poList = poList;
	}

	@Override
	public String toString() {
		return "BatchInsertUserIpDTO [poList=" + poList + ", getPoList()=" + getPoList() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
