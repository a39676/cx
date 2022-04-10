<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
  <%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>


<div class="">
  <!-- <form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'> -->
  <form name='loginForm' action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />" method='POST'>

    <table>
      <tr>
        <td>用户名:</td>
        <td><input type='text' name='user_name' value=''></td>
      </tr>
      <tr>
        <td>密码:</td>
        <td><input type='password' name='pwd' /></td>
      </tr>
      <tr>
        <td colspan='1'>
          <input name="submit" type="submit" value="登录" />
        </td>
        <%-- <td colspan="1">
          <a href="/user/userRegist">[注册]</a>
        </td> --%>
      </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  </form>
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
