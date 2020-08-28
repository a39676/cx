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
<script type="text/javascript" src="/static_resources/js/jquery/v3_2_1/jquery.min.js"></script>

<link rel="stylesheet" href="/static_resources/css/bootstrap/v4_0_0_beta/bootstrap.min.css">

<script type="text/javascript" src="/static_resources/js/popper/v1_11_0/popper.min.js"></script>

<script type="text/javascript" src="/static_resources/js/bootstrap/v4_0_0_beta/bootstrap.min.js"></script>

<link href="/static_resources/css/summernote/v0_8_12/summernote-bs4.css" rel="stylesheet" type="text/css">

<script src="/static_resources/js/summernote/v0_8_12/summernote-bs4.min.js"></script>

</head>
<body>
<div class="container-fluid">
 
  <div class="row">
    <div class="container-fluid" name="createArticleLong" >
      <sec:authorize access="hasRole('ROLE_USER')">
      <div class="row">
        <div class="col-sm-12" >
          <textarea class="input form-control" id="articleTitle" rows="1" cols="50" placeholder="请输入标题~">${articleVO.articleTitle}</textarea>
        </div>
      </div>

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
          <div class="btn-group">
            <sec:authorize access="hasRole('ROLE_POSTER')">
              <textarea class="input form-control" type="text" 
              name="superAdminKey" placeholder="please insert key"></textarea>
            </sec:authorize>
            <c:if test="${createNew == true}">
              <button class="btn  btn-primary btn-sm" 
                id="createNew">
                <span class="badge badge-primary">提交</span>
              </button>
              <button class="btn  btn-primary btn-sm" 
                id="editorAgain">
                <span class="badge badge-primary">继续编辑</span>
              </button>
            </c:if>
            <c:if test="${edit == true}">
              <button class="btn  btn-primary btn-sm" 
                id="edit">
                <span class="badge badge-primary">提交编辑</span>
              </button>
            </c:if>
          </div>
        </div>
      </div>

      <div id="sourceArticleVO" pk="${articleVO.privateKey}" disabled="disabled" style="display: none;" contentLines='${articleVO.contentLines}'></div>

      <div class="row">
        <div class="col-sm-12" >
          <span id="iconUploadResult" badge badge-primary></span>
        </div>
      </div>
      </sec:authorize>
  </div> <%-- createArticleLong container --%>

</div> <%-- main row --%>
</body>

<footer>
  
  <script type="text/javascript">
    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
  </script>

  <script type="text/javascript">
    

    $(document).ready(function() {
      
      $("#createNew").click(function () {
        var url = "/article/createArticleLong";
        var title = $("#articleTitle").val();
        var s = $('#summernote');
        var content = s.summernote('code');
        var channelId = $("select[name='channelList'] option:selected").val();
    
        var jsonOutput = {
          channelId:channelId,
          title:title,
          content:content
        };
    
        var resultSpan = document.getElementById("iconUploadResult");
        resultSpan.innerHTML = "";
    
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
            resultSpan.innerHTML = datas.message;
            if(datas.result == "0") {
              document.getElementById("articleTitle").disabled = true;
              document.getElementById("summernote").disabled = true;
              document.getElementById("createNew").disabled = true;
            }
          },  
          error: function(datas) {              
          }  
        });  
      });

      $("#createNew").click(function () {
        document.getElementById("articleTitle").disabled = false;
        document.getElementById("summernote").disabled = false;
        document.getElementById("createNew").disabled = false;
      });

    });
  </script>
</footer>
</html>