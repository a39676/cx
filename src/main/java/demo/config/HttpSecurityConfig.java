package demo.config;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ai.aiArt.pojo.constant.AiArtApiUrlConstant;
import ai.aiChat.pojo.constant.AiChatApiUrlConstant;
import ai.aiChat.pojo.constant.AiChatFromWechatSdkUrlConstant;
import demo.ai.aiArt.pojo.constant.AiArtMangerUrl;
import demo.ai.manager.pojo.constant.AiManagerUrlConstant;
import demo.article.article.pojo.constant.ArticleAdminUrlConstant;
import demo.article.articleComment.pojo.constant.ArticleAdminCommentUrlConstant;
import demo.base.admin.pojo.constant.AdminUrlConstant;
import demo.base.system.pojo.constant.BaseUrl;
import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.base.user.pojo.constant.UsersUrl;
import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.impl.CustomAuthenticationFailHandler;
import demo.base.user.service.impl.CustomAuthenticationSuccessHandler;
import demo.common.service.CommonService;
import demo.config.costom_component.CustomAuthenticationProvider;
import demo.config.costom_component.CustomPasswordEncoder;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinManagerUrl;
import demo.finance.cryptoCoin.sharing.pojo.constant.CryptoCoinSharingUrl;
import demo.finance.currencyExchangeRate.notice.pojo.constant.CurrencyExchangeRateNoticeUrl;
import demo.image.pojo.constant.ImageUrl;
import demo.joy.common.pojo.constant.JoyManagerUrl;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.pmemo.pojo.constant.PMemoUrl;
import demo.pmemo.pojo.constant.UrgeNoticeManagerUrl;
import demo.pmemo.pojo.constant.UrgeNoticeUrl;
import demo.test.pojo.constant.TestUrl;
import demo.tool.other.pojo.constant.ToolUrlConstant;
import demo.tool.wordHelper.pojo.constant.WordHelperUrl;
import image.pojo.constant.ImageInteractionUrl;
import wechatPaySdk.jsApi.pojo.constant.WechatPaySdkUrlConstant;
import wechatSdk.pojo.constant.WechatSdkUrlConstant;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig extends CommonService {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private CustomAuthenticationFailHandler customAuthenticationFailHandler;
	@Autowired
	private CustomAuthenticationProvider authProvider;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.requestMatchers("/welcome**").permitAll()
		.requestMatchers(LoginUrlConstant.LOGIN + "/**") .permitAll()
		.requestMatchers(UsersUrl.root + "/**").permitAll()
		.requestMatchers("/static_resources/**") .permitAll()
		.requestMatchers("/tHome/**").permitAll()
		.requestMatchers(BaseUrl.SHUTDOWN + "/**").access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
		.requestMatchers(BaseUrl.OPTION_CONSTANT + "/**").access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
		.requestMatchers(TestUrl.root + "/**").access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
		.requestMatchers("/holder/**") .access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_USER))
		.requestMatchers("/accountInfo/**").access(hasAnyRole(SystemRolesType.ROLE_USER))
		.requestMatchers(AdminUrlConstant.root + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN))
		.requestMatchers(ArticleAdminUrlConstant.root + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN))
		.requestMatchers(ArticleAdminCommentUrlConstant.root + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN))
		.requestMatchers("/dba/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_DBA))
		.requestMatchers(ToolUrlConstant.root + "/**").access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
		.requestMatchers(CryptoCoinManagerUrl.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
		.requestMatchers(CurrencyExchangeRateNoticeUrl.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
		.requestMatchers(PMemoUrl.ROOT + PMemoUrl.SET).access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
		.requestMatchers(UrgeNoticeManagerUrl.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
		.requestMatchers(CryptoCoinSharingUrl.ROOT + CryptoCoinSharingUrl.CALCULATE_DETAIL).permitAll()
		.requestMatchers(CryptoCoinSharingUrl.ROOT + "/**").access("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_CRYPTO_SHARING_MANAGER')")
		.requestMatchers(AiManagerUrlConstant.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
		.requestMatchers(AiArtMangerUrl.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
		.requestMatchers(WordHelperUrl.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_USER_ACTIVE, SystemRolesType.ROLE_STUDENT))
		.requestMatchers(JoyUrl.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_USER_ACTIVE))
		.requestMatchers(JoyManagerUrl.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_ADMIN, SystemRolesType.ROLE_SUPER_ADMIN))
		
		.and().formLogin().loginPage("/login/login").failureUrl("/login/login?error")
		.loginProcessingUrl("/auth/login_check").successHandler(customAuthenticationSuccessHandler)
		.failureHandler(customAuthenticationFailHandler).usernameParameter("user_name").passwordParameter("pwd")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")).and()
		.exceptionHandling().accessDeniedPage("/403").and().rememberMe()
		.tokenRepository(persistentTokenRepository()).tokenValiditySeconds(3600).and().authorizeRequests().and()
		.csrf();
		;
//		http.securityMatcher(BaseUrl.SHUTDOWN + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasRole("SUPER_ADMIN"))
//				.securityMatcher(BaseUrl.OPTION_CONSTANT + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(TestUrl.root + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher("/holder/**")
//				.authorizeHttpRequests(authorize -> authorize.anyRequest()
//						.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_USER.getName()))
//				.securityMatcher("/accountInfo/**")
//				.authorizeHttpRequests(authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_USER.getName()))
//				.securityMatcher(AdminUrlConstant.root + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(ArticleAdminUrlConstant.root + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher("/dba/**")
//				.authorizeHttpRequests(authorize -> authorize.anyRequest()
//						.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_DBA.getName()))
//				.securityMatcher(ToolUrlConstant.root + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(CryptoCoinManagerUrl.ROOT + "/**")
//				.authorizeHttpRequests(authorize -> authorize.anyRequest()
//						.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()))
//				.securityMatcher(CurrencyExchangeRateNoticeUrl.ROOT + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(PMemoUrl.ROOT + PMemoUrl.SET)
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(UrgeNoticeManagerUrl.ROOT + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(CryptoCoinSharingUrl.ROOT + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(AiManagerUrlConstant.ROOT + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(AiArtMangerUrl.ROOT + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_SUPER_ADMIN.getName()))
//				.securityMatcher(WordHelperUrl.ROOT + "/**")
//				.authorizeHttpRequests(authorize -> authorize.anyRequest()
//						.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_STUDENT.getName()))
//				.securityMatcher(JoyUrl.ROOT + "/**")
//				.authorizeHttpRequests(
//						authorize -> authorize.anyRequest().hasAuthority(SystemRolesType.ROLE_USER_ACTIVE.getName()))
//				.securityMatcher(JoyManagerUrl.ROOT + "/**")
//				.authorizeHttpRequests(authorize -> authorize.anyRequest()
//						.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()))
//				.formLogin(authorize -> authorize.loginPage("/login/login").failureUrl("/login/login?error")
//						.loginProcessingUrl("/auth/login_check").successHandler(customAuthenticationSuccessHandler)
//						.failureHandler(customAuthenticationFailHandler).usernameParameter("user_name")
//						.passwordParameter("pwd"))
//				.logout(authorize -> authorize.logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")))
//				.exceptionHandling(authorize -> authorize.accessDeniedPage("/403"));
		
//		http.authorizeRequests().requestMatchers("/welcome**").permitAll()
//				.requestMatchers(LoginUrlConstant.LOGIN + "/**").permitAll().requestMatchers(UsersUrl.root + "/**")
//				.permitAll().requestMatchers("/static_resources/**").permitAll().requestMatchers("/tHome/**")
//				// joy url start
//				// joy url end
//				.and().formLogin().loginPage("/login/login").failureUrl().loginProcessingUrl("/auth/login_check")
//				.successHandler(customAuthenticationSuccessHandler).failureHandler(customAuthenticationFailHandler)
//				.usernameParameter("user_name").passwordParameter("pwd").and().logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")).and().exceptionHandling()
//				.accessDeniedPage("/403").and().rememberMe().tokenRepository(persistentTokenRepository())
//				.tokenValiditySeconds(3600).and().authorizeRequests().and().csrf()
//				.ignoringRequestMatchers(UrgeNoticeUrl.ROOT + "/**");
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/test/testIgnoring")
				.requestMatchers(ImageInteractionUrl.ROOT + "/**")
				.requestMatchers(AiChatFromWechatSdkUrlConstant.ROOT + "/**")
				.requestMatchers(WechatPaySdkUrlConstant.ROOT + "/**")
				.requestMatchers(WechatSdkUrlConstant.ROOT + "/**").requestMatchers(AiChatApiUrlConstant.ROOT + "/**")
				.requestMatchers(AiArtApiUrlConstant.ROOT + "/**").requestMatchers(ImageUrl.ROOT + "/**")
				.requestMatchers(UrgeNoticeUrl.ROOT + "/**")
				;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public CustomPasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}

	private String hasRole(SystemRolesType roleType) {
		if (roleType == null) {
			return "hasRole('')";
		}

		return "hasRole('" + roleType.getName() + "')";
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

		if ((sysRoleTypes == null || sysRoleTypes.isEmpty()) && (orgRoleTypes == null || orgRoleTypes.isEmpty())) {
			return "hasRole('')";
		}

		if (sysRoleTypes != null) {
			for (SystemRolesType roleType : sysRoleTypes) {
				if (roleType != null) {
					roleExpressionBuilder.append("'" + roleType.getName() + "',");
				}
			}
		}

		if (orgRoleTypes != null) {
			for (OrganzationRolesType roleType : orgRoleTypes) {
				if (roleType != null) {
					roleExpressionBuilder.append("'" + roleType.getName() + "',");
				}
			}
		}

		roleExpressionBuilder.replace(roleExpressionBuilder.length() - 1, roleExpressionBuilder.length(), "");

		roleExpressionBuilder.append(")");
		return roleExpressionBuilder.toString();
	}

}
