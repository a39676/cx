package demo.base.user.pojo.bo;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthorityBO implements GrantedAuthority {

	private static final long serialVersionUID = -1638208464681969796L;

	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String getAuthority() {
		return this.roleName;
	}

}
