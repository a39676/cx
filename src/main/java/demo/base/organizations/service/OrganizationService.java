package demo.base.organizations.service;

import demo.base.organizations.pojo.dto.OrgRegistDTO;
import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.result.OrgRegistResult;

public interface OrganizationService {

	OrgRegistResult topOrgRegist(OrgRegistDTO dto);

	OrgRegistResult affiliatesOrgRegist(OrgRegistDTO dto);

	Organizations getOrgById(Long orgId);

}
