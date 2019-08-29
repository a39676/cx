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
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<c:set var="lineNames" value="${lineNames}" />
<c:set var="dataLists" value="${dataLists}" />
<c:set var="lineNames" value="${lineNames}" />
<c:set var="randomColorList" value="${randomColorList}" />


<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script type="text/javascript">

  $(document).ready(function() {

    new Chart(document.getElementById("chartView"), {
      type: 'line',
      data: {
        labels: [
          <c:forEach items="${horizontalLabels}" var="horizontalLabel">
            "${horizontalLabel}",
          </c:forEach>
        ],
        datasets: [
          <c:forEach items="${lineNames}" var="lineName" varStatus="lineNameLoop">
            { 
              data: [
                <c:forEach items="${dataLists[lineNameLoop.index]}" var="subData">
                  ${subData},
                </c:forEach>
              ],
              label: "${lineName}",
              borderColor: "#${randomColorList[lineNameLoop.index].hexString}",
              fill: false
            },
          </c:forEach>
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