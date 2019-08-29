<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="container-fluid">

  <div class="row">
  <div class="col-md-12">
    <%@ include file="../accountInfoJSP/accountSelectorV1.jsp" %>
  </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-md-9">
      <div>
        <div>
          <label name="transationDate">交易日期</label>
          <!-- <input type="text" name="transationDate" data-date-format="yyyy-mm-dd"> -->
          <input type="Date" id="transationDate">
          <button class="btn btn-sm btn-primary" id="setTransationDateToday">today</button>
        </div>
        <div>
          <label name="transationParties">交易方</label>
          <input type="text" name="transationParties" placeholder="请输入交易方">
        </div>
        <div name="transationType">
          <label name="transationType">交易类型:</label><br>
          <label><input type="radio" name="transationType" value="1"/>收入</label>
          <label><input type="radio" name="transationType" value="2"/>支出</label><br>
          <label><input type="radio" name="transationType" value="3"/>冲正</label>  
        </div>
        <div>
          <label name="transationAmount">交易金额</label>
          <input type="text" name="transationAmount" placeholder="请输入交易金额">
        </div>
        <div name="fixCreditQuota" style="display:none">
          <label name="fixCreditQuota">可用额度</label>
          <input type="text" name="fixCreditQuota" placeholder="请输入可用额度">
        </div>
        <div>
          <label name="remark">备注</label>
          <input type="text" name="remark" placeholder="是否需要输入备注?">
        </div>

        <div>
          <button class="btn btn-sm" name="insertNewTransation">新增交易记录</button>
          <button class="btn btn-sm" name="test">test</button>
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

    // insert new transation
    $("button[name='insertNewTransation']").click(function(){ 
      // $(this).css("background-color","blue");
      var transationDate = document.getElementById("transationDate").value;
      var transationParties = $("input[name='transationParties']").val();
      var transationAmount = $("input[name='transationAmount']").val();
      var fixCreditQuota = $("input[name='fixCreditQuota']").val();
      var accountNumber = $("select[name='accountNumbers'] option:selected").val();
      var transationType = $("input[name='transationType']:checked").val();
      var remark = $("input[name='remark']").val();

      var jsonParams = {};

      jsonParams['transationDate'] = transationDate;
      jsonParams['transationParties'] = transationParties;
      jsonParams['transationAmount'] = transationAmount;
      jsonParams['fixCreditQuota'] = fixCreditQuota;
      jsonParams['accountNumber'] = accountNumber; 
      jsonParams['transationType'] = transationType;  
      jsonParams['remark'] = remark; 

      
      $.ajax({               
        type: "POST",  
        url: "${pageContext.request.contextPath}/accountInfo/insertNewTransationV4",   
        data: JSON.stringify(jsonParams), 
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },         
        timeout: 15000,
        success:function(data){
          if(data.code == "0") {
            $("label[name='message']").text(
              ""
              + "交易对象: " + data.transationParties + "; "
              + "交易金额: " + data.transationAmount + "; "
              + "交易日期: " + data.transationDate + "; "
              + "交易账号: " + data.accountNumber + "; "
              );
          } else {
            $("label[name='message']").text(data.message);
          }
        }, 
        error:function(e){
          // console.log(e);
        }
      });
    });  

    $("div[name='transationType']").click(function() {
      if($("input[name='transationType'][value='3']").is(':checked')) { 
        $("input[name='remark']").val("冲正");
        $("input[name='transationParties']").val("冲正");
        $("div[name='fixCreditQuota']").show();
      } else {
        $("div[name='fixCreditQuota']").hide();
        $("input[name='remark']").val("");
        $("input[name='transationParties']").val("");
      }
    });

    $("#setTransationDateToday").click(function(){
      var now = new Date();

      var day = ("0" + now.getDate()).slice(-2);
      var month = ("0" + (now.getMonth() + 1)).slice(-2);
      var year = now.getFullYear();

      var today = (year) + "-" + (month) + "-" + (day); // + " 00:00";
      console.log("today: " + today);

      var transationDate = document.getElementById("transationDate").value;
      console.log("transationDate: " + transationDate);

      var currentDate = new Date();
      console.log("currentDate: " + currentDate);
      document.getElementById("transationDate").value = today;
    });


  });
</script>
</footer>
</html>