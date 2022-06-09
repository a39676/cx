<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>

<body>

  <div style="display:none;" name="hiddenMessage" bankId="" bankUnionId="" accountId="" ></div>

  <div class="">
    <a href="/">Home</a><br>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
      Welcome! ${pageContext.request.userPrincipal.name}
      <a href="/login/logout"/>Logout</a><br>
    </c:if>
    <a href="/accountInfo/accountStatistics" target="_blank">All cards</a><br>
    <a href="/accountInfo/insertNewTransationV4" target="_blank">Add new trading record V4</a><br>
    <a href="/accountInfo/transationHistory" target="_blank">Tmp tarding record query</a><br>
    <a href="/trading/importTradingRecordFromFiles" target="_blank">Import tarding from document</a><br>
    <a href="/accountInfo/transationHistoryQuery" target="_blank">Trading record query</a><br>
    <a href="/accountInfo/accountRegist" target="_blank">Add new card</a><br>
    <br>
    <p>Testing</p>
    <a href="/accountInfo/insertNewTransationV4" target="_blank">Add new trading record V4</a><br>
    <a href="/bankInfo/bankSelectorV4" target="_blank">Bank selector V4</a><br>
    <a href="/accountInfo/accountSelectorV1" target="_blank">Account selector V4</a><br>
  </div>
</body>

<footer>
</footer>

<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">
  $(document).ready(function() {
  });
</script>
</html>
