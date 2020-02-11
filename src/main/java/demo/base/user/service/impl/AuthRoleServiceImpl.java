package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.AuthRoleMapper;
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.AuthRoleExample;
import demo.base.user.pojo.po.AuthRoleExample.Criteria;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindAuthRoleResult;
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
		
		if((dto.getRoleIdList() == null && dto.getRoleIdList().size() < 1) && (dto.getRoleNameList() == null && dto.getRoleNameList().size() < 1)) {
			return r;
		}
		
		Set<Long> roleIdSet = new HashSet<Long>();
		if(dto.getRoleNameList() != null && dto.getRoleNameList().size() > 0) {
			List<Roles> roleList = roleService.getRolesByFuzzyNameFromRedis(dto.getRoleNameList());
			roleIdSet = roleList.stream().map(Roles::getRoleId).collect(Collectors.toSet());
		}
		
		AuthRoleExample example = new AuthRoleExample();
		Criteria c = example.createCriteria();
		if(dto.getRoleIdList() != null && dto.getRoleIdList().size() > 0) {
			roleIdSet.addAll(dto.getRoleIdList());
			c.andRoleIdIn(List.copyOf(roleIdSet));
		}
		
		List<AuthRole> authRoleList = authRoleMapper.selectByExample(example);
		r.setAuthRoleList(authRoleList);
		r.setIsSuccess();
		return r;
	}
	
	public List<AuthRole> selectByExample(AuthRoleExample example) {
		return authRoleMapper.selectByExample(example);
	}
	
	@Override
	public int deleteById(Long id) {
		AuthRole record = new AuthRole();
		record.setId(id);
		record.setIsDelete(true);
		return authRoleMapper.updateByPrimaryKeySelective(record);
	}
	
}
