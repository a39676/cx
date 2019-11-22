<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 因需要使用富文本编辑器, 特别使用指定的库 -->
<!-- <%@ include file="../baseElementJSP/normalHeader.jsp" %> -->
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
    <div class="container-fluid" name="createArticleLong" >
      <sec:authorize access="hasRole('ROLE_USER')">
      <div class="row">
        <div class="col-sm-12" >
          <span class="badge badge-primary">请选择提交频道</span>
          <select class="" name="channelList" style="">
            <c:forEach items="${channelList}" var="subChannel">
              <option value="${subChannel.uuid}">${subChannel.channelName}</option>
            </c:forEach>
          </select>
        </div>
      </div>


      <div class="row">
        <div class="col-sm-12" >
          <textarea class="input form-control" name="articleTitle" rows="1" cols="50" placeholder="请输入标题~"></textarea>
        </div>
      </div>
      
      <div class="row">
        <div class="col-sm-12" >
          <textarea class="input form-control" name="creatingArticleLong" rows="4" cols="50" placeholder="请输入内容, 图片请直接粘帖图片链接"></textarea> 
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
            <button class="btn  btn-primary btn-sm" 
              name="createArticleLong">
              <span class="badge badge-primary">提交</span>
            </button>
            <button class="btn  btn-primary btn-sm" 
              name="createArticleLongEditor">
              <span class="badge badge-primary">提交富文本</span>
            </button>
            <button class="btn  btn-primary btn-sm" 
              name="closeArticleCreator">
              <span class="badge badge-primary">不想说了...</span>
            </button>
          </div>
        </div>
      </div>
      
      <div class="row">
        <div class="col-sm-12" >
          <span name="createArticleResult" badge badge-primary></span>
        </div>
      </div>
      </sec:authorize>
  </div> <%-- createArticleLong container --%>

</div> <%-- main row --%>
</body>

<footer>
  <!-- 因需要使用富文本编辑器, 特别使用指定的库 -->
  <!-- <%@ include file="../baseElementJSP/normalFooter.jsp" %> -->
  
  <script type="text/javascript">
    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
  </script>

<!--   <sec:authorize access="hasRole('ROLE_USER')">
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/creatingArticleLongV3.js'/>"></script>
  </sec:authorize> -->

  <script type="text/javascript">
    
    $(document).ready(function() {
      $("button[name='createArticleLongEditor']").click(function () {
        var url = "/article/createArticleLong";
        var title = $("textarea[name='articleTitle']").val();
        var s = $('#summernote');
        var content = s.summernote('code');
        var uuid = $("select[name='channelList'] option:selected").val();
    
        var jsonOutput = {
          uuid:uuid,
          title:title,
          content:content
        };
    
        var resultSpan = $("span[name='createArticleResult']");
        resultSpan.text("");
    
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
            resultSpan.text(datas.message);
            if(datas.result == "0") {
              $("textarea[name='articleTitle']").val("");
              $("textarea[name='creatingArticleLong']").val("");
            }
          },  
          error: function(datas) {              
          }  
        });  
      });
    });
  </script>
</footer>
</html>