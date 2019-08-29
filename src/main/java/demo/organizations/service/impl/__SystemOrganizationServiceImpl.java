package demo.organizations.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.organizations.mapper.OrganizationsMapper;
import demo.organizations.pojo.constant.OrgConstant;
import demo.organizations.pojo.po.Organizations;
import demo.organizations.pojo.result.OrgRegistResult;
import demo.organizations.service.__SystemOrganizationService;

@Service
public class __SystemOrganizationServiceImpl extends CommonService implements __SystemOrganizationService {
	
	private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	private OrganizationsMapper orgMapper;
	
	@Override
	public OrgRegistResult __initBaseOrg(Long superAdminId) {
		OrgRegistResult result = new OrgRegistResult();

		Organizations po = orgMapper.selectByPrimaryKey(OrgConstant.baseOrgId);
		if (po == null) {
			po = new Organizations();
			po.setId(OrgConstant.baseOrgId);
			po.setCreateBy(superAdminId);
			po.setCreateTime(LocalDateTime.now());
			po.setOrgName(OrgConstant.baseOrgName);
			po.setTopOrg(OrgConstant.baseOrgId);
			po.setBelongTo(OrgConstant.baseOrgId);

			int count = orgMapper.insertSelective(po);
			if (count < 1) {
				log.error("init base org save error");
				result.failWithMessage("base org save error");
				return result;
			}
		} else if (po.getIsDelete()) {
			po.setIsDelete(false);
			int count = orgMapper.updateByPrimaryKeySelective(po);
			if (count < 1) {
				log.error("init base org update error");
				result.failWithMessage("base org update error");
				return result;
			}
		}
		
		result.normalSuccess();
		return result;
	}
	
	

}
