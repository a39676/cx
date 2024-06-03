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
    <div class="row">
      <div class="col-md-1">
      </div>
      <div class="col-md-10">
        <canvas id="countingChart" style="width:100%;"></canvas>
      </div>
      <div class="col-md-1">
      </div>
    </div>
    <div class="row">
      <div class="col-md-1">
      </div>
      <div class="col-md-10">
        <canvas id="countingChart2" style="width:100%;"></canvas>
      </div>
      <div class="col-md-1">
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      ${crossList}
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

  <div>
    <div class="row">
      <div class="col-md-12">
        ${recentBigMoveMsg}
        <p>recent</p>
        <table>
          <c:forEach items="${recentTimeKeyList}" var="settingName">
            <tr>
              <td style="background: yellow;">
                ${settingName}
              </td>
            </tr>
            <c:forEach items="${recentDataMap[settingName]}" var="subData">
              <%-- <td>${subData}</td> --%>
              <tr>
                <td>${subData.symbol}_${subData.rate}%_${subData.redirect}</td>
              </tr>
            </c:forEach>
          </c:forEach>
        </table>
      </div>
    </div>
  </div>

  <div>
    <div class="row">
      <div class="col-md-12">
        <button id="twelveHours">12 Hours</button>
        <button id="today">24 Hours</button>
        <button id="thirdDay">48-72 Hours</button>
        <button id="threeToSevenDays">3-7 Days</button>
        <button id="thisWeek">This week</button>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-12">
        <label>hourRangeStart</label> 
        <input type="number" id="hourRangeStart" style="width: 60px;" 
          placeholder="hourRangeStart" value="0">
        <label>hourRangeEnd</label> 
        <input type="number" id="hourRangeEnd" style="width: 60px;" 
          placeholder="hourRangeEnd" value="24">
        <input type="text" id="symbols" style="width: 120px;" 
          placeholder="BTCUSDT, ETHUSDT">
        <button id="QueryBigMoveData">Submit</button>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-11">
        <label id="customQueryResultMsg"></label>
      </div>
    </div>
  
    <div class="row">
      <div class="col-md-11">
        <div id="customQueryResult">
          
        </div>
      </div>
    </div>
  </div>


</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript" src="/static_resources/js/chartJS/chartJS/chartJs_v_2_9_4.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $("#QueryBigMoveData").click(function() {
      queryBigMoveData();
    });

    function queryBigMoveData(){
      var url = "/ct/bigMoveData";

      var hourRangeStart = $("#hourRangeStart").val();
      var hourRangeEnd = $("#hourRangeEnd").val();
      var symbols = $("#symbols").val();
      

      var jsonOutput = {
        hourRangeStart:hourRangeStart,
        hourRangeEnd:hourRangeEnd,
        symbols:symbols,
      };
      $("#customQueryResultMsg").text("Querying");
      $("#customQueryResult").html("");
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
          $("#customQueryResultMsg").text(datas.message + " Total:" + datas.total + ", Rising: " +datas.risingCounting + ", Falling: "+ datas.fallingCounting);
          if(datas.code != 0){
            return;
          }
          var targetDiv = buildBigMoveDataTable(datas);
          $("#customQueryResult").html(targetDiv);
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
    $("#twelveHours").click(function () {
      setHourRange(0, 12);
    });
    $("#today").click(function () {
      setHourRange(0, 24);
    });
    $("#thirdDay").click(function () {
      setHourRange(48, 72);
    });
    $("#threeToSevenDays").click(function () {
      setHourRange(72, 168);
    });
    $("#thisWeek").click(function () {
      setHourRange(0, 168);
    });
    
    
    function setHourRange(start, end) {
      $("#hourRangeStart").val(start);
      $("#hourRangeEnd").val(end);
    }
  });
</script>

<script type="text/javascript">
  const xValues = [];
  <c:forEach items="${xValues}" var="subData" varStatus="loop">
    xValues.push("${subData}");
  </c:forEach>
  
  new Chart("countingChart", {
    type: "line",
    data: {
      labels: xValues,
      datasets: [{
        data: ${total},
        borderColor: "#fad7a0",
        fill: false,
        label: "total"
      },{
        data: ${mainCounting},
        borderColor: "#f9e79f",
        fill: false,
        label: "mainCounting"
      },{
        data: ${otherCounting},
        borderColor: "#d2b4de",
        fill: false,
        label: "otherCounting"
      }]
    },
    options: {
      legend: {display: true}
    }
  });

  new Chart("countingChart2", {
    type: "line",
    data: {
      labels: xValues,
      datasets: [{
        data: ${mainSummaryCounting},
        borderColor: "#d4ac0d",
        fill: false,
        label: "mainSummaryCounting"
      },{
        data: ${mainRisingCounting},
        borderColor: "#28b463",
        fill: false,
        label: "mainRisingCounting"
      },{
        data: ${mainFallingCounting},
        borderColor: "#cb4335",
        fill: false,
        label: "mainFallingCounting"
      },{
        data: ${otherSummaryCounting},
        borderColor: "#f7dc6f",
        fill: false,
        label: "otherSummaryCounting"
      },{
        data: ${otherRisingCounting},
        borderColor: "#82e0aa",
        fill: false,
        label: "otherRisingCounting"
      },{
        data: ${otherFallingCounting},
        borderColor: "#f1948a",
        fill: false,
        label: "otherFallingCounting"
      }]
    },
    options: {
      legend: {display: true}
    }
  });

</script>
