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

  <!-- user search start -->
  <div class="row">
    <%@ include file="../adminJSP/userSearch.jsp" %>
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
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $(".conditionInput").change(function () {
        authButtonReCSS();
      });

      $("#usernameDiv").click(function () {
        var userId = $("#usernameDiv").attr("selectedUserId");

        var url = "/userauth/findUserAuth";

        var jsonOutput = {
          userId:userId
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
            var hasAuthList = data.authList;
            authButtonReCSS();
            $.each(hasAuthList, function(index, authDetail) {
              $(".authButton[authid='"+authDetail.id+"']").addClass("btn-primary");
              $(".authButton[authid='"+authDetail.id+"']").removeClass("btn-light");
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
            var authList = data.authList;
            var authsDiv = $("#authsDiv");
            authsDiv.empty();
            $.each(authList, function(index, authDetail) {
              authsDiv.append($("<button class='btn btn-sm btn-light'></button>").attr("authId", authDetail.id).text(authDetail.authName));
              authsDiv.append($("<label>&nbsp;&nbsp;|&nbsp;&nbsp;</label>"));
              $("button[authId='"+authDetail.id+"']").addClass("authButton");
              $("button[authId='"+authDetail.id+"']").bind("click", userAuthEdit);
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
        var thisAuthButton = $(this);
        var userId = $("#usernameDiv").attr("selectedUserId");
        var authIdList = [];
        var authButtons = $(".authButton");
        authButtons.each(function (index, value) {
          if ($(this).hasClass("btn-primary")) {
            authIdList.push($(this).attr("authId"));
          }
        });

        var url = "/userauth/editUserAuth";

        var jsonOutput = {
          userId:userId,
          newAuthIdList:authIdList
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
            console.log(data);
            if(data.code == "0") {
              if (thisAuthButton.hasClass("btn-primary")) {
                thisAuthButton.removeClass("btn-primary");
                thisAuthButton.addClass("btn-light");
              } else {
                thisAuthButton.addClass("btn-primary");
                thisAuthButton.removeClass("btn-light");
              }
              $("#resultMsg").val("edit success");
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