<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <sec:csrfMetaTags />
  <title>阅后即焚</title>
</head>
<body>
<div class="container-fluid">

  <div class="row">
    <div class="col-md-6">
      <span>创建"阅后即焚"信息</span>
    </div>
  </div>

  <div class="row">
    <div class="col-md-6">
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
    <div class="col-md-6">
      <div class="input-group-prepend ">
        <span class="input-group-text" style="font-size: small;">最大阅读次数</span>
      </div>
      <input type="number" class="form-control" name="readLimit" min="1" max="10" value="3">
    </div>
  </div>

  <div class="row">
    <div class="col-md-6">
      <div class="input-group-prepend ">
        <span class="input-group-text" style="font-size: small;">阅读密码(可选, 限制必须通过密码取得信息)</span>
      </div>
      <input class="form-control" type="password" id="burningMsgPwd" maxlength="16" value="">
    </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-sm-12">
      <span class="input-group-text" style="font-size: small;">
        编写的消息会有存活时间 及 阅读次数限制, 过期即销毁.
      </span>
    </div>
  </div>

<hr>

<div class="row">
  <div class="col-sm-12" >
    <%@ include file="../summernote/summernote.jsp" %>
  </div>
</div>

<div class="row">
  <div class="col-sm-12" >
    <button class="btn  btn-primary btn-sm" name="createNewBurnArticle"><span style="font-size: small;">生成</span></button>
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

        var url = "/articleBurn/creatingBurnMessage";
        var readLimit = $("input[name='readLimit']").val();
        var validTime = $("select[name='validTime'] option:selected").val();
        var pwd = $("#burningMsgPwd").val();
        var s = $('#summernote');
        var content = s.summernote('code');

        var urlPrefix = window.location.host;

        var jsonOutput = {
          readLimit : readLimit,
          validTime : validTime,
          content : content,
          pwd : pwd
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
