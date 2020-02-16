package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.constant.InitSystemConstant;
import demo.base.user.mapper.AuthMapper;
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindAuthsConditionDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.AuthExample;
import demo.base.user.pojo.po.AuthExample.Criteria;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.pojo.type.AuthTypeType;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.service.AuthRoleService;
import demo.base.user.service.AuthService;
import demo.base.user.service.RoleService;
import demo.baseCommon.service.CommonService;

@Service
public class AuthServiceImpl extends CommonService implements AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private AuthRoleService authRoleService;
	@Autowired
	private RoleService roleService;
	
	@Override
	public Long __createBaseSuperAdminAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.SUPER_ADMIN, RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_ADMIN);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseAdminAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.ADMIN, RolesType.ROLE_ADMIN);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseUserActiveAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.USER, RolesType.ROLE_USER, RolesType.ROLE_USER_ACTIVE);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseUserAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.USER, RolesType.ROLE_USER);
		return newAuthID;
	}
	
	@Override
	public Long __createBasePosterAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.POSTER, RolesType.ROLE_USER, RolesType.ROLE_POSTER);
		return newAuthID;
	}
	
	@Override
	public Long __createBaseDelayPosterAuth(Long supserAdminUserId) {
		Long newAuthID = __createBaseAuth(supserAdminUserId, AuthType.DELAY_POSTER, RolesType.ROLE_USER, RolesType.ROLE_POSTER, RolesType.ROLE_DELAY_POSTER);
		return newAuthID;
	}
	
	private Long __createBaseAuth(Long supserAdminUserId, AuthType authType, RolesType... roleTypes) {
		Auth newBaseAuth = new Auth();
		Long newAuthID = snowFlake.getNextId();
		newBaseAuth.setId(newAuthID);
		newBaseAuth.setAuthName(authType.getName());
		newBaseAuth.setAuthType(AuthTypeType.SYS_AUTH.getCode());
		newBaseAuth.setCreateBy(newAuthID);
		newBaseAuth.setBelongOrg(InitSystemConstant.BASE_ORG_ID);
		
		int count = authMapper.insertSelective(newBaseAuth);
		if(count < 1) {
			log.error("create base %s auth error", authType.getName());
			return null;
		} 
		
		Long newAuthRoleId = null;
		
		List<Roles> baseRoleList = new ArrayList<Roles>();
		for(RolesType rt : roleTypes) {
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
	public FindAuthsResult findSuperAdministratorAuth() {
		
		FindAuthsConditionDTO dto = new FindAuthsConditionDTO();
		dto.setAuthName(AuthType.SUPER_ADMIN.getName());
		dto.setAuthType(AuthTypeType.SYS_AUTH.getCode());
		dto.setBelongOrgIdList(InitSystemConstant.BASE_ORG_ID);
		
		return findAuthsByCondition(dto);
	}
	
	
	@Override
	public FindAuthsResult findAuthsByCondition(FindAuthsConditionDTO dto) {
		FindAuthsResult result = new FindAuthsResult();
		AuthExample example = new AuthExample();
		Criteria c = example.createCriteria();

		/*
		 * 只能查找本机构角色
		 * 管理员能查找下级机构的管理员角色?
		 * 非管理员可否查找角色?
		 * 
		 */
		if(dto.getBelongOrgIdList() == null || dto.getBelongOrgIdList().size() < 1) {
			if(isBigUser()) {
				dto.setBelongOrgIdList(InitSystemConstant.BASE_ORG_ID);
			} else {
				result.failWithMessage("必须指定所属机构");
			}
		}
		
		if(dto.getAuthIdList() != null && dto.getAuthIdList() .size() > 0) {
			c.andIdIn(dto.getAuthIdList());
		}
		if(StringUtils.isNotBlank(dto.getAuthName())) {
			c.andAuthNameLike("%" + dto.getAuthName() + "%");
		}
		if(dto.getAuthType() != null) {
			c.andAuthTypeEqualTo(dto.getAuthType());
		}
		if(dto.getBelongOrgIdList() != null && dto.getBelongOrgIdList().size() > 0) {
			c.andBelongOrgIn(dto.getBelongOrgIdList());
		}
		if((dto.getRoleIdList() != null && dto.getRoleIdList().size() > 0) 
				||(dto.getRoleNameList() != null && dto.getRoleNameList().size() > 0)) {
			FindAuthRoleDTO findAuthRoleDTO = new FindAuthRoleDTO();
			findAuthRoleDTO.setRoleIdList(dto.getRoleIdList());
			findAuthRoleDTO.setRoleNameList(dto.getRoleNameList());
			findAuthRoleDTO.setOrgIdList(dto.getBelongOrgIdList());
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
	public FindAuthsResult findAuthsByCondition(AuthType authType) {
		FindAuthsResult r = new FindAuthsResult();
		AuthExample example = new AuthExample();
		example.createCriteria().andAuthTypeEqualTo(authType.getCode().intValue());
		List<Auth> auths = authMapper.selectByExample(example);
		r.setAuthList(auths);
		return r;
	}
}
