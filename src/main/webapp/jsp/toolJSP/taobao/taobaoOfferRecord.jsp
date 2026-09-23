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

    <hr>

    <div class="row">
      <div class="col-md-6">
        <textarea id="orderSortInput" rows="3" cols="80" placeholder="订单排序"></textarea>
        <button id="orderSort">sort</button>
      </div>
      <div class="col-md-6">
        <textarea id="orderSortOutput" rows="3" cols="80" placeholder="订单排序输出"></textarea>
      </div>
    </div>

    <hr>

    <div class="row">
      <div class="col-md-6">
        <textarea id="orderFromBuyerInput" rows="3" cols="80" placeholder="买家订单入库"></textarea>
        <select id="internationalDialingCodeSelector">
          <option value="">集运仓后收件区域</option>
          <c:forEach items="${internationalDialingCodeList}" var="internationalDialingCode" varStatus="loop">
            <option value="${internationalDialingCode.code}">
              ${internationalDialingCode.code} ${internationalDialingCode.areaName}
            </option>
          </c:forEach>
        </select>
        <input type="text" name="" class="clickToCopy" id="receiverFullInfo">
        <details>
          <summary style="cursor: pointer; color: #0066cc; user-select: none;">
            点击展开/折叠详细内容
          </summary>
          <p style="margin-top: 8px; color: #333; line-height: 1.5;">
            <c:forEach items="${internationalDialingCodeList}" var="internationalDialingCode" varStatus="loop">
              +${internationalDialingCode.code} ${internationalDialingCode.areaName}<br>
            </c:forEach>
          </p>
        </details>
        <button id="buyerOrderInput">新增买家订单</button>
      </div>
      <div class="col-md-6">
        <textarea id="sourceBuyerOrderId" rows="2" cols="20" placeholder="原买家订单ID"></textarea>
        <textarea id="supplierOrderId" rows="2" cols="20" placeholder="供应商订单ID"></textarea>
        <input type="number" id="supplierOrderAmount" placeholder="供应商订单总价">
        <input type="text" id="supplierOrderRemark" placeholder="供应商订单备注">
        <select id="merchantID">
          <option value="">供应商</option>
          <c:forEach items="${supplierList}" var="supplier" varStatus="loop">
            <option value="${supplier.id}">${supplier.commodityName}</option>
          </c:forEach>
          <option value="-99999">未指定</option>
        </select>
        <button id="supplierOrderInput">新增供应订单</button>
      </div>
    </div>

  <hr>
  <div class="row">
    <div class="col-md-12">
      <h3>订单利润统计查询</h3>
      <div class="form-inline">
        <input type="text" id="queryBuyerOrderId" class="form-control" placeholder="输入 buyerOrderId">
        <input type="datetime-local" id="queryStartTime" class="form-control" placeholder="开始时间">
        <input type="datetime-local" id="queryEndTime" class="form-control" placeholder="结束时间">
        <button id="queryStatisticsBtn" class="btn btn-primary">查询统计</button>
      </div>
    </div>
  </div>
  
  <div class="row" style="margin-top: 20px;">
    <div class="col-md-12">
      <table class="table table-bordered" id="statisticsTable">
        <thead>
          <tr>
            <th>买家订单</th>
            <th>供应商订单</th>
            <th>利润</th>
          </tr>
        </thead>
        <tbody id="statisticsTableTbody">
          <tr>
            <td rowspan="">
              
            </td>
          </tr>
          <!-- 动态渲染数据 -->
        </tbody>
      </table>
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
      allowClear: true
    });

    $("#buyerOrderInput").click( function() {
      $("#msg").html("Loading");
      orderFromBuyerInput();
    });

    function orderFromBuyerInput(){
      var orderFromBuyerInput = $("#orderFromBuyerInput").val();
      var internationalDialingCodeSelectorVal = $("#internationalDialingCodeSelector").find(":selected").val();
      
      var url = "/taobao/offer/addFromDwonstreamBuyer";

      var jsonOutput = {
        offerRawText : orderFromBuyerInput,
        internationalNum : internationalDialingCodeSelectorVal,
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
          $("#sourceBuyerOrderId").val(datas.buyerOrderID);
          $("#receiverFullInfo").val(datas.receiverFullInfo)
        },  
        error: function(datas) {  
          $("#msg").html(datas.message);
        }  
      });  
    };

    $("#supplierOrderInput").click( function() {
      $("#msg").html("Loading");
      orderToSupplierInput();
    });

    function orderToSupplierInput(){
      var downstreamBuyerOrderId = $("#sourceBuyerOrderId").val();
      var supplierOrderId = $("#supplierOrderId").val();
      var amount = $("#supplierOrderAmount").val();
      var remark = $("#supplierOrderRemark").val();
      var merchantID = $("#merchantID").find(":selected").val();
      
      var url = "/taobao/offer/addToSupplier";

      var jsonOutput = {
        downstreamBuyerOrderId : downstreamBuyerOrderId,
        supplierOrderId : supplierOrderId,
        amount : amount,
        remark : remark,
        merchantID : merchantID,
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

    $("#queryStatisticsBtn").click(function () {
      queryOfferStatistics();
    });

    function queryOfferStatistics(){
      $("#msg").html("Loading statistics...");

      var url = "/taobao/offer/statistics";

      var jsonOutput = {
        buyerOrderId: $("#queryBuyerOrderId").val(),
        startTimeStr: $("#queryStartTime").val(),
        endTimeStr: $("#queryEndTime").val()
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
          var tbody = $("#statisticsTableTbody");
          tbody.empty();

          var rowList = datas.statisticsList;

          var htmlStr = "";
          for (let i = 0; i < rowList.length; i++) {
            htmlStr += "<tr>";
            var supplierOrderListSize = 1;
            var row = rowList[i];
            if(row.supplierOrderVoList != null){
              supplierOrderListSize = row.supplierOrderVoList.length;
            }
            var buyerOrder = row.buyerOrderVO;
            htmlStr += "<td>";
            htmlStr += buyerOrder.orderID + ", amount: " + buyerOrder.amount + "<br>";
            htmlStr += buyerOrder.nickname + "<br>" 
            htmlStr += buyerOrder.packageReceiverName + " " + buyerOrder.phone;
            htmlStr += buyerOrder.address + "<br>";
            if(buyerOrder.afterTransitRegionID != null){
              htmlStr += '<span style="background-color: #cfe2ff; border-radius: 4px; padding: 2px 6px; display: inline-block;">' + buyerOrder.afterTransitRegionID + " " + buyerOrder.afterTransitRegionName + "</span><br>";
            }
            htmlStr += buyerOrder.createTimeStr + "<br>";
            // buyerOrder.remark 赋圆角 绿色背景
            if(buyerOrder.remark != null) {
              htmlStr += '<span style="background-color: #d1e7dd; border-radius: 4px; padding: 2px 6px; display:inline-block;">' + buyerOrder.remark + '</span><br>';
            }
            htmlStr += "</td>";
            if(row.supplierOrderVoList != null){
              var supplierOrderVoList = row.supplierOrderVoList;
              htmlStr += "<td>";
              htmlStr += "<table><thead>";
              htmlStr += "<tr>";
              htmlStr += "<th>订单ID</th>";
              htmlStr += "<th>金额</th>";
              htmlStr += "<th>供应商</th>";
              htmlStr += "<th>备注</th>";
              htmlStr += "</tr>";
              htmlStr += "</thead><tbody>";
              for (let j = 0; j < supplierOrderVoList.length; j++) {
                var supplierOrder = supplierOrderVoList[j];
                htmlStr += "<tr>";
                htmlStr += "<td>";
                htmlStr += supplierOrder.orderID;
                htmlStr += "</td>";
                htmlStr += "<td>";
                htmlStr += supplierOrder.amount;
                htmlStr += "</td>";
                htmlStr += "<td>";
                htmlStr += supplierOrder.supplierName;
                htmlStr += "</td>";
                htmlStr += "<td>";
                // supplierOrder.remark 赋圆角 绿色背景
                if(supplierOrder.remark != null) {
                  htmlStr += '<span style="background-color: #d1e7dd; border-radius: 4px; padding: 2px 6px; display: inline-block;">' + supplierOrder.remark + '</span>';
                }
                htmlStr += "</td>";
                htmlStr += "</tr>";
              }
              htmlStr += "</tbody></table>"
              htmlStr += "</td>";
            } else {
              htmlStr += "<td>";
              htmlStr += "No data";
              htmlStr += "</td>";
            }
            htmlStr += "<td>";
            if (row.profit < 0) {
              htmlStr += '<span style="color: red; font-weight: bold;">' + row.profit + '</span>';
            } else {
              htmlStr += row.profit;
            }
            htmlStr += "</td>";
            htmlStr += "</tr>";
          }
          htmlStr += "<tr>";
          htmlStr += "<td>";
          htmlStr += datas.totalBuyerOrderAmount;
          htmlStr += "</td>";
          htmlStr += "<td>";
          htmlStr += datas.totalSupplierOrderAmount;
          htmlStr += "</td>";
          htmlStr += "<td>";
          htmlStr += datas.totalProfit;
          htmlStr += "</td>";
          htmlStr += "</tr>";
          tbody.html(htmlStr);
        },  
        error: function(datas) {  
          $("#msg").html(datas.message);
        }  
      });  
    };


    $("#orderSort").click(function () {
      var orderRawData = $("#orderSortInput").val();
      orderSort(orderRawData);
    })

    function orderSort(rawData) {
      // 1. 解析文本并提取商品列表
      const lines = rawData.split('\n');
      const items = [];
      let currentItem = {};
      
      for (let line of lines) {
        const parts = line.split('：');
        if (parts.length < 2) continue;
        const key = parts[0].trim();
        const value = parts.slice(1).join('：').trim();    
        if (key === '商品标题') {
          if (Object.keys(currentItem).length > 0) {
            items.push(currentItem);
          }
          currentItem = { 商品标题: value };
        } else if (key === '商品数量') {
          currentItem.商品数量 = Number(value);
        } else if (key === '商品ID') {
          currentItem.商品ID = value;
        } else if (key === '商品规格sku') {
          currentItem.商品规格sku = value;
        }
      }
  
      if (Object.keys(currentItem).length > 0) {
        items.push(currentItem);
      }

      // 2. 先按“商品ID”排序，再按“商品规格sku”排序
      items.sort((a, b) => {
        // 优先比较商品ID
        if (a.商品ID !== b.商品ID) {
          return a.商品ID.localeCompare(b.商品ID, 'en', { numeric: true });
        }
        // 商品ID相同时，比较商品规格sku
        return a.商品规格sku.localeCompare(b.商品规格sku);
      });
      
      // 3. 转换为规范的 JSON 格式
      const jsonResult = JSON.stringify(items, null, 4);
      $("#orderSortOutput").val(jsonResult);
    }

    // 点击 class = clickToCopy 复制内容到剪贴板
    $(".clickToCopy").click(function() {
      var content = $(this).val();
      // 判断内容是否为空，且不是正在加载的提示
      if (content && content.trim() !== "" && content !== "Loading") {
        navigator.clipboard.writeText(content).then(function() {
          // 复制成功提示（可根据需求自行修改或去掉）
          console.log("内容已成功复制到剪贴板");
        }).catch(function(err) {
          console.error("复制到剪贴板失败: ", err);
        });
      }
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