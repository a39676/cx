package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.pojo.result.FindOrgListResult;
import demo.base.organizations.service.OrganizationService;
import demo.base.system.pojo.constant.InitSystemConstant;
import demo.base.user.mapper.AuthMapper;
import demo.base.user.pojo.bo.DeleteAuthBO;
import demo.base.user.pojo.bo.BatchDeleteAuthRoleBO;
import demo.base.user.pojo.bo.EditUserAuthBO;
import demo.base.user.pojo.bo.FindAuthsBO;
import demo.base.user.pojo.bo.InsertNewAuthBO;
import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.constant.AuthManagerView;
import demo.base.user.pojo.dto.DeleteAuthDTO;
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindAuthsDTO;
import demo.base.user.pojo.dto.FindOrgByConditionDTO;
import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.dto.InsertAuthDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.AuthExample;
import demo.base.user.pojo.po.AuthExample.Criteria;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindAuthsVOResult;
import demo.base.user.pojo.result.FindRolesResult;
import demo.base.user.pojo.result.FindRolesVOResult;
import demo.base.user.pojo.result.InsertNewAuthResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.pojo.type.AuthTypeType;
import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.pojo.vo.AuthVO;
import demo.base.user.service.AuthRoleService;
import demo.base.user.service.AuthService;
import demo.base.user.service.RoleService;
import demo.base.user.service.UserAuthService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.service.CommonService;

@Service
public class AuthServiceImpl extends CommonService implements AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private AuthMapper authMapper;
	@Autowired
	private AuthRoleService authRoleService;
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService orgService;
	
	@Override
	public Long __createBaseSuperAdminAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.SUPER_ADMIN, SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseAdminAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.ADMIN, SystemRolesType.ROLE_ADMIN);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseUserActiveAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.USER, SystemRolesType.ROLE_USER, SystemRolesType.ROLE_USER_ACTIVE);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseUserAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.USER, SystemRolesType.ROLE_USER);
		return newAuthID;
	}
	
	@Override
	public Long __createBasePosterAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.POSTER, SystemRolesType.ROLE_USER, SystemRolesType.ROLE_POSTER);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseDelayPosterAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.DELAY_POSTER, SystemRolesType.ROLE_USER, SystemRolesType.ROLE_POSTER, SystemRolesType.ROLE_DELAY_POSTER);
		return newAuthID;
	}
	
	private Long __createBaseAuth(Long supserAdminUserId, AuthType authType, SystemRolesType... roleTypes) {
		Auth newBaseAuth = new Auth();
		Long newAuthID = snowFlake.getNextId();
		newBaseAuth.setId(newAuthID);
		newBaseAuth.setAuthName(authType.getName());
		newBaseAuth.setAuthType(AuthTypeType.SYS_AUTH.getCode());
		newBaseAuth.setCreateBy(newAuthID);
		newBaseAuth.setBelongOrg(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
		
		int count = authMapper.insertSelective(newBaseAuth);
		if(count < 1) {
			log.error("create base %s auth error", authType.getName());
			return null;
		} 
		
		Long newAuthRoleId = null;
		
		List<Roles> baseRoleList = new ArrayList<Roles>();
		for(SystemRolesType rt : roleTypes) {
			baseRoleList.add(roleService.getBaseRoleByName(rt.getName()));
		}
		if(baseRoleList == null || baseRoleList.size() < 1) {
			log.error("There is no base roles, please init base role;");
			return null;
		}
		for(Roles role : baseRoleList) {
			newAuthRoleId = authRoleService.insertAuthRole(newAuthID, role.getRoleId(), supserAdminUserId);
			if(newAuthRoleId == null) {
				log.error("bind base %s auth role error", authType.getName());
				return null;
			}
		}
		
		return newAuthID;
	}
	
	
	@Override
	public ModelAndView authManagerView(String orgPK) {
		ModelAndView view = new ModelAndView(AuthManagerView.authManager);
		
		if(StringUtils.isBlank(orgPK)) {
			return view;
		}
		
		if(isBigUser()) {
			view.addObject("orgPk", orgPK);
			return view;
		}

		Long orgId = decryptPrivateKey(orgPK);
		if(orgId == null) {
			return view;
		}
		
		CommonResultCX validUserOrgResult = orgService.validUserOrg(orgId);
		if(validUserOrgResult.isFail()) {
			return view;
		} else {
			view.addObject("orgPk", orgPK);
		}
		
		return view;
	}
	
	@Override
	public ModelAndView authEditView(String authPK) {
		ModelAndView view = new ModelAndView(AuthManagerView.authEdit);
		
		if(StringUtils.isBlank(authPK)) {
			return view;
		}
		
		Long authId = decryptPrivateKey(authPK);
		
		CommonResultCX canEditUserAuthResult = canEditUserAuth(authId);
		if(canEditUserAuthResult.isFail()) {
			return view;
		}
		
		FindAuthsResult findAuthResult = findAuthsByCondition(authId);
		if(findAuthResult.isFail() || findAuthResult.getAuthList() == null || findAuthResult.getAuthList().isEmpty()) {
			return view;
		}
		Auth auth = findAuthResult.getAuthList().get(0);
		view.addObject("authName", auth.getAuthName());
		view.addObject("authPK", authPK);
		
		AuthTypeType authTypeType = AuthTypeType.getType(auth.getAuthType());
		if(AuthTypeType.SYS_AUTH.equals(authTypeType) && isBigUser()) {
			FindRolesVOResult findSysRoleResult = roleService.findSysRoles();
			view.addObject("sysRoleList", findSysRoleResult.getRoleVOList());
		}
		
		if(AuthTypeType.ORG_AUTH.equals(authTypeType)) {
			FindRolesVOResult findOrgRoleResult = roleService.findOrgRoles();
			view.addObject("orgRoleList", findOrgRoleResult.getRoleVOList());
		}
		
		FindAuthRoleDTO findAuthRoleDTO = new FindAuthRoleDTO();
		findAuthRoleDTO.setAuthIdList(authId);
		FindAuthRoleResult findAuthRoleResult = authRoleService.findAuthRole(findAuthRoleDTO);
		List<AuthRole> authRoleList = findAuthRoleResult.getAuthRoleList();
		if(authRoleList != null) {
			List<String> hasRolePK = new ArrayList<String>();
			List<Long> roleIdList = authRoleList.stream().map(AuthRole::getRoleId).collect(Collectors.toList());
			if(roleIdList != null && !roleIdList.isEmpty()) {
				hasRolePK.addAll(encryptId(roleIdList));
				view.addObject("hasRolePK", hasRolePK);
			}
		}
		
		return view;
	}
	
	@Override
	public FindAuthsResult findSuperAdministratorAuth() {
		FindAuthsResult r = new FindAuthsResult();
		
		AuthExample example = new AuthExample();
		example.createCriteria()
		.andIsDeleteEqualTo(false)
		.andBelongOrgEqualTo(InitSystemConstant.ORIGINAL_BASE_ORG_ID)
		.andAuthTypeEqualTo(AuthTypeType.SYS_AUTH.getCode())
		.andAuthNameEqualTo(AuthType.SUPER_ADMIN.getName());
		List<Auth> authList = authMapper.selectByExample(example);
		
		r.setAuthList(authList);
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public FindAuthsResult findAuthsByCondition(FindAuthsDTO dto) {
		FindAuthsResult r = new FindAuthsResult();
		FindAuthsBO bo = new FindAuthsBO();
		
		if(dto.getAuthPkList() != null && !dto.getAuthPkList().isEmpty()) {
			List<Long> authIdList = new ArrayList<Long>();
			Long authId = null;
			for(String authPk : dto.getAuthPkList()) {
				authId = decryptPrivateKey(authPk);
				if(authId == null) {
					r.failWithMessage("error param");
					return r;
				}
				authIdList.add(authId);
			}
			bo.setAuthIdList(authIdList);
		}
		
		if(dto.getBelongOrgPkList() != null && !dto.getBelongOrgPkList().isEmpty()) {
			List<Long> orgIdList = new ArrayList<Long>();
			Long orgId = null;
			for(String orgPk : dto.getBelongOrgPkList()) {
				orgId = decryptPrivateKey(orgPk);
				if(orgId == null) {
					r.failWithMessage("error param");
					return r;
				}
				orgIdList.add(orgId);
			}
			bo.setBelongOrgIdList(orgIdList);
		}
		
		if(isBigUser()) {
			bo.setIsDelete(dto.getIsDelete());
		} else {
			bo.setIsDelete(false);
		}
		
		bo.setAuthIsDelete(dto.getAuthIsDelete());
		bo.setAuthRoleIsDelete(dto.getAuthRoleIsDelete());
		bo.setRoleIsDelete(dto.getRoleIsDelete());

		bo.setAuthName(dto.getAuthName());
		bo.setAuthType(dto.getAuthType());
		bo.setOrgRoleTypeList(dto.getOrgRoleTypeList());
		bo.setSysRoleTypeList(dto.getSysRoleTypeList());
		
		return findAuthsByCondition(bo);
	}
	
	@Override
	public FindAuthsResult findAuthsByCondition(FindAuthsBO bo) {
		FindAuthsResult result = new FindAuthsResult();
		AuthExample example = new AuthExample();
		Criteria c = example.createCriteria();

		// 非管理员 禁止参数全空
		if((bo.getBelongOrgIdList() == null || bo.getBelongOrgIdList().isEmpty())
				&& (bo.getAuthIdList() == null || bo.getAuthIdList().isEmpty())
				&& (StringUtils.isBlank(bo.getAuthName()))
				&& bo.getAuthType() == null
				&& (bo.getSysRoleTypeList() == null || bo.getSysRoleTypeList().isEmpty())
				&& (bo.getOrgRoleTypeList() == null || bo.getOrgRoleTypeList().isEmpty())
				) {
			if(isBigUser()) {
				bo.setBelongOrgIdList(Arrays.asList(InitSystemConstant.ORIGINAL_BASE_ORG_ID));
			} else {
				result.failWithMessage("至少输入一个参数");
				return result;
			}
		}
		
		c.andIsDeleteEqualTo(bo.getIsDelete());
		if(bo.getAuthType() != null) {
			if(!isBigUser() && bo.getAuthType().equals(AuthTypeType.SYS_AUTH.getCode())) {
				result.failWithMessage("无权操作");
				return result;
			}
			c.andAuthTypeEqualTo(bo.getAuthType());
		}
		if(bo.getBelongOrgIdList() != null && bo.getBelongOrgIdList().size() > 0) {
			CommonResultCX validUserOrgIdResult = null;
			for(Long orgId : bo.getBelongOrgIdList()) {
				validUserOrgIdResult = orgService.validUserOrg(orgId);
				if(validUserOrgIdResult.isFail()) {
					result.addMessage(validUserOrgIdResult.getMessage());
					return result;
				}
			}
			c.andBelongOrgIn(bo.getBelongOrgIdList());
		}
		if(bo.getAuthIdList() != null && !bo.getAuthIdList().isEmpty()) {
			c.andIdIn(bo.getAuthIdList());
		}
		if(StringUtils.isNotBlank(bo.getAuthName())) {
			c.andAuthNameLike("%" + bo.getAuthName() + "%");
		}
		
		if((bo.getSysRoleTypeList() != null && !bo.getSysRoleTypeList().isEmpty())
				|| (bo.getOrgRoleTypeList() != null && !bo.getOrgRoleTypeList().isEmpty())
				) {
			FindAuthRoleDTO findAuthRoleDTO = new FindAuthRoleDTO();
			findAuthRoleDTO.setSysRoleTypeList(bo.getSysRoleTypeList());
			findAuthRoleDTO.setOrgRoleTypeList(bo.getOrgRoleTypeList());
			findAuthRoleDTO.setOrgIdList(bo.getBelongOrgIdList());
			findAuthRoleDTO.setAuthIdList(bo.getAuthIdList());
			FindAuthRoleResult authRoleResult = authRoleService.findAuthRole(findAuthRoleDTO);
			if(!authRoleResult.isSuccess()) {
				result.setMessage(authRoleResult.getMessage());
				return result;
			}
			
			Set<Long> authIdSet = authRoleResult.getAuthRoleList().stream().map(AuthRole::getAuthId).collect(Collectors.toSet());
			if(authIdSet.size() > 0) {
				c.andIdIn(List.copyOf(authIdSet));
			}
		}
		
		List<Auth> auths = authMapper.selectByExample(example);

		result.setAuthList(auths);
		result.setIsSuccess();
		return result;
	}
	
	@Override
	public FindAuthsVOResult findAuthVOListByCondition(FindAuthsDTO dto) {
		FindAuthsVOResult r = new FindAuthsVOResult();
		
		FindAuthsResult poResult = findAuthsByCondition(dto);
		if(!poResult.isSuccess()) {
			r.addMessage(poResult.getMessage());
			return r;
		}
		
		List<Auth> auths = poResult.getAuthList();
		
		List<AuthVO> authVOList = new ArrayList<AuthVO>();
		for(Auth po : auths) {
			authVOList.add(buildAuthVOByPO(po));
		}
		
		r.setAuthVOList(authVOList);
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public AuthVO buildAuthVOByPO(Auth po) {
		AuthVO vo = new AuthVO();
		vo.setPk(encryptId(po.getId()));
		vo.setAuthName(po.getAuthName());
		vo.setAuthType(po.getAuthType());
		vo.setBelongOrg(po.getBelongOrg());
		vo.setIsDelete(po.getIsDelete());
		return vo;
	}
	
	@Override
	public FindAuthsResult findAuthsByCondition(Long authId) {
		FindAuthsResult r = new FindAuthsResult();
		if(authId == null) {
			return r;
		}
		Auth auth = authMapper.selectByPrimaryKey(authId);
		r.setAuthList(Arrays.asList(auth));
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public FindAuthsResult findAuthsByCondition(AuthType authType) {
		FindAuthsResult r = new FindAuthsResult();
		AuthExample example = new AuthExample();
		example.createCriteria().andAuthTypeEqualTo(authType.getCode().intValue());
		List<Auth> auths = authMapper.selectByExample(example);
		r.setAuthList(auths);
		return r;
	}
	
	@Override
	public InsertNewAuthResult insertOrgAuth(InsertAuthDTO dto) {
		if(!vaildAndEditInsertNewOrgAuth(dto)) {
			InsertNewAuthResult r = new InsertNewAuthResult();
			r.failWithMessage("无权编辑");
			return r;
		}
		
		dto.setSysRoles(null);
		InsertNewAuthBO bo = new InsertNewAuthBO();
		BeanUtils.copyProperties(dto, bo);
		bo.setCreatorId(baseUtilCustom.getUserId());
		bo.setAuthTypeType(AuthTypeType.ORG_AUTH);
		
		return insertNewAuth(bo);
	}
	
	@Override
	public InsertNewAuthResult insertSysAuth(InsertAuthDTO dto) {
		if(!baseUtilCustom.hasSuperAdminRole()) {
			InsertNewAuthResult r = new InsertNewAuthResult();
			r.failWithMessage("无权编辑");
			return r;
		}
		
		dto.setOrgRoles(null);
		InsertNewAuthBO bo = new InsertNewAuthBO();
		BeanUtils.copyProperties(dto, bo);
		bo.setCreatorId(baseUtilCustom.getUserId());
		bo.setAuthTypeType(AuthTypeType.ORG_AUTH);
		
		return insertNewAuth(bo);
	}
	
	private InsertNewAuthResult insertNewAuth(InsertNewAuthBO bo) {
		InsertNewAuthResult r = new InsertNewAuthResult();
		Auth newAuth = new Auth();
		if((bo.getOrgRoles() != null && !bo.getOrgRoles().isEmpty())
				&& (bo.getSysRoles() != null && !bo.getSysRoles().isEmpty())) {
			r.failWithMessage("参数异常");
			return r;
		} else if ((bo.getOrgRoles() == null || bo.getOrgRoles().isEmpty())
				&& (bo.getSysRoles() == null || bo.getSysRoles().isEmpty())){
			r.failWithMessage("null param");
			return r;
		}
		newAuth.setCreateBy(bo.getCreatorId());
		Long newAuthID = snowFlake.getNextId();
		newAuth.setId(newAuthID);
		newAuth.setAuthName(bo.getAuthName());
		newAuth.setBelongOrg(bo.getBelongOrgId());
		newAuth.setAuthType(bo.getAuthTypeType().getCode());
		
		int count = authMapper.insertSelective(newAuth);
		if(count < 1) {
			r.failWithMessage("database error");
			return r;
		} 
		
		Long newAuthRoleId = null;
		
		List<String> roleNameList = new ArrayList<String>();
		if(bo.getOrgRoles() != null) {
			for(OrganzationRolesType orgRole : bo.getOrgRoles()) {
				roleNameList.add(orgRole.getName());
			}
		}
		if(bo.getSysRoles() != null) {
			for(SystemRolesType sysRole : bo.getSysRoles()) {
				roleNameList.add(sysRole.getName());
			}
		}
		
		FindRolesDTO findRolesDTO = new FindRolesDTO();
		findRolesDTO.setRoleNameList(roleNameList);
		FindRolesResult getRoleResult = roleService.getRolesByCondition(findRolesDTO);
		if(!getRoleResult.isSuccess()) {
			r.addMessage(getRoleResult.getMessage());
			return r;
		}
		
		List<Roles> roleList = getRoleResult.getRoleList();
		for(Roles role : roleList) {
			newAuthRoleId = authRoleService.insertAuthRole(newAuthID, role.getRoleId(), bo.getCreatorId());
			if(newAuthRoleId == null) {
				r.failWithMessage("insert auth role service error");
				return r;
			}
		}
		
		r.setIsSuccess();
		return r;
	}
	
	private boolean vaildAndEditInsertNewOrgAuth(InsertAuthDTO dto) {
		if(dto.getBelongOrgId() == null || !baseUtilCustom.isLoginUser()) {
			return false;
		}
		
		if(isBigUser()) {
			return true;
		}
		
		CommonResultCX validUserOrgResult = orgService.validUserOrg(dto.getBelongOrgId());
		return validUserOrgResult.isSuccess();
	}
	
	@Override
	public CommonResultCX deleteAuth(DeleteAuthDTO dto) {
		DeleteAuthBO bo = new DeleteAuthBO();
		bo.setOrgId(decryptPrivateKey(dto.getOrgPk()));
		bo.setAuthIdList(decryptPrivateKey(dto.getAuthPkList()));
		return deleteAuth(bo);
	}
	
	@Override
	public CommonResultCX deleteAuth(DeleteAuthBO bo) {
		CommonResultCX r = new CommonResultCX();
		if((bo.getAuthIdList() == null || bo.getAuthIdList().isEmpty())
				&& (bo.getOrgId() == null)
				) {
			r.failWithMessage("参数为空");
			return r;
		}
		
		CommonResultCX canEditUserAuthResult = null;
		List<Long> deleteAuthIdList = new ArrayList<Long>();
		
		AuthExample authExample = new AuthExample();
		Criteria authCriteria = authExample.createCriteria();
		if(bo.getOrgId() != null) {
			authCriteria.andBelongOrgEqualTo(bo.getOrgId());
		}
		if(bo.getAuthIdList() != null && !bo.getAuthIdList().isEmpty()) {
			authCriteria.andIdIn(bo.getAuthIdList());
		}
		
		List<Auth> targetAuthList = authMapper.selectByExample(authExample);
		
		for(Auth auth : targetAuthList) {
			canEditUserAuthResult = canEditUserAuth(auth.getId());
			if(!canEditUserAuthResult.isSuccess()) {
				r.addMessage(canEditUserAuthResult.getMessage());
				return r;
			}
			deleteAuthIdList.add(auth.getId());
		}
		
		EditUserAuthBO deleteUserAuthBO = null;
		for(Long authId : deleteAuthIdList) {
			deleteUserAuthBO = new EditUserAuthBO();
			deleteUserAuthBO.setAuthId(authId);
			canEditUserAuthResult = userAuthService.deleteUserAuth(deleteUserAuthBO);
			if(canEditUserAuthResult.isFail()) {
				r.addMessage(canEditUserAuthResult.getMessage());
				return r;
			}
		}
		
		Long operatorId = baseUtilCustom.getUserId();
		AuthExample example = new AuthExample();
		example.createCriteria().andIdIn(deleteAuthIdList);
		Auth record = new Auth();
		record.setIsDelete(true);
		record.setUpdateBy(operatorId);
		record.setUpdateTime(LocalDateTime.now());
		int updateCount = authMapper.updateByExample(record, example);
		if(updateCount < 1) {
			return r;
		}
		
		BatchDeleteAuthRoleBO deleteAuthRoleBO = new BatchDeleteAuthRoleBO();
		deleteAuthRoleBO.setAuthIdList(deleteAuthIdList);
		CommonResultCX deleteAuthRoleResult = authRoleService.batchDeleteAuthRole(deleteAuthRoleBO);
		if(!deleteAuthRoleResult.isSuccess()) {
			r.addMessage(deleteAuthRoleResult.getMessage());
			return r;
		}
		
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public CommonResultCX canEditUserAuth(Long authId) {
		CommonResultCX r = new CommonResultCX();
		if(authId == null) {
			r.failWithMessage("参数为空");
			return r;
		}
		
		MyUserPrincipal principal = baseUtilCustom.getUserPrincipal();
		if((principal.getSuperManagerOrgList() == null || principal.getSuperManagerOrgList().isEmpty())
				&& (principal.getControllerOrganizations() == null || principal.getControllerOrganizations().isEmpty())
				&& (principal.getSubOrganizations() == null || principal.getSubOrganizations().isEmpty())
				&& !isBigUser()
				) {
			r.failWithMessage("无权操作该角色");
			return r;
		}

		FindAuthsResult findAuthResult = findAuthsByCondition(authId);
		if(!findAuthResult.isSuccess() || findAuthResult.getAuthList() == null || findAuthResult.getAuthList().isEmpty()) {
			r.addMessage(findAuthResult.getMessage());
			return r;
		}
		
		Auth targetAuth = findAuthResult.getAuthList().get(0);
		
		List<Long> orgIdList = null;
		if(principal.getSuperManagerOrgList() != null && !principal.getSuperManagerOrgList().isEmpty()) {
			orgIdList = principal.getSuperManagerOrgList().stream().map(Organizations::getId).collect(Collectors.toList());
			if(orgIdList.contains(targetAuth.getBelongOrg())) {
				r.setIsSuccess();
				return r;
			}
		}

		if(principal.getControllerOrganizations() != null && !principal.getControllerOrganizations().isEmpty()) {
			orgIdList = principal.getControllerOrganizations().stream().map(Organizations::getId).collect(Collectors.toList());
			if(orgIdList.contains(targetAuth.getBelongOrg())) {
				r.setIsSuccess();
				return r;
			}
		}
		
		if(principal.getSubOrganizations() != null && !principal.getSubOrganizations().isEmpty()) {
			orgIdList = principal.getSubOrganizations().stream().map(Organizations::getId).collect(Collectors.toList());
			if(orgIdList.contains(targetAuth.getBelongOrg())) {
				r.setIsSuccess();
				return r;
			}
		}
		
		FindOrgByConditionDTO findOrgDTO = new FindOrgByConditionDTO();
		findOrgDTO.setTopOrg(targetAuth.getBelongOrg());
		findOrgDTO.setOrgId(targetAuth.getBelongOrg());
		FindOrgListResult findOrgResult = orgService.findOrgList(findOrgDTO);
		if(findOrgResult.isFail()) {
			r.addMessage(findOrgResult.getMessage());
			return r;
		}
		
		if(findOrgResult.getOrgVOList() != null && !findOrgResult.getOrgVOList().isEmpty()) {
			r.setIsSuccess();
			return r;
		}
		
		r.failWithMessage("无权操作该角色");
		return r;
		
	}
}
