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
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <thead>
            <tr>
              <td>Symbol</td>
              <td>amountTotal</td>
              <td>countingTotal</td>
              <td>amountBuy</td>
              <td>amountSell</td>
              <td>countingBuy</td>
              <td>countingSell</td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${dataList}" var="subData" varStatus="loop">
              <tr>
                <td>
                  <a href="https://www.zhang3.xyz/cryptoCoinData/bigTradeFutureUmChartBySymbol?symbol=${subData.symbol}"
                   target="_blank">
                    ${subData.symbol}
                  </a>
                </td>
                <c:choose>
                  <c:when test="${subData.amountTotal > 0}">
                    <td class="table-success">${subData.amountTotal}</td>  
                  </c:when>
                  <c:when test="${subData.amountTotal < 0}">
                    <td class="table-danger">${subData.amountTotal}</td>  
                  </c:when>
                  <c:otherwise>
                    <td>${subData.amountTotal}</td>  
                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${subData.countingTotal > 0}">
                    <td class="table-success">${subData.countingTotal}</td>  
                  </c:when>
                  <c:when test="${subData.countingTotal < 0}">
                    <td class="table-danger">${subData.countingTotal}</td>  
                  </c:when>
                  <c:otherwise>
                    <td>${subData.countingTotal}</td>
                  </c:otherwise>
                </c:choose>
                <td>${subData.amountBuy}</td>
                <td>${subData.amountSell}</td>
                <td>${subData.countingBuy}</td>
                <td>${subData.countingSell}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>

</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>

<script type="text/javascript">
  

</script>
