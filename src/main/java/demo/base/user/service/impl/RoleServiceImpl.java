package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.RolesMapper;
import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.RolesExample;
import demo.base.user.pojo.po.RolesExample.Criteria;
import demo.base.user.pojo.result.FindRolesResult;
import demo.base.user.pojo.result.FindRolesVOResult;
import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.pojo.vo.RoleVO;
import demo.base.user.service.RoleService;
import demo.common.service.CommonService;

@Service
public class RoleServiceImpl extends CommonService implements RoleService {

	@Autowired
	private RolesMapper roleMapper;
	
	@Override
	public void __initBaseRole() {
		RolesExample example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		List<Roles> hadBaseRoleList = roleMapper.selectByExample(example);
		List<String> hadRoleNameList = hadBaseRoleList.stream().map(Roles::getRole).collect(Collectors.toList());
		Roles r = null;
		for(SystemRolesType rt : SystemRolesType.values()) {
			if(!hadRoleNameList.contains(rt.getName())) {
				r = new Roles();
				r.setRoleId(snowFlake.getNextId());
				r.setRole(rt.getName());
				r.setIsDelete(false);
				roleMapper.insertSelective(r);
			}
		}
		
		for(OrganzationRolesType rt : OrganzationRolesType.values()) {
			if(!hadRoleNameList.contains(rt.getName())) {
				r = new Roles();
				r.setRoleId(snowFlake.getNextId());
				r.setRole(rt.getName());
				r.setIsDelete(false);
				roleMapper.insertSelective(r);
			}
		}
	}

	@Override
	public Roles getBaseRoleByName(String roleName) {
		if(StringUtils.isBlank(roleName)) {
			return null;
		}
		SystemRolesType t = SystemRolesType.getRole(roleName);
		if(t == null ) {
			return null;
		}
		
		RolesExample example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false).andRoleEqualTo(roleName);
		List<Roles> roles = roleMapper.selectByExample(example);
		if(roles != null && roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}

	@Override
	public FindRolesResult getRolesByCondition(FindRolesDTO dto) {
		FindRolesResult r = new FindRolesResult();
		
		RolesExample example = new RolesExample();
		Criteria c = example.createCriteria();
		
		List<String> roleNameList = new ArrayList<String>();
		if(dto.getSysRoleTypeList() != null && !dto.getSysRoleTypeList().isEmpty()){
			for(SystemRolesType roltType : dto.getSysRoleTypeList()) {
				roleNameList.add(roltType.getName());
			}
		}
		if(dto.getOrgRoleTypeList() != null && !dto.getOrgRoleTypeList().isEmpty()){
			for(OrganzationRolesType roltType : dto.getOrgRoleTypeList()) {
				roleNameList.add(roltType.getName());
			}
		}
		if(dto.getRoleNameList() != null && !dto.getRoleNameList().isEmpty()) {
			for(String roleName : dto.getRoleNameList()) {
				roleNameList.add(roleName);
			}
		}
		if(!roleNameList.isEmpty()) {
			c.andRoleIn(roleNameList);
		}
		if(dto.getRolesIdList() != null && !dto.getRolesIdList().isEmpty()) {
			c.andRoleIdIn(dto.getRolesIdList());
		}
		
		r.setRoleList(roleMapper.selectByExample(example));
		r.setIsSuccess();
		return r;
	}

	@Override
	public FindRolesVOResult findRolesVO(FindRolesDTO dto) {
		FindRolesVOResult r = new FindRolesVOResult();
		
		FindRolesResult poResult = getRolesByCondition(dto);
		if(poResult.isFail()) {
			r.addMessage(poResult.getMessage());
			return r;
		}
		
		List<Roles> poList = poResult.getRoleList();
		if(poList == null || poList.isEmpty()) {
			r.setIsSuccess();
			return r;
		}
		
		List<RoleVO> voList = new ArrayList<RoleVO>();
		for(Roles role : poList) {
			voList.add(buildVO(role));
		}
		r.setRoleVOList(voList);
		r.setIsSuccess();
		
		return r;
	}
	
	@Override
	public Roles findRoleByName(String roleName) {
		if(StringUtils.isBlank(roleName)) {
			return null;
		}
		
		RolesExample example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false).andRoleEqualTo(roleName);
		List<Roles> roleList = roleMapper.selectByExample(example);
		if(roleList == null || roleList.isEmpty()) {
			return null;
		}
		
		return roleList.get(0);
	}
	
	private RoleVO buildVO(Roles po) {
		RoleVO vo = new RoleVO();
		vo.setPk(encryptId(po.getRoleId()));
		vo.setRoleName(po.getRole());
		return vo;
	}

	@Override
	public FindRolesVOResult findSysRoles() {
		FindRolesDTO dto = new FindRolesDTO();
		List<String> sysRoleNameList = new ArrayList<String>();
		for(SystemRolesType r : SystemRolesType.values()) {
			sysRoleNameList.add(r.getName());
		}
		dto.setRoleNameList(sysRoleNameList);
		
		return findRolesVO(dto);
	}
	
	@Override
	public FindRolesVOResult findOrgRoles() {
		FindRolesDTO dto = new FindRolesDTO();
		List<String> orgRoleNameList = new ArrayList<String>();
		for(OrganzationRolesType r : OrganzationRolesType.values()) {
			orgRoleNameList.add(r.getName());
		}
		dto.setRoleNameList(orgRoleNameList);
		
		return findRolesVO(dto);
	}

	@Override
	public RolesType findRolesType(Roles role) {
		if(role == null || StringUtils.isBlank(role.getRole())) {
			return null;
		}
		
		SystemRolesType sysRoleType = SystemRolesType.getRole(role.getRole());
		if(sysRoleType != null) {
			return RolesType.SYS_ROLE;
		}
		
		OrganzationRolesType orgRoleType = OrganzationRolesType.getRole(role.getRole());
		if(orgRoleType != null) {
			return RolesType.ORG_ROLE;
		}
		
		return null;
	}
}
