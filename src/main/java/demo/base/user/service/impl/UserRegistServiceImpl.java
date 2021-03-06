package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auxiliaryCommon.pojo.type.GenderType;
import demo.base.system.pojo.constant.SystemRedisKey;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.HostnameService;
import demo.base.user.mapper.UserRegistMapper;
import demo.base.user.mapper.UsersMapper;
import demo.base.user.pojo.bo.EditUserAuthBO;
import demo.base.user.pojo.bo.FindUserAuthBO;
import demo.base.user.pojo.constant.UserConstant;
import demo.base.user.pojo.dto.FindAuthsDTO;
import demo.base.user.pojo.dto.ResetFailAttemptDTO;
import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.result.ModifyRegistEmailResult;
import demo.base.user.pojo.result.NewUserRegistResult;
import demo.base.user.pojo.result.ValidUserRegistResult;
import demo.base.user.pojo.result.__baseSuperAdminRegistResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.service.AuthService;
import demo.base.user.service.UserAuthService;
import demo.base.user.service.UserDetailService;
import demo.base.user.service.UserRegistService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
import demo.common.service.CommonService;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.tool.pojo.dto.ResendMailDTO;
import demo.tool.pojo.dto.SendForgotUsernameMailDTO;
import demo.tool.pojo.dto.SendMailDTO;
import demo.tool.pojo.po.MailRecord;
import demo.tool.pojo.result.SendRegistMailResult;
import demo.tool.pojo.type.MailType;
import demo.tool.service.MailService;
import demo.tool.service.TextFilter;
import demo.tool.service.ValidRegexToolService;
import toolPack.numericHandel.NumericUtilCustom;

@Service
public class UserRegistServiceImpl extends CommonService implements UserRegistService {

	@Autowired
	private UserRegistMapper userRegistMapper;
	@Autowired
	private MailService mailService;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private NumericUtilCustom numberUtil;
	
	@Autowired
	private CustomPasswordEncoder passwordEncoder;
	@Autowired
	private UsersServiceImpl userService;
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private AuthService authService;
	@Autowired
	private ValidRegexToolService validRegexToolService;
	@Autowired
	private HostnameService hostnameService;
	@Autowired
	private TextFilter textFilter;
	
	@Override
	public NewUserRegistResult newUserRegist(UserRegistDTO registDTO, String ip, HttpServletRequest request) {
		NewUserRegistResult result = new NewUserRegistResult();
		
		int ipRegistCount = 0;
		if(!isBigUser()) {
			ipRegistCount = redisConnectService.checkFunctionalModuleVisitData(request, SystemRedisKey.userRegistCountingKeyPrefix);
		}
		if(ipRegistCount > UserConstant.oneDayRegistCount) {
			result.failWithMessage("最近已经注册过了,请不要重复注册");
			return result;
		}
		
		ValidUserRegistResult validUserRegistDTOResult = validAndSanitizeUserRegistDTO(registDTO);
		
		if (!validUserRegistDTOResult.isSuccess()) {
			result.setValidUserRegistResult(validUserRegistDTOResult);
			return result;
		}

		Long newUserId = snowFlake.getNextId();
		UsersDetail userDetail = buildUserDetailFromUserRegistDTO(registDTO, ip, newUserId);
		Users user = buildUserFromRegistDTO(registDTO, newUserId);
		
		SendRegistMailResult sendRegistMailResult = sendRegistMail(registDTO, request, newUserId);
		if(!sendRegistMailResult.isSuccess()) {
			result.addMessage(sendRegistMailResult.getMessage());
			return result;
		}

		try {
			insertNewUserData(user, userDetail);
		} catch (Exception e) {
			result.failWithMessage("server error");
			return result;
		}
		
		result.normalSuccess();
		redisConnectService.insertFunctionalModuleVisitData(request, SystemRedisKey.userRegistCountingKeyPrefix, 1, TimeUnit.DAYS);
		
		return result;
	}
	
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	private void insertNewUserData(Users newUser, UsersDetail newUserDetail) {
		userRegistMapper.insertNewUser(newUser);
		userDetailService.insertSelective(newUserDetail);
		userAuthService.insertBaseUserAuth(newUser.getUserId(), AuthType.USER);
	}
	
	private ValidUserRegistResult validAndSanitizeUserRegistDTO(UserRegistDTO dto) {
		ValidUserRegistResult r = new ValidUserRegistResult();
		
		PolicyFactory filter = textFilter.getArticleFilter();
		
		if (!validRegexToolService.validNormalUserName(dto.getUserName())) {
			dto.setUserName(filter.sanitize(dto.getUserName()));
			r.setUsername("\"" + dto.getUserName() + "\" 账户名异常, 必须以英文字母开头,长度为6~16个字符.(只可输入英文字母及数字)");
			log.error("user regist error, username: " + dto.getUserName());
		}

		if (userRegistMapper.isUserExists(dto.getUserName()) > 0) {
			r.setUsername("账户名已存在");
			log.error("user regist error, duplicate username: " + dto.getUserName());
		}

		dto.setNickName(filter.sanitize(dto.getNickName()));
		if(StringUtils.isBlank(dto.getNickName())) {
			r.setNickname("请您一定要起给昵称...");
			log.error("user regist error, null nickname: " + dto.getNickName());
		} else if (dto.getNickName().length() > 32) {
			r.setNickname("昵称太长了...");
			log.error("user regist error, nickname too long: " + dto.getNickName());
		} else if (userDetailService.isNicknameExists(dto.getNickName())) {
			r.setNickname("昵称重复了...");
			log.error("user regist error, nickname duplicate: " + dto.getNickName());
		}

		if (!validRegexToolService.validPassword(dto.getPwd())) {
			r.setPwd("密码长度不正确(8到16位)");
			log.error("user regist error, pwd too short");
		}

		if (!dto.getPwd().equals(dto.getPwdRepeat())) {
			r.setPwdRepeat("两次输入的密码不一致");
			log.error("user regist error, pwd repeat error");
		}

		if (!validRegexToolService.validEmail(dto.getEmail())) {
			r.setEmail("请输入正确的邮箱");
			log.error("user regist error, email error: " + dto.getEmail());
		} else {
			if (userDetailService.ensureActiveEmail(dto.getEmail()).isSuccess()) {
				r.setEmail("邮箱已注册(忘记密码或用户名?可尝试找回)");
				log.error("user regist error, email duplicate: " + dto.getEmail());
			}
		}

		if (StringUtils.isNotBlank(dto.getMobile())) {
			if(!numberUtil.matchMobile(dto.getMobile())) {
				r.setMobile("请填入正确的手机号,或留空");
				log.error("user regist error, mobile error: " + dto.getMobile());
			}
			
			if(userDetailService.ensureActiveMobile(Long.parseLong(dto.getMobile())).isSuccess()) {
				r.setMobile("手机号已经被占用, 若需要转移到当前帐号, 请联系管理员, 请参见网站上方\"联系方式\"");
				log.error("user regist error, mobile duplicate: " + dto.getMobile());
			}
		}

		dto.setReservationInformation(filter.sanitize(dto.getReservationInformation()));
		if (StringUtils.isNotBlank(dto.getReservationInformation())) {
			if (dto.getReservationInformation().length() > 32) {
				r.setReservationInformation("预留信息过长...32个字符以内..(中文算2个字符)");
				log.error("user regist error, reservation information too long");
			}
		}

		if (StringUtils.isNotBlank(dto.getQq())) {
			r.setQq("QQ号格式异常...");
			log.error("user regist error, QQ num error, qq: " + dto.getQq());
		}
		
		if(r.getUsername() == null 
				&& r.getNickname() == null 
				&& r.getPwd() == null 
				&& r.getPwdRepeat() == null 
				&& r.getEmail() == null 
				&& r.getMobile() == null 
				&& r.getReservationInformation() == null 
				&& r.getQq() == null 
				) {
			r.setIsSuccess();
		}
		
		return r;
	}
	
	private Users buildUserFromRegistDTO(UserRegistDTO dto, Long userId) {
		dto.setPwd(passwordEncoder.encode(dto.getPwd()));
    	Users user = new Users();
    	user.setUserId(userId);
    	user.setUserName(dto.getUserName());
    	user.setPwd(dto.getPwd());
    	user.setPwdd(dto.getPwd());
    	user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
    	return user;
    }
	
	private UsersDetail buildUserDetailFromUserRegistDTO(UserRegistDTO dto, String ip, Long userId) {
		UsersDetail ud = new UsersDetail();
		
		ud.setEmail(dto.getEmail());
		if(numberUtil.matchMobile(dto.getMobile())) {
			ud.setMobile(Long.parseLong(dto.getMobile()));
		}
		ud.setNickName(dto.getNickName());
		if(numberUtil.matchSimpleNumber(dto.getQq())) {
			ud.setQq(Long.parseLong(dto.getQq()));
		}
		ud.setRegistIp(numberUtil.ipToLong(ip));
		ud.setReservationInformation(dto.getReservationInformation());
		ud.setUserId(userId);
		
		if (dto.getGender() == null || GenderType.unknow.getCode().equals(dto.getGender())) {
			ud.setGender(GenderType.unknow.getCode());
		} else if (GenderType.unknow.getCode().equals(dto.getGender())) {
			ud.setGender(GenderType.male.getCode());
		} else {
			ud.setGender(GenderType.female.getCode());
		}
		
		return ud;
	}
	
	private SendRegistMailResult sendRegistMail(UserRegistDTO dto, HttpServletRequest request, Long userId) {
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if(hostnameType == null) {
			return new SendRegistMailResult();
		}
		SendMailDTO sendRegistMailDTO = new SendMailDTO();
		sendRegistMailDTO.setUserId(userId);
		sendRegistMailDTO.setHostName(findHostNameFromRequst(request));
		sendRegistMailDTO.setNickName(dto.getNickName());
		sendRegistMailDTO.setSendTo(dto.getEmail());
		return mailService.sendRegistMail(sendRegistMailDTO);
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public __baseSuperAdminRegistResult __baseSuperAdminRegist() {
		log.error("building base super admin");
		UserRegistDTO userRegistDTO = new UserRegistDTO();
		userRegistDTO.setUserName("daven");
		userRegistDTO.setNickName("DavenC");
		userRegistDTO.setEmail("example@email.com");
		userRegistDTO.setQq("1050092382");
		userRegistDTO.setPwd("defaultPWD");
		userRegistDTO.setPwd(passwordEncoder.encode(userRegistDTO.getPwd()));
		userRegistDTO.setGender(GenderType.unknow.getCode());
		userRegistDTO.setMobile("13800138000");
		
		Long newUserId = snowFlake.getNextId();
		
		ValidUserRegistResult validAndSanitizeUserRegistResult = validAndSanitizeUserRegistDTO(userRegistDTO);
		log.error("validAndSanitizeUserRegistResult: " + validAndSanitizeUserRegistResult.isSuccess());
		if(validAndSanitizeUserRegistResult.isFail()) {
			log.error(validAndSanitizeUserRegistResult.getMessage());
		}
		
		__baseSuperAdminRegistResult result = new __baseSuperAdminRegistResult();
		
		UsersDetail userDetail = buildUserDetailFromUserRegistDTO(userRegistDTO, "0.0.0.0", newUserId);
		Users user = buildUserFromRegistDTO(userRegistDTO, newUserId);
		
		userRegistMapper.insertNewUser(user);
		userDetailService.insertSelective(userDetail);
		userAuthService.insertBaseUserAuth(newUserId, AuthType.SUPER_ADMIN);
		
		log.error("insert base super admin");
		
		result.normalSuccess();
		result.setNewSuperAdminId(newUserId);

		return result;
	}

	@Override
	public boolean isUserExists(String userName) {

		boolean result = false;

		int count = userRegistMapper.isUserExists(userName);

		if (count > 0) {
			result = true;
		}

		return result;
	}
	
	@Override
	public ModifyRegistEmailResult modifyRegistEmail(Long userId, String email) {
//		CommonResult result = new CommonResult();
//		if(userId == null || !validEmail(email)) {
//			result.fillWithResult(ResultType.errorParam);
//			return result;
//		}
//		
//		int updateCount = usersDetailMapper.modifyRegistEmail(email, userId);
//		if(updateCount < 1) {
//			result.fillWithResult(ResultType.mailExists);
//			return result;
//		}
//		
//		result = resendRegistMail(userId);
//		return result;
		
		ModifyRegistEmailResult r = new ModifyRegistEmailResult();
		if(userId == null || !validRegexToolService.validEmail(email)) {
			r.fillWithResult(ResultTypeCX.errorParam);
			return r;
		}
		
		if (userDetailService.ensureActiveEmail(email).isSuccess()) {
			r.failWithMessage("邮箱已注册(忘记密码或用户名?可尝试找回)");
			return r;
		}
		
		int updateCount = userDetailService.modifyRegistEmail(email, userId);
		if(updateCount < 1) {
			r.fillWithResult(ResultTypeCX.serviceError);
			return r;
		}
		
		/*
		 * TODO 更改邮箱需要将用户角色变回未激活? 最简便
		 */
		
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public CommonResultCX registActivation(String mailKey) {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(mailKey)) {
			result.fillWithResult(ResultTypeCX.linkExpired);
			return result;
		}
		
		MailRecord mr = mailService.findMailByMailKeyMailType(mailKey, MailType.registActivation);

		if (mr == null || mr.getUserId() == null) {
			result.fillWithResult(ResultTypeCX.linkExpired);
			return result;
		}
		
		FindUserAuthBO findUserAuthBO = new FindUserAuthBO();
		findUserAuthBO.setUserId(mr.getUserId());
		FindUserAuthResult findUserAuthResult = userAuthService.findUserAuth(findUserAuthBO);
		if(!findUserAuthResult.isSuccess()) {
			result.addMessage(findUserAuthResult.getMessage());
			return result;
		}
		
		FindAuthsDTO findAuthDTO = new FindAuthsDTO();
		findAuthDTO.setAuthType(AuthType.USER_ACTIVE.getCode().intValue());
		FindAuthsResult activeUserAuthResult = authService.findAuthsByCondition(findAuthDTO);
		if(!activeUserAuthResult.isSuccess()) {
			result.addMessage(activeUserAuthResult.getMessage());
			return result;
		}
		Auth activeUserAuth = activeUserAuthResult.getAuthList().get(0);
		
		EditUserAuthBO editUserAuthBO = new EditUserAuthBO();
		editUserAuthBO.setUserId(mr.getUserId());
		editUserAuthBO.setAuthId(activeUserAuth.getId());
		CommonResultCX editUserAuthResult = userAuthService.insertUserAuth(editUserAuthBO);
		if(!editUserAuthResult.isSuccess()) {
			result.addMessage(editUserAuthResult.getMessage());
			return result;
		}
		
		mailService.updateWasUsed(mr.getId());
		result.successWithMessage("账号已激活");
		return result;
	}
	
	@Override
	public CommonResultCX resendRegistMail(Long userId, HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();
		if(userId == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
		}
		
		UsersDetail ud = userDetailService.findById(userId);
		
		if(ud == null || !validRegexToolService.validEmail(ud.getEmail())) {
			result.fillWithResult(ResultTypeCX.nullParam);
		}
		
		MailRecord oldMail = mailService.findRegistActivationUnusedByUserId(userId);
		
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if(hostnameType == null) {
			return result;
		}
		
		if(oldMail == null || oldMail.getId() == null) {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setHostName(findHostNameFromRequst(request));
			sendMailDTO.setNickName(ud.getNickName());
			sendMailDTO.setSendTo(ud.getEmail());
			sendMailDTO.setUserId(userId);
			result = mailService.sendRegistMail(sendMailDTO);
			
		} else {
			// 发送(包括重发) 时间差少于1分钟, 不再重复发送
			LocalDateTime now = LocalDateTime.now();
			if(oldMail.getCreateTime().plusMinutes(1).isAfter(now) 
					|| (oldMail.getResendTime() != null && oldMail.getResendTime().plusMinutes(1).isAfter(now))) {
				result.isSuccess();
				return result;
			}
			ResendMailDTO resendMailDTO = new ResendMailDTO();
			resendMailDTO.setHostName(hostnameType.getName());
			resendMailDTO.setMailKey(encryptId(oldMail.getId()));
			resendMailDTO.setNickName(ud.getNickName());
			resendMailDTO.setSendTo(ud.getEmail());
			resendMailDTO.setUserId(userId);
			result = mailService.resendRegistMail(resendMailDTO);
		}
		
		return result;
	}
	
	@Override
	public CommonResultCX sendForgotPasswordMail(String email, HttpServletRequest request) {
		CommonResultCX r = new CommonResultCX();
		
		if(!validRegexToolService.validEmail(email)) {
			r.fillWithResult(ResultTypeCX.mailNotActivation);
			return r;
		}
		if (!userDetailService.ensureActiveEmail(email).isSuccess()) {
			r.failWithMessage("邮箱未被绑定, 无法用于找回密码");
			return r;
		}
		
		List<UsersDetail> userDetailList = userDetailService.findByEmail(email);
		
		if(userDetailList == null || userDetailList.isEmpty()) {
			r.fillWithResult(ResultTypeCX.mailNotActivation);
			return r;
		}
		UsersDetail ud = userDetailList.get(0);
		
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if(hostnameType == null) {
			r.fillWithResult(ResultTypeCX.serviceError);
			return r;
		}
		SendMailDTO sendForgeotPasswordDTO = new SendMailDTO();
		sendForgeotPasswordDTO.setHostName(findHostNameFromRequst(request));
		sendForgeotPasswordDTO.setNickName(ud.getNickName());
		sendForgeotPasswordDTO.setSendTo(ud.getEmail());
		sendForgeotPasswordDTO.setUserId(ud.getUserId());
		r = mailService.sendForgotPasswordMail(sendForgeotPasswordDTO);
		
		if(!r.isSuccess()) {
			r.fillWithResult(ResultTypeCX.serviceError);
			return r;
		}
		
		return r;
	}
	
	@Override
	public CommonResultCX sendForgotUsernameMail(String email, HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();

		if(!validRegexToolService.validEmail(email)) {
			result.fillWithResult(ResultTypeCX.mailNotActivation);
			return result;
		}
		if (!userDetailService.ensureActiveEmail(email).isSuccess()) {
			result.fillWithResult(ResultTypeCX.mailNotActivation);
			return result;
		}
		
		List<UsersDetail> userDetailList = userDetailService.findByEmail(email);
		
		if(userDetailList == null || userDetailList.isEmpty()) {
			result.fillWithResult(ResultTypeCX.mailNotActivation);
			return result;
		}
		UsersDetail ud = userDetailList.get(0);
		
		Users user = usersMapper.selectByPrimaryKey(ud.getUserId());
		if(user == null) {
			result.failWithMessage("用户不存在");
			return result;
		}
		
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if(hostnameType == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		SendForgotUsernameMailDTO sendForgotUsernameMailDTO = new SendForgotUsernameMailDTO();
		sendForgotUsernameMailDTO.setEmail(email);
		sendForgotUsernameMailDTO.setHostName(findHostNameFromRequst(request));
		sendForgotUsernameMailDTO.setUserId(ud.getUserId());
		sendForgotUsernameMailDTO.setUserName(user.getUserName());
		result = mailService.sendForgotUsernameMail(sendForgotUsernameMailDTO);
		
		if(!result.isSuccess()) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		return result;
	}
	
	private CommonResultCX resetPassword(Long userId, String newPassword, String newPasswordRepeat) {
		CommonResultCX result = new CommonResultCX();
		if (!validRegexToolService.validPassword(newPassword)) {
			result.fillWithResult(ResultTypeCX.invalidPassword);
			return result;
		}
		
		if(!newPassword.equals(newPasswordRepeat)) {
			result.fillWithResult(ResultTypeCX.differentPassword);
			return result;
		}
		
		Users user = usersMapper.findUser(userId);
		if(user == null) {
			result.fillWithResult(ResultTypeCX.linkExpired);
			return result;
		}
		
		int resetCount = userRegistMapper.resetPassword(passwordEncoder.encode(newPassword), newPassword, user.getUserId());
		if(resetCount > 0) {
			result.fillWithResult(ResultTypeCX.resetPassword);
			return result;
		} else {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
	}
	
	@Override
	public CommonResultCX resetPasswordByMailKey(String mailKey, String newPassword, String newPasswordRepeat) {
		CommonResultCX result  = new CommonResultCX();
		if(StringUtils.isBlank(mailKey)) {
			result.fillWithResult(ResultTypeCX.linkExpired);
			return result;
		}
		
		MailRecord mr = mailService.findMailByMailKeyMailType(mailKey, MailType.forgotPassword);
		if (mr == null || mr.getUserId() == null) {
			result.fillWithResult(ResultTypeCX.linkExpired);
			return result;
		}
		
		result = resetPassword(mr.getUserId(), newPassword, newPasswordRepeat);
		if(!result.isSuccess()) {
			return result;
		}
		
		mailService.updateWasUsed(mr.getId());
		
		ResetFailAttemptDTO resetFailAttemptParam = new ResetFailAttemptDTO();
		resetFailAttemptParam.setUserId(mr.getUserId());
		usersMapper.resetFailAttempts(resetFailAttemptParam);
		
		Users tmpUser = new Users();
		tmpUser.setEnabled(true);
		userService.setLockeds(tmpUser);
		
		result.successWithMessage("已成功重置密码");
		return result;
	}

	@Override
	public CommonResultCX resetPasswordByLoginUser(Long userId, String oldPassword, String newPassword, String newPasswordRepeat) {
		CommonResultCX result = new CommonResultCX();
		String encodePassword = passwordEncoder.encode(oldPassword);
		if(usersMapper.matchUserPassword(userId, encodePassword) < 1) {
			result.fillWithResult(ResultTypeCX.wrongOldPassword);
			return result;
		}
		
		return resetPassword(userId, newPassword, newPasswordRepeat);
	}
	

}
