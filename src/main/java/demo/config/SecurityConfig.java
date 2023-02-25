package demo.config;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.impl.CustomAuthenticationFailHandler;
import demo.base.user.service.impl.CustomAuthenticationSuccessHandler;
import demo.base.user.service.impl.CustomUserDetailsService;
import demo.config.costom_component.CustomAuthenticationProvider;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.config.costom_component.LimitLoginAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private CustomAuthenticationFailHandler customAuthenticationFailHandler;
	@Autowired
    private CustomAuthenticationProvider authProvider;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new LimitLoginAuthenticationProvider();
	    authProvider.setUserDetailsService(customUserDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests
				.requestMatchers(new AntPathRequestMatcher("/welcome**")).permitAll()
//				.requestMatchers(new AntPathRequestMatcher(LoginUrlConstant.LOGIN + "/**")).permitAll()
	            .anyRequest().authenticated()
	            )
		.formLogin((form) -> form
				.loginPage("/login/login")
				.loginProcessingUrl("/auth/login_check")
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailHandler)
				.usernameParameter("user_name").passwordParameter("pwd")
				.permitAll())
//		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))
		.logout((logout) -> logout.permitAll())
		.exceptionHandling().accessDeniedPage("/403")
		.and()
			.rememberMe().tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(3600)
		.and()
	    	.csrf()
		;
	  
        return http.build();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public CustomPasswordEncoder passwordEncoder(){
		return new CustomPasswordEncoder();
	}
	
	@SuppressWarnings("unused")
	private String hasRole(SystemRolesType roleType) {
		if(roleType == null) {
			return "hasRole('')";
		}
		
		return "hasRole('"+ roleType.getName() +"')";
	}
	
	@SuppressWarnings("unused")
	private String buildRolesName(SystemRolesType... roleTypes) {
		return buildRolesName(Arrays.asList(roleTypes), null);
	}
	
	private String buildRolesName(List<SystemRolesType> sysRoleTypes, List<OrganzationRolesType> orgRoleTypes) {
		StringBuffer roleExpressionBuilder = new StringBuffer("hasAnyRole(");
		
		if((sysRoleTypes == null || sysRoleTypes.isEmpty())
				&& (orgRoleTypes == null || orgRoleTypes.isEmpty())) {
			return "hasRole('')";
		}
		
		if(sysRoleTypes != null) {
			for(SystemRolesType roleType : sysRoleTypes) {
				if(roleType != null) {
					roleExpressionBuilder.append("'" + roleType.getName() + "',");
				}
			}
		}
		
		if(orgRoleTypes != null) {
			for(OrganzationRolesType roleType : orgRoleTypes) {
				if(roleType != null) {
					roleExpressionBuilder.append("'" + roleType.getName() + "',");
				}
			}
		}
		
		roleExpressionBuilder.replace(
				roleExpressionBuilder.length()-1, 
				roleExpressionBuilder.length(), 
				"");
		
		roleExpressionBuilder.append(")");
		return roleExpressionBuilder.toString();
	}
	
}
