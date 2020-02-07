package demo.base.user.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.user.mapper.UserRegistMapper;
import demo.base.user.mapper.UsersDetailMapper;
import demo.base.user.mapper.UsersMapper;
import demo.base.user.pojo.bo.UserMailAndMailKeyBO;
import demo.base.user.pojo.dto.FindActiveEmailDTO;
import demo.base.user.pojo.dto.ResetFailAttemptDTO;
import demo.base.user.pojo.dto.UpdateDuplicateEmailDTO;
import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.pojo.vo.__baseSuperAdminRegistVO;
import demo.base.user.service.RoleService;
import demo.base.user.service.UserAuthService;
import demo.base.user.service.UserRegistService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.GenderType;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.baseCommon.service.CommonService;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.tool.pojo.MailRecord;
import demo.tool.pojo.type.MailType;
import demo.tool.service.ValidRegexToolService;
import demo.tool.service.impl.MailServiceImpl;
import net.sf.json.JSONObject;

@Service
public class UserRegistServiceImpl extends CommonService implements UserRegistService {

	@Autowired
	private UserRegistMapper userRegistMapper;
	@Autowired
	private MailServiceImpl mailService;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersDetailMapper usersDetailMapper;
	
	@Autowired
	private CustomPasswordEncoder passwordEncoder;
	@Autowired
	private UsersServiceImpl userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private ValidRegexToolService validRegexToolService;
	
	/* FIXME 2019-06-26 发现, 应改造, 至少迁移至redis */
	private static List<UserMailAndMailKeyBO> mailRecordList = new ArrayList<UserMailAndMailKeyBO>();
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CommonResultCX newUserRegist(UserRegistDTO param, String ip) {
		CommonResultCX result = new CommonResultCX();
		UsersDetail userDetail = new UsersDetail();
		JSONObject outputJson = new JSONObject();
		boolean exceptionFlag = false;

		if (!validRegexToolService.validNormalUserName(param.getUserName())) {
			outputJson.put("userName", "\"" + param.getUserName() + "\" 账户名异常, 必须以英文字母开头,长度为6~16个字符.(只可输入英文字母及数字)");
			exceptionFlag = true;
		}

		if (userRegistMapper.isUserExists(param.getUserName()) > 0) {
			outputJson.put("userName", "账户名已存在");
			exceptionFlag = true;
		}

		String nickNameAfterEscapeHtml = StringEscapeUtils.escapeHtml(param.getNickName());
		if(StringUtils.isBlank(param.getNickName())) {
			outputJson.put("nickName", "请您一定要起给昵称...");
			exceptionFlag = true;
		} else if (nickNameAfterEscapeHtml.length() > 32) {
			outputJson.put("nickName", "昵称太长了...");
			exceptionFlag = true;
		} else if (usersDetailMapper.isNickNameExists(nickNameAfterEscapeHtml) > 0) {
			outputJson.put("nickName", "昵称重复了...");
			exceptionFlag = true;
		} else {
			userDetail.setNickName(nickNameAfterEscapeHtml);
		}

		if (!validRegexToolService.validPassword(param.getPwd())) {
			outputJson.put("pwd", "密码长度不正确(8到16位)");
			exceptionFlag = true;
		}

		if (!param.getPwd().equals(param.getPwdRepeat())) {
			outputJson.put("pwdRepeat", "两次输入的密码不一致");
			exceptionFlag = true;
		}

		if (!validRegexToolService.validEmail(param.getEmail())) {
			outputJson.put("email", "请输入正确的邮箱");
			exceptionFlag = true;
		} else {
			FindActiveEmailDTO findActiveEmailParam = new FindActiveEmailDTO();
			findActiveEmailParam.setEmail(param.getEmail());
			Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
			if(r == null) {
				outputJson.put("email", "系统繁忙中");
				exceptionFlag = true;
			} else {
				findActiveEmailParam.setRoleId(r.getRoleId());
			}
			if (usersDetailMapper.isActiveEmailExists(findActiveEmailParam) > 0) {
				outputJson.put("email", "邮箱已注册(忘记密码或用户名?可尝试找回)");
				exceptionFlag = true;
			} else {
				userDetail.setEmail(param.getEmail());
			}
		}

		if (StringUtils.isNotBlank(param.getMobile())) {
			if (validRegexToolService.validMobile(param.getMobile())) {
				userDetail.setMobile(Long.parseLong(param.getMobile()));
			} else {
				outputJson.put("mobile", "请填入正确的手机号,或留空");
				exceptionFlag = true;
			}
		}

		String reservationInfomationAfterEscapeHtml = StringEscapeUtils.escapeHtml(param.getReservationInformation());
		if (param.getReservationInformation() != null
				&& param.getReservationInformation().replaceAll("\\s", "").length() > 0) {
			if (reservationInfomationAfterEscapeHtml.length() > 32) {
				outputJson.put("reservationInformation", "预留信息过长...32个字符以内..(中文算2个字符)");
				exceptionFlag = true;
			} else {
				userDetail.setReservationInformation(reservationInfomationAfterEscapeHtml);
			}
		}

		if (StringUtils.isNotBlank(param.getQq())) {
			if (validRegexToolService.validQQ(param.getQq())) {
				userDetail.setQq(Long.parseLong(param.getQq()));
			} else {
				outputJson.put("qq", "QQ号格式异常...");
				exceptionFlag = true;
			}
		}

		if (exceptionFlag) {
			result.normalFail();
			result.setMessage(outputJson.toString());
			return result;
		}

		if (param.getGender() == null || GenderType.unknow.getCode().equals(param.getGender())) {
			userDetail.setGender(GenderType.unknow.getCode());
		} else if (GenderType.unknow.getCode().equals(param.getGender())) {
			userDetail.setGender(GenderType.male.getCode());
		} else {
			userDetail.setGender(GenderType.female.getCode());
		}

		param.setPwdd(param.getPwd());
		param.setPwd(passwordEncoder.encode(param.getPwd()));
		Users user = createUserFromUserRegistParam(param);
		Long newUserId = snowFlake.getNextId();
		user.setUserId(newUserId);
		
		userRegistMapper.insertNewUser(user);

		userDetail.setUserId(newUserId);
		userDetail.setRegistIp(numberUtil.ipToLong(ip));
		usersDetailMapper.insertSelective(userDetail);
		
		String mailKey = mailService.insertNewRegistMailKey(newUserId);
		if(StringUtils.isBlank(mailKey)) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		userAuthService.insertBaseUserAuth(newUserId, AuthType.USER);
		
		result.normalSuccess();
		result.setMessage("\"" + StringEscapeUtils.unescapeHtml(nickNameAfterEscapeHtml) + "\" 已成功注册,谢谢您的关注! 如需激活邮箱,请使用注册邮箱: " + param.getEmail() + ",发送: "+ mailKey + " 到" + constantService.getValByName(SystemConstantStore.adminMailName));

		/*
		 * 暂时停止主动发送激活邮件,改为用户发送激活邮件 2018-06-28
		 */
//		CommonResult serviceResult = sendRegistMail(user.getUserId(), userDetail.getEmail(), userDetail.getNickName());
//		if(!serviceResult.getResult().equals(ResultType.success.getCode())) {
//			logger.debug(LocalDateTime.now() + " : " + serviceResult.message + " : " + user.getUserId());
//		}
		return result;
	}
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public __baseSuperAdminRegistVO __baseSuperAdminRegist() {
		UserRegistDTO param = new UserRegistDTO();
		__baseSuperAdminRegistVO result = new __baseSuperAdminRegistVO();
		UsersDetail userDetail = new UsersDetail();

		param.setUserName("daven");
		if (userRegistMapper.isUserExists(param.getUserName()) > 0) {
			result.failWithMessage("账户名已存在");
			return result;
		}
		
		param.setNickName("DavenC");
		String nickNameAfterEscapeHtml = StringEscapeUtils.escapeHtml(param.getNickName());
		if (usersDetailMapper.isNickNameExists(nickNameAfterEscapeHtml) > 0) {
			result.failWithMessage("昵称重复了...");
			return result;
		} else {
			userDetail.setNickName(nickNameAfterEscapeHtml);
		}

		
		param.setEmail("example@email.com");
		if (!validRegexToolService.validEmail(param.getEmail())) {
			result.failWithMessage("请输入正确的邮箱");
			return result;
		} else {
			FindActiveEmailDTO findActiveEmailParam = new FindActiveEmailDTO();
			findActiveEmailParam.setEmail(param.getEmail());
			Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
			if(r == null) {
				result.failWithMessage("系统繁忙中");
				return result;
			} else {
				findActiveEmailParam.setRoleId(r.getRoleId());
			}
			if (usersDetailMapper.isActiveEmailExists(findActiveEmailParam) > 0) {
				result.failWithMessage("邮箱已注册(忘记密码或用户名?可尝试找回)");
				return result;
			} else {
				userDetail.setEmail(param.getEmail());
			}
		}

		param.setQq("1050092382");
		userDetail.setQq(Long.parseLong(param.getQq()));

		userDetail.setGender(GenderType.unknow.getCode());

		param.setPwd("defaultPWD");
		param.setPwd(passwordEncoder.encode(param.getPwd()));
		Users user = createUserFromUserRegistParam(param);
		Long newUserId = snowFlake.getNextId();
		user.setUserId(newUserId);
		
		userRegistMapper.insertNewUser(user);

		userDetail.setUserId(newUserId);
		userDetail.setRegistIp(numberUtil.ipToLong("0.0.0.0"));
		usersDetailMapper.insertSelective(userDetail);
		
		userAuthService.insertBaseUserAuth(newUserId, AuthType.SUPER_ADMIN);
		
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
	
	private Users createUserFromUserRegistParam(UserRegistDTO param) {
    	Users user = new Users();
    	user.setUserName(param.getUserName());
    	user.setPwd(param.getPwd());
    	user.setPwdd(param.getPwdd());
    	user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
    	return user;
    }
	
	public CommonResult sendRegistMail(Long userId, String sendTo, String nickName) {
		/*
		 * 暂时不再主动发送激活邮件,改由用户发回激活邮件  2018-06-28
		 */
//		return mailServiceImpl.sendRegistMail(userId, sendTo, nickName);
		return null;
	}

	@Override
	public CommonResultCX modifyRegistEmail(Long userId, String email) {
		/*
		 * 暂时不再主动发送激活邮件,改由用户发回激活邮件  2018-06-28
		 */
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
		
		CommonResultCX result = new CommonResultCX();
		if(userId == null || !validRegexToolService.validEmail(email)) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		FindActiveEmailDTO p = new FindActiveEmailDTO();
		p.setEmail(email);
		Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
		if(r == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		p.setRoleId(r.getRoleId());
		if(usersDetailMapper.isActiveEmailExists(p) > 0) {
			result.fillWithResult(ResultTypeCX.mailExists);
			return result;
		}
		
		int updateCount = usersDetailMapper.modifyRegistEmail(email, userId);
		if(updateCount < 1) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		return result;
	}
	
	@Override
	public CommonResultCX registActivation(String mailKey, String activeEMail) {
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
		
		userAuthService.deleteUserAuth(mr.getUserId(), AuthType.USER);
		userAuthService.insertBaseUserAuth(mr.getUserId(), AuthType.USER_ACTIVE);
		
		mailService.updateWasUsed(mr.getMailId());
		UpdateDuplicateEmailDTO updateEmailParam = new UpdateDuplicateEmailDTO();
		updateEmailParam.setNewEmail("");
		updateEmailParam.setUserId(mr.getUserId());
		updateEmailParam.setOldEmail(activeEMail);
		usersDetailMapper.updateDuplicateEmail(updateEmailParam);
		result.successWithMessage("账号已激活");
		return result;
	}
	
	@Override
	public void handleMails() {
		Message[] mails = searchMailInbox();
		if(mails.length < 1) {
			return;
		}
		
		List<UserMailAndMailKeyBO> targetBoList = matchMails(mails);
		
		for(UserMailAndMailKeyBO bo : targetBoList) {
			if(bo.getMailType().equals(MailType.registActivation.getCode())) {
				registActivation(bo.getMailKey(), bo.getEmail());
			}
		}
	}
	
	private Message[] searchMailInbox() {
		findUserEmailAndKey();
		if(mailRecordList.size() < 1) {
			return new Message[] {};
		}
		SearchTerm st = mailService.searchByTargetContents(mailRecordList);
		return mailService.searchInbox(st);
	}
	
	private List<UserMailAndMailKeyBO> matchMails(Message[] mails) {
		List<UserMailAndMailKeyBO> targetBo = new ArrayList<UserMailAndMailKeyBO>();
		UserMailAndMailKeyBO tmpBo = null;
		for(Message mail : mails) {
			tmpBo = new UserMailAndMailKeyBO();
			try {
				for(UserMailAndMailKeyBO m : mailRecordList) {
					if(((MimeMultipart) mail.getContent()).getBodyPart(0).getContent().toString().contains(m.getMailKey())) {
						tmpBo.setMailKey(m.getMailKey());
						tmpBo.setMailType(m.getMailType());
						tmpBo.setUserId(m.getUserId());
						tmpBo.setMailId(m.getMailId());
					}
				}
				if(tmpBo.getMailId() != null) {
					targetBo.add(tmpBo);
				}
			} catch (MessagingException e) {
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
		}
		return targetBo;
	}
	
	private void findUserEmailAndKey() {
		mailRecordList = usersDetailMapper.findUserEmailAndKey();
	}
	/*
	 * 2018-06-28 暂停向注册用户发送激活邮件,改由用户发回激活码.
	 */
/*
	//	@Override
//	public CommonResult resendRegistMail(Long userId) {
//		CommonResult result = new CommonResult();
//		if(userId == null) {
//			result.fillWithResult(ResultType.nullParam);
//		}
//		
//		UsersDetail ud = usersDetailMapper.findUserDetail(userId);
//		
//		if(ud == null || StringUtils.isBlank(ud.getEmail()) || !validEmail(ud.getEmail())) {
//			result.fillWithResult(ResultType.nullParam);
//		}
//		
//		List<MailRecord> mails = mailService.findRegistActivationUnusedByUserId(userId);
//		
//		if(mails == null || mails.size() == 0) {
//			result = sendRegistMail(userId, ud.getEmail(), ud.getNickName());
//			
//		} else {
//			MailRecord oldMail = mails.get(0);
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
 */
	
	@Override
	public CommonResultCX sendForgotPasswordMail(String email, HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(email)) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		if(!validRegexToolService.validEmail(email)) {
			result.fillWithResult(ResultTypeCX.mailNotActivation);
			return result;
		}
		FindActiveEmailDTO findActiveEmailParam = new FindActiveEmailDTO();
		findActiveEmailParam.setEmail(email);
		Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
		if(r == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		findActiveEmailParam.setRoleId(r.getRoleId());
		Long userId = usersDetailMapper.findUserIdByActivationEmail(findActiveEmailParam);
		if(userId == null) {
			result.fillWithResult(ResultTypeCX.mailNotActivation);
			return result;
		}
		
		result = mailService.sendForgotPasswordMail(userId, email, findHostNameFromRequst(request));
		
		if(!result.isSuccess()) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		return result;
	}
	
	@Override
	public CommonResultCX sendForgotUsernameMail(String email, HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(email)) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}

		if(!validRegexToolService.validEmail(email)) {
			result.fillWithResult(ResultTypeCX.mailNotActivation);
			return result;
		}
		
		FindActiveEmailDTO findActiveEmailParam = new FindActiveEmailDTO();
		findActiveEmailParam.setEmail(email.replaceAll("\\s", ""));
		Roles r = roleService.getRoleByNameFromRedis(RolesType.ROLE_USER_ACTIVE.getName());
		if(r == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		findActiveEmailParam.setRoleId(r.getRoleId());
		String userName = usersDetailMapper.findUserNameByActivationEmail(findActiveEmailParam);
		if(StringUtils.isBlank(userName)) {
			result.fillWithResult(ResultTypeCX.mailNotActivation);
			return result;
		}
		
		result = mailService.sendForgotUsernameMail(userName, email, findHostNameFromRequst(request));
		
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
		
		mailService.updateWasUsed(mr.getMailId());
		
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
