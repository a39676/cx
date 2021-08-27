package demo.tool.mail.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.constant.UsersUrl;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
import demo.common.service.CommonService;
import demo.tool.mail.mapper.MailRecordMapper;
import demo.tool.mail.pojo.dto.ResendMailDTO;
import demo.tool.mail.pojo.dto.SendForgotUsernameMailDTO;
import demo.tool.mail.pojo.dto.SendMailDTO;
import demo.tool.mail.pojo.po.MailRecord;
import demo.tool.mail.pojo.po.MailRecordExample;
import demo.tool.mail.pojo.result.SendRegistMailResult;
import demo.tool.mail.pojo.type.MailType;
import demo.tool.mail.service.MailService;
import mail.service.MailToolService;
import toolPack.emailHandle.MailHandle;
import toolPack.emailHandle.mailService.send.SendEmail;

@Service
public class MailServiceImpl extends CommonService implements MailService {

	@Autowired
	private MailConstantService mailConstantService;
	
	@Autowired
	private MailToolService mailToolService;
	
	@Autowired
	private MailRecordMapper mailRecordMapper;
	
	
	private boolean isMailReady() {
		String adminMailName = mailConstantService.getAdminMailName();
		String adminMailPwd = mailConstantService.getAdminMailPwd();
		
		return (adminMailName != null && adminMailPwd != null);
		
	}
	
	@Override
	public CommonResult sendSimpleMail(String sendTo, String title, String content, String mailKey, MailType mailType) {
		CommonResult result = new CommonResult();
		if(mailType == null || mailType.getCode() == null) {
			result.failWithMessage(ResultTypeCX.nullParam.getName());
			return result;
		}
		if(!isMailReady()) {
			result.failWithMessage(ResultTypeCX.mailBaseOptionError.getName());
			return result;
		}
		
		Properties properties = mailToolService.buildSinaSmtpSslProperties();

		SendEmail sm = new SendEmail();
		sm.sendMail(
				mailConstantService.getAdminMailName(), 
				mailConstantService.getAdminMailPwd(), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(mailConstantService.getAdminMailName()),
				title, 
				content, 
				null,
				properties
				);
		

		result.successWithMessage(mailKey);
		return result;
	}

	@Override
	public void sendMailWithAttachment(String sendTo, String title, String content, List<String> attachmentPathList,
			Properties properties) {
		if(!isMailReady()) {
			return;
		}
		SendEmail sm = new SendEmail();
		sm.sendMail(
				mailConstantService.getAdminMailName(), 
				mailConstantService.getAdminMailPwd(), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(mailConstantService.getAdminMailName()),
				title, 
				content, 
				attachmentPathList,
				properties
				);
	}

	@Override
	public void sendMailWithAttachment(String sendTo, String title, String content, String attachmentPath,
			Properties properties) {
		if(!isMailReady()) {
			return;
		}
		SendEmail sm = new SendEmail();
		sm.sendMail(
				mailConstantService.getAdminMailName(), 
				mailConstantService.getAdminMailPwd(), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(mailConstantService.getAdminMailName()),
				title, 
				content, 
				Arrays.asList(attachmentPath),
				properties
				);
	}

	@Override
	public SendRegistMailResult sendRegistMail(SendMailDTO dto) {
		SendRegistMailResult result = new SendRegistMailResult();
		if(dto.getUserId() == null || StringUtils.isAnyBlank(dto.getHostName(), dto.getSendTo(), dto.getNickName())) {
			result.failWithMessage(ResultTypeCX.nullParam.getName());
			return result;
		}
		if(!isMailReady()) {
			result.failWithMessage(ResultTypeCX.mailBaseOptionError.getName());
			return result;
		}
		
		Long newMailId = snowFlake.getNextId();
		String mailKey = null;
		try {
			mailKey = URLEncoder.encode(encryptId(newMailId), StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String mailUrl = dto.getHostName() + UsersUrl.root + UsersUrl.registActivation + "?mailKey=" + mailKey;
		
		sendSimpleMail(dto.getSendTo(), "欢迎注册", createRegistMailContent(dto.getNickName(), mailUrl), mailKey, MailType.registActivation);
		
		MailRecord mr = new MailRecord();
		mr.setId(newMailId);
		mr.setMailType(MailType.registActivation.getCode());
		mr.setValidTime(LocalDateTime.now().plusDays(3L));
		mr.setUserId(dto.getUserId());
		mailRecordMapper.insertSelective(mr);
		
		result.setIsSuccess();
		result.setMailKey(mailKey);
		return result;
	}
	
	@Override
	public SendRegistMailResult resendRegistMail(ResendMailDTO dto) {
		
		SendRegistMailResult result = sendRegistMail(dto);
		
		Long mailId = decryptPrivateKey(dto.getMailKey());
		MailRecord oldMail = mailRecordMapper.selectByPrimaryKey(mailId);
		if(oldMail == null) {
			result.failWithMessage(ResultTypeCX.errorParam.getName());
			return result;
		}
		oldMail.setValidTime(oldMail.getValidTime().plusDays(1));
		mailRecordMapper.updateByPrimaryKeySelective(oldMail);
		
		String mailUrl = dto.getHostName() + UsersUrl.root + UsersUrl.registActivation + "?mailKey=" + dto.getMailKey();
		sendSimpleMail(dto.getSendTo(), "欢迎注册" + dto.getHostName(), createRegistMailContent(dto.getNickName(), mailUrl), dto.getMailKey(), MailType.registActivation);
		
		result.setMailKey(dto.getMailKey());
		return result;
	}

	@Override
	public MailRecord findMailByMailKeyMailType(String mailKey, MailType mailType) {
		if(StringUtils.isBlank(mailKey) || mailType == null) {
			return new MailRecord();
		}
		
		Long id = decryptPrivateKey(mailKey);
		if(id == null) {
			return new MailRecord();
		}
		
		MailRecordExample example = new MailRecordExample();
		example.createCriteria().andIdEqualTo(id).andMailTypeEqualTo(mailType.getCode());
		List<MailRecord> mailList = mailRecordMapper.selectByExample(example);
		
		if(mailList == null || mailList.size() != 1) {
			return new MailRecord();
		}
		
		return mailList.get(0);
	}

	@Override
	public MailRecord findRegistActivationUnusedByUserId(Long userId) {
		if(userId == null) {
			return new MailRecord();
		}
		
		MailRecordExample example = new MailRecordExample();
		example.createCriteria()
		.andUserIdEqualTo(userId)
		.andMailTypeEqualTo(MailType.registActivation.getCode())
		.andWasUsedEqualTo(false)
		;
		List<MailRecord> mailList = mailRecordMapper.selectByExample(example);
		if(mailList == null || mailList.size() != 1) {
			return new MailRecord();
		}
		return mailList.get(0);
	}

	@Override
	public CommonResultCX updateWasUsed(Long mailId) {
		/*
		 * TODO
		 * delete??
		 */
		CommonResultCX result = new CommonResultCX();
		if(mailId == null) {
			return result;
		}
		MailRecord record = new MailRecord();
		record.setId(mailId);
		record.setWasUsed(true);
		int count =  mailRecordMapper.updateByPrimaryKeySelective(record);
		if(count > 0) {
			result.normalSuccess();
			return result;
		} else {
			return result;
		}
	}
	
	@Override
	public CommonResultCX sendForgotPasswordMail(SendMailDTO dto) {
		CommonResultCX result = new CommonResultCX();
		if(dto.getUserId() == null || StringUtils.isBlank(dto.getSendTo())) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		if(!isMailReady()) {
			result.failWithMessage(ResultTypeCX.mailBaseOptionError.getName());
			return result;
		}
		
		MailRecordExample example = new MailRecordExample();
		example.createCriteria()
		.andUserIdEqualTo(dto.getUserId())
		.andMailTypeEqualTo(MailType.forgotPassword.getCode())
		.andWasUsedEqualTo(false)
		.andValidTimeGreaterThan(LocalDateTime.now())
		;
		List<MailRecord> oldMails = mailRecordMapper.selectByExample(example);
		
		if(oldMails == null || oldMails.size() < 1) {
			result = sendNewForgotPasswordMail(dto);
		} else {
			result = resendForgotPasswordMail(dto, oldMails.get(0));
		}

		return result;
	}
	
	private CommonResultCX sendNewForgotPasswordMail(SendMailDTO dto) {
		MailRecord mr = new MailRecord();
		Long mailId = snowFlake.getNextId();
		mr.setId(mailId);
		mr.setMailType(MailType.forgotPassword.getCode());
		mr.setValidTime(LocalDateTime.now().plusDays(3L));
		mr.setUserId(dto.getUserId());
		
		mailRecordMapper.insertSelective(mr);
		
		String mailKey = null;
		try {
			mailKey = URLEncoder.encode(encryptId(mailId), StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			log.error(mailId + ", trans mail key error");
		} 
		String mailUrl = dto.getHostName() + UsersUrl.root + UsersUrl.resetPassword + "?mailKey=" + mailKey;
		return (CommonResultCX) sendSimpleMail(dto.getSendTo(), ("重置您在" + dto.getHostName() + "的密码"), createForgotPasswordMailContent(mailUrl), mailKey, MailType.forgotPassword);
	}
	
	private CommonResultCX resendForgotPasswordMail(SendMailDTO dto, MailRecord oldMail) {
		oldMail.setValidTime(oldMail.getValidTime().plusDays(1));
		mailRecordMapper.updateByPrimaryKeySelective(oldMail);
		
		String mailKey = null;
		try {
			mailKey = URLEncoder.encode(encryptId(oldMail.getId()), StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			log.error(oldMail.getId() + ", trans mail key error");
		}
		String mailUrl = dto.getHostName() + UsersUrl.root + UsersUrl.resetPassword + "?mailKey=" + mailKey;
		return (CommonResultCX) sendSimpleMail(dto.getSendTo(), ("重置您在" + dto.getHostName() + "的密码"), createForgotPasswordMailContent(mailUrl), mailKey, MailType.forgotPassword);
	}
	
	@Override
	public CommonResultCX sendForgotUsernameMail(SendForgotUsernameMailDTO dto) {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isAnyBlank(dto.getUserName(), dto.getEmail()) || dto.getUserId() == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		if(!isMailReady()) {
			result.fillWithResult(ResultTypeCX.mailBaseOptionError);
			return result;
		}
		
		sendSimpleMail(dto.getEmail(), ("获取您在" + dto.getHostName() + "的用户名"), ("您在" + dto.getHostName() + "的用户名是: " + dto.getUserName() + ""), "", MailType.forgotUsername);
		
		result.successWithMessage("");
		
		return result;
	}
	
	private String createRegistMailContent(String nickName, String mailUrl) {
		StringBuffer content = new StringBuffer();
		content.append("感谢注册! " + nickName + "\n");
		content.append("十分惊喜!还在建站就遇到了您...很多功能尚在建造,招呼不周,请见谅...如果有什么建议,请联系  \n");
		content.append("如非本人注册,请忽略此邮件 \n");
		content.append(mailUrl + "  点击此处激活账户  或复制此链接到浏览器访问. \n");
		content.append("再次感谢您的注册! 祝生活愉快!");
		return content.toString();
	}
	
	private String createForgotPasswordMailContent(String mailUrl) {
		StringBuffer content = new StringBuffer();
		content.append("收到了您重置密码的请求... \n");
		content.append("如非本人申请,请忽略此邮件 \n");
		content.append(mailUrl + "  点击此处重置密码  或复制此链接到浏览器访问. \n");
		return content.toString();
	}

	@Override
	public void sendErrorMail(String errorMessage) {
		if(!isMailReady()) {
			return;
		}
		
		Properties properties = mailToolService.buildSinaSmtpSslProperties();
		SendEmail sm = new SendEmail();
		sm.sendMail(
				mailConstantService.getAdminMailName(), 
				mailConstantService.getAdminMailPwd(), 
				Arrays.asList(mailConstantService.getAdminMailName()),
				null,
				null,
				("error : " + LocalDateTime.now().toString()), 
				errorMessage, 
				null,
				properties
				);
		
	}
	
	@Override
	public Message[] searchInbox(SearchTerm st) {
		if(!isMailReady()) {
			return new Message[] {};
		}
		
		MailHandle mailHandle = new MailHandle();

		Properties imapProperties = mailToolService.buildSinaImapSslProperties();
		Properties smtpProperties = mailToolService.buildSinaSmtpSslProperties();
		Store store = mailHandle.getMailStore(
				mailConstantService.getAdminMailName(), 
				mailConstantService.getAdminMailPwd(), 
				smtpProperties, 
				imapProperties
				);
		
		Folder inbox = mailHandle.getInbox(store);
		
		Message[] targetMail = mailHandle.getMailReadOnly(inbox, st);
		
		return targetMail;
	}

	@Override
	public SearchTerm singleSearchTerm(String targetSendFrom, String targetContent, Date startDate) {
		SearchTerm searchTerm = new SearchTerm() {
			private static final long serialVersionUID = 7873209385471356176L;

			@Override
			public boolean match(Message message) {
				Date receiveDate = null;
				try {
					receiveDate = message.getReceivedDate();
				} catch (MessagingException e1) {
					e1.printStackTrace();
					return false;
				}
				if(receiveDate.before(startDate)) {
					return false;
				}
				
				Address[] from = null;
				try {
					from = message.getFrom();
				} catch (MessagingException e) {
					e.printStackTrace();
					return false;
				}
				if(from == null || from.length < 1) {
					return false;
				}
				boolean flag = false;
				for(Address f : from) {
					if(f.toString().equals(targetSendFrom)) {
						flag = true;
					}
				}
				if(!flag) {
					return false;
				}
				
				try {
					MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
					String content = mimeMultipart.getBodyPart(0).getContent().toString();
					if(content.contains(targetContent)) {
						return true;
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		};
		return searchTerm;
	}
	
	/*
//	@Override
//	public SearchTerm searchByTargetContents(List<UserMailAndMailKeyBO> userMailAndMailKeyBOList) {
//		SearchTerm searchTerm = new SearchTerm() {
//
//			private static final long serialVersionUID = -4492471468971682840L;
//
//			@Override
//			public boolean match(Message message) {
//				UserMailAndMailKeyBO bo = null;
//				boolean flag = false;
//				for(int i = 0; i < userMailAndMailKeyBOList.size(); i ++) {
//					bo = userMailAndMailKeyBOList.get(i);
//					try {
//						if(message.getReceivedDate() == null) {
//							continue;
//						}
//						LocalDateTime receivedDate = dateHandler.dateToLocalDateTime(message.getReceivedDate());
//						if(receivedDate == null || receivedDate.isAfter(bo.getValidTime())) {
//							continue;
//						}
//						String content = "";
//						MimeMultipart mimeMultipart = null;
//						if(message.getContent().getClass().equals(String.class)) {
//							content = (String) message.getContent();
//						} else if(message.getContent().getClass().equals(MimeMultipart.class)) {
//							mimeMultipart = (MimeMultipart) message.getContent();
//							content = mimeMultipart.getBodyPart(0).getContent().toString();
//						}
//						if(!content.contains(bo.getMailKey())) {
//							continue;
//						}
//						
//						Address[] from = message.getFrom();
//						for(Address f : from) {
//							if(f.toString().equals(bo.getEmail())) {
//								flag = true;
//							}
//						}
//						return flag;
//					} catch (MessagingException e1) {
//						e1.printStackTrace();
//						continue;
//					} catch (IOException e) {
//						e.printStackTrace();
//						continue;
//					}
//				}
//				return false;
//			}
//		};
//		return searchTerm;
//	}
	 */

}
