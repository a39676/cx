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
        <table class="table table-striped table-bordered table-hover">
          <tr>
            <td>货名</td>
            <td>挂链ID</td>
            <td>来源ID</td>
            <td>供应商</td>
            <td>首图</td>
            <td>是否包邮</td>
            <td>备注</td>
          </tr>
          <tr>
            <td>
              <input type="text" name="" id="commodityName" placeholder="货名"><br>
              <input type="text" name="" id="commodityNameZhTw" placeholder="貨名"><br>
              <input type="text" name="" id="commodityNameEn" placeholder="货名EN">
            </td>
            <td>
              <input type="text" name="" id="commodityId" placeholder="挂链ID">
            </td>
            <td>
              <input type="text" name="" id="sourceId" placeholder="来源ID">
            </td>
            <td>
              <select id="merchantID">
                <option value="">Null</option>
                <c:forEach items="${supplierList}" var="supplier" varStatus="loop">
                  <option value="${supplier.id}">${supplier.commodityName}</option>
                </c:forEach>
                <option value="-99999">未指定</option>
              </select>
              <input type="text" name="" id="merchantName" placeholder="商户名">
            </td>
            <td>
              <input type="text" name="" id="commodityImgName" placeholder="首图(仅图片名称部分)"><br>
            </td>
            <td>
              <input type="checkbox" id="includePostage">
            </td>
            <td>
              <input type="text" name="" id="remark" placeholder="备注"><br>
            </td>
          </tr>
        </table>
        <button id="searchProduct">SearchProduct</button>
        <button id="createProduct">CreateProduct</button>
        <button id="resetProductCondition">reset</button>
        <a href="/taobaoProductSource/priceCalculate" target="_blank">价格计算参考</a>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="productList">
          
        </div>
      </div>
    </div>
    
  </div>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script type="text/javascript">

  $(document).ready(function() {

    $('#merchantID').select2({
      <%-- placeholder: '请选择城市', --%>
      allowClear: true
    });

    $("#searchProduct").click( function() {
      $("#productList").html("Loading");
      searchProduct();
    });

    function searchProduct(){ 
      var commodityName = $("#commodityName").val();
      var commodityNameZhTw = $("#commodityNameZhTw").val();
      var commodityNameEn = $("#commodityNameEn").val();
      var commodityId = $("#commodityId").val();
      var merchantID = $("#merchantID").find(":selected").val();
      var merchantName = $("#merchantName").val();
      var sourceId = $("#sourceId").val();
      var commodityImgName = $("#commodityImgName").val();
      var includePostage = $("#includePostage").is(":checked");
      var remark = $("#remark").val();
      
      var url = "/taobaoProductSource/search";

      var jsonOutput = {
        commodityName : commodityName,
        commodityNameZhTw : commodityNameZhTw,
        commodityNameEn : commodityNameEn,
        commodityIdListStr : commodityId,
        sourceIdIdListStr : sourceId,
        merchantID : merchantID,
        merchantName : merchantName,
        commodityImgName : commodityImgName,
        includePostage : includePostage,
        remark : remark,
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
          $("#productList").html(datas);
        },  
        error: function(datas) {  
          $("#productList").html(datas);
        }  
      });  
    };

    $("#createProduct").click( function() {
      $("#msg").html("Loading");
      createProduct();
    });

    function createProduct(){
      var commodityName = $("#commodityName").val();
      var commodityNameZhTw = $("#commodityNameZhTw").val();
      var commodityNameEn = $("#commodityNameEn").val();
      var commodityId = $("#commodityId").val();
      var sourceId = $("#sourceId").val();
      var merchantID = $("#merchantID").find(":selected").val();
      var commodityImgName = $("#commodityImgName").val();
      var includePostage = $("#includePostage").is(":checked");
      var remark = $("#remark").val();

      
      var url = "/taobaoProductSource/add";

      var jsonOutput = {
        commodityName : commodityName,
        commodityNameZhTw : commodityNameZhTw,
        commodityNameEn : commodityNameEn,
        commodityId : commodityId,
        merchantID : merchantID,
        sourceId : sourceId,
        commodityImgName : commodityImgName,
        includePostage : includePostage,
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

    $("#resetProductCondition").click(function () {
      $("#commodityName").val("");
      $("#commodityNameZhTw").val("");
      $("#commodityNameEn").val("");
      $("#commodityId").val("");
      $("#sourceId").val("");
      $("#merchantID").val("").change();
      $("#commodityImgName").val("");
      $("#includePostage").prop("checked", false);
      $("#remark").val("");
      $("#msg").html("");
    });
  
  });
</script>
<script type="text/javascript">
  const TARGET_URL = '/1jlbdmb'; 
  const INTERVAL_TIME = 3000; // 检查间隔时间：毫秒
  const MAX_FAILURES = 3; // 最大连续失败次数
  let failureCount = 0; // 当前连续失败计数器
  let timerId = null; // 用于存储定时器ID
  function startPolling() {
      $.ajax({
        url: TARGET_URL,
        type: 'GET', // 根据你的接口换成 POST 或 GET
        success: function(response) {
          failureCount = 0; 
          scheduleNext();
        },
        error: function(xhr, status, error) {
          failureCount++;
          console.warn(`Connect failed (${failureCount}/${MAX_FAILURES}):`, error);            
          if (failureCount >= MAX_FAILURES) {
              console.error('已连续失败 3 次，停止定时任务！');
              clearTimeout(timerId); // 明确清除定时器
              return; 
          }            
          scheduleNext();
        }
        });
  }
  function scheduleNext() {
    // 启动定时器，30秒后再次执行 startPolling
    timerId = setTimeout(startPolling, INTERVAL_TIME);
  }
  // 首次触发任务
  startPolling();
</script>
</footer>
</html>