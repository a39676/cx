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
import demo.config.customComponent.CustomAuthenticationProvider;
import demo.config.customComponent.CustomPasswordEncoder;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataUrl;
import demo.finance.cryptoCoin.sharing.pojo.constant.CryptoCoinSharingUrl;
import demo.finance.cryptoCoin.trading.pojo.constant.CryptoCoinTradingUrl;
import demo.finance.currencyExchangeRate.notice.pojo.constant.CurrencyExchangeRateNoticeUrl;
import demo.image.pojo.constant.ImageUrl;
import demo.interaction.ccm.pojo.constant.CcmManageUrl;
import demo.joy.common.pojo.constant.JoyManagerUrl;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.pmemo.pojo.constant.PMemoUrl;
import demo.pmemo.pojo.constant.UrgeNoticeManagerUrl;
import demo.pmemo.pojo.constant.UrgeNoticeUrl;
import demo.test.pojo.constant.TestUrl;
import demo.tool.other.pojo.constant.ToolUrlConstant;
import demo.tool.wordHelper.pojo.constant.WordHelperUrl;
import finance.cryptoCoin.pojo.constant.CryptoCoinBinanceTradingCommonUrl;
import image.pojo.constant.ImageInteractionUrl;
import tool.pojo.constant.CxBbtInteractionUrl;
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
		http.authorizeRequests().antMatchers("/welcome**").permitAll().antMatchers(LoginUrlConstant.LOGIN + "/**")
				.permitAll().antMatchers(UsersUrl.root + "/**").permitAll().antMatchers("/static_resources/**")
				.permitAll().antMatchers("/tHome/**").permitAll()
				// used to allow anonymous access
				// .antMatchers("/welcome**").access("IS_AUTHENTICATED_ANONYMOUSLY")
//            .antMatchers(ArticleUrlConstant.root + "/**").access("hasAnyRole('" + RolesType.ROLE_ADMIN.getRoleName() + "','" + RolesType.ROLE_USER.getRoleName() + "')")
				.antMatchers(BaseUrl.SHUTDOWN + "/**").access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
				.antMatchers(BaseUrl.OPTION_CONSTANT + "/**").access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
				.antMatchers(TestUrl.root + "/**").access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
				.antMatchers("/holder/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_USER))
				.antMatchers("/accountInfo/**").access(hasAnyRole(SystemRolesType.ROLE_USER))
				.antMatchers(AdminUrlConstant.root + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN))
				.antMatchers(ArticleAdminUrlConstant.root + "/**").access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN))
				.antMatchers(ArticleAdminCommentUrlConstant.root + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN)).antMatchers("/dba/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_DBA))
				.antMatchers(ToolUrlConstant.root + "/**").access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
				.antMatchers(CurrencyExchangeRateNoticeUrl.ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
				.antMatchers(PMemoUrl.ROOT + PMemoUrl.SET).access(hasRole(SystemRolesType.ROLE_SUPER_ADMIN))
				.antMatchers(UrgeNoticeManagerUrl.ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
				.antMatchers(CryptoCoinSharingUrl.ROOT + CryptoCoinSharingUrl.CALCULATE_DETAIL).permitAll()
				.antMatchers(CryptoCoinSharingUrl.ROOT + "/**")
				.access("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_CRYPTO_SHARING_MANAGER')")
				.antMatchers(CcmManageUrl.ROOT + "/**")
				.access("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_CRYPTO_SHARING_MANAGER')")
				.antMatchers(AiManagerUrlConstant.ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
				.antMatchers(CryptoCoinTradingUrl.FUTURE_CM_ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
				.antMatchers(CryptoCoinTradingUrl.FUTURE_UM_ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
				.antMatchers(CryptoCoinTradingUrl.SPOT_ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
				.antMatchers(CryptoCoinDataUrl.ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
				.antMatchers(AiArtMangerUrl.ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN, SystemRolesType.ROLE_ADMIN))
				.antMatchers(WordHelperUrl.ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_USER_ACTIVE, SystemRolesType.ROLE_STUDENT))
				// joy url start
				.antMatchers(JoyUrl.ROOT + "/**").access(hasAnyRole(SystemRolesType.ROLE_USER_ACTIVE))
				.antMatchers(JoyManagerUrl.ROOT + "/**")
				.access(hasAnyRole(SystemRolesType.ROLE_ADMIN, SystemRolesType.ROLE_SUPER_ADMIN))
				// joy url end
				.and().formLogin().loginPage("/login/login").failureUrl("/login/login?error")
				.loginProcessingUrl("/auth/login_check").successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailHandler).usernameParameter("user_name").passwordParameter("pwd")
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")).and()
				.exceptionHandling().accessDeniedPage("/403").and().rememberMe()
				.tokenRepository(persistentTokenRepository()).tokenValiditySeconds(3600).and().authorizeRequests().and()
				.csrf().ignoringAntMatchers(UrgeNoticeUrl.ROOT + "/**");
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/test/testIgnoring").antMatchers(ImageInteractionUrl.ROOT + "/**")
				.antMatchers(AiChatFromWechatSdkUrlConstant.ROOT + "/**")
				.antMatchers(WechatPaySdkUrlConstant.ROOT + "/**").antMatchers(WechatSdkUrlConstant.ROOT + "/**")
				.antMatchers(AiChatApiUrlConstant.ROOT + "/**").antMatchers(AiArtApiUrlConstant.ROOT + "/**")
				.antMatchers(ImageUrl.ROOT + "/**").antMatchers(CxBbtInteractionUrl.ROOT + "/**")
				.antMatchers(CryptoCoinBinanceTradingCommonUrl.ROOT + "/**");
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
