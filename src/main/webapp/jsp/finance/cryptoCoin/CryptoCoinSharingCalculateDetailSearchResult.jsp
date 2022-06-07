<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
      <table class="table table-hover table-bordered table-striped table-light">
        <thead class="thead-dark">
          <tr>
            <th style="text-align: center; vertical-align: middle;">日期</th>
            <th colspan="2" style="text-align: center; vertical-align: middle;">操作</th>
          </tr>
        </thead>
        <c:forEach items="${detailList}" var="detail">
          <tr>
            <td style="text-align: center; vertical-align: middle;">
              <span detailPK="${detail.pk}">${detail.outputTimeStr}</span>
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <a href="/cryptoCoinSharingCalculate/calculateDetail?pk=${detail.pk}" target="_blank">查看</a>
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <button class="btn btn-danger" detailPK="${detail.pk}" type="button" name="deleteDetail">删除</button>
              <span detailPK="${detail.pk}" name="result"></span>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("button[name='deleteDetail']").click(function () {
        deleteSharingDetail($(this).attr("detailPK"));
      });

      function deleteSharingDetail(detailPK) {

        var url = "/cryptoCoinSharingCalculate/deleteSharingDetail";

        var jsonOutput = {
          pk : detailPK
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
            // $("#result").html("");
            $("span[detailPK='"+detailPK+"'][name='result']").val(data.message);
            if(data.code == 0){
              $("button[detailPK='"+detailPK+"'][name='deleteDetail']").prop("disabled",true);
            }
          },
          error:function(e){
            // $("#result").text(e);
          }
        });
      };
  });

  </script>
</footer>
</html>
