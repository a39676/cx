<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<span>${message}</span>
<div class="container-fluid" pk="${pk}">
<sec:authorize access="hasRole('ROLE_ADMIN')">
<div class="row" pk=${pk} name="commentSearchDiv">
  <%@ include file="../articleJSP/articleCommentSearch.jsp" %>
</div>
</sec:authorize>
<div class="row" pk="${pk}" name="commentDetailList">
  <%@ include file="../articleJSP/articleCommentListSubList.jsp" %>
</div>

<div class="row" pk="${pk}">
  <div class="col-sm-12" >
    <div class="btn-group">
      <button class="btn btn-success btn-sm" startTime=""
        name="showMoreArticleComment" pk="${pk}" style="">
        <span class="badge badge-success">更多评论</span>
      </button>
      <button class="btn btn-primary btn-sm" 
        name="showArticleCommentCreator" pk="${pk}" style="">
        <span class="badge badge-primary">发表评论</span>
      </button>
    </div>
  </div>
</div>

<div class="container-fluid" pk="${pk}" name="createArticleComment" >
<sec:authorize access="hasRole('ROLE_USER')">
<div class="row articleCommentCreatingSubArea" pk="${pk}" style="display: none">
  <div class="col-sm-12" >
    <textarea class="input form-control" pk="${pk}" name="creatingArticleComment" rows="4" cols="50" placeholder="请输入内容, 图片请直接粘帖图片链接"></textarea> 
  </div>
</div>

<div class="row articleCommentCreatingSubArea" pk="${pk}" style="display: none">
  <div class="col-sm-12" >
    <div class="btn-group">
      <button class="btn btn-primary btn-sm" 
        pk="${pk}" name="createArticleComment">
        <span class="badge badge-primary">提交</span>
      </button>
      <button class="btn btn-primary btn-sm" 
        pk="${pk}" name="closeArticleCommentCreator">
        <span class="badge badge-primary">不想说了...</span>
      </button>
    </div>
  </div>
</div>

<div class="row articleCommentCreatingSubArea" pk="${pk}" style="display: none">
  <div class="col-sm-12" >
    <span name="createArticleResult" pk="${pk}" class="badge badge-primary"></span>
  </div>
</div>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
<div class="row articleCommentCreatingSubArea" pk="${pk}" style="display: none">
  <div class="col-sm-12" >
    <span class="badge badge-primary">不好意思...请先登陆或者注册...</span>
  </div>
</div>
</sec:authorize>
</div>

<div class="row" pk="${pk}">
  <div class="col-sm-12" >
    <div class="btn-group">
      <button class="btn btn-info btn-sm" 
        name="hideArticleComment" pk="${pk}" style="">
        <span class="badge badge-info">收起评论</span>
      </button>
    </div>
  </div>
</div>

</div>



<script type="text/javascript">
$(document).ready(function() {

  $("button[name='hideArticleComment'][pk='${pk}']").click(function () {
    $("div[name='commentDiv'][pk='${pk}']").hide();
    $("button[name='findComment'][pk='${pk}']").fadeIn(150);
  });

  $("button[name='showArticleCommentCreator'][pk='${pk}']").click(function () {
    $(".articleCommentCreatingSubArea[pk='${pk}']").show();
  });

  $("button[name='closeArticleCommentCreator'][pk='${pk}']").click(function () {
    $(".articleCommentCreatingSubArea[pk='${pk}']").hide();
  });

  $("button[name='showMoreArticleComment'][pk='${pk}']").click(function () {
    var pk = '${pk}';
    var startTime = $("button[name='showMoreArticleComment'][pk='${pk}']").attr("startTime");
    var url = "${pageContext.request.contextPath}/articleComment/findArticleCommentSubPage";
    var jsonOutput = {
      pk:pk,
      startTime:startTime
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      // dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        $("div[name='commentDetailList'][pk='${pk}']").append(datas);
      },  
      error: function(datas) {

      }  
    });  
  });

  <sec:authorize access="hasRole('ROLE_ADMIN')">
    $("button[name='reviewMoreComment'][pk='${pk}']").click(function () {
    var pk = '${pk}';
    var startTime = $("button[name='showMoreArticleComment'][pk='${pk}']").attr("startTime");
    var isPass = "0";
    if($("input[name='commentIsPass'][pk='${pk}']").is(":checked")) {
      isPass = "1";
    }
    var isDelete = "0";
    if($("input[name='commentIsDelete'][pk='${pk}']").is(":checked")) {
      isDelete = "1";
    }
    var url = "${pageContext.request.contextPath}/articleComment/findArticleCommentSubPage";
    var jsonOutput = {
      pk:pk,
      startTime:startTime,
      isPass:isPass,
      isDelete:isDelete
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      // dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        $("div[name='commentDetailList'][pk='${pk}']").append(datas);
      },  
      error: function(datas) {

      }  
    });  
  });
  </sec:authorize>

  <sec:authorize access="hasRole('ROLE_USER')">
  $("button[name='createArticleComment'][pk='${pk}']").click(function () {
    var pk = '${pk}';
    var url = "${pageContext.request.contextPath}/articleComment/createArticleComment";
    var content = $("textarea[pk='${pk}'][name='creatingArticleComment']").val();
    var jsonOutput = {
      pk:pk,
      content:content
    };
    var resultSpan = $("span[name='createArticleResult'][pk='${pk}']");
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
          $("textarea[pk='${pk}'][name='articleTitle']").val("");
          $("textarea[pk='${pk}'][name='creatingArticleComment']").val("");
        }
      },  
      error: function(datas) {              
      }  
    });
  });
  </sec:authorize>
});
</script>
