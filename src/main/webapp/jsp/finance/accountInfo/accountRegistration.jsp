<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <%@ include file="../../../baseElementJSP/normalHeader.jsp" %>
  <title>${title}</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta charset="UTF-8">

</head>
<body>

  <form class="col-md-3" name="headForm">
    <div class="form-group">
    </div>
    <div class="form-group">
        <label>${emptyValue}</label>
    </div>
  </form>

  

  <div><span id="message"></span></div>
  <div id="accountRegistrationForm" class="col-md-3"> 
    <div class="form-group">
      <label name="accountNumber">账户号: </label>
      <input id="accountNumber" type="text" placeholder="请输入帐号" class=""/><br>
      <span id="accountNumberP" class=""></span>
    </div>

    <div class="form-group">
      <label name="accountAlias">账户别名: </label>
      <input id="accountAlias" type="text" placeholder="请输入帐号别名" class=""/>
    </div>
    
    <div class="form-group">
      <label name="accountType">帐户类型: </label><br>
      <select id="accountType" class="form-control">
        <option value="1">借记卡</option>
        <option value="2">贷记卡</option>
        <option value="3">分期付款(占信用额度)</option>
        <option value="4">分期付款(不占信用额度)</option>
      </select>
    </div>

    <%@ include file="../bank/bankSelectorV4.jsp" %>
    
    <div class="form-group">
      <label class="form-check-label">
        <input type="checkbox" name="isSubsidiaryAffiliation" >是附属账户
      <label>
    </div>
    
    <div class="form-group">
      <label name="accountAffiliation"></label>
      <select id="accountAffiliation" class="form-control">
        <option value="0">请选择归属的主账号</option>
      </select>
    </div>

    <div class="form-group">
      <label name="vaildDate">有效期: </label>
      <input type="Date" placeholder="请选择有效期" id="vaildDate">
    </div>
    
    <div class="form-group">
      <label name="accountBalance">账户余额: </label>
      <input id="accountBalance" value="" class=""/>
    </div>
    <div class="form-group">
      <label name="creditsQuota">信用额度: </label>
      <input id="creditsQuota" value="" class=""/>
    </div>
    <div class="form-group">
      <label name="temproraryCreditsQuota">临时信用额度: </label>
      <input id="temproraryCreditsQuota" value="" class=""/>
    </div>
    <div class="form-group">
      <label name="temproraryCreditsVaildDate">临时信用额度有效期: </label>
      <input type="Date" placeholder="临时信用额度有效期" id="temproraryCreditsVaildDate">
    </div>
    <div class="form-group">
      <label name="remark">备注: </label>
      <input id="remark" value="" class=""/>
    </div>

     
    <div class="form-group">
      <span onclick="accountFormSubmit();" class="label label-primary">Submit</span>
    </div>
  </div>
</body>
<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">
      // account number type number only
      $("#accountNumber").keyup(function () { 
        var $this = $(this);
        $this.val($this.val().replace(/[^0-9]/g, ""));
      });

      // accountNumber duplicate check
      $("#accountNumber").blur(function(){ 
        var accountNumber = $(this).val();
        var jsonParams = {};
        jsonParams['accountNumber'] = accountNumber;
        $.ajax({               
          type: "POST",  
          url: "/accountInfo/accountNumberDuplicateCheck",   
          data: JSON.stringify(jsonParams), 
          dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            if (data.code === '0') {
              document.getElementById("accountNumberP").className = "badge badge-success";
              $("#accountNumberP").text("can use");
            } else {
              document.getElementById("accountNumberP").className = "badge badge-danger";
              $("#accountNumberP").text(data.message);
            }
          }, 
          error:function(e){
          }
        });
      });  

      //accountAffiliation select dynamic
      function accountAffiliation(){
        var $thisCheckBox = $("input[name='isSubsidiaryAffiliation']");
        var $affiliationSelector = $("#accountAffiliation");
        var bankId = $("div[name='bankHiddenMessage']").attr("bankId");
        if ($thisCheckBox.is(":checked")){
          var jsonOutput = {
            bankId : bankId,
          };
          $.ajax({               
            type: "POST",  
            url: "/accountInfo/currentAccountNumberList",   
            data: JSON.stringify(jsonOutput),
            dataType: 'json',
            contentType: "application/json",
            timeout: 15000,
            beforeSend: function(xhr) {
              xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success:function(data){  
              $affiliationSelector.empty();
              if(data.code == 0) {
                var accountList = data.accountList;
                $affiliationSelector.append($("<option></option>").attr("value", null).text("Not belong to any account"));
                $.each(accountList, function(index, accountInfo) {
                  $affiliationSelector.append($("<option></option>").attr("value", accountInfo.accountId).text(accountInfo.alias));
                });
              } else {
                $affiliationSelector.append($("<option></option>").attr("value", 0).text(data.message));
              }
            }, 
            error:function(e){
            }
          });
        } else {
          $affiliationSelector.empty();
          $affiliationSelector.append($("<option></option>").attr("value", null).text("Not belong to any account"));
        };
      };

      $("input[name='isSubsidiaryAffiliation']").click(accountAffiliation);

      
      $("select[name='bankId']").change( function() {
        accountAffiliation();
      });

      function accountFormSubmit(){
        var bankUnionId = $("div[name='bankHiddenMessage']").attr("bankUnionId");
        var bankId = $("div[name='bankHiddenMessage']").attr("bankId");
        var jsonOutput = {
          accountNumber: $("#accountNumber").val(),
          accountAlias: $("#accountAlias").val(),
          accountType: $("#accountType").val(),
          bankUnionId: bankUnionId,
          bankId: bankId,
          accountAffiliation: document.getElementById("accountAffiliation").value,
          vaildDate: $("#vaildDate").val(),
          accountBalance: $("#accountBalance").val(),
          creditsQuota: $("#creditsQuota").val(),
          temproraryCreditsQuota: $("#temproraryCreditsQuota").val(),
          temproraryCreditsVaildDate: $("#temproraryCreditsVaildDate").val(),
          remark: $("#remark").val(),
        };

        $.ajax({
          type: "POST",
          url:"/accountInfo/accountRegist",
          data: JSON.stringify(jsonOutput),
          dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success: function (data) {
            if (data.code == "0") {
              document.getElementById("message").className = "badge badge-success";
              $("#accountRegistrationForm").html("");
            } else {
              document.getElementById("message").className = "badge badge-danger";
            }
            
            $("#message").text(data.message);
          },
          error:function(data){

          }
        });
      };

  </script>
</footer>
