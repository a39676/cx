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
        <canvas id="countingChart" style="width:100%;"></canvas>
      </div>
      <div class="col-md-1">
      </div>
    </div>
    <div class="row">
      <div class="col-md-1">
      </div>
      <div class="col-md-10">
        <canvas id="countingChart2" style="width:100%;"></canvas>
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
  
  new Chart("countingChart", {
    type: "line",
    data: {
      labels: xValues,
      datasets: [{
        data: ${total},
        borderColor: "#fad7a0",
        fill: false,
        label: "total"
      },{
        data: ${mainCounting},
        borderColor: "#f9e79f",
        fill: false,
        label: "mainCounting"
      },{
        data: ${otherCounting},
        borderColor: "#d2b4de",
        fill: false,
        label: "otherCounting"
      }]
    },
    options: {
      legend: {display: true}
    }
  });

  new Chart("countingChart2", {
    type: "line",
    data: {
      labels: xValues,
      datasets: [{
        data: ${mainSummaryCounting},
        borderColor: "#d4ac0d",
        fill: false,
        label: "mainSummaryCounting"
      },{
        data: ${mainRisingCounting},
        borderColor: "#28b463",
        fill: false,
        label: "mainRisingCounting"
      },{
        data: ${mainFallingCounting},
        borderColor: "#cb4335",
        fill: false,
        label: "mainFallingCounting"
      },{
        data: ${mainTotalList},
        borderColor: "#9b59b6",
        fill: false,
        label: "mainTotalList"
      },{
        data: ${otherSummaryCounting},
        borderColor: "#f7dc6f",
        fill: false,
        label: "otherSummaryCounting"
      },{
        data: ${otherRisingCounting},
        borderColor: "#82e0aa",
        fill: false,
        label: "otherRisingCounting"
      },{
        data: ${otherFallingCounting},
        borderColor: "#f1948a",
        fill: false,
        label: "otherFallingCounting"
      },{
        data: ${otherTotalList},
        borderColor: "#c39bd3",
        fill: false,
        label: "otherTotalList"
      }]
    },
    options: {
      legend: {display: true}
    }
  });

</script>
