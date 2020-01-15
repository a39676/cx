<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="./cleanBlogNormalHeader.jsp" %>
  <script type="text/javascript" async="" src="https://ssl.google-analytics.com/ga.js"></script>
</head>

<body>

  <!-- Navigation -->
  <%@ include file="./nav.jsp" %>

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('${headerImg}')"> 
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h2>${title}</h2>
            <span class="subheading">${subheading}</span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Main Content -->
  <div class="container-fluid">
    <div class="row">
      <!-- channels -->
      <div class="col-md-2 mx-auto">
        <sec:authorize access="hasRole('ROLE_USER')">
          <button class="btn btn-sm btn-warning" id="createNewArticle"><b>Create new</b></button>
          <div class="row"><hr style="color: rgba(55, 66, 250,1.0)"></div>
        </sec:authorize>
        <div class="btn-group-vertical" id="articleChannels"></div>
      </div>
      <div class="col-md-8 mx-auto">
        <div class="container-fluid">
          <div class="row" id="blogArea" markTime="" loadingFlag="" articleChannel="" keyword="">
            <div class="col-md-12 mx-auto" id="blogRowArea"></div>
          </div>
          <div class="row">
            <div class="col-md-12 mx-auto">
              <div class="spinner-border text-warning" role="status" id="articleAreaLoadingImg">
                <span class="sr-only">Loading...</span>
              </div>
              <button class="btn btn-sm btn-success" id="loadMoreButton"><b>LOAD MORE</b></button>
            </div>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <%-- 管理员专用搜索框 --%>
            <%@ include file="../articleJSP/articleSearchV3.jsp" %>
            </sec:authorize>
          </div>
        </div>
      </div>
      <div class="col-md-2 mx-auto">
      </div>
      
    </div>
  </div>

  <hr>

  <!-- Footer -->
  <%@ include file="./footer.jsp" %>

  <!-- SCIPTS -->
  <%@ include file="./cleanBlogNormalFooter.jsp" %>
  <%-- <script type="text/javascript" src="/static_resources/cleanBlog/js/articleNormal.js"></script> --%>
  <script type="text/javascript" src="/static_resources/js/autotest/autotestLinkFillToBlogHome.js"></script>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
  <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/articleSearch.js'/>"></script>
  </sec:authorize>

  <script type="text/javascript">
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
    jsonOutput['vcode'] = getUrlParameter('vcode');
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
  $("#searchByTitle").prop('disabled', true);

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
  </script>
</body>

</html>
