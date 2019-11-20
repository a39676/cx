/**
 * for home.jsp
 */
$(document).ready(function() {

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

  $(".slider-category").click(function () {
    var uuid = $(this).attr("uuid");
    loadArticleLongSummaryFirstPage(uuid);
  });


  document.getElementById("loginTag").onclick = function () {
    showLogin()
  };
  function showLogin(){ 
    var url = "/login/login";
    $.ajax({  
      type : "GET",  
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
