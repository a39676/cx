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
<script type="text/javascript" src="/static_resources/js/chartJS/chartJS/chartJs_v_2_9_4.js"></script>

<script type="text/javascript">

  new Chart("bigMoveBySymbomCountingChart", {
    type: "bubble",
    data: {
      datasets: [{
        label: 'Falling',
        data: [
          <c:forEach items="${saleList}" var="subData" varStatus="loop">
            {
              x: ${subData.timeGap},
              y: ${subData.price},
              r: ${subData.r},
            },
          </c:forEach>
        ],
        backgroundColor: 'rgba(255, 99, 132, 0.6)'
      },{
        label: 'Buying',
        data: [
          <c:forEach items="${buyList}" var="subData" varStatus="loop">
            {
              x: ${subData.timeGap},
              y: ${subData.price},
              r: ${subData.r},
            },
          </c:forEach>
        ],
        backgroundColor: 'rgba(46, 204, 113, 0.6)'
      }]
    },
    options: {
      legend: {display: true}
    }
  });

</script>
