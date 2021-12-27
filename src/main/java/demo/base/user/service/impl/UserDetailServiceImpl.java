package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.RolesMapper;
import demo.base.user.mapper.UserRolesMapper;
import demo.base.user.mapper.UsersDetailMapper;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.RolesExample;
import demo.base.user.pojo.po.UserRoles;
import demo.base.user.pojo.po.UserRolesExample;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.po.UsersDetailExample;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.UserDetailService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.service.CommonService;

@Service
public class UserDetailServiceImpl extends CommonService implements UserDetailService {

	@Autowired
	private UsersDetailMapper userDetailMapper;
	@Autowired
	private UserRolesMapper userRoleMapper;
	@Autowired
	private RolesMapper roleMapper;

	@Override
	public boolean isNicknameExists(String nickname) {
		if (StringUtils.isBlank(nickname)) {
			return true;
		}
		return (userDetailMapper.isNickNameExists(nickname) > 0);
	}

	@Override
	public CommonResultCX ensureActiveEmail(String email) {
		CommonResultCX r = new CommonResultCX();

		UsersDetailExample userDetailExample = new UsersDetailExample();
		userDetailExample.createCriteria().andEmailEqualTo(email);
		List<UsersDetail> userDetailList = userDetailMapper.selectByExample(userDetailExample);
		if (userDetailList == null || userDetailList.size() < 1) {
			return r;
		}

		List<Long> userIdList = userDetailList.stream().map(UsersDetail::getUserId).collect(Collectors.toList());

		UserRolesExample userRoleExample = new UserRolesExample();
		userRoleExample.createCriteria().andIsDeleteEqualTo(false).andUserIdIn(userIdList);
		List<UserRoles> userRoleList = userRoleMapper.selectByExample(userRoleExample);
		List<Long> roleIdList = userRoleList.stream().map(UserRoles::getRoleId).collect(Collectors.toList());
		
		if(roleIdList == null || roleIdList.isEmpty()) {
			return r;
		}
		
		RolesExample rolesExample = new RolesExample();
		rolesExample.createCriteria().andIsDeleteEqualTo(false).andRoleIdIn(roleIdList);
		List<Roles> roles = roleMapper.selectByExample(rolesExample);
		
		if(roles == null || roles.isEmpty()) {
			return r;
		}
		
		String rolesStr = roles.toString();
		
		r.setSuccess(rolesStr.contains(SystemRolesType.ROLE_USER_ACTIVE.getName()));
		return r;
	}

	@Override
	public CommonResultCX ensureActiveMobile(Long mobile) {
		CommonResultCX r = new CommonResultCX();

		UsersDetailExample userDetailExample = new UsersDetailExample();
		userDetailExample.createCriteria().andMobileEqualTo(mobile);
		List<UsersDetail> userDetailList = userDetailMapper.selectByExample(userDetailExample);
		if (userDetailList == null || userDetailList.size() < 1) {
			return r;
		}

		List<Long> userIdList = userDetailList.stream().map(UsersDetail::getUserId).collect(Collectors.toList());

		UserRolesExample userRoleExample = new UserRolesExample();
		userRoleExample.createCriteria().andIsDeleteEqualTo(false).andUserIdIn(userIdList);
		List<UserRoles> userRoleList = userRoleMapper.selectByExample(userRoleExample);
		List<Long> roleIdList = userRoleList.stream().map(UserRoles::getRoleId).collect(Collectors.toList());
		
		RolesExample rolesExample = new RolesExample();
		rolesExample.createCriteria().andIsDeleteEqualTo(false).andRoleIdIn(roleIdList);
		List<Roles> roles = roleMapper.selectByExample(rolesExample);
		
		if(roles == null || roles.isEmpty()) {
			return r;
		}
		
		String rolesStr = roles.toString();
		
		r.setSuccess(rolesStr.contains(SystemRolesType.ROLE_USER_ACTIVE.getName()));
		return r;
	}

	@Override
	public List<UsersDetail> findByEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return new ArrayList<UsersDetail>();
		}

		UsersDetailExample example = new UsersDetailExample();
		example.createCriteria().andEmailEqualTo(email);
		return userDetailMapper.selectByExample(example);
	}

	@Override
	public UsersDetail findById(Long id) {
		return userDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public int modifyRegistEmail(String email, Long userId) {
		return userDetailMapper.modifyRegistEmail(email, userId);
	}

	@Override
	public int insertSelective(UsersDetail newUserDetail) {
		return userDetailMapper.insertSelective(newUserDetail);
	}
}
