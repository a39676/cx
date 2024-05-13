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

  <p>24h</p>
  <table>
    <c:forEach items="${settingNameListFor24H}" var="settingName">
      <tr>
        <td style="background: yellow;">
          ${settingName}
        </td>
      </tr>
      <c:forEach items="${hour24DataMap[settingName]}" var="subData">
        <%-- <td>${subData}</td> --%>
        <tr>
          <td>${subData.symbol}_${subData.totalRate}%_${subData.totalCounter}</td>
          <td>${subData.totalRiseRate}%_${subData.riseCounter}</td>
          <td>${subData.totalFallRate}%_${subData.fallCounter}</td>
        </tr>
      </c:forEach>
    </c:forEach>
  </table>

  <p>recent</p>
  <table>
    <c:forEach items="${settingNameList}" var="settingName">
      <tr>
        <td style="background: yellow;">
          ${settingName}
        </td>
      </tr>
      <c:forEach items="${recentDataMap[settingName]}" var="subData">
        <%-- <td>${subData}</td> --%>
        <tr>
          <td>${subData.symbol}_${subData.rate}%_${subData.redirect}_${subData.newPrice}</td>
        </tr>
      </c:forEach>
    </c:forEach>
  </table>



</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript">
  $(document).ready(function() {


  });
</script>
