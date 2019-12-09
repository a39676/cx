<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="../cleanBlogJSP/cleanBlogNormalHeader.jsp" %>
</head>

<body>

  <!-- Navigation -->
  <%@ include file="./nav.jsp" %>

  <!-- Page Header -->
  <!-- 
  <header class="masthead" style="background-image: url('/static_resources/cleanBlog/img/fog-4597348_1920.jpg')">
  -->
  <header class="masthead" style="background-image: url('/static_resources/cleanBlog/img/nature-4607496_1920.jpg')"> 
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h2>${title}</h2>
            <span class="subheading">可重复的事情, 让机器去做</span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Main Content -->
  <div class="container-fluid">
    <div class="row">
      <!-- channels -->
      <div class="col-md-2 mx-auto">
        <div class="btn-group-vertical" id="testCases">
          <button class='btn btn-sm testCaseButton'>bing 搜索样例</button>
        </div>
      </div>
      <div class="col-md-8 mx-auto">
        <div class="container-fluid">
          <div class="row">
            <div class="form-check">
              <input class="form-check-input" type="radio" id="searchReportRadio" name="rowType" checked="checked" value="查询日志报告">
              <label class="form-check-label badge badge-primary" for="searchReportRadio">查询日志报告</label>
            </div>
          </div>
          <div class="row" id="searchReportRow">
            <div class="col-md-12 mx-auto">
              <form id="searchConditionArea" markTime="" loadingFlag="">
                <div class="control-group">
                  <span class="badge badge-warning">任务创建时间范围</span><input type="date" id="createStartDate"> 
                  <input type="time" time="HH:mm:ss" id="createStartTime" step="1" value="00:00:00">
                  <span>~</span>
                  <input type="date" id="createEndDate" value="${createEndTime}"> <input type="time" time="HH:mm:ss" id="createEndTime" step="1" value="23:59:59">
                </div>
                <div class="control-group">
                  <span class="badge badge-warning">任务启动时间范围</span><input type="date" id="runTimeStartDate"> 
                  <input type="time" time="HH:mm:ss" id="runTimeStartTime"step="1" value="00:00:00">
                  <span>~</span>
                  <input type="date" id="runTimeEndDate" value="${runTimeEndTime}"> <input type="time" time="HH:mm:ss" id="runTimeEndTime" step="1" value="23:59:59">
                </div>                
                <div class="control-group">
                  <span class="badge badge-warning">请选择案例</span>
                  <select id="moduleIdSelector">
                    <option value="">All</option>
                    <option value="3">bing搜索 Demo</option>
                  </select>
                </div>
                <div class="control-group">
                  <span class="badge badge-warning">具体任务ID(可选)</span><input type="number" min="0" step="1" id="id" placeholder="具体任务ID(可选)">
                </div>
              </form>
            </div>
          </div>
          <div class="row">
            <div class="form-check">
              <input class="form-check-input" type="radio" id="insertTestEventRadio" name="rowType" value="新增测试任务">
              <label class="form-check-label badge badge-primary" for="insertTestEventRadio">新增测试任务</label>
            </div>
          </div>
          <div class="row" id="insertTestEventRow" style="display: none;">
            <div class="col-md-12 mx-auto">
              <form id="insertTestEventForm">
                <div class="control-group">
                  <span class="badge badge-warning">请选择案例</span>
                  <select id="">
                    <option value="3">bing搜索 Demo</option>
                  </select>
                </div>
                <div class="control-group">
                  <span class="badge badge-warning">搜索关键字</span>
                  <input type="text" size="35" id="searchKeyWord" placeholder="请输入搜索关键字, 若空, 默认为 testDemo">
                </div>
                <div class="control-group">
                  <span class="badge badge-warning">预约运行时间(可空)</span>
                  <input type="date" id="appointmentDate">
                  <input type="time" time="HH:mm:ss" id="appointmentTime" step="1">
                </div>
                <span class="btn btn-sm btn-primary" id="insertTestEventButton">加入本次任务</span>
                <label id="insertTestEventResult"></label>
              </form>
            </div>
          </div>
          <hr>
          <div class="row" id="reportArea">
            <div class="col-md-12 mx-auto" id="reportRowArea"></div>
          </div>
          <hr>
          <div class="row">
            <div class="col-md-12 mx-auto">
              <div class="spinner-border text-warning" role="status" id="loadingImg">
                <span class="sr-only">Loading...</span>
              </div>
              <button class="btn btn-sm btn-success" id="loadMoreButton"><b>LOAD MORE</b></button>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-2 mx-auto">
      </div>
      
    </div>
  </div>

  <hr>

  <!-- Footer -->
  <%@ include file="../cleanBlogJSP/footer.jsp" %>

  <!-- SCIPTS -->
  <%@ include file="../cleanBlogJSP/cleanBlogNormalFooter.jsp" %>

  <script type="text/javascript">
  $(document).ready(function() {
    var getUrlParameter = function getUrlParameter(sParam) {
      var sPageURL = decodeURIComponent(window.location.search.substring(1)),
      sURLVariables = sPageURL.split('&'),
      sParameterName,
      i;
      for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] === sParam) {
          return sParameterName[1] === undefined ? true : sParameterName[1];
        }
      }
    };

    function buildReportRow(subReportRowVO) {
      var newReportRow = "";
      newReportRow += "<div class='post-preview'>";
      newReportRow += "<a href='/atDemo/findReportByTestEventId?testEventId="+subReportRowVO.id+"' target='_blank'>";
      newReportRow += "<h2 class='post-title'>"+subReportRowVO.eventName+"</h2>";
      newReportRow += "<h3 class='post-subtitle'>ID: "+subReportRowVO.id+"</h3>";
      newReportRow += "</a>";
      newReportRow += "<p class='post-meta'>";
      newReportRow += "任务创建时间: "+subReportRowVO.createTimeStr + "; ";
      newReportRow += "任务执行时间: "+subReportRowVO.startTimeStr + "; ";
      newReportRow += "任务结束时间: "+subReportRowVO.endTimeStr + "; ";
      newReportRow += "</p>";
      newReportRow += "</div>";
      newReportRow += "<hr>"
      return newReportRow;
    }

    $("#loadMoreButton").click(function () {
      loadReportSummary();
    });

    function loadReportSummary() {
      var reportRowArea = $("#reportRowArea");
      var searchConditionArea = $("#searchConditionArea");
      
      var id = document.getElementById("id").value;
      var moduleIdSelector = document.getElementById("moduleIdSelector");
      var moduleId = moduleIdSelector.options[moduleIdSelector.selectedIndex].value;
      var markTime = searchConditionArea.attr("markTime");

      var sourceCreateStartDate = document.getElementById("createStartDate").value;
      var sourceCreateStartTime = document.getElementById("createStartTime").value;
      var sourceCreateEndDate = document.getElementById("createEndDate").value;
      var sourceCreateEndTime = document.getElementById("createEndTime").value;
      var sourceRunTimeStartDate = document.getElementById("runTimeStartDate").value;
      var sourceRunTimeStartTime = document.getElementById("runTimeStartTime").value;
      var sourceRunTimeEndDate = document.getElementById("runTimeEndDate").value;
      var sourceRunTimeEndTime = document.getElementById("runTimeEndTime").value;

      var createStartTime = null;
      var createEndTime = null;
      var runTimeStartTime = null;
      var runTimeEndTime = null;

      if(sourceCreateStartDate.length) {
        createStartTime = "" + sourceCreateStartDate + " " + timeFormat(sourceCreateStartTime);
      }
      if(sourceCreateEndDate.length) {
        createEndTime = "" + sourceCreateEndDate + " " + timeFormat(sourceCreateEndTime);
      }
      if(sourceRunTimeStartDate.length) {
        runTimeStartTime = "" + sourceRunTimeStartDate + " " + timeFormat(sourceRunTimeStartTime);
      }
      if(sourceRunTimeEndDate.length) {
        runTimeEndTime = "" + sourceRunTimeEndDate + " " + timeFormat(sourceRunTimeEndTime);
      }

      $("#loadingImg").fadeIn(150);    
      if(searchConditionArea.attr("loadingFlag") == "1") {
        $("#loadingImg").fadeOut(150);
        return;
      }
      searchConditionArea.attr("loadingFlag", "1");
      var jsonOutput = {
        endTime:markTime,
        createStartTime: createStartTime,
        createEndTime: createEndTime,
        runTimeStartTime: runTimeStartTime,
        runTimeEndTime: runTimeEndTime,
        id:id,
        moduleId:moduleId
      };

      var url = "/atDemo/findReportsByCondition";
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
          var jsonResponse = JSON.parse(datas);
          var reportRowArea = $("#reportRowArea");
          var newRow = "";
          jsonResponse.forEach(function(subReportVO) {
            newRow = buildReportRow(subReportVO);
            reportRowArea.append(newRow);
            searchConditionArea.attr("markTime", subReportVO.createTimeStr);
          });
          $("#loadingImg").fadeOut(150);
        },  
        error: function(datas) {  
          $("#loadingImg").fadeOut(150);
        }
      }); 
      searchConditionArea.attr("loadingFlag", "0");
    };

    function timeFormat(timeStr) {
      if(timeStr.length == 5) {
        timeStr = timeStr + ":00";
      }
      return timeStr;
    }

    function getDateNow() {
      var dt = new Date(); 
      var year = dt.getFullYear();
      var month = (dt.getMonth() + 1);
      if(month < 10) { month = "0" + month; }
      var date = dt.getDate();
      if (date < 10) { date = "0" + date; }
      var dtStr = year + "-" + month + "-" + date; 
      return dtStr;
    }

    function getTimeNow() {
      var dt = new Date(); 
      
      var hours = dt.getHours();
      if (hours < 10) { hours = "0" + hours; }
      var minutes = dt.getMinutes();
      if (minutes < 10) { minutes = "0" + minutes; }
      var seconds = dt.getSeconds();
      if (seconds < 10) { seconds = "0" + seconds; }
      var dtStr = hours + ":" + minutes + ":" + seconds;
      return dtStr;
    }

    function refreshReportRowArea() {
      var reportRowArea = $("#reportRowArea");
      reportRowArea.html("");
      loadReportSummary();
    }

    $("#searchConditionArea").change(function () {
      refreshReportRowArea();
    }); 
    
    loadReportSummary();

    $("input[name='rowType']").click(function () {
      var tmpv = $("#searchReportRadio:checked").val();
      if(tmpv != null && tmpv.length) {
        document.getElementById("insertTestEventRow").style.display = "none";
        document.getElementById("searchReportRow").style.display = "";
      } else {
        document.getElementById("insertTestEventRow").style.display = "";
        document.getElementById("searchReportRow").style.display = "none";
      }
    })

    $("#insertTestEventButton").click(function () {
      var reportRowArea = $("#reportRowArea");
      var insertTestEventResult = $("#insertTestEventResult");
      var searchKeyWord = $("#searchKeyWord").val();

      var sourceAppointmentDate = document.getElementById("appointmentDate").value;
      var sourceAppointmentTime = document.getElementById("appointmentTime").value;
      
      var appointment = null;

      if(sourceAppointmentDate.length > 2 && sourceAppointmentTime.length < 2) {
        sourceAppointmentTime = "00:00:00";
      } else if (sourceAppointmentDate.length < 2 && sourceAppointmentTime.length > 2) {
        sourceAppointmentDate = getDateNow();
      } else if (sourceAppointmentDate.length < 2 && sourceAppointmentTime.length < 2) {
        sourceAppointmentDate = getDateNow();
        sourceAppointmentTime = getTimeNow();
      }

      appointment = "" + sourceAppointmentDate + " " + timeFormat(sourceAppointmentTime);

      var jsonOutput = {
        appointment: appointment,
        searchKeyWord:searchKeyWord
      };

      var url = "/atDemo/insertBingDemoTestEvent";
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
          // var jsonResponse = JSON.parse(datas);
          
          if (datas.code == 0) {
            var eventId = datas.eventId;
            var waitingEventCount = datas.waitingEventCount;
            var r = "";
            r += "已经新增任务, 前面还有: "+waitingEventCount+"个任务在队列, 预计最快将于 "+appointment+" 运行。"
            r += "<br>";
            r += "30分钟内可加入"+datas.maxInsertCount+"个任务, 现已加入"+datas.hasInsertCount+"个";
            r += "";
            insertTestEventResult.html(r);
          } else {
            insertTestEventResult.html(datas.message);
          }

        },  
        error: function(datas) {
          insertTestEventResult.html(datas.message);  
        }
      }); 
    })
  });
  </script>
</body>

</html>
