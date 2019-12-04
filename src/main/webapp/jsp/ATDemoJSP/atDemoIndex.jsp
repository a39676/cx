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
          <button class='btn btn-sm testCaseButton' onclick='loadReportSummary(3)'>bing 搜索样例</button>
        </div>
      </div>
      <div class="col-md-8 mx-auto">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12 mx-auto">
              <form id="searchConditionArea" markTime="" loadingFlag="" moduleId="">
                <input type="Date" id="createStartTime">
                <input type="Date" id="createEndTime">
                <input type="Date" id="runTimeStartTime">
                <input type="Date" id="runTimeEndTime">
                <input type="number" min="0" step="1" id="moduleId">
                <input type="number" min="0" step="1" id="id">
              </form>
            </div>
          </div>
          <div class="row" id="reportArea">
            <div class="col-md-12 mx-auto" id="reportRowArea"></div>
          </div>
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
      newReportRow += "<a href='/atDemo/findReportByTestEventId?pk="+subReportRowVO.privateKey+"' target='_blank'>";
      newReportRow += "<h2 class='post-title'>"+subReportRowVO.articleTitle+"</h2>";
      newReportRow += "<h3 class='post-subtitle'></h3>";
      newReportRow += "</a>";
      newReportRow += "<p class='post-meta'>";
      newReportRow += "任务创建时间: "+subReportRowVO.createTimeStr;
      newReportRow += "任务执行时间: "+subReportRowVO.startTimeStr;
      newReportRow += "任务结束时间: "+subReportRowVO.endTimeStr;
      newReportRow += "</p>";
      newReportRow += "</div>";
      newReportRow += "<hr>"
      return newReportRow;
    }

    function loadReportSummary(moduleId) {
      var reportRowArea = $("#reportRowArea");
      var searchConditionArea = $("#searchConditionArea");
      
      reportRowArea.attr("moduleId", moduleId);
      var id = document.getElementById("id").value;
      var moduleId = document.getElementById("moduleId").value;
      var markTime = searchConditionArea.attr("markTime");
      var createStartTime = document.getElementById("createStartTime").value;
      var createEndTime = document.getElementById("createEndTime").value;
      var runTimeStartTime = document.getElementById("runTimeStartTime").value;
      var runTimeEndTime = document.getElementById("runTimeEndTime").value;
      $("#loadingImg").fadeIn(150);    
      if(searchConditionArea.attr("loadingFlag") == "1") {
        $("#loadingImg").fadeOut(150);
        return;
      }
      searchConditionArea.attr("loadingFlag", "1");
      var jsonOutput = {
        id:id,
        moduleId:moduleId,
        endTime:markTime,
        createStartTime: createStartTime,
        createEndTime: createEndTime,
        runTimeStartTime: runTimeStartTime,
        runTimeEndTime: runTimeEndTime
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
          // var json = JSON.parse(datas);
          var reportRowArea = $("#reportRowArea");
          var newRow = "";
          datas.forEach(function(subReportVO) {
            newRow = buildReportRow(subReportVO);
            reportRowArea.append(newRow);
            reportRowArea.attr("markTime", subReportVO.createTimeStr);
          });
          $("#loadingImg").fadeOut(150);
        },  
        error: function(datas) {  
          $("#loadingImg").fadeOut(150);
        }
      }); 
      searchConditionArea.attr("loadingFlag", "0");
    };
    
    function loadArticleLongSummaryFirstPage(moduleId) {
      $("#reportRowArea").attr("markTime", "");
      $(".testCaseButton").attr('disabled','disabled');
      loadReportSummary(moduleId);
      $(".testCaseButton").removeAttr('disabled');
    }
    
    $("#loadMoreButton").click(function () {
      var moduleId = $("#reportRowArea").attr("moduleId");
      loadReportSummary(moduleId);
    });
    
  </script>
</body>

</html>
