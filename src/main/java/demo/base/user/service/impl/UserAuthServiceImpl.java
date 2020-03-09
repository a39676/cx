package demo.base.user.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.UserAuthMapper;
import demo.base.user.pojo.bo.EditUserAuthBO;
import demo.base.user.pojo.bo.FindAuthsBO;
import demo.base.user.pojo.bo.FindUserAuthBO;
import demo.base.user.pojo.dto.EditUserAuthDTO;
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindUserAuthDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.UserAuth;
import demo.base.user.pojo.po.UserAuthExample;
import demo.base.user.pojo.po.UserAuthExample.Criteria;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.result.FindUserAuthVOResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.pojo.vo.AuthVO;
import demo.base.user.pojo.vo.UserAuthVO;
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
	public CommonResultCX insertUserAuth(EditUserAuthDTO dto) {
		Long authId = decryptPrivateKey(dto.getNewAuthPk());
		Long userId = decryptPrivateKey(dto.getUserPk());
		return insertUserAuth(userId, authId);
	}
	
	@Override
	public CommonResultCX insertUserAuth(EditUserAuthBO bo) {
		return insertUserAuth(bo.getUserId(), bo.getAuthId());
	}
	
	private CommonResultCX insertUserAuth(Long userId, Long authId) {
		CommonResultCX r = new CommonResultCX();
		
		if(userId == null || authId == null) {
			r.failWithMessage("参数异常");
			return r;
		}

		CommonResultCX canEditResult = authService.canEditUserAuth(authId);
		if(!canEditResult.isSuccess()) {
			r.addMessage(canEditResult.getMessage());
			return r;
		}
		
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
	public CommonResultCX deleteUserAuth(EditUserAuthDTO dto) {
		EditUserAuthBO bo = new EditUserAuthBO();
		bo.setAuthId(decryptPrivateKey(dto.getNewAuthPk()));
		bo.setUserId(decryptPrivateKey(dto.getUserPk()));
		
		return deleteUserAuth(bo);
	}
	
	@Override
	public CommonResultCX deleteUserAuth(EditUserAuthBO bo) {
		CommonResultCX r = new CommonResultCX();
		
		if(bo.getAuthId() == null && bo.getUserId() == null) {
			r.failWithMessage("error param");
			return r;
		}
		
		CommonResultCX canEditResult = authService.canEditUserAuth(bo.getAuthId());
		if(!canEditResult.isSuccess()) {
			r.addMessage(canEditResult.getMessage());
			return r;
		}
		
		UserAuthExample example = new UserAuthExample();
		Criteria userAuthCriteria = example.createCriteria();
		if(bo.getAuthId() != null) {
			userAuthCriteria.andAuthIdEqualTo(bo.getAuthId());
		}
		if(bo.getUserId() != null) {
			userAuthCriteria.andUserIdEqualTo(bo.getUserId());
		}
		userAuthCriteria.andIsDeleteEqualTo(false);
		List<UserAuth> userAuthList = userAuthMapper.selectByExample(example);
		if(userAuthList == null || userAuthList.size() < 1) {
			r.setIsSuccess();
		} else {
			UserAuth po = new UserAuth();
			po.setIsDelete(true);
			po.setUpdateTime(LocalDateTime.now());
			po.setUpdateBy(baseUtilCustom.getUserId());
			int count = userAuthMapper.updateByExampleSelective(po, example);
			if(count == userAuthList.size()) {
				r.setIsSuccess();
			}
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
	public FindUserAuthVOResult findUserAuthVO(FindUserAuthDTO dto) {
		FindUserAuthVOResult r = new FindUserAuthVOResult();
		
		FindUserAuthResult poResult = findUserAuth(dto);
		if(!poResult.isSuccess()) {
			r.addMessage(poResult.getMessage());
			return r;
		}
		
		List<UserAuthVO> userAuthVOList = new ArrayList<UserAuthVO>();
		for(UserAuth po : poResult.getUserAuthList()) {
			userAuthVOList.add(buildUserAuthVOByPO(po));
		}
		r.setUserAuthVOList(userAuthVOList);
		
		List<AuthVO> authVOList = new ArrayList<AuthVO>();
		for(Auth po : poResult.getAuthList()) {
			authVOList.add(authService.buildAuthVOByPO(po));
		}
		r.setAuthVOList(authVOList);
		
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public FindUserAuthResult findUserAuth(FindUserAuthDTO dto) {
		FindUserAuthBO bo = new FindUserAuthBO();
		bo.setAuthId(decryptPrivateKey(dto.getAuthPk()));
		bo.setUserId(decryptPrivateKey(dto.getUserPk()));
		bo.setSysRoleTypeList(dto.getSysRoleTypeList());
		bo.setOrgRoleTypeList(dto.getOrgRoleTypeList());
		return findUserAuth(bo);
	}
	
	@Override
	public FindUserAuthResult findUserAuth(FindUserAuthBO bo) {
		FindUserAuthResult r = new FindUserAuthResult();
		if(bo.getUserId() == null && bo.getAuthId() == null) {
			return r;
		}
		
		UserAuthExample example = new UserAuthExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo(false);
		if(bo.getUserId() != null) {
			criteria.andUserIdEqualTo(bo.getUserId());
		}
		if(bo.getAuthId() != null) {
			criteria.andAuthIdEqualTo(bo.getAuthId());
		}
		List<UserAuth> userAuthList = userAuthMapper.selectByExample(example);
		
		if(userAuthList == null || userAuthList.isEmpty()) {
			return r;
		}
		
		FindAuthsBO findAuthBO = new FindAuthsBO();
		List<Long> authIdList = userAuthList.stream().map(UserAuth::getAuthId).collect(Collectors.toList());
		findAuthBO.setAuthIdList(authIdList);
		FindAuthsResult authListResult = authService.findAuthsByCondition(findAuthBO);
		if(!authListResult.isSuccess() || authListResult.getAuthList() == null || authListResult.getAuthList().size() < 1) {
			r.addMessage(authListResult.getMessage());
			return r;
		}
		
		List<Auth> authList = authListResult.getAuthList();
		
		List<Long> orgIdList = authList.stream().map(Auth::getBelongOrg).collect(Collectors.toList());
		
		if((bo.getSysRoleTypeList() != null && !bo.getSysRoleTypeList().isEmpty())
				|| bo.getOrgRoleTypeList() != null && !bo.getOrgRoleTypeList().isEmpty()
				) {
			FindAuthRoleDTO findAuthRoleDTO = new FindAuthRoleDTO();
			findAuthRoleDTO.setAuthIdList(authIdList);
			findAuthRoleDTO.setSysRoleTypeList(bo.getSysRoleTypeList());
			findAuthRoleDTO.setOrgRoleTypeList(bo.getOrgRoleTypeList());
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
	
	private UserAuthVO buildUserAuthVOByPO(UserAuth po) {
		UserAuthVO vo = new UserAuthVO();
		vo.setAuthPk(encryptId(po.getAuthId()));
		vo.setIsDelete(po.getIsDelete());
		vo.setPk(encryptId(po.getId()));
		vo.setUserPk(encryptId(po.getUserId()));
		return vo;
	}
	

	/*
	 * 批量编辑角色的逻辑
	 * 暂时不启用
	 */
//	public CommonResultCX editUserAuth(EditUserAuthDTO dto) {
//		CommonResultCX r = new CommonResultCX();
//		
//		Long authId = decryptPrivateKey(dto.getNewAuthPk());
//		Long userId = decryptPrivateKey(dto.getUserPk());
//		
//		if(authId == null || userId == null) {
//			r.failWithMessage("error param");
//			return r;
//		}
//		
//		
//		FindUserAuthDTO findUserAuthDTO = new FindUserAuthDTO();
//		findUserAuthDTO.setUserId(userId);
//		FindUserAuthResult userAuthResult = findUserAuth(findUserAuthDTO);
//		
//		if(!userAuthResult.isSuccess()) {
//			r.addMessage(userAuthResult.getMessage());
//			return r;
//		}
//		
//		Set<Long> oldAuthIdSet = null;
//		if(userAuthResult.getAuthList() != null && userAuthResult.getAuthList().size() > 0) {
//			oldAuthIdSet = userAuthResult.getUserAuthList().stream().map(UserAuth::getAuthId).collect(Collectors.toSet());
//			for(Auth oldAuth : userAuthResult.getAuthList()) {
//				if(!dto.getNewAuthIdList().contains(oldAuth.getId())) {
//					r = deleteUserAuth(userId, oldAuth.getId());
//					if(!r.isSuccess()) {
//						return r;
//					}
//				}
//			}
//		} else {
//			oldAuthIdSet = new HashSet<Long>();
//		}
//		
//		if(dto.getNewAuthIdList() != null && dto.getNewAuthIdList().size() > 0) {
//			for(Long authId : dto.getNewAuthIdList()) {
//				if(!oldAuthIdSet.contains(authId)) {
//					r = insertUserAuth(userId, authId);
//					if(!r.isSuccess()) {
//						return r;
//					}
//				}
//			}
//		}
//		
//		r.setIsSuccess();
//		return r;
//	}
}
