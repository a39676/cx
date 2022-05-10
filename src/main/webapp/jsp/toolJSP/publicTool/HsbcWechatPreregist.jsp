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
            <span class="input-group-text">身份证</span>
          </div>
          <input type="text" class="form-control" name="" id="idNumber">
        </div>
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">手机号</span>
          </div>
          <input type="text" class="form-control" name="" id="phoneNumber">
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <span>每个预注册任务可能耗时数分钟, 可能因为证件号码无效 / 重复手机号码导致失败, 请使用其他参数重试</span>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button type="button" name="button"id="insertTask">加入预注册任务</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <span id="result"></span>
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

        var idNumber = $("#idNumber").val();
        var phoneNumber = $("#phoneNumber").val();
        var apkDownloadPassword = $("#apkDownloadPassword").val();

        var jsonOutput = {
          "idNumber":idNumber,
          "phoneNumber":phoneNumber,
          "apkDownloadPassword":apkDownloadPassword,
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
          },
          error: function(datas) {
            $("#result").text(datas.message);
          }
        });
      }

    })
  </script>
</body>

<footer>

</footer>

</html>
