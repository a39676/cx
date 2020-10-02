package demo.base.organizations.pojo.result;

import java.util.List;

import demo.base.organizations.pojo.vo.OrganizationVO;
import demo.common.pojo.result.CommonResultCX;

public class FindOrgListResult extends CommonResultCX {

	private List<OrganizationVO> orgVOList;

	public List<OrganizationVO> getOrgVOList() {
		return orgVOList;
	}

	public void setOrgVOList(List<OrganizationVO> orgVOList) {
		this.orgVOList = orgVOList;
	}

}
