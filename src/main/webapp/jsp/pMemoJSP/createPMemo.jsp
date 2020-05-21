<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 因需要使用富文本编辑器, 特别使用指定的库 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<sec:csrfMetaTags />
<title>${ title }</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.js"></script>

</head>
<body>
<div class="container-fluid">

<div class="row">
  <div class="col-sm-12" >
    <div id="summernote"></div>
    <script>
      $('#summernote').summernote({
        tabsize: 2,
        height: 100
      });
    </script>
  </div>
</div>

<div class="row">
  <div class="col-sm-12">
    <input type="text" name="" id="validDateStr" placeholder="validDateStr"><br>
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

        console.log(jsonOutput);

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