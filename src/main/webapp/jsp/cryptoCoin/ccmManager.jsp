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

  <div>
    <div class="row">
      <div class="col-md-12">
        <textarea id="msg"></textarea>
        <ul class="main-menu visible-on-click" id="CcmTool">
          <li style="background-color:yellow;">CCM tool</li>
          <li>
            <a href="/ccmManage/refreshOption" target="_blank">refreshSystemOption</a>
          </li>
          <li>
            <a href="/ccmManage/refreshCryptoCoinOption" target="_blank">refreshCryptoCoinOption</a>
          </li>
          <li>
            <a href="/ccmManage/refreshCryptoCoinPriceRangeOption" target="_blank">refreshCryptoCoinPriceRangeOption</a>
          </li>
          <li>
            <a href="/ccmManage/restartCryptoCoinWebSocketClient" target="_blank">restartCryptoCoinWebSocketClient</a>
          </li>
          <li>
            <a href="/ccmManage/clearAllOldRedisKey" target="_blank">clearAllOldRedisKey</a>
          </li>
          <li>
            <a href="/ccmManage/refreshBinanceTradingOption" target="_blank">refreshBinanceTradingOption</a>
          </li>
          <li>
            <a href="/ccmManage/refreshCryptoCoinIndexGapOption" target="_blank">refreshCryptoCoinIndexGapOption</a>
          </li>
          <li>
            <input type="text" id="reconnectBinanceBigTradeStreamSymbol">
            <button id="reconnectBinanceBigTradeStream">reconnectBinanceBigTradeStream</button>
          </li>
        </ul> <%-- CcmTool --%>
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
    $("#reconnectBinanceBigTradeStream").click(function() {
      sendFutureOrder();
    });

    function sendFutureOrder(){
      var url = "/ccmManage/reconnectBinanceBigTradeStream";

      var symbol = $("#reconnectBinanceBigTradeStreamSymbol").val();

      url = url + "?symbol=" + symbol;

      console.log(url);

      $("#msg").text("sending");
      $.ajax({
        type : "GET",
        async : true,
        url : url,
        // data: JSON.stringify(jsonOutput),
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
