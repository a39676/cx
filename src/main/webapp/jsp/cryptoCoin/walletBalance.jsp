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

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12">
        ${msg}
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <table class="table table-striped table-bordered table-hover">
          <thead>
            <tr>
              <td>walletName</td>
              <td>activate</td>
              <td>
                Balance(BTC)<br>
                Balance(USDT)
              </td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${detailList}" var="subData" varStatus="loop">
              <tr>
                <td>${subData.walletName}</td>
                <td>${subData.activate}</td>
                <td>
                  ${subData.balance}<br>
                  ${subData.balanceInUSDT}
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>

</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>

<script type="text/javascript">
  

</script>
