/**
 * for articleSearch
 */
$(document).ready(function() {

  $("button[name='setEndTimeNow']").click(function () {
    setEndTimeNow(); 
  });

  $("button[name='setStartTimeDefault']").click(function () {
    setStartTimeDefault(); 
  });
   
  function setEndTimeNow() { 
    var dt = new Date(); 
    var year = dt.getFullYear();
    var month = (dt.getMonth() + 1);
    if(month < 10) { month = "0" + month; }
    var date = dt.getDate();
    if (date < 10) { date = "0" + date; }
    var hours = dt.getHours();
    if (hours < 10) { hours = "0" + hours; }
    var minutes = dt.getMinutes();
    if (minutes < 10) { minutes = "0" + minutes; }
    var seconds = dt.getSeconds();
    if (seconds < 10) { seconds = "0" + seconds; }
    var dtStr = year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds; 
    $("input[name='endTime']").val(dtStr); 
  }

  function setStartTimeDefault() { 
    $("input[name='startTime']").val("2000-01-01 00:00:00"); 
  }

  $("button[name='adminButtomMark']").on('click', function() {
    var blogArea = $("#blogArea");
    var channelId = blogArea.attr("articleChannel");
    adminLoadArticleLongSummaryList(channelId);
  });

  // need admin role
  function adminLoadArticleLongSummaryList(channelId) {
    var blogArea = $("#blogArea");
    blogArea.attr("articleChannel", channelId);
    var url = "/article/articleLongSummaryListByChannel";
    var isDelete = false;
    if($("input[name='isDelete']").is(":checked")) {
      isDelete = true;
    }
    var isPass = false;
    if($("input[name='isPass']").is(":checked")) {
      isPass = true;
    }
    var isEdited = false;
    if($("input[name='isEdited']").is(":checked")) {
      isEdited = true;
    }
    var jsonOutput = {
      endTime:$("input[name='endTime']").val(),
      startTime:$("input[name='startTime']").val(),
      articleChannelId:uuid,
      isDelete:isDelete,
      isPass:isPass,
      isEdited:isEdited
    };
    $("#articleAreaLoadingImg").fadeIn(150);    
    if(blogArea.attr("loadingFlag") == "1") {
      $("#articleAreaLoadingImg").fadeOut(150);
      return;
    }
    blogArea.attr("loadingFlag", "1");
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
          $("input[name='endTime']").val(subArticleVO.createDateTimeString);
          // blogArea.attr("markTime", subArticleVO.createDateTimeString);
        });
        $("#articleAreaLoadingImg").fadeOut(150);
        blogArea.attr("loadingFlag", "0");
      },  
      error: function(datas) {  
        $("#articleAreaLoadingImg").fadeOut(150);
        blogArea.attr("loadingFlag", "0");
      }
    }); 
  };

  function addSummaryLineBackgroundColor(o) {
    o.style.backgroundColor = "#74b9ff";
  };
  
  function removeSummaryLineBackgroundColor(o) {
    o.style.backgroundColor = "";
  };

  function buildSummaryLine(subArticleVO) {
    var newRow = "";
    newRow += "<a href='/article/readArticleLong?pk="+subArticleVO.privateKey+"' target='_blank'>";
    newRow += "  <div class='row border border-primary rounded summaryLine' pk='"+subArticleVO.privateKey+"'";
    newRow += "  onmouseover='addSummaryLineBackgroundColor(this)'";
    newRow += "  onmouseout='removeSummaryLineBackgroundColor(this)'";
    newRow += "  style='max-height:200px;'";
    newRow += "  >";
    newRow += "    <div class='col-md-2'><img src='"+subArticleVO.imgUrl+"' style='max-width:85%; max-height:150px;'></div>";
    newRow += "    <div class='col-md-4'><h3><span class='badge badge-info' style=''>"+subArticleVO.articleTitle+"</span></h3></div>";
    newRow += "    <div class='col-md-1'><img src='"+subArticleVO.headIamgeUrl+"' style='max-width:60px; max-height:60px;'></div>";
    newRow += "    <div class='col-md-2'>";
    newRow += "      <div>"+subArticleVO.createDateString+"</div><br>";
    newRow += "      <div><span class='badge badge-pill badge-success'>comment:"+subArticleVO.commentCount+"</span></div><br>";
    newRow += "      <div><span class='badge badge-pill badge-success'>view:"+subArticleVO.viewCount+"</span></div><br>";
    newRow += "    </div>";
    newRow += "  </div>";
    newRow += "</a>";
    newRow += "<br>";
    return newRow;
  }
});