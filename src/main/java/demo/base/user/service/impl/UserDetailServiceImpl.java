package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.UsersDetailMapper;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.po.UsersDetailExample;
import demo.base.user.service.UserAuthService;
import demo.base.user.service.UserDetailService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.service.CommonService;

@Service
public class UserDetailServiceImpl extends CommonService implements UserDetailService {

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private UsersDetailMapper userDetailMapper;
	
	@Override
	public boolean isNicknameExists(String nickname) {
		if(StringUtils.isBlank(nickname)) {
			return true;
		}
		return (userDetailMapper.isNickNameExists(nickname) > 0);
	}
	
	@Override
	public CommonResultCX ensureActiveEmail(String email) {
		CommonResultCX r = new CommonResultCX();
		
		UsersDetailExample userDetailExample = new UsersDetailExample();
		userDetailExample.createCriteria().andEmailEqualTo(email);
		List<UsersDetail> userDetailList = userDetailMapper.selectByExample(userDetailExample);
		if(userDetailList == null || userDetailList.size() < 1) {
			return r;
		}
		
		List<Long> userIdList = userDetailList.stream().map(UsersDetail::getUserId).collect(Collectors.toList());
		return userAuthService.hasActiveUser(userIdList);
	}
	
	@Override
	public CommonResultCX ensureActiveMobile(Long mobile) {
		CommonResultCX r = new CommonResultCX();
		
		UsersDetailExample userDetailExample = new UsersDetailExample();
		userDetailExample.createCriteria().andMobileEqualTo(mobile);
		List<UsersDetail> userDetailList = userDetailMapper.selectByExample(userDetailExample);
		if(userDetailList == null || userDetailList.size() < 1) {
			return r;
		}
		
		List<Long> userIdList = userDetailList.stream().map(UsersDetail::getUserId).collect(Collectors.toList());
		return userAuthService.hasActiveUser(userIdList);
	}
	
	@Override
	public List<UsersDetail> findByEmail(String email) {
		if(StringUtils.isBlank(email)) {
			return new ArrayList<UsersDetail>();
		}
		
		UsersDetailExample example = new UsersDetailExample();
		example.createCriteria().andEmailEqualTo(email);
		return userDetailMapper.selectByExample(example);
	}
	
	@Override
	public UsersDetail findById(Long id) {
		return userDetailMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int modifyRegistEmail(String email, Long userId) {
		return userDetailMapper.modifyRegistEmail(email, userId);
	}
	
	@Override
	public int insertSelective(UsersDetail newUserDetail) {
		return userDetailMapper.insertSelective(newUserDetail);
	}
}
