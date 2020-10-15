package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.organizations.service.OrganizationService;
import demo.base.user.mapper.AuthRoleMapper;
import demo.base.user.pojo.bo.BatchDeleteAuthRoleBO;
import demo.base.user.pojo.dto.EditAuthRoleByRoleNameDTO;
import demo.base.user.pojo.dto.EditAuthRoleDTO;
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.AuthRoleExample;
import demo.base.user.pojo.po.AuthRoleExample.Criteria;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindRolesResult;
import demo.base.user.pojo.type.AuthTypeType;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.service.AuthRoleService;
import demo.base.user.service.AuthService;
import demo.base.user.service.RoleService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.service.CommonService;

@Service
public class AuthRoleServiceImpl extends CommonService implements AuthRoleService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthRoleServiceImpl.class);
	
	@Autowired
	private AuthRoleMapper authRoleMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthService authService;
	@Autowired
	private OrganizationService orgService;
	
	@Override
	public Long __insertAuthRole(Long authId, Long roleId, Long creatorId) {
		if(authId == null || roleId == null || creatorId == null) {
			return null;
		}
		Long newAuthRoleId = snowFlake.getNextId();
		AuthRole ar = new AuthRole();
		ar.setId(newAuthRoleId);
		ar.setAuthId(authId);
		ar.setRoleId(roleId);
		ar.setCreateBy(creatorId);
		ar.setCreateTime(LocalDateTime.now());
		int count = authRoleMapper.insertSelective(ar);
		if(count < 1) {
			log.error("insert auth role error authId :" + authId + " roleId: " + roleId + "creatorId: " + creatorId);
			return null;
		}
		return newAuthRoleId;
	}
	
	@Override
	public FindAuthRoleResult findAuthRole(FindAuthRoleDTO dto) {
		FindAuthRoleResult r = new FindAuthRoleResult();
		
		if((dto.getOrgIdList() == null || dto.getOrgIdList().isEmpty())
				&& (dto.getAuthIdList() == null || dto.getAuthIdList().isEmpty())
				&& (dto.getSysRoleTypeList() == null || dto.getSysRoleTypeList().isEmpty())
				&& (dto.getOrgRoleTypeList() == null || dto.getOrgRoleTypeList().isEmpty())
				&& (dto.getRoleNameList() == null || dto.getRoleNameList().isEmpty())
				) {
			r.failWithMessage("不可输入空参数");
			return r;
		}
		
		AuthRoleExample authRoleExample = new AuthRoleExample();
		Criteria authRoleCriteria = authRoleExample.createCriteria();
		authRoleCriteria.andIsDeleteEqualTo(false);
		
		if(dto.getAuthIdList() != null && dto.getAuthIdList().size() > 0) {
			authRoleCriteria.andAuthIdIn(dto.getAuthIdList());
		}
		
		if((dto.getSysRoleTypeList() != null && !dto.getSysRoleTypeList().isEmpty())
				|| (dto.getOrgRoleTypeList() != null && !dto.getOrgRoleTypeList().isEmpty())
				|| (dto.getRoleNameList() != null && !dto.getRoleNameList().isEmpty())
				) {
			FindRolesDTO getRolesDTO = new FindRolesDTO();
			if(dto.getRoleNameList() != null && !dto.getRoleNameList().isEmpty()) {
				getRolesDTO.setRoleNameList(dto.getRoleNameList());
			}
			if(dto.getSysRoleTypeList() != null && !dto.getSysRoleTypeList().isEmpty()) {
				getRolesDTO.setSysRoleTypeList(dto.getSysRoleTypeList());
			}
			if(dto.getOrgRoleTypeList() != null && !dto.getOrgRoleTypeList().isEmpty()) {
				getRolesDTO.setOrgRoleTypeList(dto.getOrgRoleTypeList());
			}
			FindRolesResult findRolesResult = roleService.getRolesByCondition(getRolesDTO);
			if(!findRolesResult.isSuccess()) {
				r.addMessage(findRolesResult.getMessage());
				return r;
			}
			List<Roles> roleList = findRolesResult.getRoleList();
			authRoleCriteria.andRoleIdIn(roleList.stream().map(Roles::getRoleId).collect(Collectors.toList()));
		}
		
		List<AuthRole> authRoleList = authRoleMapper.selectByExample(authRoleExample);
		r.setAuthRoleList(authRoleList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResultCX batchDeleteAuthRole(BatchDeleteAuthRoleBO bo) {
		CommonResultCX r = new CommonResultCX();
		
		if(bo.getAuthIdList() == null || bo.getAuthIdList().isEmpty()) {
			return r;
		}
		
		Long operatorId = baseUtilCustom.getUserId();
		AuthRoleExample example = new AuthRoleExample();
		example.createCriteria().andAuthIdIn(bo.getAuthIdList()).andIsDeleteEqualTo(false);
		AuthRole record = new AuthRole();
		record.setIsDelete(true);
		record.setUpdateBy(operatorId);
		record.setUpdateTime(LocalDateTime.now());
		int updateCount = authRoleMapper.updateByExampleSelective(record, example);
		if(updateCount > 0) {
			r.setIsSuccess();
		}
		
		return r;
	}
	
	@Override
	public CommonResultCX deleteAuthRole(EditAuthRoleDTO dto) {
		CommonResultCX r = new CommonResultCX();
		
		if(StringUtils.isAnyBlank(dto.getAuthPK(), dto.getRolePK())) {
			r.failWithMessage("null param");
			return r;
		}
		
		Long authId = decryptPrivateKey(dto.getAuthPK());
		Long roleId = decryptPrivateKey(dto.getRolePK());
		if(authId == null || roleId == null) {
			r.failWithMessage("null param");
			return r;
		}
		
		Long operatorId = baseUtilCustom.getUserId();
		
		CommonResultCX canEditResult = canEditAuthRole(authId, roleId);
		if(canEditResult.isFail()) {
			r.failWithMessage(canEditResult.getMessage());
			return r;
		}
		
		AuthRoleExample example = new AuthRoleExample();
		example.createCriteria().andAuthIdEqualTo(authId).andRoleIdEqualTo(roleId).andIsDeleteEqualTo(false);
		AuthRole record = new AuthRole();
		record.setIsDelete(true);
		record.setUpdateBy(operatorId);
		record.setUpdateTime(LocalDateTime.now());
		int updateCount = authRoleMapper.updateByExampleSelective(record, example);
		if(updateCount > 0) {
			r.setIsSuccess();
		}
		
		return r;
	}
	
	@Override
	public CommonResultCX insertAuthRole(EditAuthRoleDTO dto) {
		CommonResultCX r = new CommonResultCX();
		
		if(StringUtils.isAnyBlank(dto.getAuthPK(), dto.getRolePK())) {
			r.failWithMessage("null param");
			return r;
		}
		
		Long authId = decryptPrivateKey(dto.getAuthPK());
		Long roleId = decryptPrivateKey(dto.getRolePK());
		return insertAuthRole(authId, roleId);
	}
	
	@Override
	public CommonResultCX insertAuthRole(EditAuthRoleByRoleNameDTO dto) {
		CommonResultCX r = new CommonResultCX();
		
		if(StringUtils.isAnyBlank(dto.getAuthPK(), dto.getRoleName())) {
			r.failWithMessage("null param");
			return r;
		}
		
		Long authId = decryptPrivateKey(dto.getAuthPK());
		Roles role = roleService.findRoleByName(dto.getRoleName());
		
		if(role == null) {
			r.failWithMessage("error param");
			return r;
		}
		return insertAuthRole(authId, role.getRoleId());
	}
	
	private CommonResultCX insertAuthRole(Long authId, Long roleId) {
		CommonResultCX r = new CommonResultCX();
		
		if(authId == null || roleId == null) {
			r.failWithMessage("null param");
			return r;
		}
		
		CommonResultCX canEditResult = canEditAuthRole(authId, roleId);
		if(canEditResult.isFail()) {
			r.failWithMessage(canEditResult.getMessage());
			return r;
		}
		
		AuthRoleExample example = new AuthRoleExample();
		example.createCriteria().andAuthIdEqualTo(authId).andRoleIdEqualTo(roleId);
		List<AuthRole> authList = authRoleMapper.selectByExample(example);
		if(authList != null && !authList.isEmpty()) {
			AuthRole auth = authList.get(0);
			if(auth.getIsDelete()) {
				auth.setIsDelete(false);
				auth.setUpdateTime(LocalDateTime.now());
				auth.setUpdateBy(baseUtilCustom.getUserId());
				int updateCount = authRoleMapper.updateByPrimaryKey(auth);
				if(updateCount > 0) {
					r.setIsSuccess();
				}
			} else {
				r.setIsSuccess();
			}
			return r;
		}
		
		Long operatorId = baseUtilCustom.getUserId();
		AuthRole newPO = new AuthRole();
		newPO.setId(snowFlake.getNextId());
		newPO.setCreateBy(operatorId);
		newPO.setAuthId(authId);
		newPO.setRoleId(roleId);
		int insertCount = authRoleMapper.insertSelective(newPO);
		if(insertCount > 0) {
			r.setIsSuccess();
		}
		
		return r;
	}
	
	private CommonResultCX canEditAuthRole(Long authId, Long roleId) {
		CommonResultCX r = authService.canEditUserAuth(authId);
		if(r.isFail()) {
			return r;
		}
		
		FindRolesDTO getRolsDTO = new FindRolesDTO();
		getRolsDTO.setRolesIdList(Arrays.asList(roleId));
		FindRolesResult getRoleResult = roleService.getRolesByCondition(getRolsDTO);
		if(getRoleResult.isFail() || getRoleResult.getRoleList() == null || getRoleResult.getRoleList().isEmpty()) {
			r.failWithMessage(getRoleResult.getMessage());
			return r;
		}
		
		Roles role = getRoleResult.getRoleList().get(0);
		RolesType roleType = roleService.findRolesType(role);
		if(roleType == null) {
			r.failWithMessage("error param");
			return r;
		}
		
		boolean bigUserFlag = isBigUser();
		if(RolesType.SYS_ROLE.equals(roleType)) {
			if(!bigUserFlag) {
				r.failWithMessage("无权操作");
				return r;
			} else {
				r.setIsSuccess();
				return r;
			}
		}
		
		FindAuthsResult findAuthResult = authService.findAuthsByCondition(authId);
		if(findAuthResult.isFail() || findAuthResult.getAuthList() == null || findAuthResult.getAuthList().isEmpty()) {
			r.failWithMessage(findAuthResult.getMessage());
			return r;
		}
		Auth auth = findAuthResult.getAuthList().get(0);
		
		AuthTypeType authType = AuthTypeType.getType(auth.getAuthType());
		if(AuthTypeType.SYS_AUTH.equals(authType)) {
			if(!bigUserFlag || RolesType.ORG_ROLE.equals(roleType)) {
				r.failWithMessage("无权操作");
				return r;
			}
		} else if(AuthTypeType.ORG_AUTH.equals(authType)) {
			if(RolesType.SYS_ROLE.equals(roleType)) {
				r.failWithMessage("无权操作");
				return r;
			}
		}
		
		CommonResultCX validUserOrgResult = orgService.validUserOrg(auth.getBelongOrg());
		if(validUserOrgResult.isFail()) {
			r.failWithMessage(validUserOrgResult.getMessage());
			return r;
		}
		
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public FindRolesResult findRolesByCondition(FindRolesDTO dto) {
		FindRolesResult r = new FindRolesResult();
		
		FindAuthRoleDTO findAuthRoleDTO = new FindAuthRoleDTO();
		findAuthRoleDTO.setOrgIdList(dto.getBelongOrgIdList());
		if(dto.getAuthIdList() != null && !dto.getAuthIdList().isEmpty()) {
			findAuthRoleDTO.setAuthIdList(dto.getAuthIdList());
		}
		if(dto.getSysRoleTypeList() != null && !dto.getSysRoleTypeList().isEmpty()) {
			findAuthRoleDTO.setSysRoleTypeList(dto.getSysRoleTypeList());
		}
		if(dto.getOrgRoleTypeList() != null && !dto.getOrgRoleTypeList().isEmpty()) {
			findAuthRoleDTO.setOrgRoleTypeList(dto.getOrgRoleTypeList());
		}
		FindAuthRoleResult authRoleResult = findAuthRole(findAuthRoleDTO);
		
		if(!authRoleResult.isSuccess()) {
			r.addMessage(authRoleResult.getMessage());
			return r;
		}
		List<Long> tmpRoleIdList = authRoleResult.getAuthRoleList().stream().map(AuthRole::getRoleId).collect(Collectors.toList());
		
		FindRolesDTO getRoleDTO = new FindRolesDTO();
		getRoleDTO.setRolesIdList(tmpRoleIdList);
		FindRolesResult getRolesResult = roleService.getRolesByCondition(getRoleDTO);
		
		if(!getRolesResult.isSuccess()) {
			r.addMessage(getRolesResult.getMessage());
			return r;
		}
		
		r.setRoleList(getRolesResult.getRoleList());
		r.setIsSuccess();
		return r;
		
	}
}
