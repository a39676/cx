package demo.base.organizations.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.organizations.mapper.OrganizationsMapper;
import demo.base.organizations.pojo.bo.FindOrgByConditionBO;
import demo.base.organizations.pojo.dto.FindOrgByConditionDTO;
import demo.base.organizations.pojo.dto.FindUserControlOrgDTO;
import demo.base.organizations.pojo.dto.OrgRegistDTO;
import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.po.OrganizationsExample;
import demo.base.organizations.pojo.po.OrganizationsExample.Criteria;
import demo.base.organizations.pojo.result.FindOrgListResult;
import demo.base.organizations.pojo.result.FindUserControlOrgResult;
import demo.base.organizations.pojo.result.OrgRegistResult;
import demo.base.organizations.pojo.vo.OrganizationVO;
import demo.base.organizations.service.OrganizationService;
import demo.base.user.pojo.bo.FindUserAuthBO;
import demo.base.user.pojo.dto.FindUserByConditionDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.result.FindUserByConditionResult;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UserAuthService;
import demo.base.user.service.UsersService;
import demo.baseCommon.service.CommonService;

@Service
public class OrganizationServiceImpl extends CommonService implements OrganizationService {

	private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	@Autowired
	private OrganizationsMapper orgMapper;
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private UsersService userService;
	
	@Override
	public OrgRegistResult topOrgRegist(OrgRegistDTO dto) {
		OrgRegistResult result = verifyTopOrgRegistDTO(dto);
		if (!result.isSuccess()) {
			return result;
		}

		Organizations po = new Organizations();
		Long newId = snowFlake.getNextId();
		po.setId(newId);
		po.setCreateBy(baseUtilCustom.getUserId());
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
		po.setCreateBy(baseUtilCustom.getUserId());
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

	@Override
	public FindUserControlOrgResult findUserControlOrg(FindUserControlOrgDTO dto) {
		FindUserControlOrgResult r = new FindUserControlOrgResult();
		
		if(dto.getUserId() == null) {
			r.failWithMessage("null param");
			return r;
		}
		
		FindUserAuthBO findUserAuthBO = new FindUserAuthBO();
		findUserAuthBO.setUserId(dto.getUserId());
		findUserAuthBO.setRoleTypeList(Arrays.asList(RolesType.ROLE_ADMIN));
		FindUserAuthResult adminUserAuthListResult = userAuthService.findUserAuth(findUserAuthBO);
		if(!adminUserAuthListResult.isSuccess()) {
			r.addMessage(adminUserAuthListResult.getMessage());
			return r;
		}
		
		List<Auth> adminAuthList = adminUserAuthListResult.getAuthList();
		
		List<Long> controllOrgIdList = adminAuthList.stream().map(Auth::getBelongOrg).collect(Collectors.toList());
		
		if(controllOrgIdList == null || controllOrgIdList.isEmpty()) {
			r.isSuccess();
			return r;
		}
		
		OrganizationsExample orgExample = new OrganizationsExample();
		orgExample.createCriteria().andIsDeleteEqualTo(false).andBelongToIn(controllOrgIdList).andIdNotIn(controllOrgIdList);
		List<Organizations> subOrgList = orgMapper.selectByExample(orgExample);
		
		orgExample = new OrganizationsExample();
		orgExample.createCriteria().andIsDeleteEqualTo(false).andIdIn(controllOrgIdList);
		List<Organizations> controllOrgList = orgMapper.selectByExample(orgExample);
		
		r.setControllOrgList(controllOrgList);
		r.setSubOrgList(subOrgList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public FindOrgListResult findOrgList(FindOrgByConditionDTO dto) {
		FindOrgByConditionBO bo = null;
		
		if(!baseUtilCustom.isLoginUser()) {
			return findOrgList(bo);
		}
		
		if(isBigUser()) {
			bo = buildFindOrgBOAdmin(dto);
		} else {
			bo = buildFindOrgBO(dto);
		}
		
		return findOrgList(bo);
	}

	private FindOrgByConditionBO buildFindOrgBO(FindOrgByConditionDTO dto) {
		FindOrgByConditionBO bo = new FindOrgByConditionBO();
		bo.setIsDelete(false);
		bo.setOrgName(dto.getOrgName());
		return bo;
	}
	
	private FindOrgByConditionBO buildFindOrgBOAdmin(FindOrgByConditionDTO dto) {
		FindOrgByConditionBO bo = new FindOrgByConditionBO();
		bo.setIsDelete(dto.getIsDelete());
		bo.setOrgName(dto.getOrgName());
		bo.setOrgId(decryptPrivateKey(dto.getOrgPk()));
		bo.setBelongTo(dto.getBelongTo());
		bo.setTopOrg(dto.getTopOrg());
		bo.setCreatorId(decryptPrivateKey(dto.getCreatorPk()));
		bo.setCreatorName(dto.getCreatorName());
		return bo;
	}
	
	private FindOrgListResult findOrgList(FindOrgByConditionBO bo) {
		FindOrgListResult r = new FindOrgListResult();
		
		if(bo == null) {
			return r;
		}
		
		if(bo.getOrgId() == null 
				&& bo.getBelongTo() == null
				&& bo.getTopOrg() == null
				&& bo.getCreatorId() == null
				&& StringUtils.isBlank(bo.getCreatorName())
				&& StringUtils.isBlank(bo.getOrgName())
				) {
			r.failWithMessage("不可所有参数为空");
			return r;
		}
		
		OrganizationsExample orgExample = new OrganizationsExample();
		Criteria orgCriteria = orgExample.createCriteria();
		
		if(bo.getCreatorId() != null || StringUtils.isNotBlank(bo.getCreatorName())) {
			FindUserByConditionDTO findUserDTO = new FindUserByConditionDTO();
			if(bo.getCreatorId() != null) {
				findUserDTO.setUserId(bo.getCreatorId());
			}
			if(StringUtils.isNotBlank(bo.getCreatorName())) {
				findUserDTO.setUserNickName(bo.getCreatorName());
			}
			FindUserByConditionResult findUserResult = userService.findUserByCondition(findUserDTO);
			if(!findUserResult.isSuccess()) {
				r.addMessage(findUserResult.getMessage());
				return r;
			}
			
			if(findUserResult.getUserVOList() == null || findUserResult.getUserVOList().isEmpty()) {
				return r;
			}
			
			List<Long> creatorIdList = new ArrayList<Long>();
			for(UsersDetailVO vo : findUserResult.getUserVOList()) {
				creatorIdList.add(decryptPrivateKey(vo.getUserPk()));
			}
			orgCriteria.andCreateByIn(creatorIdList);
		}
		
		if(bo.getIsDelete() != null) {
			orgCriteria.andIsDeleteEqualTo(bo.getIsDelete());
		}
		if(bo.getOrgId() != null) {
			orgCriteria.andIdEqualTo(bo.getOrgId());
		}
		if(bo.getBelongTo() != null) {
			orgCriteria.andBelongToEqualTo(bo.getBelongTo());
		}
		if(bo.getTopOrg() != null) {
			orgCriteria.andTopOrgEqualTo(bo.getTopOrg());
		}
		if(StringUtils.isNotBlank(bo.getOrgName())) {
			orgCriteria.andOrgNameLike("%" + bo.getOrgName() + "%");
		}
		
		List<Organizations> orgPOList = orgMapper.selectByExample(orgExample);
		List<OrganizationVO> orgVOList = new ArrayList<OrganizationVO>();
		
		for(Organizations po : orgPOList) {
			orgVOList.add(buildOrgVOByPO(po));
		}
		
		r.setOrgVOList(orgVOList);
		r.setIsSuccess();
		
		return r;
	}
	
	private OrganizationVO buildOrgVOByPO(Organizations po) {
		/*
		 * TODO
		 * 直属机构名 顶级机构名 未填充
		 */
		OrganizationVO vo = new OrganizationVO();
		vo.setPk(encryptId(po.getId()));
		vo.setOrgName(po.getOrgName());
		vo.setBelongToPk(encryptId(po.getBelongTo()));
		vo.setTopOrgPk(encryptId(po.getTopOrg()));
		vo.setIsDelete(po.getIsDelete());
		return vo;
	}
}
