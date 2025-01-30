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
              <td>timeStr</td>
              <td>symbol</td>
              <td>side</td>
              <td>price</td>
              <td>type</td>
              <td>下单数量(币)</td> <%-- (origQty) --%>
              <td>已执行</td> <%-- (executedQty) --%>
              <td>下单数量(U)</td> <%-- (cummulativeQuoteQty) --%>
              <td>status</td>
              <td>timeInForce</td>
              <td>更新时间</td>
              <td>workingTimeStr</td>
              <td>stopPrice</td>
              <td>icebergQty</td>
              <td>updateTime</td>
              <td>isWorking</td>
              <td>workingTime</td>
              <td>原始报价数量</td> <%-- (origQuoteOrderQty) --%>
              <td>selfTradePreventionMode</td>
              <td>time</td>
              <td>orderId</td>
              <td>orderListId</td>
              <td>clientOrderId</td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${orderList}" var="subData" varStatus="loop">
              <tr>
                <td>${subData.timeStr}</td>
                <td>
                  ${subData.symbol}<br>
                  <button class="cancelOrderSpot btn btn-sm btn-danger"
                    symbol="${subData.symbol}" orderId="${subData.orderId}" 
                    userId="${userId}" userNickname="${userNickname}"
                    exchangeCode="${exchangeCode}">
                    CancelOrder
                  </button>
                </td>
                <td>${subData.side}</td>
                <td>${subData.price}</td>
                <td>${subData.type}</td>
                <td>${subData.origQty}</td>
                <td>${subData.executedQty}</td>
                <td>${subData.cummulativeQuoteQty}</td>
                <td>${subData.status}</td>
                <td>${subData.timeInForce}</td>
                <td>${subData.updateTimeStr}</td>
                <td>${subData.workingTimeStr}</td>
                <td>${subData.stopPrice}</td>
                <td>${subData.icebergQty}</td>
                <td>${subData.updateTime}</td>
                <td>${subData.isWorking}</td>
                <td>${subData.workingTime}</td>
                <td>${subData.origQuoteOrderQty}</td>
                <td>${subData.selfTradePreventionMode}</td>
                <td>${subData.time}</td>
                <td>${subData.orderId}</td>
                <td>${subData.orderListId}</td>
                <td>${subData.clientOrderId}</td>
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
    $(".cancelOrderSpot").click(function() {
      cancelOrderById($(this));
    });

    function cancelOrderById(ele){
      var url = "/cryptoTradingSpot/binanceSpotCancelOrderByID";

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
