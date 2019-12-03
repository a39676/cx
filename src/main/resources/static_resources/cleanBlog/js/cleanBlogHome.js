/**
 * for cleanBlogIndex.jsp
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

  var getLocation = function(href) {
      var location = document.createElement("a");
      location.href = href;
      return location;
  };

  function fillFootMarker() {
    var location = getLocation(window.location.href);
    var footMarker = document.getElementById("footMarker");
    var visitCount = footMarker.getAttribute("visitCount");
    footMarker.innerHTML = ('Copyright &copy; ' + location.hostname + ' 2019 | 访问统计: ' + visitCount);
  };

  fillFootMarker();
  
});
