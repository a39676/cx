<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:forEach items="${summaryVOList}" var="articleLongSummaryVO">
  <div class="row articleLongSummary border border-primary rounded" 
    pk="${articleLongSummaryVO.privateKey}" loadingFlag="0">
    <div class="col-sm-1" >
      <c:set var="evaluationMap" value="${articleLongSummaryVO.evaluationMap}" />
      <span style="font-size: small;color: #0200fc" pk="${articleLongSummaryVO.privateKey}" name="evaluation" evaluationCode="1" voted="0">${evaluationMap[(1).intValue()].evaluationName}: </span>
      <span style="font-size: small;color: gray" pk="${articleLongSummaryVO.privateKey}" name="evaluationCount" evaluationCode="1">${evaluationMap[(1).intValue()].evaluationCount}</span><br>
      <span style="font-size: small;color: red" pk="${articleLongSummaryVO.privateKey}" name="evaluation" evaluationCode="-1" voted="0">${evaluationMap[(-1).intValue()].evaluationName}: </span>
      <span style="font-size: small;color: gray" pk="${articleLongSummaryVO.privateKey}" name="evaluationCount" evaluationCode="-1">${evaluationMap[(-1).intValue()].evaluationCount}</span>
      <span style="font-size: small;color: gray" pk="${articleLongSummaryVO.privateKey}" name="evaluationResult"></span>
    </div>
    <div class="col-sm-2 articleLongSummaryContent" pk="${articleLongSummaryVO.privateKey}" style="cursor: pointer;">
      <span class="badge badge-primary badge-pill" style="cursor: pointer;" name="nickName" nickName="${articleLongSummaryVO.nickName}" pk="${articleLongSummaryVO.privateKey}" loadingFlag="0">
        ${articleLongSummaryVO.nickName}
      </span><br>
      <span class="badge badge-light">${articleLongSummaryVO.createDateDescription}</span>
    </div>
    <div class="col-sm-7 articleLongSummaryContent" pk="${articleLongSummaryVO.privateKey}" style="cursor: pointer;">
      <%-- title & first line --%>
      <span class="badge badge-light" pk="${articleLongSummaryVO.privateKey}" name="sampleWord" style="color: gray">点击展开</span>
      <c:if test="${!empty articleLongSummaryVO.articleTitle}">
        <label style="color: #1ce2bf">${articleLongSummaryVO.articleTitle}: </label>  
      </c:if>
      <span style="font-size: small;color: gray">${articleLongSummaryVO.firstLine}</span>
      <sec:authorize access="hasRole('ROLE_ADMIN')">
        <c:if test="${articleLongSummaryVO.hasCommentNotReview == true}">
          <span class="badge badge-danger">有待审核评论</span>
        </c:if>
      </sec:authorize>
    </div>
    <div class="col-sm-2 articleLongSummaryContent" pk="${articleLongSummaryVO.privateKey}" style="cursor: pointer;">
      <%-- imagePart --%>
      <c:forEach items="${articleLongSummaryVO.imgUrls}" var="voImage">
        <img src="${voImage}" style="width: 50px; height: 50px">
      </c:forEach>
    </div>
  </div>
  <div class="row" name="loading" style="display: none;" pk="${articleLongSummaryVO.privateKey}">
    <div class="col-sm-4"></div>
    <div class="col-sm-4">
      <img src="https://wx1.sinaimg.cn/mw690/0062ci2oly1ftzyk5dlejg303k03k40e.gif" style="width: 30px; height: 30px;">
    </div>
  </div>
  <div class="row moreUserDetail" style="display: none" pk="${articleLongSummaryVO.privateKey}">
  </div>
  <div class="row readArticleLong" style="display: none" pk="${articleLongSummaryVO.privateKey}">
  </div>
</c:forEach>


<script type="text/javascript">
$(document).ready(function() {

  $("span[name='nickName']").click(function () {
    var loadingFlag = $(this).attr("loadingFlag");
    if(loadingFlag != "0") {
      return;
    }

    var pk = $(this).attr("pk");
    var nickName = $(this).attr("nickName");

    $(".moreUserDetail").fadeOut();
    if($("div[name='moreUserDetail'][pk='"+pk+"']").length > 0) {
      $(".moreUserDetail[pk='"+pk+"']").fadeIn();
      return;
    }

    $(this).attr("loadingFlag", "1");

    var nickName = $(this).attr("nickName");
    var url = "${pageContext.request.contextPath}/user/otherUserInfo";

    var jsonOutput = {};
    jsonOutput.nickName = nickName;
    jsonOutput.pk = pk;

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
        $("div[name='moreUserDetail'][pk='"+pk+"']").remove();
        $(".moreUserDetail[pk='"+pk+"']").append(datas);
        $(".moreUserDetail[pk='"+pk+"']").fadeIn();
        $("span[nickName='"+nickName+"'][pk='"+pk+"']").attr("loadingFlag", "0");
      },  
      error: function(datas) {
      }  
    });  
  });

  $(".articleLongSummaryContent").click(function () {
    var pk = $(this).attr("pk");
    loadArticleLong(pk);
  });

  function loadArticleLong(privateKey) {
    var pk = privateKey;
    var loadingFlag = $(".articleLongSummary[pk='"+pk+"']").attr("loadingFlag");
    if(loadingFlag == "1") {
      return;
    }
    $(".readArticleLong").fadeOut();
    if($(".articleLongShow[pk='"+pk+"']").length) {
      $(".readArticleLong[pk='"+pk+"']").fadeIn();
      return;
    }
    $(".articleLongSummary[pk='"+pk+"']").attr("loadingFlag", "1");
    $("div[name='loading'][pk='"+pk+"']").fadeIn(150);
    var url = "${pageContext.request.contextPath}/article/readArticleLong";
    var jsonOutput = {
      pk:pk
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
        $(".readArticleLong[pk='"+pk+"']").append(datas);
        $(".readArticleLong[pk='"+pk+"']").fadeIn();
        $(".articleLongSummary[pk='"+pk+"']").attr("loadingFlag", "0");
        $("div[name='loading'][pk='"+pk+"']").fadeOut(150);
      },  
      error: function(datas) {
        $(".articleLongSummary[pk='"+pk+"']").attr("loadingFlag", "0");
        $("div[name='loading'][pk='"+pk+"']").fadeOut(150);
      }  
    });  
  };
});
</script>
