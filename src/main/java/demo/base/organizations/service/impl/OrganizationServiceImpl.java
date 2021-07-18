package demo.base.organizations.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.organizations.mapper.OrganizationsMapper;
import demo.base.organizations.pojo.bo.FindOrgByConditionBO;
import demo.base.organizations.pojo.constant.OrganizationConstant;
import demo.base.organizations.pojo.dto.DeleteOrgDTO;
import demo.base.organizations.pojo.dto.FindUserControlOrgDTO;
import demo.base.organizations.pojo.dto.OrgRegistDTO;
import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.po.OrganizationsExample;
import demo.base.organizations.pojo.po.OrganizationsExample.Criteria;
import demo.base.organizations.pojo.result.FindOrgListResult;
import demo.base.organizations.pojo.result.FindUserControlOrgResult;
import demo.base.organizations.pojo.result.OrgRegistResult;
import demo.base.organizations.pojo.result.VerifyAffiliatesOrgRegistDTOResult;
import demo.base.organizations.pojo.vo.OrganizationVO;
import demo.base.organizations.service.OrganizationService;
import demo.base.user.pojo.bo.DeleteAuthBO;
import demo.base.user.pojo.bo.FindUserAuthBO;
import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.dto.EditAuthRoleByRoleNameDTO;
import demo.base.user.pojo.dto.FindOrgByConditionDTO;
import demo.base.user.pojo.dto.FindUserByConditionDTO;
import demo.base.user.pojo.dto.InsertAuthDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.result.FindUserByConditionResult;
import demo.base.user.pojo.result.InsertNewAuthResult;
import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.AuthRoleService;
import demo.base.user.service.AuthService;
import demo.base.user.service.UserAuthService;
import demo.base.user.service.UsersService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.service.CommonService;
import demo.tool.service.TextFilter;

@Service
public class OrganizationServiceImpl extends CommonService implements OrganizationService {

	private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	@Autowired
	protected TextFilter textFilter;
	
	@Autowired
	private OrganizationsMapper orgMapper;
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private AuthRoleService authRoleService;
	@Autowired
	private UsersService userService;
	@Autowired
	private AuthService authService;
	
	@Override
	public OrgRegistResult topOrgRegist(OrgRegistDTO dto) {
		OrgRegistResult result = new OrgRegistResult();
		if(!isBigUser()) {
			result.failWithMessage("没有权限操作");
			return result;
		}
		
		CommonResultCX verifyTopOrgRegistDTOResult = verifyTopOrgRegistDTO(dto);
		if (!verifyTopOrgRegistDTOResult.isSuccess()) {
			result.addMessage(verifyTopOrgRegistDTOResult.getMessage());
			return result;
		}
		
		PolicyFactory filter = textFilter.getAllFilter();
		dto.setOrgName(filter.sanitize(dto.getOrgName()));

		Organizations po = new Organizations();
		Long newOrgId = snowFlake.getNextId();
		po.setId(newOrgId);
		po.setCreateBy(baseUtilCustom.getUserId());
		po.setCreateTime(LocalDateTime.now());
		po.setOrgName(dto.getOrgName());
		po.setTopOrg(newOrgId);
		po.setBelongTo(newOrgId);

		int i = orgMapper.insertSelective(po);
		if (i < 1) {
			log.error("top org save error");
			result.failWithMessage("top org save error");
			return result;
		}
		
		InsertAuthDTO insertNewAuthDTO = new InsertAuthDTO();
		insertNewAuthDTO.setAuthName(dto.getOrgName() + "_Admin");
		insertNewAuthDTO.setBelongOrgPK(encryptId(newOrgId));
		InsertNewAuthResult insertOrgAuthResult = authService.insertAuth(insertNewAuthDTO);
		if(!insertOrgAuthResult.isSuccess()) {
			result.addMessage(insertOrgAuthResult.getMessage());
			return result;
		}
		
		EditAuthRoleByRoleNameDTO insertAuthRoleDTO = new EditAuthRoleByRoleNameDTO();
		insertAuthRoleDTO.setAuthPK(insertOrgAuthResult.getAuthPK());
		insertAuthRoleDTO.setRoleName(OrganzationRolesType.ROLE_ORG_SUPER_ADMIN.getName());
		CommonResultCX insertAuthRoleResult = authRoleService.insertAuthRole(insertAuthRoleDTO);
		if(insertAuthRoleResult.isFail()) {
			result.failWithMessage(insertAuthRoleResult.getMessage());
			return result;
		}
		
		result.setIsSuccess();
		return result;
	}

	@Override
	public OrgRegistResult affiliatesOrgRegist(OrgRegistDTO dto) {
		OrgRegistResult result = new OrgRegistResult();
		VerifyAffiliatesOrgRegistDTOResult verifyAffiliatesOrgRegistDTOResult = verifyAffiliatesOrgRegistDTO(dto);
		if (!result.isSuccess()) {
			return result;
		}
		
		CommonResultCX validUserOrgResult = validUserOrg(verifyAffiliatesOrgRegistDTOResult.getBelongToOrgId());
		if(!validUserOrgResult.isSuccess()) {
			result.addMessage(validUserOrgResult.getMessage());
			return result;
		}
		
		PolicyFactory filter = textFilter.getAllFilter();
		dto.setOrgName(filter.sanitize(dto.getOrgName()));

		Organizations po = new Organizations();
		Long newOrgId = snowFlake.getNextId();
		po.setId(newOrgId);
		po.setCreateBy(baseUtilCustom.getUserId());
		po.setCreateTime(LocalDateTime.now());
		po.setOrgName(dto.getOrgName());
		po.setTopOrg(verifyAffiliatesOrgRegistDTOResult.getTopOrgId());
		po.setBelongTo(verifyAffiliatesOrgRegistDTOResult.getBelongToOrgId());

		int i = orgMapper.insertSelective(po);
		if (i < 1) {
			result.failWithMessage("affiliates org save error");
			return result;
		}
		
		InsertAuthDTO insertNewAuthDTO = new InsertAuthDTO();
		insertNewAuthDTO.setAuthName(dto.getOrgName() + "_admin_affiliate");
		insertNewAuthDTO.setBelongOrgPK(encryptId(newOrgId));
		InsertNewAuthResult insertOrgAuthResult = authService.insertAuth(insertNewAuthDTO);
		if(!insertOrgAuthResult.isSuccess()) {
			result.addMessage(insertOrgAuthResult.getMessage());
			return result;
		}
		
		EditAuthRoleByRoleNameDTO insertAuthRoleDTO = new EditAuthRoleByRoleNameDTO();
		insertAuthRoleDTO.setAuthPK(insertOrgAuthResult.getAuthPK());
		insertAuthRoleDTO.setRoleName(OrganzationRolesType.ROLE_SUB_ORG_ADMIN.getName());
		CommonResultCX insertAuthRoleResult = authRoleService.insertAuthRole(insertAuthRoleDTO);
		if(insertAuthRoleResult.isFail()) {
			result.failWithMessage(insertAuthRoleResult.getMessage());
			return result;
		}
		
		result.setIsSuccess();
		return result;
	}

	private CommonResultCX verifyTopOrgRegistDTO(OrgRegistDTO dto) {
		CommonResultCX result = new CommonResultCX();
		if (StringUtils.isBlank(dto.getOrgName()) || dto.getOrgName().length() > OrganizationConstant.orgNameMaxLength) {
			result.failWithMessage("机构名异常, 最长不得超过: " + OrganizationConstant.orgNameMaxLength + " 个字符, 汉字占用两个字符.");
			return result;
		}
		
		boolean validOrgNameDuplicate = validOrgNameDuplicate(dto.getOrgName());
		if(!validOrgNameDuplicate) {
			result.failWithMessage("重复机构名");
			return result;
		}

		result.normalSuccess();
		return result;
	}

	private VerifyAffiliatesOrgRegistDTOResult verifyAffiliatesOrgRegistDTO(OrgRegistDTO dto) {
		VerifyAffiliatesOrgRegistDTOResult r = new VerifyAffiliatesOrgRegistDTOResult();
		CommonResultCX verifyTopOrgRegistDTOResult = verifyTopOrgRegistDTO(dto);
		if (!verifyTopOrgRegistDTOResult.isSuccess()) {
			r.addMessage(verifyTopOrgRegistDTOResult.getMessage());
			return r;
		}

		if (StringUtils.isAnyBlank(dto.getBelongTo(), dto.getTopOrg())) {
			r.failWithMessage("请选择归属机构");
			return r;
		}
		
		if(StringUtils.isBlank(dto.getOrgName()) || OrganizationConstant.orgNameMaxLength < dto.getOrgName().length()) {
			r.failWithMessage("机构名异常, 最长不得超过: " + OrganizationConstant.orgNameMaxLength + " 个字符, 汉字占用两个字符.");
			return r;
		}
		
		boolean validOrgNameDuplicate = validOrgNameDuplicate(dto.getOrgName());
		if(!validOrgNameDuplicate) {
			r.failWithMessage("重复机构名");
			return r;
		}
		
		Long topOrgId = decryptPrivateKey(dto.getTopOrg());
		Long belongToOrgId = decryptPrivateKey(dto.getBelongTo());
		OrganizationsExample example = new OrganizationsExample();
		example.createCriteria().andIsDeleteEqualTo(false).andIdIn(Arrays.asList(topOrgId, belongToOrgId));
		List<Organizations> belongOrgList = orgMapper.selectByExample(example);
		if(belongOrgList == null || belongOrgList.isEmpty()) {
			r.failWithMessage("参数异常");
			return r;
		}

		r.setTopOrgId(topOrgId);
		r.setBelongToOrgId(belongToOrgId);
		r.normalSuccess();
		return r;
	}
	
	private boolean validOrgNameDuplicate(String orgName) {
		OrganizationsExample example = new OrganizationsExample();
		example.createCriteria().andIsDeleteEqualTo(false).andOrgNameEqualTo(orgName);
		List<Organizations> orgList = orgMapper.selectByExample(example);
		if(orgList != null && !orgList.isEmpty()) {
			return false;
		} else {
			return true;
		}
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
		findUserAuthBO.setOrgRoleTypeList(Arrays.asList(OrganzationRolesType.ROLE_ORG_SUPER_ADMIN));
		FindUserAuthResult orgSuperAdminUserAuthListResult = userAuthService.findUserAuth(findUserAuthBO);
		if(!orgSuperAdminUserAuthListResult.isSuccess()) {
			r.addMessage(orgSuperAdminUserAuthListResult.getMessage());
			return r;
		}
		List<Auth> orgSuperAdminAuthList = orgSuperAdminUserAuthListResult.getAuthList();
		List<Long> superManagerOrgIdList = orgSuperAdminAuthList.stream().map(Auth::getBelongOrg).collect(Collectors.toList());
		
		findUserAuthBO = new FindUserAuthBO();
		findUserAuthBO.setUserId(dto.getUserId());
		findUserAuthBO.setOrgRoleTypeList(Arrays.asList(OrganzationRolesType.ROLE_SUB_ORG_ADMIN));
		FindUserAuthResult orgAdminUserAuthListResult = userAuthService.findUserAuth(findUserAuthBO);
		if(!orgAdminUserAuthListResult.isSuccess()) {
			r.addMessage(orgAdminUserAuthListResult.getMessage());
			return r;
		}
		List<Auth> orgAdminAuthList = orgAdminUserAuthListResult.getAuthList();
		List<Long> controllOrgIdList = orgAdminAuthList.stream().map(Auth::getBelongOrg).collect(Collectors.toList());
		
		OrganizationsExample orgExample = new OrganizationsExample();
		if(controllOrgIdList != null && !controllOrgIdList.isEmpty()) {
			orgExample.createCriteria().andIsDeleteEqualTo(false).andBelongToIn(controllOrgIdList).andIdNotIn(controllOrgIdList);
			List<Organizations> subOrgList = orgMapper.selectByExample(orgExample);

			orgExample = new OrganizationsExample();
			orgExample.createCriteria().andIsDeleteEqualTo(false).andIdIn(controllOrgIdList);
			List<Organizations> controllOrgList = orgMapper.selectByExample(orgExample);
			
			r.setControllOrgList(controllOrgList);
			r.setSubOrgList(subOrgList);
		}
		
		if(superManagerOrgIdList != null && !superManagerOrgIdList.isEmpty()) {
			orgExample = new OrganizationsExample();
			orgExample.createCriteria().andIsDeleteEqualTo(false).andTopOrgIn(superManagerOrgIdList);
			List<Organizations> superManagerOrgList = orgMapper.selectByExample(orgExample);
			r.setSuperManagerOrgList(superManagerOrgList);
		}
		
		r.setIsSuccess();
		return r;
	}

	@Override
	public FindOrgListResult findOrgList(FindOrgByConditionDTO dto) {
		FindOrgByConditionBO bo = buildFindOrgBO(dto);
		return findOrgList(bo);
	}

	private FindOrgByConditionBO buildFindOrgBO(FindOrgByConditionDTO dto) {
		FindOrgByConditionBO bo = new FindOrgByConditionBO();
		bo.setIsDelete(dto.getIsDelete());
		bo.setOrgName(dto.getOrgName());
		if(StringUtils.isNotBlank(dto.getOrgPk())) {
			bo.setOrgId(decryptPrivateKey(dto.getOrgPk()));
		}
		if(StringUtils.isNotBlank(dto.getBelongTo())) {
			bo.setBelongTo(decryptPrivateKey(dto.getBelongTo()));
		}
		if(StringUtils.isNotBlank(dto.getTopOrg())) {
			bo.setTopOrg(decryptPrivateKey(dto.getTopOrg()));
		}
		if(StringUtils.isNotBlank(dto.getCreatorPk())) {
			bo.setCreatorId(decryptPrivateKey(dto.getCreatorPk()));
		}
		bo.setCreatorName(dto.getCreatorName());
		return bo;
	}
	
	@Override
	public FindOrgListResult findOrgList(FindOrgByConditionBO bo) {
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
		 * TODO 直属机构名 顶级机构名 未填充
		 */
		OrganizationVO vo = new OrganizationVO();
		vo.setPk(encryptId(po.getId()));
		vo.setOrgName(po.getOrgName());
		vo.setBelongToPk(encryptId(po.getBelongTo()));
		vo.setTopOrgPk(encryptId(po.getTopOrg()));
		vo.setIsDelete(po.getIsDelete());
		return vo;
	}

	@Override
	public CommonResultCX validUserOrg(Long orgId) {
		CommonResultCX r = new CommonResultCX();
		if(orgId == null) {
			r.failWithMessage("参数为空");
			return r;
		}
		
		MyUserPrincipal principal = baseUtilCustom.getUserPrincipal();
		if((principal.getSuperManagerOrgList() == null || principal.getSuperManagerOrgList().isEmpty())
				&& (principal.getControllerOrganizations() == null || principal.getControllerOrganizations().isEmpty())
				&& (principal.getSubOrganizations() == null || principal.getSubOrganizations().isEmpty())
				&& !isBigUser()
				) {
			r.failWithMessage("该账号无权操作此机构内容");
			return r;
		}

		List<Long> orgIdList = null;
		if(principal.getSuperManagerOrgList() != null) {
			orgIdList = principal.getSuperManagerOrgList().stream().map(Organizations::getId).collect(Collectors.toList());
			if(orgIdList.contains(orgId)) {
				r.setIsSuccess();
				return r;
			}
		}
		
		if(principal.getControllerOrganizations() != null) {
			orgIdList = principal.getControllerOrganizations().stream().map(Organizations::getId).collect(Collectors.toList());
			if(orgIdList.contains(orgId)) {
				r.setIsSuccess();
				return r;
			}
		}
		
		if(principal.getSubOrganizations() != null) {
			orgIdList = principal.getSubOrganizations().stream().map(Organizations::getId).collect(Collectors.toList());
			if(orgIdList.contains(orgId)) {
				r.setIsSuccess();
				return r;
			}
		}
		
		if(isBigUser()) {
			Organizations org = orgMapper.selectByPrimaryKey(orgId);
			if(org.getId().equals(org.getTopOrg())) {
				r.setIsSuccess();
				return r;
			}
		}
		
		r.failWithMessage("该账号无权操作此机构内容");
		return r;
		
	}

	@Override
	public CommonResultCX deleteOrg(DeleteOrgDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if(StringUtils.isBlank(dto.getOrgPk())) {
			return r;
		}
		
		Long orgId = decryptPrivateKey(dto.getOrgPk());
		if(orgId == null) {
			return r;
		}
		
		if(!isBigUser()) {
			CommonResultCX validUserOrgResult = validUserOrg(orgId);
			if(!validUserOrgResult.isSuccess()) {
				r.addMessage(validUserOrgResult.getMessage());
				return r;
			}
		}
		
		Long operator = baseUtilCustom.getUserId();
		Organizations po = new Organizations();
		po.setId(orgId);
		po.setIsDelete(true);
		po.setUpdateBy(operator);
		po.setUpdateTime(LocalDateTime.now());
		int upcateCount = orgMapper.updateByPrimaryKeySelective(po);
		if(upcateCount < 1) {
			return r;
		}
		
		DeleteAuthBO bo = new DeleteAuthBO();
		bo.setOrgId(orgId);
		CommonResultCX deleteAuthResult = authService.deleteAllAuthByOrgId(bo);
		if(deleteAuthResult.isFail()) {
			r.addMessage(deleteAuthResult.getMessage());
			return r;
		}
		
		r.setIsSuccess();
		return r;
	}
}
