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
    <div class="row">
      <div class="col-md-12">
        <%@ include file="./userSelector.jsp"%>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <table class="table">
          <tr>
            <td>
              <label>限价:</label>
              <button id="startLongByLimit" class="btn btn-sm btn-success">开多</button>
              <button id="startShortByLimit" class="btn btn-sm btn-danger">开空</button>
              <button id="stopLongByLimit" class="btn btn-sm btn-danger">平多</button>
              <button id="stopShortByLimit" class="btn btn-sm btn-success">平空</button><br>
              <label>市价:</label>
              <button id="startLongByMarket" class="btn btn-sm btn-success">开多</button>
              <button id="startShortByMarket" class="btn btn-sm btn-danger">开空</button>
              <button id="stopLongByMarket" class="btn btn-sm btn-danger">平多</button>
              <button id="stopShortByMarket" class="btn btn-sm btn-success">平空</button>
            </td>
          </tr>
          <tr>
            <td>
              <label>Symbol(标的)</label>
            </td>
            <td>
              <label>quantity[数量(张)]</label><br>
              <label>repeat(重复次数)</label>
            </td>
            <td>
              <label>price(价格)</label>
            </td>
            <td>
              <label>orderSide</label>
            </td>
            <td>
              <label>positionSide</label>
            </td>
            <td>
              <label>orderType</label>
            </td>
            <td>
              <label>timeRange</label>
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="" placeholder="symbol" id="symbol">
            </td>
            <td>
              <input type="number" name="" id="quantity" placeholder="quantity"><br>
              <input type="number" name="" id="orderRepeatCounting" placeholder="orderRepeatCounting" 
              style="color:red;font-weight:bold;">
            </td>
            <td>
              <input type="number" name="" id="price" placeholder="price">
            </td>
            <td>
              <select id="orderSide">
                <option value="1">Buy</option>
                <option value="2">Sell</option>
              </select>
            </td>
            <td>
              <select id="positionSide">
                <option value="1">Long</option>
                <option value="2">Short</option>
              </select>
            </td>
            <td>
              <select id="orderType">
                <option value="1">Limit</option>
                <option value="2">Market</option>
              </select>
            </td>
            <td>
              <input type="text" name="" id="startTime" placeholder="startTime: 20251231">
              <input type="text" name="" id="endTime" placeholder="endTime: 20251231">
            </td>
          </tr>
          <tr>
            <td>
              <div class="btn-group">
                <button id="submitCmFutureOrder" class="btn btn-sm btn-primary">
                  Create order<br>
                  创建订单
                </button>
                <button id="submitCmFutureOrderForMultipleUser" class="btn btn-sm btn-success">
                  多用户
                </button>
              </div>
            </td>
            <td>
              <div class="btn-group">
                <button id="binanceFutureCmCancelMultipleOrder" class="btn btn-sm btn-primary">
                  Cancel multiple order<br>
                  取消订单
                </button><br>
                <button id="cmFutureCancleMultipleOrderForMultipleUser" class="btn btn-sm btn-success">
                  多用户
                </button>
              </div>
            </td>
            <td>
              <div class="btn-group">
                <button id="binanceFutureCmCloseBothLongShortPositionByMarket" class="btn btn-sm btn-primary">
                  CloseBothLongShortPosition<br>
                  同时市价平仓
                </button><br>
                <button id="binanceFutureCmCloseBothLongShortPositionByMarketMultipleUser" class="btn btn-sm btn-success">
                  多用户
                </button>
              </div>
            </td>
            <td>
              <button id="resetOrderSetting">ResetOrderSetting</button>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div class="row">
      <div class="col-md-6">
        <c:forEach items="${tradingSymbolList}" var="symbol" varStatus="loop">
          <button class="symbolButton btn btn-sm btn-secondary" symbol="${symbol}USD_PERP">${symbol}USD_PERP
          </button>
        </c:forEach>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="getPositionInfo" class="btn btn-sm btn-secondary">
          <label>getPositionInfo</label><br>
          <label>获取持仓信息</label>
        </button>
        <button id="getOpenOrders" class="btn btn-sm btn-secondary">
          <label>getOpenOrders</label><br>
          <label>获取挂单</label>
        </button>
        <button id="queryOrdersHistory" class="btn btn-sm btn-secondary">
          <label>queryOrdersHistory</label><br>
          <label>获取挂单历史</label>
        </button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="positionInfoResult">
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div id="queryOrdersResult">
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
    $("#submitCmFutureOrder").click(function() {
      sendFutureOrder();
    });

    function sendFutureOrder(){
      var url = "/cryptoTradingFutureCm/binanceFutureCmSendOrder";

      var symbol = $("#symbol").val();
      var quantity = $("#quantity").val();
      var orderRepeatCounting = $("#orderRepeatCounting").val();
      var price = $("#price").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var positionSideCode = $('#positionSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();
      

      var jsonOutput = {
        symbol:symbol,
        quantity:quantity,
        orderRepeatCounting: orderRepeatCounting,
        orderSideCode:orderSideCode,
        positionSideCode:positionSideCode,
        orderTypeCode:orderTypeCode,
        userId:selectedUserId,
        userNickname:selectedUserNickname,
        exchangeCode:selectedExchangeCode,
      };

      if(orderTypeCode == 1){
        jsonOutput["price"] = price;
      }

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

    $("#submitCmFutureOrderForMultipleUser").click(function() {
      sendFutureOrderForMultipleUser();
    });

    function sendFutureOrderForMultipleUser(){

      var url = "/cryptoTradingFutureCm/binanceFutureCmSendOrderMultipleUser";

      var symbol = $("#symbol").val();
      var quantity = $("#quantity").val();
      var orderRepeatCounting = $("#orderRepeatCounting").val();
      var price = $("#price").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var positionSideCode = $('#positionSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();
      var userIdList = [];
      var userNicknameList = [];
      $(':checkbox.userCheckbox:checked').each(function(i){
        userIdList[i] = Number($(this).attr("localUserId"));
        userNicknameList[i] = $(this).attr("userNickname");
      });

      var jsonOutput = {
        symbol:symbol,
        quantity:quantity,
        orderRepeatCounting:orderRepeatCounting,
        price:price,
        orderSideCode:orderSideCode,
        positionSideCode:positionSideCode,
        orderTypeCode:orderTypeCode,
        exchangeCode:selectedExchangeCode,
        userIdList:userIdList,
        userNicknameList:userNicknameList,
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

    $("#binanceFutureCmCancelMultipleOrder").click(function() {
      cancelFutureOrder();
    });

    function cancelFutureOrder(){
      var url = "/cryptoTradingFutureCm/binanceFutureCmCancelMultipleOrder";

      var symbol = $("#symbol").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var positionSideCode = $('#positionSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();

      var jsonOutput = {
        symbol:symbol,
        orderSideCode:orderSideCode,
        positionSideCode:positionSideCode,
        orderTypeCode:orderTypeCode,
        userId:selectedUserId,
        userNickname:selectedUserNickname,
        exchangeCode:selectedExchangeCode,
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

    $("#cmFutureCancleMultipleOrderForMultipleUser").click(function() {
      cancelFutureMultipleOrderForMultipleUser();
    });

    function cancelFutureMultipleOrderForMultipleUser(){
      var url = "/cryptoTradingFutureCm/binanceFutureCmCancelMultipleOrderMultipleUser";

      var userIdList = [];
      var userNicknameList = [];
      $(':checkbox.userCheckbox:checked').each(function(i){
        userIdList[i] = $(this).attr("localUserId");
        userNicknameList[i] = $(this).attr("userNickname");
      });

      var symbol = $("#symbol").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var positionSideCode = $('#positionSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();

      var jsonOutput = {
        symbol:symbol,
        orderSideCode:orderSideCode,
        positionSideCode:positionSideCode,
        orderTypeCode:orderTypeCode,
        exchangeCode:selectedExchangeCode,
        userIdList:userIdList,
        userNicknameList:userNicknameList,
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

    $("#binanceFutureCmCloseBothLongShortPositionByMarket").click(function() {
      binanceFutureCmCloseBothLongShortPositionByMarket();
    });

    function binanceFutureCmCloseBothLongShortPositionByMarket(){
      var url = "/cryptoTradingFutureCm/binanceFutureCmCloseBothLongShortPositionByMarket";

      var symbol = $("#symbol").val();
      var quantity = $("#quantity").val();
      var orderRepeatCounting = $("#orderRepeatCounting").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();

      var jsonOutput = {
        symbol:symbol,
        quantity:quantity,
        orderRepeatCounting:orderRepeatCounting,
        userId:selectedUserId,
        userNickname:selectedUserNickname,
        exchangeCode:selectedExchangeCode,
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

    $("#binanceFutureCmCloseBothLongShortPositionByMarketMultipleUser").click(function() {
      binanceFutureCmCloseBothLongShortPositionByMarketMultipleUser();
    });

    function binanceFutureCmCloseBothLongShortPositionByMarketMultipleUser(){
      var url = "/cryptoTradingFutureCm/binanceFutureCmCloseBothLongShortPositionByMarketMultipleUser";

      var userIdList = [];
      var userNicknameList = [];
      $(':checkbox.userCheckbox:checked').each(function(i){
        userIdList[i] = $(this).attr("localUserId");
        userNicknameList[i] = $(this).attr("userNickname");
      });
      
      var symbol = $("#symbol").val();
      var quantity = $("#quantity").val();
      var orderRepeatCounting = $("#orderRepeatCounting").val();
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();

      var jsonOutput = {
        symbol:symbol,
        quantity:quantity,
        orderRepeatCounting:orderRepeatCounting,
        userIdList:userIdList,
        userNicknameList:userNicknameList,
        exchangeCode:selectedExchangeCode,
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
    $("#getPositionInfo").click(function() {
      getPositionInfo();
    });

    function getPositionInfo(){
      var url = "/cryptoTradingFutureCm/positionInfoCm";

      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();
      
      var jsonOutput = {
        userId:selectedUserId,
        userNickname:selectedUserNickname,
        exchangeCode:selectedExchangeCode,
      };

      $("#msg").text("sending");
      $("#positionInfoResult").html("");
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
          $("#positionInfoResult").html(datas);
          $("#msg").text("");
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }

    $("#getOpenOrders").click(function() {
      queryOpenOrders();
    });

    function queryOpenOrders(){
      var url = "/cryptoTradingFutureCm/getOpenOrdersCm";

      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();
      
      var jsonOutput = {
        userId:selectedUserId,
        userNickname:selectedUserNickname,
        exchangeCode:selectedExchangeCode,
      };

      $("#msg").text("sending");
      $("#queryOrdersResult").html("");
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
          $("#queryOrdersResult").html(datas);
          $("#msg").text("");
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }

    $("#queryOrdersHistory").click(function() {
      queryOrdersHistory();
    });

    function queryOrdersHistory(){
      var url = "/cryptoTradingFutureCm/getOrdersBySymbolCm";

      var symbol = $("#symbol").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();
      var startTime = $("#startTime").val();
      var endTime = $("#endTime").val();
      
      var jsonOutput = {
        symbol:symbol,
        userId:selectedUserId,
        userNickname:selectedUserNickname,
        exchangeCode:selectedExchangeCode,
        startTime:startTime,
        endTime:endTime,
      };

      $("#msg").text("sending");
      $("#queryOrdersResult").html("");
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
          $("#queryOrdersResult").html(datas);
          $("#msg").text("");
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
    $(".symbolButton").click(function () {
      $("#symbol").val($(this).attr("symbol"));
      $("#quantity").val("");
      $("#orderRepeatCounting").val("");
      $("#price").val("");
    });
  });
</script>

<script type="text/javascript">
  $(document).ready(function() {
    $("#startLongByLimit").click(function () {
      $("#orderSide").val("1").change();
      $("#positionSide").val("1").change();
      $("#orderType").val("1").change();
    });
    $("#startShortByLimit").click(function () {
      $("#orderSide").val("2").change();
      $("#positionSide").val("2").change();
      $("#orderType").val("1").change();
    });
    $("#stopLongByLimit").click(function () {
      $("#orderSide").val("2").change();
      $("#positionSide").val("1").change();
      $("#orderType").val("1").change();
    });
    $("#stopShortByLimit").click(function () {
      $("#orderSide").val("1").change();
      $("#positionSide").val("2").change();
      $("#orderType").val("1").change();
    });

    $("#startLongByMarket").click(function () {
      $("#orderSide").val("1").change();
      $("#positionSide").val("1").change();
      $("#orderType").val("2").change();
    });
    $("#startShortByMarket").click(function () {
      $("#orderSide").val("2").change();
      $("#positionSide").val("2").change();
      $("#orderType").val("2").change();
    });
    $("#stopLongByMarket").click(function () {
      $("#orderSide").val("2").change();
      $("#positionSide").val("1").change();
      $("#orderType").val("2").change();
    });
    $("#stopShortByMarket").click(function () {
      $("#orderSide").val("1").change();
      $("#positionSide").val("2").change();
      $("#orderType").val("2").change();
    });

    $("#resetOrderSetting").click(function () {
      $("#orderSide").val("1").change();
      $("#positionSide").val("1").change();
      $("#orderType").val("1").change();
      $("#symbol").val("");
      $("#quantity").val("");
      $("#orderRepeatCounting").val("");
      $("#price").val("");
    })
  });
</script>
