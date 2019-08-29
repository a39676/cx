/**
 * for readArticleLongV3.jsp
 */
$(document).ready(function() {

  var pk=$("#readArticleLong").attr("pk");

  $("div[name='readArticleLong']").dblclick(function () {
    $(".readArticleLong").hide();
  });
  $("div[name='readArticleLong']").dblclick(function () {
    $(".readArticleLong").hide();
  });
  

  $("span[name='evaluation']").hover(function() {
    $(this).css('cursor','pointer');
  });

  $("button[name='showComplaint']").click(function () {
    $("div[name='complaintDiv']").fadeIn(150);
    $(this).fadeOut(150);
  });
  $("button[name='cancelComplaint']").click(function () {
    $("div[name='complaintDiv']").fadeOut(150);
    $("button[name='showComplaint']").fadeIn(150);
  });

  $("button[name='complaint']").click(function () {
    var complaintReason = $("input[name='complaintReason']").val();

    var url = "/article/articleLongComplaint";
    var jsonOutput = {
      pk:pk,
      complaintReason:complaintReason
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
        if(datas.result == "0") {
          $("button[name='complaint']").prop("disabled",true);
          $("button[name='cancelComplaint']").prop("disabled",true);
          $("input[name='complaintReason']").prop("disabled",true);
          $("span[name='complaintResult']").text(datas.message);
        } else {
          $("span[name='complaintResult']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });

  $("img[name='articleImage']").click(function () {
    var fold = $(this).attr("fold");

    if (fold == 0) {
      foldImage($(this));
    } else {
      unfoldImage($(this));
    }
  });

  function foldImage(imageTag) {
    var fold = imageTag.attr("fold");
    var imgMaxWidth = 900;
    var imgMaxHeight = 900;

    if (fold == 1) {
      return;
    } else {
      var tmpImg = new Image();
      tmpImg.src = imageTag.attr("src");
  
      var imgWidth = tmpImg.width;
      var imgHeight = tmpImg.height;

      var width = 0;
      var height = 0;
      
      if(imgWidth < imgMaxWidth && imgHeight < imgMaxHeight) {
        width = imgWidth;
        height = imgHeight;
      } else {
        if(imgWidth > imgHeight) {
          width = imgMaxWidth;
          var imgSizeCoefficient = imgMaxWidth / imgWidth;
          height = imgHeight * imgSizeCoefficient;
        } else {
          height = imgMaxHeight;
          var imgSizeCoefficient = imgMaxHeight / imgHeight;
          width = imgWidth * imgSizeCoefficient;
        }
      }
  
      imageTag.width(width);
      imageTag.height(height);
      imageTag.attr("fold", 1);
      imageTag.css("cursor", "zoom-out");
    }
  }

  function unfoldImage(imageTag) {
    var fold = imageTag.attr("fold");
    var imgMinWidth = 300;
    var imgMinHeight = 300;

    if (fold == 0) {
      return;
    } else {
      var tmpImg = new Image();
      tmpImg.src = imageTag.attr("src");
  
      var imgWidth = tmpImg.width;
      var imgHeight = tmpImg.height;

      var width = 50;
      var height = 50;
      
      if(imgWidth < imgMinWidth && imgHeight < imgMinHeight) {
        if(imgWidth > 0 && imgHeight > 0) {
          width = imgWidth;
          height = imgHeight;
        }
      } else {
        if(imgWidth > imgHeight) {
          width = imgMinWidth;
          var imgSizeCoefficient = imgMinWidth / imgWidth;
          height = imgHeight * imgSizeCoefficient;
        } else {
          height = imgMinHeight;
          var imgSizeCoefficient = imgMinHeight / imgHeight;
          width = imgWidth * imgSizeCoefficient;
        }
      }
  
      imageTag.width(width);
      imageTag.height(height);
      imageTag.attr("fold", 0);
      imageTag.css("cursor", "zoom-in");
    }
  }

  $("img[name='articleImage'][pk='"+pk+"']").each(function() {
    unfoldImage($(this));
  });

  $("button[name='findComment']").click(function () {
    if($("div[name='commentDetailList']").length) {
      $("div[name='commentDiv']").show();
      $("div[name='commentDetailList']").show();
      $("button[name='findComment']").fadeOut(150);
      return;
    }

    var url = "/articleComment/findArticleCommentPage";
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
        $("div[name='commentDiv']").append(datas);
        $("button[name='findComment']").fadeOut(150);
      },  
      error: function(datas) {

      }  
    });  
  });

  $("span[name='evaluation']").click(function() {
    if($("span[name='evaluation']").attr("voted") == "1") {
      $("span[name='evaluationResult']").text("已评...请勿重复操作...");
      return;
    }
    var url = "/article/insertArticleLongEvaluation";
    var evaluationCode = $(this).attr("evaluationCode");
    var evaluationCountSpan = $("span[name='evaluationCount'][evaluationCode='"+evaluationCode+"']");

    var jsonOutput = {
      pk:pk,
      evaluationCode:evaluationCode
    }

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
        if(datas.result == "0") {
          $("span[name='evaluation']").attr("voted", "1");
          evaluationCountSpan.text(parseInt(evaluationCountSpan.text()) + 1);
        } 
        $("span[name='evaluationResult']").text(datas.message);

      },  
      error: function(datas) {

      }  
    }); 
  });

});