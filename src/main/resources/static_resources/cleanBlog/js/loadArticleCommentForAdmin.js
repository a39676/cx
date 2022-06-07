$(document).ready(function() {

  var pk=$("#readArticleLong").attr("pk");

  $("#reviewMoreComment").click(function () {
    loadCommentPage();
  });

  loadCommentPage();

  function loadCommentPage() {
    var commentList = $("#commentList");
    var pk = commentList.attr("pk");
    var martTimeInput = $("input[name='commentStartTime']");
    var markTime = martTimeInput.val();
    var isPass = false;
    if($("#commentIsPass").is(":checked")) {
      isPass = true;
    }
    var isDelete = false;
    if($("#commentIsDelete").is(":checked")) {
      isDelete = true;
    }
    var isReject = false;
    if($("#commentIsReject").is(":checked")) {
      isReject = true;
    }

    if(commentList.attr("loadingFlag") == "1") {
      return;
    }
    $("#articleAreaLoadingImg").fadeIn(100);
    commentList.attr("loadingFlag", "1");
    var jsonOutput = {
      pk:pk,
      startTime:markTime,
      isPass:isPass,
      isDelete:isDelete,
      isReject:isReject,
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
          $(".commentReviewButton[name='passComment'][pk='"+commentVO.pk+"']").bind("click", reviewComment);
          $(".commentReviewButton[name='deleteComment'][pk='"+commentVO.pk+"']").bind("click", reviewComment);
          $(".commentReviewButton[name='rejectComment'][pk='"+commentVO.pk+"']").bind("click", reviewComment);
          martTimeInput.val(commentVO.createTimeStr);
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

  function reviewComment() {
    var pk = $(this).attr("pk");
    var operatorType = $(this).attr("name");
    var url = "";

    if(operatorType == "passComment") {
      url = "/articleAdminComment/passArticleComment";
    } else if(operatorType == "rejectComment") {
      url = "/articleAdminComment/rejectArticleComment";
    } else if(operatorType == "deleteComment") {
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
        if(datas.code == 0) {
          var thisButton = $("button[pk='"+pk+"'][name='"+operatorType+"']");
          thisButton.prop('disabled', true);
          thisButton.text("已"+thisButton.text());
        }
        $("textarea[pk='"+pk+"'][name='commentReviewResult']").text(datas.message);
      },
      error: function(datas) {
        $("textarea[pk='"+pk+"'][name='commentReviewResult']").text(datas);
      }
    });
  };

  $("button[name='resetCommomSearchStartTime']").click(function () {
    resetCommomSearchStartTime();
  });

  function resetCommomSearchStartTime() {
    $("input[name='commentStartTime']").val("2000-01-01 00:00:00");
  }

  function buildComment(commentVO) {
    var commentRow = "";
    commentRow += "<div class='row commentInfoRow' pk='"+commentVO.pk+"'>";
    commentRow += "  <div class='col-lg-8 col-md-10 mx-auto'>";
    commentRow += "    <h6><span><p class='post-meta badge badge-light'>";
    commentRow += "      "+commentVO.nickName+" on "+commentVO.createTimeStr;
    commentRow += "    </p></span></h6>";
    commentRow += "  </div>";
    commentRow += "</div>";
    commentRow += "<div class='row'>";
    commentRow += "  <div class='col-lg-8 col-md-10 mx-auto'>";
    commentRow += "    <h4><span><p class='post-meta badge badge-light' style=;word-break:break-word;'>"
    commentRow +=        commentVO.content;
    commentRow += "    </p></span></h4>";
    commentRow += "  </div>";
    commentRow += "</div>";
    commentRow += "<div class='row'>";
    commentRow += "  <div class='col-lg-8 col-md-10 mx-auto'>";
    if (commentVO.isPass) {
      commentRow += "    <button class='badge badge-success' disabled>已通过</button>";
    } else {
      commentRow += "    <button class='commentReviewButton badge badge-success' name='passComment' pk='"+commentVO.pk+"';>通过</button>";
    }
    if (commentVO.isDelete) {
      commentRow += "    <button class='badge badge-danger' disabled>已删除</button>";
    } else {
      commentRow += "    <button class='commentReviewButton badge badge-danger' name='deleteComment' pk='"+commentVO.pk+"';>删除</button>";
    }
    if (commentVO.isReject) {
      commentRow += "    <button class='badge badge-danger' disabled>已拒绝</button>";
    } else {
      commentRow += "    <button class='commentReviewButton badge badge-danger' name='rejectComment' pk='"+commentVO.pk+"';>拒绝</button>";
    }
    commentRow += "    <textarea pk='"+commentVO.pk+"' name='commentReviewResult' disabled='disabled'></textarea>";
    commentRow += "  </div>";
    commentRow += "  <div class='col-lg-4 col-md-10 mx-auto'>";
    commentRow += "  </div>";
    commentRow += "</div>";
    commentRow += "<hr>";
    return commentRow;
  }

});
