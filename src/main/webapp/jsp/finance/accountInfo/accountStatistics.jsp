<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<div class="row">
<div class="col-md-12">
  <%@ include file="../bank/bankSelectorV2.jsp" %>
</div>
</div>

<div class="row">
<div class="col-md-8" name="accountList">
  <jsp:include page="/accountInfo/accountListView" flush="true"/>
</div>

<div class="col-md-4">
  <c:forEach items="${accountSumByBankId}" var="bankSum">
  <div name="accountSumByBankId" bankId="${bankSum.bankId}">
    <div><label>${bankSum.bankChineseName}</label></div>
    <c:if test="${bankSum.sumCreditQuota != 0}">
      <div><label>总信用额度: ${bankSum.sumCreditQuota}</label></div>
    </c:if>
    <c:if test="${bankSum.maxCreditQuota != 0}">
      <div><label>最大额度: ${bankSum.maxCreditQuota}</label></div>
    </c:if>
    <c:if test="${bankSum.sumSpentCreditQuota < 0}">
      <div><label style="color:red">已用额度: ${0 - bankSum.sumSpentCreditQuota}</label></div>
    </c:if>
    <c:if test="${bankSum.sumOverDeposit != 0}">
      <div><label>溢存款: ${bankSum.sumOverDeposit}</label></div>
    </c:if>
    <c:if test="${bankSum.sumDeposit != 0}">
      <div><label>存款: ${bankSum.sumDeposit}</label></div>
    </c:if>
    <c:if test="${bankSum.subSum != 0}">
      <div><label <c:if test="${bankSum.subSum < 0}">style="color: red"</c:if>>合计: ${bankSum.subSum}</label></div>
    </c:if>
  </div>
  <hr style="color: blue">
  </c:forEach>
</div>
</div>


<script type="text/javascript">

  $(document).ready(function() {
      

    //getAccountListByBankId 
    function getAccountListByBankId(bankId){
      var $accountListDiv = $("div[name='accountListDiv']");

      var $jsonSend = {
        bankId : bankId
      };
      
      $.ajax({               
        type: "POST",  
        url: "/accountInfo/accountListView",   
        data: JSON.stringify($jsonSend),
        // dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){  
          $accountListDiv.html(data);
        }, 
        error:function(e){
        }
      });
    };

    $("div[name='bankList']").click(function () {
      var $bankId = $("div[name='hiddenMessage']").attr("bankId");
      getAccountListByBankId($bankId);
    });

  });
</script>