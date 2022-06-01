<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
<title>${ title }</title>
</head>
<body>
<div class="container-fluid">

  <div class="row">
    <div class="col-sm-3">
      <a href="/">返回首页</a>
    </div>
  </div>

  <div class="row">
    <div class="col-sm-3">
      <br>
    </div>
  </div>

  <div class="row">
    <div class="col-sm-3">
      <div class="form-check">
        <input class="form-check-input" type="radio" id="decodeByUpload" name="rowType" checked="checked" value="上传图片识别">
        <label class="form-check-label btn btn-sm btn-primary" for="decodeByUpload">上传图片识别</label>
      </div>
      <div id="decodeByUploadPart">
        <input type="file" name="" accept="image/*" id="newImgUpload"><br>
        <img src="" alt="" id="qrCodeImgPreview" name=""><br>
      </div>
      <br><br>
      <div class="form-check">
        <input class="form-check-input" type="radio" id="decodeByUrl" name="rowType" value="在线图片识别">
        <label class="form-check-label btn btn-sm btn-primary" for="decodeByUrl">在线图片识别</label>
      </div>
      <div id="decodeByOnlineImgPart">
        <input type="text" name="" value="" id="onlineImg" placeholder="请输入图片URL"><br>
      </div>

      <br><br>

      <button class="btn  btn-primary btn-sm" id="Decode">
        <span class="badge badge-primary">识别</span>
      </button>
    </div>

    <div class="col-sm-3" >
      <div class="input-group-prepend">
        <span class="input-group-text">生成QR Code图片</span>
      </div>
      <input type="text" name="" value="" placeholder="请输入要生成二维码的信息" size="24" id="generatQrCode">

      <br>
      <br>

      <button class="btn  btn-primary btn-sm" id="Generator">
        <span class="badge badge-primary">生成</span>
      </button>
    </div>
  </div>

  <div class="row">
    <div class="col-sm-3" >
      <span id="outputResult" badge badge-primary></span>
    </div>
    <div class="col-sm-3">
      <img id="qrcodeResult" src="" alt="">
    </div>
  </div>
</div>
</body>

<footer>

  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>

  <script type="text/javascript">

    $(document).ready(function() {

      ready();
      function ready() {
        document.getElementById("decodeByUploadPart").style.display = "";
        document.getElementById("decodeByOnlineImgPart").style.display = "none";
        $("label[for='decodeByUpload']").addClass("btn-primary");
        $("label[for='decodeByUpload']").removeClass("btn-secondary");
        $("label[for='decodeByUrl']").addClass("btn-secondary");
        $("label[for='decodeByUrl']").removeClass("btn-primary");
      }

      $("input[name='rowType']").click(function () {
        var tmpv = $("#decodeByUpload:checked").val();
        if(tmpv != null && tmpv.length) {
          document.getElementById("decodeByUploadPart").style.display = "";
          document.getElementById("decodeByOnlineImgPart").style.display = "none";
          $("label[for='decodeByUpload']").addClass("btn-primary");
          $("label[for='decodeByUpload']").removeClass("btn-secondary");
          $("label[for='decodeByUrl']").addClass("btn-secondary");
          $("label[for='decodeByUrl']").removeClass("btn-primary");
          $("#onlineImg").val("");
        } else {
          document.getElementById("decodeByUploadPart").style.display = "none";
          document.getElementById("decodeByOnlineImgPart").style.display = "";
          $("label[for='decodeByUpload']").removeClass("btn-primary");
          $("label[for='decodeByUpload']").addClass("btn-secondary");
          $("label[for='decodeByUrl']").removeClass("btn-secondary");
          $("label[for='decodeByUrl']").addClass("btn-primary");
          $("#newImgUpload").val("");
          $("#qrCodeImgPreview").attr("src", "");
        }
        $("#outputResult").text("");
      })

      $("#newImgUpload").change(function () {
        const file = this.files[0];
        if (file){
          let reader = new FileReader();
          reader.onload = function(event){
            $("#qrCodeImgPreview").attr("src", event.target.result);
            $("#qrCodeImgPreview").attr("style", "width:200px; hight:200px;")
          }
          reader.readAsDataURL(file);
        }
      });

      $("#Decode").click(function () {
        var url = "/publicTool/qrcode/decode";

        var imgSrc = $("#qrCodeImgPreview").attr("src");
        var onlineImgUrl = $("#onlineImg").val();

        var content;
        if(onlineImgUrl.length > 0){
          content = onlineImgUrl;
        } else {
          content = imgSrc;
        }

        var jsonOutput = {
          content:content
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
            $("#outputResult").text("识别结果: " + data);
          },
          error:function(e){
          }
        });
      });

      $("#Generator").click(function () {
        var url = "/publicTool/qrcode/generator";

        var content = $("#generatQrCode").val();

        var jsonOutput = {
          content:content
        };

        $.ajax({
          type : "POST",
          async : true,
          url : url,
          data: JSON.stringify(jsonOutput),
          cache : false,
          contentType: "application/json",
          // dataType: "image/jpg",
          timeout:50000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            $('#qrcodeResult').attr('src', "data:image/png;base64," + datas);
          },
          error: function(datas) {
          }
        });
      });
    });
  </script>
</footer>
</html>
