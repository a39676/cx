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

  ${msg}
  <div>
    <div id="chartDiv">
      <%@ include file="./getBigMoveChart.jsp"%>
    </div>
  </div>

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
        <input type="number" id="bigMoveDataQueryHourRangeStart" style="width: 60px;" 
          placeholder="bigMoveDataQueryHourRangeStart" value="168">
        <label>HourRangeEnd</label> 
        <input type="number" id="bigMoveDataQueryHourRangeEnd" style="width: 60px;" 
          placeholder="bigMoveDataQueryHourRangeEnd" value="0">
        <input type="text" id="symbols" style="width: 120px;" 
          placeholder="BTCUSDT, ETHUSDT">
        <select id="bigMoveDataVersion">
          <option value="1">1</option>
          <option value="2">2</option>
        </select>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="updateMainChart">updateMainChart</button>
        <button id="QueryBigMoveDataTable">BigMoveDataTableQuery</button>
        <button id="QueryBigMoveDataChart">BigMoveDataChartQuery</button>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-11">
        <label id="bigMoveDataTableQueryResultMsg"></label>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-11">
        <div id="bigMoveDataTableQueryResult">
          
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-11">
        <label id="bigMoveDataChartResultMsg"></label>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-11">
        <div id="bigMoveDataChartQueryResult">
          
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <label> rising: ${risingCrossList} </label><br>
      <label> falling: ${fallingCrossList} </label>
    </div>
  </div>

  <div>
    <div class="row">
      <div class="col-md-12">
        ${todayBigMoveMsg}
      <p>0-24h: Total:${dataIn24H.total}, Rising:${dataIn24H.risingCounting}, Falling:${dataIn24H.  fallingCounting}</p>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-11">
        <div id="dataIn24H" style="height: 200px; overflow: auto;">
        </div>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-12">
        ${yesterdayBigMoveMsg}
      <p>24-48h: Total:${dataIn48H.total}, Rising:${dataIn48H.risingCounting}, Falling:${dataIn48H. fallingCounting}</p>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-11">
        <div id="dataIn48H" style="height: 200px; overflow: auto;">
        </div>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-12">
        ${lastWeekBigMoveMsg}
      <p>Last week: Total:${dataInLastWeek.total}, Rising:${dataInLastWeek.risingCounting}, Falling:  ${dataInLastWeek.fallingCounting}</p>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-11">
        <div id="dataInLastWeek" style="height: 200px; overflow: auto;">
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
    $("#QueryBigMoveDataTable").click(function() {
      queryBigMoveData();
    });

    function queryBigMoveData(){
      var url = "/ct/bigMoveDataTable";

      var bigMoveDataQueryHourRangeStart = $("#bigMoveDataQueryHourRangeStart").val();
      var bigMoveDataQueryHourRangeEnd = $("#bigMoveDataQueryHourRangeEnd").val();
      var version = $('#bigMoveDataVersion').find(":selected").val();
      var symbols = $("#symbols").val();
      

      var jsonOutput = {
        hourRangeStart:bigMoveDataQueryHourRangeStart,
        hourRangeEnd:bigMoveDataQueryHourRangeEnd,
        symbols:symbols,
        version:version,
      };
      $("#bigMoveDataTableQueryResultMsg").text("Querying");
      $("#bigMoveDataTableQueryResult").html("");
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
          $("#bigMoveDataTableQueryResultMsg").text(datas.message + " Total:" + datas.total + ", Rising: " +datas.risingCounting + ", Falling: "+ datas.fallingCounting);
          if(datas.code != 0){
            return;
          }
          var targetDiv = buildBigMoveDataTable(datas);
          $("#bigMoveDataTableQueryResult").html(targetDiv);
        },
        error: function(datas) {            
        }
      });
    }

    function buildBigMoveDataTable(argument){
      if(argument.code != 0){
        return;
      }
      var targetDiv = "";
      targetDiv += '  <table class="table table-striped table-sm">';
      targetDiv += '    <tr>';
      targetDiv += '      <td style="text-align: center;">Symbol</td>';
      targetDiv += '      <td style="text-align: center;">Total Rate</td>';
      targetDiv += '      <td style="text-align: center;">Total count</td>';
      targetDiv += '      <td style="text-align: center;">Rising rate</td>';
      targetDiv += '      <td style="text-align: center;">Rising count</td>';
      targetDiv += '      <td style="text-align: center;">Falling rate</td>';
      targetDiv += '      <td style="text-align: center;">Falling count</td>';
      targetDiv += '    </tr>';
      for(i=0;i<argument.dataList.length;i++){
        var subData = argument.dataList[i];
        targetDiv += '        <tr>';
        if (subData.totalRate > 0) {
          targetDiv += '        <td style="text-align: center;" class="table-success">';
        } else if (subData.totalRate < 0){
          targetDiv += '        <td style="text-align: center;" class="table-danger">';
        } else {
          targetDiv += '        <td style="text-align: center;" class="table-light">';
        }
        targetDiv += subData.symbol;
        targetDiv += '        </td>';
        targetDiv += '        <td style="text-align: center;">';
        targetDiv += subData.totalRate;
        targetDiv += '        </td>';
        targetDiv += '        <td style="text-align: center;">';
        targetDiv += subData.totalCounter;
        targetDiv += '        </td>';
        targetDiv += '        <td style="text-align: center;" class="table-success">';
        targetDiv += subData.totalRiseRate;
        targetDiv += '        </td>';
        targetDiv += '        <td style="text-align: center;">';
        targetDiv += subData.riseCounter;
        targetDiv += '        </td>';
        targetDiv += '        <td style="text-align: center;" class="table-danger">';
        targetDiv += subData.totalFallRate;
        targetDiv += '        </td>';
        targetDiv += '        <td style="text-align: center;">';
        targetDiv += subData.fallCounter;
        targetDiv += '        </td>';
        targetDiv += '      </tr>';
      }
      targetDiv += '  </table>';
      return targetDiv;
    }

    function fillDataIn24H() {
      var targetDiv = buildBigMoveDataTable(${dataIn24H});
      $("#dataIn24H").html(targetDiv);
    }
    fillDataIn24H();

    function fillDataIn48H() {
      var targetDiv = buildBigMoveDataTable(${dataIn48H});
      $("#dataIn48H").html(targetDiv);
    }
    fillDataIn48H();

    function fillDataInLastWeek() {
      var targetDiv = buildBigMoveDataTable(${dataInLastWeek});
      $("#dataInLastWeek").html(targetDiv);
    }
    fillDataInLastWeek();
  });
</script>

<script type="text/javascript">
  $(document).ready(function() {
    $("#QueryBigMoveDataChart").click(function() {
      queryBigMoveDataChart();
    });

    function queryBigMoveDataChart(){
      var url = "/ct/bigMoveChartBySymbol";

      var bigMoveDataQueryHourRangeStart = $("#bigMoveDataQueryHourRangeStart").val();
      var bigMoveDataQueryHourRangeEnd = $("#bigMoveDataQueryHourRangeEnd").val();
      var version = $('#bigMoveDataVersion').find(":selected").val();
      var symbols = $("#symbols").val();

      var jsonOutput = {
        hourRangeStart:bigMoveDataQueryHourRangeStart,
        hourRangeEnd:bigMoveDataQueryHourRangeEnd,
        symbols:symbols,
        version:version,
      };


      $("#bigMoveDataChartResultMsg").text("Querying");
      $("#bigMoveDataChartQueryResult").html("");
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
          $("#bigMoveDataChartQueryResult").html(datas);
          $("#bigMoveDataChartResultMsg").text("");
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
      $("#bigMoveDataQueryHourRangeStart").val(start);
      $("#bigMoveDataQueryHourRangeEnd").val(end);
    }
  });
</script>

<script type="text/javascript">
  $(document).ready(function() {
    $("#updateMainChart").click(function () {
      updateMainChart();
    });

    function updateMainChart(){
      var url = "/ct/bigMoveChart";

      var bigMoveDataQueryHourRangeStart = $("#bigMoveDataQueryHourRangeStart").val();
      var bigMoveDataQueryHourRangeEnd = $("#bigMoveDataQueryHourRangeEnd").val();
      var version = $('#bigMoveDataVersion').find(":selected").val();

      var jsonOutput = {
        hourRangeStart:bigMoveDataQueryHourRangeStart,
        hourRangeEnd:bigMoveDataQueryHourRangeEnd,
        version:version,
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

</script>
