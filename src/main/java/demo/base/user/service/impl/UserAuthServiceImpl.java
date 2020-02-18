package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.UserAuthMapper;
import demo.base.user.pojo.dto.EditUserAuthDTO;
import demo.base.user.pojo.dto.FindAuthsConditionDTO;
import demo.base.user.pojo.dto.FindUserAuthDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.UserAuth;
import demo.base.user.pojo.po.UserAuthExample;
import demo.base.user.pojo.po.UserAuthExample.Criteria;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.service.AuthService;
import demo.base.user.service.UserAuthService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.service.CommonService;

@Service
public class UserAuthServiceImpl extends CommonService implements UserAuthService {

	private static final Logger log = LoggerFactory.getLogger(UserAuthServiceImpl.class);

	@Autowired
	private UserAuthMapper userAuthMapper;
	@Autowired
	private AuthService authService;

	@Override
	public CommonResultCX insertUserAuth(Long userId, Long authId) {
		CommonResultCX r = new CommonResultCX();
		
		UserAuth po = new UserAuth();
		Long creatorId = baseUtilCustom.getUserId();
		if (creatorId == null) {
			creatorId = userId;
		}
		Long newId = snowFlake.getNextId();
		po.setId(newId);
		po.setAuthId(authId);
		po.setUserId(userId);
		po.setCreateBy(creatorId);

		int count = userAuthMapper.insertSelective(po);
		if (count < 1) {
			log.error("insert user auth error userId: %s, authId: %s", userId, authId);
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResultCX insertBaseUserAuth(Long userId, AuthType authType) {
		CommonResultCX r = new CommonResultCX();
		FindAuthsResult authListResult = authService.findAuthsByCondition(authType);
		if(!authListResult.isSuccess()) {
			r.addMessage(authListResult.getMessage());
			return r;
		}
		List<Auth> authList = authListResult.getAuthList();
		if (authList == null || authList.size() < 1) {
			log.error("insert user auth error, auth not exists userId: %s, authType: %s", userId, authType.getName());
			return null;
		}
		
		Auth auth = authList.get(0);
		
		return insertUserAuth(userId, auth.getId());
	}
	
	@Override
	public CommonResultCX deleteUserAuth(Long userId, Long authId) {
		CommonResultCX r = new CommonResultCX();
		UserAuthExample example = new UserAuthExample();
		example.createCriteria().andUserIdEqualTo(userId).andAuthIdEqualTo(authId).andIsDeleteEqualTo(false);
		List<UserAuth> userAuthList = userAuthMapper.selectByExample(example);
		if(userAuthList == null || userAuthList.size() < 1) {
			return r;
		}
		
		UserAuth bo = userAuthList.get(0);
		bo.setIsDelete(true);
		bo.setUpdateTime(LocalDateTime.now());
		bo.setUpdateBy(baseUtilCustom.getUserId());
		int count = userAuthMapper.updateByPrimaryKey(bo);
		if(count > 0) {
			r.setIsSuccess();
		}
		return r;
	}
	
	@Override
	public CommonResultCX deleteUserAuth(Long userId, AuthType authType) {
		FindAuthsResult authListResult = authService.findAuthsByCondition(authType);
		
		if (!authListResult.isSuccess()) {
			return authListResult;
		}
		
		Auth auth = authListResult.getAuthList().get(0);
		
		return deleteUserAuth(userId, auth.getId());
	}
	
	@Override
	public CommonResultCX isActiveUser(Long userId) {
		CommonResultCX r = new CommonResultCX();
		UserAuthExample example = new UserAuthExample();
		example.createCriteria().andUserIdEqualTo(userId).andAuthIdEqualTo(AuthType.USER_ACTIVE.getCode());
		List<UserAuth> userAuthList = userAuthMapper.selectByExample(example);
		if(userAuthList != null && userAuthList.size() > 1) {
			r.setIsSuccess();
		}
		return r;
	}
	
	@Override
	public CommonResultCX hasActiveUser(List<Long> userIdList) {
		CommonResultCX r = new CommonResultCX();
		UserAuthExample example = new UserAuthExample();
		example.createCriteria().andUserIdIn(userIdList).andAuthIdEqualTo(AuthType.USER_ACTIVE.getCode());
		List<UserAuth> userAuthList = userAuthMapper.selectByExample(example);
		if(userAuthList != null && userAuthList.size() > 1) {
			r.setIsSuccess();
		}
		return r;
	}

	@Override
	public FindUserAuthResult findUserAuth(FindUserAuthDTO dto) {
		FindUserAuthResult r = new FindUserAuthResult();
		if(dto.getUserId() == null && dto.getAuthId() == null) {
			return r;
		}
		
		UserAuthExample example = new UserAuthExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo(false);
		if(dto.getUserId() != null) {
			criteria.andUserIdEqualTo(dto.getUserId());
		}
		if(dto.getAuthId() != null) {
			criteria.andAuthIdEqualTo(dto.getAuthId());
		}
		List<UserAuth> userAuthList = userAuthMapper.selectByExample(example);
		
		if(userAuthList != null && userAuthList.size() > 0) {
			FindAuthsConditionDTO findAuthDTO = new FindAuthsConditionDTO();
			List<Long> authIdList = userAuthList.stream().map(UserAuth::getAuthId).collect(Collectors.toList());
			findAuthDTO.setAuthIdList(authIdList);
			FindAuthsResult authListResult = authService.findAuthsByCondition(findAuthDTO);
			if(!authListResult.isSuccess() || authListResult.getAuthList() == null || authListResult.getAuthList().size() < 1) {
				r.addMessage(authListResult.getMessage());
				return r;
			}
			
			r.setUserAuthList(userAuthList);
			r.setAuthList(authListResult.getAuthList());
		}
		
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public CommonResultCX editUserAuth(EditUserAuthDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if(dto.getUserId() == null) {
			return r;
		}
		
		FindUserAuthDTO findUserAuthDTO = new FindUserAuthDTO();
		findUserAuthDTO.setUserId(dto.getUserId());
		FindUserAuthResult userAuthResult = findUserAuth(findUserAuthDTO);
		
		if(!userAuthResult.isSuccess()) {
			r.addMessage(userAuthResult.getMessage());
			return r;
		}
		
		Set<Long> oldAuthIdSet = null;
		if(userAuthResult.getAuthList() != null && userAuthResult.getAuthList().size() > 0) {
			oldAuthIdSet = userAuthResult.getUserAuthList().stream().map(UserAuth::getAuthId).collect(Collectors.toSet());
			for(Auth oldAuth : userAuthResult.getAuthList()) {
				if(!dto.getNewAuthIdList().contains(oldAuth.getId())) {
					r = deleteUserAuth(dto.getUserId(), oldAuth.getId());
					if(!r.isSuccess()) {
						return r;
					}
				}
			}
		} else {
			oldAuthIdSet = new HashSet<Long>();
		}
		
		if(dto.getNewAuthIdList() != null && dto.getNewAuthIdList().size() > 0) {
			for(Long authId : dto.getNewAuthIdList()) {
				if(!oldAuthIdSet.contains(authId)) {
					r = insertUserAuth(dto.getUserId(), authId);
					if(!r.isSuccess()) {
						return r;
					}
				}
			}
		}
		
		r.setIsSuccess();
		return r;
	}
}
