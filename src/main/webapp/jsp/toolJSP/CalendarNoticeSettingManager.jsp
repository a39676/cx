<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">

<div class="row">
  <input type="text" name="" id="newNoticeContent" placeholder="新设提醒内容">
</div>

<div class="row">
  <span>Notice time</span> <input type="Date" id="noticeTime" placeholder="提醒时间">
  <input type="Time" id="noticeStartTime" value="00:00:00" step="1">
</div>

<div class="row">
  <input type="checkbox" id="isLunarNotice" name="isLunarNotice">
  <label for="isLunarNotice">农历日期提醒(如需要重复提醒, 则按输入日期所对应的农历日期进行重复, 只能精确到日)</label><br>
</div>

<div class="row" id="repeatSettingRow">
  <input type="text" id="repeatTimeRange" placeholder="重复提醒间隔">
  <select id="repeatTimeUnit">
    <option value="">无需重复提醒(None)</option>
    <c:forEach items="${timeUnitTypeList}" var="timeUnitType">
      <option value="${timeUnitType.code}">${timeUnitType.cnName}${timeUnitType.name}</option>
    </c:forEach>
  </select>
</div>

<div class="row">
  <span>validTime(endTime)</span> <input type="Date" id="validDate" placeholder="过期时间">
  <input type="Time" id="validTime" value="00:00:00" step="1">
</div>

<div class="row" id="preNoticeSettingRow">
  <input type="text" id="preNoticeRepeatTimeRange" placeholder="提前提醒间隔">
  <select id="preNoticeRepeatTimeUnit">
    <option value="">无需提前提醒(None)</option>
    <c:forEach items="${timeUnitTypeList}" var="timeUnitType">
      <option value="${timeUnitType.code}">${timeUnitType.cnName}${timeUnitType.name}</option>
    </c:forEach>
  </select>
  <input type="text" id="preNoticeCount" placeholder="提前提醒次数">
</div>

<hr>

<button id="insert">insert</button>

<hr>

<p>modify result: </p>
<p id="result" updatingFlag="0"></p>

<div id="noticeSearchResult">
</div>

</div>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#insert").click(function () {
        addNotice();
      });

      searchNotice();

      function addNotice() {

        var url = "/tool/canlendarNotice/addNotice";

        var noticeContent = $("#newNoticeContent").val();
        var noticeTime;
        var lunarNoticeTime;
        if($("#isLunarNotice").is(":checked")){
          lunarNoticeTime = $("#noticeTime").val() + " " + $("#noticeStartTime").val();
        } else{
          noticeTime = $("#noticeTime").val() + " " + $("#noticeStartTime").val();
        }
        var validTime = $("#validDate").val() + " " + $("#validTime").val();
        var isLunarNotice = $("#isLunarNotice").is(":checked");
        var repeatTimeUnit = $("#repeatTimeUnit option:selected").val();
        var repeatTimeRange = $("#repeatTimeRange").val();
        var preNoticeRepeatTimeUnit = $("#preNoticeRepeatTimeUnit option:selected").val();
        var preNoticeRepeatTimeRange = $("#preNoticeRepeatTimeRange").val();
        var preNoticeCount = $("#preNoticeCount").val();

        var jsonOutput = {
          noticeContent : noticeContent,
          noticeTime : noticeTime,
          lunarNoticeTime : lunarNoticeTime,
          validTime : validTime,
          isLunarNotice : isLunarNotice,
          repeatTimeUnit : repeatTimeUnit,
          repeatTimeRange : repeatTimeRange,
          preNoticeRepeatTimeUnit : preNoticeRepeatTimeUnit,
          preNoticeRepeatTimeRange : preNoticeRepeatTimeRange,
          preNoticeCount : preNoticeCount,
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

      function searchNotice() {
        var url = "/tool/canlendarNotice/searchNotice";

        var noticeSearchResult = $("#noticeSearchResult");

        var jsonOutput = {
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
