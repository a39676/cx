package demo.config.customComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.type.SystemRolesType;

public class BaseUtilCustom {

	public MyUserPrincipal getCurrentUser() {
		if (isLoginUser()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.getPrincipal() != null) {
				return (MyUserPrincipal) auth.getDetails();
			}
		}
		return null;
	}

	public String getCurrentUserName() {
		MyUserPrincipal userDetails = getCurrentUser();
		if (userDetails != null) {
			return userDetails.getUsername();
		} else {
			return null;
		}
	}

	public boolean isLoginUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext == null) {
			return false;
		}
		Authentication authentication = securityContext.getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return false;
		}
		return true;
//		return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}

	public List<String> getRoles() {
		List<String> roleList = new ArrayList<String>();
		if (isLoginUser()) {

			if (getCurrentUser() == null) {
				return roleList;
			}
			return getCurrentUser().getRoles();
		}
		return roleList;
	}

	public boolean hasAdminRole() {
		List<String> roleList = getRoles();
		for (int i = 0; i < roleList.size(); i++) {
			if (SystemRolesType.ROLE_ADMIN.getName().equals(roleList.get(i))
					|| SystemRolesType.ROLE_SUPER_ADMIN.getName().equals(roleList.get(i))) {
				return true;
			}
		}

		return false;
	}

	public boolean hasSuperAdminRole() {
		List<String> roleList = getRoles();
		for (int i = 0; i < roleList.size(); i++) {
			if (SystemRolesType.ROLE_SUPER_ADMIN.getName().equals(roleList.get(i))) {
				return true;
			}
		}

		return false;
	}

	public boolean hasAnyRole(List<String> inputRoleNameList) {
		if (inputRoleNameList == null || inputRoleNameList.size() < 1) {
			return false;
		}

		List<String> ownRoleList = getRoles();

		if (ownRoleList == null || ownRoleList.size() < 1) {
			return false;
		}

		String inputTmpRoleName = null;
		for (int i = 0; i < inputRoleNameList.size(); i++) {
			inputTmpRoleName = inputRoleNameList.get(i);
			if (ownRoleList.contains(inputTmpRoleName)) {
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
		if (StringUtils.isBlank(roleName)) {
			return false;
		}

		List<String> roleList = getRoles();

		if (roleList == null || roleList.size() < 1) {
			return false;
		}

		for (int i = 0; i < roleList.size(); i++) {
			if (String.valueOf(roleList.get(i)).equals(roleName)) {
				return true;
			}
		}

		return false;
	}

	public MyUserPrincipal getUserPrincipal() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext == null || securityContext.getAuthentication() == null
				|| securityContext.getAuthentication().getDetails() == null) {
			return new MyUserPrincipal();
		}

		Object obj = securityContext.getAuthentication().getDetails();
		if (obj instanceof MyUserPrincipal) {
			return (MyUserPrincipal) obj;
		} else {
			return new MyUserPrincipal();
		}
	}

	public Long getUserId() {
		MyUserPrincipal principal = getUserPrincipal();
		if (principal.getUser() != null) {
			return principal.getUser().getUserId();
		} else {
			return null;
		}
	}

}
