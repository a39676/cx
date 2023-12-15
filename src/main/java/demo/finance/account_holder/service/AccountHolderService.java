package demo.finance.account_holder.service;

import java.util.List;

import demo.finance.account_holder.pojo.po.AccountHolder;
import jakarta.servlet.http.HttpServletRequest;


public interface AccountHolderService {
	
	public AccountHolder getHolder(Long holderId);
	
	public List<AccountHolder> findHolder(String holderName);
	
	public Long holderRegister(Long userId, HttpServletRequest request);
	
	List<AccountHolder> getCurrentHolders();

}
