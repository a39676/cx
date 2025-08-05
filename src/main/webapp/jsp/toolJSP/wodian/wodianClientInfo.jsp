<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
  <table class="table table-striped table-bordered table-hover">
    <tr>
      <td>
        <label>客户</label>
      </td>
      <td>
        <label></label>
      </td>
    </tr>

    <tr>
      <td>
        <label>${name}</label>
      </td>
      <td>
        <label>${phone}</label>
      </td>
    </tr>
  </table>

</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    
  
  });

</script>
</footer>
</html>