package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.type.GenderType;
import demo.base.organizations.pojo.dto.FindUserControlOrgDTO;
import demo.base.organizations.pojo.result.FindUserControlOrgResult;
import demo.base.organizations.service.OrganizationService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.user.mapper.UsersDetailMapper;
import demo.base.user.mapper.UsersMapper;
import demo.base.user.pojo.bo.FindUserAuthBO;
import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.dto.FindUserByConditionDTO;
import demo.base.user.pojo.dto.OtherUserInfoDTO;
import demo.base.user.pojo.dto.ResetFailAttemptDTO;
import demo.base.user.pojo.dto.UserAttemptQuerayDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.UserAuth;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.po.UsersDetailExample;
import demo.base.user.pojo.po.UsersExample;
import demo.base.user.pojo.po.UsersExample.Criteria;
import demo.base.user.pojo.result.FindRolesResult;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.result.FindUserByConditionResult;
import demo.base.user.pojo.type.UserPrivateLevelType;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.AuthRoleService;
import demo.base.user.service.UserAuthService;
import demo.base.user.service.UsersService;
import demo.common.service.CommonService;
import demo.tool.service.ValidRegexToolService;

/**
 * @author Acorn 2017年4月13日
 */
@Service
public class UsersServiceImpl extends CommonService implements UsersService {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersDetailMapper usersDetailMapper;
	@Autowired
	private ValidRegexToolService validRegexToolService;
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private AuthRoleService authRoleService;
	@Autowired
	private OrganizationService orgService;
	
	@Override
	public int insertFailAttempts(String userName) {
		int insertCount = usersMapper.insertFailAttempts(userName);
		if(insertCount == 0) {
			return insertCount;
		}
		UserAttemptQuerayDTO param = new UserAttemptQuerayDTO();
		param.setUserName(userName);
		int maxAttempts = Integer.parseInt(systemConstantService.getSysValByName(SystemConstantStore.maxAttempts));
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
		MyUserPrincipal myUserPrincipal = new MyUserPrincipal();
		Users user = usersMapper.findUserByUserName(userName);
		if(user == null) {
			return myUserPrincipal;
		}
		
		myUserPrincipal.setUser(user);
		
		UsersDetail userDetail = usersDetailMapper.selectByPrimaryKey(user.getUserId());
		if(userDetail != null) {
			myUserPrincipal.setNickName(userDetail.getNickName());
			myUserPrincipal.setEmail(userDetail.getEmail());
		}
		
		FindUserAuthBO findUserAuthBO = new FindUserAuthBO();
		findUserAuthBO.setUserId(user.getUserId());
		FindUserAuthResult authResult = userAuthService.findUserAuth(findUserAuthBO);
		if(!authResult.isSuccess()) {
			return myUserPrincipal;
		}
		List<Auth> auths = authResult.getAuthList();
		myUserPrincipal.setAuths(auths);
		
		FindUserControlOrgDTO findUserControlOrgDTO = new FindUserControlOrgDTO();
		findUserControlOrgDTO.setUserId(user.getUserId());
		FindUserControlOrgResult findUserControllOrgResult = orgService.findUserControlOrg(findUserControlOrgDTO);
		if(findUserControllOrgResult.isSuccess()) {
			myUserPrincipal.setSuperManagerOrgList(findUserControllOrgResult.getSuperManagerOrgList());
			myUserPrincipal.setControllerOrganizations(findUserControllOrgResult.getControllOrgList());
			myUserPrincipal.setSubOrganizations(findUserControllOrgResult.getSubOrgList());
		}
		
		List<Long> authIdList = auths.stream().map(Auth::getId).collect(Collectors.toList());
		FindRolesDTO findRolesDTO = new FindRolesDTO();
		findRolesDTO.setAuthIdList(authIdList);
		FindRolesResult rolesResult = authRoleService.findRolesByCondition(findRolesDTO );
		if(rolesResult.isSuccess()) {
			List<Roles> roles = rolesResult.getRoleList();
			List<String> rolesStr = roles.stream().map(Roles::getRole).collect(Collectors.toList());
			myUserPrincipal.setRoles(rolesStr);
		}
		
		return myUserPrincipal;
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
	
	private UsersDetailVO buildUserDetailVOByPO(UsersDetail po, boolean allDetail) {
		UsersDetailVO vo = new UsersDetailVO();
		if(po == null) {
		    return vo;
		}
		
		vo.setUserPk(encryptId(po.getUserId()));
		vo.setNickName(po.getNickName());

		UserPrivateLevelType privateLevel = UserPrivateLevelType.p1;
		if(po.getPrivateLevel() != null && UserPrivateLevelType.getType(po.getPrivateLevel()) != null) {
			privateLevel = UserPrivateLevelType.getType(po.getPrivateLevel());
		}
		
		if(allDetail || privateLevel.equals(UserPrivateLevelType.p3)) {
			vo.setEmail(po.getEmail());
			if(GenderType.male.getCode().equals(po.getGender())) {
				vo.setGender(GenderType.male.getName());
			} else if(GenderType.female.getCode().equals(po.getGender())) {
				vo.setGender(GenderType.female.getName());
			} else {
				vo.setGender(GenderType.unknow.getName());
			}
			vo.setLastLoginTime(localDateTimeHandler.localDateTimeToDate(po.getLastLoginTime()));
			vo.setMobile(po.getMobile());
			vo.setQq(po.getQq());
			if(allDetail) {
				vo.setPrivateLevel(po.getPrivateLevel());
				vo.setReservationInformation(po.getReservationInformation());
			}
		} else {
			vo.setPrivateMessage("用户比较害羞,不想让别人看见.");
		}
		
		return vo;
	}
	
	@Override
	public List<Users> findUserListByAuthId(Long authId) {
		if (authId == null) {
			return new ArrayList<Users>();
		}
		
		FindUserAuthBO findUserAuthBO = new FindUserAuthBO();
		findUserAuthBO.setAuthId(authId);
		FindUserAuthResult userAuthResult = userAuthService.findUserAuth(findUserAuthBO);
		if(!userAuthResult.isSuccess()) {
			return new ArrayList<Users>();
		}
		List<UserAuth> userAuthList = userAuthResult.getUserAuthList();
		if(userAuthList == null || userAuthList.isEmpty()) {
			return new ArrayList<Users>();
		}
		
		List<Long> userIdList = userAuthList.stream().map(UserAuth::getUserId).collect(Collectors.toList());

		UsersExample userExample = new UsersExample();
		userExample.createCriteria().andUserIdIn(userIdList);
		return usersMapper.selectByExample(userExample);
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
			r.setUserVOList(userVOList);
			return r;
		}
		
		if (validUserName != null || dto.getUserId() != null) {
			UsersExample userExample = new UsersExample();
			Criteria userCriteria = userExample.createCriteria();
			if(dto.getAccountNonExpired() != null) {
				userCriteria.andAccountNonExpiredEqualTo(dto.getAccountNonExpired());
			}
			if(dto.getAccountNonLocked() != null) {
				userCriteria.andAccountNonLockedEqualTo(dto.getAccountNonLocked());
			}
			if(dto.getCredentialsNonExpired() != null) {
				userCriteria.andCredentialsNonExpiredEqualTo(dto.getCredentialsNonExpired());
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
		// 至少需要 模糊昵称 || 用户ID范围, 才进行查询
		if(StringUtils.isNotBlank(dto.getUserNickName()) || userIdList.size() > 0) {
			UsersDetailExample userDetailExample = new UsersDetailExample();
			demo.base.user.pojo.po.UsersDetailExample.Criteria userDetailCriteria = userDetailExample.createCriteria();
			if(StringUtils.isNotBlank(dto.getUserNickName())) {
				userDetailCriteria.andNickNameLike("%" + dto.getUserNickName() + "%");
			}
			if(userIdList.size() > 0) {
				userDetailCriteria.andUserIdIn(userIdList);
			}
			userDetailList = usersDetailMapper.selectByExample(userDetailExample);
		} else {
			userDetailList = new ArrayList<UsersDetail>();
		}
		
		for(UsersDetail u : userDetailList) {
			userVOList.add(buildUserDetailVOByPO(u, true));
		}
		
		r.setUserVOList(userVOList);
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
		
		
		return view;
	}
}
