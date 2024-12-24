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
      <%-- <%@ include file="./btcAndLowCapIndexGapChart.jsp"%> --%>
    </div>
  </div>

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
              <button id="startLong" class="btn btn-sm btn-success">开多</button>
              <button id="startShort" class="btn btn-sm btn-danger">开空</button>
              <button id="stopLong" class="btn btn-sm btn-danger">平多</button>
              <button id="stopShort" class="btn btn-sm btn-success">平空</button>
            </td>
          </tr>
          <tr>
            <td>
              <label>Symbols</label>
            </td>
            <td>
              <label>OrderAmount</label>
            </td>
            <td>
              <label>preOrderRatio</label>
            </td>
            <td>
              <label>orderQuantityRatio</label>
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
          </tr>
          <tr>
            <td>
              <input type="text" name="" placeholder="symbols" id="orderSymbols">
            </td>
            <td>
              <input type="number" name="" id="orderAmount" placeholder="orderAmount">
            </td>
            <td>
              <input type="number" name="" id="preOrderRatio" placeholder="preOrderRatio %">
            </td>
            <td>
              <input type="number" name="" id="orderQuantityRatio" placeholder="orderQuantityRatio">
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
          </tr>
          <tr>
            <td>
              <label>modifyOrderId</label>
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="" placeholder="modifyOrderId" id="modifyOrderId">
            </td>
          </tr>

          <tr>
            <td>
              <button id="submitUmFutureOrder">Create order(UM)</button>
            </td>
            <td>
              <button id="submitUmFutureOrderModify">Update order(UM)</button>
            </td>
            <td>
              <button id="submitClosePositionByRatio">Close position by ratio(UM)</button>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <%@ include file="./symbolGroupData.jsp"%> 
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="getPositionInfo">getPositionInfo</button>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <button id="getOpenOrders">getOpenOrders</button>
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
        <div id="openOrdersResult">
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div id="ordersHistoryBySymbolResult">
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
    $("#submitUmFutureOrder").click(function() {
      sendFutureOrder();
    });

    function sendFutureOrder(){
      var url = "/cryptoTradingFutureUm/binanceFutureUmSendOrder";

      var orderSymbolsStr = $("#orderSymbols").val();
      var orderAmount = $("#orderAmount").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var positionSideCode = $('#positionSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var preOrderRatio = $("#preOrderRatio").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      
      var orderSymbols = orderSymbolsStr.split(",");

      var jsonOutput = {
        symbols:orderSymbols,
        amount:orderAmount,
        orderSideCode:orderSideCode,
        positionSideCode:positionSideCode,
        orderTypeCode:orderTypeCode,
        preOrderRatio:preOrderRatio,
        userId:selectedUserId,
        userNickname:selectedUserNickname,
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
    $("#submitUmFutureOrderModify").click(function() {
      sendFutureOrderModify();
    });

    function sendFutureOrderModify(){
      var url = "/cryptoTradingFutureUm/binanceUmBatchOrderModify";

      var modifyOrderSymbolsStr = $("#orderSymbols").val();
      var modifyOrderId = $("#modifyOrderId").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var positionSideCode = $('#positionSide').find(":selected").val();
      var modifyOrderPriceRatio = $("#preOrderRatio").val();
      var modifyOrderQuantityRatio = $("#orderQuantityRatio").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      
      var modifyOrderSymbols = modifyOrderSymbolsStr.split(",");

      var jsonOutput = {
        symbols:modifyOrderSymbols,
        orderId:modifyOrderId,
        targetOrderSideCode:orderSideCode,
        targetPositionSideCode:positionSideCode,
        priceRatio:modifyOrderPriceRatio,
        quantityRatio:modifyOrderQuantityRatio,
        userId:selectedUserId,
        userNickname:selectedUserNickname,
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
    $("#submitClosePositionByRatio").click(function() {
      closePositionByQuantityRatio();
    });

    function closePositionByQuantityRatio(){
      var url = "/cryptoTradingFutureUm/binanceUmClosePositionByQuantityRatio";

      var orderSymbolsStr = $("#orderSymbols").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var positionSideCode = $('#positionSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var closePositionByRatioPreOrderRatio = $("#preOrderRatio").val();
      var closePositionQuantityRatio = $("#orderQuantityRatio").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      
      var orderSymbols = orderSymbolsStr.split(",");

      var jsonOutput = {
        symbols:orderSymbols,
        orderSideCode:orderSideCode,
        positionSideCode:positionSideCode,
        orderTypeCode:orderTypeCode,
        preOrderRatio:closePositionByRatioPreOrderRatio,
        closePositionQuantityRatio:closePositionQuantityRatio,
        userId:selectedUserId,
        userNickname:selectedUserNickname,
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

<script type="text/javascript">
  $(document).ready(function() {
    $("#getPositionInfo").click(function() {
      getPositionInfo();
    });

    function getPositionInfo(){
      var url = "/cryptoTradingFutureUm/positionInfoUm";

      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      
      var jsonOutput = {
        userId:selectedUserId,
        userNickname:selectedUserNickname,
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
      openOrdersResult();
    });

    function openOrdersResult(){
      var url = "/cryptoTradingFutureUm/getOpenOrdersUm";

      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      
      var jsonOutput = {
        userId:selectedUserId,
        userNickname:selectedUserNickname,
      };

      $("#msg").text("sending");
      $("#openOrdersResult").html("");
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
          $("#openOrdersResult").html(datas);
          $("#msg").text("");
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }
  });
</script>