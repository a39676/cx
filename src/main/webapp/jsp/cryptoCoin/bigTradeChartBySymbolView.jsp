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
          placeholder="symbol">
        <button id="submit">submit</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="chartDiv"></div>
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
    $("#submit").click(function () {
      queryData();
    });

    function queryData(){
      var url = "/cryptoCoinData${targetUrl}";

      var dataQueryHourRangeStart = $("#dataQueryHourRangeStart").val();
      var dataQueryHourRangeEnd = $("#dataQueryHourRangeEnd").val();
      var symbol = $("#symbol").val();

      var jsonOutput = {
        start:dataQueryHourRangeStart,
        end:dataQueryHourRangeEnd,
        symbol:symbol,
      };

      $("#chartDiv").html("");
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
          $("#chartDiv").html(datas);
        },
        error: function(datas) {            
        }
      });
    }
  });

</script>

<script type="text/javascript">
  $(document).ready(function() {
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
