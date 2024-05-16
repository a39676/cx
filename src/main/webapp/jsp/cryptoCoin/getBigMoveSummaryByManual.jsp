<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


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
    <div class="col-md-6">
      ${todayBigMoveMsg}
      <p>0-24h</p>
      <table>
        <c:forEach items="${bigMoveIn24H}" var="subData">
          <tr>
            <td>
              ${subData.symbol}_${subData.totalRate}%_${subData.totalCounter}
              <c:if test = "${subData.totalRate > 0}">
                🟢
              </c:if>
              <c:if test = "${subData.totalRate < 0}">
                🔴
              </c:if>
            </td>
            <td>${subData.totalRiseRate}%_${subData.riseCounter}</td>
            <td>${subData.totalFallRate}%_${subData.fallCounter}</td>
          </tr>
        </c:forEach>
      </table>
    </div>

    <div class="col-md-6">
      ${yesterdayBigMoveMsg}
      <p>24-48h</p>
      <table>
        <c:forEach items="${bigMoveIn48H}" var="subData">
          <tr>
            <td>
              ${subData.symbol}_${subData.totalRate}%_${subData.totalCounter}
              <c:if test = "${subData.totalRate > 0}">
                🟢
              </c:if>
              <c:if test = "${subData.totalRate < 0}">
                🔴
              </c:if>
            </td>
            <td>${subData.totalRiseRate}%_${subData.riseCounter}</td>
            <td>${subData.totalFallRate}%_${subData.fallCounter}</td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>

  <div class="row">
    <div class="col-md-5">
      ${lastWeekBigMoveMsg}
      <p>last week</p>
      <table>
        <c:forEach items="${bigMoveInLastWeek}" var="subData">
          <tr>
            <td>
              ${subData.symbol}_${subData.totalRate}%_${subData.totalCounter}
              <c:if test = "${subData.totalRate > 0}">
                🟢
              </c:if>
              <c:if test = "${subData.totalRate < 0}">
                🔴
              </c:if>
            </td>
            <td>${subData.totalRiseRate}%_${subData.riseCounter}</td>
            <td>${subData.totalFallRate}%_${subData.fallCounter}</td>
          </tr>
        </c:forEach>
      </table>
    </div>

    <div class="col-md-2">
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

</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript" src="/static_resources/js/chartJS/gstatic/loader.js"></script>
<script type="text/javascript">
  $(document).ready(function() {


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
