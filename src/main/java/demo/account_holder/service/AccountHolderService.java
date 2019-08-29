package demo.account_holder.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import demo.account_holder.pojo.po.AccountHolder;


public interface AccountHolderService {
	
	public AccountHolder getHolder(Long id);
	
	public List<AccountHolder> findHolder(String holderName);
	
	public Long holderRegister(Long userId, HttpServletRequest request);
	
	List<AccountHolder> getCurrentHolders(String userName);

}
