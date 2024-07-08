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
<title>${ title }</title>

<body>

  <label id="msg"></label>

  <div>
    <div id="chartDiv">
      <%@ include file="./btcAndLowCapIndexGapChart.jsp"%>
    </div>
  </div>

  <div>
    <div class="row">
      <div class="col-md-12">
        <button id="startLong">开多</button>|<button id="startShort">开空</button><br>
        <button id="stopLong">平多</button>|<button id="stopShort">平空</button>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <input type="text" name="" placeholder="symbols" id="orderSymbols">
        <input type="number" name="" id="orderAmount" placeholder="orderAmount">
        <select id="orderSide">
          <option value="1">Buy</option>
          <option value="2">Sell</option>
        </select>
        <select id="positionSide">
          <option value="1">Long</option>
          <option value="2">Short</option>
        </select>
        <select id="orderType">
          <option value="1">Limit</option>
          <option value="2">Market</option>
        </select>
        <input type="number" name="" id="preOrderRatio" placeholder="preOrderRatio %">
        <button id="submitUmFutureOrder">submitUmFutureOrder</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <input type="text" name="" placeholder="symbols" id="btcArbitrageSymbols">
        <input type="number" name="" id="btcArbitrageAmount" placeholder="btcArbitrageAmount">
        <button id="submitBtcArbitrageBatchOrder">submitBtcArbitrageBatchOrder</button>
      </div>
    </div>
  
</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript">
  $(document).ready(function() {
    $("#submitUmFutureOrder").click(function() {
      sendFutureOrder();
    });

    function sendFutureOrder(){
      var url = "/cryptoTrading/binanceFutureUmSendOrder";

      var orderSymbolsStr = $("#orderSymbols").val();
      var orderAmount = $("#orderAmount").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var positionSideCode = $('#positionSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var preOrderRatio = $("#preOrderRatio").val();
      
      var orderSymbols = orderSymbolsStr.split(",");

      var jsonOutput = {
        symbols:orderSymbols,
        amount:orderAmount,
        orderSideCode:orderSideCode,
        positionSideCode:positionSideCode,
        orderTypeCode:orderTypeCode,
        preOrderRatio:preOrderRatio,
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

<script type="text/javascript">
  $(document).ready(function() {
    $("#submitBtcArbitrageBatchOrder").click(function() {
      binanceUmBtcArbitrageBatchOrder();
    });

    function binanceUmBtcArbitrageBatchOrder(){
      var url = "/cryptoTrading/binanceUmBtcArbitrageBatchOrder";

      var btcArbitrageSymbols = $("#btcArbitrageSymbols").val();
      var singleAmount = $("#btcArbitrageAmount").val();

      var symbolArray = btcArbitrageSymbols.split(",");

      var jsonOutput = {
        symbols:symbolArray,
        singleAmount:singleAmount,
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

<script type="text/javascript">
  $(document).ready(function() {
    $("#startLong").click(function () {
      $("#orderSide").val("1").change();
      $("#positionSide").val("1").change();
    });
    $("#startShort").click(function () {
      $("#orderSide").val("2").change();
      $("#positionSide").val("2").change();
    });
    $("#stopLong").click(function () {
      $("#orderSide").val("2").change();
      $("#positionSide").val("1").change();
    });
    $("#stopShort").click(function () {
      $("#orderSide").val("1").change();
      $("#positionSide").val("2").change();
    });
  });
</script>

