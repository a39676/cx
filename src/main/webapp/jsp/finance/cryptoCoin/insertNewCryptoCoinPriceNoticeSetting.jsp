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
  <input type="text" id="timeRangeOfDataWatch" placeholder="监控数据时间范围">
  <select id="timeUnitOfDataWatch">
    <c:forEach items="${timeUnitType}" var="timeUnitType">
      <option value="${timeUnitType.code}">${timeUnitType.cnName}</option>
    </c:forEach>
  </select>
  <input type="text" id="fluctuationSpeedPercentage" placeholder="升速/跌速范围(%)">
</div>

<div class="row">
  <input type="text" id="timeRangeOfNoticeInterval" placeholder="提示时间间隔设置">
  <select id="timeUnitOfNoticeInterval">
    <c:forEach items="${timeUnitType}" var="timeUnitType">
      <option value="${timeUnitType.code}">${timeUnitType.cnName}</option>
    </c:forEach>
  </select>
</div>

<div class="row">
  <input type="Date" id="noticeStartDate"><input type="Time" id="noticeStartTime" value="00:00">
</div>

<div class="row">
  <input type="number" id="noticeCount" placeholder="提醒次数">
</div>

<hr>

<button id="insert">insert</button>

<hr>

<p>insert result: </p>
<p id="result"></p>

<div class="row">
  <div class="col-md-12">
    <table class="table table-striped table-dark">
      <thead class="thead-dark">
        <tr>
          <td>接收人</td> <%-- noticeReciver --%>
          <td>数据时间范围</td> <%-- timeUnitOfDataWatch --%>
          <td>通知间隔</td> <%-- timeUnitOfNoticeInterval --%>
          <td>加密币</td> <%-- cryptoCoinName --%>
          <td>兑换币</td> <%-- currencyName --%>
          <td>监控最高价</td> <%-- maxPrice --%>
          <td>监控最低价</td> <%-- minPrice --%>
          <td>监控涨跌速(%)</td> <%-- fluctuactionSpeedPercentage --%>
          <td>剩余提醒次数</td> <%-- noticeCount --%>
          <td>上次提醒时间</td> <%-- noticeTime --%>
          <td>下次提醒时间</td> <%-- nextNoticeTime --%>
          <td>有效期至</td> <%-- validTime --%>
          <td></td>
        </tr>
      </thead>
      <c:forEach items="${noticeVOList}" var="noticeVO">
        <tr class="noticeVO" noticePK="${noticeVO.pk}">
          <td noticePK="${noticeVO.pk}" name="noticeReciver">${noticeVO.noticeReciver}</td>
          <td>
            <input type="number" noticePK="${noticeVO.pk}" name="timeRangeOfDataWatch" 
            value="${noticeVO.timeRangeOfDataWatch}" style="width: 80px;">
            <select noticePK="${noticeVO.pk}" name="timeUnitOfDataWatchName">
              <option value="${noticeVO.timeUnitOfDataWatch}">${noticeVO.timeUnitOfDataWatchName}</option>
              <c:forEach items="${timeUnitType}" var="timeUnitType">
                <option value="${timeUnitType.code}">${timeUnitType.cnName}</option>
              </c:forEach>
            </select>
          </td>
          <td>
            <input type="number" noticePK="${noticeVO.pk}" name="timeUnitOfNoticeInterval" 
            value="${noticeVO.timeUnitOfNoticeInterval}" style="width: 80px;">
            <select noticePK="${noticeVO.pk}" name="timeUnitOfNoticeIntervalName">
              <option value="${noticeVO.timeUnitOfNoticeInterval}">${noticeVO.timeUnitOfNoticeIntervalName}</option>
              <c:forEach items="${timeUnitType}" var="timeUnitType">
                <option value="${timeUnitType.code}">${timeUnitType.cnName}</option>
              </c:forEach>
            </select>
          </td>
          <td>
            <select noticePK="${noticeVO.pk}" name="cryptoCoinCode">
              <option value="${noticeVO.cryptoCoinCode}">${noticeVO.cryptoCoinName}</option>
              <c:forEach items="${cryptoCoinType}" var="subCoinType">
                <option value="${subCoinType.code}">${subCoinType.name}</option>
              </c:forEach>
            </select>
          </td>
          <td>
            <select noticePK="${noticeVO.pk}" name="currencyCode">
              <option value="${noticeVO.currencyCode}">${noticeVO.currencyName}</option>
              <c:forEach items="${currencyType}" var="subCurrencyType">
                <option value="${subCurrencyType.code}">${subCurrencyType.name}</option>
              </c:forEach>
            </select>
          </td>
          <td>
            <input type="" noticePK="${noticeVO.pk}" name="maxPrice" 
            value="${noticeVO.maxPrice}" style="width: 80px;">
          </td>
          <td>
            <input type="" noticePK="${noticeVO.pk}" name="minPrice" 
            value="${noticeVO.minPrice}" style="width: 80px;">
          </td>
          <td>
            <input type="number" noticePK="${noticeVO.pk}" name="fluctuactionSpeedPercentage" 
            value="${noticeVO.fluctuactionSpeedPercentage}" style="width: 80px;">
          </td>
          <td>
            <input type="number" noticePK="${noticeVO.pk}" name="noticeCount" 
            value="${noticeVO.noticeCount}" style="width: 80px;">
          </td>
          <td>${noticeVO.noticeTime}</td>
          <td>${noticeVO.nextNoticeTime}</td>
          <td>${noticeVO.validTime}</td>
          <td>
            <button name="modify" noticePK="${noticeVO.pk}">修改</button>
            <button name="delete" noticePK="${noticeVO.pk}">删除</button>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      document.getElementById('noticeStartDate').valueAsDate = new Date();
      document.getElementById('validTime').valueAsDate = new Date();

      $("#insert").click(function () {
        insertCryptoCoinNoticeSetting();
      });

      $("button[name='delete']").click(function () {
        var pk = $(this).attr("noticePK");
        console.log(pk);
        deleteNotice(pk);
      })

      function insertCryptoCoinNoticeSetting() {
      
        var url = "/cryptoCoin/insertCryptoCoinNoticeSetting";

        var coinType = $("#coinType option:selected").val();
        var currencyType = $("#currencyType option:selected").val();

        var maxPrice = $("#maxPrice").val();
        var minPrice = $("#minPrice").val();
        
        var originalPrice = $("#originalPrice").val();
        var pricePercentage = $("#pricePercentage").val();

        var timeUnitOfDataWatch = $("#timeUnitOfDataWatch option:selected").val();
        var timeRangeOfDataWatch = $("#timeRangeOfDataWatch").val();
        var fluctuationSpeedPercentage = $("#fluctuationSpeedPercentage").val();

        var timeUnitOfNoticeInterval = $("#timeUnitOfNoticeInterval option:selected").val();
        var timeRangeOfNoticeInterval = $("#timeRangeOfNoticeInterval").val();

        var telegramChatPK = $("#telegramChatPK option:selected").val();
        var validTime = $("#validTime").val();

        var startNoticeTime = $("#noticeStartDate").val() + " " + $("#noticeStartTime").val();

        var noticeCount = $("#noticeCount").val();

        var jsonOutput = {
          coinType : coinType,
          currencyType : currencyType,
          maxPrice : maxPrice,
          minPrice : minPrice,
          originalPrice : originalPrice,
          pricePercentage : pricePercentage,
          timeUnitOfDataWatch : timeUnitOfDataWatch,
          timeRangeOfDataWatch : timeRangeOfDataWatch,
          fluctuationSpeedPercentage : fluctuationSpeedPercentage,
          timeUnitOfNoticeInterval : timeUnitOfNoticeInterval,
          timeRangeOfNoticeInterval : timeRangeOfNoticeInterval,
          telegramChatPK : telegramChatPK,
          noticeCount : noticeCount,
          validTime : validTime,
          startNoticeTime : startNoticeTime,
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
          }, 
          error:function(e){
            $("#result").text(e);
          }
        });
      };

      function deleteNotice(pk) {
        var url = "/cryptoCoin/deleteNotice";
        var jsonOutput = {
          pk : pk,
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
          }, 
          error:function(e){
            $("#result").text(e);
          }
        });
      }
    });

  </script>
</footer>
</html>