package demo.tool.service.impl;

import org.springframework.stereotype.Service;

@Service
public class MailConstantService {

	private String adminMailName = "adminMailName";
	private String adminMailPwd = "adminMailPwd";
	
	public String getAdminMailName() {
//		TODO building mail constant service
		return adminMailName;
	}
	
	public String getAdminMailPwd() {
//		TODO building mail constant service
		return adminMailPwd;
	}
	
	
}
