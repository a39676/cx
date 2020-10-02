package demo.base.organizations.service;

import demo.base.organizations.pojo.bo.FindOrgByConditionBO;
import demo.base.organizations.pojo.dto.DeleteOrgDTO;
import demo.base.organizations.pojo.dto.FindUserControlOrgDTO;
import demo.base.organizations.pojo.dto.OrgRegistDTO;
import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.result.FindOrgListResult;
import demo.base.organizations.pojo.result.FindUserControlOrgResult;
import demo.base.organizations.pojo.result.OrgRegistResult;
import demo.base.user.pojo.dto.FindOrgByConditionDTO;
import demo.common.pojo.result.CommonResultCX;

public interface OrganizationService {

	OrgRegistResult topOrgRegist(OrgRegistDTO dto);

	OrgRegistResult affiliatesOrgRegist(OrgRegistDTO dto);

	Organizations getOrgById(Long orgId);

	/**
	 * 用于 用户登录时 构建 MyUserPrincipal 数据, 故不可直接使用 MyUserPrincipal , 构成死循环
	 */
	FindUserControlOrgResult findUserControlOrg(FindUserControlOrgDTO dto);

	FindOrgListResult findOrgList(FindOrgByConditionDTO dto);
	FindOrgListResult findOrgList(FindOrgByConditionBO bo);

	CommonResultCX validUserOrg(Long orgId);

	CommonResultCX deleteOrg(DeleteOrgDTO dto);

}
