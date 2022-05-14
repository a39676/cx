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

  <div class="container-fluid">

    <div class="row">
      <div class="col-md-12">
        <table class="table table-hover table-bordered table-striped table-light">
          <thead class="">
            <tr class="table-primary">
              <td>Param</td>
              <td>Start time</td>
              <td>End time</td>
              <td></td>
            </tr>
          </thead>
          <c:forEach items="${reportVoList}" var="reportVO">
            <tbody>
              <tr class="">
                <td>
                  <div class="col-md-12" style="word-break:break-word;">
                    <span class="badge">证件号码: ${reportVO.idNumber}</span>
                    <span class="badge">手机号: ${reportVO.phoneNumber}</span><br>
                    <span class="badge">LastName(姓): ${reportVO.customerLastName}</span>
                    <span class="badge">FirstName(名): ${reportVO.customerFirstName}</span><br>
                    <span class="badge">证件类型: ${reportVO.idTypeCnName}</span>
                    <span class="badge">国籍(国家/地区): ${reportVO.areaTypeCnName}</span>
                    <span class="badge">手机区号: ${reportVO.phoneAreaCnName}</span>
                  </div>
                </td>
                <td>
                  ${reportVO.startTimeStr}
                </td>
                <td>
                  ${reportVO.endTimeStr}
                </td>
                <td>
                  <c:if test="${reportVO.endTimeStr == null}">
                    <span class="badge badge-primary">Waiting</span>
                  </c:if>
                  <c:if test="${reportVO.endTimeStr != null && reportVO.isPass == true}">
                    <span class="badge badge-success">Pass</span>
                  </c:if>
                  <c:if test="${reportVO.endTimeStr != null && reportVO.isPass == false}">
                    <span class="badge badge-danger">Failed</span>
                  </c:if>
                </td>
              </tr>
            </tbody>
          </c:forEach>
        </table>
      </div>
    </div>
  </div>

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
