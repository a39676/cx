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
      <table class="table table-bordered" style="" id="shopDetailTable" startPK="" endPK="">
        <c:forEach items="${seedVoList}" var="seed" varStatus="loop">
          <c:if test="${(loop.index + 1) % 4 == 1}">
            <tr>
          </c:if>
          <td>
            <span>${seed.plantName},${seed.stageName}</span>
            <img src="${seed.imgUrlPath}" alt="${seed.plantName}">
          </td>
          <c:if test="${(loop.index + 1) % 4 == 0}">
            </tr>
          </c:if>
        </c:forEach>
      </table>
      <button type="button" name="button" class="btn btn-sm btn-primary" id="lastPage">上一页</button>
      <button type="button" name="button" class="btn btn-sm btn-primary" id="nextPage">下一页</button>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#lastPage").click(function () {
        seedSearchWithPage(false);
      });
      $("#nextPage").click(function () {
        seedSearchWithPage(true);
      });

      function seedSearchWithPage(isNextPage) {
        var url = "/joy/garden/shop/seedSearchView";

        var jsonOutput = {
          isNextPage:isNextPage,
        };

        if(isNextPage){
          jsonOutput["endPK"] = "${endPK}";
        } else {
          jsonOutput["startPK"] = "${startPK}";
        }

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
            $("#shopDetail").html(data);
          },
          error:function(e){
          }
        });
      }
    });

  </script>
</footer>
</html>
