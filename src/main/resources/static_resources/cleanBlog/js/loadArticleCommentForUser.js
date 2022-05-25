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
    commentRow += "<hr>";
    return commentRow;
  }
});
