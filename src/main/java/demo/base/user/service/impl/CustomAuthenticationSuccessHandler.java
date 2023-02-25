package demo.base.user.service.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import demo.base.user.pojo.type.SystemRolesType;
import demo.config.costom_component.BaseUtilCustom;
import demo.finance.cryptoCoin.sharing.pojo.constant.CryptoCoinSharingUrl;
import demo.toyParts.educate.pojo.constant.EducateUrl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private BaseUtilCustom baseUtilCustom;

	protected Log logger = LogFactory.getLog(this.getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

//		String remoteAddr = null;
//		if (request != null) {
//            remoteAddr = request.getHeader("X-FORWARDED-FOR");
//            if (remoteAddr == null || "".equals(remoteAddr)) {
//                remoteAddr = request.getRemoteAddr();
//            }
//        }
//		UserIp ui = new UserIp();
//		ui.setIp(NumericUtilCustom.ipToLong(remoteAddr));
//		ui.setUri(request.getRequestURI());
//		ui.setUserId(userId);
//		userIpMapper.insertSelective(ui);

		handle(request, response, auth);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl();

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl() {
		String targetUrl = "/";
		if (baseUtilCustom.hasAdminRole()) {
			targetUrl = "/admin/manager";
		} else if (baseUtilCustom.hasRole(SystemRolesType.ROLE_CRYPTO_SHARING_MANAGER.getName())) {
			targetUrl = CryptoCoinSharingUrl.ROOT + CryptoCoinSharingUrl.HOME;
		} else if (baseUtilCustom.hasRole(SystemRolesType.ROLE_STUDENT.getName())) {
			targetUrl = EducateUrl.ROOT + "/";
		}
		return targetUrl;
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}
