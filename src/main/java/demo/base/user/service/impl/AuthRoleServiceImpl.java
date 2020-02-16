package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.AuthRoleMapper;
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.AuthRoleExample;
import demo.base.user.pojo.po.AuthRoleExample.Criteria;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindRolesResult;
import demo.base.user.service.AuthRoleService;
import demo.base.user.service.RoleService;
import demo.baseCommon.service.CommonService;

@Service
public class AuthRoleServiceImpl extends CommonService implements AuthRoleService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthRoleServiceImpl.class);

	@Autowired
	private AuthRoleMapper authRoleMapper;
	@Autowired
	private RoleService roleService;

	@Override
	public Long insertAuthRole(Long authId, Long roleId, Long creatorId) {
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
		
		if(dto.getOrgIdList() == null || dto.getOrgIdList().size() < 1) {
			r.failWithMessage("请指定所属机构");
			return r;
		}
		
		AuthRoleExample example = new AuthRoleExample();
		Criteria criteria = example.createCriteria();
		
		if(dto.getAuthIdList() != null && dto.getAuthIdList().size() > 0) {
			criteria.andAuthIdIn(dto.getAuthIdList());
		}
		
		if((dto.getRoleNameList() != null && !dto.getRoleNameList().isEmpty()) 
				|| dto.getRoleIdList() != null && !dto.getRoleIdList().isEmpty()) {
			FindRolesDTO getRolesDTO = new FindRolesDTO();
			getRolesDTO.setBelongOrgIdList(dto.getOrgIdList());
			if(dto.getRoleNameList() != null && !dto.getRoleNameList().isEmpty()) {
				getRolesDTO.setRoleNameList(dto.getRoleNameList());
			}
			if(dto.getRoleIdList() != null && !dto.getRoleIdList().isEmpty()) {
				getRolesDTO.setRolesIdList(dto.getRoleIdList());
			}
			FindRolesResult findRolesResult = roleService.getRolesByCondition(getRolesDTO);
			if(!findRolesResult.isSuccess()) {
				r.addMessage(findRolesResult.getMessage());
				return r;
			}
			List<Roles> roleList = findRolesResult.getRoleList();
			criteria.andRoleIdIn(roleList.stream().map(Roles::getRoleId).collect(Collectors.toList()));
		}
		
		List<AuthRole> authRoleList = authRoleMapper.selectByExample(example);
		r.setAuthRoleList(authRoleList);
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public int deleteById(Long id) {
		AuthRole record = new AuthRole();
		record.setId(id);
		record.setIsDelete(true);
		return authRoleMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public FindRolesResult findRolesByCondition(FindRolesDTO dto) {
		FindRolesResult r = new FindRolesResult();
		
		if(dto.getBelongOrgIdList() == null || dto.getBelongOrgIdList().isEmpty()) {
			r.failWithMessage("请指定所属机构");
			return r;
		}
		
		FindAuthRoleDTO findAuthRoleDTO = new FindAuthRoleDTO();
		findAuthRoleDTO.setOrgIdList(dto.getBelongOrgIdList());
		if(dto.getAuthIdList() != null && !dto.getAuthIdList().isEmpty()) {
			findAuthRoleDTO.setAuthIdList(dto.getAuthIdList());
		}
		if(dto.getRolesIdList() != null && !dto.getRolesIdList().isEmpty()) {
			findAuthRoleDTO.setRoleIdList(dto.getRolesIdList());
		}
		if(dto.getRoleNameList() != null && !dto.getRoleNameList().isEmpty()) {
			findAuthRoleDTO.setRoleNameList(dto.getRoleNameList());
		}
		FindAuthRoleResult authRoleResult = findAuthRole(findAuthRoleDTO);
		
		if(!authRoleResult.isSuccess()) {
			r.addMessage(authRoleResult.getMessage());
			return r;
		}
		List<Long> tmpRoleIdList = authRoleResult.getAuthRoleList().stream().map(AuthRole::getRoleId).collect(Collectors.toList());
		
		FindRolesDTO getRoleDTO = new FindRolesDTO();
		getRoleDTO.setRolesIdList(tmpRoleIdList);
		FindRolesResult getRolesResult = roleService.getRolesByCondition(getRoleDTO );
		
		if(!getRolesResult.isSuccess()) {
			r.addMessage(getRolesResult.getMessage());
			return r;
		}
		
		r.setRoleList(getRolesResult.getRoleList());
		return r;
		
	}
}
