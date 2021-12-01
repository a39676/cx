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
          <td>pk</td> <%-- pk --%>
          <td>noticeContent</td> <%-- noticeContent --%>
          <td>repeat</td> <%-- repeat setting --%>
          <td>noticeTime</td> <%-- noticeTime --%>
          <td>validTime</td> <%-- validTime --%>
          <td>isLunarCalendar</td> <%-- isLunarCalendar --%>
          <td>pre notice repeat</td> <%-- pre notice repeat setting --%>
          <td>preNoticeTime</td> <%-- preNoticeTime --%>
          <td>preNoticeCount</td>
          <td></td>
        </tr>
      </thead>
      <tbody id="noticeVOList">
        <c:forEach items="${noticeVOList}" var="noticeVO">
          <tr class="noticeVO">
            <td name="noticePk" noticePK="${noticeVO.pk}">
              <%-- ${noticeVO.pk} --%>
            </td>
            <td name="noticeContent">${noticeVO.noticeContent}</td>
            <td>
              <input type="number" name="repeatTimeRange"
              value="${noticeVO.repeatTimeRange}" style="width: 80px;">
              <select name="timeUnitOfNotice">
                <option value="${noticeVO.repeatTimeUnit}">${noticeVO.repeatTimeUnitName}</option>
                <c:forEach items="${timeUnitType}" var="timeUnitType">
                  <option value="${timeUnitType.code}">${timeUnitType.cnName}${timeUnitType.name}</option>
                </c:forEach>
              </select>
            </td>
            <td>
              <c:set var = "tmpDate" value = "${fn:substring(noticeVO.noticeTime, 0, 10)}" />
              <c:set var = "tmpTime" value = "${fn:substring(noticeVO.noticeTime, 11, 19)}" />
              <input type="date" name="nextNoticeDate" value="${tmpDate}">
              <input type="time" name="nextNoticeTime" value="${tmpTime}">
            </td>
            <td>
              <c:set var = "tmpDate" value = "${fn:substring(noticeVO.validTime, 0, 10)}" />
              <c:set var = "tmpTime" value = "${fn:substring(noticeVO.validTime, 11, 19)}" />
              <input type="date" name="nextNoticeDate" value="${tmpDate}">
              <input type="time" name="nextNoticeTime" value="${tmpTime}">
            </td>
            <td>
              ${noticeVO.isLunarCalendar}
            </td>
            <td>
              <input type="number" name="preNoticeRepeatTimeRange"
              value="${noticeVO.preNoticeRepeatTimeRange}" style="width: 80px;">
              <select name="preNoticeTimeUnitOfNotice">
                <option value="${noticeVO.preNoticeRepeatTimeUnit}">${noticeVO.preNoticeRepeatTimeUnitName}</option>
                <c:forEach items="${timeUnitType}" var="timeUnitType">
                  <option value="${timeUnitType.code}">${timeUnitType.cnName}${timeUnitType.name}</option>
                </c:forEach>
              </select>
            </td>
            <td>
              <c:set var = "tmpDate" value = "${fn:substring(noticeVO.preNoticeTime, 0, 10)}" />
              <c:set var = "tmpTime" value = "${fn:substring(noticeVO.preNoticeTime, 11, 19)}" />
              <input type="date" name="nextNoticeDate" value="${tmpDate}">
              <input type="time" name="nextNoticeTime" value="${tmpTime}">
            </td>
            <td>
              <input type="" name="preNoticeRepeatCount"
              value="${noticeVO.preNoticeRepeatCount}" style="width: 80px;">
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

      // $("button[name='modify']").click(function () {
      //   var pk = $(this).attr("noticePK");
      //   updateNotice(pk);
      // })

      function deleteNotice(pk) {
        var url = "/tool/canlendarNotice/deleteNotice";
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
        // TODO
        // var url = "/cryptoCoin/updateNotice";

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
