<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

  <div>
    <label name="message"></label>
  </div>

  <div  class="col-md-12" id="registForm">

    <div class="form-group">
      <span name="userName" >账户名<span style='color: rgb(255, 0, 0);font-weight: bold;'>*</span></span>
      <input id="userName" class="form-control" placeholder="请输入账户名(登录用...)" />
      <span id="userNameWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div class="form-group">
      <span name="nickName" >昵称<span style='color: rgb(255, 0, 0);font-weight: bold;'>*</span></span>
      <input id="nickName" class="form-control" placeholder="请填一个昵称吧(不建议与账户名相同)" />
      <span id="nickNameWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div class="form-group">
      <span name="email" >email<span style='color: rgb(255, 0, 0);font-weight: bold;'>*</span></span>
      <input id="email" class="form-control" placeholder="请填入电子邮箱,以接收验证邮件" />
      <span id="emailWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div class="form-group">
      <span name="pwd" >密码<span style='color: rgb(255, 0, 0);font-weight: bold;'>*</span></span>
      <input type="password" id="pwd" class="form-control"/>
      <span id="pwdWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div class="form-group">
      <span name="pwdRepeat" >重复输入一次密码<span style='color: rgb(255, 0, 0);font-weight: bold;'>*</span></span>
      <input type="password" id="pwdRepeat" class="form-control"/>
      <span id="pwdRepeatWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div class="form-group">
      <span name="gender" >性别<span style='color: rgb(255, 0, 0);font-weight: bold;'>*</span></span>
      <input type="radio" id="male"
        name="gender" value="male">
      <span>男</span>
  	  <label>|</label>
      <input type="radio" id="female"
        name="gender" value="female">
      <span>女</span>
      <label>|</label>
      <input type="radio" id="secret"
        name="gender" value="secret" checked="checked">
      <span>保密</span>
      <span id="genderWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div class="form-group">
      <span name="qq" >qq号</span>
      <input id="qq" class="form-control" placeholder="要填个Q号不?" />
      <span id="qqWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div class="form-group">
      <span name="mobile" >手机号</span>
      <input id="mobile" class="form-control" placeholder="如果你想留个手机号?" />
      <span id="mobileWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div class="form-group">
      <span name="reservationInformation" >预留信息</span>
      <input id="reservationInformation" class="form-control" placeholder="用于账号找回,网站验证之类的." />
      <span id="reservationInformationWarnMsg" style='color: rgb(255, 0, 0);font-weight: bold;'></span>
    </div>

    <div id="captcha" class="cf-turnstile" 
      data-sitekey="${siteKey}" data-callback="cfCallBack" 
      error-callback="errorCallBack" token="">
    </div>

    <div>
      <button name="apply" class="btn btn-warning btn-sm">提交</button>
    </div>
  </div>


  <div>
    <span id="registResult" style="font-size: smaller"></span>
  </div>
  <br>
  <div id="loginView">

  </div>
</body>
<footer>
<%@ include file="../../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  function cfCallBack() {
    turnstile.render('#captcha', {
      sitekey : '${siteKey}',
      callback : function(token) {
        document.getElementById("captcha").setAttribute("token", token);
      },
    });
  };

  $(document).ready(function() {

    $("button[name='apply']").click(function() {
        userRegist();
    });


    function userRegist(){
      $("label[name='message']").empty();

      var url = "/user/userRegist";

      var gender;
      if($('#male').is(':checked')) {
        gender = 1;
      } else if ($('#female').is(':checked')) {
        gender = 0;
      } else {
        gender = -1;
      }

      var jsonOutput = {
        userName : $("#userName").val(),
        nickName : $("#nickName").val(),
        email : $("#email").val(),
        pwd : $("#pwd").val(),
        pwdRepeat : $("#pwdRepeat").val(),
        qq : $("#qq").val(),
        mobile : $("#mobile").val(),
        reservationInformation : $("#reservationInformation").val(),
        gender : gender,
        captchaToken : $("#captcha").attr("token"),
      };

      $.ajax({
          type : "POST",
          async : true,
          url : url,
          data: JSON.stringify(jsonOutput),
          contentType: "application/json",
          dataType: 'json',
          timeout:15000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            if(datas.code == 0) {
              $("#registForm").html("");
              $("#registResult").text(datas.message);
            } else {
              $("label[name='message']").text(datas.message);
              $("#pwd").val("");
              $("#pwdRepeat").val("");
              if(datas.validUserRegistResult.username != null) {
                $("#userNameWarnMsg").append(datas.validUserRegistResult.username);
              }
              if(datas.validUserRegistResult.nickname != null) {
                $("#nickNameWarnMsg").append(datas.validUserRegistResult.nickname);
              }
              if(datas.validUserRegistResult.email != null) {
                $("#emailWarnMsg").append(datas.validUserRegistResult.email);
              }
              if(datas.validUserRegistResult.pwd != null) {
                $("#pwdWarnMsg").append(datas.validUserRegistResult.pwd);
              }
              if(datas.validUserRegistResult.pwdRepeat != null) {
                $("#pwdRepeatWarnMsg").append(datas.validUserRegistResult.pwdRepeat);
              }
              if(datas.validUserRegistResult.qq != null) {
                $("#qqWarnMsg").append(datas.validUserRegistResult.qq);
              }
              if(datas.validUserRegistResult.mobile != null) {
                $("#mobileWarnMsg").append(datas.validUserRegistResult.mobile);
              }
              if(datas.validUserRegistResult.reservationInformation != null) {
                $("#reservationInformationWarnMsg").append(datas.validUserRegistResult.reservationInformation);
              }
            }
          },
          error: function(datas) {
            $("#registResult").text(datas.message);
          }
      });
    };

  });

</script>
</footer>
</html>
