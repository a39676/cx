package demo.base.organizations.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.organizations.mapper.OrganizationsMapper;
import demo.base.organizations.pojo.dto.OrgRegistDTO;
import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.result.OrgRegistResult;
import demo.base.organizations.service.OrganizationService;
import demo.baseCommon.service.CommonService;
import demo.config.costom_component.BaseUtilCustom;

@Service
public class OrganizationServiceImpl extends CommonService implements OrganizationService {

	private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	@Autowired
	private BaseUtilCustom baseUtil;

	@Autowired
	private OrganizationsMapper orgMapper;

	@Override
	public OrgRegistResult topOrgRegist(OrgRegistDTO dto) {
		OrgRegistResult result = verifyTopOrgRegistDTO(dto);
		if (!result.isSuccess()) {
			return result;
		}

		Organizations po = new Organizations();
		Long newId = snowFlake.getNextId();
		po.setId(newId);
		po.setCreateBy(baseUtil.getUserId());
		po.setCreateTime(LocalDateTime.now());
		po.setOrgName(dto.getOrgName());
		po.setTopOrg(newId);
		po.setBelongTo(newId);

		int i = orgMapper.insertSelective(po);
		if (i < 1) {
			log.error("top org save error");
			result.failWithMessage("top org save error");
		} else {
			result.normalSuccess();
		}

		return result;
	}

	@Override
	public OrgRegistResult affiliatesOrgRegist(OrgRegistDTO dto) {
		OrgRegistResult result = verifyAffiliatesOrgRegistDTO(dto);
		if (!result.isSuccess()) {
			return result;
		}

		Organizations po = new Organizations();
		Long newId = snowFlake.getNextId();
		po.setId(newId);
		po.setCreateBy(baseUtil.getUserId());
		po.setCreateTime(LocalDateTime.now());
		po.setOrgName(dto.getOrgName());
		po.setTopOrg(dto.getTopOrg());
		po.setBelongTo(dto.getBelongTo());

		int i = orgMapper.insertSelective(po);
		if (i < 1) {
			log.error("affiliates org save error");
			result.failWithMessage("affiliates org save error");
		} else {
			result.normalSuccess();
		}

		return result;
	}

	private OrgRegistResult verifyTopOrgRegistDTO(OrgRegistDTO dto) {
		OrgRegistResult result = new OrgRegistResult();
		if (StringUtils.isBlank(dto.getOrgName()) || dto.getOrgName().length() > 16) {
			result.failWithMessage("机构名称长度为1~16位");
			return result;
		}

		result.normalSuccess();
		return result;
	}

	private OrgRegistResult verifyAffiliatesOrgRegistDTO(OrgRegistDTO dto) {
		OrgRegistResult result = verifyTopOrgRegistDTO(dto);
		if (!result.isSuccess()) {
			return result;
		}

		if (dto.getBelongTo() == null || dto.getTopOrg() == null) {
			result.failWithMessage("请选择归属机构");
			return result;
		}

		result.normalSuccess();
		return result;
	}

	@Override
	public Organizations getOrgById(Long orgId) {
		if (orgId == null) {
			return null;
		}

		return orgMapper.selectByPrimaryKey(orgId);
	}

}
