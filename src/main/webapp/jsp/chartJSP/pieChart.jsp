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

<canvas id="chartView" width="800" height="450"></canvas>

</body>

<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script type="text/javascript">

$(document).ready(function() {

  new Chart(document.getElementById("chartView"), {
    type: 'pie',
    data: {
      labels: [
        <c:forEach items="${labels}" var="label">
          "${label}",
        </c:forEach>
      ],
      datasets: [
        {
          label: "${chartTitle}",
          backgroundColor: [
            <c:forEach items="${randomColorList}" var="subColor">
              "#${subColor.hexString}",
            </c:forEach>
          ],
          data: [
            <c:forEach items="${dataList}" var="subData">
              ${subData},
            </c:forEach>
          ]
        }
      ]
    },
    options: {
      title: {
        display: true,
        text: '${chartTitle}'
      }
    }
  });
  
});

</script>
</footer>
</html>