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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import demo.article.article.pojo.constant.ArticleAdminUrlConstant;
import demo.article.articleComment.pojo.constant.ArticleAdminCommentUrlConstant;
import demo.base.admin.pojo.constant.AdminUrlConstant;
import demo.base.system.pojo.constant.BaseUrl;
import demo.base.task.pojo.constant.TaskUrl;
import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.base.user.pojo.constant.UsersUrl;
import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.impl.CustomAuthenticationFailHandler;
import demo.base.user.service.impl.CustomAuthenticationSuccessHandler;
import demo.base.user.service.impl.CustomUserDetailsService;
import demo.config.costom_component.CustomAuthenticationProvider;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.config.costom_component.LimitLoginAuthenticationProvider;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinManagerUrl;
import demo.finance.cryptoCoin.sharing.pojo.constant.CryptoCoinSharingUrl;
import demo.finance.currencyExchangeRate.notice.pojo.constant.CurrencyExchangeRateNoticeUrl;
import demo.joy.common.pojo.constant.JoyManagerUrl;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.pmemo.pojo.constant.PMemoUrl;
import demo.pmemo.pojo.constant.UrgeNoticeManagerUrl;
import demo.pmemo.pojo.constant.UrgeNoticeUrl;
import demo.test.pojo.constant.TestUrl;
import demo.tool.other.pojo.constant.ToolUrlConstant;
import demo.toyParts.weixin.pojo.constant.WXUrl;
import image.pojo.constant.ImageInteractionUrl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
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
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/welcome**").permitAll()
            .antMatchers(LoginUrlConstant.LOGIN + "/**").permitAll()
            .antMatchers(UsersUrl.root + "/**").permitAll()
            .antMatchers("/static_resources/**").permitAll()
            .antMatchers("/tHome/**").permitAll()
            .antMatchers(WXUrl.root + WXUrl.weixin).permitAll()
//            .anyRequest().authenticated() 
            // used to allow anonymous access 
            // .antMatchers("/welcome**").access("IS_AUTHENTICATED_ANONYMOUSLY")
//            .antMatchers(ArticleUrlConstant.root + "/**").access("hasAnyRole('" + RolesType.ROLE_ADMIN.getRoleName() + "','" + RolesType.ROLE_USER.getRoleName() + "')")
            .antMatchers(BaseUrl.SHUTDOWN + "/**")
            	.access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
            .antMatchers(BaseUrl.OPTION_CONSTANT+ "/**")
            	.access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
            .antMatchers(TestUrl.root + "/**")
        		.access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
            .antMatchers("/holder/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_USER))
            .antMatchers("/accountInfo/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_USER))
            .antMatchers(AdminUrlConstant.root + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN))
            .antMatchers(ArticleAdminUrlConstant.root + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN))
            .antMatchers(ArticleAdminCommentUrlConstant.root + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN)) 
            .antMatchers("/dba/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_DBA)) 
            .antMatchers(ToolUrlConstant.root + "/**")
            	.access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
            .antMatchers(CryptoCoinManagerUrl.ROOT + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
            .antMatchers(CurrencyExchangeRateNoticeUrl.ROOT + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
            .antMatchers(PMemoUrl.ROOT + PMemoUrl.SET)
            	.access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
            .antMatchers(WXUrl.root + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_DEV))
            .antMatchers(UrgeNoticeManagerUrl.ROOT + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
            .antMatchers(TaskUrl.ROOT + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
            .antMatchers(CryptoCoinSharingUrl.ROOT + CryptoCoinSharingUrl.CALCULATE_DETAIL)
            	.permitAll()
            .antMatchers(CryptoCoinSharingUrl.ROOT + "/**")
            	.access("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_CRYPTO_SHARING_MANAGER')")
            // joy url start
            .antMatchers(JoyUrl.ROOT + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_USER_ACTIVE))
            .antMatchers(JoyManagerUrl.ROOT + "/**")
            	.access(hasAnyRole(SystemRolesType.ROLE_ADMIN, SystemRolesType.ROLE_SUPER_ADMIN))
            // joy url end
            	
            .and()
				.formLogin().loginPage("/login/login").failureUrl("/login/login?error")
				.loginProcessingUrl("/auth/login_check")
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailHandler)
				.usernameParameter("user_name").passwordParameter("pwd")
			.and()
		    	.logout().logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))
		    .and()
		    	.exceptionHandling().accessDeniedPage("/403")
		    .and()
		    	.rememberMe().tokenRepository(persistentTokenRepository())
		    	.tokenValiditySeconds(3600)
		    .and()
		    	.authorizeRequests()
		    .and()
		    	.csrf()
		    		.ignoringAntMatchers(UrgeNoticeUrl.ROOT + "/**")
//		    尝试搭建 web socket, 修改同源策略
//		    .and()
//		        .headers().frameOptions().sameOrigin()
		    ;
	  
        /*
         * 增加filter在此  同样操作 但建议使用
         * http.addFilterAfter(newFilter,CsrfFilter.class); 
         * 2020-03-18 
         * 编码过滤拟转移到 application.yml
         */
//        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
//        encodingFilter.setEncoding(StandardCharsets.UTF_8.displayName());
//        encodingFilter.setForceEncoding(true);
//        http.addFilterBefore(encodingFilter, CsrfFilter.class);
        
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	    .antMatchers("/test/testIgnoring")
	    .antMatchers(ImageInteractionUrl.ROOT + "/**")
	    ;
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
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
	
	private String hasRole(SystemRolesType roleType) {
		if(roleType == null) {
			return "hasRole('')";
		}
		
		return "hasRole('"+ roleType.getName() +"')";
	}
	
//	private String hasRole(OrganzationRolesType roleType) {
//		if(roleType == null) {
//			return "hasRole('')";
//		}
//		
//		return "hasRole('"+ roleType.getName() +"')";
//	}
	
	private String hasAnyRole(SystemRolesType... roleTypes) {
		return hasAnyRole(Arrays.asList(roleTypes), null);
	}
	
//	private String hasAnyRole(OrganzationRolesType... roleTypes) {
//		return hasAnyRole(null, Arrays.asList(roleTypes));
//	}
	
	private String hasAnyRole(List<SystemRolesType> sysRoleTypes, List<OrganzationRolesType> orgRoleTypes) {
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
