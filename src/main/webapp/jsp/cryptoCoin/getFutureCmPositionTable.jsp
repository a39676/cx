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
              <td>持仓</td>
              <td>持仓方向</td>
              <td>进入价</td>
              <td>损益平衡价</td>
              <td>目前报价</td>
              <td>未实现损益</td>
              <td>清算价</td>
              <td>杠杆数</td>
              <td>基礎資產最大數量</td>
              <td>逐/全仓</td>
              <td>逐仓保证金</td>
              <td>是否自动添加保证金</td>
              <td>名义价值</td>
              <td>更新时间</td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${dataList}" var="subData" varStatus="loop">
              <tr>
                <td>${subData.symbol}</td>
                <td>${subData.positionAmt}(张)<br>${subData.positionAmt*100}(USD)</td>
                <c:choose>
                  <c:when test="${subData.positionSide == 'LONG'}">
                    <td class="table-success">
                      ${subData.positionSide}
                    </td>
                  </c:when>
                  <c:when test="${subData.positionSide == 'SHORT'}">
                    <td class="table-danger">
                      ${subData.positionSide}
                    </td>
                  </c:when>
                  <c:otherwise>
                    <td>${subData.unRealizedProfit}</td>
                  </c:otherwise>
                </c:choose>
                <td>${subData.entryPrice}</td>
                <td>${subData.breakEvenPrice}</td>
                <td>${subData.markPrice}</td>
                <c:choose>
                  <c:when test="${subData.unRealizedProfit > 0}">
                    <td class="table-success">
                      ${subData.unRealizedProfit}<br>
                      ${subData.unRealizedProfit * subData.markPrice}(USD)
                    </td>
                  </c:when>
                  <c:when test="${subData.unRealizedProfit < 0}">
                    <td class="table-danger">
                      ${subData.unRealizedProfit}<br>
                      ${subData.unRealizedProfit * subData.markPrice}(USD)
                    </td>
                  </c:when>
                  <c:otherwise>
                    <td>${subData.unRealizedProfit}</td>
                  </c:otherwise>
                </c:choose>
                <td>${subData.liquidationPrice}</td>
                <td>${subData.leverage}</td>
                <td>${subData.maxQty}</td>
                <td>${subData.marginType}</td>
                <td>${subData.isolatedMargin}</td>
                <td>${subData.isAutoAddMargin}</td>
                <td>${subData.notionalValue}</td>
                <td>${subData.updateTime}</td>
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
