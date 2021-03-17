<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

  <form:form method="post" action="" modelAttribute="accountInfoResult"> 
    <div class="form-group">
      <form:label path="accountHolderName">accountHolderName</form:label>
      <form:input path="accountHolderName" />${accountInfoResult.holder.accountHolderName}
    </div>
    <div class="form-group">
      <form:label path="accountNumber">accountNumber</form:label>
      <form:input path="accountNumber" />${accountInfoResult.accountNumber}
    </div>
    <div class="form-group">
      <form:label path="accountType">accountType</form:label>
      <form:input path="accountType" />${accountInfoResult.accountType}
    </div>
    <div class="form-group">
      <label>Belong to: ${mainAccountNumber}</label>
    </div>
    <div class="form-group">
      <form:label path="vaildDate">
        vaildDate:
        <fmt:formatDate value="${accountInfoResult.vaildDate}" pattern="yyyy-MM-dd" />
      </form:label>
    </div>
    <div class="form-group">
      <form:label path="bankId">bankId</form:label>
      <form:input path="bankId" />${accountInfoResult.bankInfoCustom.bankEnameShort}
    </div>
    <div class="form-group">
      <form:label path="bankUnionId">bankUnionId</form:label>
      <form:input path="bankUnionId" />${accountInfoResult.bankInfoCustom.bankUnion.bankUnionEnameShort}
    </div>
    <div class="form-group">
      <form:label path="accountBalance">accountBalance</form:label>
      <form:input path="accountBalance" />${accountInfoResult.accountBalance}
    </div>
    <div class="form-group">
      <form:label path="creditsQuota">creditsQuota</form:label>
      <form:input path="creditsQuota" />${accountInfoResult.creditsQuota}
    </div>
    <div class="form-group">
      <form:label path="temproraryCreditsQuota">temproraryCreditsQuota</form:label>
      <form:input path="temproraryCreditsQuota" />${accountInfoResult.temproraryCreditsQuota}
    </div>
    <div class="form-group">
      <form:label path="temproraryCreditsVaildDate">
        temproraryCreditsVaildDate:
        <fmt:formatDate value="${accountInfoResult.temproraryCreditsVaildDate}" pattern="yyyy-MM-dd" />
      </form:label>
    </div>
    <div class="form-group">
      <form:label path="remark">remark</form:label>
      <form:input path="remark" />${accountInfoResult.remark}
    </div>
  </form:form>
  
  <div>
    <label>${emptyValue}</label>
  </div>



  <!-- <table class="table">
  	<thead>
  	<div class="form-group">
  		id
  		name
  		gender
  		birth
  </div>
  	</thead>

  	<body>
  		<c:forEach var="holder" items="${holderList}">
  		<div class="form-group">
  			${holder.accountHolderId}	
  			${holder.accountHolderName}	
  			${holder.gender}	
  			${holder.birth}	
  	</div>
  		</c:forEach>
  	</body>
  </table> -->
   
</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
</footer>
</html>