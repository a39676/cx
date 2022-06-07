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
      <input type="text" name="" value="" placeholder="请输入新的花园名称" id="newGardenNameInput">
      <button class="btn btn-primary btn-sm" type="button" name="button" id="submit">提交</button>
      <span id="result"></span>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#submit").click(function () {
        create();
      });

      function create() {
        var url = "/joy/garden/createNewGarden";

        var gardenName = $("#newGardenNameInput").val();

        var jsonOutput = {
          gardenName : gardenName,
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
              window.location.href = "/joy/garden/index";
            } else {
              $("#result").text(data.message);
            }
          },
          error:function(e){
            $("#result").text("出现异常,请联系管理员");
          }
        });
      }
    });

  </script>
</footer>
</html>
