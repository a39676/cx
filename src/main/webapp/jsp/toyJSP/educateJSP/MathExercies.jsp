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
      <div class="col-md-12" id="detail" pk=${exercies.exerciesPK}>
        <h3><span class="badge badge-primary">学期: ${exercies.gradeType.name}</span></h3>
        <h3><span class="badge badge-primary">科目: ${exercies.subjectType.cnName}</span></h3>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12" id="questionList">
        <%-- ${exercies.questionList} --%>
        <c:forEach items="${exercies.questionList}" var="question">
          <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
            ${question}<br>
          </sec:authorize>
          <div class="input-group">
            <div class="input-group">
              <span class="question input-group-text" questionNumber="${question.questionNumber}">
                <h5>(${question.questionNumber}):</h5>
                <h4>&nbsp;&nbsp;&nbsp;${question.expression}</h4>
              </span>
            </div>
            <br>
            <c:forEach items="${question.standardAnswer}" var="standardAnswer">
              <input type="number" class="form-control answerInput" style="font-size:26px" size="8" name="" questionNumber="${question.questionNumber}">
            </c:forEach>
          </div>
          <span questionNumber="${question.questionNumber}" class="standarAnswer"></span>
          <br>
        </c:forEach>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button type="button" name="button" class="btn btn-primary" id="submitAnswer">提交</button>
        <button type="button" name="button" class="btn btn-primary" id="reload" style="display: none;">再做一份</button>
      </div>
    </div>

    <div class="row" id="resultRow" style="display:none;">
      <div class="col-md-12">
        <h3><span class="badge badge-success" id="resultSpan"></span></h3>
        <h3><span class="badge badge-success" id="score">分数</span></h3>
        <h3><span class="badge badge-success" id="point">积分</span></h3>
      </div>
    </div>

    <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
      <div class="row" id="demo">
        <div class="col-md-12">
          <div class="input-group">
            <div class="input-group-prepend">
              <span class="input-group-text" id="">Multiple inputs</span>
            </div>
            <input type="text" class="form-control" value="input v1">
            <input type="text" class="form-control" value="input v2">
          </div>
        </div>
      </div>
    </sec:authorize>
  </div>

  <!-- SCIPTS -->
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">
    $(document).ready(function() {

      $("#reload").click(function () {
        location.reload();
      });

      $("#submitAnswer").click(function () {
        var url = "/educate/answerSubmit";

        var detail = $("#detail");
        var pk = detail.attr("pk");

        var exerciesAnswerDTO = new Array();
        var answerElementDTO = {};
        for (let i = 1; i <= ${questionListSize}; i++) {
          var answerList = new Array();
          var answer = $(".answerInput[questionNumber='"+ i +"']");
          answer.each(function(index,element) {
            answerList.push($(this).val());
          });
          answerElementDTO = {};
          answerElementDTO.questionNumber = i;
          answerElementDTO.answer = answerList;
          exerciesAnswerDTO.push(answerElementDTO);
        }

        var jsonOutput = {
          "pk":pk,
          "answerList":exerciesAnswerDTO,
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
            $("#resultRow").show();
            $("html, body").animate({ scrollTop: $(document).height() }, "slow");
            $("#resultSpan").text(datas.message);
            $("#score").text("本份习题获得分数: " + datas.totalScore);
            $("#point").text("本份习题获得积分: " + datas.points);

            $("#submitAnswer").prop('disabled', true);

            var wrongNumberList = datas.wrongNumberList;
            wrongNumberList.forEach(function (element) {
              $(".answerInput[questionNumber='"+element+"']").css('border-color', 'red');
            });

            var answerMap = datas.answerMap;
            Object.keys(answerMap).forEach(function (key) {
              $(".standarAnswer[questionNumber='"+key+"']").text(answerMap[key]);
            });

            $("#reload").show();
          },
          error: function(datas) {
            $("#resultSpan").text(datas.message);
          }
        });
      });
      
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
