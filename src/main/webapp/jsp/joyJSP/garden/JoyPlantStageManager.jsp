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
              <span class="input-group-text">植物名称: ${plant.plantName}</span>
            </div>
          </div>
          <hr>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段名称</span>
            </div>
            <input type="text" class="" name="newPlantStageName" id="newPlantStageName"/>
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段持续时间(分钟)</span>
            </div>
            <input type="number" class="" name="newPlantStageLivingMinute" min="1" id="newPlantStageLivingMinute"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段图片</span>
            </div>
            <input type="file" name="" accept="image/*" id="newPlantStageImgUpload">
            <img src="" alt="" id="newPlantStageImgPreview" name="newPlantStageImgPreview">
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">是否循环此阶段</span>
            </div>
            <select class="" name="" id="isCycleStage">
              <option value="false">否</option>
              <option value="true">是</option>
            </select>
          </div>
          <hr>
          <button class="btn btn-primary" type="button" name="" id="createNewPlantStage">创建植物生长阶段</button>
          <span id="createResult"></span>
        </div>
      </form>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <table class="table table-hover table-bordered table-striped table-light">
        <thead class="thead-dark">
          <tr>
            <th style="text-align: center; vertical-align: middle;">名称</th>
            <th style="text-align: center; vertical-align: middle;">持续时间(分钟)</th>
            <th style="text-align: center; vertical-align: middle;">Image</th>
            <th></th>
          </tr>
        </thead>
        <c:forEach items="${stageVOList}" var="stageVO">
          <tr class="" name="" stagePK="${stageVO.pk}">
            <td style="text-align: center; vertical-align: middle;">
              <input type="text" stagePK="${stageVO.pk}" name="stageName" value="${stageVO.stageName}">
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <input type="number" min="1" stagePK="${stageVO.pk}" name="livingMinute" value="${stageVO.livingMinute}">
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <img src="${stageVO.imgUrlPath}" alt="">
            </td>
            <td style="text-align: center; vertical-align: middle;">
              <button class="btn btn-sm btn-primary" type="button" name="updateStage" stagePK="${stageVO.pk}">编辑此阶段</button>
              <button class="btn btn-sm btn-primary" type="button" name="updateStageSort" stagePK="${stageVO.pk}" flag="1">提升此阶段排序</button>
              <button class="btn btn-sm btn-primary" type="button" name="updateStageSort" stagePK="${stageVO.pk}" flag="0">降低此阶段排序</button>
              <button class="btn btn-sm btn-danger" type="button" name="deleteStage" stagePK="${stageVO.pk}">删除此阶段</button>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      var plantPK = "${plantPK}";

      $("#newPlantStageImgUpload").change(function () {
        const file = this.files[0];
        if (file){
          let reader = new FileReader();
          reader.onload = function(event){
            $("#newPlantStageImgPreview").attr("src", event.target.result);
            $("#newPlantStageImgPreview").attr("style", "width:80px; hight:80px;")
          }
          reader.readAsDataURL(file);
        }
      });

      $("#createNewPlantStage").click(function () {
        var url = "/joyManager/garden/createPlantStage";

        var newStageNameInput = $("#newPlantStageName");
        var newStageName = newStageNameInput.val();
        if(newStageName.length < 1){
          newStageNameInput.attr("style", "border:1px solid #ff0000");
          return;
        } else {
          newStageNameInput.attr("style", "");
        }
        var livingMinuteInput = $("#newPlantStageLivingMinute");
        var livingMinute = livingMinuteInput.val();
        if(livingMinute.length < 1){
          livingMinuteInput.attr("style", "border:1px solid #ff0000");
          return;
        } else {
          livingMinuteInput.attr("style", "");
        }

        var imgSrc = $("#newPlantStageImgPreview").attr("src");
        var cycleStage = $("#isCycleStage option:selected").val();

        var jsonOutput = {
          plantPK : "${plantPK}",
          stageName : newStageName,
          stageImgSrc : imgSrc,
          livingMinute : livingMinute,
          cycleStage : cycleStage,
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
              $("#createResult").text("新建成功");
            } else {
              $("#createResult").text(data.message);
            }
            newStageNameInput.attr("style", "");
            livingMinuteInput.attr("style", "");
          },
          error:function(e){
          }
        });

      });

      $("button[name='deleteStage']").click(function () {
        $("#createResult").text("");
        var url = "/joyManager/garden/plantStageDelete";

        var stagePK = $(this).attr("stagePK");

        var jsonOutput = {
          stagePK : stagePK,
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
              $("#createResult").text("删除成功");
              $("tr[stagePK='"+stagePK+"']").hide();
            } else {
              $("#createResult").text(data.message);
            }
          },
          error:function(e){
          }
        });
      });

      $("button[name='updateStage']").click(function () {
        $("#createResult").text("");
        var url = "/joyManager/garden/plantStageUpdate";

        var stagePK = $(this).attr("stagePK");
        var stageName = $("input[name='stageName'][stagePK='"+stagePK+"']").val();
        var livingMinute = $("input[name='livingMinute'][stagePK='"+stagePK+"']").val();

        var jsonOutput = {
          stagePK : stagePK,
          stageName : stageName,
          livingMinute : livingMinute,
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
              $("#createResult").text("编辑成功");
            } else {
              $("#createResult").text(data.message);
            }
          },
          error:function(e){
          }
        });
      });

      $("button[name='updateStageSort']").click(function () {
        var flag = $(this).attr("flag");
        var stagePK = $(this).attr("stagePK");
        updatePlantStageSort(stagePK, flag);
      });

      function updatePlantStageSort(stagePK, flag) {
        $("#createResult").text("");
        var url = "/joyManager/garden/plantStageSortUpdate";

        var jsonOutput = {
          stagePK : stagePK,
          flag : flag,
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
              $("#createResult").text("编辑成功");
            } else {
              $("#createResult").text(data.message);
            }
          },
          error:function(e){
          }
        });
      };

    });

  </script>
</footer>
</html>
