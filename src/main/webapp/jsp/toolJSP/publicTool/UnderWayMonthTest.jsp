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
      <div class="col-md-6">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">
              用户名
            </span>
          </div>
          <input type="text" class="form-control" name="" id="username">
        </div>
      </div>

      <div class="col-md-6">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">
              密码
            </span>
          </div>
          <input type="password" class="form-control" name="" id="pwd">
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <button class="btn btn-sm btn-primary" type="button" name="button" id="insertTask">加入任务</button>
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
        var url = "/publicTool/freeYourTime/addMonthTest";

        var username = $("#username").val();
        var pwd = $("#pwd").val();
        

        var jsonOutput = {
          "username":username,
          "pwd":pwd,
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
