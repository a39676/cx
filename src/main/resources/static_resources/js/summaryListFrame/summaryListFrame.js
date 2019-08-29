$(document).ready(function() {

  var uuid = $("#dynamicArea").attr("articleChannel");
  var pageSize = 10;

  if ($("input[name='pageSize']").length) {
    pageSize = $("input[name='pageSize']").val();
  }

  $("button[name='showArticleCreator']").click(function () {
    $(".articleLongCreatingSubArea").show();
  });
  $("button[name='closeArticleCreator']").click(function () {
    $(".articleLongCreatingSubArea").hide();
  });

  $("input[name='pageNo']").change(function() {
    // var pageNo = $(this).val();
    loadArticleLongSummaryPage();
  });

  // mark
  function loadArticleLongSummaryPage() {
    var pageNo = $("input[name='pageNo']").val();
    var loadingFlag = $("div[name='channelButtonGroup']").attr("loadingFlag");
    if (loadingFlag == 1) {
      return;
    }

    var url = "/article/articleLongSummaryPageByChannel";
    if ($("input[name='pageSize']").length) {
      pageSize = $("input[name='pageSize']").val();
    }
    var jsonOutput = {
      pageNo:pageNo,
      pageSize:pageSize,
      articleChannelUUID:uuid
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
        $("div[name='articleLongSummaryList']").append(datas);
        $("div[name='channelButtonGroup']").attr("loadingFlag", "0");
      },  
      error: function(datas) {  
        $("button[name='bottom']").show();
        $("div[name='channelButtonGroup']").attr("loadingFlag", "0");
      }  
    });  
  };


  // 准备迁往 like or hate 子页面
  // $(".likeOrHateThisChannel").click(function () {
  //   $(".likeOrHateThisChannel").fadeOut();
  //   if($(this).attr("likeOrHate") == 1) {
  //     $("span[name='likeOrHateEnsure']").text("真的喜欢!");
  //     $("button[name='likeOrHateEnsure']").attr("likeOrHate", 1);
  //     $("button[name='likeOrHateEnsure']").addClass("btn-success");
  //     $("span[name='likeOrHateEnsure']").addClass("badge-success");
  //     $("button[name='likeOrHateEnsure']").removeClass("btn-danger");
  //     $("span[name='likeOrHateEnsure']").removeClass("badge-danger");
  //     $(".likeOrHateEnsure").fadeIn();
  //   } else if($(this).attr("likeOrHate") == -1){
  //     $("span[name='likeOrHateEnsure']").text("真的讨厌!");
  //     $("button[name='likeOrHateEnsure']").attr("likeOrHate", -1);
  //     $("button[name='likeOrHateEnsure']").addClass("btn-danger");
  //     $("span[name='likeOrHateEnsure']").addClass("badge-danger");
  //     $("button[name='likeOrHateEnsure']").removeClass("btn-success");
  //     $("span[name='likeOrHateEnsure']").removeClass("badge-success");
  //     $(".likeOrHateEnsure").fadeIn();
  //   } 
  // });

  // $("button[name='wrongClick']").click(function () {
  //   $(".likeOrHateEnsure").fadeOut();
  //   $(".likeOrHateThisChannel").fadeIn();
  // });

  // $("button[name='likeOrHateEnsure']").click(function () {
  //   var uuid = $(this).attr("articleChannel");
  //   var likeOrHate = $(this).attr("likeOrHate");
  //   var url = "/article/likeOrHateThisChannel";

  //   var jsonOutput = {
  //     uuid:uuid
  //   };
  //   if(likeOrHate == "1") {
  //     jsonOutput.likeOrHate = 1
  //   } else {
  //     jsonOutput.likeOrHate = -1
  //   }


  //   $.ajax({  
  //     type : "POST",  
  //     async : true,
  //     url : url,  
  //     data: JSON.stringify(jsonOutput),
  //     cache : false,
  //     contentType: "application/json",
  //     dataType: "json",
  //     timeout:50000,  
  //     beforeSend: function(xhr) {
  //       xhr.setRequestHeader(csrfHeader, csrfToken);
  //     },
  //     success:function(datas){
  //       if(datas.result == "0") {
  //         $(".likeOrHateEnsure").fadeOut();
  //       }
  //       if(likeOrHate == "-1") {
  //         jsonOutput.likeOrHate = 1
  //         $(".articleLongSummaryListByChannel[articleChannel='"+uuid+"']").remove();
  //         $("button[name='articleChannelButton'][uuid='"+uuid+"']").remove();
  //       }
  //     },  
  //     error: function(datas) {              
  //     }  
  //   });  
  // });
  // 准备迁往 like or hate 子页面


  // createArticleLong
  $("button[name='createArticleLong']").click(function () {
    var url = "/article/createArticleLong";
    var title = $("textarea[name='articleTitle']").val();
    var content = $("textarea[name='creatingArticleLong']").val();

    var jsonOutput = {
      uuid:uuid,
      title:title,
      content:content
    };

    // mark
    // <sec:authorize access="hasRole('ROLE_POSTER')">
    // var superAdminKey = $("textarea[name='superAdminKey']").val();
    // if(superAdminKey.length > 0) {
    //   jsonOutput.superAdminKey = superAdminKey;
    // }
    // </sec:authorize>

    var resultSpan = $("span[name='createArticleResult']");
    resultSpan.text("");

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
        console.log(datas);
        resultSpan.text(datas.message);
        if(datas.result == "0") {
          $("textarea[name='articleTitle']").val("");
          $("textarea[name='creatingArticleLong']").val("");
        }
      },  
      error: function(datas) {              
      }  
    });  
  });

  // mark
  // 准备迁移到管理员专用搜索框
  // <sec:authorize access="hasRole('ROLE_ADMIN')">
  // $("button[name='adminButtomMark']").on('click', function() {
  //   adminLoadArticleLongSummaryList();
  // });

  // function adminLoadArticleLongSummaryList() {
  //   $("button[name='adminButtomMark']").hide();
  //   var url = "/article/articleLongSummarySubListByChannel";
  //   var isDelete = false;
  //   if($("input[name='isDelete']").is(":checked")) {
  //     isDelete = true;
  //   }
  //   var isPass = false;
  //   if($("input[name='isPass']").is(":checked")) {
  //     isPass = true;
  //   }
  //   var isEdited = false;
  //   if($("input[name='isEdited']").is(":checked")) {
  //     isEdited = true;
  //   }

  //   var jsonOutput = {
  //     endTime:$("input[name='endTime']").val(),
  //     startTime:$("input[name='startTime']").val(),
  //     articleChannelUUID:uuid,
  //     isDelete:isDelete,
  //     isPass:isPass,
  //     isEdited:isEdited
  //   };

  //   $.ajax({  
  //     type : "POST",  
  //     async : true,
  //     url : url,  
  //     data: JSON.stringify(jsonOutput),
  //     cache : false,
  //     contentType: "application/json",
  //     // dataType: "json",
  //     timeout:50000,  
  //     beforeSend: function(xhr) {
  //       xhr.setRequestHeader(csrfHeader, csrfToken);
  //     },
  //     success:function(datas){
  //       $("button[name='adminButtomMark']").show();
  //       $("div[name='articleLongSummaryList']").append(datas);
  //     },  
  //     error: function(datas) {  
  //       $("button[name='adminButtomMark']").show();
  //     }  
  //   });  
  // };
  // 准备迁移到管理员专用搜索框
  // </sec:authorize>  
  });