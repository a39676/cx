/**
 * for creatingArticleLongV3.jsp
 */
$(document).ready(function() {

  // createArticleLong
  $("button[name='createArticleLong']").click(function () {
    var url = "/article/createArticleLong";
    var title = $("textarea[name='articleTitle']").val();
    var content = $("textarea[name='creatingArticleLong']").val();
    var uuid = $("select[name='channelList'] option:selected").val();

    var jsonOutput = {
      uuid:uuid,
      title:title,
      content:content
    };

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

  $("button[name='createArticleLongEditor']").click(function () {
    var url = "/article/createArticleLong";
    var title = $("textarea[name='articleTitle']").val();
    var s = $('#summernote');
    var content = s.summernote('code');
    var uuid = $("select[name='channelList'] option:selected").val();

    var jsonOutput = {
      uuid:uuid,
      title:title,
      content:content
    };

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


});