/**
 * for cleanBlogIndex.jsp
 */
$(document).ready(function() {

  function fillAT() {
      $.ajax({  
        type : "POST",  
        async : true,
        url : "/atDemo/linkToATHome",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },  
        success:function(datas){
          $("#autoTestDemo").html(datas);
        },  
        error: function(datas) {                
        }  
      });
    }

  fillAT();
});
