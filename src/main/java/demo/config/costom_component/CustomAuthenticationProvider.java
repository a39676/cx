package demo.config.costom_component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import demo.base.user.pojo.bo.CustomGrantedAuthorityBO;
import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.service.impl.CustomUserDetailsService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		MyUserPrincipal detail = customUserDetailsService.loadUserByUsername(name);
		List<CustomGrantedAuthorityBO> roleList = getAuthorityBoList(detail.getRoles());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(name, password, roleList);
		token.setDetails(detail);
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private List<CustomGrantedAuthorityBO> getAuthorityBoList(List<String> roleStrList) {
		List<CustomGrantedAuthorityBO> result = new ArrayList<>();
		if (roleStrList == null || roleStrList.size() < 1) {
			return new ArrayList<CustomGrantedAuthorityBO>();
		}
		for (int i = 0; i < roleStrList.size(); i++) {
			CustomGrantedAuthorityBO bo = new CustomGrantedAuthorityBO();
			bo.setRoleName(roleStrList.get(i));
			result.add(bo);
		}
		return result;
	}
}
