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
    <table class="table table-striped table-dark">
      <thead class="thead-dark">
        <tr>
          <td>noticeContent</td> <%-- noticeContent --%>
          <td>repeat</td> <%-- repeat setting --%>
          <td>noticeTime</td> <%-- noticeTime --%>
          <td>validTime</td> <%-- validTime --%>
          <td>isLunarCalendar</td> <%-- isLunarCalendar --%>
          <td>isStrongNotice</td> <%-- isStrongNotice --%>
          <td>pre notice repeat</td> <%-- pre notice repeat setting --%>
          <td>preNoticeCount</td>
          <td></td>
        </tr>
      </thead>
      <tbody id="noticeVOList">
        <c:forEach items="${noticeVOList}" var="noticeVO">
          <tr class="noticeVO">
            <td>
              <input name="noticeContent" noticePK="${noticeVO.pk}" type="text"
                value="${noticeVO.noticeContent}">
            </td>
            <td>
              <input type="number" name="repeatTimeRange" noticePK="${noticeVO.pk}"
              value="${noticeVO.repeatTimeRange}" style="width: 80px;">
              <select noticePK="${noticeVO.pk}" name="repeatTimeUnit">
                <option value="${noticeVO.repeatTimeUnit}">${noticeVO.repeatTimeUnitName}</option>
                <c:forEach items="${timeUnitType}" var="timeUnitType">
                  <option value="${timeUnitType.code}">${timeUnitType.cnName}${timeUnitType.name}</option>
                </c:forEach>
              </select>
            </td>
            <td>
              <c:set var = "tmpDate" value = "${fn:substring(noticeVO.noticeTime, 0, 10)}" />
              <c:set var = "tmpTime" value = "${fn:substring(noticeVO.noticeTime, 11, 19)}" />
              <input type="date" noticePK="${noticeVO.pk}" name="noticeDate" value="${tmpDate}">
              <input type="time" noticePK="${noticeVO.pk}" name="noticeTime" value="${tmpTime}" step="1">
            </td>
            <td>
              <c:set var = "tmpDate" value = "${fn:substring(noticeVO.validTime, 0, 10)}" />
              <c:set var = "tmpTime" value = "${fn:substring(noticeVO.validTime, 11, 19)}" />
              <input type="date" noticePK="${noticeVO.pk}" name="validDate" value="${tmpDate}">
              <input type="time" noticePK="${noticeVO.pk}" name="validTime" value="${tmpTime}" step="1">
            </td>
            <td>
              <c:if test="${noticeVO.isLunarCalendar}">
                <input type="checkbox" noticePK="${noticeVO.pk}" name="isLunarNotice" checked="checked">
              </c:if>
              <c:if test="${noticeVO.isLunarCalendar == false}">
                <input type="checkbox" noticePK="${noticeVO.pk}"name="isLunarNotice" >
              </c:if>
            </td>
            <td>
              <c:if test="${noticeVO.strongNotice}">
                <input type="checkbox" noticePK="${noticeVO.pk}" name="isStrongNotice" checked="checked">
                </c:if>
                <c:if test="${noticeVO.strongNotice == false}">
                  <input type="checkbox" noticePK="${noticeVO.pk}"name="isStrongNotice" >
                  </c:if>
                </td>
            <td>
              <input type="number" name="preNoticeRepeatTimeRange" noticePK="${noticeVO.pk}"
              value="${noticeVO.preNoticeRepeatTimeRange}" style="width: 80px;">
              <select name="preNoticeTimeUnitOfNotice" noticePK="${noticeVO.pk}">
                <option value="${noticeVO.preNoticeRepeatTimeUnit}">${noticeVO.preNoticeRepeatTimeUnitName}</option>
                <c:forEach items="${timeUnitType}" var="timeUnitType">
                  <option value="${timeUnitType.code}">${timeUnitType.cnName}${timeUnitType.name}</option>
                </c:forEach>
              </select>
            </td>
            <td>
              <input type="" name="preNoticeRepeatCount" noticePK="${noticeVO.pk}"
              value="${noticeVO.preNoticeRepeatCount}" style="width: 80px;">
            </td>
            <td>
              <button name="edit" noticePK="${noticeVO.pk}">修改</button>
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
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("button[name='delete']").click(function () {
        var pk = $(this).attr("noticePK");
        deleteNotice(pk);
      })

      $("button[name='edit']").click(function () {
        var pk = $(this).attr("noticePK");
        editNotice(pk);
      })

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

      function editNotice(pk) {
        var url = "/tool/canlendarNotice/editNotice";

        var noticeContent = $("input[noticePK='"+pk+"'][name='noticeContent']").val();
        var validTime = $("input[noticePK='"+pk+"'][name='validDate']").val()
        + " " + $("input[noticePK='"+pk+"'][name='validTime']").val();
        var repeatTimeRange = $("input[noticePK='"+pk+"'][name='repeatTimeRange']").val();
        var repeatTimeUnit = $("select[noticePK='"+pk+"'][name='repeatTimeUnit'] option:selected").val();
        var isLunarNotice = $("input[noticePK='"+pk+"'][name='isLunarNotice']").is(":checked");
        var isStrongNotice = $("input[noticePK='"+pk+"'][name='isStrongNotice']").is(":checked");
        var noticeTime;
        var lunarNoticeTime;
        if(isLunarNotice){
          lunarNoticeTime = $("input[noticePK='"+pk+"'][name='noticeDate']").val() + " " + $("input[noticePK='"+pk+"'][name='noticeTime']").val();
        } else{
          noticeTime = $("input[noticePK='"+pk+"'][name='noticeDate']").val() + " " + $("input[noticePK='"+pk+"'][name='noticeTime']").val();
        }
        var preNoticeRepeatTimeRange = $("input[noticePK='"+pk+"'][name='preNoticeRepeatTimeRange']").val();
        var preNoticeRepeatTimeUnit = $("select[noticePK='"+pk+"'][name='preNoticeTimeUnitOfNotice'] option:selected").val();
        var preNoticeCount = $("input[noticePK='"+pk+"'][name='preNoticeRepeatCount']").val();

        var jsonOutput = {
          pk : pk,
          noticeContent : noticeContent,
          noticeTime : noticeTime,
          lunarNoticeTime : lunarNoticeTime,
          validTime : validTime,
          isLunarNotice : isLunarNotice,
          isStrongNotice : isStrongNotice,
          repeatTimeUnit : repeatTimeUnit,
          repeatTimeRange : repeatTimeRange,
          preNoticeRepeatTimeUnit : preNoticeRepeatTimeUnit,
          preNoticeRepeatTimeRange : preNoticeRepeatTimeRange,
          preNoticeCount : preNoticeCount,
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
