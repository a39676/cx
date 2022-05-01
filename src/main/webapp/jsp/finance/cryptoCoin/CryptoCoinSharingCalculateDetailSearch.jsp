<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
    <div class="col-md-12">
      <select class="detailSearchCondition" id="machineSelector" name="">
        <c:forEach items="${machineList}" var="machine">
          <option value="${machine.pk}">${machine.machineName}</option>
        </c:forEach>
      </select>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <input type="Date" class="detailSearchCondition" value="${today}" id="startTime" data-date-format="yyyy-mm-dd"/>
      <input type="Date" class="detailSearchCondition" value="${today}" id="endTime" data-date-format="yyyy-mm-dd"/>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div id="result">
      </div>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      searchSharingDetailList();
      
      $(".detailSearchCondition").change(function () {
        searchSharingDetailList();
      });

      function searchSharingDetailList() {

        var url = "/cryptoCoinSharingCalculate/sharingCalculateDetailListSearch";

        var pk = $("#machineSelector option:selected").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();

        var jsonOutput = {
          pk : pk,
          startDateStr : startTime,
          endDateStr : endTime
        };

        $.ajax({
          type : "POST",
          url : url,
          data: JSON.stringify(jsonOutput),
          // dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            $("#result").html("");
            $("#result").html(data);
          },
          error:function(e){
          }
        });
      };

    });

  </script>
</footer>
</html>
