package demo.config;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
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
import demo.base.user.pojo.constant.LoginUrl;
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

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authManReqMatR = new Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
//			@Override
//			public void customize(
//					AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry t) {
//				// TODO Auto-generated method stub
//			}
//		};
//		http.authorizeHttpRequests(authManReqMatR);
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(request -> request.requestMatchers("/welcome**").permitAll());
		http.authorizeHttpRequests(request -> request.requestMatchers(LoginUrl.LOGIN + "/**").permitAll());
		http.authorizeHttpRequests(request -> request.requestMatchers(UsersUrl.root + "/**").permitAll());
		http.authorizeHttpRequests(request -> request.requestMatchers("/static_resources/**").permitAll());
		http.authorizeHttpRequests(request -> request.requestMatchers("/tHome/**").permitAll());
//		http.formLogin().loginPage("login/login");
		http.formLogin(Customizer.withDefaults());
		http.exceptionHandling().accessDeniedPage("/403");
//		.and().formLogin().loginPage("/login/login").failureUrl("/login/login?error")
//		.loginProcessingUrl("/auth/login_check").successHandler(customAuthenticationSuccessHandler)
//		.failureHandler(customAuthenticationFailHandler).usernameParameter("user_name").passwordParameter("pwd")
//		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")).and()
//		.exceptionHandling().accessDeniedPage("/403").and().rememberMe()
//		.tokenRepository(persistentTokenRepository()).tokenValiditySeconds(3600).and().authorizeRequests().and()

		http.authorizeHttpRequests(request -> request.requestMatchers(BaseUrl.SHUTDOWN + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(BaseUrl.OPTION_CONSTANT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(TestUrl.root + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers("/holder/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_USER.getName()));
		http.authorizeHttpRequests(
				request -> request.requestMatchers("/accountInfo/**").hasAnyRole(SystemRolesType.ROLE_USER.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(AdminUrlConstant.root + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(ArticleAdminUrlConstant.root + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(ArticleAdminCommentUrlConstant.root + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers("/dba/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_DBA.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(ToolUrlConstant.root + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(CurrencyExchangeRateNoticeUrl.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(PMemoUrl.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(UrgeNoticeManagerUrl.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request
				.requestMatchers(CryptoCoinSharingUrl.ROOT + CryptoCoinSharingUrl.CALCULATE_DETAIL).permitAll());
		http.authorizeHttpRequests(request -> request.requestMatchers(CryptoCoinSharingUrl.ROOT + "/**").hasAnyRole(
				SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName(),
				SystemRolesType.ROLE_CRYPTO_SHARING_MANAGER.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(CcmManageUrl.ROOT + "/**").hasAnyRole(
				SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName(),
				SystemRolesType.ROLE_CRYPTO_SHARING_MANAGER.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(AiManagerUrlConstant.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(CryptoCoinTradingUrl.FUTURE_CM_ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(CryptoCoinTradingUrl.FUTURE_UM_ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(CryptoCoinTradingUrl.SPOT_ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(CryptoCoinDataUrl.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(AiArtMangerUrl.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(WordHelperUrl.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
//		joy URL start
		http.authorizeHttpRequests(request -> request.requestMatchers(JoyUrl.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_USER_ACTIVE.getName()));
		http.authorizeHttpRequests(request -> request.requestMatchers(JoyManagerUrl.ROOT + "/**")
				.hasAnyRole(SystemRolesType.ROLE_SUPER_ADMIN.getName(), SystemRolesType.ROLE_ADMIN.getName()));
//		joy URL end

		http.authorizeHttpRequests(request -> request.requestMatchers("/**").authenticated());

//		http.authorizeRequests().requestMatchers("/welcome**").permitAll()
//				.requestMatchers(LoginUrlConstant.LOGIN + "/**").permitAll().requestMatchers(UsersUrl.root + "/**")
//				.permitAll().requestMatchers("/static_resources/**").permitAll().requestMatchers("/tHome/**")
//				// used to allow anonymous access
//				.and().formLogin().loginPage("/login/login").failureUrl("/login/login?error")
//				.loginProcessingUrl("/auth/login_check").successHandler(customAuthenticationSuccessHandler)
//				.failureHandler(customAuthenticationFailHandler).usernameParameter("user_name").passwordParameter("pwd")
//				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")).and()
//				.exceptionHandling().accessDeniedPage("/403").and().rememberMe()
//				.tokenRepository(persistentTokenRepository()).tokenValiditySeconds(3600).and().authorizeRequests().and()
//				.csrf();
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
				.requestMatchers(CxBbtInteractionUrl.ROOT + "/**")
				.requestMatchers(CryptoCoinBinanceTradingCommonUrl.ROOT + "/**");
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

}
