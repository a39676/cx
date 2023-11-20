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
  <div class="col-sm-12">
    <input type="text" name="" id="validDateStr" value="${defaultValidDateTime}"><br>
    <input type="text" name="" id="redisKeyValue" placeholder="redisKeyValue">
  </div>
</div>

<div class="row">
  <div class="col-sm-12" >
    <button class="btn  btn-primary btn-sm" id="createPMemo"><span style="font-size: small;">提交</span></button>
  </div>
</div>

<div class="row">
  <div class="col-sm-12" >
    <div id="resultView">
      <div><span style="font-size: small;color: red" name="createArticleResult"></span></div>
    </div>
  </div>
</div>


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

      $("#createPMemo").click( function() {
        createPMemo();
      });
  
      function createPMemo() {
        
        var url = "/pMemo/set";
        var validDateStr = $("#validDateStr").val();
        var redisKeyValue = $("#redisKeyValue").val();
        var s = $('#summernote');
        var content = s.summernote('code');

        var jsonOutput = {
          content : content,
          validDateStr : validDateStr,
          redisKeyValue : redisKeyValue
        };

        $("span[name='createArticleResult']").text("");
  
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
            $("span[name='createArticleResult']").text(datas.message);
          },  
          error: function(datas) {
            $("span[name='createArticleResult']").text(datas);
          }  
        });  
      };

    });

  </script>
</footer>
</html>