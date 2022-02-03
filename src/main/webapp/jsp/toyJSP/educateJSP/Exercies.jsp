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
        学期: ${exercies.gradeType.name}
        <br>
        科目: ${exercies.subjectType.cnName}
      </div>
    </div>

    <div class="row">
      <div class="col-md-12" id="questionList">
        <%-- ${exercies.questionList} --%>
        <c:forEach items="${exercies.questionList}" var="question">
          ${question}<br>
          <span class="question" questionNumber="${question.questionNumber}">
            ${question.expression} =
          </span>
          <input type="number" class="answerInput" name="" value="" questionNumber="${question.questionNumber}">
          <br>
        </c:forEach>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button type="button" name="button" id="submitAnswer">提交</button>
        <button type="button" name="button" id="reload" style="display: none;">再做一份</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <span id="resultSpan"></span>
        <br>
        <span id="score">分数</span>
        <br>
        <span id="point">积分</span>
      </div>
    </div>
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
        var answerSourceList = $(".answerInput");
        var subAnswer = {}
        var answerResultList = new Array();
        answerSourceList.each(function(index,element) {
          subAnswer = {};
          subAnswer.questionNumber = $(this).attr("questionnumber");
          subAnswer.answer = $(this).val();
          answerResultList.push(subAnswer);
        });

        var jsonOutput = {
          "pk":pk,
          "answerList":answerResultList,
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
            $("#resultSpan").text(datas.message);
            $("#score").text("本份习题获得分数: " + datas.totalScore);
            $("#point").text("本份习题获得积分: " + datas.points);

            $("#submitAnswer").prop('disabled', true);

            var wrongNumberList = datas.wrongNumberList;
            wrongNumberList.forEach(function (element) {
              $(".answerInput[questionNumber='"+element+"']").css('border-color', 'red');
            });
            $("#reload").show();
          },
          error: function(datas) {
            $("#resultSpan").text(datas.message);
          }
        });
      });
    })
  </script>
</body>

<footer>

</footer>

</html>
