<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<sec:csrfMetaTags />
<title>${ title }</title>
</head>
<body>
<div class="container-fluid">

<div class="row">
  <div class="col-sm-12" >
    <%@ include file="../summernote/summernote.jsp" %>
  </div>
</div>

<div class="row">
  <div class="col-sm-12" >
    <button class="btn  btn-primary btn-sm" id="createPNote"><span style="font-size: small;">提交</span></button>
  </div>
</div>

<div class="row">
  <div class="col-sm-12" >
    <div id="resultView">
      <div><span style="font-size: small;color: red" name="createNoteResult"></span></div>
    </div>
  </div>
</div>

<div id="sourceNoteVO" disabled="disabled" style="display: none;" contentLines='${noteVO.content}'></div>

</div>
</body>

<footer>
  
  <script type="text/javascript">
    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
  </script>

  <script type="text/javascript">

    $(document).ready(function() {

      $("#createPNote").click( function() {
        createPNote();
      });
  
      function createPNote() {
        
        var url = "/pUrgeNote/edit";
        var s = $('#summernote');
        var content = s.summernote('code');

        var jsonOutput = {
          content : content,
        };

        $("span[name='createNoteResult']").text("");
  
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
            $("span[name='createNoteResult']").text(datas.message);
          },  
          error: function(datas) {
            $("span[name='createNoteResult']").text(datas);
          }  
        });  
      };

      var contentLines = $("#sourceNoteVO").attr("contentLines");
      $("#summernote").summernote("code", contentLines);

    });

  </script>
</footer>
</html>