package demo.account_holder.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.account_holder.mapper.AccountHolderCustomMapper;
import demo.account_holder.pojo.po.AccountHolder;
import demo.account_holder.service.AccountHolderService;
import demo.baseCommon.service.CommonService;

@Service
public class AccountHolderServiceImpl extends CommonService implements AccountHolderService {

	@Autowired
	private AccountHolderCustomMapper accountHolderCustomMapper;
	
	@Override
	public AccountHolder getHolder(Long id) {
		AccountHolder holder = accountHolderCustomMapper.findAccountHolderByID(id);
		return holder;
	}

	@Override
	public List<AccountHolder> findHolder(String holderName) {
		List<AccountHolder> holderList = accountHolderCustomMapper.findAccountHolderByName(holderName);
		return holderList;
	}

	@Override
	public Long holderRegister(Long userId, HttpServletRequest request) {
		
		AccountHolder holder = new AccountHolder();
		
		holder.setAccountHolderName(request.getParameter("accountHolderName"));
		holder.setGender(request.getParameter("gender"));
		holder.setUserId(userId);
		
		if(request.getParameter("birth") != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date birth = dateFormat.parse(request.getParameter("birth"));
				holder.setBirth(birth);
			} catch (Exception e) {
				holder.setBirth(null);
			}
		}
		
		try {
			Long newHolderId = snowFlake.getNextId();
			accountHolderCustomMapper.addAccountHolder(holder);
			return newHolderId;
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public List<AccountHolder> getCurrentHolders(String userName) {
		if(StringUtils.isEmpty(userName)) {
			return null;
		} else {
			return accountHolderCustomMapper.getCurrentHolders(userName);
		}
	}
	
}
