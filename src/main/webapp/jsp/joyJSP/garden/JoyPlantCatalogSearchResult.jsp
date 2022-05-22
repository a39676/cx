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
      <table class="table table-hover table-bordered table-striped table-light">
        <thead class="thead-dark">
          <tr>
            <th style="text-align: center; vertical-align: middle;">名称</th>
            <th style="text-align: center; vertical-align: middle;">创造者</th>
            <th style="text-align: center; vertical-align: middle;">类型</th>
          </tr>
        </thead>
        <c:forEach items="${plantList}" var="plant">
          <tr class="table-primary">
            <td style="text-align: center; vertical-align: middle;">
              <input type="text" name="" value="${plant.plantName}" plantPK="${plant.pk}">
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <input type="text" name="" value="${plant.creatorName}" plantPK="${plant.pk}">
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <select class="" name="" plantPK="${plant.pk}">
                <option value="${plant.plantType.code}">${plant.plantType.cnName}</option>
                <c:forEach items="${plantTypeList}" var="plantType">
                  <option value="${plantType.code}">${plantType.cnName}</option>
                </c:forEach>
              </select>
            </td>
          </tr>
          <tr>
            <td style="text-align: center; vertical-align: middle;">
              阶段名称
            </td>
            <td style="text-align: center; vertical-align: middle;">
              图片
            </td>
            <td style="text-align: center; vertical-align: middle;">
              持续时间
            </td>
          </tr>
            <c:forEach items="${plant.growingOptionList}" var="growingOption">
              <tr>
                <td style="text-align: center; vertical-align: middle;">
                  ${growingOption.stageName}
                </td>
                <td style="text-align: center; vertical-align: middle;">
                  ${growingOption.stageImgUrl}
                  <img src="${growingOption.stageImgUrl}" alt="">
                </td>
                <td style="text-align: center; vertical-align: middle;">
                  ${growingOption.stageLivingMinute}
                </td>
              </tr>
            </c:forEach>
        </c:forEach>
      </table>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

    });

  </script>
</footer>
</html>
