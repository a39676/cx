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
    <div class="col-md-4">
      ${todayBigMoveMsg}
      <p>24h</p>
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

    <div class="col-md-4">
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

    <div class="col-md-4">
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
<script type="text/javascript">
  $(document).ready(function() {


  });
</script>
