/**
 * for bonaHeader.jsp
 */
$(document).ready(function() {

  /**
    登录/登出
  */

  $("#loginTag").click(function () {
    showLogin();
  });

  function showLogin(){ 
    var url = "/login/login";
    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      success:function(datas){
        $("#dynamicLoginDiv").html(datas);
      },  
      error: function(datas) {                
      }  
    });  
  };
  
});