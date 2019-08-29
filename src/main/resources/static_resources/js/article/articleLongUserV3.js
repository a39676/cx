/**
 * for readArticleLongV3.jsp
 */
$(document).ready(function() {

  var pk=$("#readArticleLong").attr("pk");

  $("button[name='delete']").click(function () {
    var url = "/article/deleteArticle";
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
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        if(datas.result == "0") {
          $("button[name='delete']").prop("disabled",true);
          $("span[name='reviewResult']").text("已删除");
        } else {
          $("span[name='reviewResult']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });
});