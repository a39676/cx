package demo.base.organizations.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.organizations.mapper.OrganizationsMapper;
import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.result.OrgRegistResult;
import demo.base.organizations.service.__SystemOrganizationService;
import demo.base.system.pojo.constant.InitSystemConstant;
import demo.baseCommon.service.CommonService;

@Service
public class __SystemOrganizationServiceImpl extends CommonService implements __SystemOrganizationService {
	
	private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	private OrganizationsMapper orgMapper;
	
	@Override
	public OrgRegistResult __initBaseOrg(Long superAdminId) {
		if(superAdminId == null) {
			superAdminId = 0L;
		}
		
		OrgRegistResult result = new OrgRegistResult();

		Organizations po = orgMapper.selectByPrimaryKey(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
		if (po == null) {
			po = new Organizations();
			po.setId(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
			po.setCreateBy(superAdminId);
			po.setCreateTime(LocalDateTime.now());
			po.setOrgName(InitSystemConstant.BASE_ORG_NAME);
			po.setTopOrg(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
			po.setBelongTo(InitSystemConstant.ORIGINAL_BASE_ORG_ID);

			int count = orgMapper.insertSelective(po);
			if (count < 1) {
				log.error("init base org save error");
				result.failWithMessage("base org save error");
				return result;
			}
			result.setBaseOrg(po);
		} else if (po.getIsDelete()) {
			po.setIsDelete(false);
			int count = orgMapper.updateByPrimaryKeySelective(po);
			if (count < 1) {
				log.error("init base org update error");
				result.failWithMessage("base org update error");
				return result;
			}
			result.setBaseOrg(po);
		}
		
		result.normalSuccess();
		return result;
	}
	
	

}
