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
      <button class="btn btn-sm btn-primary" id="authInfo" pk="${authPK}">${authName}</button>
    </div>
  </div>

  <hr>
  
  <div class="row" id="sysRoleList" >
    <div class="col-md-12" >
      <c:forEach items="${sysRoleList}" var="sysRole">
        <button class="btn btn-sm btn-light roleButton" pk="${sysRole.pk}">${sysRole.roleName}</button>
      </c:forEach>
    </div>
  </div>

  <div class="row" id="orgRoleList" >
    <div class="col-md-12" >
      <c:forEach items="${orgRoleList}" var="orgRole">
        <button class="btn btn-sm btn-light roleButton" pk="${orgRole.pk}">${orgRole.roleName}</button>
      </c:forEach>
    </div>
  </div>

  <hr>

  <!-- result row start-->
  <div class="row">
    <div class="col-md-12">
      <input type="" name="" id="authOperatorResultMsg" disabled="disabled" style="width: 800px;">
    </div>
  </div>
  <!-- result row end-->

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      var hasRolePKStr = "${hasRolePK}";
      hasRolePKStr = hasRolePKStr.substr(1, hasRolePKStr.length-2);
      var hasRolePK = hasRolePKStr.split(', ');
      console.log(hasRolePK);

      var roleButton = document.querySelectorAll('.roleButton');
      console.log("roleButton.length: " + roleButton.length);
      for (var i = 0; i < roleButton.length; i++) {
        var pk = roleButton[i].getAttribute("pk");
        console.log("pk: " + pk);
        if (hasRolePK.includes(pk)) {
          var targetRoleButton = $(".roleButton[pk='"+pk+"']");
          targetRoleButton.removeClass("btn-light");
          targetRoleButton.addClass("btn-primary");
        }
      }

      $(".roleButton").click(function () {
        var rolePK = $(this).attr("pk");
        var authPk = "${authPK}";
        var operatorType;

        if (rolePK != null && authPk != null) {
          if ($(this).hasClass("btn-primary")) {
            operatorType = "delete";
          } else if ($(this).hasClass("btn-light")) {
            operatorType = "insert";
          }

          authRoleEdit(rolePK, authPk, operatorType);
        }
      });

      function authRoleEdit(rolePK, authPk, operatorType) {
        $("#authOperatorResultMsg").val("");
        var url;
        if(operatorType == "delete") {
          url = "/auth/deleteAuthRole"
        } else if (operatorType == "insert") {
          url = "/auth/insertAuthRole"
        } else {
          return;
        }

        var jsonOutput = {
          rolePK:rolePK,
          authPK:authPk
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
            if(data.code == "0") {
              $("#authOperatorResultMsg").val("edit success: " + $(".roleButton[pk='"+rolePK+"']").text());
              if(operatorType == "delete") {
                $(".roleButton[pk='"+rolePK+"']").addClass("btn-light");
                $(".roleButton[pk='"+rolePK+"']").removeClass("btn-primary");
              } else if (operatorType == "insert") {
                url = "/userauth/insertUserAuth"
                $(".roleButton[pk='"+rolePK+"']").addClass("btn-primary");
                $(".roleButton[pk='"+rolePK+"']").removeClass("btn-light");
              }
            } else {
              $("#authOperatorResultMsg").val("edit fail: " + data.message);
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