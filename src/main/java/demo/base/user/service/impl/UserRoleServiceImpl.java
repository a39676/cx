package demo.base.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.UserRolesMapper;
import demo.base.user.pojo.constant.UserConstant;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.UserRoles;
import demo.base.user.service.UserRoleService;
import demo.common.service.CommonService;

@Service
public class UserRoleServiceImpl extends CommonService implements UserRoleService {

	@Autowired
	private UserRolesMapper userRoleMapper;
	@Autowired
	private UserRoleConstantService userRoleConstantService;
	
	@Override
	public void insertBaseUserAuth(Long userId) {
		List<Roles> baseUserRoles = userRoleConstantService.getBaseUserRoles();
		UserRoles po = null;
		for(Roles r : baseUserRoles) {
			po = new UserRoles();
			po.setCreateBy(UserConstant.noneUserId);
			po.setRoleId(r.getRoleId());
			po.setUserId(userId);
			po.setId(snowFlake.getNextId());
			try {
				userRoleMapper.insertSelective(po);
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public void insertActiveUserAuth(Long userId) {
		List<Roles> baseUserRoles = userRoleConstantService.getActiveUserRoles();
		UserRoles po = null;
		for(Roles r : baseUserRoles) {
			po = new UserRoles();
			po.setCreateBy(UserConstant.noneUserId);
			po.setRoleId(r.getRoleId());
			po.setUserId(userId);
			po.setId(snowFlake.getNextId());
			try {
				userRoleMapper.insertSelective(po);
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public void insertSuperAdminAuth(Long userId) {
		List<Roles> baseUserRoles = userRoleConstantService.getSuperAdminRoles();
		UserRoles po = null;
		for(Roles r : baseUserRoles) {
			po = new UserRoles();
			po.setCreateBy(UserConstant.noneUserId);
			po.setRoleId(r.getRoleId());
			po.setUserId(userId);
			po.setId(snowFlake.getNextId());
			try {
				userRoleMapper.insertSelective(po);
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void insertStudentUserAuth(Long userId) {
		List<Roles> studentUserRoles = userRoleConstantService.getStudentUserRoles();
		UserRoles po = null;
		for(Roles r : studentUserRoles) {
			po = new UserRoles();
			po.setCreateBy(UserConstant.noneUserId);
			po.setRoleId(r.getRoleId());
			po.setUserId(userId);
			po.setId(snowFlake.getNextId());
			try {
				userRoleMapper.insertSelective(po);
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		}
	}
	
	
}
