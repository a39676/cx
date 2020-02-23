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
    <div class="col-md-12">      
      <%@ include file="../userManager/userSearch.jsp" %>
    </div>
  </div>
  <!-- user search end -->

  <hr>

  <!-- auth search start-->
  <div class="row">
    <div class="col-md-12">
      <%@ include file="../../auth/authManager/authSearch.jsp" %>
    </div>
  </div>
  <!-- auth search end-->

  <hr>

  <!-- result row start-->
  <div class="row">
    <div class="col-md-12">
      <input type="" name="" id="userAuthOperatorResultMsg" disabled="disabled" style="width: 800px;">
    </div>
  </div>
  <!-- result row end-->

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#usernameDiv").click(function () {
        var userPk = $("#usernameDiv").attr("selectedUserPk");
        $("#userAuthOperatorResultMsg").val("");

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
            var authVOList = data.authVOList;
            var authPkList = [];
            $.each(authVOList, function(index, authVO) {
              authPkList.push(authVO.pk);
            });
            $("#usernameDiv").attr("userAuthPkList", authPkList);
          }, 
          error:function(e){
          }
        });  
      });

    
      $("#authNameDiv").click(function () {
        var userPk = $("#usernameDiv").attr("selectedUserPk");
        var authPk = $("#authNameDiv").attr("selectedAuthPk");
        var operatorType = $("#authNameDiv").attr("operatorType");

        if (userPk != null && authPk != null && operatorType != null) {
          userAuthEdit(userPk, authPk, operatorType);
          $("#authNameDiv").attr("selectedAuthPk", "");
          $("#authNameDiv").attr("operatorType", "");
        }


      });

      function userAuthEdit(userPk, modifyAuthPk, operatorType) {
        $("#userAuthOperatorResultMsg").val("");
        var url;
        if(operatorType == "delete") {
          url = "/userauth/deleteUserAuth"
        } else if (operatorType == "insert") {
          url = "/userauth/insertUserAuth"
        } else {
          return;
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
              $("#userAuthOperatorResultMsg").val("edit success: " + $(".authButton[pk='"+modifyAuthPk+"']").text());
              var authPkList = $("#usernameDiv").attr("userAuthPkList").split(",");
              if(operatorType == "delete") {
                $(".authButton[pk='"+modifyAuthPk+"']").addClass("btn-light");
                $(".authButton[pk='"+modifyAuthPk+"']").removeClass("btn-primary");
                authPkList.pop(modifyAuthPk);
              } else if (operatorType == "insert") {
                url = "/userauth/insertUserAuth"
                $(".authButton[pk='"+modifyAuthPk+"']").addClass("btn-primary");
                $(".authButton[pk='"+modifyAuthPk+"']").removeClass("btn-light");
                authPkList.push(modifyAuthPk);
              }
              $("#usernameDiv").attr("userAuthPkList", authPkList);
            } else {
              $("#userAuthOperatorResultMsg").val("edit fail: " + data.message);
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