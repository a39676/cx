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
        <button id="oneHour">1 Hours</button>
        <button id="threeHours">3 Hours</button>
        <button id="twelveHours">12 Hours</button>
        <button id="today">1 Day</button>
        <button id="twoDays">2 Days</button>
        <button id="threedDay">3 Days</button>
        <button id="thisWeek">This week</button>
        <button id="twoWeeks">Two Weeks</button>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <label>HourRangeStart</label> 
        <input type="number" id="dataQueryHourRangeStart" style="width: 60px;" 
          placeholder="dataQueryHourRangeStart" value="24">
        <label>HourRangeEnd</label> 
        <input type="number" id="dataQueryHourRangeEnd" style="width: 60px;" 
          placeholder="dataQueryHourRangeEnd" value="0">
        <input type="text" id="symbol" style="width: 100px;" 
          placeholder="symbol" value="${preSetSymbol}">
        <button id="submitForLine">submitForLine</button>
        <button id="submitForBubble">submitForBubble</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="lineChartDiv"></div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="bubbleChartDiv"></div>
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
    $("#submitForLine").click(function () {
      queryLineData();
    });

    function queryLineData(){
      var url = "/cryptoCoinData${lineChartUrl}";

      var dataQueryHourRangeStart = $("#dataQueryHourRangeStart").val();
      var dataQueryHourRangeEnd = $("#dataQueryHourRangeEnd").val();
      var symbol = $("#symbol").val();

      var jsonOutput = {
        start:dataQueryHourRangeStart,
        end:dataQueryHourRangeEnd,
        symbol:symbol,
      };

      $("#lineChartDiv").html("");
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
          $("#lineChartDiv").html(datas);
        },
        error: function(datas) {            
        }
      });
    }

    $("#submitForBubble").click(function () {
      queryBubbleData();
    });

    function queryBubbleData(){
      var url = "/cryptoCoinData${bubbleChartUrl}";

      var dataQueryHourRangeStart = $("#dataQueryHourRangeStart").val();
      var dataQueryHourRangeEnd = $("#dataQueryHourRangeEnd").val();
      var symbol = $("#symbol").val();

      var jsonOutput = {
        start:dataQueryHourRangeStart,
        end:dataQueryHourRangeEnd,
        symbol:symbol,
      };

      $("#bubbleChartDiv").html("");
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
          $("#bubbleChartDiv").html(datas);
        },
        error: function(datas) {            
        }
      });
    }

  });
</script>

<script type="text/javascript">
  $(document).ready(function() {
    $("#oneHour").click(function () {
      setHourRange(1, 0);
    });
    $("#threeHours").click(function () {
      setHourRange(3, 0);
    });
    $("#twelveHours").click(function () {
      setHourRange(12, 0);
    });
    $("#today").click(function () {
      setHourRange(24, 0);
    });
    $("#twoDays").click(function () {
      setHourRange(48, 0);
    });
    $("#threedDay").click(function () {
      setHourRange(72, 0);
    });
    $("#thisWeek").click(function () {
      setHourRange(168, 0);
    });
    $("#twoWeeks").click(function () {
      setHourRange(336, 0);
    });
    
    function setHourRange(start, end) {
      $("#dataQueryHourRangeStart").val(start);
      $("#dataQueryHourRangeEnd").val(end);
    }
  });
</script>
