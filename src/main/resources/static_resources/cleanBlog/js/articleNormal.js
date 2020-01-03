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
  var channelButton = "";
  channelButton += "<button class='btn btn-sm channelButton'";
  channelButton += " channelId='"+channel.channelId+"'";
  channelButton += " ";
  channelButton += "onclick='loadArticleLongSummaryFirstPage(\""+channel.channelId+"\")'>";
  channelButton += channel.channelName;
  channelButton += "</button>";
  return channelButton;
}

function buildSummaryLine(subArticleVO) {
  var newRow = " ";
  newRow += " <div class='post-preview'> ";
  newRow += " <a href='/article/readArticleLong?pk="+subArticleVO.privateKey+"' target='_blank'> ";
  newRow += " <h2 class='post-title'>"+subArticleVO.articleTitle+"</h2> ";
  newRow += " <h3 class='post-subtitle'></h3> ";
  newRow += " </a> ";
  newRow += " <p class='post-meta'>Post by: "+subArticleVO.nickName+" on: "+subArticleVO.createDateString+", views: "+subArticleVO.viewCount+"</p> ";
  newRow += " </div> ";
  newRow += " <hr >"
  return newRow;
}

function loadArticleLongSummaryHot(channelId) {
  var blogArea = $("#blogArea");
  blogArea.attr("articleChannel", channelId);
  if(blogArea.attr("loadingFlag") == "1") {
    return;
  }
  $("#articleAreaLoadingImg").fadeIn(100);
  blogArea.attr("loadingFlag", "1");
  var jsonOutput = {
    articleChannelId:channelId,
    vcode:getUrlParameter('vcode'),
    isHot:"true",
  };

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
    }
  }); 
  $("#articleAreaLoadingImg").fadeOut(100);
  setTimeout(function(){}, 1200);
  blogArea.attr("loadingFlag", "0");
};

function loadArticleLongSummary(channelId) {
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
    endTime:markTime,
    isHot:"false",
  };
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
    }
  }); 
  $("#articleAreaLoadingImg").fadeOut(100);
  setTimeout(function(){}, 800);
  blogArea.attr("loadingFlag", "0");
};

function loadArticleLongSummaryFirstPage(channelId) {

  $("#blogArea").attr("markTime", "");
  $(".channelButton").prop('disabled', true);
  $("#searchByTitle").prop('disabled', true);

  var blogRowArea = $("#blogRowArea");
  blogRowArea.html("");
  loadArticleLongSummaryHot(channelId);
  setTimeout(function(){
    loadArticleLongSummary(channelId);
    setTimeout(function(){
      $(".channelButton").prop('disabled', false);
      $("#searchByTitle").prop('disabled', false);
    }, 1000);
  }, 500);
    
}

$("#loadMoreButton").click(function () {
  var channelId = $("#blogArea").attr("articleChannel");
  if(channelId != null && channelId.length > 0) {
    loadArticleLongSummary(channelId);
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
    isHot:"false",
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