<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="container-fluid">

  <div class="row">
  <div class="col-md-12">
    <%@ include file="../bankInfoJSP/bankSelector.jsp" %>
  </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-md-9">

      <div>
        <select name="accountNumbers" >
          <option value="" disabled selected>Select your account number</option>
          <c:forEach var="account" items="${accounts}">
            <option value="${account.accountNumber}" >${account.accountAlias} : ${account.accountNumber}</option>
          </c:forEach>
        </select>
      </div>

      <div>
        <div>
          <label name="transationDate">交易日期</label>
          <input type="text" name="transationDate" data-date-format="yyyy-mm-dd">   
        </div>
        <div>
          <label name="transationParties">交易方</label>
          <input type="text" name="transationParties" placeholder="请输入交易方">
        </div>
        <div>
          <label name="transationType">交易类型</label>
          <label><input type="radio" name="transationType" value="1"/>收入</label>
          <label><input type="radio" name="transationType" value="2"/>支出</label><br>
          <label><input type="radio" name="transationType" value="3"/>冲正</label>  
        </div>
        <div>
          <label name="transationAmount">交易金额</label>
          <input type="text" name="transationAmount" placeholder="请输入交易金额">
        </div>

        <div>
          <button name="insertNewTransation">新增交易记录</button>
          <label name="message"></label>
        </div>
      </div>

    </div>
  </div>

  <hr>

</div>


<footer>


<script type="text/javascript">

  $(document).ready(function() {

    // console.log("csrf parameterName: " + csrfParameter + ", csrfToken: " + csrfToken + ", csrfHeader: " + csrfHeader);


    $("select[name='accountNumbers']").change( function() {
        console.log($("select[name='accountNumbers'] option:selected").val());
      });


    // insert new transation
    $("button[name='insertNewTransation']").click(function(){ 
      // $(this).css("background-color","blue");
      var transationDate = $("input[name='transationDate']").val();
      var transationParties = $("input[name='transationParties']").val();
      var transationAmount = $("input[name='transationAmount']").val();
      var accountNumber = $("select[name='accountNumbers'] option:selected").val();
      var transationType = $("input[name='transationType']:checked").val();
      var jsonParams = {};
      jsonParams['transationDate'] = transationDate;
      jsonParams['transationParties'] = transationParties;
      jsonParams['transationAmount'] = transationAmount;
      jsonParams['accountNumber'] = accountNumber; 
      jsonParams['transationType'] = transationType;  
      console.log(jsonParams);
      $.ajax({               
        type: "POST",  
        url: "${pageContext.request.contextPath}/accountInfo/insertNewTransation",   
        data: JSON.stringify(jsonParams), 
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },         
        timeout: 15000,
        success:function(data){  
          $("label[name='message']").text(data.message);
        }, 
        error:function(e){
          // console.log("error");
          console.log(e);
        }
      });
    });  

    //getAccountNumberList 
    function getAccountNumberList(){
      var $accountNumbers = $("select[name='accountNumbers']");
      var $bankSelector = $("select[name='bankId']")

      var jsonOutput = {
        bankId : $bankSelector.val(),
        dataType: 'json'
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
          $accountNumbers.empty();
          if(data.code == 0) {
            var accountList = data.accountList
            $.each(accountList, function(index, account) {
            $accountNumbers.append(
              $("<option></option>").attr("value", account.accountNumber).text(account.accountAlias + " : " + account.accountNumber)
            );
            });
          } else {

          }
        }, 
        error:function(e){
          // console.log("error");
          // console.log(e);
        }
      });
    };
     
    $("select[name='bankId']").change( function() {
      getAccountNumberList();
    });
  });
</script>
</footer>
</html>