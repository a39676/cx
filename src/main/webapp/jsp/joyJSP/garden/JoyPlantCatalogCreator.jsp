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
      <form name="" novalidate>
        <div class="control-group">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">新植物名称</span>
            </div>
            <input type="text" class="" name="" id="newPlantName"/>
            <div class="input-group-prepend">
              <span class="input-group-text">植物类型</span>
            </div>
            <select class="" name="" id="newPlantType">
              <c:forEach items="${plantTypeList}" var="plantType">
                <option value="${plantType.code}">${plantType.cnName}</option>
              </c:forEach>
            </select>
          </div>
          <button class="btn btn-primary" type="button" name="" id="createNewPlant">Create New Plant</button>
        </div>
      </form>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <span id="createResult"></span>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#createNewPlant").click(function () {
        create();
      });

      function create() {
        var url = "/joy/garden/plantCatalogCreator";

        var newPlantName = $("#newPlantName").val();
        var newPlantType = $("#newPlantType option:selected").val();

        var jsonOutput = {
          plantName : newPlantName,
          plantTypeCode : newPlantType,
        };

        $.ajax({
          type : "POST",
          url : url,
          data: JSON.stringify(jsonOutput),
          // dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            if(data.code == 0){
              $("#createResult").text("创建成功");
            } else {
              $("#createResult").text(data.message);
            }
          },
          error:function(e){
            $("#createResult").text("出现异常,请联系管理员");
          }
        });
      }
    });

  </script>
</footer>
</html>
