loadArticleChannels();

var getUrlParameter = function getUrlParameter(sParam) {
  var sPageURL = decodeURIComponent(window.location.search.substring(1)),
  sURLVariables = sPageURL.split('&'),
  sParameterName,
  i;
  for (i = 0; i < sURLVariables.length; i++) {
    sParameterName = sURLVariables[i].split('=');
    if (sParameterName[0] === sParam) {
      return sParameterName[1] === undefined ? true : sParameterName[1];
    }
  }
};

function loadArticleChannels() {
  var url = "/article/findChannels";
  $.ajax({
    type : "POST",
    async : true,
    url : url,
    // data: JSON.stringify(jsonOutput),
    cache : false,
    dataType: 'json',
    contentType: "application/json;",
    beforeSend: function(xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    success:function(datas){
      datas.channelList.forEach(function(channel) {
        $("#articleChannels").append(buildSubChannel(channel));
      });
      var channelId = datas.channelList[0].channelId;
      setTimeout(function(){}, 800);
      loadArticleLongSummaryFirstPage(channelId);
    },
    error: function(datas) {
    }
  });
}
function buildSubChannel(channel) {
  var channelElement = "";
  channelElement += "<button class='btn btn-sm btn-primary channelButton'";
  channelElement += " channelId='"+channel.channelId+"'";
  channelElement += " ";
  channelElement += "onclick='loadArticleLongSummaryFirstPage(\""+channel.channelId+"\")'>";
  channelElement += channel.channelName;
  channelElement += "</button>";
  return channelElement;
}

function buildSummaryLine(subArticleVO) {
  var newRow = " ";
  newRow += " <tr> ";
  newRow += " <td> ";
  newRow += " <a href='/article/readArticleLong?pk="+subArticleVO.privateKey+"' target='_blank'> ";
  newRow += " <h2 class=''>";
  if (subArticleVO.isHot) {
    newRow += " <span style='text-decoration-line: underline; color: rgb(255, 156, 0); background-color: rgb(255, 255, 255);'>"+subArticleVO.articleTitle+"</span> ";
  } else {
    newRow += subArticleVO.articleTitle;
  }
  newRow += " </h2> ";
  newRow += " <h3 class=''></h3> ";
  newRow += " </a> ";
  newRow += " <p class=''>"
  newRow += ""+subArticleVO.createDateString+", views: "+subArticleVO.viewCount+", comments: "+subArticleVO.commentCount;
  if (subArticleVO.hasCommentNotReview) {
    newRow += " <br>CommentReviewWaiting ";
  }
  newRow += " </p>";
  newRow += " </td> ";
  newRow += " <td> ";
  // if has img
  newRow += " ";
  newRow += " </td> ";
  newRow += " </tr> ";
  return newRow;
}

function loadArticleLongSummary(channelId, withHot) {
  var blogArea = $("#blogArea");
  blogArea.attr("articleChannel", channelId);
  var markTime = blogArea.attr("markTime");
  if(blogArea.attr("loadingFlag") == "1") {
    return;
  }
  $("#articleAreaLoadingImg").fadeIn(100);
  blogArea.attr("loadingFlag", "1");
  var jsonOutput = {
    articleChannelId:channelId,
    isHot:withHot,
  };
  if(withHot == "true") {
    jsonOutput['vp'] = getUrlParameter('vp');
  } else {
    jsonOutput['endTime'] = markTime;
  }
  var url = "/article/articleLongSummaryListByChannel";
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
      // var json = JSON.parse(datas);
      var articleLongSummaryVOList = datas.articleLongSummaryVOList;
      var blogRowArea = $("#blogRowArea");
      var newRow = "";
      articleLongSummaryVOList.forEach(function(subArticleVO) {
        newRow = buildSummaryLine(subArticleVO);
        blogRowArea.append(newRow);
        blogArea.attr("markTime", subArticleVO.createDateTimeString);
      });
    },
    error: function(datas) {
      location.reload();
    }
  });
  $("#articleAreaLoadingImg").fadeOut(100);
  setTimeout(function(){
    blogArea.attr("loadingFlag", "0");
  }, 500);
};

function loadArticleLongSummaryFirstPage(channelId) {
  $("#blogArea").attr("markTime", "");
  $(".channelButton").prop('disabled', true);
  $(".channelButton").removeClass("btn-warning");
  $(".channelButton").addClass("btn-primary");
  $("#searchByTitle").prop('disabled', true);
  $(".channelButton[channelId='"+channelId+"'").removeClass("btn-primary");
  $(".channelButton[channelId='"+channelId+"'").addClass("btn-warning");
  var blogRowArea = $("#blogRowArea");
  blogRowArea.html("");
  setTimeout(function(){
    loadArticleLongSummary(channelId, "true");
    setTimeout(function(){
      $(".channelButton").prop('disabled', false);
      $("#searchByTitle").prop('disabled', false);
    }, 1000);
  }, 500);
}

$("#loadMoreButton").click(function () {
  var channelId = $("#blogArea").attr("articleChannel");
  if(channelId != null && channelId.length > 0) {
    loadArticleLongSummary(channelId, "false");
  } else {
    searchArticleLongSummary(false);
  }
});

$("#createNewArticle").click(function () {
  var win = window.open("/article/creatingArticleLong", '_blank');
  win.focus();
});

$("#searchByTitle").click(function () {
  searchArticleLongSummary(true);
});

function searchArticleLongSummary(firstSearchFlag) {
  $(".channelButton").prop('disabled', true);
  $("#searchByTitle").prop('disabled', true);
  var newKeyword = $("#searchKeyWord").val();
  var blogArea = $("#blogArea");
  var blogRowArea = $("#blogRowArea");
  var channelId = blogArea.attr("articleChannel");
  if (firstSearchFlag) {
    blogArea.attr("articleChannel", "");
    blogArea.attr("markTime", "");
    blogRowArea.html("");
  } else {
  }
  var markTime = blogArea.attr("markTime");
  var oldKeyword = blogArea.attr("keyword");
  if(blogArea.attr("loadingFlag") == "1") {
    return;
  }
  $("#articleAreaLoadingImg").fadeIn(100);
  blogArea.attr("loadingFlag", "1");
  var jsonOutput = {
    endTime:markTime,
    isHot:firstSearchFlag,
  };
  if (firstSearchFlag) {
    jsonOutput["title"] = newKeyword;
  } else {
    jsonOutput["title"] = oldKeyword;
  }
  var url = "/article/articleLongSummaryListByChannel";
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
      var articleLongSummaryVOList = datas.articleLongSummaryVOList;
      blogArea.attr("keyword", newKeyword);
      var blogRowArea = $("#blogRowArea");
      var newRow = "";
      articleLongSummaryVOList.forEach(function(subArticleVO) {
        newRow = buildSummaryLine(subArticleVO);
        blogRowArea.append(newRow);
        blogArea.attr("markTime", subArticleVO.createDateTimeString);
      });
    },
    error: function(datas) {
    }
  });
  $("#articleAreaLoadingImg").fadeOut(100);
  setTimeout(function(){}, 800);
  blogArea.attr("loadingFlag", "0");
  setTimeout(function(){
    $(".channelButton").prop('disabled', false);
    $("#searchByTitle").prop('disabled', false);
  }, 1000);
}
