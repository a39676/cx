package demo.base.organizations.service;

import demo.base.organizations.pojo.dto.FindUserControlOrgDTO;
import demo.base.organizations.pojo.dto.OrgRegistDTO;
import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.result.FindOrgListResult;
import demo.base.organizations.pojo.result.FindUserControlOrgResult;
import demo.base.organizations.pojo.result.OrgRegistResult;
import demo.base.user.pojo.dto.FindOrgByConditionDTO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface OrganizationService {

	OrgRegistResult topOrgRegist(OrgRegistDTO dto);

	OrgRegistResult affiliatesOrgRegist(OrgRegistDTO dto);

	Organizations getOrgById(Long orgId);

	FindUserControlOrgResult findUserControlOrg(FindUserControlOrgDTO dto);

	FindOrgListResult findOrgList(FindOrgByConditionDTO dto);

	CommonResultCX validUserOrg(Long orgId);

}
