<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp"%>
</head>
<sec:csrfMetaTags />

<body>

  <div>
    <div class="row">
      <div class="col-md-1">
      </div>
      <div class="col-md-10">
        <canvas id="bigMoveBySymbomCountingChart" style="width:100%;"></canvas>
      </div>
      <div class="col-md-1">
      </div>
    </div>
    <div class="row">
      <div class="col-md-1">
      </div>
      <div class="col-md-10">
        <canvas id="bigMoveBySymbomRatioChart" style="width:100%;"></canvas>
      </div>
      <div class="col-md-1">
      </div>
    </div>
  </div>

</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript" src="/static_resources/js/chartJS/chartJS/chartJs_v_2_9_4.js"></script>

<script type="text/javascript">
  var xValues = [];
  <c:forEach items="${xValues}" var="subData" varStatus="loop">
    xValues.push("${subData}");
  </c:forEach>
  
  new Chart("bigMoveBySymbomCountingChart", {
    type: "line",
    data: {
      labels: xValues,
      datasets: [{
        data: ${countingList},
        borderColor: "#fad7a0",
        fill: false,
        label: "countingList"
      },{
        data: ${raisingCounting},
        borderColor: "#f9e79f",
        fill: false,
        label: "raisingCounting"
      },{
        data: ${fallingCounting},
        borderColor: "#d2b4de",
        fill: false,
        label: "fallingCounting"
      }]
    },
    options: {
      legend: {display: true}
    }
  });

  new Chart("bigMoveBySymbomRatioChart", {
    type: "line",
    data: {
      labels: xValues,
      datasets: [{
        data: ${ratioList},
        borderColor: "#fad7a0",
        fill: false,
        label: "ratioList"
      }]
    },
    options: {
      legend: {display: true}
    }
  });

</script>
