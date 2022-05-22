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
          <hr>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段1名称</span>
            </div>
            <input type="text" class="" name="newPlantStageName" bindId="newPlantStage1" id="newPlantStageName1"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段1持续时间</span>
            </div>
            <input type="number" class="" name="" min="1" bindId="newPlantStage1" id="newPlantStageLivingMinute1"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段1图片</span>
            </div>
            <input type="file" name="" class="newPlantStageImgUpload" bindId="newPlantStage1" accept="image/*" id="newPlantStageImgUpload1">
            <img src="" alt="" id="newPlantStageImg1" name="newPlantStageImgPreview" bindId="newPlantStage1">
            <input type="text" name="newPlantStageImgUploadResult" value="" class="" disabled bindId="newPlantStage1">
            <button type="button" class="btn btn-primary newPlantStageImgUploadBtn" name="button" bindId="newPlantStage1">上传生长阶段1图片</button>
          </div>
          <hr>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段2名称</span>
            </div>
            <input type="text" class="" name="newPlantStageName" bindId="newPlantStage2" id="newPlantStageName2"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段2持续时间</span>
            </div>
            <input type="number" class="" name="" min="1" bindId="newPlantStage2" id="newPlantStageLivingMinute2"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段2图片</span>
            </div>
            <input type="file" name="" class="newPlantStageImgUpload" bindId="newPlantStage2" accept="image/*" id="newPlantStageImgUpload2">
            <img src="" alt="" id="newPlantStageImg2" name="newPlantStageImgPreview" bindId="newPlantStage2">
            <input type="text" name="newPlantStageImgUploadResult" value="" class="" disabled bindId="newPlantStage2">
            <button type="button" class="btn btn-primary newPlantStageImgUploadBtn" name="button" bindId="newPlantStage2">上传生长阶段2图片</button>
          </div>
          <hr>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段3名称</span>
            </div>
            <input type="text" class="" name="newPlantStageName" bindId="newPlantStage3" id="newPlantStageName3"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段3持续时间</span>
            </div>
            <input type="number" class="" name="" min="1" bindId="newPlantStage3" id="newPlantStageLivingMinute3"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段3图片</span>
            </div>
            <input type="file" name="" class="newPlantStageImgUpload" bindId="newPlantStage3" accept="image/*" id="newPlantStageImgUpload3">
            <img src="" alt="" id="newPlantStageImg3" name="newPlantStageImgPreview" bindId="newPlantStage3">
            <input type="text" name="newPlantStageImgUploadResult" value="" class="" disabled bindId="newPlantStage3">
            <button type="button" class="btn btn-primary newPlantStageImgUploadBtn" name="button" bindId="newPlantStage3">上传生长阶段3图片</button>
          </div>
          <hr>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段4名称</span>
            </div>
            <input type="text" class="" name="newPlantStageName" bindId="newPlantStage4" id="newPlantStageName4"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段4持续时间</span>
            </div>
            <input type="number" class="" name="" min="1" bindId="newPlantStage4" id="newPlantStageLivingMinute4"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段4图片</span>
            </div>
            <input type="file" name="" class="newPlantStageImgUpload" bindId="newPlantStage4" accept="image/*" id="newPlantStageImgUpload4">
            <img src="" alt="" id="newPlantStageImg4" name="newPlantStageImgPreview" bindId="newPlantStage4">
            <input type="text" name="newPlantStageImgUploadResult" value="" class="" disabled bindId="newPlantStage4">
            <button type="button" class="btn btn-primary newPlantStageImgUploadBtn" name="button" bindId="newPlantStage4">上传生长阶段4图片</button>
          </div>
          <hr>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段5名称</span>
            </div>
            <input type="text" class="" name="newPlantStageName" bindId="newPlantStage5" id="newPlantStageName5"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段5持续时间</span>
            </div>
            <input type="number" class="" name="" min="1" bindId="newPlantStage5" id="newPlantStageLivingMinute5"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">生长阶段5图片</span>
            </div>
            <input type="file" name="" class="newPlantStageImgUpload" bindId="newPlantStage5" accept="image/*" id="newPlantStageImgUpload5">
            <img src="" alt="" id="newPlantStageImg5" name="newPlantStageImgPreview" bindId="newPlantStage5">
            <input type="text" name="newPlantStageImgUploadResult" value="" class="" disabled bindId="newPlantStage5">
            <button type="button" class="btn btn-primary newPlantStageImgUploadBtn" name="button" bindId="newPlantStage5">上传生长阶段5图片</button>
          </div>
          <hr>
          <button class="btn btn-primary" type="button" name="" id="createNewPlant">Create</button>
        </div>
      </form>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12" id="createResult">

    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $(".newPlantStageImgUpload").change(function () {
        var bindId = $(this).attr("bindId");
        const file = this.files[0];
        if (file){
          let reader = new FileReader();
          reader.onload = function(event){
            $("img[name='newPlantStageImgPreview'][bindId='"+bindId+"']").attr("src", event.target.result);
            $("img[name='newPlantStageImgPreview'][bindId='"+bindId+"']").attr("style", "width:80px; hight:80px;")
          }
          reader.readAsDataURL(file);
        }
      });

      $(".newPlantStageImgUploadBtn").click(function () {
        var thisId = $(this).attr("bindId");

        var url = "/test2/t2";

        var imgNameInput = $("input[name='newPlantStageName'][bindId='"+thisId+"']");
        var imgName = imgNameInput.val();
        if(imgName.length < 1){
          imgNameInput.attr("style", "border:1px solid #ff0000");
          return;
        }

        var imgBase64Str = $("img[name='newPlantStageImgPreview'][bindId='"+thisId+"']").attr("src");

        var jsonOutput = {
          imgName : imgName,
          imgBase64Str : imgBase64Str.split("base64,")[1],
          imgTagCode : 14,
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
            $("input[name='newPlantStageImgUploadResult'][bindId='"+thisId+"']").val(data.imgUrl);
            imgNameInput.attr("style", "");
          },
          error:function(e){
          }
        });
      });

      $("#createNewPlant").click(function () {
        create();
      });

      function create() {
        var url = "/joy/garden/createNewPlant";

        var newPlantName = $("#newPlantName").val();
        var newPlantType = $("#newPlantType option:selected").val();

        var jsonOutput = {
          newPlantName : newPlantName,
          newPlantType : newPlantType,
        };

        console.log(jsonOutput);

        // $.ajax({
        //   type : "POST",
        //   url : url,
        //   data: JSON.stringify(jsonOutput),
        //   // dataType: 'json',
        //   contentType: "application/json",
        //   beforeSend: function(xhr) {
        //     xhr.setRequestHeader(csrfHeader, csrfToken);
        //   },
        //   timeout: 15000,
        //   success:function(data){
        //     console.log(data);
        //     $("#searchResult").html(data);
        //   },
        //   error:function(e){
        //     // $("#result").text(e);
        //   }
        // });
      }
    });

  </script>
</footer>
</html>
