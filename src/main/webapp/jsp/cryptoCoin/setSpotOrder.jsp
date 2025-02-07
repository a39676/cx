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
              <label>限价</label>
              <button id="buyByLimit" class="btn btn-sm btn-success">买入</button>
              <button id="sellByLimit" class="btn btn-sm btn-danger">卖出</button><br>
              <label>市价</label>
              <button id="buyByMarket" class="btn btn-sm btn-success">买入</button>
              <button id="sellByMarket" class="btn btn-sm btn-danger">卖出</button>
            </td>
          </tr>
          <tr>
            <td>
              <label>Symbols</label>
            </td>
            <td>
              <label>Price</label>
            </td>
            <td>
              <label>quantity</label>
            </td>
            <td>
              <label>orderSide</label>
            </td>
            <td>
              <label>orderType</label>
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="" placeholder="symbols" id="symbol">
            </td>
            <td>
              <input type="number" name="" id="price" placeholder="price">
            </td>
            <td>
              <input type="number" name="" id="quantity" placeholder="quantity">
            </td>
            <td>
              <select id="orderSide">
                <option value="1">Buy</option>
                <option value="2">Sell</option>
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
              <div class="btn-group">
                <button class="btn btn-sm btn-primary" id="submitSpotOrder">Create order</button>
                <button class="btn btn-sm btn-success" id="submitSpotOrderMultipleUser">多用户</button>
              </div>
            </td>
            <td>
              <div class="btn-group">
                <button id="spotCancelMultipleOrder" class="btn btn-sm btn-primary">
                  Cancel multiple order<br>
                  取消订单
                </button><br>
                <button id="spotCancleMultipleOrderForMultipleUser" class="btn btn-sm btn-success">
                  多用户
                </button>
              </div>
            </td>
            <td>
              <button id="resetOrderSetting">ResetOrderSetting</button>
            </td>
          </tr>

          <tr>
            <td>
              <c:forEach items="${tradingSymbolList}" var="symbol" varStatus="loop">
                <button class="symbolButton btn btn-sm btn-secondary" symbol="${symbol}USDT">${symbol}USDT</button>
              </c:forEach>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="getPositionInfo">getPositionInfo</button>
        <button id="getOpenOrders">getOpenOrders</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="getWalletBalance">getWalletBalance</button>
        <button id="getAccountSummary">getAccountSummary</button>
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
        <div id="openWalletBalanceResult">
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div>
          <div id="accountSummaryResult">
            
          </div>
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
    $("#buyByLimit").click(function () {
      $("#orderSide").val("1").change();
      $("#orderType").val("1").change();
    });
    $("#sellByLimit").click(function () {
      $("#orderSide").val("2").change();
      $("#orderType").val("1").change();
    });
    $("#buyByMarket").click(function () {
      $("#orderSide").val("1").change();
      $("#orderType").val("2").change();
    });
    $("#sellByMarket").click(function () {
      $("#orderSide").val("2").change();
      $("#orderType").val("2").change();
    });

    $("#resetOrderSetting").click(function () {
      $("#orderSide").val("1").change();
      $("#orderType").val("1").change();
      $("#symbol").val("");
      $("#quantity").val("");
      $("#price").val("");
    })
  });
</script>


<script type="text/javascript">
  $(document).ready(function() {

    $("#submitSpotOrder").click(function() {
      sendSpotOrder();
    });

    function sendSpotOrder(){
      var url = "/cryptoTradingSpot/spotSendOrder";

      var symbol = $("#symbol").val();
      var price = $("#price").val();
      var quantity = $("#quantity").val();
      var sideCode = $('#orderSide').find(":selected").val();
      var typeCode = $('#orderType').find(":selected").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();
      

      var jsonOutput = {
        symbol:symbol,
        price:price,
        sideCode:sideCode,
        typeCode:typeCode,
        quantity:quantity,
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

    $("#submitSpotOrderMultipleUser").click(function() {
      sendSpotOrderMultipleUser();
    });

    function sendSpotOrderMultipleUser(){
      var url = "/cryptoTradingSpot/spotSendOrderMultipleUser";

      var symbol = $("#symbol").val();
      var price = $("#price").val();
      var quantity = $("#quantity").val();
      var sideCode = $('#orderSide').find(":selected").val();
      var typeCode = $('#orderType').find(":selected").val();
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
        price:price,
        sideCode:sideCode,
        typeCode:typeCode,
        quantity:quantity,
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

    $("#spotCancelMultipleOrder").click(function() {
      cancelSpotOrder();
    });

    function cancelSpotOrder(){
      var url = "/cryptoTradingSpot/spotCancelMultipleOrder";

      var symbol = $("#symbol").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();

      var jsonOutput = {
        symbol:symbol,
        orderSideCode:orderSideCode,
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

    $("#spotCancleMultipleOrderForMultipleUser").click(function() {
      cancelSpotMultipleOrderForMultipleUser();
    });

    function cancelSpotMultipleOrderForMultipleUser(){
      var url = "/cryptoTradingSpot/spotCancelMultipleOrderMultipleUser";

      var userIdList = [];
      var userNicknameList = [];
      $(':checkbox.userCheckbox:checked').each(function(i){
        userIdList[i] = $(this).attr("localUserId");
        userNicknameList[i] = $(this).attr("userNickname");
      });

      var symbol = $("#symbol").val();
      var orderSideCode = $('#orderSide').find(":selected").val();
      var orderTypeCode = $('#orderType').find(":selected").val();
      var selectedExchange = $('#exchangeSelector').find(":selected");
      var selectedExchangeCode = selectedExchange.val();

      var jsonOutput = {
        symbol:symbol,
        orderSideCode:orderSideCode,
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

    $("#getPositionInfo").click(function() {
      getPositionInfo();
    });

    function getPositionInfo(){
      var url = "/cryptoTradingSpot/positionInfoSpot";

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
      openOrdersResult();
    });

    function openOrdersResult(){
      var url = "/cryptoTradingSpot/getOpenOrdersSpot";

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

    $("#getWalletBalance").click(function() {
      walletBalanceResult();
    });

    function walletBalanceResult(){
      var url = "/cryptoTradingSpot/getWalletBalance";

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
      $("#openWalletBalanceResult").html("");
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
          $("#openWalletBalanceResult").html(datas);
          $("#msg").text("");
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }

    $("#getAccountSummary").click(function() {
      accountSummaryResult();
    });

    function accountSummaryResult(){
      var url = "/ccm/getAccountSummary";

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
      $("#accountSummaryResult").html("");
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
          if (datas.success){
            $("#accountSummaryResult").html(datas.summary);
          } else {
            $("#accountSummaryResult").html(datas.message);
          }
          $("#msg").text("");
          <%-- TODO multiple exchange --%>
          
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
    });
  });
</script>