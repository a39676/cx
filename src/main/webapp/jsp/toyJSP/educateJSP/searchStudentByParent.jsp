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
      <input type="number" name="pointConsume" studentPK="${student.pk}">
      <button name="submitPointConsume" studentPK="${student.pk}">Submit</button> 
    </p>
  </c:forEach>


</body>
<footer>
<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("button[name='apply']").click(function() {
        userRegist();
    });


    function userRegist(){
      $("label[name='message']").empty();

      var url = "/user/studentRegist";

      var gender;
      if($('#male').is(':checked')) {
        gender = 1;
      } else if ($('#female').is(':checked')) {
        gender = 0;
      } else {
        gender = -1;
      }

      var jsonOutput = {
        userName : $("#userName").val(),
        nickName : $("#nickName").val(),
        gradeType : $("#gradeType option:selected").val(),
        email : $("#email").val(),
        pwd : $("#pwd").val(),
        pwdRepeat : $("#pwdRepeat").val(),
        qq : $("#qq").val(),
        mobile : $("#mobile").val(),
        reservationInformation : $("#reservationInformation").val(),
        gender : gender
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
            if(datas.code == 0) {
              $("#registForm").html("");
              $("#registResult").text(datas.message);
            } else {
              $("label[name='message']").text(datas.message);
              $("#pwd").val("");
              $("#pwdRepeat").val("");
              if(datas.validUserRegistResult.username != null) {
                $("#userNameWarnMsg").append(datas.validUserRegistResult.username);
              }
              if(datas.validUserRegistResult.nickname != null) {
                $("#nickNameWarnMsg").append(datas.validUserRegistResult.nickname);
              }
              if(datas.validUserRegistResult.email != null) {
                $("#emailWarnMsg").append(datas.validUserRegistResult.email);
              }
              if(datas.validUserRegistResult.pwd != null) {
                $("#pwdWarnMsg").append(datas.validUserRegistResult.pwd);
              }
              if(datas.validUserRegistResult.pwdRepeat != null) {
                $("#pwdRepeatWarnMsg").append(datas.validUserRegistResult.pwdRepeat);
              }
              if(datas.validUserRegistResult.qq != null) {
                $("#qqWarnMsg").append(datas.validUserRegistResult.qq);
              }
              if(datas.validUserRegistResult.mobile != null) {
                $("#mobileWarnMsg").append(datas.validUserRegistResult.mobile);
              }
              if(datas.validUserRegistResult.reservationInformation != null) {
                $("#reservationInformationWarnMsg").append(datas.validUserRegistResult.reservationInformation);
              }
            }
          },
          error: function(datas) {
            $("#registResult").text(datas.message);
          }
      });
    };

  });

</script>
</footer>
</html>
