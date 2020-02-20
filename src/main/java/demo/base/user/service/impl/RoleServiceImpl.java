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
import demo.base.user.pojo.type.RolesType;
import demo.base.user.service.RoleService;
import demo.baseCommon.service.CommonService;

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
		for(RolesType rt : RolesType.values()) {
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
		RolesType t = RolesType.getRole(roleName);
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
		
		if(dto.getRoleTypeList() != null && dto.getRoleTypeList().size() > 0) {
			List<String> roleNameList = new ArrayList<String>();
			for(RolesType roltType : dto.getRoleTypeList()) {
				roleNameList.add(roltType.getName());
			}
			c.andRoleIn(roleNameList);
		}
		if(dto.getRolesIdList() != null && dto.getRolesIdList().size() > 0) {
			c.andRoleIdIn(dto.getRolesIdList());
		}
		
		r.setRoleList(roleMapper.selectByExample(example));
		r.setIsSuccess();
		return r;
	}
}
