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

  var getLocation = function(href) {
      var location = document.createElement("a");
      location.href = href;
      return location;
  };

  function fillFootMarker() {
    var location = getLocation(window.location.href);
    var footMarker = document.getElementById("footMarker");
    var visitCount = footMarker.getAttribute("visitCount");
    var now = new Date();
    var year = now.getFullYear()
    footMarker.innerHTML = ('Copyright &copy; ' + location.hostname + ' ' + year + ' | 访问统计: ' + visitCount);
  };

  fillFootMarker();
  
});
