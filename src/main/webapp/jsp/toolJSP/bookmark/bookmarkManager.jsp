<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-dark">
          <thead class="thead-dark">
            <tr>
              <td>Name</td>
              <td>Need pwd</td>
              <td></td>
            </tr>
          </thead>
          <tbody id="bookmarkVoList">
            <c:forEach items="${bookmarkList}" var="bookmarkVO">
              <tr class="bookmarkVO">
                <td>
                  <a href="/bookmark/detail?pk=${bookmarkVO.pk}">${bookmarkVO.bookmarkName}</a>
                </td>
                <td>
                  <input name="needPwd" bookmarkPK="${bookmarkVO.pk}" type="text"
                  value="${bookmarkVO.needPwd}">
                </td>
                <td>
                  Edit
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {


    });

  </script>
</footer>
</html>
