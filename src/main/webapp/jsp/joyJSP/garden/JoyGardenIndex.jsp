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
      ${gardenInfo}
    </div>
  </div>

  <div class="row">
    <div class="col-md-3">
    </div>
    <div class="col-md-6">
      ${landVoList}
      <table class="table table-bordered">
        <c:forEach items="${fieldVoList}" var="field" varStatus="loop">
          <c:if test="${(loop.index + 1) % 3 == 1}">
            <tr>
          </c:if>
          <td>
            <%-- <span>${field}</span> --%>
            <span>${field.landType}, ${field.landLevel}</span>
          </td>
          <c:if test="${(loop.index + 1) % 3 == 0}">
            </tr>
          </c:if>
        </c:forEach>
        <c:if test="${canCreateNewField}">
          <tr id="createNewFieldLandTR">
            <td>
              <span id="createNewFieldLand">创建新种植地</span>
            </td>
          </tr>
        </c:if>
      </table>
    </div>
    <div class="col-md-3">
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <span id="createFieldLandResult"></span>
    </div>
  </div>


</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#createNewFieldLand").click(function () {
        createNewFieldLand();
      });

      function createNewFieldLand() {
        var url = "/joy/garden/createNewFieldLand";

        var jsonOutput = {};

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
              $("#createFieldLandResult").text("创建成功");
            } else {
              $("#createFieldLandResult").text(data.message);
            }
            if(data.message == "MAX"){
              $("#createNewFieldLandTR").hide();
            }
          },
          error:function(e){
            $("#createFieldLandResult").text("出现异常,请联系管理员");
          }
        });
      }
    });

  </script>
</footer>
</html>
