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
      var uuid = datas.channelList[0].uuid;
      loadArticleLongSummaryFirstPage(uuid);
    },  
    error: function(datas) {
    }  
  });  
}

function buildSubChannel(channel) {
  var channelButton = "";
  channelButton += "<button class='btn btn-sm channelButton' uuid='" + channel.uuid + "' onclick='loadArticleLongSummaryFirstPage("+channel.uuid+")'>";
  channelButton += channel.channelName;
  channelButton += "</button>";
  return channelButton;
}

function addSummaryLineBackgroundColor(o) {
  o.style.backgroundColor = "#74b9ff";
};

function removeSummaryLineBackgroundColor(o) {
  o.style.backgroundColor = "";
};

function buildSummaryLine(subArticleVO) {
  var newRow = "";
  newRow += "<a href='/article/readArticleLong?pk="+subArticleVO.privateKey+"' target='_blank'>";
  newRow += "  <div class='post-preview summaryLine' pk='"+subArticleVO.privateKey+"'";
  newRow += "  onmouseover='addSummaryLineBackgroundColor(this)'";
  newRow += "  onmouseout='removeSummaryLineBackgroundColor(this)'";
  newRow += "  style='max-height:200px;'";
  newRow += "  >";
  newRow += "    <div class='col-md-2'><br><img src='"+subArticleVO.imgUrl+"' style='max-width:85%; max-height:150px;'></div>";
  newRow += "    <div class='col-md-4'><br><h3><span class='badge badge-info' style=''>"+subArticleVO.articleTitle+"</span></h3></div>";
  newRow += "    <div class='col-md-1'><br><img src='"+subArticleVO.headIamgeUrl+"' style='max-width:60px; max-height:60px;'></div>";
  newRow += "    <div class='col-md-2'>";
  newRow += "      <br>";
  newRow += "      <div>"+subArticleVO.createDateString+"</div><br>";
  newRow += "      <div><span class='badge badge-pill badge-success'>comment:"+subArticleVO.commentCount+"</span></div><br>";
  newRow += "      <div><span class='badge badge-pill badge-success'>view:"+subArticleVO.viewCount+"</span></div><br>";
  newRow += "    </div>";
  newRow += "  </div>";
  newRow += "</a>";
  newRow += "<br>";
  return newRow;
}

function loadArticleLongSummaryHot(uuid) {
  var blogArea = $("#blogArea");
  blogArea.attr("articleChannel", uuid);
  $("#articleAreaLoadingImg").fadeIn(150);    
  if(blogArea.attr("loadingFlag") == "1") {
    $("#articleAreaLoadingImg").fadeOut(150);
    return;
  }
  blogArea.attr("loadingFlag", "1");
  var jsonOutput = {
    articleChannelUUID:uuid,
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
      blogRowArea.html("");
      var newRow = "";
      articleLongSummaryVOList.forEach(function(subArticleVO) {
        newRow = buildSummaryLine(subArticleVO);
        blogRowArea.append(newRow);
        blogArea.attr("markTime", subArticleVO.createDateTimeString);
      });
      $("#articleAreaLoadingImg").fadeOut(150);
    },  
    error: function(datas) {  
      $("#articleAreaLoadingImg").fadeOut(150);
    }
  }); 
  blogArea.attr("loadingFlag", "0");
};

function loadArticleLongSummary(uuid) {
  var blogArea = $("#blogArea");
  blogArea.attr("articleChannel", uuid);
  var markTime = blogArea.attr("markTime");
  $("#articleAreaLoadingImg").fadeIn(150);    
  if(blogArea.attr("loadingFlag") == "1") {
    $("#articleAreaLoadingImg").fadeOut(150);
    return;
  }
  blogArea.attr("loadingFlag", "1");
  var jsonOutput = {
    articleChannelUUID:uuid,
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
      $("#articleAreaLoadingImg").fadeOut(150);
    },  
    error: function(datas) {  
      $("#articleAreaLoadingImg").fadeOut(150);
    }
  }); 
  blogArea.attr("loadingFlag", "0");
};

function loadArticleLongSummaryFirstPage(uuid) {
  $("#blogArea").attr("markTime", "");
  loadArticleLongSummaryHot(uuid);
  loadArticleLongSummary(uuid);
}

$("#loadMoreButton").click(function () {
  var uuid = $("#blogArea").attr("articleChannel");
  loadArticleLongSummary(uuid);
});

$("#createNewArticle").click(function () {
  var win = window.open("/article/creatingArticleLong", '_blank');
  win.focus();
});