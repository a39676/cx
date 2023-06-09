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
    <div class="row d-flex justify-content-center">
      <div class="col-sm-12">
        <div style="object-fit: contain;">
          <img src="${promoteImgUrl}">
        </div>
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
