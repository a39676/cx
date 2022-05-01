<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
      <table class="table table-hover table-bordered table-striped table-light">
        <thead class="thead-dark">
          <tr>
            <th style="text-align: center; vertical-align: middle;">日期</th>
            <th style="text-align: center; vertical-align: middle;">手续费(手续费率)</th>
            <th style="text-align: center; vertical-align: middle;">扣除佣金后</th>
            <th style="text-align: center; vertical-align: middle;">刨除手续费后</th>
            <th style="text-align: center; vertical-align: middle;">份数</th>
            <th style="text-align: center; vertical-align: middle;">每份分币</th>
          </tr>
        </thead>
        <tr>
          <td style="text-align: center; vertical-align: middle;">${detail.markDateStr}</td>
          <td style="text-align: center; vertical-align: middle;">${detail.hanldingFee}(${detail.hanldingFeeRate})</td>
          <td style="text-align: center; vertical-align: middle;">${detail.restAfterHanldingFee}</td>
          <td style="text-align: center; vertical-align: middle;">${detail.restAfterCommissionFee}</td>
          <td style="text-align: center; vertical-align: middle;">${detail.partingCount}</td>
          <td style="text-align: center; vertical-align: middle;">${detail.coinCountingOfEachPartOfMachine}</td>
        </tr>
        <tr class="table-primary">
          <td></td>
          <td style="text-align: center; vertical-align: middle;">用户名</td>
          <td style="text-align: center; vertical-align: middle;">佣金(率)</td>
          <td style="text-align: center; vertical-align: middle;">份数</td>
          <td style="text-align: center; vertical-align: middle;">份额产出</td>
          <td style="text-align: center; vertical-align: middle;">总共应转</td>
        </tr>
        <c:forEach items="${detail.caculateResultList}" var="assistant">
          <tr>
            <td></td>
            <td style="text-align: center; vertical-align: middle;">${assistant.assistantName}</td>
            <td style="text-align: center; vertical-align: middle;">
              <c:set var="commissionFeeRateInPercent"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${assistant.commissionFeeRate * 100}" /></c:set>
              <span>${assistant.commissionFee}</span><span>(${commissionFeeRateInPercent}%)</span>
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <span>${assistant.partingCount}</span>
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <span>${assistant.receiveFromParting}</span>
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <span>${assistant.totalCoinCounting}</span>
            </td>
          </tr>
        </c:forEach>
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
