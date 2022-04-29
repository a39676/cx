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
      ${machineList}
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#compare").click(function () {
        cryptoCoinDailyDataMutipleComparetor();
      });

      function cryptoCoinDailyDataMutipleComparetor() {

        var url = "/cryptoCoin/dataCompare/CryptoCoinDailyDataComparetor";

        var coinTypeOrigin = $("#coinTypeOrigin").val();
        var coinTypeComparedList = $("#coinTypeComparedList").val().split(",");
        var currencyCode = $("#currencyOfSearch option:selected").val();
        var startDateTimeStr = $("#noticeStartDate").val() + " " + $("#noticeStartTime").val();


        var jsonOutput = {
          coinTypeOrigin : coinTypeOrigin,
          coinTypeComparedList : coinTypeComparedList,
          currencyType : currencyCode,
          startDateTimeStr : startDateTimeStr,
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
            $("#result").html("");
            var subData;
            for(var i = 0; i < data.resultList.length; i++){
              subData = data.resultList[i];
              $("#result").append("<tr><td>" + subData.comparedCoinTypeName + "</td><td>"
                + subData.differentRate + "</td></tr>");
            }

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
