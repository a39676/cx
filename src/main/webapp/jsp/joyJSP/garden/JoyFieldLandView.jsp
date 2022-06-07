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

  <div class="col-sm-3">
  </div>
  <div class="col-sm-6">
    <table class="table table-bordered">
      <tr>
        <td class="fieldlandTD" landPK="${fieldVoList[0].pk}" landLevel="${fieldVoList[0].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[0] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[0] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
        <td class="fieldlandTD" landPK="${fieldVoList[1].pk}" landLevel="${fieldVoList[1].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[1] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[1] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
        <td class="fieldlandTD" landPK="${fieldVoList[2].pk}" landLevel="${fieldVoList[2].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[2] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[2] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
      </tr>
      <tr>
        <td class="fieldlandTD" landPK="${fieldVoList[3].pk}" landLevel="${fieldVoList[3].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[3] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[3] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
        <td class="fieldlandTD" landPK="${fieldVoList[4].pk}" landLevel="${fieldVoList[4].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[4] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[4] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
        <td class="fieldlandTD" landPK="${fieldVoList[5].pk}" landLevel="${fieldVoList[5].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[5] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[5] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
      </tr>
      <tr>
        <td class="fieldlandTD" landPK="${fieldVoList[6].pk}" landLevel="${fieldVoList[6].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[6] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[6] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
        <td class="fieldlandTD" landPK="${fieldVoList[7].pk}" landLevel="${fieldVoList[7].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[7] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[7] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
        <td class="fieldlandTD" landPK="${fieldVoList[8].pk}" landLevel="${fieldVoList[8].landLevel}"
          style="text-align: center; vertical-align: middle;">
          <c:if test="${fieldVoList[8] != null}">
            <img src='data:image/png;base64,${fieldImgSrc}' alt="">
          </c:if>
          <c:if test="${fieldVoList[8] == null}">
            <img src='data:image/png;base64,${fieldNotDevImgSrc}' alt="">
          </c:if>
        </td>
      </tr>
    </table>
  </div>
  <div class="col-sm-3">
    <c:if test="${canCreateNewField}">
      <button type="button" name="" class="btn btn-sm btn-primary" id="createNewFieldLand">创建新种植地</button>
    </c:if>
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
              getFieldLandView();
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

      function getFieldLandView() {
        var url = "/joy/garden/getFieldLandView";

        var jsonOutput = {};

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
            $("#fieldLandRow").html(data);
          },
          error:function(e){
          }
        });
      }
    });

  </script>
</footer>
</html>
