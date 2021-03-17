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

  <!-- org search start-->
  <div class="row">
    <div class="col-md-12">
      <%@ include file="./orgSearch.jsp" %>
    </div>
  </div>
  <!-- org search end-->

  <hr>

  <div class="row">
    <div class="col-md-12">
      <button class="btn btn-sm btn-success" id="addOrg">add new org</button>
      <button class="btn btn-sm btn-primary" id="editOrg">edit this org</button>
      <button class="btn btn-sm btn-danger" id="delOrg">delete org</button>
    </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-md-12">
      <button class="btn btn-sm btn-primary" id="editOrgAuth">edit auth</button>
    </div>
  </div>

  <hr>

  <!-- result row start-->
  <div class="row">
    <div class="col-md-12">
      <input type="" name="" id="orgOperatorResultMsg" disabled="disabled" style="width: 800px;">
    </div>
  </div>
  <!-- result row end-->

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#editOrgAuth").click(function () {
        var orgPK = $("#orgNameDiv").attr("selectedOrgPk");
        var url = "/auth/authManager?orgPK=" + encodeURIComponent(orgPK);
        window.open(url, '_blank');
      });

    });

  </script>
</footer>
</html>