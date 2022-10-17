<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
  <button class="btn btn-primary btn-sm">bootstrap demo</button>
</div>

<div class="row">
  From: 
  <input type="text" id="currencyFrom" name="" placeholder="From currency(USD)">
  To:
  <input type="text" id="currencyTo" name="" placeholder="From currency(USD)">
</div>

<div class="row">
  <select id="telegramChatPK">
    <c:forEach items="${chatVOList}" var="chatVO">
      <option value="${chatVO.pk}">${chatVO.username}</option>
    </c:forEach>
  </select>
</div>

<div class="row">
  <input type="text" id="maxRate" placeholder="高汇率提示位">
  <input type="text" id="minRate" placeholder="低汇率提示位">
</div>

<div class="row">
  <input type="text" id="originalRate" placeholder="参考汇率">
  <input type="text" id="pricePercentage" placeholder="汇率波动范围(%)">
</div>

<div class="row">
  <input type="text" id="timeRangeOfDataWatch" placeholder="监控数据时间范围">
  <select id="timeUnitOfDataWatch">
    <c:forEach items="${timeUnitType}" var="timeUnitType">
      <option value="${timeUnitType.code}">${timeUnitType.cnName}${timeUnitType.name}</option>
    </c:forEach>
  </select>
  <input type="text" id="fluctuationSpeedPercentage" placeholder="升速/跌速范围(%)">
</div>

<div class="row">
  <input type="text" id="timeRangeOfNoticeInterval" placeholder="提示时间间隔设置">
  <select id="timeUnitOfNoticeInterval">
    <c:forEach items="${timeUnitType}" var="timeUnitType">
      <option value="${timeUnitType.code}">${timeUnitType.cnName}${timeUnitType.name}</option>
    </c:forEach>
  </select>
</div>

<div class="row">
  <span>validTime(endTime)</span> <input type="Date" id="validTime">
</div>
<div class="row">
  <span>noticeStartTime</span><input type="Date" id="noticeStartDate"><input type="Time" id="noticeStartTime" value="00:00">
</div>

<div class="row">
  <input type="number" id="noticeCount" placeholder="提醒次数">
</div>

<hr>

<button id="insert">insert</button>

<hr>

<p>modify result: </p>
<p id="result" updatingFlag="0"></p>

<div class="row">
  <div class="col-md-12">
    <table class="table table-striped" id="noticeSearchConditionTable">
      <thead>
        <tr>
          <td>
            <select id="telegramChatPKOfSearch">
              <option value="">ALL</option>
              <c:forEach items="${chatVOList}" var="chatVO">
                <option value="${chatVO.pk}">${chatVO.username}</option>
              </c:forEach>
            </select>
          </td>
          <td>
            <input type="text" name="" id="coinTypeOfSearch">
          </td>
          <td>
            <select id="currencyOfSearch">
              <option value="">ALL</option>
              <c:forEach items="${currencyType}" var="subCurrencyType">
                <option value="${subCurrencyType.code}">${subCurrencyType.name}</option>
              </c:forEach>
            </select>
          </td>
        </tr>
      </thead>
    </table>
  </div>
</div>

<div id="noticeSearchResult">
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
        insertCurrencyExchangeRateNoticeSetting();
      });

      $("button[name='delete']").click(function () {
        var pk = $(this).attr("noticePK");
        deleteNotice(pk);
      })

      $("button[name='modify']").click(function () {
        var pk = $(this).attr("noticePK");
        updateNotice(pk);
      })

      searchNotice();

      function insertCurrencyExchangeRateNoticeSetting() {

        var url = "/currencyExchangeRateNotice/insertNotice";

        var coinType = $("#coinType").val();
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

      $("#noticeSearchConditionTable").change(
        function () {
          searchNotice();
        }
      );

      function searchNotice() {
        var url = "/currencyExchangeRateNotice/searchNotice";

        var reciverPK = $("#telegramChatPKOfSearch option:selected").val();
        var cryptoCoinType = $("#coinTypeOfSearch").val();
        var currencyCode = $("#currencyOfSearch option:selected").val();

        var noticeSearchResult = $("#noticeSearchResult");

        var jsonOutput = {
          reciverPK : reciverPK,
          cryptoCoinType : cryptoCoinType,
          currencyCode : currencyCode,
        };

        $.ajax({
          type : "POST",
          url : url,
          data: JSON.stringify(jsonOutput),
          // dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(datas){
            noticeSearchResult.html(datas);
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
