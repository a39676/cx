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
  </div>

</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript" src="/static_resources/js/chartJS/chartJS/chartJs_v_4_4_1.js"></script>

<script type="text/javascript">

  const data = {
    datasets: [{
      label: 'First Dataset',
      data: [{
        x: 20,
        y: 30,
        r: 15
      }, {
        x: 40,
        y: 10,
        r: 10
      }],
      backgroundColor: 'rgba(255, 99, 132, 0.2)'
    }]
  };

  const config = {
    type: 'bubble',
    data: data,
    options: {}
  };

  new Chart("bigMoveBySymbomCountingChart", config);


  <%-- new Chart("bigMoveBySymbomCountingChart", {
    type: "bubble",
    data: {
      datasets: [{
        data: ${countingList},
        borderColor: "#fad7a0",
        fill: false,
        label: "countingList"
      },{
        data: ${raisingCounting},
        borderColor: "#28b463",
        fill: false,
        label: "raisingCounting"
      },{
        data: ${fallingCounting},
        borderColor: "#cb4335",
        fill: false,
        label: "fallingCounting"
      }]
    },
    options: {
      legend: {display: true}
    }
  }); --%>

</script>
