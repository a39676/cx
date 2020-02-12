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
<div class="container-fluid">

  <div class="row">
    <div class="col-md-4" >
      <label>UserName</label>
      <input class="conditionInput" type="text" name="UserName" id="UserName" placeholder="UserName">
    </div>
    <div class="col-md-4" >
      <label>UserNickName</label>
      <input class="conditionInput" type="text" name="UserNickName" id="UserNickName" placeholder="UserNickName">
    </div>
    <div class="col-md-4" >
      <label>userId</label>
      <input class="conditionInput" type="text" name="userId" id="userId" placeholder="userId">
    </div>
  </div>

  <div class="row" id="conditionValue2" accountNonLocked="true" accountNonExpired="true" credentialsNonExpired="true">
    <div class="col-md-4" >
      <label class="badge">accountNonLocked</label>
      <div class="form-check form-check-inline">
        <input class="form-check-input conditionInput" type="radio" id="accountNonLockedTrue" name="accountNonLockedType" checked="checked" value="true">
        <label class="form-check-label badge" for="accountNonLockedTrue" name="accountNonLockedValue" value="true">True</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input conditionInput" type="radio" id="accountNonLockedFalse" name="accountNonLockedType" value="false">
        <label class="form-check-label badge" for="accountNonLockedFalse" name="accountNonLockedValue" value="false">False</label>
      </div>
    </div>

    <div class="col-md-4" >
      <label class="badge">accountNonExpired</label>
      <div class="form-check form-check-inline">
        <input class="form-check-input conditionInput" type="radio" id="accountNonExpiredTrue" name="accountNonExpiredType" checked="checked" value="true">
        <label class="form-check-label badge" for="accountNonExpiredTrue">True</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input conditionInput" type="radio" id="accountNonExpiredFalse" name="accountNonExpiredType" value="false">
        <label class="form-check-label badge" for="accountNonExpiredFalse">False</label>
      </div>
    </div>

    <div class="col-md-4" >
      <label class="badge">credentialsNonExpired</label>
      <div class="form-check form-check-inline">
        <input class="form-check-input conditionInput" type="radio" id="credentialsNonExpiredTrue" name="credentialsNonExpiredType" checked="checked" value="true">
        <label class="form-check-label badge" for="credentialsNonExpiredTrue">True</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input conditionInput" type="radio" id="credentialsNonExpiredFalse" name="credentialsNonExpiredType" value="false">
        <label class="form-check-label badge" for="credentialsNonExpiredFalse">False</label>
      </div>
    </div>
  </div>
  
  <hr>
  
  <div class="row">
    <div class="col-md-12">
      <div id="usernameDiv"></div>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("input[name='accountNonLockedType']").click(function () {
        accountNonLockedValue($(this).val());
      });
      function accountNonLockedValue(e) {
        $("#conditionValue2").attr("accountNonLocked", e);
      }

      $("input[name='accountNonExpiredType']").click(function () {
        accountNonExpiredValue($(this).val());
      });
      function accountNonExpiredValue(e) {
        $("#conditionValue2").attr("accountNonExpired", e);
      }

      $("input[name='credentialsNonExpiredType']").click(function () {
        credentialsNonExpiredValue($(this).val());
      });
      function credentialsNonExpiredValue(e) {
        $("#conditionValue2").attr("credentialsNonExpired", e);
      }

      $(".conditionInput").change(function () {
        var UserName = $("#UserName").val();
        var UserNickName = $("#UserNickName").val();
        var userId = $("#userId").val();
        var accountNonLocked = $("#conditionValue2").attr("accountNonLocked");
        var accountNonExpired = $("#conditionValue2").attr("accountNonExpired");
        var credentialsNonExpired = $("#conditionValue2").attr("credentialsNonExpired");

        var url = "/user/findUserByCondition";

        var jsonOutput = {
          userId:userId,
          userName:UserName,
          userNickName:UserNickName,
          accountNonLocked:accountNonLocked,
          accountNonExpired:accountNonExpired,
          credentialsNonExpired:credentialsNonExpired
        };

        console.log(jsonOutput);

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
            console.log(data);
            console.log(data.userList);
            var userList = data.userList;
            var usernameDiv = $("#usernameDiv");
            usernameDiv.empty();
            $.each(userList, function(index, userInfo) {
              usernameDiv.append($("<button class='btn btn-sm btn-light'></button>").attr("userId", userInfo.userId).text(userInfo.nickName));
              usernameDiv.append($("<label>&nbsp;&nbsp;|&nbsp;&nbsp;</label>"));
              $("button[userId='"+userInfo.userId+"']").addClass("bankButton");
            });
          }, 
          error:function(e){
          }
        });  
      });

    });

  </script>
</footer>
</html>