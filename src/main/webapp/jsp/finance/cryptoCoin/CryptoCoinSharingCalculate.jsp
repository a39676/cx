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
              <th style="text-align: center; vertical-align: middle;">集群名</th>
              <th style="text-align: center; vertical-align: middle;">对应币</th>
              <th style="text-align: center; vertical-align: middle;">手续费</th>
              <th style="text-align: center; vertical-align: middle;">分割份数</th>
              <th></th>
            </tr>
          </thead>
          <tr>
            <td style="text-align: center; vertical-align: middle;">${machine.machineName}</td>
            <td style="text-align: center; vertical-align: middle;">${machine.coinName}</td>
            <c:set var="handlingFeeRateInPercent"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${machine.handlingFeeRate * 100}" /></c:set>
            <td style="text-align: center; vertical-align: middle;">${handlingFeeRateInPercent}%</td>
            <td style="text-align: center; vertical-align: middle;">${machine.partingCount}</td>
            <td></td>
          </tr>
          <tr class="table-primary">
            <td colspan="2" style="text-align: center; vertical-align: middle;">用户名</td>
            <td style="text-align: center; vertical-align: middle;">佣金</td>
            <td style="text-align: center; vertical-align: middle;">所占份数</td>
            <td></td>
          </tr>
          <c:forEach items="${machine.assistantList}" var="assistant">
            <tr>
              <td colspan="2" style="text-align: center; vertical-align: middle;">${assistant.name}</td>
              <c:set var="commissionFeeRateInPercent"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${assistant.commissionFeeRate * 100}" /></c:set>
              <td style="text-align: center; vertical-align: middle;">
                <input type="number" machinePK="${machine.machinePK}" assistantPK="${assistant.pk}" name="commissionFeeRateInPercent" value="${commissionFeeRateInPercent}">%
              </td>
              <td style="text-align: center; vertical-align: middle;">
                <input type="number" machinePK="${machine.machinePK}" assistantPK="${assistant.pk}" name="partingCount" value="${assistant.partingCount}">
              </td>
              <td style="text-align: center; vertical-align: middle;">
                <button class="btn btn-primary" type="button" name="updateAssistant" assistantPK="${assistant.pk}" machinePK="${machine.machinePK}">修改</button>
                <span name="updateAssistantResult" assistantPK="${assistant.pk}" machinePK="${machine.machinePK}"></span>
              </td>
            </tr>
          </c:forEach>
        </table>
        <form name="calculateAssistant" machinePK="${machine.machinePK}" novalidate>
          <div class="control-group">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text">标记日期</span>
              </div>
              <input type="Date" class="" value="${today}" machinePK="${machine.machinePK}" name="markDate" data-date-format="yyyy-mm-dd"/>
              <div class="input-group-prepend">
                <span class="input-group-text">本期获得币</span>
              </div>
              <input type="number" class="form-control" placeholder="0" machinePK="${machine.machinePK}" name="getCoinCounting" required data-validation-required-message="请输入本期获得币数" value="">
            </div>
            <button machinePK="${machine.machinePK}" class="btn btn-primary" type="button" name="calculate">核算</button>
            <span machinePK="${machine.machinePK}" name="calculateResult"></span>
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
        var machinePK = $(this).attr("machinePK");
        assistantCalculate(machinePK);
      });

      function assistantCalculate(machinePK) {

        var url = "/cryptoCoinSharingCalculate/sharingCalculate";

        var markDate = $("input[machinePK='"+machinePK+"'][name='markDate']").val();
        var getCoinCounting = $("input[machinePK='"+machinePK+"'][name='getCoinCounting']").val();

        var jsonOutput = {
          machinePK : machinePK,
          markDateStr : markDate,
          getCoinCounting : getCoinCounting
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
            if(data.code == 0){
              $("span[name='calculateResult'][machinePK='"+machinePK+"']").text("核算完毕");
            } else {
              $("span[name='calculateResult'][machinePK='"+machinePK+"']").text("核算失败,请联系管理员");
            }
          },
          error:function(e){
            // $("#result").text(e);
          }
        });
      };

      $("button[name='updateAssistant']").click(function () {
        var machinePK = $(this).attr("machinePK");
        var assistantPK = $(this).attr("assistantPK");
        updateAssistant(machinePK, assistantPK);
      });

      function updateAssistant(machinePK, assistantPK) {

        var url = "/cryptoCoinSharingCalculate/updateAssistant";

        var partingCount = $("input[name='partingCount'][assistantPK='"+assistantPK+"'][machinePK='"+machinePK+"']").val();
        var commissionFeeRateInPercent =  $("input[name='commissionFeeRateInPercent'][assistantPK='"+assistantPK+"'][machinePK='"+machinePK+"']").val();

        var jsonOutput = {
          machinePK : machinePK,
          assistantPK : assistantPK,
          partingCount : partingCount,
          commissionFeeRateInPercent : commissionFeeRateInPercent
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
            if(data.code == 0){
              $("span[name='updateAssistantResult'][assistantPK='"+assistantPK+"'][machinePK='"+machinePK+"']").text("修改成功");
            } else {
              $("span[name='updateAssistantResult'][assistantPK='"+assistantPK+"'][machinePK='"+machinePK+"']").text(data.message);
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
