package demo.base.user.pojo.bo;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import demo.base.user.pojo.po.Users;

public class MyUserPrincipal implements UserDetails {

	private static final long serialVersionUID = 3333800775540529992L;

	private Users user;
	private String email;
	private String nickName;
	private List<String> roles;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MyUserPrincipal [user=" + user + ", email=" + email + ", nickName=" + nickName + ", roles=" + roles
				+ "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if (roles != null && roles.size() > 0) {
			for (String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
		}
		return authorities;
	}
	
	@Override
	public String getPassword() {
		if (user != null) {
			return user.getPwd();
		} else {
			return null;
		}
	}

	@Override
	public String getUsername() {
		if (user != null) {
			return user.getUserName();
		} else {
			return null;
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		if (user != null) {
			return user.getAccountNonExpired();
		} else {
			return false;
		}
	}

	@Override
	public boolean isAccountNonLocked() {
		if (user != null) {
			return user.getAccountNonLocked();
		} else {
			return false;
		}
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if (user != null) {
			return user.getCredentialsNonExpired();
		} else {
			return false;
		}
	}

	@Override
	public boolean isEnabled() {
		if (user != null) {
			return user.getEnabled();
		} else {
			return false;
		}
	}

}
