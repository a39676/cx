<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <!-- as navigation -->
      <div class="col-md-3">
        <sec:authorize access="hasRole('ROLE_USER')">
          <%@ include file="./financeclearNavigationRoleUser.jsp" %>
        </sec:authorize>
      </div>
    </div>

    <div class="row">
      <form class="col-md-3" name="headForm">
        <label id="mark"></label>
        <div class="form-group">
          <!-- 预留隐藏字段 -->
          <input type="hidden" name="" value="${message}" />
        </div>
        <div class="form-group">
          <label>${exception}</label>
          <label>${emptyValue}</label>
        </div>
      </form>
    </div>
  </div>
</body>

<footer>
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

  });

</script>
</html>
