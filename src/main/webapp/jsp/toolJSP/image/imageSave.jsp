<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<input type="text" id="imageUrl" placeholder="please type image url" style="height: 50px">
<input type="text" id="imageRemark" style="height: 50px"> <br>
<br>
<button id="saveImageUrl1">save image url 1</button><br>
<br>
<button id="saveImageUrl9">save image url 9</button><br>
<br>
<button id="saveImageUrl10">save image url 10</button><br>
<br>
<input type="text" id="passTime" style="height: 50px"> <br>
<button id="cleanTmpFiles">cleanTmpFiles</button><br>
<div>
  <img id="imageView" src="1" fold="0" style="width: 100px; height: 100px">
</div>
<div id="resultView"></div>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#saveImageUrl1").click( function() {
      var imageUrl = $("#imageUrl").val();
      viewImage(imageUrl);
      saveImage(imageUrl, 1);
    });

    $("#saveImageUrl9").click( function() {
      var imageUrl = $("#imageUrl").val();
      viewImage(imageUrl);
      saveImage(imageUrl, 9);
    });

    $("#saveImageUrl10").click( function() {
      var imageUrl = $("#imageUrl").val();
      viewImage(imageUrl);
      saveImage(imageUrl, 10);
    });

    $("#imageView").click( function() {
      var fold = $(this).attr("fold");

      if (fold == 0) {
        var tmpImg = new Image();
        tmpImg.src = $(this).attr("src");
        var width = tmpImg.width;
        var height = tmpImg.height;
        $(this).width(width);
        $(this).height(height);
        $(this).attr("fold", 1);
      } else {
        $(this).width(120);
        $(this).height(120);
        $(this).attr("fold", 0);
      }
    });

    function viewImage(url) {
      $("#imageView").attr("src", url);
    }

    function saveImage(imageUrl, tagNum) {
      
      var url = "${pageContext.request.contextPath}/tool/image/imageSaveHandle";
      var imageRemark = $("#imageRemark").val();
      var $imageUrl = {
        imageUrl:imageUrl,
        tag:tagNum,
        imageRemark:imageRemark
      };

      $("#imageUrl").val("");
      $("#resultView").html("{}");
      $("#imageRemark").val("");

      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        data: JSON.stringify($imageUrl),
        cache : false,
        contentType: "application/json",
        // dataType: "json",
        timeout:5000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#resultView").html(datas);
        },  
        error: function(datas) {  
          $("#resultView").html(datas.responseText);
        }  
      });  
    };


    $("#cleanTmpFiles").click( function() {
      var url = "${pageContext.request.contextPath}/tool/cleanTmpFiles";

      var passTime = $("#passTime").val();

      var $imageUrl = {
        passTime:passTime
      };

      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        data: JSON.stringify($imageUrl),
        cache : false,
        contentType: "application/json",
        // dataType: "json",
        timeout:5000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#resultView").html(datas);
        },  
        error: function(datas) {  
          $("#resultView").html(datas.responseText);
        }  
      });  
    });
  
  });

</script>
</footer>
</html>