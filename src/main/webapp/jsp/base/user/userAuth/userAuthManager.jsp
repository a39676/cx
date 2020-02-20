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

  <!-- user search start -->
  <div class="row">
    <%@ include file="../userManager/userSearch.jsp" %>
  </div>
  <!-- user search end -->

  <hr>

  <!-- auth row start-->
  <div class="row">
    <div class="col-md-12" id="authsDiv">
    </div>
  </div>
  <!-- auth row end-->

  <hr>

  <!-- result row start-->
  <div class="row">
    <div class="col-md-12">
      <input type="" name="" id="resultMsg" disabled="disabled">
    </div>
  </div>
  <!-- result row end-->

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $(".conditionInput").change(function () {
        authButtonReCSS();
      });

      $("#usernameDiv").click(function () {
        var userPk = $("#usernameDiv").attr("selectedUserPk");
        $("#resultMsg").val("");

        var url = "/userauth/findUserAuth";

        var jsonOutput = {
          userPk:userPk
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
            var hasAuthList = data.authVOList;
            authButtonReCSS();
            $.each(hasAuthList, function(index, authDetail) {
              $(".authButton[authPk='"+authDetail.pk+"']").addClass("btn-primary");
              $(".authButton[authPk='"+authDetail.pk+"']").removeClass("btn-light");
            });
          }, 
          error:function(e){
          }
        });  
      });

      findAuth();

      function findAuth() {
        var url = "/userauth/findAuth";

        var jsonOutput = {
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
            var authList = data.authVOList;
            var authsDiv = $("#authsDiv");
            authsDiv.empty();
            $.each(authList, function(index, authDetail) {
              authsDiv.append($("<button class='btn btn-sm btn-light'></button>").attr("authPk", authDetail.pk).text(authDetail.authName));
              authsDiv.append($("<label>&nbsp;&nbsp;|&nbsp;&nbsp;</label>"));
              $("button[authPk='"+authDetail.pk+"']").addClass("authButton");
              $("button[authPk='"+authDetail.pk+"']").bind("click", userAuthEdit);
            });
          }, 
          error:function(e){
          }
        });  
      }

      function authButtonReCSS() {
        $(".authButton").addClass("btn-light");
        $(".authButton").removeClass("btn-primary");
      }
      
      function userAuthEdit() {
        $("#resultMsg").val("");
        var thisAuthButton = $(this);
        var modifyAuthPk = $(this).attr("authPk");
        var authName = $(this).text();

        var userPk = $("#usernameDiv").attr("selectedUserPk");
        var url;

        if(thisAuthButton.hasClass("btn-primary")) {
          url = "/userauth/deleteUserAuth"
        } else {
          url = "/userauth/insertUserAuth"
        }

        var jsonOutput = {
          userPk:userPk,
          newAuthPk:modifyAuthPk
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
            if(data.code == "0") {
              if (thisAuthButton.hasClass("btn-primary")) {
                thisAuthButton.removeClass("btn-primary");
                thisAuthButton.addClass("btn-light");
              } else {
                thisAuthButton.addClass("btn-primary");
                thisAuthButton.removeClass("btn-light");
              }
              $("#resultMsg").val("edit success: " + authName);
            } else {
              $("#resultMsg").val("edit fail: " + data.message);
            }
          }, 
          error:function(e){
          }
        });  
      }
      
    });

  </script>
</footer>
</html>