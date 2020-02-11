package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.user.mapper.AuthMapper;
import demo.base.user.mapper.RolesMapper;
import demo.base.user.mapper.UsersDetailMapper;
import demo.base.user.mapper.UsersMapper;
import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.base.user.pojo.dto.FindUserByConditionDTO;
import demo.base.user.pojo.dto.OtherUserInfoDTO;
import demo.base.user.pojo.dto.ResetFailAttemptDTO;
import demo.base.user.pojo.dto.UserAttemptQuerayDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.po.UsersDetailExample;
import demo.base.user.pojo.po.UsersExample;
import demo.base.user.pojo.po.UsersExample.Criteria;
import demo.base.user.pojo.result.FindUserByConditionResult;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.pojo.type.UserPrivateLevelType;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UsersService;
import demo.baseCommon.pojo.type.GenderType;
import demo.baseCommon.service.CommonService;
import demo.tool.service.ValidRegexToolService;

/**
 * @author Acorn 2017年4月13日
 */
@Service
public class UsersServiceImpl extends CommonService implements UsersService {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private AuthMapper authMapper;
	@Autowired
	private RolesMapper roleMapper;
	@Autowired
	private UsersDetailMapper usersDetailMapper;
	@Autowired
	private ValidRegexToolService validRegexToolService;
	
	@Override
	public int insertFailAttempts(String userName) {
		int insertCount = usersMapper.insertFailAttempts(userName);
		if(insertCount == 0) {
			return insertCount;
		}
		UserAttemptQuerayDTO param = new UserAttemptQuerayDTO();
		param.setUserName(userName);
		int maxAttempts = Integer.parseInt(constantService.getValByName(SystemConstantStore.maxAttempts));
		if (getUserAttempts(param).size() >= maxAttempts) {
			usersMapper.lockUserWithAttempts(userName);
		} 

		return insertCount;
		
	}

	@Override
	public int countAttempts(String userName) {
		if(StringUtils.isBlank(userName)) {
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
		if(StringUtils.isBlank(userName)) {
			return 0;
		}
		ResetFailAttemptDTO param = new ResetFailAttemptDTO();
		param.setUserName(userName);
		return usersMapper.resetFailAttempts(param);
	}

	@Override
	public Long getUserIdByUserName(String userName) {
		if(StringUtils.isBlank(userName)) {
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
		MyUserPrincipal myUserDetail = new MyUserPrincipal();
		Users user = usersMapper.findUserByUserName(userName);
		if(user == null) {
			return myUserDetail;
		}
		
		myUserDetail.setUser(user);
		
		List<Auth> auths = findAuthsByUserId(user.getUserId());
		List<Long> authIdList = auths.stream().map(Auth::getId).collect(Collectors.toList());
		List<Roles> roles = findRolesByAuthIdList(authIdList);
		
		List<String> rolesStr = roles.stream().map(Roles::getRole).collect(Collectors.toList());
		myUserDetail.setRoles(rolesStr);
		
		List<String> authStr = auths.stream().map(Auth::getAuthName).collect(Collectors.toList());
		myUserDetail.setAuths(authStr);
		
		return myUserDetail;
	}

	@Override
	public UsersDetailVO findUserDetail(Long userId) {
		if(userId == null) {
			return new UsersDetailVO();
		}
		
		return buildUserDetailVOByPO(usersDetailMapper.selectByPrimaryKey(userId), true);
	}
	
	@Override
	public String findHeadImageUrl(Long userId) {
		if(userId == null) {
			return null;
		}
		return usersDetailMapper.findHeadImage(userId);
	}
	
	@Override
	public UsersDetailVO findOtherUserDetail(OtherUserInfoDTO param) {
		if(StringUtils.isBlank(param.getNickName()) || StringUtils.isBlank(param.getPk())) {
			return new UsersDetailVO();
		}
		UsersDetailExample example = new UsersDetailExample();
		example.createCriteria().andNickNameEqualTo(param.getNickName());
		List<UsersDetail> udList = usersDetailMapper.selectByExample(example);
		if(udList == null || udList.size() < 1) {
			return new UsersDetailVO();
		}
 		return buildUserDetailVOByPO(udList.get(0), false);
	}
	
	private UsersDetailVO buildUserDetailVOByPO(UsersDetail ud) {
		return buildUserDetailVOByPO(ud, false);
	}
	
	private UsersDetailVO buildUserDetailVOByPO(UsersDetail ud, boolean myDetail) {
		UsersDetailVO vo = new UsersDetailVO();
		if(ud == null) {
		    return vo;
		}
		
		vo.setNickName(ud.getNickName());

		UserPrivateLevelType privateLevel = UserPrivateLevelType.p1;
		if(ud.getPrivateLevel() != null && UserPrivateLevelType.getType(ud.getPrivateLevel()) != null) {
			privateLevel = UserPrivateLevelType.getType(ud.getPrivateLevel());
		}
		
		if(myDetail || privateLevel.equals(UserPrivateLevelType.p3)) {
			vo.setEmail(ud.getEmail());
			if(GenderType.male.getCode().equals(ud.getGender())) {
				vo.setGender(GenderType.male.getName());
			} else if(GenderType.female.getCode().equals(ud.getGender())) {
				vo.setGender(GenderType.female.getName());
			} else {
				vo.setGender(GenderType.unknow.getName());
			}
			vo.setLastLoginTime(localDateTimeHandler.localDateTimeToDate(ud.getLastLoginTime()));
			vo.setMobile(ud.getMobile());
			vo.setQq(ud.getQq());
			if(myDetail) {
				vo.setPrivateLevel(ud.getPrivateLevel());
				vo.setReservationInformation(ud.getReservationInformation());
			}
		} else {
			vo.setPrivateMessage("用户比较害羞,不想让别人看见.");
		}
		
		return vo;
	}

	@Override
	public List<Long> findUserIdListByAuthId(Long authId) {
		if (authId == null) {
			return new ArrayList<Long>();
		}

		return authMapper.findUserIdByAuthId(authId);
	}
	
	@Override
	public List<Auth> findAuthsByUserId(Long userId) {
		return authMapper.findAuthsByUserId(userId);
	}
	
	@Override
	public List<Roles> findRolesByAuthId(Long authId) {
		return roleMapper.findRolesByAuthId(authId);
	}
	
	@Override
	public List<Roles> findRolesByAuthIdList(List<Long> authIdList) {
		if(authIdList == null || authIdList.size() < 1) {
			return new ArrayList<Roles>();
		}
		return roleMapper.findRolesByAuthIdList(authIdList);
	}

	@Override
	public FindUserByConditionResult findUserByCondition(FindUserByConditionDTO dto) {
		FindUserByConditionResult r = new FindUserByConditionResult();
		List<UsersDetailVO> userVOList = new ArrayList<UsersDetailVO>();
		List<Long> userIdList = new ArrayList<Long>();
		String validUserName = null;
		
		if(validRegexToolService.validNormalUserName(dto.getUserName())) {
			validUserName = dto.getUserName();
		}
		
		if(dto.getUserId() == null && validUserName == null && StringUtils.isBlank(dto.getUserNickName())) {
			r.setUserList(userVOList);
			return r;
		}
		
		if(dto.getUserId() != null) {
			userIdList.add(dto.getUserId());
			
		} else if (validUserName != null) {
			UsersExample userExample = new UsersExample();
			Criteria userCriteria = userExample.createCriteria();
			if(dto.getAccountNonExpired() != null) {
				userCriteria.andAccountNonExpiredEqualTo(dto.getAccountNonExpired());
			}
			if(dto.getAccountNonLocked() != null) {
				userCriteria.andAccountNonLockedEqualTo(dto.getAccountNonExpired());
			}
			if(dto.getCredentialsNonExpired() != null) {
				userCriteria.andCredentialsNonExpiredEqualTo(dto.getAccountNonExpired());
			}
			if(dto.getUserId() != null) {
				userCriteria.andUserIdEqualTo(dto.getUserId());
			}
			if(validUserName != null) {
				userCriteria.andUserNameLike("%" + validUserName + "%");
			}
			
			List<Users> userList = usersMapper.selectByExample(userExample);
			userIdList.addAll(userList.stream().map(Users::getUserId).collect(Collectors.toList()));
		}
		
		
		List<UsersDetail> userDetailList = null;
		UsersDetailExample userDetailExample = new UsersDetailExample();
		demo.base.user.pojo.po.UsersDetailExample.Criteria userDetailCriteria = userDetailExample.createCriteria();
		if(StringUtils.isNotBlank(dto.getUserNickName())) {
			userDetailCriteria.andNickNameLike("%" + dto.getUserNickName() + "%");
		}
		if(userIdList.size() > 0) {
			userDetailCriteria.andUserIdIn(userIdList);
		}
		userDetailList = usersDetailMapper.selectByExample(userDetailExample);
		
		for(UsersDetail u : userDetailList) {
			userVOList.add(buildUserDetailVOByPO(u));
		}
		r.setUserList(userVOList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public ModelAndView findUserInfo() {
		ModelAndView view = new ModelAndView("userJSP/userInfo");
		
		if(!baseUtilCustom.isLoginUser()) {
			view.setViewName(LoginUrlConstant.login);
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
		
		if(!baseUtilCustom.getRoles().contains(RolesType.ROLE_USER_ACTIVE.getName())) {
			if(!baseUtilCustom.getAuthDetail().containsKey("modifyRegistMail")) {
				view.addObject("notActive", "notActive");
			}
		}
		
		return view;
	}
}
