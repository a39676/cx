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

  <!-- org search start -->
  <div class="row">
    <div class="col-md-12">      
      <%@ include file="../../org/orgManager/orgSearch.jsp" %>
    </div>
  </div>
  <!-- org search end -->  

  <hr>
  
  <div class="row">
    <div class="col-md-4" >
      <label>authName</label>
      <input class="authSearchConditionInput" type="text" name="authName" id="authName" placeholder="authName">
    </div>
    <div class="col-md-4" >
      <label>authPk</label>
      <input class="authSearchConditionInput" type="text" name="authPk" id="authPk" placeholder="authPk">
    </div>
  </div>

  <hr>
  
  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <div class="row" id="authConditionValue2" isDelete="false">
      <div class="col-md-4" >
        <label class="badge">isDelete</label>
        <div class="form-check form-check-inline">
          <input class="form-check-input authSearchConditionInput" type="radio" id="authIsDeleteTrue" name="authIsDeleteType" value="true">
          <label class="form-check-label badge" for="authIsDeleteTrue" name="authIsDeleteValue" value="true">True</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input authSearchConditionInput" type="radio" id="authIsDeleteFalse" name="authIsDeleteType" checked="checked" value="false">
          <label class="form-check-label badge" for="authIsDeleteFalse" name="authIsDeleteValue" value="false">False</label>
        </div>
      </div>
    </div>
  </sec:authorize>
  
  <hr>
  
  <div class="row">
    <div class="col-md-12">
      <div id="authNameDiv" selectedAuthPk="" operatorType=""></div>
    </div>
  </div>

  <hr>

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      function cleanAuthNameDiv() {
        var authNameDiv = $("#authNameDiv");
        authNameDiv.empty();
        authNameDiv.attr("selectedAuthPk", "");
        authNameDiv.attr("operatorType", "");
      }

      $(".orgSearchConditionInput").change(function () {
        cleanAuthNameDiv();
        authSearch();
      });
      $("#orgNameDiv").click(function () {
        cleanAuthNameDiv();
        authSearch();
      });

      var authIsDelete = false;
      $("input[name='authIsDeleteType']").click(function () {
        authIsDeleteValue($(this).val());
      });
      function authIsDeleteValue(e) {
        $("#authConditionValue2").attr("isDelete", e);
        authIsDelete = $("#authConditionValue2").attr("isDelete");
      }

      $(".authSearchConditionInput").change(function () {
        authSearch();
      });

      function authSearch() {
        $("#authNameDiv").attr("selectedAuthPk", "");

        var belongOrgPkList = [];
        var orgPk = $("#orgNameDiv").attr("selectedOrgPk");
        if(orgPk != null && orgPk.length > 0) {
          belongOrgPkList.push(orgPk);
        }

        var authPkList = [];
        var authPk = $("#authPk").val();
        if(authPk != null && authPk.length > 0) {
          authPkList.push(authPk);
        }

        var authName = $("#authName").val();
        var isDelete = authIsDelete;

        var url = "/userauth/findAuth";

        var jsonOutput = {
          belongOrgPkList : belongOrgPkList,
          authPkList : authPkList,
          authName : authName,
          isDelete : isDelete
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
            var authVOList = data.authVOList;
            var authNameDiv = $("#authNameDiv");
            authNameDiv.empty();
            var usernameDiv = $("#usernameDiv");
            var userAuthPkList = null;
            if(usernameDiv != null && usernameDiv.attr("userAuthPkList") != null) {
              userAuthPkList = usernameDiv.attr("userAuthPkList").split(",");
            }
            $.each(authVOList, function(index, authVOInfo) {
              authNameDiv.append($("<button class='btn btn-sm btn-light'></button>").attr("pk", authVOInfo.pk).text(authVOInfo.authName));
              authNameDiv.append($("<label>&nbsp;&nbsp;|&nbsp;&nbsp;</label>"));
              $("button[pk='"+authVOInfo.pk+"']").addClass("authButton");
              if(userAuthPkList != null && (jQuery.inArray(authVOInfo.pk, userAuthPkList)) >= 0) {
                $("button[pk='"+authVOInfo.pk+"']").addClass("btn-primary");
                $("button[pk='"+authVOInfo.pk+"']").removeClass("btn-light");
              }
              $("button[pk='"+authVOInfo.pk+"']").bind("click", authButtonClick);
            });
          }, 
          error:function(e){
          }
        });
      }

      function authButtonClick() {
        var pk = $(this).attr("pk");
        var authNameDiv = $("#authNameDiv");
        authNameDiv.attr("selectedAuthPk", pk);
        if($(this).hasClass("btn-primary")) {
          authNameDiv.attr("operatorType", "delete");
        } else {
          authNameDiv.attr("operatorType", "insert");
        }
      };

    });

  </script>
</footer>
</html>