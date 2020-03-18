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

      

    });

  </script>
</footer>
</html>