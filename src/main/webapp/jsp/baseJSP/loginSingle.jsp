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

  <div class="col-md-12" id="loginBox">

    <c:if test="${not empty error}">
      <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
      <div class="message">${message}</div>
    </c:if>

    <div class="form-group">
      <span style="font-size: smaller;">用户名: </span>
      <input type="text" name="user_name" class="form-control" />
    </div>
    <div class="form-group">
      <span style="font-size: smaller;">密码: </span>
      <input type="password" name="pwd" class="form-control" />
    </div>

    <div class="btn-group">
      <button class="btn btn-warning btn-sm" name="loginSubmit">
        <span class="badge badge-warning">[登录]</span>
      </button>
      <button class="btn btn-warning btn-sm" id="cancelLogin">
        <span class="badge badge-warning">[取消]</span>
      </button>
    </div>
    <%--
    <button class="loginCustomButton btn btn-warning btn-sm" name="userRegist" url="/user/userRegist">
      <span class="badge badge-warning">[注册]</span>
    </button>
    <button class="loginCustomButton btn btn-warning btn-sm" name="forgotPassword" url="/user/forgotPasswordOrUsername">
      <span class="badge badge-warning">[忘记密码/用户名]</span>
    </button>
     --%>
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
    <div>
      <span name="loginMessage" style="font-size: small;"></span>
    </div>

  </div>

</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">
$(document).ready(function() {

  $("button[name='loginSubmit']").click(function () {

    var loginUrl = "/auth/login_check";
    var userName = $("input[name='user_name']").val();
    var pwd = $("input[name='pwd']").val();

    $.ajax({
      type: "POST",
      url: loginUrl,
      data: {
        user_name:userName,
        pwd:pwd
      },
      dataType: 'json',
      // contentType: "application/json",
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      timeout: 30000,
      success:function(data){
        if(data.code == 0) {
          // window.location.href = "/";
        } else {
          if(data.message.length < 1) {
            $("span[name='loginMessage']").text("账户名或密码错误");
          } else {
            $("span[name='loginMessage']").text(data.message);
          }
        }
      },
      error:function(e){
      }
    });
  });

  document.getElementById("cancelLogin").onclick = function () {
    document.getElementById("loginBox").style.display = "none";
  };

  // $(".loginCustomButton").click(function () {
  //   loginCustomButtonClick($(this).attr("url"));
  // });

  // function loginCustomButtonClick(buttonUrl){
  //   $.ajax({
  //     type : "GET",
  //     async : true,
  //     url : buttonUrl,
  //     success:function(datas){
  //         $("div[name='subBodyRow']").html(datas);
  //         $("div[name='subBodyRow']").show();
  //     },
  //     error: function(datas) {
  //     }
  //   });
  // }

});

</script>
</footer>
</html>
