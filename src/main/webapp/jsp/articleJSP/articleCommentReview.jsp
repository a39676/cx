<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <%@ include file="../cleanBlogNormalHeader.jsp" %>
</head>

<body>

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('/static_resources/cleanBlog/img/fog-4597348_1920.jpg')">
    <div class="overlay" id="readArticleLong" pk="${articleLongVO.privateKey}">
    </div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="post-heading">
            <h1>${articleLongVO.articleTitle}</h1>
            <h2 class="subheading"></h2>
            <span class="meta">
              Posted by <a href="#">${articleLongVO.nickName}</a>
              on ${articleLongVO.createDateString}
            </span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Post Content -->
  <article>
    <div class="container">
      <%-- content --%>
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
          <c:if test="${articleLongVO.editDateString != null}">
            <span style="text-transform: capitalize">Last edited time: ${articleLongVO.editDateString}</span>
          </c:if>
          </sec:authorize>
          <p class="para" style="word-break:break-word;">${articleLongVO.contentLines}</p>

        </div>
      </div>
      <hr>
      <%-- comment list start --%>
      <div class="container" id="commentArea">
        <div class="row">
          <div class="container" pk="${articleLongVO.privateKey}" id="commentList" 
          markTime="${articleLongVO.createDateString}" loadingFlag="">
            
          </div>
        </div>
        <div class="row">
          <div class="col-lg-8 col-md-10 mx-auto">
            <div class="spinner-border text-warning" role="status" id="articleAreaLoadingImg">
              <span class="sr-only">Loading...</span>
            </div>
            <button class="btn btn-sm btn-primary" id="loadMoreButton"><b>Load more comment</b></button>
          </div>
        </div>
        
      </div>
      <%-- comment list end --%>
      
    </div>
  </article>

  <hr>

  <!-- Footer -->
  <%@ include file="./footer.jsp" %>

  <%@ include file="../cleanBlogNormalFooter.jsp" %>
  

  <script type="text/javascript">
$(document).ready(function() {

  $("#loadMoreButton").click(function () {
    loadCommentPage();
  });

  loadCommentPage();

  function loadCommentPage() {
    var commentList = $("#commentList");
    var pk = commentList.attr("pk");
    var markTime = commentList.attr("markTime");

    if(commentList.attr("loadingFlag") == "1") {
      return;
    }
    $("#articleAreaLoadingImg").fadeIn(100);    
    commentList.attr("loadingFlag", "1");
    var jsonOutput = {
      pk:pk,
      startTime:markTime,
      limit:20,
    };
    var url = "/articleComment/findArticleCommentPage";
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
        var commentVOList = datas.commentList;
        var commentList = $("#commentList");
        var newRow = "";
        commentVOList.forEach(function(commentVO) {
          newRow = buildComment(commentVO);
          commentList.append(newRow);
          commentList.attr("markTime", commentVO.createTimeStr);
        });
      },  
      error: function(datas) {  
      }
    }); 
    $("#articleAreaLoadingImg").fadeOut(100);
    setTimeout(function(){
      commentList.attr("loadingFlag", "0");
    }, 500);
  };

  function buildComment(commentVO) {
    var commentRow = "";
    commentRow += "<div class='row'>";
    commentRow += "  <div class='col-lg-8 col-md-10 mx-auto'>";
    commentRow += "    <p class='post-meta'>";
    commentRow += "      Post by: "+commentVO.nickName+" on: "+commentVO.createTimeStr;
    commentRow += "    </p>";
    commentRow += "  </div>";
    commentRow += "</div>";
    commentRow += "<div class='row'>";
    commentRow += "  <div class='col-lg-8 col-md-10 mx-auto'>";
    commentRow += "    <p class='para' style=;word-break:break-word;'>"
    commentRow +=        commentVO.content;
    commentRow += "    </p>";
    commentRow += "  </div>";
    commentRow += "</div>";
    commentRow += "<hr>";
    return commentRow;
  }
})
  </script>

</body>

</html>
