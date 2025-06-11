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

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12">
        ${msg}
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <thead>
            <tr>
              <td>Symbol</td>
              <td>free</td>
              <td>locked</td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${accountInfo.balances}" var="balance" varStatus="loop">
              <tr>
                <td>
                  ${balance.asset}
                  <a href="/cryptoTradingSpot/getOrdersBySymbolSpot?symbol=${balance.asset}USDT&userId=${userId}&nickname=${nickname}" target="_blank">USDT</a>
                  <a href="/cryptoTradingSpot/getOrdersBySymbolSpot?symbol=${balance.asset}USDC&userId=${userId}&nickname=${nickname}" target="_blank">USDC</a>
                </td>
                <td>${balance.free}</td>
                <td>${balance.locked}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <tr>
            <td>canTrade</td>
            <td>canWithdraw</td>
            <td>canDeposit</td>
            <td>brokered</td>
            <td>requireSelfTradePrevention</td>
            <td>preventSor</td>
          </tr>
          <tr>
            <td>${accountInfo.canTrade}</td>
            <td>${accountInfo.canWithdraw}</td>
            <td>${accountInfo.canDeposit}</td>
            <td>${accountInfo.brokered}</td>
            <td>${accountInfo.requireSelfTradePrevention}</td>
            <td>${accountInfo.preventSor}</td>
          </tr>
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
