<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>

  <style type="text/css">
    .container-fluid{
      width: 100%;
    }
  </style>

</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-12">
        <img src="${promoteImgUrl}" style="width: 100%; object-fit: contain;">
      </div>
    </div>
  </div>
</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

  });

</script>
</footer>
</html>
