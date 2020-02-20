package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import demo.base.user.pojo.dto.FindAuthsDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.AuthExample;
import demo.base.user.pojo.po.AuthExample.Criteria;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindAuthsVOResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.pojo.type.AuthTypeType;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.pojo.vo.AuthVO;
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
		newBaseAuth.setBelongOrg(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
		
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
		
		FindAuthsDTO dto = new FindAuthsDTO();
		dto.setAuthName(AuthType.SUPER_ADMIN.getName());
		dto.setAuthType(AuthTypeType.SYS_AUTH.getCode());
		dto.setBelongOrgIdList(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
		
		return findAuthsByCondition(dto);
	}
	
	@Override
	public FindAuthsResult findAuthsByCondition(FindAuthsDTO dto) {
		FindAuthsResult result = new FindAuthsResult();
		AuthExample example = new AuthExample();
		Criteria c = example.createCriteria();

		/*
		 * 
		 * TODO
		 * 如何限制 只能查找本机构角色
		 * 管理员能查找下级机构的管理员角色
		 * 
		 */
		
		// 非管理员 禁止参数全空
		if((dto.getBelongOrgIdList() == null || dto.getBelongOrgIdList().isEmpty())
				&& (dto.getAuthIdList() == null || dto.getAuthIdList().isEmpty())
				&& (StringUtils.isBlank(dto.getAuthName()))
				&& dto.getAuthType() == null
				&& (dto.getRoleTypeList() == null || dto.getRoleTypeList().isEmpty())
				) {
			if(isBigUser()) {
				dto.setBelongOrgIdList(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
			} else {
				result.failWithMessage("至少输入一个参数");
				return result;
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
		if(dto.getRoleTypeList() != null && !dto.getRoleTypeList().isEmpty()) {
			FindAuthRoleDTO findAuthRoleDTO = new FindAuthRoleDTO();
			findAuthRoleDTO.setRoleTypeList(dto.getRoleTypeList());
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
	
	private AuthVO buildAuthVOByPO(Auth po) {
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
		FindAuthsDTO dto = new FindAuthsDTO();
		dto.setAuthIdList(Arrays.asList(authId));
		return findAuthsByCondition(dto);
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
