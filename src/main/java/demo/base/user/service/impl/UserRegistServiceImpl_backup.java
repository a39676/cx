//package demo.base.user.service.impl;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringEscapeUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import demo.base.system.pojo.result.HostnameType;
//import demo.base.system.service.HostnameService;
//import demo.base.user.mapper.UserRegistMapper;
//import demo.base.user.mapper.UsersDetailMapper;
//import demo.base.user.mapper.UsersMapper;
//import demo.base.user.pojo.dto.ResetFailAttemptDTO;
//import demo.base.user.pojo.dto.UpdateDuplicateEmailDTO;
//import demo.base.user.pojo.dto.UserRegistDTO;
//import demo.base.user.pojo.po.Roles;
//import demo.base.user.pojo.po.Users;
//import demo.base.user.pojo.po.UsersDetail;
//import demo.base.user.pojo.po.UsersDetailExample;
//import demo.base.user.pojo.type.AuthType;
//import demo.base.user.pojo.type.RolesType;
//import demo.base.user.pojo.vo.__baseSuperAdminRegistVO;
//import demo.base.user.service.RoleService;
//import demo.base.user.service.UserAuthService;
//import demo.base.user.service.UserRegistService;
//import demo.baseCommon.pojo.result.CommonResultCX;
//import demo.baseCommon.pojo.type.GenderType;
//import demo.baseCommon.pojo.type.ResultTypeCX;
//import demo.baseCommon.service.CommonService;
//import demo.config.costom_component.CustomPasswordEncoder;
//import demo.tool.pojo.dto.SendMailDTO;
//import demo.tool.pojo.po.MailRecord;
//import demo.tool.pojo.result.SendRegistMailResult;
//import demo.tool.pojo.type.MailType;
//import demo.tool.service.MailService;
//import demo.tool.service.ValidRegexToolService;
//import net.sf.json.JSONObject;
//
//@Service
//public class UserRegistServiceImpl_backup extends CommonService implements UserRegistService {
//
//	@Autowired
//	private UserRegistMapper userRegistMapper;
//	@Autowired
//	private MailService mailService;
//	@Autowired
//	private UsersMapper usersMapper;
//	@Autowired
//	private UsersDetailMapper usersDetailMapper;
//	
//	@Autowired
//	private CustomPasswordEncoder passwordEncoder;
//	@Autowired
//	private UsersServiceImpl userService;
//	@Autowired
//	private RoleService roleService;
//	@Autowired
//	private UserAuthService userAuthService;
//	@Autowired
//	private ValidRegexToolService validRegexToolService;
//	@Autowired
//	private HostnameService hostnameService;
//	
//	
//	@Override
//	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
//	public CommonResultCX newUserRegist(UserRegistDTO registDTO, String ip, HttpServletRequest request) {
//		CommonResultCX result = new CommonResultCX();
//		UsersDetail userDetail = new UsersDetail();
//		JSONObject outputJson = new JSONObject();
//		boolean exceptionFlag = false;
//
//		if (!validRegexToolService.validNormalUserName(registDTO.getUserName())) {
//			outputJson.put("userName", "\"" + registDTO.getUserName() + "\" 账户名异常, 必须以英文字母开头,长度为6~16个字符.(只可输入英文字母及数字)");
//			exceptionFlag = true;
//		}
//
//		if (userRegistMapper.isUserExists(registDTO.getUserName()) > 0) {
//			outputJson.put("userName", "账户名已存在");
//			exceptionFlag = true;
//		}
//
//		String nickNameAfterEscapeHtml = StringEscapeUtils.escapeHtml(registDTO.getNickName());
//		if(StringUtils.isBlank(registDTO.getNickName())) {
//			outputJson.put("nickName", "请您一定要起给昵称...");
//			exceptionFlag = true;
//		} else if (nickNameAfterEscapeHtml.length() > 32) {
//			outputJson.put("nickName", "昵称太长了...");
//			exceptionFlag = true;
//		} else if (usersDetailMapper.isNickNameExists(nickNameAfterEscapeHtml) > 0) {
//			outputJson.put("nickName", "昵称重复了...");
//			exceptionFlag = true;
//		} else {
//			userDetail.setNickName(nickNameAfterEscapeHtml);
//		}
//
//		if (!validRegexToolService.validPassword(registDTO.getPwd())) {
//			outputJson.put("pwd", "密码长度不正确(8到16位)");
//			exceptionFlag = true;
//		}
//
//		if (!registDTO.getPwd().equals(registDTO.getPwdRepeat())) {
//			outputJson.put("pwdRepeat", "两次输入的密码不一致");
//			exceptionFlag = true;
//		}
//
//		if (!validRegexToolService.validEmail(registDTO.getEmail())) {
//			outputJson.put("email", "请输入正确的邮箱");
//			exceptionFlag = true;
//		} else {
//			/*
//			 * TODO
//			 * 为何此处有权限相关逻辑?
//			 */
//			EnsureActiveEmailDTO findActiveEmailParam = new EnsureActiveEmailDTO();
//			findActiveEmailParam.setEmail(registDTO.getEmail());
//			Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
//			if(r == null) {
//				outputJson.put("email", "系统繁忙中");
//				exceptionFlag = true;
//			} else {
//				findActiveEmailParam.setRoleId(r.getRoleId());
//			}
//			if (!ensureActiveEmail(registDTO.getEmail()).isSuccess()) {
//				outputJson.put("email", "邮箱已注册(忘记密码或用户名?可尝试找回)");
//				exceptionFlag = true;
//			} else {
//				userDetail.setEmail(registDTO.getEmail());
//			}
//		}
//
//		if (StringUtils.isNotBlank(registDTO.getMobile())) {
//			if (validRegexToolService.validMobile(registDTO.getMobile())) {
//				userDetail.setMobile(Long.parseLong(registDTO.getMobile()));
//			} else {
//				outputJson.put("mobile", "请填入正确的手机号,或留空");
//				exceptionFlag = true;
//			}
//		}
//
//		String reservationInfomationAfterEscapeHtml = StringEscapeUtils.escapeHtml(registDTO.getReservationInformation());
//		if (registDTO.getReservationInformation() != null
//				&& registDTO.getReservationInformation().replaceAll("\\s", "").length() > 0) {
//			if (reservationInfomationAfterEscapeHtml.length() > 32) {
//				outputJson.put("reservationInformation", "预留信息过长...32个字符以内..(中文算2个字符)");
//				exceptionFlag = true;
//			} else {
//				userDetail.setReservationInformation(reservationInfomationAfterEscapeHtml);
//			}
//		}
//
//		if (StringUtils.isNotBlank(registDTO.getQq())) {
//			if (validRegexToolService.validQQ(registDTO.getQq())) {
//				userDetail.setQq(Long.parseLong(registDTO.getQq()));
//			} else {
//				outputJson.put("qq", "QQ号格式异常...");
//				exceptionFlag = true;
//			}
//		}
//
//		if (exceptionFlag) {
//			result.normalFail();
//			result.setMessage(outputJson.toString());
//			return result;
//		}
//
//		if (registDTO.getGender() == null || GenderType.unknow.getCode().equals(registDTO.getGender())) {
//			userDetail.setGender(GenderType.unknow.getCode());
//		} else if (GenderType.unknow.getCode().equals(registDTO.getGender())) {
//			userDetail.setGender(GenderType.male.getCode());
//		} else {
//			userDetail.setGender(GenderType.female.getCode());
//		}
//
//		registDTO.setPwdd(registDTO.getPwd());
//		registDTO.setPwd(passwordEncoder.encode(registDTO.getPwd()));
//		Users user = createUserFromUserRegistParam(registDTO);
//		Long newUserId = snowFlake.getNextId();
//		user.setUserId(newUserId);
//		
//		userRegistMapper.insertNewUser(user);
//
//		userDetail.setUserId(newUserId);
//		userDetail.setRegistIp(numberUtil.ipToLong(ip));
//		usersDetailMapper.insertSelective(userDetail);
//		
//		HostnameType hostnameType = hostnameService.findHostname(request);
//		if(hostnameType == null) {
//			return result;
//		}
//		
//		SendMailDTO sendRegistMailDTO = new SendMailDTO();
//		sendRegistMailDTO.setHostName(hostnameType.getName());
//		sendRegistMailDTO.setNickName(registDTO.getNickName());
//		sendRegistMailDTO.setSendTo(registDTO.getEmail());
//		sendRegistMailDTO.setUserId(newUserId);
//		SendRegistMailResult sendRegistMailResult = mailService.sendRegistMail(sendRegistMailDTO);
//		if(!sendRegistMailResult.isSuccess()) {
//			return sendRegistMailResult;
//		}
//		
//		userAuthService.insertBaseUserAuth(newUserId, AuthType.USER);
//		
//		result.normalSuccess();
//		return result;
//	}
//	
//	@Override
//	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
//	public __baseSuperAdminRegistVO __baseSuperAdminRegist() {
//		UserRegistDTO param = new UserRegistDTO();
//		__baseSuperAdminRegistVO result = new __baseSuperAdminRegistVO();
//		UsersDetail userDetail = new UsersDetail();
//
//		param.setUserName("daven");
//		if (userRegistMapper.isUserExists(param.getUserName()) > 0) {
//			result.failWithMessage("账户名已存在");
//			return result;
//		}
//		
//		param.setNickName("DavenC");
//		String nickNameAfterEscapeHtml = StringEscapeUtils.escapeHtml(param.getNickName());
//		if (usersDetailMapper.isNickNameExists(nickNameAfterEscapeHtml) > 0) {
//			result.failWithMessage("昵称重复了...");
//			return result;
//		} else {
//			userDetail.setNickName(nickNameAfterEscapeHtml);
//		}
//
//		
//		param.setEmail("example@email.com");
//		if (!validRegexToolService.validEmail(param.getEmail())) {
//			result.failWithMessage("请输入正确的邮箱");
//			return result;
//		} else {
//			EnsureActiveEmailDTO findActiveEmailParam = new EnsureActiveEmailDTO();
//			findActiveEmailParam.setEmail(param.getEmail());
//			Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
//			if(r == null) {
//				result.failWithMessage("系统繁忙中");
//				return result;
//			} else {
//				findActiveEmailParam.setRoleId(r.getRoleId());
//			}
//			if (usersDetailMapper.isActiveEmailExists(findActiveEmailParam) > 0) {
//				result.failWithMessage("邮箱已注册(忘记密码或用户名?可尝试找回)");
//				return result;
//			} else {
//				userDetail.setEmail(param.getEmail());
//			}
//		}
//
//		param.setQq("1050092382");
//		userDetail.setQq(Long.parseLong(param.getQq()));
//
//		userDetail.setGender(GenderType.unknow.getCode());
//
//		param.setPwd("defaultPWD");
//		param.setPwd(passwordEncoder.encode(param.getPwd()));
//		Users user = createUserFromUserRegistParam(param);
//		Long newUserId = snowFlake.getNextId();
//		user.setUserId(newUserId);
//		
//		userRegistMapper.insertNewUser(user);
//
//		userDetail.setUserId(newUserId);
//		userDetail.setRegistIp(numberUtil.ipToLong("0.0.0.0"));
//		usersDetailMapper.insertSelective(userDetail);
//		
//		userAuthService.insertBaseUserAuth(newUserId, AuthType.SUPER_ADMIN);
//		
//		result.normalSuccess();
//		result.setNewSuperAdminId(newUserId);
//
//		return result;
//	}
//
//	@Override
//	public boolean isUserExists(String userName) {
//
//		boolean result = false;
//
//		int count = userRegistMapper.isUserExists(userName);
//
//		if (count > 0) {
//			result = true;
//		}
//
//		return result;
//	}
//	
//	private Users createUserFromUserRegistParam(UserRegistDTO param) {
//    	Users user = new Users();
//    	user.setUserName(param.getUserName());
//    	user.setPwd(param.getPwd());
//    	user.setPwdd(param.getPwdd());
//    	user.setAccountNonExpired(true);
//		user.setAccountNonLocked(true);
//		user.setCredentialsNonExpired(true);
//		user.setEnabled(true);
//    	return user;
//    }
//	
//	@Override
//	public CommonResultCX modifyRegistEmail(Long userId, String email) {
//		/*
//		 * 暂时不再主动发送激活邮件,改由用户发回激活邮件  2018-06-28
//		 */
////		CommonResult result = new CommonResult();
////		if(userId == null || !validEmail(email)) {
////			result.fillWithResult(ResultType.errorParam);
////			return result;
////		}
////		
////		int updateCount = usersDetailMapper.modifyRegistEmail(email, userId);
////		if(updateCount < 1) {
////			result.fillWithResult(ResultType.mailExists);
////			return result;
////		}
////		
////		result = resendRegistMail(userId);
////		return result;
//		
//		CommonResultCX result = new CommonResultCX();
//		if(userId == null || !validRegexToolService.validEmail(email)) {
//			result.fillWithResult(ResultTypeCX.errorParam);
//			return result;
//		}
//		
//		EnsureActiveEmailDTO p = new EnsureActiveEmailDTO();
//		p.setEmail(email);
//		Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
//		if(r == null) {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//		p.setRoleId(r.getRoleId());
//		if(usersDetailMapper.isActiveEmailExists(p) > 0) {
//			result.fillWithResult(ResultTypeCX.mailExists);
//			return result;
//		}
//		
//		int updateCount = usersDetailMapper.modifyRegistEmail(email, userId);
//		if(updateCount < 1) {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//		
//		return result;
//	}
//	
//	@Override
//	public CommonResultCX registActivation(String mailKey, String activeEMail) {
//		CommonResultCX result = new CommonResultCX();
//		if(StringUtils.isBlank(mailKey)) {
//			result.fillWithResult(ResultTypeCX.linkExpired);
//			return result;
//		}
//		
//		MailRecord mr = mailService.findMailByMailKeyMailType(mailKey, MailType.registActivation);
//
//		if (mr == null || mr.getUserId() == null) {
//			result.fillWithResult(ResultTypeCX.linkExpired);
//			return result;
//		}
//		
//		userAuthService.deleteUserAuth(mr.getUserId(), AuthType.USER);
//		userAuthService.insertBaseUserAuth(mr.getUserId(), AuthType.USER_ACTIVE);
//		
//		mailService.updateWasUsed(mr.getId());
//		UpdateDuplicateEmailDTO updateEmailParam = new UpdateDuplicateEmailDTO();
//		updateEmailParam.setNewEmail("");
//		updateEmailParam.setUserId(mr.getUserId());
//		updateEmailParam.setOldEmail(activeEMail);
//		usersDetailMapper.updateDuplicateEmail(updateEmailParam);
//		result.successWithMessage("账号已激活");
//		return result;
//	}
//	
//	@Override
//	public CommonResultCX resendRegistMail(Long userId) {
//		CommonResultCX result = new CommonResultCX();
//		if(userId == null) {
//			result.fillWithResult(ResultTypeCX.nullParam);
//		}
//		
//		UsersDetail ud = usersDetailMapper.selectByPrimaryKey(userId);
//		
//		if(ud == null || !validRegexToolService.validEmail(ud.getEmail())) {
//			result.fillWithResult(ResultTypeCX.nullParam);
//		}
//		
//		MailRecord mail = mailService.findRegistActivationUnusedByUserId(userId);
//		
//		if(mail == null || mail.getId() == null) {
//			SendMailDTO dto = new SendMailDTO();
//			dto.setHostName(hostName);
//			result = mailService.sendRegistMail(userId, ud.getEmail(), ud.getNickName());
//			
//		} else {
//			MailRecord oldMail = mail.get(0);
//			if(System.currentTimeMillis() - oldMail.getCreateTime().getTime() < 1000L * 60 
//					|| (oldMail.getResendTime() != null && System.currentTimeMillis() - oldMail.getResendTime().getTime() < 1000L * 60)) {
//				result.successWithMessage(oldMail.getMailKey());
//				return result;
//			}
//			result = mailService.resendRegistMail(userId, ud.getEmail(), ud.getNickName(), oldMail.getMailKey());
//		}
//		
//		return result;
//		return null;
//	}
//	
//	@Override
//	public CommonResultCX sendForgotPasswordMail(String email, HttpServletRequest request) {
//		CommonResultCX result = new CommonResultCX();
//		if(StringUtils.isBlank(email)) {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//		
//		if(!validRegexToolService.validEmail(email)) {
//			result.fillWithResult(ResultTypeCX.mailNotActivation);
//			return result;
//		}
//		EnsureActiveEmailDTO findActiveEmailParam = new EnsureActiveEmailDTO();
//		findActiveEmailParam.setEmail(email);
//		Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
//		if(r == null) {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//		findActiveEmailParam.setRoleId(r.getRoleId());
//		Long userId = usersDetailMapper.findUserIdByActivationEmail(findActiveEmailParam);
//		if(userId == null) {
//			result.fillWithResult(ResultTypeCX.mailNotActivation);
//			return result;
//		}
//		
//		result = mailService.sendForgotPasswordMail(userId, email, findHostNameFromRequst(request));
//		
//		if(!result.isSuccess()) {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//		
//		return result;
//	}
//	
//	@Override
//	public CommonResultCX sendForgotUsernameMail(String email, HttpServletRequest request) {
//		CommonResultCX result = new CommonResultCX();
//		if(StringUtils.isBlank(email)) {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//
//		if(!validRegexToolService.validEmail(email)) {
//			result.fillWithResult(ResultTypeCX.mailNotActivation);
//			return result;
//		}
//		
//		EnsureActiveEmailDTO findActiveEmailParam = new EnsureActiveEmailDTO();
//		findActiveEmailParam.setEmail(email.replaceAll("\\s", ""));
//		Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
//		if(r == null) {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//		findActiveEmailParam.setRoleId(r.getRoleId());
//		String userName = usersDetailMapper.findUserNameByActivationEmail(findActiveEmailParam);
//		if(StringUtils.isBlank(userName)) {
//			result.fillWithResult(ResultTypeCX.mailNotActivation);
//			return result;
//		}
//		
//		result = mailService.sendForgotUsernameMail(userName, email, findHostNameFromRequst(request));
//		
//		if(!result.isSuccess()) {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//		
//		return result;
//	}
//	
//	private CommonResultCX resetPassword(Long userId, String newPassword, String newPasswordRepeat) {
//		CommonResultCX result = new CommonResultCX();
//		if (!validRegexToolService.validPassword(newPassword)) {
//			result.fillWithResult(ResultTypeCX.invalidPassword);
//			return result;
//		}
//		
//		if(!newPassword.equals(newPasswordRepeat)) {
//			result.fillWithResult(ResultTypeCX.differentPassword);
//			return result;
//		}
//		
//		Users user = usersMapper.findUser(userId);
//		if(user == null) {
//			result.fillWithResult(ResultTypeCX.linkExpired);
//			return result;
//		}
//		
//		int resetCount = userRegistMapper.resetPassword(passwordEncoder.encode(newPassword), newPassword, user.getUserId());
//		if(resetCount > 0) {
//			result.fillWithResult(ResultTypeCX.resetPassword);
//			return result;
//		} else {
//			result.fillWithResult(ResultTypeCX.serviceError);
//			return result;
//		}
//	}
//	
//	@Override
//	public CommonResultCX resetPasswordByMailKey(String mailKey, String newPassword, String newPasswordRepeat) {
//		CommonResultCX result  = new CommonResultCX();
//		if(StringUtils.isBlank(mailKey)) {
//			result.fillWithResult(ResultTypeCX.linkExpired);
//			return result;
//		}
//		
//		MailRecord mr = mailService.findMailByMailKeyMailType(mailKey, MailType.forgotPassword);
//		if (mr == null || mr.getUserId() == null) {
//			result.fillWithResult(ResultTypeCX.linkExpired);
//			return result;
//		}
//		
//		result = resetPassword(mr.getUserId(), newPassword, newPasswordRepeat);
//		if(!result.isSuccess()) {
//			return result;
//		}
//		
//		mailService.updateWasUsed(mr.getId());
//		
//		ResetFailAttemptDTO resetFailAttemptParam = new ResetFailAttemptDTO();
//		resetFailAttemptParam.setUserId(mr.getUserId());
//		usersMapper.resetFailAttempts(resetFailAttemptParam);
//		
//		Users tmpUser = new Users();
//		tmpUser.setEnabled(true);
//		userService.setLockeds(tmpUser);
//		
//		result.successWithMessage("已成功重置密码");
//		return result;
//	}
//
//	@Override
//	public CommonResultCX resetPasswordByLoginUser(Long userId, String oldPassword, String newPassword, String newPasswordRepeat) {
//		CommonResultCX result = new CommonResultCX();
//		String encodePassword = passwordEncoder.encode(oldPassword);
//		if(usersMapper.matchUserPassword(userId, encodePassword) < 1) {
//			result.fillWithResult(ResultTypeCX.wrongOldPassword);
//			return result;
//		}
//		
//		return resetPassword(userId, newPassword, newPasswordRepeat);
//	}
//	
//	/**
//	 * 查找此 email 是否属于已激活用户
//	 * @param email
//	 * @return
//	 */
//	private CommonResultCX ensureActiveEmail(String email) {
//		CommonResultCX r = new CommonResultCX();
//		
//		UsersDetailExample userDetailExample = new UsersDetailExample();
//		userDetailExample.createCriteria().andEmailEqualTo(email);
//		List<UsersDetail> userDetailList = usersDetailMapper.selectByExample(userDetailExample);
//		if(userDetailList == null || userDetailList.size() < 1) {
//			return r;
//		}
//		
//		List<Long> userIdList = userDetailList.stream().map(UsersDetail::getUserId).collect(Collectors.toList());
//		return userAuthService.hasActiveUser(userIdList);
//	}
//
//}
