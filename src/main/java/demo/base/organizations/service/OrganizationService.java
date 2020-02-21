package demo.base.organizations.service;

import demo.base.organizations.pojo.dto.FindOrgByConditionDTO;
import demo.base.organizations.pojo.dto.FindUserControlOrgDTO;
import demo.base.organizations.pojo.dto.OrgRegistDTO;
import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.result.FindOrgListResult;
import demo.base.organizations.pojo.result.FindUserControlOrgResult;
import demo.base.organizations.pojo.result.OrgRegistResult;

public interface OrganizationService {

	OrgRegistResult topOrgRegist(OrgRegistDTO dto);

	OrgRegistResult affiliatesOrgRegist(OrgRegistDTO dto);

	Organizations getOrgById(Long orgId);

	FindUserControlOrgResult findUserControlOrg(FindUserControlOrgDTO dto);

	FindOrgListResult findOrgList(FindOrgByConditionDTO dto);

}
