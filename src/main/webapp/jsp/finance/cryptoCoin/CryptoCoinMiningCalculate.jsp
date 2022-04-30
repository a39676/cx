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
      <c:forEach items="${machineList}" var="machine">
        <table class="table table-hover table-bordered table-striped table-light">
          <thead class="thead-dark">
            <tr>
              <th colspan="2" style="text-align: center; vertical-align: middle;">集群名(ID)</th>
              <th style="text-align: center; vertical-align: middle;">对应币</th>
              <th style="text-align: center; vertical-align: middle;">手续费</th>
              <th style="text-align: center; vertical-align: middle;">分割份数</th>
            </tr>
          </thead>
          <tr>
            <td colspan="2" style="text-align: center; vertical-align: middle;">${machine.machineName}(${machine.idStr})</td>
            <td style="text-align: center; vertical-align: middle;">${machine.coinName}</td>
            <c:set var="handlingFeeRateInPercent"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${machine.handlingFeeRate * 100}" /></c:set>
            <td style="text-align: center; vertical-align: middle;">${handlingFeeRateInPercent}%</td>
            <td style="text-align: center; vertical-align: middle;">${machine.partingCount}</td>
          </tr>
          <tr class="table-primary">
            <td style="text-align: center; vertical-align: middle;">用户名</td>
            <td colspan="2"></td>
            <td style="text-align: center; vertical-align: middle;">佣金</td>
            <td style="text-align: center; vertical-align: middle;">所占份数</td>
          </tr>
          <c:forEach items="${machine.assistantList}" var="assistant">
            <tr>
              <td style="text-align: center; vertical-align: middle;">${assistant.name}</td>
              <td colspan="2"></td>
              <c:set var="commissionFeeRateInPercent"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${assistant.commissionFeeRate * 100}" /></c:set>
              <td style="text-align: center; vertical-align: middle;">${commissionFeeRateInPercent}%</td>
              <td style="text-align: center; vertical-align: middle;">${assistant.partingCount}</td>
            </tr>
          </c:forEach>
        </table>
        <form name="calculateAssistant" machineId="${machine.id}" novalidate>
          <div class="control-group">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text">标记日期</span>
              </div>
              <input type="Date" class="" value="${today}" machineId="${machine.id}" name="markDate" data-date-format="yyyy-mm-dd"/>
              <div class="input-group-prepend">
                <span class="input-group-text">本期获得币</span>
              </div>
              <input type="number" class="form-control" placeholder="0" machineId="${machine.id}" name="getCoinCounting" required data-validation-required-message="请输入本期获得币数" value="">
            </div>
            <button machineId="${machine.id}" class="btn btn-primary" type="button" name="calculate">核算</button>
          </div>
        </form>
        <hr>
      </c:forEach>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("button[name='calculate']").click(function () {
        var machineId = $(this).attr("machineId");
        assistantCalculate(machineId);
      });

      function assistantCalculate(machineId) {

        var url = "/cryptoCoin/dataCompare/CryptoCoinDailyDataComparetor";

        var markDate = $("input[machineId='"+machineId+"'][name='markDate']").val();
        var getCoinCounting = $("input[machineId='"+machineId+"'][name='getCoinCounting']").val();

        var jsonOutput = {
          machineId : machineId,
          markDate : markDate,
          getCoinCounting : getCoinCounting
        };

        console.log(jsonOutput);

        // $.ajax({
        //   type : "POST",
        //   url : url,
        //   data: JSON.stringify(jsonOutput),
        //   dataType: 'json',
        //   contentType: "application/json",
        //   beforeSend: function(xhr) {
        //     xhr.setRequestHeader(csrfHeader, csrfToken);
        //   },
        //   timeout: 15000,
        //   success:function(data){
        //     $("#result").html("");
        //     var subData;
        //
        //   },
        //   error:function(e){
        //     $("#result").text(e);
        //   }
        // });
      };

    });

  </script>
</footer>
</html>
