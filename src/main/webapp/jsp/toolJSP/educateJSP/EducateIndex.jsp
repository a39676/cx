<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style type="text/css">
  .dataTD{
    text-align: center;
    vertical-align: middle;
  }
</style>
<head>
  <%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12" id="detail">
        <h4><span class="badge badge-primary">昵称: ${nickname}</span></h4>
        <h4><span class="badge badge-primary">学期: ${gradeType.name}</span></h4>
        <h4><span class="badge badge-primary">积分: ${points}</span></h4>
      </div>
    </div>

    <hr>

    <div class="row">
      <div class="col-md-12">
        <span>查看最近</span>
        <input type="text" id="leaderboardCalculateDays" name="" value="7" style="width:30px;">
        <span>天,</span>
        <select class="" name="" id="leaderboardOrderType">
          <c:forEach items="${leaderboardOrderTypeList}" var="leaderboardOrderType">
            <option value="${leaderboardOrderType.code}">${leaderboardOrderType.cnName}</option>
          </c:forEach>
        </select>
        <span>排行榜</span>
        <div class="" id="leaderboard">

        </div>
      </div>
    </div>

    <hr>

    <div class="row">
      <table class="table table-hover table-bordered table-striped table-light">
        <thead class="">
          <tr class="table-primary">
            <td class="dataTD">科目</td>
            <td class="dataTD" colspan="4">今日</td>
            <td class="dataTD" colspan="3">近7日</td>
            <td class="dataTD" colspan="3">近30日</td>
          </tr>
          <tr class="table-secondary">
            <td></td>
            <td>最新分数</td>
            <td>完成习题(份)</td>
            <td>累计总成绩</td>
            <td>平均分</td>
            <td>完成习题(份)</td>
            <td>累计总成绩</td>
            <td>平均分</td>
            <td>完成习题(份)</td>
            <td>累计总成绩</td>
            <td>平均分</td>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${exerciseDataSummary.subjectDataList}" var="subjectData">
            <tr>
              <td><span>${subjectData.subjectType.cnName}</span></td>
              <td><span>${subjectData.lastScore}</span></td>
              <td><span>${subjectData.exerciseCountToday}</span></td>
              <td><span>${subjectData.totalScoreToday}</span></td>
              <td><span>${subjectData.avgScoreToday}</span></td>
              <td><span>${subjectData.exerciseCountSevenDays}</spar></td>
              <td><span>${subjectData.totalScoreSevenDays}</span></td>
              <td><span>${subjectData.avgScoreSevenDays}</span></td>
              <td><span>${subjectData.exerciseCountThirtyDays}</span></td>
              <td><span>${subjectData.totalScoreThirtyDays}</span></td>
              <td><span>${subjectData.avgScoreThirtyDays}</span></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <hr>

    <div class="row">
      <div class="col-md-12">
        <table class="table table-hover table-bordered table-striped table-light">
          <thead class="">
            <tr class="table-primary">
              <td class="dataTD">科目</td>
              <td class="dataTD"></td>
              <td class="dataTD">分數</td>
              <td class="dataTD">耗時(分鐘)</td>
              <td class="dataTD">開始時間</td>
              <td class="dataTD">結束時間</td>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${exerciseNewData.mathDataList}" var="mathData">
              <tr>
                <td class="dataTD"><span>${mathData.subjectType.cnName}</span></td>
                <td class="dataTD"><span>${mathData.gradeType.name}</span></td>
                <td class="dataTD"><span>${mathData.score}</span></td>
                <td class="dataTD"><span>${mathData.timeConsumInMinute}</span></td>
                <td class="dataTD"><span>${mathData.startTimeStr}</span></td>
                <td class="dataTD"><span>${mathData.endTimeStr}</span></td>
              </tr>
            </c:forEach>
            <c:forEach items="${exerciseNewData.chineseDataList}" var="chineseData">
              <tr>
                <td class="dataTD"><span>${chineseData.subjectType.cnName}</span></td>
                <td class="dataTD"><span>${chineseData.gradeType.name}</span></td>
                <td class="dataTD"><span>${chineseData.score}</span></td>
                <td class="dataTD"><span>${chineseData.timeConsumInMinute}</span></td>
                <td class="dataTD"><span>${chineseData.startTimeStr}</span></td>
                <td class="dataTD"><span>${chineseData.endTimeStr}</span></td>
              </tr>
            </c:forEach>
            <c:forEach items="${exerciseNewData.englishDataList}" var="englishData">
              <tr>
                <td class="dataTD"><span>${englishData.subjectType.cnName}</span></td>
                <td class="dataTD"><span>${englishData.gradeType.name}</span></td>
                <td class="dataTD"><span>${englishData.score}</span></td>
                <td class="dataTD"><span>${englishData.timeConsumInMinute}</span></td>
                <td class="dataTD"><span>${englishData.startTimeStr}</span></td>
                <td class="dataTD"><span>${englishData.endTimeStr}</span></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>

    <hr>

    <div class="row">
      <div class="col-md-12">
        <h4><span class="badge badge-primary">习题选项</span></h4>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text badge-primary" for="inputGroupSelect01">选择学期</span>
          </div>
          <select class="custom-select" id="selectGrade">
            <option value="${gradeType.code}">当前学期${gradeType.name}</option>
            <c:forEach items="${gradeTypeList}" var="gradeType">
              <option value="${gradeType.code}">${gradeType.name}</option>
            </c:forEach>
          </select>
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text badge-primary" for="inputGroupSelect01">选择科目</span>
          </div>
          <select class="custom-select" name="" id="selectSubject">
            <c:forEach items="${subjectList}" var="subject">
              <option value="${subject.code}">${subject.cnName}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
    <hr>
    <div class="row">
      <div class="col-md-12">
        <a id="startExerciseLink" href="#" target="_blank">开始习题</a>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <a id="wordHelper" href="/wordHelper/wordHelper" target="_blank">背单词</a>
      </div>
    </div>
  </div>

  <!-- SCIPTS -->
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#selectGrade").change(function () {
        changeStartExerciseLink();
      });
      $("#selectSubject").change(function () {
        changeStartExerciseLink();
      });

      function changeStartExerciseLink() {
        var url = "/educate/question";

        var gradeTypeCode = $("#selectGrade option:selected").val();
        var subjectType = $("#selectSubject option:selected").val();

        url = url + "?" + "gradeTypeCode=" + gradeTypeCode + "&subject=" + subjectType;

        var startExerciseLink = $("#startExerciseLink");
        startExerciseLink.attr("href", url);
      }

      changeStartExerciseLink();

      $("#leaderboardCalculateDays").change(function () {
        changeLeaderboardLink();
      });
      $("#leaderboardOrderType").change(function () {
        changeLeaderboardLink();
      });

      function changeLeaderboardLink() {
        var url = "/educate/leaderboard";

        var days = $("#leaderboardCalculateDays").val();
        var orderType = $("#leaderboardOrderType option:selected").val();

        var jsonOutput = {
          "orderType":orderType,
          "days":days,
        };

        $.ajax({
          type : "POST",
          async : true,
          url : url,
          data: JSON.stringify(jsonOutput),
          cache : false,
          contentType: "application/json",
          // dataType: "json",
          timeout:50000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            $("#leaderboard").html(datas);
          },
          error: function(datas) {
          }
        });
      }

      changeLeaderboardLink();

      // keep alive request
      var intervalId = window.setInterval(function(){
        $.ajax({
          type : "GET",
          async : true,
          url : "/1jlbdmb",
          data: "",
          cache : false,
          timeout:50000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
          },
          error: function(datas) {
          }
        });
      }, 15000);
    })
  </script>
</body>

<footer>

</footer>

</html>
