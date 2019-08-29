<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="row">
<div class="col-md-12" name="accountListDiv">
  
  <c:forEach items="${accountList}" var="account">
    <div><label>${account.accountAlias}</label></div>
    <div><label name="accountNumber" accountNumber="${account.accountNumber}">
      ${account.bankEnameShort} : ${account.accountNumber}
    </label></div>
    <fmt:formatDate  value="${account.vaildDate}" var="vaildDate" pattern="yyyy-MM-dd" type="date" />
    <div><label>有效日期至: ${vaildDate}</label></div>
    <c:choose>
  
      <c:when test="${account.accountType == 1}">
        <div><label>余额: ${account.accountBalance}</label></div>
      </c:when>
  
      <c:when test="${account.accountType == 2 || account.accountType == 3 || account.accountType == 4}">
        
        <c:choose>
          <c:when test="${account.temproraryCreditsQuota <= 0}">
            <div>
              <label>
              余额/固定额度: 
              <c:if test="${account.accountBalance < 0}"><label style="color: red;">${account.accountBalance}</label></c:if>
              <c:if test="${account.accountBalance >= 0}"><label style="color: green;">${account.accountBalance}</label></c:if>
              /${account.creditsQuota} 
              (可用额度: ${account.avaliableQuota})
              </label>
            </div>
          </c:when>
          <c:otherwise>
            <div>
              <label>
              余额/固定额度/临时额度: 
              <c:if test="${account.accountBalance < 0}"><label style="color: red;">${account.accountBalance}</label></c:if>
              <c:if test="${account.accountBalance >= 0}"><label style="color: green;">${account.accountBalance}</label></c:if>/${account.creditsQuota}/${account.temproraryCreditsQuota} 
              (可用额度: ${account.avaliableQuota})
              </label>
            </div>
          </c:otherwise>
        </c:choose>

        <c:forEach items="${billInfoList}" var="billInfo">
          <c:choose>
            <c:when test="${billInfo.accountId == account.accountId}">
              <div>
                <label>上次账单日${billInfo.lastBillDate}</label>
                <label>本次还款日${billInfo.thisRefundDate}(还有${billInfo.daysToThisRefundDate}天)</label><br>
                <label>下次账单日${billInfo.nextBillDate}(还有${billInfo.daysToNextBillDate}天)</label>
                <label>下次还款日${billInfo.nextRefundDate}(还有${billInfo.daysToNextRefundDate}天)</label><br>
              </div>
            </c:when>
            <c:otherwise>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </c:when>
  
      <c:otherwise>
        
      </c:otherwise>
  
    </c:choose>
    <hr>
  </c:forEach>
</div>
 


</div>

<script type="text/javascript">

  $(document).ready(function() {
      
    $("label[name='accountNumber']").click(function(){
      var $accountNumber = {
        accountNumber:$(this).attr("accountNumber")
      };
      // console.log($accountNumber);
      

      $.ajax({               
        type: "POST", 
        async : true,
        url: "/accountInfo/accountDetail",   
        data: JSON.stringify($accountNumber),
        // dataType: 'json',
        contentType: "application/json",
        timeout: 15000,
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(data){ 
          $("#subBody").html(data); 
          
        }, 
        error:function(e){
          // console.log("error");
          // console.log(e);
        }
      });
    });

  });
</script>