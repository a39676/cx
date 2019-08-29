<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">

  <div class="row">
    <div class="input-group col-sm-2">
      <div class="input-group-prepend">
        <span class="input-group-text" style="font-size: small;">最大阅读次数</span>
      </div>
      <input type="number" class="form-control" name="readLimit" min="0" max="10" value="3">
    </div>
  
    <div class="form-control col-sm-2" >
      <div class="">
        <span class="input-group-text" style="font-size: small;">有效时间</span>
      </div>
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

<div class="row">
  <div class="col-sm-12" >
    <input class="input form-control" type="text" name="creatingBurnArticle" style="width: 100%; height: 50px" 
      placeholder="阅后即焚,顾名思义,您输入的消息会有固定的被读次数,超出阅读次数后即销毁." >
  </div>
</div>

<div class="row">
  <div class="col-sm-12" >
    <button class="btn  btn-primary btn-sm" name="createBurnArticle"><span style="font-size: small;">提交</span></button>
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
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("input[name='readLimit']").keyup(function () { 
        var $readLimit = $(this).val();
        if($readLimit > 10) {
          $(this).val("10");
        }
      });
      
      $("button[name='createBurnArticle']").click( function() {
        createBurnArticle();
      });
  
      function createBurnArticle() {
        
        var url = "${pageContext.request.contextPath}/article/creatingBurnMessage";
        var readLimit = $("input[name='readLimit']").val();
        var validTime = $("select[name='validTime'] option:selected").val();
        var content = $("input[name='creatingBurnArticle']").val();

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
              $("span[name='readUrl']").text(urlPrefix + datas.readUri);
              $("span[name='burnUrl']").text(urlPrefix + datas.burnUri);
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