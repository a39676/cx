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
              <td>持仓方向</td>
              <td>数量</td>
              <td>
                开仓均价<br>
                盈亏平衡价<br>
                当前价格
              </td>
              <td>未实现盈亏</td>
              <td>参考强平价</td>
              <td>杠杆倍数</td>
              <%-- <td>当前杠杆倍数允许的名义价值上限</td> --%>
              <td>逐仓/全仓</td>
              <td>逐仓保证金</td>
              <%-- <td>isAutoAddMargin</td> --%>
              <%-- <td>notional</td> --%>
              <%-- <td>isolatedWallet</td> --%>
              <%-- <td>更新时间</td> --%>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${dataList}" var="subData" varStatus="loop">
              <tr>
                <td>
                  <button class="symbolInOpenOrderList" symbol='${subData.symbol}'>
                    ${subData.symbol}
                  </button>
                </td>
                <c:choose>
                  <c:when test="${subData.positionSide == 'LONG'}">
                    <td class="table-success">${subData.positionSide}</td>    
                  </c:when>
                  <c:when test="${subData.positionSide == 'SHORT'}">
                    <td class="table-danger">${subData.positionSide}</td>    
                  </c:when>
                  <c:otherwise>
                    <td>${subData.positionSide}</td>
                  </c:otherwise>
                </c:choose>
                <td>
                  ${subData.positionAmt}<br>
                  ${subData.positionAmt * subData.entryPrice} (USDT)
                </td>
                <td>
                  ${subData.entryPrice}<br>
                  ${subData.breakEvenPrice}<br>
                  ${subData.markPrice}<br>
                  <fmt:formatNumber type = "number" maxIntegerDigits = "3" 
                      value = "${(subData.markPrice / subData.entryPrice - 1) * 100}"/>%
                </td>
                <c:choose>
                  <c:when test="${subData.unRealizedProfit > 0}">
                    <td class="table-success">${subData.unRealizedProfit}</td>    
                  </c:when>
                  <c:when test="${subData.unRealizedProfit < 0}">
                    <td class="table-danger">${subData.unRealizedProfit}</td>    
                  </c:when>
                  <c:otherwise>
                    <td>${subData.unRealizedProfit}</td>
                  </c:otherwise>
                </c:choose>
                <td>${subData.liquidationPrice}</td>
                <td>${subData.leverage}</td>
                <%-- <td>${subData.maxNotionalValue}</td> --%>
                <td>${subData.marginType}</td>
                <td>${subData.isolatedMargin}</td>
                <%-- <td>${subData.isAutoAddMargin}</td> --%>
                <%-- <td>${subData.notional}</td> --%>
                <%-- <td>${subData.isolatedWallet}</td> --%>
                <%-- <td>${subData.updateTime}</td> --%>
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
  $(document).ready(function() {

    $(".symbolInOpenOrderList").click(function () {
      var url = "/cryptoTradingFutureUm/getOrdersBySymbolUm";

      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var symbol = $(this).attr("symbol");

      var jsonOutput = {
        symbol:symbol,
        userId:selectedUserId,
        userNickname:selectedUserNickname,
      };
      $("#msg").text("sending");
      $("#ordersHistoryBySymbolResult").html("");
      $.ajax({
        type : "POST",
        async : true,
        url : url,
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        <%-- dataType: "json", --%>
        timeout:50000,
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#ordersHistoryBySymbolResult").html(datas);
          $("#msg").text("");
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    });

  });

</script>
