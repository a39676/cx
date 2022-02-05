<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <%@ include file="../../baseElementJSP/normalHeader.jsp" %>

</head>
<body>

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12" id="detail">
        <span>昵称: ${nickname}</span>
        <br>
        <span>学期: ${gradeType.name}</span>
        <br>
        <span>积分: ${points}</span>
      </div>
    </div>
    <hr>
    <div class="row">
      <c:forEach items="${exerciesData.subjectDataList}" var="subjectData">
        <div class="col-md-4">
          <span>${subjectData.subjectType.cnName}</span><br>
          <span>最新分数${subjectData.lastScore}</span><br>
          <span>最近7日做了 ${subjectData.exerciesCountSevenDays}份习题</span><br>
          <span>最近7日总分数: ${subjectData.totalScoreSevenDays}</span><br>
          <span>最近7日习题平均分: ${subjectData.avgScoreSevenDays}</span><br>
          <span>最近30日做了 ${subjectData.exerciesCountThirtyDays}份习题</span><br>
          <span>最近30日总分数: ${subjectData.totalScoreThirtyDays}</span><br>
          <span>最近30日习题平均分: ${subjectData.avgScoreThirtyDays}</span><br>
        </div>
      </c:forEach>
    </div>
    <hr>
    <div class="row">
      <div class="col-md-12">
        <span>习题选项</span>
        <br>
        <span>选择学期</span>
        <select class="" name="" id="selectGrade">
          <option value="${gradeType.code}">当前学期${gradeType.name}</option>
          <c:forEach items="${gradeTypeList}" var="gradeType">
            <option value="${gradeType.code}">${gradeType.name}</option>
          </c:forEach>
        </select>
        <br>
        <span>选择学科</span>
        <select class="" name="" id="selectSubject">
          <c:forEach items="${subjectList}" var="subject">
            <option value="${subject.code}">${subject.cnName}</option>
          </c:forEach>
        </select>
      </div>
    </div>
    <hr>
    <div class="row">
      <div class="col-md-12">
        <button type="button" name="button" id="startExercies">开始习题</button>
      </div>
    </div>
  </div>

  <!-- SCIPTS -->
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">
    $(document).ready(function() {
      console.log("started");

      $("#startExercies").click(function () {
        console.log("going");
        var url = "/educate/question";

        var gradeType = $("#selectGrade option:selected").val();
        var subjectType = $("#selectSubject option:selected").val();

        url = url + "?" + "grade=" + gradeType + "&subject=" + subjectType;

        console.log("url: " + url);
        window.open(url, '_blank');
      });

      console.log("end");
    })
  </script>
</body>

<footer>

</footer>

</html>