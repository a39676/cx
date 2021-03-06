<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
	<h1>Title : Spring Security Remember Me Example - Update Form</h1>
	<h1>Message : This page is for ROLE_ADMIN and fully authenticated only
            (Remember me cookie is not allowed!)</h1>

	<h2>Update Account Information...</h2>
	
	<sec:authorize access="isRememberMe()">
		<h2># This user is login by "Remember Me Cookies".</h2>
	</sec:authorize>

	<sec:authorize access="isFullyAuthenticated()">
		<h2># This user is login by username / password.</h2>
	</sec:authorize>
</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
</footer>
</html>