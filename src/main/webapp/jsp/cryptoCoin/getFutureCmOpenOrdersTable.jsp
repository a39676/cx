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
              <td>下单价格</td>  <!-- "0", -->
              <td>平均成本</td>  <!-- "0.0", -->
              <td>cumBase</td>  <!-- "0", -->
              <td>已执行</td>  <!-- "0", -->
              <td>原下单数量</td>  <!-- "0.40", -->
              <td>origType</td>  <!-- "TRAILING_STOP_MARKET", -->
              <td>是否平仓</td>  <!-- false, -->
              <td>side</td>  <!-- "BUY", -->
              <td>positionSide</td>  <!-- "SHORT", -->
              <td>status</td>  <!-- "NEW", -->
              <td>stopPrice</td>  <!-- "9300", // please ignore when order type is TRAILING_STOP_MARKET -->
              <td>closePosition</td>  <!-- false, // if Close-All -->
              <td>timeInForce</td>  <!-- "GTC", -->
              <td>type</td>  <!-- "TRAILING_STOP_MARKET", -->
              <td>activatePrice</td>  <!-- "9020", // activation price, only return with TRAILING_STOP_MARKET order -->
              <td>priceRate</td>  <!-- "0.3", // callback rate, only return with TRAILING_STOP_MARKET order -->
              <td>workingType</td>  <!-- "CONTRACT_PRICE", -->
              <td>priceProtect</td>  <!-- false -->
              <td>
                orderId<br>
                clientOrderId
              </td>
              <td>time</td>  <!-- 1579276756075, // order time -->
              <td>updateTime</td>  <!-- 1579276756075, // update time -->
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${dataList}" var="subData" varStatus="loop">
              <tr>
                <td>
                  ${subData.symbol}<br>
                  <button class="cancelOrderFutureCM btn btn-sm btn-danger"
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
                <td>${subData.price}</td>  <!-- "0", -->
                <td>${subData.avgPrice}</td>  <!-- "0.0", -->
                <td>${subData.cumBase}</td>  <!-- "0", -->
                <td>${subData.executedQty}</td>  <!-- "0", -->
                <td>${subData.origQty}</td>  <!-- "0.40", -->
                <td>${subData.origType}</td>  <!-- "TRAILING_STOP_MARKET", -->
                <td>${subData.reduceOnly}</td>  <!-- false, -->
                <td>${subData.side}</td>  <!-- "BUY", -->
                <td>${subData.positionSide}</td>  <!-- "SHORT", -->
                <td>${subData.status}</td>  <!-- "NEW", -->
                <td>${subData.stopPrice}</td>  <!-- "9300", // please ignore when order type is TRAILING_STOP_MARKET -->
                <td>${subData.closePosition}</td>  <!-- false, // if Close-All -->
                <td>${subData.timeInForce}</td>  <!-- "GTC", -->
                <td>${subData.type}</td>  <!-- "TRAILING_STOP_MARKET", -->
                <td>${subData.activatePrice}</td>  <!-- "9020", // activation price, only return with TRAILING_STOP_MARKET order -->
                <td>${subData.priceRate}</td>  <!-- "0.3", // callback rate, only return with TRAILING_STOP_MARKET order -->
                <td>${subData.workingType}</td>  <!-- "CONTRACT_PRICE", -->
                <td>${subData.priceProtect}</td>  <!-- false -->
                <td>
                  ${subData.orderId}<br>
                  ${subData.clientOrderId}
                </td>
                <td>${subData.time}</td>  <!-- 1579276756075, // order time -->
                <td>${subData.updateTime}</td>  <!-- 1579276756075, // update time -->
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
    $(".cancelOrderFutureCM").click(function() {
      cancelOrderById($(this));
    });

    function cancelOrderById(ele){
      var url = "/cryptoTradingFutureCm/binanceFutureCmCancelOrderByID";

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

      console.log(jsonOutput);
      
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
