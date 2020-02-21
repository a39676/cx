<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="col-sm-12">
  <div class="container-fluid" pk="${pk}">
  <c:forEach items="${commentList}" var="commentVO">
    <div class="row border-bottom border-warning rounded" pk="${pk}" name="commentSingleDetail" commentId="${commentVO.articleCommentId}">
      <c:if test="${commentVO.articleCommentId > 0}">
      <div class="col-sm-1">
        <c:set var="evaluationCodeAndCount" value="${commentVO.evaluationCodeAndCount}"/>
        <span style="font-size: small; color: #0200fc; cursor: pointer;" articleCommentId="${commentVO.articleCommentId}" commentId="${commentVO.articleCommentId}" 
          name="evaluation" evaluationCode="1" voted="0">${evaluationCodeAndCount[(1).intValue()].evaluationName}: </span>
        <span style="font-size: small; color: gray" articleCommentId="${commentVO.articleCommentId}" commentId="${commentVO.articleCommentId}" 
          name="evaluationCount" evaluationCode="1">${evaluationCodeAndCount[(1).intValue()].evaluationCount}</span>
        <br>
        <span style="font-size: small; color: red; cursor: pointer;" articleCommentId="${commentVO.articleCommentId}" commentId="${commentVO.articleCommentId}" 
          name="evaluation" evaluationCode="-1" voted="0">${evaluationCodeAndCount[(-1).intValue()].evaluationName}: </span>
        <span style="font-size: small; color: gray" articleCommentId="${commentVO.articleCommentId}" commentId="${commentVO.articleCommentId}" 
          name="evaluationCount" evaluationCode="-1">${evaluationCodeAndCount[(-1).intValue()].evaluationCount}</span>
        <span style="font-size: small; color: gray" articleCommentId="${commentVO.articleCommentId}" commentId="${commentVO.articleCommentId}" 
          name="evaluationResult"></span>
      </div>
      <div class="col-sm-2">
        <span style="font-size: small; color: gray">${commentVO.nickName}</span><br>
        <span style="font-size: small; color: gray">${commentVO.createTimeStr}</span>
      </div>
      </c:if>
      <div class="col-sm-9">
        <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
        <div class="btn-group">
        <c:choose>
          <c:when test="${commentVO.isPass == false && commentVO.isDelete == false}">
            <button class="btn btn-success btn-sm"
              name="passArticleComment" commentId="${commentVO.articleCommentId}" style="">
              <span class="badge badge-success">通过此评论</span>
            </button>
          </c:when>
          <c:when test="${commentVO.isPass == true}">
            <button class="btn btn-success btn-sm"
              name="passArticleComment" commentId="${commentVO.articleCommentId}" style="" disabled="disabled">
              <span class="badge badge-success">评论已通过</span>
            </button>
          </c:when>
          <c:otherwise>
            
          </c:otherwise>
        </c:choose>
        <c:choose>
          <c:when test="${commentVO.isDelete == false}">
            <button class="btn btn-danger btn-sm"
              name="deleteArticleComment" commentId="${commentVO.articleCommentId}" style="">
              <span class="badge badge-danger">删除此评论</span>
            </button>
          </c:when>
          <c:otherwise>
            <button class="btn btn-danger btn-sm"
              name="deleteArticleComment" commentId="${commentVO.articleCommentId}" style="" disabled="disabled">
              <span class="badge badge-danger">评论已删除</span>
            </button>
          </c:otherwise>
        </c:choose>
        </div>
        <br>
        <span class="badge badge-light" commentId="${commentVO.articleCommentId}" name="commentReviewResult"></span>
        </sec:authorize>
        <c:forEach items="${commentVO.contentLines}" var="line">
          <span name="articleCommentLine" pk="${pk}" style="font-size: x-small;">${line}</span><br>
        </c:forEach>
      </div>
    </div>
  </c:forEach>
  </div>
</div>



<script type="text/javascript">
$(document).ready(function() {

  $("button[name='showMoreArticleComment'][pk='${pk}']").attr("startTime", "${startTime}");

  $("img[name='articleImage']").click(function () {
    var fold = $(this).attr("fold");
    var imgMaxWidth = 700;

    if (fold == 0) {
      var tmpImg = new Image();
      tmpImg.src = $(this).attr("src");
  
      var imgWidth = tmpImg.width;
      var imgHeight = tmpImg.height;

      var width = 0;
      var height = 0;
      // var imgSizeCoefficient = imgWidth / imgHeight;
      
      if(imgWidth < imgMaxWidth) {
        width = imgWidth;
        height = imgHeight;45
      } else if(imgWidth > imgMaxWidth) {
        width = imgMaxWidth;
        var imgSizeCoefficient = imgMaxWidth / imgWidth;
        height = imgHeight * imgSizeCoefficient;
      }
  
      $(this).width(width);
      $(this).height(height);
      $(this).attr("fold", 1);
    } else {
      $(this).width(150);
      $(this).height(150);
      $(this).attr("fold", 0);
    }
  });

  $("div[name='commentSingleDetail'][pk='${pk}']").hover(function() {
    var commentId = $(this).attr("commentId");
    $("div[name='commentSingleDetail'][commentId='"+commentId+"']").css("background-color", "#e0e0eb");
  });

  $("div[name='commentSingleDetail'][pk='${pk}']").mouseleave(function() {
    var commentId = $(this).attr("commentId");
    $("div[name='commentSingleDetail'][commentId='"+commentId+"']").css("background-color", "#ffffff");
  });

  $("button[name='hideArticleComment'][pk='${pk}']").click(function () {
    $("div[name='commentDiv'][pk='${pk}']").hide();
  });

  $("button[name='closeArticleCommentCreator'][pk='${pk}']").click(function () {
    $(".articleCommentCreatingSubArea[pk='${pk}']").hide();
  });
  
  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
  $("input[name='commentStartTime'][pk='${pk}']").val("${startTime}");
  $("input[name='commentStartTime'][pk='${pk}']").keyup(function () {
    $("button[name='showMoreArticleComment'][pk='${pk}']").attr("startTime", $(this).val());
  });

  $("button[name='deleteArticleComment']").click(function () {
    var commentId = $(this).attr("commentId");
    var url = "${pageContext.request.contextPath}/articleAdminComment/deleteArticleComment";
    var jsonOutput = {
      commentId:commentId
    };

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
        if(datas.result == "0") {
          $("button[name='passArticleComment'][commentId='"+commentId+"']").prop('disabled', true);
          $("button[name='deleteArticleComment'][commentId='"+commentId+"']").prop('disabled', true);
          $("span[name='commentReviewResult'][commentId='"+commentId+"']").text(datas.message);
        }
      },  
      error: function(datas) {              
      }  
    });
  });

  $("button[name='passArticleComment']").click(function () {
    var commentId = $(this).attr("commentId");
    var url = "${pageContext.request.contextPath}/articleAdminComment/passArticleComment";
    var jsonOutput = {
      commentId:commentId
    };

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
        if(datas.result == "0") {
          $("button[name='passArticleComment'][commentId='"+commentId+"']").prop('disabled', true);
          $("button[name='deleteArticleComment'][commentId='"+commentId+"']").prop('disabled', true);
          $("span[name='commentReviewResult'][commentId='"+commentId+"']").text(datas.message);
        }
      },  
      error: function(datas) {              
      }  
    });
  });
  </sec:authorize>

  <sec:authorize access="hasRole('ROLE_USER')">
  $("button[name='showArticleCommentCreator'][pk='${pk}']").click(function () {
    $(".articleCommentCreatingSubArea[pk='${pk}']").show();
  });
  </sec:authorize>
});
</script>
