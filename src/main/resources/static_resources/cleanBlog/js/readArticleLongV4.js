$(document).ready(function() {

  var pk=$("#readArticleLong").attr("pk");

  $("span[name='evaluation']").hover(function() {
    $(this).css('cursor','pointer');
  });

  $("span[name='comment']").click(function () {
    var content = $("#message").val();
    var nickname = $("#nickname").val();
    var email = $("#email").val();
    var mobile = $("#mobile").val();

    var url = "/articleComment/createArticleComment";
    var jsonOutput = {
      pk:pk,
      content:content,
      nickname: nickname,
      email: email,
      mobile: mobile
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
          $("span[name='comment']").prop("disabled",true);
          $("span[name='commentResult']").text(datas.message);
        } else {
          $("span[name='commentResult']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
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