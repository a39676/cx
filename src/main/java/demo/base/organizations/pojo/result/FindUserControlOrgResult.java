package demo.base.organizations.pojo.result;

import java.util.List;

import demo.base.organizations.pojo.po.Organizations;
import demo.baseCommon.pojo.result.CommonResultCX;

public class FindUserControlOrgResult extends CommonResultCX {

	private List<Organizations> controllOrgList;

	private List<Organizations> subOrgList;

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
		return "FindUserControlOrgResult [controllOrgList=" + controllOrgList + ", subOrgList=" + subOrgList + "]";
	}

}
