package demo.organizations.service;

import demo.organizations.pojo.dto.OrgRegistDTO;
import demo.organizations.pojo.po.Organizations;
import demo.organizations.pojo.result.OrgRegistResult;

public interface OrganizationService {

	OrgRegistResult topOrgRegist(OrgRegistDTO dto);

	OrgRegistResult affiliatesOrgRegist(OrgRegistDTO dto);

	Organizations getOrgById(Long orgId);

}
