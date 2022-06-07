<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<sec:csrfMetaTags />
<title>${ title }</title>

<span>upload test</span>
<input type="file" name="" value="" accept="image/*" id="uploadImageInput">
<input type="text" name="" value="imgName" id="imgName">
<img id="uploadImgPreview" src="" alt="" />
<button type="button" name="button" id="uploadImgButton">uploadImg</button>


<footer>
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>

<script type="text/javascript">

  $(document).ready(function() {

    $("#uploadImgButton").click(function () {
      var url = "/test2/t2";

      var imgName = $("#imgName").val();
      var imgBase64Str = $("#uploadImgPreview").attr("src");

      var jsonOutput = {
        imgName : imgName,
        validTime : "2000-12-31 23:59:59",
        imgBase64Str : imgBase64Str.split("base64,")[1],
        imgTagCode : 14,
      };

      console.log(jsonOutput);

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
          console.log(data);
          // $("#searchResult").html(data);
        },
        error:function(e){
          // $("#result").text(e);
        }
      });
    });

    $("#uploadImageInput").change(function () {
      console.log("some");
      const file = this.files[0];
      if (file){
        let reader = new FileReader();
        reader.onload = function(event){
          console.log(event.target.result);
          $("#uploadImgPreview").attr("src", event.target.result);
          $("#uploadImgPreview").attr("style", "width:80px; hight:80px;")
        }
        reader.readAsDataURL(file);
      }
    });
  });

</script>
