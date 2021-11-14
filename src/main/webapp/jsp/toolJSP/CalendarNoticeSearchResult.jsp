<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">


<div class="row">
  <div class="col-md-12">
    <table class="table table-striped table-dark">
      <thead class="thead-dark">
        <tr>
          <td>接收人</td> <%-- noticeReciver --%>
          <td>数据时间范围</td> <%-- timeUnitOfDataWatch --%>
          <td>通知间隔</td> <%-- timeUnitOfNoticeInterval --%>
          <td>加密币 & 兑换币</td> <%-- cryptoCoin currency --%>
          <td>监控最高价</td> <%-- maxPrice --%>
          <td>监控最低价</td> <%-- minPrice --%>
          <td>监控涨跌速(%)</td> <%-- fluctuactionSpeedPercentage --%>
          <td>剩余提醒次数</td> <%-- noticeCount --%>
          <td>上次提醒时间</td> <%-- noticeTime --%>
          <td>下次提醒时间</td> <%-- nextNoticeTime --%>
          <td>有效期至</td> <%-- validTime --%>
          <td></td>
        </tr>
      </thead>
      <tbody id="noticeVOList">
        <c:forEach items="${noticeVOList}" var="noticeVO">
          <tr class="noticeVO" noticePK="${noticeVO.pk}">
            <td noticePK="${noticeVO.pk}" name="noticeReciver">${noticeVO.noticeReciver}</td>
            <td>
              <input type="number" noticePK="${noticeVO.pk}" name="timeRangeOfDataWatch"
              value="${noticeVO.timeRangeOfDataWatch}" style="width: 80px;">
              <select noticePK="${noticeVO.pk}" name="timeUnitOfDataWatch">
                <option value="${noticeVO.timeUnitOfDataWatch}">${noticeVO.timeUnitOfDataWatchName}</option>
                <c:forEach items="${timeUnitType}" var="timeUnitType">
                  <option value="${timeUnitType.code}">${timeUnitType.cnName}</option>
                </c:forEach>
              </select>
            </td>
            <td>
              <input type="number" noticePK="${noticeVO.pk}" name="timeRangeOfNoticeInterval"
              value="${noticeVO.timeRangeOfNoticeInterval}" style="width: 80px;">
              <select noticePK="${noticeVO.pk}" name="timeUnitOfNoticeInterval">
              <option value="${noticeVO.timeUnitOfNoticeInterval}">${noticeVO.timeUnitOfNoticeIntervalName} </option>
                <c:forEach items="${timeUnitType}" var="timeUnitType">
                  <option value="${timeUnitType.code}">${timeUnitType.cnName}</option>
                </c:forEach>
              </select>
            </td>
            <td>
              <input type="text" noticePK="${noticeVO.pk}" name="cryptoCoinType"
              value="${noticeVO.cryptoCoinName}" style="width: 60px;">
              <select noticePK="${noticeVO.pk}" name="currencyCode">
                <option value="${noticeVO.currencyCode}">${noticeVO.currencyName}</option>
                <c:forEach items="${currencyType}" var="subCurrencyType">
                  <option value="${subCurrencyType.code}">${subCurrencyType.name}</option>
                </c:forEach>
              </select>
            </td>
            <td>
              <input type="" noticePK="${noticeVO.pk}" name="maxPrice"
              value="${noticeVO.maxPrice}" style="width: 80px;">
            </td>
            <td>
              <input type="" noticePK="${noticeVO.pk}" name="minPrice"
              value="${noticeVO.minPrice}" style="width: 80px;">
            </td>
            <td>
              <input type="number" noticePK="${noticeVO.pk}" name="fluctuactionSpeedPercentage"
              value="${noticeVO.fluctuactionSpeedPercentage}" style="width: 50px;">
            </td>
            <td>
              <input type="number" noticePK="${noticeVO.pk}" name="noticeCount"
              value="${noticeVO.noticeCount}" style="width: 80px;">
            </td>
            <td>
              ${noticeVO.noticeTime}
            </td>
            <td>
              <c:set var = "tmpDate" value = "${fn:substring(noticeVO.nextNoticeTime, 0, 10)}" />
              <c:set var = "tmpTime" value = "${fn:substring(noticeVO.nextNoticeTime, 11, 19)}" />
              <input type="date" name="nextNoticeDate" noticePK="${noticeVO.pk}" value="${tmpDate}">
              <input type="time" name="nextNoticeTime" noticePK="${noticeVO.pk}" value="${tmpTime}">
            </td>
            <td>
              <c:set var = "tmpDate" value = "${fn:substring(noticeVO.validTime, 0, 10)}" />
              <c:set var = "tmpTime" value = "${fn:substring(noticeVO.validTime, 11, 19)}" />
              <input type="date" name="validDate" noticePK="${noticeVO.pk}" value="${tmpDate}">
              <input type="time" name="validTime" noticePK="${noticeVO.pk}" value="${tmpTime}">
            </td>
            <td>
              <button name="modify" noticePK="${noticeVO.pk}">修改</button>
              <button name="delete" noticePK="${noticeVO.pk}">删除</button>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>


</div>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("button[name='delete']").click(function () {
        var pk = $(this).attr("noticePK");
        deleteNotice(pk);
      })

      $("button[name='modify']").click(function () {
        var pk = $(this).attr("noticePK");
        updateNotice(pk);
      })

      function deleteNotice(pk) {
        var url = "/cryptoCoin/deleteNotice";
        var jsonOutput = {
          pk : pk,
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
      }

      function updateNotice(pk) {
        var url = "/cryptoCoin/updateNotice";

        var cryptoCoinType = $("input[noticePK='"+pk+"'][name='cryptoCoinType']").val();
        var currencyCode = $("select[noticePK='"+pk+"'][name='currencyCode'] option:selected").val();

        var timeRangeOfDataWatch = $("input[noticePK='"+pk+"'][name='timeRangeOfDataWatch']").val();
        var timeUnitOfDataWatch = $("select[noticePK='"+pk+"'][name='timeUnitOfDataWatch'] option:selected").val();

        var timeRangeOfNoticeInterval = $("input[noticePK='"+pk+"'][name='timeRangeOfNoticeInterval']").val();
        var timeUnitOfNoticeInterval = $("select[noticePK='"+pk+"'][name='timeUnitOfNoticeInterval'] option:selected").val();

        var maxPrice = $("input[noticePK='"+pk+"'][name='maxPrice']").val();
        var minPrice = $("input[noticePK='"+pk+"'][name='minPrice']").val();
        var fluctuactionSpeedPercentage = $("input[noticePK='"+pk+"'][name='fluctuactionSpeedPercentage']").val();
        var noticeCount = $("input[noticePK='"+pk+"'][name='noticeCount']").val();

        var nextNoticeDateTime = $("input[noticePK='"+pk+"'][name='nextNoticeDate']").val()
        + " " + $("input[noticePK='"+pk+"'][name='nextNoticeTime']").val();
        var validDateTime = $("input[noticePK='"+pk+"'][name='validDate']").val()
        + " " + $("input[noticePK='"+pk+"'][name='validTime']").val();

        var jsonOutput = {
          pk : pk,
          cryptoCoinType : cryptoCoinType,
          currencyCode : currencyCode,
          maxPrice : maxPrice,
          minPrice : minPrice,
          timeRangeOfDataWatch : timeRangeOfDataWatch,
          timeUnitOfDataWatch : timeUnitOfDataWatch,
          timeRangeOfNoticeInterval : timeRangeOfNoticeInterval,
          timeUnitOfNoticeInterval : timeUnitOfNoticeInterval,
          fluctuactionSpeedPercentage : fluctuactionSpeedPercentage,
          noticeCount : noticeCount,
          nextNoticeTime : nextNoticeDateTime,
          validTime : validDateTime,
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
      }

    });

  </script>
</footer>
</html>
