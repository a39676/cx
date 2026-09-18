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
      <div class="col-md-3">
        <textarea id="orderHtmlInput" cols="30" rows="10" placeholder="order edit">
        </textarea>
        <input type="number" name="" id="rate" value="2">
      </div>
      <div class="col-md-8">
        <textarea id="orderHtmlOutput" class="clickToCopy" cols="80" rows="10">
        </textarea>
      </div>
      <div class="col-md-1">
        <textarea id="orderPriceList" class="clickToCopy" cols="10" rows="3">
        </textarea>
        <textarea id="orderTotalPrice" class="clickToCopy" cols="10" rows="3">
        </textarea>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="orderHtmlEdit">orderHtmlEdit</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-6">
        <textarea id="menuHtmlInput" cols="80" rows="10" placeholder="menu element collect">
        </textarea>
      </div>
      <div class="col-md-6">
        <textarea id="menuHtmlOutput" class="clickToCopy" cols="80" rows="10">
        </textarea>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button id="menuHtmlEdit">menuHtmlEdit</button>
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

    $("#orderHtmlEdit").click( function() {
      $("#orderHtmlOutput").val("Loading");
      orderHtmlEdit();
    });

    function orderHtmlEdit(){ 
      var orderHtmlInput = $("#orderHtmlInput").val();
      var rate = $("#rate").val();
      
      var url = "/tmpTool/meituanOrderHtmlEdit";

      var jsonOutput = {
        htmlStr : orderHtmlInput,
        rate : rate,
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
          $("#orderHtmlOutput").val(datas.htmlStr);
          $("#orderPriceList").val(datas.priceList);
          $("#orderTotalPrice").val(datas.totalPrice);
        },  
        error: function(datas) {  
          $("#orderHtmlOutput").val(datas);
        }  
      });  
    };

    $("#menuHtmlEdit").click( function() {
      $("#menuHtmlOutput").val("Loading");
      menuHtmlEdit();
    });

    function menuHtmlEdit(){ 
      var menuHtmlInput = $("#menuHtmlInput").val();
      var rate = $("#rate").val();
      
      var url = "/tmpTool/meituanMenuHtmlElementCollect";

      var jsonOutput = {
        htmlStr : menuHtmlInput,
        rate : rate,
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
          $("#menuHtmlOutput").val(datas.htmlStr);
        },  
        error: function(datas) {  
          $("#menuHtmlOutput").val(datas);
        }  
      });  
    };
  
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
</script>
</footer>
</html>