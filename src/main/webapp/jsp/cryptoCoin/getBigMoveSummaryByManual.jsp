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
  <div class="row">
    <div class="col-md-12" id="chart_div">
      
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      ${todayBigMoveMsg}
      <p>0-24h: Total:${dataIn24H.total}, Rising:${dataIn24H.risingCounting}, Falling:${dataIn24H.fallingCounting}</p>
    </div>
  </div>

  <div class="row">
    <div class="col-md-11">
      <div style="height: 200px; overflow: auto;">
        <table class="table table-striped table-sm">
          <tr>
            <td style="text-align: center;">Symbol</td>
            <td style="text-align: center;">Total Rate</td>
            <td style="text-align: center;">Total count</td>
            <td style="text-align: center;">Rising rate</td>
            <td style="text-align: center;">Rising count</td>
            <td style="text-align: center;">Falling rate</td>
            <td style="text-align: center;">Falling count</td>
          </tr>
          <c:forEach items="${dataIn24H.dataList}" var="subData">
            <c:if test = "${subData.totalRate > 0}">
              <tr class="table-success">
            </c:if>
            <c:if test = "${subData.totalRate == 0}">
              <tr class="table-light">
            </c:if>
            <c:if test = "${subData.totalRate < 0}">
              <tr class="table-danger">
            </c:if>
              <td style="text-align: center;">
                ${subData.symbol}
              </td>
              <td style="text-align: center;">
                ${subData.totalRate}%
              </td>
              <td style="text-align: center;">
                ${subData.totalCounter}
              </td>
              <td style="text-align: center;">
                ${subData.totalRiseRate}%
              </td>
              <td style="text-align: center;">
                ${subData.riseCounter}
              </td>
              <td style="text-align: center;">
                ${subData.totalFallRate}%
              </td>
              <td style="text-align: center;">
                ${subData.fallCounter}
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      ${yesterdayBigMoveMsg}
      <p>24-48h: Total:${dataIn48H.total}, Rising:${dataIn48H.risingCounting}, Falling:${dataIn48H.fallingCounting}</p>
    </div>
  </div>

  <div class="row">
    <div class="col-md-11">
      <div style="height: 200px; overflow: auto;">
        <table class="table table-striped table-sm">
          <tr>
            <td style="text-align: center;">Symbol</td>
            <td style="text-align: center;">Total Rate</td>
            <td style="text-align: center;">Total count</td>
            <td style="text-align: center;">Rising rate</td>
            <td style="text-align: center;">Rising count</td>
            <td style="text-align: center;">Falling rate</td>
            <td style="text-align: center;">Falling count</td>
          </tr>
          <c:forEach items="${dataIn48H.dataList}" var="subData">
            <c:if test = "${subData.totalRate > 0}">
              <tr class="table-success">
            </c:if>
            <c:if test = "${subData.totalRate == 0}">
              <tr class="table-light">
            </c:if>
            <c:if test = "${subData.totalRate < 0}">
              <tr class="table-danger">
            </c:if>
              <td style="text-align: center;">
                ${subData.symbol}
              </td>
              <td style="text-align: center;">
                ${subData.totalRate}%
              </td>
              <td style="text-align: center;">
                ${subData.totalCounter}
              </td>
              <td style="text-align: center;">
                ${subData.totalRiseRate}%
              </td>
              <td style="text-align: center;">
                ${subData.riseCounter}
              </td>
              <td style="text-align: center;">
                ${subData.totalFallRate}%
              </td>
              <td style="text-align: center;">
                ${subData.fallCounter}
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      ${lastWeekBigMoveMsg}
      <p>Last week: Total:${dataInLastWeek.total}, Rising:${dataInLastWeek.risingCounting}, Falling:${dataInLastWeek.fallingCounting}</p>
    </div>
  </div>

  <div class="row">
    <div class="col-md-11">
      <div style="height: 200px; overflow: auto;">
        <table class="table table-striped table-sm">
          <tr>
            <td style="text-align: center;">Symbol</td>
            <td style="text-align: center;">Total Rate</td>
            <td style="text-align: center;">Total count</td>
            <td style="text-align: center;">Rising rate</td>
            <td style="text-align: center;">Rising count</td>
            <td style="text-align: center;">Falling rate</td>
            <td style="text-align: center;">Falling count</td>
          </tr>
          <c:forEach items="${dataInLastWeek.dataList}" var="subData">
            <c:if test = "${subData.totalRate > 0}">
              <tr class="table-success">
            </c:if>
            <c:if test = "${subData.totalRate == 0}">
              <tr class="table-light">
            </c:if>
            <c:if test = "${subData.totalRate < 0}">
              <tr class="table-danger">
            </c:if>
              <td style="text-align: center;">
                ${subData.symbol}
              </td>
              <td style="text-align: center;">
                ${subData.totalRate}%
              </td>
              <td style="text-align: center;">
                ${subData.totalCounter}
              </td>
              <td style="text-align: center;">
                ${subData.totalRiseRate}%
              </td>
              <td style="text-align: center;">
                ${subData.riseCounter}
              </td>
              <td style="text-align: center;">
                ${subData.totalFallRate}%
              </td>
              <td style="text-align: center;">
                ${subData.fallCounter}
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </div>

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

  <div class="row">
    <div class="col-md-12">
      <label>hourRangeStart</label> 
      <input type="number" id="hourRangeStart" 
        placeholder="hourRangeStart" value="0">
      <label>hourRangeEnd</label> 
      <input type="number" id="hourRangeEnd" 
        placeholder="hourRangeEnd" value="24">
      <button id="QueryBigMoveData">Submit</button>
    </div>
  </div>

  <div class="row">
    <div class="col-md-11">
      <label id="customQueryResultMsg"></label>
    </div>
  </div>

  <div class="row">
    <div class="col-md-11" id="customQueryResult">
    </div>
  </div>

</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript" src="/static_resources/js/chartJS/gstatic/loader.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $("#QueryBigMoveData").click(function() {
      queryBigMoveData();
    });

    function queryBigMoveData(){
      var url = "/ct/bigMoveData";

      var hourRangeStart = $("#hourRangeStart").val();
      var hourRangeEnd = $("#hourRangeEnd").val();

      var jsonOutput = {
        hourRangeStart:hourRangeStart,
        hourRangeEnd:hourRangeEnd,
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
          var targetDiv = "";
          targetDiv += '<div style="height: 200px; overflow: auto;">';
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
          for(i=0;i<datas.dataList.length;i++){
            var subData = datas.dataList[i];
            if (subData.totalRate > 0) {
              targetDiv += '        <tr class="table-success">';
            } else if (subData.totalRate > 0){
              targetDiv += '        <tr class="table-light">';
            } else {
              targetDiv += '        <tr class="table-danger">';
            }
            targetDiv += '        <td style="text-align: center;">';
            targetDiv += subData.symbol;
            targetDiv += '        </td>';
            targetDiv += '        <td style="text-align: center;">';
            targetDiv += subData.totalRate;
            targetDiv += '        </td>';
            targetDiv += '        <td style="text-align: center;">';
            targetDiv += subData.totalCounter;
            targetDiv += '        </td>';
            targetDiv += '        <td style="text-align: center;">';
            targetDiv += subData.totalRiseRate;
            targetDiv += '        </td>';
            targetDiv += '        <td style="text-align: center;">';
            targetDiv += subData.riseCounter;
            targetDiv += '        </td>';
            targetDiv += '        <td style="text-align: center;">';
            targetDiv += subData.totalFallRate;
            targetDiv += '        </td>';
            targetDiv += '        <td style="text-align: center;">';
            targetDiv += subData.fallCounter;
            targetDiv += '        </td>';
            targetDiv += '      </tr>';
          }
          targetDiv += '  </table>';
          targetDiv += '</div>';
          $("#customQueryResult").html(targetDiv);
        },
        error: function(datas) {            
        }
      });
    }
  });
</script>

<script type="text/javascript">
  google.charts.load('current', {packages: ['corechart', 'line']});
  google.charts.setOnLoadCallback(drawAxisTickColors);

  function drawAxisTickColors() {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'Total');
    data.addColumn('number', 'Binance');
    data.addColumn('number', 'GateIO');

    data.addRows([
      <c:forEach items="${bigMoveDailySummaryData}" var="subData" varStatus="loop">
        [${loop.index}, ${subData.total}, ${subData.binanceCounting}, ${subData.gateIoCounting}],
      </c:forEach>
    ]);

    var options = {
      hAxis: {
        title: 'Title for X',
        textStyle: {
          color: '#01579b',
          fontSize: 20,
          fontName: 'Arial',
          bold: true,
          italic: true
        },
        titleTextStyle: {
          color: '#01579b',
          fontSize: 16,
          fontName: 'Arial',
          bold: false,
          italic: true
        }
      },
      vAxis: {
        title: 'Title for Y',
        textStyle: {
          color: '#1a237e',
          fontSize: 24,
          bold: true
        },
        titleTextStyle: {
          color: '#1a237e',
          fontSize: 24,
          bold: true
        }
      },
      colors: ['#1a237e', '#a52714', '#097138']
    };
    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
  }

</script>
