package demo.tool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.tool.service.ValidRegexToolService;
import toolPack.numericHandel.NumericUtilCustom;

@Service
public class ValidRegexToolServiceImpl extends CommonService implements ValidRegexToolService {

	@Autowired
	private NumericUtilCustom numberUtil;
	
	@Override
	public boolean validQQ(String qq) {
		if (qq == null || !numberUtil.matchInteger(qq) || qq.length() < 5 || qq.length() > 11) {
			return false;
		}
		return true;
	}

	@Override
	public boolean validMobile(String mobile) {
		if (mobile == null) {
			return false;
		}
		return numberUtil.matchMobile(mobile);
	}
	
	@Override
	public boolean validNormalUserName(String userNameInput) {
		if (userNameInput == null) {
			return false;
		}
		return userNameInput.matches("[a-z][a-zA-Z0-9_]{5,15}");
	}

	@Override
	public boolean validPassword(String passwordInput) {
		if (passwordInput == null) {
			return false;
		}
		return passwordInput.matches(".{8,16}");
	}
	
	@Override
	public boolean validEmail(String email) {
		if (email == null) {
			return false;
		}
		return email.matches("[a-zA-Z0-9][a-zA-Z0-9_-]*@[a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]{2,4}(\\.[a-z]{2,4})?");
	}
	
	/*
//	public boolean validMobileEmail(String email) {
//		if(!validEmail(email)) {
//			return false;
//		}
//		
//		String[] emailPart = email.split("@");
//		if(!validMobile(emailPart[0]) || !validMobileEmailHost(emailPart[1])) {
//			return false;
//		}
//		
//		return true;
//	}
 */
	
	/*
//	public boolean validMobileEmailHost(String hostName) {
//		if(hostName == null) {
//			return false;
//		}
//		if(hostName.matches("189.cn") || hostName.matches("139.com") || hostName.matches("wo.cn")) {
//			return true;
//		}
//		return false;
//	}
 */
}
