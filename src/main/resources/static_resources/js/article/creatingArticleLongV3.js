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

  var editor = CKEDITOR.replace('#editor', { 
    filebrowserImageUploadUrl :"/picture/editor/upload/1",
    removePlugins:'elementspath,resize',
    codeSnippet_theme: 'zenburn',
    height:'600'
  });

});