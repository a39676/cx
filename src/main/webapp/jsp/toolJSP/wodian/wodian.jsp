<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12">
        <label id="msg">${msg}</label>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <select id="salesmanSelector">
          <option value="">业务</option>
          <c:forEach items="${salesmanList}" var="salesman" varStatus="loop">
            <option value="${salesman.id}">${salesman.name}</option>
          </c:forEach>
        </select>
        <select id="merchantsSelector">
          <option value="">商家</option>
          <c:forEach items="${merchantsList}" var="merchants" varStatus="loop">
            <option value="${merchants.id}">${merchants.name}</option>
          </c:forEach>
        </select>
        <input type="text" name="" id="startDateStr" placeholder="开始日期">
        <input type="text" name="" id="endDateStr" placeholder="结束日期">
        <button id="search">Search</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <select id="salesmanSelectorForNewContract">
          <option value="">业务</option>
          <c:forEach items="${salesmanList}" var="salesman" varStatus="loop">
            <option value="${salesman.id}">${salesman.name}</option>
          </c:forEach>
        </select>
        <select id="merchantsSelectorForNewContract">
          <option value="">商家</option>
          <c:forEach items="${merchantsList}" var="merchants" varStatus="loop">
            <option value="${merchants.id}">${merchants.name}</option>
          </c:forEach>
        </select>
        <input type="text" name="" id="newContractDate" placeholder="日期">
        <input type="text" name="" id="clientNameForNewContract" placeholder="客户">
        <input type="text" name="" id="clientPhoneForNewContract" placeholder="手机号">
        <input type="number" name="" id="contractAmountForNewContract" placeholder="金额">
        <input type="number" name="" id="goldCoinForClientForNewContract" placeholder="金币(客)">
        <input type="number" name="" id="goleCoinForMerchantsForNewContract" placeholder="金币(商家)">
        <input type="number" name="" id="integralForClientForNewContract" placeholder="积分(客)">
        <input type="number" name="" id="integralForMerchantsForNewContract" placeholder="积分(商家)">
        <input type="number" name="" id="partCountsForNewContract" placeholder="份数">
        <input type="text" name="" id="renmarkForNewContract" placeholder="备注">
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="create">Create</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="contractTable">
          
        </div>
      </div>
    </div>
  </div>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#search").click( function() {
      search();
    });

    function search(){ 
      var salesmanId = $('#salesmanSelector').find(":selected").val();
      var merchantsId = $('#merchantsSelector').find(":selected").val();
      var startDateStr = $("#startDateStr").val();
      var endDateStr = $("#endDateStr").val();
      
      var url = "/wodian/getContractListByCondition";

      var jsonOutput = {
        salesmanId : salesmanId,
        merchantsId : merchantsId,
        startDateStr : startDateStr,
        endDateStr : endDateStr,
      };

      $.ajax({  
        type : "POST",  
        async : true,
        url : url, 
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        <%-- dataType: "json", --%>
        timeout:50000,
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#contractTable").html(datas);
        },  
        error: function(datas) {  
          $("#contractTable").html(datas);
        }  
      });  
    };

    $("#create").click( function() {
      create();
    });

    function create(){ 
      var salesmanId = $('#salesmanSelectorForNewContract').find(":selected").val();
      var merchantsId = $('#merchantsSelectorForNewContract').find(":selected").val();
      var contractCreateTimeStr = $("#startDateStr").val();
      var clientName = $("#clientNameForNewContract").val();
      var clientPhoneNumber = $("#clientPhoneForNewContract").val();
      var contractAmount = $("#contractAmountForNewContract").val();
      var goldCoinForClient = $("#goldCoinForClientForNewContract").val();
      var goleCoinForMerchants = $("#goleCoinForMerchantsForNewContract").val();
      var integralForClient = $("#integralForClientForNewContract").val();
      var integralForMerchants = $("#integralForMerchantsForNewContract").val();
      var partCounts = $("#partCountsForNewContract").val();
      var remark = $("#renmarkForNewContract").val();
      
      var url = "/wodian/createContract";

      var jsonOutput = {
        salesmanId : salesmanId,
        merchantsId : merchantsId,
        contractCreateTimeStr : contractCreateTimeStr,
        clientName : clientName,
        clientPhoneNumber : clientPhoneNumber,
        contractAmount : contractAmount,
        goldCoinForClient : goldCoinForClient,
        goleCoinForMerchants : goleCoinForMerchants,
        integralForClient : integralForClient,
        integralForMerchants : integralForMerchants,
        partCounts : partCounts,
        remark : remark,
      };

      $.ajax({  
        type : "POST",  
        async : true,
        url : url, 
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        dataType: "json",
        timeout:50000,
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#msg").html(datas.message);
        },  
        error: function(datas) {  
          $("#msg").html(datas.message);
        }  
      });  
    };
  
  });

</script>
</footer>
</html>