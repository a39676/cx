<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
<style type="text/css">

</style>
</head>

<body>

<div>

  <sec:authorize access="!hasRole('ROLE_USER')">
    <label>Please</label> <a href="${pageContext.request.contextPath}/login/login">Login</a> <br>
    <label> or </label> <a href="${pageContext.request.contextPath}/user/userRegist">Regist</a>
  </sec:authorize>
  
</div>
</body>

<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">
  $(document).ready(function() {
  	
  });
</script>
</footer>
</html>