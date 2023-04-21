<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

  <head>
    <%@ include file="../baseElementJSP/normalHeader.jsp" %>
    <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  </head>

  <div class="container-fluid" style="width: 100%; max-width: 600px;">
    <div class="row">
      <div class="col-sm-12">
        <table class="table">
          <tbody>
            <c:forEach items="${imgVoList}" var="imgVO" varStatus="status">
              <c:if test="${status.index % 3 == 0}">
                <tr>
              </c:if>
              <td name="imgTd" imgPk="${imgVO.imgPk}">
                <c:url value="/image/getThumbnail" var="url">
                  <c:param name="imgPK" value="${imgVO.imgPk}" />
                  <c:param name="width" value="200" />
                  <c:param name="height" value="200" />
                </c:url>
                <img src="${url}"><br>
                <label>${imgVO.jobPk}</label>
                <button class="btn btn-sm btn-danger" name="removeFromImageWall" imgPk="${imgVO.imgPk}">
                  Remove
                </button>
              </td>
              <c:if test="${status.index % 3 == 2 or status.last}">
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    
  </div>

  <script type="text/javascript">
    
    $(document).ready(function() {

      $("button[name='removeFromImageWall']").click(function() {
        removeFromImageWall($(this).attr("imgPk"));
      })

      function removeFromImageWall(imgPk){
        var url = "/aiArtManager/removeFromImageWall";

        var jsonOutput = {
          pk:imgPk,
        };

        console.log(jsonOutput);
  
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
            console.log(datas);
            if (datas.code == 0) {
              $("td[name='imgTd'][imgPk='"+imgPk+"']").hide();
            } else {

            }
          },
          error: function(datas) {
            
          }
        });
      }

    });
  </script>

</html>