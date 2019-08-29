<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div>
  <div id="bankIdSelector">
    <%@ include file="../bankInfoJSP/bankSelectorV4.jsp" %>
  </div>
</div>

<!-- 2019-06-04 待开发
<div>
  <input type="text" name="fuzzyAccountAlias" placeholder="请输入账户别名">
</div>
 -->

<div>
  <div class="btn-group" name="accountTypeList">
    <button type="button" name="accountTypeButton" class="btn btn-sm btn-light" accountType="">所有</button>
    <label>&nbsp;|&nbsp;</label>
    <button type="button" name="accountTypeButton" class="btn btn-sm btn-light" accountType="1">借记卡</button>
    <label>&nbsp;|&nbsp;</label>
    <button type="button" name="accountTypeButton" class="btn btn-sm btn-light" accountType="2">贷记卡</button>
    <label>&nbsp;|&nbsp;</label>
    <button type="button" name="accountTypeButton" class="btn btn-sm btn-light" accountType="3">子账户占用信用额度</button>
    <label>&nbsp;|&nbsp;</label>
    <button type="button" name="accountTypeButton" class="btn btn-sm btn-light" accountType="4">子账户不占用信用额度</button>
  </div>
</div>

<div>
  <select name="accountNumbers" >
    <option value="" disabled selected>Select your account number</option>
    <c:forEach var="account" items="${accounts}">
      <option value="${account.accountNumber}" >${account.accountAlias} : ${account.accountNumber}</option>
    </c:forEach>
  </select>
</div>

<div name="accountHiddenMessage" accountType=""></div>


<script type="text/javascript">

$("button[name='accountTypeButton']").click(function () {
  var $accountType = $(this).attr("accountType");
  $("button[name='accountTypeButton']").removeClass("btn-primary");
  $(this).addClass("btn-primary");
  $("div[name='accountHiddenMessage']").attr("accountType", $accountType);
  accountListDynamic();
});

$("button[name='bankUnionButton']").click(function () {
  accountListDynamic();
});

$("div[name='bankButtonList']").click(function () {
  accountListDynamic();
});


//accountListDynamic select dynamic
function accountListDynamic(){
  var $accountNumberSelector = $("select[name='accountNumbers']");

  var $bankUnionId = $("div[name='bankHiddenMessage']").attr("bankUnionId");
  var $bankId = $("div[name='bankHiddenMessage']").attr("bankId");
  var $accountType = $("div[name='accountHiddenMessage']").attr("accountType");
  var $bankList = $("div[name='bankList']");

  var jsonOutput = {
    bankUnionId: $bankUnionId,
    bankId: $bankId,
    accountType : $accountType
  };
  $.ajax({               
    type: "POST",  
    url: "${pageContext.request.contextPath}/accountInfo/accountList",   
    data: JSON.stringify(jsonOutput),
    dataType: 'json',
    contentType: "application/json",
    beforeSend: function(xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    timeout: 15000,
    success:function(data){  
      $accountNumberSelector.empty();
      if(data.code == 0) {
        var accountList = data.accountList
        $.each(accountList, function(index, account) {
          $accountNumberSelector.append(
            $("<option></option>").attr("value", account.accountNumber).text(account.accountAlias + " : " + account.accountNumber)
          );
        });
      } else {
      }
    }, 
    error:function(e){
      // console.log(e);
    }
  });           
};

</script>