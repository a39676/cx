<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">

  <div class="row">
    <div class="col-md-4" >
      <label>UserName</label>
      <input class="userSearchConditionInput" type="text" name="UserName" id="UserName" placeholder="UserName">
    </div>
    <div class="col-md-4" >
      <label>UserNickName</label>
      <input class="userSearchConditionInput" type="text" name="UserNickName" id="UserNickName" placeholder="UserNickName">
    </div>
    <div class="col-md-4" >
      <label>userId</label>
      <input class="userSearchConditionInput" type="text" name="userId" id="userId" placeholder="userId">
    </div>
  </div>

  <div class="row" id="userSearchConditionValue2" accountNonLocked="true" accountNonExpired="true" credentialsNonExpired="true">
    <div class="col-md-4" >
      <label class="badge">accountNonLocked</label>
      <div class="form-check form-check-inline">
        <input class="form-check-input userSearchConditionInput" type="radio" id="accountNonLockedTrue" name="accountNonLockedType" checked="checked" value="true">
        <label class="form-check-label badge" for="accountNonLockedTrue" name="accountNonLockedValue" value="true">True</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input userSearchConditionInput" type="radio" id="accountNonLockedFalse" name="accountNonLockedType" value="false">
        <label class="form-check-label badge" for="accountNonLockedFalse" name="accountNonLockedValue" value="false">False</label>
      </div>
    </div>

    <div class="col-md-4" >
      <label class="badge">accountNonExpired</label>
      <div class="form-check form-check-inline">
        <input class="form-check-input userSearchConditionInput" type="radio" id="accountNonExpiredTrue" name="accountNonExpiredType" checked="checked" value="true">
        <label class="form-check-label badge" for="accountNonExpiredTrue">True</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input userSearchConditionInput" type="radio" id="accountNonExpiredFalse" name="accountNonExpiredType" value="false">
        <label class="form-check-label badge" for="accountNonExpiredFalse">False</label>
      </div>
    </div>

    <div class="col-md-4" >
      <label class="badge">credentialsNonExpired</label>
      <div class="form-check form-check-inline">
        <input class="form-check-input userSearchConditionInput" type="radio" id="credentialsNonExpiredTrue" name="credentialsNonExpiredType" checked="checked" value="true">
        <label class="form-check-label badge" for="credentialsNonExpiredTrue">True</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input userSearchConditionInput" type="radio" id="credentialsNonExpiredFalse" name="credentialsNonExpiredType" value="false">
        <label class="form-check-label badge" for="credentialsNonExpiredFalse">False</label>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-4" >
      <button id="cleanUserSearchCondition" class="btn btn-sm btn-warning">clean user search condition</button>
    </div>
  </div>
  
  <hr>
  
  <div class="row">
    <div class="col-md-12">
      <div id="usernameDiv" selectedUserPk="" userAuthPkList=""></div>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("input[name='accountNonLockedType']").click(function () {
        accountNonLockedValue($(this).val());
      });
      function accountNonLockedValue(e) {
        $("#userSearchConditionValue2").attr("accountNonLocked", e);
      }

      $("input[name='accountNonExpiredType']").click(function () {
        accountNonExpiredValue($(this).val());
      });
      function accountNonExpiredValue(e) {
        $("#userSearchConditionValue2").attr("accountNonExpired", e);
      }

      $("input[name='credentialsNonExpiredType']").click(function () {
        credentialsNonExpiredValue($(this).val());
      });
      function credentialsNonExpiredValue(e) {
        $("#userSearchConditionValue2").attr("credentialsNonExpired", e);
      }

      $(".userSearchConditionInput").change(function () {
        $("#usernameDiv").attr("selectedUserPk", "");

        var UserName = $("#UserName").val();
        var UserNickName = $("#UserNickName").val();
        var userId = $("#userId").val();
        var accountNonLocked = $("#userSearchConditionValue2").attr("accountNonLocked");
        var accountNonExpired = $("#userSearchConditionValue2").attr("accountNonExpired");
        var credentialsNonExpired = $("#userSearchConditionValue2").attr("credentialsNonExpired");

        var url = "/user/findUserByCondition";

        var jsonOutput = {
          userId:userId,
          userName:UserName,
          userNickName:UserNickName,
          accountNonLocked:accountNonLocked,
          accountNonExpired:accountNonExpired,
          credentialsNonExpired:credentialsNonExpired
        };

        $.ajax({               
          type: "POST",  
          url: url,
          data: JSON.stringify(jsonOutput),
          dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            var userVOList = data.userVOList;
            var usernameDiv = $("#usernameDiv");
            usernameDiv.empty();
            $.each(userVOList, function(index, userInfo) {
              usernameDiv.append($("<button class='btn btn-sm btn-light'></button>").attr("userPk", userInfo.userPk).text(userInfo.nickName));
              usernameDiv.append($("<label>&nbsp;&nbsp;|&nbsp;&nbsp;</label>"));
              $("button[userPk='"+userInfo.userPk+"']").addClass("userButton");
              $("button[userPk='"+userInfo.userPk+"']").bind("click", userButtonClick);
            });
          }, 
          error:function(e){
          }
        });  
      });

      function userButtonClick() {
        var userPk = $(this).attr("userPk");
        $(".userButton").addClass("btn-light")
        $(".userButton").removeClass("btn-primary")
        $(this).removeClass("btn-light")
        $(this).addClass("btn-primary")
        $("#usernameDiv").attr("selectedUserPk", userPk);
      };

      $("#cleanUserSearchCondition").click(function () {
        cleanUserSearchCondition();
      });
      function cleanUserSearchCondition() {
        var usernameDiv = $("#usernameDiv");
        usernameDiv.empty();
        usernameDiv.attr("selectedUserPk", "");
        usernameDiv.attr("userAuthPkList", "");
        $("#UserName").val("");
        $("#UserNickName").val("");
        $("#userId").val("");
        $("#accountNonLockedTrue").prop("checked", true);
        $("#accountNonLockedFalse").prop("checked", false);
        $("#accountNonExpiredTrue").prop("checked", true);
        $("#accountNonExpiredFalse").prop("checked", false);
        $("#credentialsNonExpiredTrue").prop("checked", true);
        $("#credentialsNonExpiredFalse").prop("checked", false);
        accountNonLockedValue("true");
        accountNonExpiredValue("true");
        credentialsNonExpiredValue("true");
      }

    });

  </script>
</footer>
</html>