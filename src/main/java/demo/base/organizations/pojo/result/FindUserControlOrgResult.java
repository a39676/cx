package demo.base.organizations.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.organizations.pojo.po.Organizations;

public class FindUserControlOrgResult extends CommonResult {

	private List<Organizations> superManagerOrgList;
	private List<Organizations> controllOrgList;
	private List<Organizations> subOrgList;

	public List<Organizations> getSuperManagerOrgList() {
		return superManagerOrgList;
	}

	public void setSuperManagerOrgList(List<Organizations> superManagerOrgList) {
		this.superManagerOrgList = superManagerOrgList;
	}

	public List<Organizations> getControllOrgList() {
		return controllOrgList;
	}

	public void setControllOrgList(List<Organizations> controllOrgList) {
		this.controllOrgList = controllOrgList;
	}

	public List<Organizations> getSubOrgList() {
		return subOrgList;
	}

	public void setSubOrgList(List<Organizations> subOrgList) {
		this.subOrgList = subOrgList;
	}

	@Override
	public String toString() {
		return "FindUserControlOrgResult [superManagerOrgList=" + superManagerOrgList + ", controllOrgList="
				+ controllOrgList + ", subOrgList=" + subOrgList + "]";
	}

}
