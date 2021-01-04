<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">

<div class="row">
  <select id="coinType">
    <c:forEach items="${cryptoCoinType}" var="subCoinType">
      <option value="${subCoinType.code}">${subCoinType.name}</option>
    </c:forEach>
  </select>

  <select id="currencyType">
    <c:forEach items="${currencyType}" var="subCurrencyType">
      <option value="${subCurrencyType.code}">${subCurrencyType.name}</option>
    </c:forEach>
  </select>
</div>

<div class="row">
  <select id="telegramChatPK">
    <c:forEach items="${chatVOList}" var="chatVO">
      <option value="${chatVO.pk}">${chatVO.username}</option>
    </c:forEach>
  </select>
  <input type="Date" id="validTime">
</div>

<div class="row">
  <input type="text" id="maxPrice" placeholder="高位提示价">
  <input type="text" id="minPrice" placeholder="低位提示价">
</div>

<div class="row">
  <input type="text" id="originalPrice" placeholder="参考价">
  <input type="text" id="pricePercentage" placeholder="价格波动范围(%)">
</div>

<div class="row">
  <input type="text" id="timeRange" placeholder="时间范围">
  <select id="timeUnit">
    <c:forEach items="${timeUnitType}" var="timeUnitType">
      <option value="${timeUnitType.code}">${timeUnitType.cnName}</option>
    </c:forEach>
  </select>
  <input type="text" id="fluctuationSpeedPercentage" placeholder="升速/跌速范围(%)">
</div>

<div class="row">
  <input type="number" id="noticeCount" placeholder="提醒次数">
</div>

<hr>

<button id="insert">insert</button>

<hr>

<p>insert result: </p>
<p id="result"></p>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#insert").click(function () {
        insertCryptoCoinNoticeSetting();
      });

      function insertCryptoCoinNoticeSetting() {
      
        var url = "/cryptoCoin/insertCryptoCoinNoticeSetting";

        var coinType = $("#coinType option:selected").val();
        var currencyType = $("#currencyType option:selected").val();

        var maxPrice = $("#maxPrice").val();
        var minPrice = $("#minPrice").val();
        
        var originalPrice = $("#originalPrice").val();
        var pricePercentage = $("#pricePercentage").val();

        var timeUnit = $("#timeUnit option:selected").val();
        var timeRange = $("#timeRange").val();
        var fluctuationSpeedPercentage = $("#fluctuationSpeedPercentage").val();

        var telegramChatPK = $("#telegramChatPK option:selected").val();
        var validTime = $("#validTime").val();

        var noticeCount = $("#noticeCount").val();

        var jsonOutput = {
          coinType : coinType,
          currencyType : currencyType,
          maxPrice : maxPrice,
          minPrice : minPrice,
          originalPrice : originalPrice,
          pricePercentage : pricePercentage,
          timeUnit : timeUnit,
          timeRange : timeRange,
          fluctuationSpeedPercentage : fluctuationSpeedPercentage,
          telegramChatPK : telegramChatPK,
          noticeCount : noticeCount,
          validTime : validTime,
        };

        $.ajax({  
          type : "POST", 
          url : url,  
          data: JSON.stringify(jsonOutput),
          dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            $("#result").text(data.message);
            console.log(data);
          }, 
          error:function(e){
            $("#result").text(e);
          }
        });
      };

    });

  </script>
</footer>
</html>