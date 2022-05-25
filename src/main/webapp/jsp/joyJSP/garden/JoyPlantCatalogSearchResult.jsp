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

<div class="row">
  <div class="col-md-12">
    <table class="table table-hover table-bordered table-striped table-light">
      <thead class="thead-dark">
        <tr>
          <th style="text-align: center; vertical-align: middle;">名称</th>
          <th style="text-align: center; vertical-align: middle;">创造者</th>
          <th style="text-align: center; vertical-align: middle;">类型</th>
          <th style="text-align: center; vertical-align: middle;">Image</th>
          <th style="text-align: center; vertical-align: middle;">操作</th>
        </tr>
      </thead>
      <c:forEach items="${plantList}" var="plant">
        <tr class="" name="plantTR" plantPK="${plant.pk}">
          <td style="text-align: center; vertical-align: middle;">
            <input type="text" name="" value="${plant.plantName}" plantPK="${plant.pk}">
          </td>
          <td style="text-align: center; vertical-align: middle;">
            <span plantPK="${plant.pk}">${plant.creatorName}</span>
          </td>
          <td style="text-align: center; vertical-align: middle;">
            <select class="" name="" plantPK="${plant.pk}">
              <option value="${plant.plantType.code}">${plant.plantType.cnName}</option>
              <c:forEach items="${plantTypeList}" var="plantType">
                <option value="${plantType.code}">${plantType.cnName}</option>
              </c:forEach>
            </select>
          </td>
          <td style="text-align: center; vertical-align: middle;">
            <img src="${plant.imgUrl}" alt="" style="width:80px; height:80px;">
          </td>
          <td style="text-align: center; vertical-align: middle;">
            <button type="button" name="editPlantStage" class="btn btn-sm btn-primary" plantPK="${plant.pk}">编辑生长阶段</button>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>

</div>

<div id="stageManagerView">
</div>

<footer>
</footer>

<%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {
    $("button[name='editPlantStage']").click(function () {
      var plantPK = $(this).attr("plantPK");
      showPlantStage(plantPK);
      $("tr[name='plantTR']").removeClass("table-danger");
      $("tr[name='plantTR'][plantPK='"+plantPK+"']").addClass("table-danger");
    });

    function showPlantStage(plantPK) {
      var url = "/joyManager/garden/plantStageManager";

      var jsonOutput = {
        plantPK : plantPK,
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
          $("#stageManagerView").html(data);
        },
        error:function(e){
          $("#stageManagerView").html("出现异常,请联系管理员");
        }
      });
    }
  });

</script>

</html>
