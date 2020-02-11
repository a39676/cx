package demo.tool.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

import demo.baseCommon.pojo.result.CommonResultCX;
import demo.tool.pojo.dto.ResendMailDTO;
import demo.tool.pojo.dto.SendForgotUsernameMailDTO;
import demo.tool.pojo.dto.SendMailDTO;
import demo.tool.pojo.po.MailRecord;
import demo.tool.pojo.result.SendRegistMailResult;
import demo.tool.pojo.type.MailType;

public interface MailService {

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
