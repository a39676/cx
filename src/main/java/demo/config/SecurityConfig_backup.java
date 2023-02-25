//package demo.config;
//
//import java.util.Arrays;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import demo.article.article.pojo.constant.ArticleAdminUrlConstant;
//import demo.article.articleComment.pojo.constant.ArticleAdminCommentUrlConstant;
//import demo.base.admin.pojo.constant.AdminUrlConstant;
//import demo.base.system.pojo.constant.BaseUrl;
//import demo.base.task.pojo.constant.TaskUrl;
//import demo.base.user.pojo.constant.UsersUrl;
//import demo.base.user.pojo.type.OrganzationRolesType;
//import demo.base.user.pojo.type.SystemRolesType;
//import demo.base.user.service.impl.CustomAuthenticationFailHandler;
//import demo.base.user.service.impl.CustomAuthenticationSuccessHandler;
//import demo.base.user.service.impl.CustomUserDetailsService;
//import demo.config.costom_component.CustomAuthenticationProvider;
//import demo.config.costom_component.CustomPasswordEncoder;
//import demo.config.costom_component.LimitLoginAuthenticationProvider;
//import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinManagerUrl;
//import demo.finance.cryptoCoin.sharing.pojo.constant.CryptoCoinSharingUrl;
//import demo.finance.currencyExchangeRate.notice.pojo.constant.CurrencyExchangeRateNoticeUrl;
//import demo.joy.common.pojo.constant.JoyManagerUrl;
//import demo.joy.common.pojo.constant.JoyUrl;
//import demo.pmemo.pojo.constant.PMemoUrl;
//import demo.pmemo.pojo.constant.UrgeNoticeManagerUrl;
//import demo.pmemo.pojo.constant.UrgeNoticeUrl;
//import demo.test.pojo.constant.TestUrl;
//import demo.tool.other.pojo.constant.ToolUrlConstant;
//import demo.toyParts.weixin.pojo.constant.WXUrl;
//import image.pojo.constant.ImageInteractionUrl;
//
//@Configuration
////@EnableWebSecurity
//public class SecurityConfig_backup {
//	
//	@Autowired
//	private DataSource dataSource;
//	@Autowired
//	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
//	@Autowired
//	private CustomAuthenticationFailHandler customAuthenticationFailHandler;
//	@Autowired
//    private CustomAuthenticationProvider authProvider;
//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authProvider);
//	}
//	
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//	    DaoAuthenticationProvider authProvider = new LimitLoginAuthenticationProvider();
//	    authProvider.setUserDetailsService(customUserDetailsService);
//	    authProvider.setPasswordEncoder(passwordEncoder());
//	    return authProvider;
//	}
//	
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests(
//				(requests) -> requests
//				.requestMatchers(new AntPathRequestMatcher("/welcome**")).permitAll()
////				.requestMatchers(new AntPathRequestMatcher(LoginUrlConstant.LOGIN + "/**")).permitAll()
//				.requestMatchers(new AntPathRequestMatcher(UsersUrl.root + "/**")).permitAll()
//				.requestMatchers(new AntPathRequestMatcher("/static_resources/**")).permitAll()
//				.requestMatchers(new AntPathRequestMatcher("/tHome/**")).permitAll()
//				.requestMatchers(new AntPathRequestMatcher(WXUrl.root + WXUrl.weixin)).permitAll()
//				.requestMatchers(new AntPathRequestMatcher(BaseUrl.SHUTDOWN + "/**")).hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName())
//				.requestMatchers(new AntPathRequestMatcher(BaseUrl.OPTION_CONSTANT+ "/**")).hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName())
//				.requestMatchers(new AntPathRequestMatcher(TestUrl.root + "/**")).hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName())
//				.requestMatchers(new AntPathRequestMatcher("/holder/****")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_USER))
//				.requestMatchers(new AntPathRequestMatcher("/accountInfo/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_USER))
//				.requestMatchers(new AntPathRequestMatcher(AdminUrlConstant.root + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher(ArticleAdminUrlConstant.root + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher(ArticleAdminCommentUrlConstant.root + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher("/dba/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_DBA))
//				.requestMatchers(new AntPathRequestMatcher(ToolUrlConstant.root + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher(CryptoCoinManagerUrl.ROOT + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher(CurrencyExchangeRateNoticeUrl.ROOT + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher(PMemoUrl.ROOT + PMemoUrl.SET)).hasAnyRole(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher(WXUrl.root + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_DEV))
//				.requestMatchers(new AntPathRequestMatcher(UrgeNoticeManagerUrl.ROOT + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher(TaskUrl.ROOT + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
//				.requestMatchers(new AntPathRequestMatcher(CryptoCoinSharingUrl.ROOT + CryptoCoinSharingUrl.CALCULATE_DETAIL)).permitAll()
//				.requestMatchers(new AntPathRequestMatcher(CryptoCoinSharingUrl.ROOT + "/**")).hasAnyRole("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_CRYPTO_SHARING_MANAGER")
//				.requestMatchers(new AntPathRequestMatcher(JoyUrl.ROOT + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_USER_ACTIVE))
//				.requestMatchers(new AntPathRequestMatcher(JoyManagerUrl.ROOT + "/**")).hasAnyRole(buildRolesName(SystemRolesType.ROLE_ADMIN, SystemRolesType.ROLE_SUPER_ADMIN))
//	            .anyRequest().authenticated()
//	            )
//		.formLogin((form) -> form
//				.loginPage("/login/login")
//				.loginProcessingUrl("/auth/login_check")
//				.successHandler(customAuthenticationSuccessHandler)
//				.failureHandler(customAuthenticationFailHandler)
//				.usernameParameter("user_name").passwordParameter("pwd")
//				.permitAll())
////		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))
//		.logout((logout) -> logout.permitAll())
//		.exceptionHandling().accessDeniedPage("/403")
//		.and()
//			.rememberMe().tokenRepository(persistentTokenRepository())
//			.tokenValiditySeconds(3600)
//		.and()
//	    	.csrf()
//	    	.ignoringRequestMatchers(UrgeNoticeUrl.ROOT + "/**")
//	    	.ignoringRequestMatchers(ImageInteractionUrl.ROOT + "/**")
//		;
//	  
//        return http.build();
//	}
//	
////	@Bean
////    public AuthenticationManagerBuilder buildAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
////        auth.authenticationProvider(authProvider());
////        return auth;
////    }
//
//	@Bean
//	public PersistentTokenRepository persistentTokenRepository() {
//		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
//		db.setDataSource(dataSource);
//		return db;
//	}
//
//	@Bean
//	public CustomPasswordEncoder passwordEncoder(){
//		return new CustomPasswordEncoder();
//	}
//	
//	private String hasRole(SystemRolesType roleType) {
//		if(roleType == null) {
//			return "hasRole('')";
//		}
//		
//		return "hasRole('"+ roleType.getName() +"')";
//	}
//	
////	private String hasRole(OrganzationRolesType roleType) {
////		if(roleType == null) {
////			return "hasRole('')";
////		}
////		
////		return "hasRole('"+ roleType.getName() +"')";
////	}
//	
//	private String buildRolesName(SystemRolesType... roleTypes) {
//		return buildRolesName(Arrays.asList(roleTypes), null);
//	}
//	
////	private String hasAnyRole(OrganzationRolesType... roleTypes) {
////		return hasAnyRole(null, Arrays.asList(roleTypes));
////	}
//	
//	private String buildRolesName(List<SystemRolesType> sysRoleTypes, List<OrganzationRolesType> orgRoleTypes) {
//		StringBuffer roleExpressionBuilder = new StringBuffer("hasAnyRole(");
//		
//		if((sysRoleTypes == null || sysRoleTypes.isEmpty())
//				&& (orgRoleTypes == null || orgRoleTypes.isEmpty())) {
//			return "hasRole('')";
//		}
//		
//		if(sysRoleTypes != null) {
//			for(SystemRolesType roleType : sysRoleTypes) {
//				if(roleType != null) {
//					roleExpressionBuilder.append("'" + roleType.getName() + "',");
//				}
//			}
//		}
//		
//		if(orgRoleTypes != null) {
//			for(OrganzationRolesType roleType : orgRoleTypes) {
//				if(roleType != null) {
//					roleExpressionBuilder.append("'" + roleType.getName() + "',");
//				}
//			}
//		}
//		
//		roleExpressionBuilder.replace(
//				roleExpressionBuilder.length()-1, 
//				roleExpressionBuilder.length(), 
//				"");
//		
//		roleExpressionBuilder.append(")");
//		return roleExpressionBuilder.toString();
//	}
//	
//}
