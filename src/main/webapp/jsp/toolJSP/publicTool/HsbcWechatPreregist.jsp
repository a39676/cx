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
          <div class="input-group-prepend">
            <span class="input-group-text">开户行</span>
          </div>
          <select class="" name="" id="openAccountBranchSelector">
            <option value="北京">北京</option>
            <option value="揭阳">揭阳</option>
            <option value="重庆">重庆</option>
            <option value="江门">江门</option>
            <option value="云浮">云浮</option>
            <option value="唐山">唐山</option>
            <option value="阳江">阳江</option>
            <option value="哈尔滨">哈尔滨</option>
            <option value="清远">清远</option>
            <option value="天津">天津</option>
            <option value="成都">成都</option>
            <option value="太原">太原</option>
            <option value="深圳">深圳</option>
            <option value="惠州">惠州</option>
            <option value="珠海">珠海</option>
            <option value="汕尾">汕尾</option>
            <option value="青岛">青岛</option>
            <option value="佛山">佛山</option>
            <option value="苏州">苏州</option>
            <option value="潮州">潮州</option>
            <option value="河源">河源</option>
            <option value="杭州">杭州</option>
            <option value="郑州">郑州</option>
            <option value="肇庆">肇庆</option>
            <option value="无锡">无锡</option>
            <option value="沈阳">沈阳</option>
            <option value="广州">广州</option>
            <option value="大连">大连</option>
            <option value="宁波">宁波</option>
            <option value="中山">中山</option>
            <option value="茂名">茂名</option>
            <option value="湛江">湛江</option>
            <option value="上海">上海</option>
            <option value="梅州">梅州</option>
            <option value="韶关">韶关</option>
            <option value="西安">西安</option>
            <option value="东莞">东莞</option>
            <option value="南京">南京</option>
            <option value="武汉">武汉</option>
            <option value="厦门">厦门</option>
            <option value="汕头">汕头</option>
            <option value="合肥">合肥</option>
            <option value="济南">济南</option>
          </select>

          <div class="input-group-prepend">
            <span class="input-group-text">居住地开户</span>
          </div>
          <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
            <input type="radio" class="btn-check" name="btnradio" id="openAccountInLivingCity" autocomplete="off" checked>
            <label class="btn btn-outline-primary" for="openAccountInLivingCity">是</label>
            <input type="radio" class="btn-check" name="btnradio" id="notOpenAccountInLivingCity" autocomplete="off">
            <label class="btn btn-outline-primary" for="notOpenAccountInLivingCity">否(将随机选择居住市)</label>
          </div>
          
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
          <input type="text" class="form-control" name="" id="idNumber" value="${randomIdNumber}">
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
          <input type="text" class="form-control" value="${randomPhoneNumber}" name="" id="phoneNumber">
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">LastName(姓)</span>
          </div>
          <input type="text" class="form-control" name="" id="customerLastName" value="${randomLastName}" maxlength="2">
          <div class="input-group-prepend">
            <span class="input-group-text">FirstName(名)</span>
          </div>
          <input type="text" class="form-control" name="" id="customerFirstName" value="${randomFirstName}" maxlength="2">
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
        <button class="btn btn-sm btn-success" type="button" name="button" id="getRandomIdData">随机生成身份信息</button>
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
      
      $("#getRandomIdData").click(function () {
        getRandomIdData();
      });

      function getRandomIdData() {
        var url = "/publicTool/hsbc/getRandomIdData";

        var idNumber = $("#idNumber");
        var customerFirstName = $("#customerFirstName");
        var customerLastName = $("#customerLastName");
        var phoneNumber = $("#phoneNumber");
        
        var jsonOutput = {
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
            idNumber.val(datas.randomIdNumber);
            customerFirstName.val(datas.randomFirstname);
            customerLastName.val(datas.randomLastname);
            phoneNumber.val(datas.randomPhoneNumber);
          },
          error: function(datas) {
            $("#result").text(datas.message);
          }
        });
      }


      $("#insertTask").click(function () {
        $("#result").text("");
        insertTask();
      });

      function insertTask() {
        var url = "/publicTool/hsbc/hsbcWechatPreregist";

        var idType = $("#idTypeSelector option:selected").val();
        var areaType = $("#areaTypeSelector option:selected").val();
        var areaTypeName = $("#areaTypeSelector option:selected").attr("cnName");
        var cityNameOfOpeningAccountBranch = $("#openAccountBranchSelector option:selected").attr("value");
        var phoneAreaTypeCode = $("#phoneAreaTypeSelector option:selected").val();
        var phoneAreaTypeName = $("#phoneAreaTypeSelector option:selected").attr("cnName");
        var idNumber = $("#idNumber").val();
        var phoneNumber = $("#phoneNumber").val();
        var apkDownloadPassword = $("#apkDownloadPassword").val();
        var customerFirstName = $("#customerFirstName").val();
        var customerLastName = $("#customerLastName").val();
        var openAccountInLivingCity = $("#openAccountInLivingCity");

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
          "openAccountInLivingCity":openAccountInLivingCity.is(':checked'),
          "cityNameOfOpeningAccountBranch":cityNameOfOpeningAccountBranch,
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
