<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

  <c:forEach items="${studentList}" var="student">
    <p>${student.studentName}</p>
    <p>Points: ${student.points}</p>
    <p>
      Point consume: 
      <input type="number" name="pointConsume" studentPK="${student.pk}" value="0">
      <input type="text" name="consumeRemark" studentPK="${student.pk}" placeholder="Remark">
      <button name="submitPointConsume" studentPK="${student.pk}">Submit</button> 
    </p>
    <label studentPK="${student.pk}" name="messageResult"></label>
  </c:forEach>


</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("button[name='submitPointConsume']").click(function() {
      var pk = $(this).attr("studentPK");
      console.log(pk);
      submitPointConsume(pk);
    });


    function submitPointConsume(pk){
      var resultLabel = $("label[name='messageResult'][studentPK='"+pk+"']");
      resultLabel.empty();

      var url = "/educate/addPointConsumeHistory";

      var point = $("input[name='pointConsume'][studentPK='"+pk+"']").val();
      var remark = $("input[name='consumeRemark'][studentPK='"+pk+"']").val();

      var jsonOutput = {
        studentPK : pk,
        point : point,
        remark : remark,
      };

      $.ajax({
          type : "POST",
          async : true,
          url : url,
          data: JSON.stringify(jsonOutput),
          contentType: "application/json",
          dataType: 'json',
          timeout:15000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            resultLabel.text(datas.message);
            if(datas.code == 0) {

            } else {

            }
          },
          error: function(datas) {
            resultLabel.text(datas.message);
          }
      });
    };

  });

</script>
</footer>
</html>
