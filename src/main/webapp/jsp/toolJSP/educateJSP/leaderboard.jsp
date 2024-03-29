<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <%@ include file="../../baseElementJSP/normalHeader.jsp" %>

</head>
<body>

<table class="table table-hover table-striped table-light">
  <thead class="">
    <tr class="table-primary">
      <td>昵称</td>
      <td></td>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${leaderboard}" var="vo">
      <tr>
        <td><span>${vo.nickname}</span></td>
        <td><span>${vo.number}</span></td>
      </tr>
    </c:forEach>
  </tbody>
</table>

  <!-- SCIPTS -->
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">
    $(document).ready(function() {

    })
  </script>
</body>

<footer>

</footer>

</html>
