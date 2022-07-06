<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">
              蒲公英 APP 下载密码
            </span>
          </div>
          <input type="password" class="form-control" name="" id="apkDownloadPassword">
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">国籍(国家/地区)</span>
          </div>
          <select class="" name="" id="areaTypeSelector">
            <c:forEach items="${internationalityTypeList}" var="internationalityType">
              <option value="${internationalityType.code}" cnName="${internationalityType.cnName}">${internationalityType.cnName}</option>
            </c:forEach>
          </select>
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">证件类型</span>
          </div>
          <select class="" name="" id="idTypeSelector">
            <c:forEach items="${idTypeList}" var="idType">
              <option value="${idType.id}">${idType.cnName}</option>
            </c:forEach>
          </select>
          <div class="input-group-prepend">
            <span class="input-group-text">证件号码</span>
          </div>
          <input type="text" class="form-control" name="" id="idNumber">
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">手机区号</span>
          </div>
          <select class="" name="" id="phoneAreaTypeSelector">
            <c:forEach items="${internationalityTypeList}" var="internationalityType">
              <option value="${internationalityType.code}" cnName="${internationalityType.cnName}">(${internationalityType.code})${internationalityType.cnName}</option>
            </c:forEach>
          </select>
          <div class="input-group-prepend">
            <span class="input-group-text">手机号</span>
          </div>
          <input type="text" class="form-control" name="" id="phoneNumber">
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">LastName(姓)</span>
          </div>
          <input type="text" class="form-control" name="" id="customerLastName" value="测" maxlength="2">
          <div class="input-group-prepend">
            <span class="input-group-text">FirstName(名)</span>
          </div>
          <input type="text" class="form-control" name="" id="customerFirstName" value="试" maxlength="2">
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <span class="badge badge-warning"><h6>每个预注册任务可能耗时数分钟, 可能因为证件号码无效 / 重复手机号码 / 错误数据组合导致失败, 请使用其他参数重试</h6></span>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button class="btn btn-sm btn-primary" type="button" name="button" id="insertTask">加入预注册任务</button>
        <button class="btn btn-sm btn-primary" type="button" name="button" id="refreshReport">刷新报告列表</button>
      </div>
    </div>

    <hr>

    <div class="row">
      <div class="col-md-12">
        <span class="badge badge-primary">
          <h6>Result: <span id="result"></span></h6>
        </span>
      </div>
    </div>

    <hr>

    <div class="row">
      <div class="col-md-12" id="reportSummary">

      </div>
    </div>
  </div>

  <!-- SCIPTS -->
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#insertTask").click(function () {
        $("#result").text("");
        insertTask();
      });

      function insertTask() {
        var url = "/publicTool/hsbc/hsbcWechatPreregist";

        var idType = $("#idTypeSelector option:selected").val();
        var areaType = $("#areaTypeSelector option:selected").val();
        var areaTypeName = $("#areaTypeSelector option:selected").attr("cnName");
        var phoneAreaTypeCode = $("#phoneAreaTypeSelector option:selected").val();
        var phoneAreaTypeName = $("#phoneAreaTypeSelector option:selected").attr("cnName");
        var idNumber = $("#idNumber").val();
        var phoneNumber = $("#phoneNumber").val();
        var apkDownloadPassword = $("#apkDownloadPassword").val();
        var customerFirstName = $("#customerFirstName").val();
        var customerLastName = $("#customerLastName").val();

        var jsonOutput = {
          "idNumber":idNumber,
          "phoneNumber":phoneNumber,
          "apkDownloadPassword":apkDownloadPassword,
          "idType":idType,
          "areaType":areaType,
          "areaName":areaTypeName,
          "phoneAreaType":phoneAreaTypeCode,
          "phoneAreaName":phoneAreaTypeName,
          "customerFirstName":customerFirstName,
          "customerLastName":customerLastName,
        };

        $.ajax({
          type : "POST",
          async : true,
          url : url,
          data: JSON.stringify(jsonOutput),
          cache : false,
          contentType: "application/json",
          dataType: "json",
          timeout:50000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            $("#result").text(datas.message);
            findReportSummary();
          },
          error: function(datas) {
            $("#result").text(datas.message);
          }
        });
      }


      $("#refreshReport").click(function () {
        findReportSummary();
      });

      function findReportSummary() {
        var url = "/publicTool/hsbc/getReportSummaryPage";

        var apkDownloadPassword = $("#apkDownloadPassword").val();

        var jsonOutput = {
          "apkDownloadPassword":apkDownloadPassword,
        };

        $.ajax({
          type : "POST",
          async : true,
          url : url,
          data: JSON.stringify(jsonOutput),
          cache : false,
          contentType: "application/json",
          // dataType: "json",
          timeout:50000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            $("#reportSummary").html(datas);
          },
          error: function(datas) {
            $("#reportSummary").html(datas);
          }
        });
      }

    })
  </script>
</body>

<footer>

</footer>

</html>
