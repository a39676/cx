$(document).ready(function() {

  var pk=$("#readArticleLong").attr("pk");

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
    commentRow += "<div class='row commentInfoRow' pk='"+commentVO.pk+"'>";
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
    commentRow += "<div class='row'>";
    commentRow += "  <div class='col-lg-8 col-md-10 mx-auto'>";
    if (commentVO.isPass) {
      commentRow += "    <button class='badge badge-success' disabled='disabled'>已通过</button>";
    } else {
      commentRow += "    <button class='commentReviewButton badge badge-success' name='passComment' pk='"+commentVO.pk+"'>通过</button>";
    }
    if (commentVO.isDelete) {
      commentRow += "    <button class='badge badge-danger' disabled='disabled'>已删除</button>";
    } else {
      commentRow += "    <button class='commentReviewButton badge badge-danger' name='deleteComment' pk='"+commentVO.pk+"'>删除</button>";
    }
    if (commentVO.isReject) {
      commentRow += "    <button class='badge badge-danger' disabled='disabled'>已拒绝</button>";
    } else {
      commentRow += "    <button class='commentReviewButton badge badge-danger' name='rejectComment' pk='"+commentVO.pk+"'>拒绝</button>";
    }
    commentRow += "  </div>";
    commentRow += "  <div class='col-lg-4 col-md-10 mx-auto'>";
    
    commentRow += "  </div>";
    commentRow += "</div>";
    commentRow += "<hr>";
    return commentRow;
  }

  function reviewComment(thisButton) {
    var pk = thisButton.attr("pk");
    var operatorType = thisButton.attr("name");
    var url = "";

    if(operatorType == passComment) {
      url = "/articleAdminComment/passArticleComment";
    } else if(operatorType == rejectComment) {
      url = "/articleAdminComment/rejectArticleComment";
    } else if(operatorType == deleteComment) {
      url = "/articleAdminComment/deleteArticleComment";
    }

    var jsonOutput = {
      pk:pk,
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
        var commentVOList = datas.commentList;
        var commentList = $("#commentList");
      },  
      error: function(datas) {

      }
    }); 
  };

});