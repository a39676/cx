package demo.tool.mail.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.pojo.result.CommonResultCX;
import demo.tool.mail.pojo.dto.ResendMailDTO;
import demo.tool.mail.pojo.dto.SendForgotUsernameMailDTO;
import demo.tool.mail.pojo.dto.SendMailDTO;
import demo.tool.mail.pojo.po.MailRecord;
import demo.tool.mail.pojo.result.SendRegistMailResult;
import demo.tool.mail.pojo.type.MailType;

public interface MailService {
	
	CommonResult sendSimpleMail(String sendTo, String title, String content, String mailKey, MailType mailType);

	void sendMailWithAttachment(String sendTo, String title, String context, List<String> attachmentPathList, Properties properties);

	void sendMailWithAttachment(String sendTo, String title, String context, String attachmentPath,
			Properties properties);

	SendRegistMailResult sendRegistMail(SendMailDTO dto);

	public MailRecord findMailByMailKeyMailType(String mailKey, MailType mailType);

	CommonResultCX updateWasUsed(Long mailId);

	CommonResultCX sendForgotPasswordMail(SendMailDTO dto);
	
	CommonResultCX sendForgotUsernameMail(SendForgotUsernameMailDTO dto);

	MailRecord findRegistActivationUnusedByUserId(Long userId);

	SendRegistMailResult resendRegistMail(ResendMailDTO dto);
	
	void sendErrorMail(String errorMessage);

	Message[] searchInbox(SearchTerm st);

	SearchTerm singleSearchTerm(String targetSendFrom, String targetContent, Date startDate);

//	SearchTerm searchByTargetContents(List<UserMailAndMailKeyBO> userMailAndMailKeyBOList);

}
