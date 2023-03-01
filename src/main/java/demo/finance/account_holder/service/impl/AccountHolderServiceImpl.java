package demo.finance.account_holder.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.finance.account_holder.mapper.AccountHolderMapper;
import demo.finance.account_holder.pojo.po.AccountHolder;
import demo.finance.account_holder.pojo.po.AccountHolderExample;
import demo.finance.account_holder.service.AccountHolderService;

@Service
public class AccountHolderServiceImpl extends CommonService implements AccountHolderService {

	@Autowired
	private AccountHolderMapper accountHolderMapper;
	
	@Override
	public AccountHolder getHolder(Long holderId) {
		AccountHolderExample example = new AccountHolderExample();
		example.createCriteria().andIdEqualTo(holderId);
		List<AccountHolder> holderList = accountHolderMapper.selectByExample(example);
		if(holderList.isEmpty()) {
			return null;
		} else {
			return holderList.get(0);
		}
	}

	@Override
	public List<AccountHolder> findHolder(String holderName) {
		AccountHolderExample example = new AccountHolderExample();
		example.createCriteria().andAccountHolderNameLike(holderName);
		return accountHolderMapper.selectByExample(example);
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
				holder.setBirth(localDateTimeHandler.dateToLocalDateTime(birth).toLocalDate());
			} catch (Exception e) {
				holder.setBirth(null);
			}
		}
		
		try {
			Long newHolderId = snowFlake.getNextId();
			accountHolderMapper.insertSelective(holder);
			return newHolderId;
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public List<AccountHolder> getCurrentHolders() {
		Long userId = baseUtilCustom.getUserId();
		if(userId == null) {
			return new ArrayList<>();
		}
		
		AccountHolderExample example = new AccountHolderExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return accountHolderMapper.selectByExample(example);
		
	}
	
}
