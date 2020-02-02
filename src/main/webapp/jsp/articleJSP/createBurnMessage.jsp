<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 因需要使用富文本编辑器, 特别使用指定的库 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<sec:csrfMetaTags />
<title>${ title }</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.js"></script>

</head>
<body>
<div class="container-fluid">

  <div class="row">
    <div class="input-group col-sm-3">
      <span class="input-group-text" style="font-size: small;">最大阅读次数</span>
      <input type="number" class="form-control" name="readLimit" min="1" max="10" value="3">
    </div>
  
    <div class="input-group col-sm-3" >
      <span class="input-group-text" style="font-size: small;">有效时间</span>
      <select class="form-control" name="validTime">
        <option value="30">30分钟</option>
        <option value="60">60分钟</option>
        <option value="120">2小时</option>
        <option value="480">4小时</option>
        <option value="720">6小时</option>
        <option value="1440">1天</option>
        <option value="2880">2天</option>
        <option value="4320">3天</option>
      </select>
    </div>
</div>

<hr>

<div class="row">
  <div class="col-sm-12">
    <span class="input-group-text" style="font-size: small;">
      阅后即焚,顾名思义,您输入的消息会有固定的被读次数,超出阅读次数后即销毁."
    </span>
  </div>
</div>

<hr>

<div class="row">
  <div class="col-sm-12" >
    <div id="summernote"></div>
    <script>
      $('#summernote').summernote({
        tabsize: 2,
        height: 100
      });
    </script>
  </div>
</div>

<div class="row">
  <div class="col-sm-12" >
    <button class="btn  btn-primary btn-sm" name="createNewBurnArticle"><span style="font-size: small;">提交</span></button>
  </div>
</div>

<div class="row">
  <div class="col-sm-12" >
    <div id="resultView" style="display: none">
      <div><span style="font-size: small;">查阅地址: </span><span name="readUrl"></span></div>
      <div><span style="font-size: small;">销毁地址: </span><span name="burnUrl"></span></div>
      <div><span style="font-size: small;color: red" name="createArticleResult"></span></div>
    </div>
  </div>
</div>


</div>
</body>

<footer>
  
  <script type="text/javascript">
    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
  </script>

  <script type="text/javascript">

    $(document).ready(function() {

      $("input[name='readLimit']").keyup(function () { 
        var $readLimit = $(this).val();
        if($readLimit > 10) {
          $(this).val("10");
        }
      });
      
      $("button[name='createNewBurnArticle']").click( function() {
        createNewBurnArticle();
      });
  
      function createNewBurnArticle() {
        
        var url = "${pageContext.request.contextPath}/articleBurn/creatingBurnMessage";
        var readLimit = $("input[name='readLimit']").val();
        var validTime = $("select[name='validTime'] option:selected").val();
        var s = $('#summernote');
        var content = s.summernote('code');

        var urlPrefix = window.location.host;

        var jsonOutput = {
          readLimit : readLimit,
          validTime : validTime,
          content : content
        };

        $("span[name='readUrl']").text("");
        $("span[name='burnUrl']").text("");
        $("span[name='createArticleResult']").text("");
  
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

            $("#resultView").show();
            if(datas.result == 0) {

              $("span[name='readUrl']").html('<a href='+datas.readUri+' target="_blank" >'+urlPrefix + datas.readUri+'</a>');
              $("span[name='burnUrl']").html('<a href='+datas.burnUri+' target="_blank" >'+urlPrefix + datas.burnUri+'</a>');
            } else {
              $("span[name='createArticleResult']").text(datas.message);
            }
          },  
          error: function(datas) {  
            $("#resultView").html(datas.responseText);
          }  
        });  
      };

    });

  </script>
</footer>
</html>