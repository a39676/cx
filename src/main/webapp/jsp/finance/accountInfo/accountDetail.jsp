<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="row">
<div class="col-md-6"> <%-- main info --%>
<div><label>${account.accountAlias}</label></div>
<div>
  <label name="accountNumber" accountNumber="${account.accountNumber}">
    ${account.bankEnameShort} : ${account.accountNumber}
  </label>
</div>
<div><label>${account.vaildDate}</label></div>
<c:choose>  
  <c:when test="${account.accountType == 1}">
    <div><label>余额: ${account.accountBalance}</label></div>
  </c:when>  
  <c:when test="${account.accountType == 2}">        
    <c:choose>
      <c:when test="${account.temproraryCreditsQuota <= 0}">
        <div><label>
          ${account.accountBalance}/${account.creditsQuota} (可用额度: ${account.creditsQuota + account.accountBalance})
        </label></div>
        <c:if test="${account.accountBalance} < 0">
          <div><label style="color: red">负债: ${account.accountBalance}</label></div>
        </c:if>
      </c:when>
      <c:otherwise>
        <div><label>
          ${account.accountBalance}/${account.creditsQuota} + ${account.temproraryCreditsQuota} 
          (可用额度: ${account.creditsQuota + account.accountBalance + account.temproraryCreditsQuota})
        </label></div>
      </c:otherwise>
    </c:choose>

    <div>
      <label name="modifyCreditsQuota">点击调整额度</label>
      <div name="modifyCreditsQuota" style="display: none">
        <input type="text" class="" placeholder="请填入新的额度"
          name="modifyCreditsQuota" />
        <span name="submitNewCreditsQuota">确认调整额度</span>
        <label name="modifyCreditsQuotaResult"></label>
      </div>
    </div>
  </c:when>  
  <c:otherwise>        
  </c:otherwise>  
</c:choose>

<div>
  <label name="modifyVaildDate">调整有效期</label>
  <div name="modifyVaildDate" style="display: none">
    <input type="text" class="" placeholder="请选择新的有效期" 
        name="modifyVaildDate" data-date-format="yyyy-mm-dd"/>
    <span name="submitNewVaildDate">确认调整有效期</span>
    <label name="modifyVaildDateResult"></label>
  </div>
</div>


</div>  <%-- col-md-6 main info end --%>

<div class="col-md-6">  <%-- affiliation start --%>

  <div>
    <label>附属信息汇总</label>
    <label style="color: red">所有附属负债: ${affiliationSum.balance}</label>
  </div>

  <div>
  <c:forEach items="${affiliationList}" var="affiliationAccount">

    <div><label>别名: ${affiliationAccount.accountAlias}</label></div>
    <div><label>余额: ${affiliationAccount.accountBalance}</label></div>
    <div><label>期限至: ${affiliationAccount.vaildDate}</label></div>
    <div><label>创建自: ${affiliationAccount.createTime}</label></div>
    <div><label>备注: ${affiliationAccount.remark}</label></div>
    <hr>
  </c:forEach>
  </div>
</div>  <%-- col-md-6 affiliation end --%>

</div>  <%-- row end --%>

<script type="text/javascript">

  $(document).ready(function() {

  $("label[name='modifyVaildDate']").click(function () {
    $("div[name='modifyVaildDate']").show();
  });

  $("label[name='modifyCreditsQuota']").click(function () {
    $("div[name='modifyCreditsQuota']").show();
  });


  function modifyVaildDate() {
    var $accountNumber = $("label[name='accountNumber']").attr("accountNumber");
    var $newVaildDate = $("input[name='modifyVaildDate']").val();

    var jsonOutput = {
      accountNumber : $accountNumber,
      newVaildDate: $newVaildDate
    };

    $.ajax({               
      type: "POST",  
      url: "/accountInfo/modifyVaildDate",   
      data: JSON.stringify(jsonOutput),
      dataType: 'json',
      contentType: "application/json",
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      timeout: 15000,
      success:function(data){  
        var $message = data.message;
        if(data.code == 0) {
          $("label[name='modifyVaildDateResult']").text($message);
        } else {
          $("label[name='modifyVaildDateResult']").text($message);
        }
      }, 
      error:function(e){
      }
    });

  };

  $("span[name='submitNewVaildDate']").click( function() {
    modifyVaildDate();
  });


  function modifyCreditsQuota() {
    var $accountNumber = $("label[name='accountNumber']").attr("accountNumber");
    var $newCreditsQuota = $("input[name='modifyCreditsQuota']").val();

    var jsonOutput = {
      accountNumber : $accountNumber,
      newCreditsQuota: $newCreditsQuota
    };

    $.ajax({               
      type: "POST",  
      url: "/accountInfo/modifyCreditsQuota",   
      data: JSON.stringify(jsonOutput),
      dataType: 'json',
      contentType: "application/json",
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      timeout: 15000,
      success:function(data){  
        var $message = data.message;
        if(data.code == 0) {
          $("label[name='modifyCreditsQuotaResult']").text($message);
        } else {
          $("label[name='modifyCreditsQuotaResult']").text($message);
        }
      }, 
      error:function(e){
      }
    });

  };

  $("span[name='submitNewCreditsQuota']").click( function() {
    modifyCreditsQuota();
  });

  });

</script>