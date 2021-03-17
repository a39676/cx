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
  <select id="metalType">
    <c:forEach items="${metalType}" var="subMetalType">
      <option value="${subMetalType.code}">${subMetalType.name}</option>
    </c:forEach>
  </select>
  <input type="text" id="maxGramPrice" placeholder="高位提示价">
  <input type="text" id="minGramPrice" placeholder="低位提示价">
  <input type="text" id="email" placeholder="email">
  <input type="Date" id="validTime">
</div>

<hr>

<button id="insert">insert</button>

<hr>

<p>insert result: </p>
<p id="result"></p>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#insert").click(function () {
        insertMetalNoticeSetting();
      });

      function insertMetalNoticeSetting() {
      
        var url = "/preciousMetal/insertMetalNoticeSetting";

        var metalType = $("#metalType option:selected").val();
        var maxGramPrice = $("#maxGramPrice").val();
        var minGramPrice = $("#minGramPrice").val();
        var email = $("#email").val();
        var validTime = $("#validTime").val();

        var jsonOutput = {
          metalType : metalType,
          maxGramPrice : maxGramPrice,
          minGramPrice : minGramPrice,
          email : email,
          validTime : validTime,
        };

        $.ajax({  
          type : "POST", 
          url : url,  
          data: JSON.stringify(jsonOutput),
          dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            $("#result").text(data.message);
          }, 
          error:function(e){
            $("#result").text(e);
          }
        });
      };

    });

  </script>
</footer>
</html>