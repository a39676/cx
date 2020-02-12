<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="row">
<div class="col-md-6"> <%-- main info --%>
<form:form method="post" action="" modelAttribute="accountInfoResult"> 
  <div class="form-group">
    <form:label path="accountHolderName">accountHolderName</form:label>
    <form:input path="accountHolderName" />${holder.accountHolderName}
  </div>
  <div class="form-group">
    <form:label path="accountNumber">账户号: </form:label>
    <form:input path="accountNumber" />${accountInfoResult.accountNumber}
  </div>
  <div class="form-group">
    <form:label path="accountAlias">别名: </form:label>
    <form:input path="accountAlias" />${accountInfoResult.accountAlias}
  </div>
  <div class="form-group">
    <form:label path="accountType">账户类型: </form:label>
    <form:input path="accountType" />${accountInfoResult.accountType}
  </div>
  <div class="form-group">
    <label>归属于: ${mainAccountNumber}</label>
  </div>
  <div class="form-group">
    <form:label path="vaildDate">
      有效期至: 
      <fmt:formatDate value="${accountInfoResult.vaildDate}" pattern="yyyy-MM-dd" />
    </form:label>
  </div>
  <div class="form-group">
    <form:label path="bankId">bankId</form:label>
    <form:input path="bankId" />${bankFullInfo.bankEnameShort}
  </div>
  <div class="form-group">
    <form:label path="bankUnionId">bankUnionId</form:label>
    <form:input path="bankUnionId" />${bankFullInfo.bankUnion.bankUnionEnameShort}
  </div>
  <div class="form-group">
    <form:label path="accountBalance">账户余额: </form:label>
    <form:input path="accountBalance" />${accountInfoResult.accountBalance}
  </div>
  <div class="form-group">
    <form:label path="creditsQuota">信用额度</form:label>
    <form:input path="creditsQuota" />${accountInfoResult.creditsQuota}
  </div>
  <div class="form-group">
    <form:label path="temproraryCreditsQuota">临时信用额度: </form:label>
    <form:input path="temproraryCreditsQuota" />${accountInfoResult.temproraryCreditsQuota}
  </div>
  <div class="form-group">
    <form:label path="temproraryCreditsVaildDate">
      临时信用额度有效期:
      <fmt:formatDate value="${accountInfoResult.temproraryCreditsVaildDate}" pattern="yyyy-MM-dd" />
    </form:label>
  </div>
  <div class="form-group">
    <form:label path="remark">备注</form:label>
    <form:input path="remark" />${accountInfoResult.remark}
  </div>
</form:form>
  
<div>
  <label>${emptyValue}</label>
</div>
</div>  <%-- col-md-6 main info end --%>


</div>  <%-- row end --%>
