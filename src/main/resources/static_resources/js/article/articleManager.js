/**
 * for readArticleLongV3.jsp
 */
$(document).ready(function() {

  var pk=$("#readArticleLong").attr("pk");

  $("button[name='trySetArticleHot']").click(function () {
    var pk = $(this).attr("pk");

    $("button[name='trySetArticleHot'][pk='"+pk+"']").fadeOut(300);
    $(".setArticleHotOption[pk='"+pk+"']").fadeIn(300);
  });

  $("button[name='setArticleHot']").click(function () {
    var url = "/articleAdmin/setArticleHot";

    var jsonOutput = {};
    jsonOutput.pk = pk;
    jsonOutput.hotMinutes = $("select[name='setArticleHotMinutes'][pk='"+pk+"'] option:selected").val();
    jsonOutput.hotLevel = $("input[name='setArticleHotLevel'][pk='"+pk+"']").val();

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
        // if(datas.result == 0) {
        //   $("button[name='setArticleHot'][pk='"+pk+"']").prop("disabled",true);
        // }
        $("span[name='manageResult'][pk='"+pk+"']").text(datas.message);
      },  
      error: function(datas) {

      }  
    }); 
  });

  $("button[name='passByAdmin']").click(function () {
    var url = "/articleAdmin/passArticle";
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
          $("button[name='passByAdmin']").prop("disabled",true);
          $("button[name='rejectByAdmin']").prop("disabled",true);
          $("div[name='changeChannelDiv']").remove();
          $("span[name='reviewResult']").text("已通过");
        } else {
          $("span[name='reviewResult']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });
  $("button[name='rejectByAdmin']").click(function () {
    var url = "/articleAdmin/rejectArticle";
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
          $("button[name='passByAdmin']").prop("disabled",true);
          $("button[name='rejectByAdmin']").prop("disabled",true);
          $("div[name='changeChannelDiv']").remove();
          $("span[name='reviewResult']").text("已拒绝");
        } else {
          $("span[name='reviewResult']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });
  $("button[name='deleteByAdmin']").click(function () {
    var url = "/articleAdmin/deleteArticle";
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
          $("button[name='deleteByAdmin']").prop("disabled",true);
          $("span[name='reviewResult']").text("已删除");
        } else {
          $("span[name='reviewResult']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });

  $("button[name='findChannel']").click(function () {
    var url = "/articleAdmin/findArticleChannel";
    var jsonOutput = {
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
        var channelSelector = $("select[name='findChannel'][pk='"+pk+"']");
        channelSelector.empty();
        channelSelector.append($("<option></option>").attr("value", "").text("请问要转到哪里?"));
        channelSelector.show();
        $("button[name='changeChannel'][pk='"+pk+"']").show();
        $.each(datas, function(index, channel) {
          channelSelector.append($("<option></option>").attr("value", channel.uuid).text(channel.channelName));
        });
        $("button[name='findChannel']").hide();
      },  
      error: function(datas) {

      }  
    });  
  });

  $("button[name='changeChannel']").click(function () {
    var uuid = $("select[name='findChannel'][pk='"+pk+"'] option:selected").val();

    var url = "/articleAdmin/changeChannel";
    var jsonOutput = {
      pk:pk,
      uuid:uuid
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
          $(".row").remove();  
        } else {
          $("span[name='reviewResult']").text(datas.message);
        }
      },  
      error: function(datas) {

      }  
    });  
  });
  
});