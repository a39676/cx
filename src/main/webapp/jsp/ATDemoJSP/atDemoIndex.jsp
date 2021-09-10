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
  <header class="masthead" style="background-image: url('/static_resources/cleanBlog/img/post-sample-image.jpg')">
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
          <button class='btn btn-sm testCaseButton'>搜索样例</button>
        </div>
      </div>
      <div class="col-md-8 mx-auto">
        <div class="container-fluid">
          <div class="row">
            <div class="form-check">
              <input class="form-check-input" type="radio" id="searchReportRadio" name="rowType" checked="checked" value="查询日志报告">
              <label class="form-check-label badge badge-success" for="searchReportRadio">查询日志报告</label>
            </div>
          </div>
          <div class="row" id="searchReportRow">
            <div class="col-md-12 mx-auto">
              <form id="searchConditionArea" markTime="" loadingFlag="">
                <div class="control-group">
                  <span class="badge badge-info">任务创建时间范围</span>
                  <input type="date" id="createStartDate" value="${defaultStartTime}">
                  <input type="time" time="HH:mm:ss" id="createStartTime" step="1" value="00:00:00">
                  <span>~</span>
                  <input type="date" id="createEndDate" value="${createEndTime}">
                  <input type="time" time="HH:mm:ss" id="createEndTime" step="1" value="23:59:59">
                </div>
                <div class="control-group">
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" id="taskHadRun" name="taskRunType" checked="checked" value="true">
                    <label class="form-check-label badge badge-success" for="taskHadRun">任务已启动</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" id="taskNotRun" name="taskRunType" value="false">
                    <label class="form-check-label badge badge-secondary" for="taskNotRun">任务未启动</label>
                  </div>
                </div>
                <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                  <div class="control-group">
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" id="taskRunSuccess" name="taskSuccessType" checked="checked" value="true">
                    <label class="form-check-label badge badge-secondary" for="taskRunSuccess">任务成功</label>
                  </div>
                  <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" id="taskRunFail" name="taskSuccessType" value="false">
                    <label class="form-check-label badge badge-secondary" for="taskRunFail">任务失败</label>
                  </div>
                </div>
                </sec:authorize>
                <div class="control-group">
                  <span class="badge badge-info">任务启动时间范围</span>
                  <input type="date" id="runTimeStartDate" value="${defaultStartTime}">
                  <input type="time" time="HH:mm:ss" id="runTimeStartTime"step="1" value="00:00:00">
                  <span>~</span>
                  <input type="date" id="runTimeEndDate" value="${runTimeEndTime}">
                  <input type="time" time="HH:mm:ss" id="runTimeEndTime" step="1" value="23:59:59">
                </div>
                <div class="control-group">
                  <span class="badge badge-info">请选择案例</span>
                  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                    <select id="moduleIdSelector">
                      <option value="">All</option>
                      <c:forEach items="${modules}" var="module" >
                        <option value="${module.key}">${module.value}</option>
                      </c:forEach>
                    </select>
                  </sec:authorize>
                  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                    <div class="control-group">
                      <span class="badge badge-info">flowId</span>
                      <input type="text" name="" id="flowId" placeholder="flowId">
                    </div>
                  </sec:authorize>
                  <sec:authorize access="!hasRole('ROLE_SUPER_ADMIN')">
                    <select id="flowIdSelector">
                      <option value="">All</option>
                      <option value="1">Bing 搜索 demo</option>
                    </select>
                  </sec:authorize>
                </div>
                <div class="control-group">
                  <span class="badge badge-info">具体任务ID(可选)</span><input type="number" min="0" step="1" id="id" placeholder="具体任务ID(可选)">
                </div>
              </form>
            </div>
          </div>
          <hr>
          <div class="row">
            <div class="form-check">
              <input class="form-check-input" type="radio" id="insertTestEventRadio" name="rowType" value="新增测试任务">
              <label class="form-check-label badge badge-secondary" for="insertTestEventRadio">新增测试任务</label>
            </div>
          </div>
          <div class="row" id="insertTestEventRow" style="display: none;">
            <div class="col-md-12 mx-auto">
              <form id="insertTestEventForm">
                <div class="control-group">
                  <span class="badge badge-info">请选择案例</span>
                  <select id="insertNewTestEventCaseIdSelector">
                    <option value="1">Bing 搜索 demo</option>
                  </select>
                </div>
                <div class="control-group">
                  <span class="badge badge-info">搜索关键字</span>
                  <input type="text" size="35" id="searchKeyword" placeholder="请输入搜索关键字, (可空)">
                </div>
                <div class="control-group">
                  <span class="badge badge-info">预约运行时间(可空)</span>
                  <input type="date" id="appointmentDate">
                  <input type="time" time="HH:mm:ss" id="appointmentTime" step="1">
                </div>
                <span class="btn btn-sm btn-warning" id="insertTestEventButton">加入本次任务</span>
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
              <div class="spinner-border text-info" role="status" id="loadingImg">
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
      newReportRow += "<a href='/atDemo/findReportByTestEventId?testEventId="+subReportRowVO.idStr+"' target='_blank'>";
      newReportRow += "<h2 class='post-title'>"+subReportRowVO.flowName+"</h2>";
      newReportRow += "<h3 class='post-subtitle'>ID: "+subReportRowVO.idStr+"</h3>";
      newReportRow += "</a>";
      newReportRow += "<p class='post-meta'>";
      newReportRow += "任务创建时间: "+subReportRowVO.createTime + "; ";
      newReportRow += "任务执行时间: "+subReportRowVO.startTime + "; ";
      newReportRow += "任务结束时间: "+subReportRowVO.endTime + "; ";
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
      var moduleId = "";
      if(document.getElementById("moduleIdSelector") != null) {
        var moduleIdSelector = document.getElementById("moduleIdSelector");
        moduleId = moduleIdSelector.options[moduleIdSelector.selectedIndex].value;
      }
      var flowId = "";
      if(document.getElementById("flowId") != null) {
        flowId = document.getElementById("flowId").value;
      } else {
        var flowIdSelector = document.getElementById("flowIdSelector");
        flowId = flowIdSelector.options[flowIdSelector.selectedIndex].value;
      }
      var markTime = searchConditionArea.attr("markTime");

      var sourceCreateStartDate = document.getElementById("createStartDate").value;
      var sourceCreateStartTime = document.getElementById("createStartTime").value;
      var sourceCreateEndDate = document.getElementById("createEndDate").value;
      var sourceCreateEndTime = document.getElementById("createEndTime").value;
      var sourceRunTimeStartDate = document.getElementById("runTimeStartDate").value;
      var sourceRunTimeStartTime = document.getElementById("runTimeStartTime").value;
      var sourceRunTimeEndDate = document.getElementById("runTimeEndDate").value;
      var sourceRunTimeEndTime = document.getElementById("runTimeEndTime").value;

      var createStartTime = buildDateTime(sourceCreateStartDate, sourceCreateStartTime);
      var createEndTime = buildDateTime(sourceCreateEndDate, sourceCreateEndTime);
      var runTimeStartTime = null;
      var runTimeEndTime = null;
      var runFlag = true;
      var runSuccessFlag = null;

      var runFlag = $("#taskHadRun:checked").val();
      if(runFlag != null && runFlag.length) {
        runTimeStartTime = buildDateTime(sourceRunTimeStartDate, sourceRunTimeStartTime);
        runTimeEndTime = buildDateTime(sourceRunTimeEndDate, sourceRunTimeEndTime);
      } else {
        runFlag = false;
      }

      var taskRunSuccessVal = $("#taskRunSuccess:checked").val();
      var taskRunFailVal = $("#taskRunFail:checked").val();
      if(taskRunSuccessVal != null && taskRunSuccessVal.length) {
        runSuccessFlag = true;
      } else if(taskRunFailVal != null && taskRunFailVal.length) {
        runSuccessFlag = false;
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
        moduleId:moduleId,
        flowId:flowId,
        runFlag:runFlag,
        isSuccess:runSuccessFlag
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
          var reportRowArea = $("#reportRowArea");
          var newRow = "";
          datas.forEach(function(subReportVO) {
            newRow = buildReportRow(subReportVO);
            reportRowArea.append(newRow);
            searchConditionArea.attr("markTime", subReportVO.createTime);
          });
          $("#loadingImg").fadeOut(150);
        },
        error: function(datas) {
          $("#loadingImg").fadeOut(150);
        }
      });
      searchConditionArea.attr("loadingFlag", "0");
    };

    function buildDateTime(dateEle, timeEle) {
      if(dateEle.length > 2 && timeEle.length < 2) {
        timeEle = "00:00:00";
      } else if (dateEle.length < 2 && timeEle.length > 2) {
        dateEle = getDateNow();
      } else if (dateEle.length < 2 && timeEle.length < 2) {
        dateEle = getDateNow();
        timeEle = getTimeNow();
      }
      createStartTime = "" + dateEle + " " + timeFormat(timeEle);
      return createStartTime;
    }

    function timeFormat(timeStr) {
      if(timeStr.length == 5) {
        timeStr = timeStr + ":00";
      } else if (timeStr.length < 5) {
        timeStr = "00:00:00";
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
      var searchConditionArea = $("#searchConditionArea");
      searchConditionArea.attr("markTime", "");
      refreshReportRowArea();
    });

    loadReportSummary();

    $("input[name='rowType']").click(function () {
      // searchReportRadio
      // insertTestEventRadio
      var tmpv = $("#searchReportRadio:checked").val();
      if(tmpv != null && tmpv.length) {
        document.getElementById("insertTestEventRow").style.display = "none";
        document.getElementById("searchReportRow").style.display = "";
        $("label[for='searchReportRadio']").addClass("badge-success");
        $("label[for='searchReportRadio']").removeClass("badge-secondary");
        $("label[for='insertTestEventRadio']").addClass("badge-secondary");
        $("label[for='insertTestEventRadio']").removeClass("badge-success");
      } else {
        document.getElementById("insertTestEventRow").style.display = "";
        document.getElementById("searchReportRow").style.display = "none";
        $("label[for='searchReportRadio']").removeClass("badge-success");
        $("label[for='searchReportRadio']").addClass("badge-secondary");
        $("label[for='insertTestEventRadio']").removeClass("badge-secondary");
        $("label[for='insertTestEventRadio']").addClass("badge-success");
      }
    })

    $("#insertTestEventButton").click(function () {
      var reportRowArea = $("#reportRowArea");
      var insertTestEventResult = $("#insertTestEventResult");
      var searchKeyword = $("#searchKeyword").val();

      var sourceAppointmentDate = document.getElementById("appointmentDate").value;
      var sourceAppointmentTime = document.getElementById("appointmentTime").value;

      var appointment = buildDateTime(sourceAppointmentDate, sourceAppointmentTime);

      var insertNewTestEventCaseIdSelector = document.getElementById("insertNewTestEventCaseIdSelector");
      var flowId = insertNewTestEventCaseIdSelector.options[insertNewTestEventCaseIdSelector.selectedIndex].value;

      var jsonOutput = {
        appointment:appointment,
        searchKeyword:searchKeyword,
        flowId:flowId
      };

      var url = "/atDemo/insertSearchingDemoTestEvent";
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
            r += "<a href='"+datas.message+"' target='_blank'> _>>>预生成报告地址<<<_ </a>";
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

    $("#taskNotRun").click(function () {
      document.getElementById("runTimeStartDate").disabled = true;
      document.getElementById("runTimeStartTime").disabled = true;
      document.getElementById("runTimeEndDate").disabled = true;
      document.getElementById("runTimeEndTime").disabled = true;
      $("label[for='taskHadRun']").removeClass("badge-success");
      $("label[for='taskHadRun']").addClass("badge-secondary");
      $("label[for='taskNotRun']").removeClass("badge-secondary");
      $("label[for='taskNotRun']").addClass("badge-success");
    })

    $("#taskHadRun").click(function () {
      document.getElementById("runTimeStartDate").disabled = false;
      document.getElementById("runTimeStartTime").disabled = false;
      document.getElementById("runTimeEndDate").disabled = false;
      document.getElementById("runTimeEndTime").disabled = false;
      $("label[for='taskHadRun']").addClass("badge-success");
      $("label[for='taskHadRun']").removeClass("badge-secondary");
      $("label[for='taskNotRun']").addClass("badge-secondary");
      $("label[for='taskNotRun']").removeClass("badge-success");
    })
  });
  </script>
</body>

</html>
