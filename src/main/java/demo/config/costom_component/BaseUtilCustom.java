package demo.config.costom_component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.type.RolesType;

public class BaseUtilCustom {
	
	public UserDetails getCurrentUser() {
		if(isLoginUser()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null && auth.getPrincipal() != null) {
				return (UserDetails) auth.getPrincipal();
			}
		}
		return null;
	}
	
	public String getCurrentUserName() {
		UserDetails userDetails = getCurrentUser();
		if(userDetails != null) {
			return userDetails.getUsername();
		} else {
			return null;
		}
	}
	
	public boolean isLoginUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(securityContext == null) {
			return false;
		}
		Authentication authentication = securityContext.getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return false;
		}
		return true;
//		return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}
	
	public List<String> getRoles() {
		List<String> roleList = new ArrayList<String>();
		if(isLoginUser()) {
			
			if(getCurrentUser() == null) {
				return roleList;
			}
			
			Collection<? extends GrantedAuthority> userDetails = getCurrentUser().getAuthorities();
			if(userDetails != null) {
				for(Object ele : getCurrentUser().getAuthorities()) {
					roleList.add(String.valueOf(ele));
				}
			}
		}
		return roleList;
	}
	
	public boolean hasAdminRole() {
		List<String> roleList = getRoles();
		for(int i = 0; i < roleList.size(); i++) {
			if(RolesType.ROLE_ADMIN.getName().equals(roleList.get(i))
					|| RolesType.ROLE_SUPER_ADMIN.getName().equals(roleList.get(i))
					) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSuperAdminRole() {
		List<String> roleList = getRoles();
		for(int i = 0; i < roleList.size(); i++) {
			if(RolesType.ROLE_SUPER_ADMIN.getName().equals(roleList.get(i))) {
				return true;
			}
		}
		
		return false;
	}

	public boolean hasAnyRole(List<String> inputRoleNameList) {
		if(inputRoleNameList == null || inputRoleNameList.size() < 1) {
			return false;
		}
		
		List<String> ownRoleList = getRoles();
		
		if(ownRoleList == null || ownRoleList.size() < 1) {
			return false;
		}
		
		String inputTmpRoleName = null;
		for(int i = 0; i < inputRoleNameList.size(); i++) {
			inputTmpRoleName = inputRoleNameList.get(i);
			if(ownRoleList.contains(inputTmpRoleName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasAnyRole(String... inputRoleName) {
		List<String> inputRoleNameList = Arrays.asList(inputRoleName);
		return hasAnyRole(inputRoleNameList);
	}
	
	public boolean hasRole(String roleName) {
		if(StringUtils.isBlank(roleName)) {
			return false;
		}
		
		List<String> roleList = getRoles();
		
		if(roleList == null || roleList.size() < 1) {
			return false;
		}
		
		for(int i = 0; i < roleList.size(); i++) {
			if(String.valueOf(roleList.get(i)).equals(roleName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public MyUserPrincipal getUserPrincipal() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if(securityContext == null || securityContext.getAuthentication() == null || securityContext.getAuthentication().getPrincipal() == null) {
			return new MyUserPrincipal();
		}
		
		Object obj = securityContext.getAuthentication().getPrincipal();
		if(obj instanceof MyUserPrincipal) {
			return (MyUserPrincipal) obj;
		} else {
			return new MyUserPrincipal();
		}
	}
	
	public Long getUserId() {
		MyUserPrincipal principal = getUserPrincipal();
		if(principal.getUser() != null) {
			return principal.getUser().getUserId();
		} else {
			return null;
		}
	}

//	public boolean setAuthDetail(HashMap<String, Object> detailMap) {
//		try {
//			AbstractAuthenticationToken auth = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//			auth.setDetails(detailMap);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
	
//	@SuppressWarnings("unchecked")
//	public HashMap<String, Object> getUserDetailOld() {
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		if(securityContext == null || securityContext.getAuthentication() == null || securityContext.getAuthentication().getDetails() == null) {
//			return new HashMap<String, Object>();
//		}
//		
//		securityContext.getAuthentication().getPrincipal();
//		
//		Object obj = SecurityContextHolder.getContext().getAuthentication().getDetails();
//		if(obj instanceof HashMap) {
//			return (HashMap<String, Object>) obj;
//		} else {
//			return new HashMap<String, Object>();
//		}
//	}
}
