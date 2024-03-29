package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.type.GenderType;
import demo.base.system.service.impl.SystemCommonService;
import demo.base.user.mapper.RolesMapper;
import demo.base.user.mapper.UserRolesMapper;
import demo.base.user.mapper.UsersDetailMapper;
import demo.base.user.mapper.UsersMapper;
import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.base.user.pojo.dto.FindUserByConditionDTO;
import demo.base.user.pojo.dto.OtherUserInfoDTO;
import demo.base.user.pojo.dto.ResetFailAttemptDTO;
import demo.base.user.pojo.dto.UserAttemptQuerayDTO;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.RolesExample;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.UserRoles;
import demo.base.user.pojo.po.UserRolesExample;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.po.UsersDetailExample;
import demo.base.user.pojo.po.UsersExample;
import demo.base.user.pojo.po.UsersExample.Criteria;
import demo.base.user.pojo.result.FindUserByConditionResult;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.pojo.type.UserPrivateLevelType;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UsersService;
import demo.tool.other.service.ValidRegexToolService;

/**
 * @author Acorn 2017年4月13日
 */
@Service
public class UsersServiceImpl extends SystemCommonService implements UsersService {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UserRolesMapper userRoleMapper;
	@Autowired
	private RolesMapper roleMapper;
	@Autowired
	private UsersDetailMapper usersDetailMapper;
	@Autowired
	private ValidRegexToolService validRegexToolService;

	@Override
	public int insertFailAttempts(String userName) {
		int insertCount = usersMapper.insertFailAttempts(userName);
		if (insertCount == 0) {
			return insertCount;
		}
		UserAttemptQuerayDTO param = new UserAttemptQuerayDTO();
		param.setUserName(userName);
		int maxAttempts = systemOptionService.getMaxAttempts();
		if (getUserAttempts(param).size() >= maxAttempts) {
			usersMapper.lockUserWithAttempts(userName);
		}

		return insertCount;

	}

	@Override
	public int countAttempts(String userName) {
		if (StringUtils.isBlank(userName)) {
			return 0;
		}
		return usersMapper.countAttempts(userName);
	}

	@Override
	public int setLockeds(Users user) {
		return usersMapper.setLockeds(user);
	}

	@Override
	public int resetFailAttempts(String userName) {
		if (StringUtils.isBlank(userName)) {
			return 0;
		}
		ResetFailAttemptDTO param = new ResetFailAttemptDTO();
		param.setUserName(userName);
		return usersMapper.resetFailAttempts(param);
	}

	@Override
	public Long getUserIdByUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			return null;
		}
		return usersMapper.getUserIdByUserName(userName);
	}

	@Override
	public ArrayList<UserAttempts> getUserAttempts(UserAttemptQuerayDTO param) {
		ArrayList<UserAttempts> userAttemptsList = usersMapper.getUserAttempts(param);
		return userAttemptsList;
	}

	@Override
	public Users getUserbyUserName(String userName) {
		return usersMapper.findUserByUserName(userName);
	}

	@Override
	public MyUserPrincipal buildMyUserPrincipalByUserName(String userName) {
		MyUserPrincipal myUserPrincipal = new MyUserPrincipal();
		Users user = usersMapper.findUserByUserName(userName);
		if (user == null) {
			return myUserPrincipal;
		}

		myUserPrincipal.setUser(user);

		UsersDetail userDetail = usersDetailMapper.selectByPrimaryKey(user.getUserId());
		if (userDetail != null) {
			myUserPrincipal.setNickName(userDetail.getNickName());
			myUserPrincipal.setEmail(userDetail.getEmail());
		}

		UserRolesExample userRoleExample = new UserRolesExample();
		userRoleExample.createCriteria().andIsDeleteEqualTo(false).andUserIdEqualTo(user.getUserId());
		List<UserRoles> userRoleList = userRoleMapper.selectByExample(userRoleExample);
		List<Long> roleIdList = userRoleList.stream().map(UserRoles::getRoleId).collect(Collectors.toList());

		RolesExample roleExmaple = new RolesExample();
		roleExmaple.createCriteria().andIsDeleteEqualTo(false).andRoleIdIn(roleIdList);
		List<Roles> roleList = roleMapper.selectByExample(roleExmaple);

		List<String> rolesStr = roleList.stream().map(Roles::getRole).collect(Collectors.toList());
		myUserPrincipal.setRoles(rolesStr);

		return myUserPrincipal;
	}

	@Override
	public UsersDetailVO findUserDetail(Long userId) {
		if (userId == null) {
			return new UsersDetailVO();
		}

		return buildUserDetailVOByPO(usersDetailMapper.selectByPrimaryKey(userId), true);
	}

	@Override
	public String findHeadImageUrl(Long userId) {
		if (userId == null) {
			return null;
		}
		return usersDetailMapper.findHeadImage(userId);
	}

	@Override
	public UsersDetailVO findOtherUserDetail(OtherUserInfoDTO param) {
		if (StringUtils.isBlank(param.getNickName()) || StringUtils.isBlank(param.getPk())) {
			return new UsersDetailVO();
		}
		UsersDetailExample example = new UsersDetailExample();
		example.createCriteria().andNickNameEqualTo(param.getNickName());
		List<UsersDetail> udList = usersDetailMapper.selectByExample(example);
		if (udList == null || udList.size() < 1) {
			return new UsersDetailVO();
		}
		return buildUserDetailVOByPO(udList.get(0), false);
	}

	private UsersDetailVO buildUserDetailVOByPO(UsersDetail po, boolean allDetail) {
		UsersDetailVO vo = new UsersDetailVO();
		if (po == null) {
			return vo;
		}

		vo.setUserPk(systemOptionService.encryptId(po.getUserId()));
		vo.setNickName(po.getNickName());

		UserPrivateLevelType privateLevel = UserPrivateLevelType.p1;
		if (po.getPrivateLevel() != null && UserPrivateLevelType.getType(po.getPrivateLevel()) != null) {
			privateLevel = UserPrivateLevelType.getType(po.getPrivateLevel());
		}

		if (allDetail || privateLevel.equals(UserPrivateLevelType.p3)) {
			vo.setEmail(po.getEmail());
			if (GenderType.male.getCode().equals(po.getGender())) {
				vo.setGender(GenderType.male.getName());
			} else if (GenderType.female.getCode().equals(po.getGender())) {
				vo.setGender(GenderType.female.getName());
			} else {
				vo.setGender(GenderType.unknow.getName());
			}
			vo.setLastLoginTime(localDateTimeHandler.localDateTimeToDate(po.getLastLoginTime()));
			vo.setMobile(po.getMobile());
			vo.setQq(po.getQq());
			if (allDetail) {
				vo.setPrivateLevel(po.getPrivateLevel());
				vo.setReservationInformation(po.getReservationInformation());
			}
		} else {
			vo.setPrivateMessage("用户比较害羞,不想让别人看见.");
		}

		return vo;
	}

	@Override
	public FindUserByConditionResult findUserByCondition(FindUserByConditionDTO dto) {
		FindUserByConditionResult r = new FindUserByConditionResult();
		List<UsersDetailVO> userVOList = new ArrayList<UsersDetailVO>();
		List<Long> userIdList = new ArrayList<Long>();
		String validUserName = null;

		if (validRegexToolService.validNormalUserName(dto.getUserName())) {
			validUserName = dto.getUserName();
		}

		if (dto.getUserId() == null && validUserName == null && StringUtils.isBlank(dto.getUserNickName())) {
			r.setUserVOList(userVOList);
			return r;
		}

		if (validUserName != null || dto.getUserId() != null) {
			UsersExample userExample = new UsersExample();
			Criteria userCriteria = userExample.createCriteria();
			if (dto.getAccountNonExpired() != null) {
				userCriteria.andAccountNonExpiredEqualTo(dto.getAccountNonExpired());
			}
			if (dto.getAccountNonLocked() != null) {
				userCriteria.andAccountNonLockedEqualTo(dto.getAccountNonLocked());
			}
			if (dto.getCredentialsNonExpired() != null) {
				userCriteria.andCredentialsNonExpiredEqualTo(dto.getCredentialsNonExpired());
			}
			if (dto.getUserId() != null) {
				userCriteria.andUserIdEqualTo(dto.getUserId());
			}
			if (validUserName != null) {
				userCriteria.andUserNameLike("%" + validUserName + "%");
			}

			List<Users> userList = usersMapper.selectByExample(userExample);
			userIdList.addAll(userList.stream().map(Users::getUserId).collect(Collectors.toList()));
		}

		List<UsersDetail> userDetailList = null;
		// 至少需要 模糊昵称 || 用户ID范围, 才进行查询
		if (StringUtils.isNotBlank(dto.getUserNickName()) || userIdList.size() > 0) {
			UsersDetailExample userDetailExample = new UsersDetailExample();
			demo.base.user.pojo.po.UsersDetailExample.Criteria userDetailCriteria = userDetailExample.createCriteria();
			if (StringUtils.isNotBlank(dto.getUserNickName())) {
				userDetailCriteria.andNickNameLike("%" + dto.getUserNickName() + "%");
			}
			if (userIdList.size() > 0) {
				userDetailCriteria.andUserIdIn(userIdList);
			}
			userDetailList = usersDetailMapper.selectByExample(userDetailExample);
		} else {
			userDetailList = new ArrayList<UsersDetail>();
		}

		for (UsersDetail u : userDetailList) {
			userVOList.add(buildUserDetailVOByPO(u, true));
		}

		r.setUserVOList(userVOList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public ModelAndView findUserInfo() {
		ModelAndView view = new ModelAndView("userJSP/userInfo");

		if (!baseUtilCustom.isLoginUser()) {
			view.setViewName(LoginUrlConstant.LOGIN);
			return view;
		}

		Long userId = baseUtilCustom.getUserId();
		UsersDetailVO ud = findUserDetail(userId);

		view.addObject("nickName", ud.getNickName());
		view.addObject("email", ud.getEmail());
		view.addObject("qq", ud.getQq());
		view.addObject("gender", ud.getGender());
		view.addObject("mobile", ud.getMobile());
		view.addObject("reservationInformation", ud.getReservationInformation());

		return view;
	}

	@Override
	public List<Users> findUserListByRole(SystemRolesType systemRoleType) {
		RolesExample roleExample = new RolesExample();
		roleExample.createCriteria().andIsDeleteEqualTo(false).andRoleEqualTo(systemRoleType.getName());
		List<Roles> roleList = roleMapper.selectByExample(roleExample);
		if(roleList == null || roleList.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<Long> roleIdList = roleList.stream().map(Roles::getRoleId).collect(Collectors.toList());
		UserRolesExample userRoleExample = new UserRolesExample();
		userRoleExample.createCriteria().andIsDeleteEqualTo(false).andRoleIdIn(roleIdList);
		List<UserRoles> userRoleList = userRoleMapper.selectByExample(userRoleExample);
		if(userRoleList == null || userRoleList.isEmpty()) {
			return new ArrayList<>();
		}
		
		Set<Long> userIdSet = new HashSet<>();
		for(UserRoles ur : userRoleList) {
			userIdSet.add(ur.getUserId());
		}
		List<Long> userIdList = new ArrayList<>();
		userIdList.addAll(userIdSet);
		
		UsersExample userExample = new UsersExample();
		userExample.createCriteria().andAccountNonLockedEqualTo(true).andAccountNonExpiredEqualTo(true).andCredentialsNonExpiredEqualTo(true).andUserIdIn(userIdList);
		List<Users> userList = usersMapper.selectByExample(userExample);
		return userList;
	}
}
