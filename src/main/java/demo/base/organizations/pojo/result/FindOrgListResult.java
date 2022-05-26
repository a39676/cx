package demo.base.organizations.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.organizations.pojo.vo.OrganizationVO;

public class FindOrgListResult extends CommonResult {

	private List<OrganizationVO> orgVOList;

	public List<OrganizationVO> getOrgVOList() {
		return orgVOList;
	}

	public void setOrgVOList(List<OrganizationVO> orgVOList) {
		this.orgVOList = orgVOList;
	}

}
