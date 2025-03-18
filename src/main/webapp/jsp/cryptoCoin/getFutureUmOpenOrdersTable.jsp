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
              <td>symbol</td>
              <td>
                下单时间<br>
                更新时间
              </td>
              <td>orderType</td>
              <td>下单数量</td>
              <td>下单价格</td>
              <td>stopPrice</td>
              <td>平均成交价格</td>
              <td>成交数量</td>
              <td>成交金额</td>
              <td>状态</td>
              <td>时效</td>
              <td>origType</td>
              <td>reduceOnly</td>
              <td>closePosition</td>
              <td>type</td>
              <td>activatePrice</td>
              <td>priceRate</td>
              <td>workingType</td>
              <td>priceProtect</td>
              <td>priceMatch</td>
              <td>selfTradePreventionMode</td>
              <td>goodTillDate</td>
              <td>
                clientOrderId<br>
                orderId
              </td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${dataList}" var="subData" varStatus="loop">
              <tr>
                <td>
                  ${subData.symbol}<br>
                  <button class="cancelOrderFutureUM btn btn-sm btn-danger"
                    symbol="${subData.symbol}" orderId="${subData.orderId}" 
                    userId="${userId}" userNickname="${userNickname}"
                    exchangeCode="${exchangeCode}">
                    CancelOrder
                  </button>
                </td>
                <td>
                  ${subData.orderTimeStr}<br>
                  ${subData.updateTimeStr}
                </td>
                <td>${subData.orderTypeInSimpleWord}</td>
                <td>${subData.origQty}</td>
                <td>${subData.price}</td>
                <td>${subData.stopPrice}</td>
                <td>${subData.avgPrice}</td>
                <td>${subData.executedQty}</td>
                <td>${subData.cumQuote}</td>
                <td>${subData.status}</td>
                <td>${subData.timeInForce.cnName}(${subData.timeInForce})</td>
                <td>${subData.origType}</td>
                <td>${subData.reduceOnly}</td>
                <td>${subData.closePosition}</td>
                <td>${subData.type}</td>
                <td>${subData.activatePrice}</td>
                <td>${subData.priceRate}</td>
                <td>${subData.workingType}</td>
                <td>${subData.priceProtect}</td>
                <td>${subData.priceMatch}</td>
                <td>${subData.selfTradePreventionMode}</td>
                <td>${subData.goodTillDate}</td>
                <td>
                  ${subData.clientOrderId}<br>
                  ${subData.orderId}
                </td>
              </tr>
            </c:forEach>
          </tbody>
          <tbody>
            <c:forEach items="${dataList}" var="subData" varStatus="loop">
              <c:choose>
                <c:when test="${subData.status.code == 8}">
                  <tr>
                    <td>${subData.avgPrice}</td>
                    <td>${subData.executedQty}</td>
                    <%-- <td>${subData.status.code}</td> --%>
                  </tr>  
                </c:when>
                <c:when test="${subData.status.code == 9}">
                  <tr>
                    <td>${subData.avgPrice}</td>
                    <td>${subData.executedQty}</td>
                    <%-- <td>${subData.status.code}</td> --%>
                  </tr>  
                </c:when>
                <c:otherwise>
                </c:otherwise>
              </c:choose>
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
    $(".cancelOrderFutureUM").click(function() {
      cancelOrderById($(this));
    });

    function cancelOrderById(ele){
      var url = "/cryptoTradingFutureUm/binanceFutureUmCancelOrderByID";

      var symbol = ele.attr("symbol");
      var orderId = ele.attr("orderId");
      var userId = ele.attr("userId");
      var userNickname = ele.attr("userNickname");
      var exchangeCode = ele.attr("exchangeCode");

      var jsonOutput = {
        symbol:symbol,
        orderId:orderId,
        userId:userId,
        userNickname:userNickname,
        exchangeCode:exchangeCode,
      };
      
      $("#msg").text("sending");
      $.ajax({
        type : "POST",
        async : true,
        url : url,
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        dataType: "json",
        timeout:50000,
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          if(datas.code != 0){
            $("#msg").text("Done: " + datas.message);
          } else {
            $("#msg").text(datas.message);
          }
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }
  });
</script>
