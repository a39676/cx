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
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindAuthsDTO;
import demo.base.user.pojo.dto.FindUserAuthDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.UserAuth;
import demo.base.user.pojo.po.UserAuthExample;
import demo.base.user.pojo.po.UserAuthExample.Criteria;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.service.AuthRoleService;
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
	@Autowired
	private AuthRoleService authRoleService;
	
	@Override
	public CommonResultCX insertUserAuth(Long userId, Long authId) {
		CommonResultCX r = new CommonResultCX();
		
		if(userId == null || authId == null) {
			r.failWithMessage("参数异常");
			return r;
		}
//		TODO
		
		
		/*
		 * TODO
		 * 鉴定指定角色, 当前用户可否更改操作的逻辑
		 * 放在???
		 */
		
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
//		TODO
		/*
		 * 未做权限鉴定
		 */
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
		
		if(userAuthList == null || userAuthList.isEmpty()) {
			return r;
		}
		
		FindAuthsDTO findAuthDTO = new FindAuthsDTO();
		List<Long> authIdList = userAuthList.stream().map(UserAuth::getAuthId).collect(Collectors.toList());
		findAuthDTO.setAuthIdList(authIdList);
		FindAuthsResult authListResult = authService.findAuthsByCondition(findAuthDTO);
		if(!authListResult.isSuccess() || authListResult.getAuthList() == null || authListResult.getAuthList().size() < 1) {
			r.addMessage(authListResult.getMessage());
			return r;
		}
		
		List<Auth> authList = authListResult.getAuthList();
		
		List<Long> orgIdList = authList.stream().map(Auth::getBelongOrg).collect(Collectors.toList());
		
		if(dto.getRoleTypeList() != null && !dto.getRoleTypeList().isEmpty()) {
			FindAuthRoleDTO findAuthRoleDTO = new FindAuthRoleDTO();
			findAuthRoleDTO.setAuthIdList(authIdList);
			findAuthRoleDTO.setRoleTypeList(dto.getRoleTypeList());
			findAuthRoleDTO.setOrgIdList(orgIdList);
			FindAuthRoleResult authRoleResult = authRoleService.findAuthRole(findAuthRoleDTO);
			if(!authRoleResult.isSuccess()) {
				r.addMessage(authRoleResult.getMessage());
				return r;
			}
			
			List<AuthRole> authRoleList = authRoleResult.getAuthRoleList();
			List<Long> filterAuthIdList = authRoleList.stream().map(AuthRole::getAuthId).collect(Collectors.toList());
			
			for(int i = 0; i < authList.size(); i++) {
				if(!filterAuthIdList.contains(authList.get(i).getId())) {
					authList.remove(i);
					i--;
				}
			}
			
			for(int i = 0; i < userAuthList.size(); i++) {
				if(!filterAuthIdList.contains(userAuthList.get(i).getAuthId())) {
					userAuthList.remove(i);
					i--;
				}
			}
			
		}
		
		r.setUserAuthList(userAuthList);
		r.setAuthList(authList);
		
		r.setIsSuccess();
		return r;
	}
	
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

	/**
	 * 
	 * @param operatorId 当前操作者用户ID
	 * @param authId 拟加入 / 删除的角色 ID (可能为自己增删角色, 可能为其他用户增删角色)
	 * @return
	 */
//	public CommonResultCX canEditUserAuth(Long operatorId, Long authId) {
////		TODO
//		
//		CommonResultCX r = new CommonResultCX();
//		if(operatorId == null || authId == null) {
//			r.failWithMessage("参数为空");
//			return r;
//		}
//		
//		FindAuthsResult findAuthResult = authService.findAuthsByCondition(authId);
//		if(!findAuthResult.isSuccess() || findAuthResult.getAuthList() == null || findAuthResult.getAuthList().isEmpty()) {
//			r.addMessage(findAuthResult.getMessage());
//			return r;
//		}
//		
//		Auth auth = findAuthResult.getAuthList().get(0);
//		
//		Organizations authOrg = orgService.getOrgById(auth.getBelongOrg());
//	}
}
