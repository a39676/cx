/**
 * for cleanBlogIndex.jsp
 */
$(document).ready(function() {

  function fillAB() {
      $.ajax({  
        type : "POST",  
        async : true,
        url : "/articleBurn/articleBurnLink",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },  
        success:function(datas){
          $("#articleBurn").html(datas);
        },  
        error: function(datas) {                
        }  
      });
    }

  fillAB();
});
