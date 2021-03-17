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
    <div class="container-fluid" name="createArticleLong" >
      <sec:authorize access="hasRole('ROLE_USER')">
      <div class="row">
        <div class="col-sm-12" >
          <textarea class="input form-control" id="remark" rows="1" cols="50" 
          placeholder="请输入npc tags, 多个 tags, 请以 ',' 分割 ">
            
          </textarea>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-12">
          <%@ include file="../../summernote/summernote.jsp" %>
        </div>
      </div>
      
      <div class="row">
        <div class="col-sm-12" >
          <div class="btn-group">
            <sec:authorize access="hasRole('ROLE_POSTER')">
              <textarea class="input form-control" type="text" 
              name="superAdminKey" placeholder="please insert key"></textarea>
            </sec:authorize>
            <button class="btn  btn-primary btn-sm" 
              id="upload">
              <span class="badge badge-primary">提交 image npc</span>
            </button>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-12" >
          <span id="iconUploadResult" badge badge-primary></span>
        </div>
      </div>
      </sec:authorize>
  </div> <%-- createArticleLong container --%>

</div> <%-- main row --%>
</body>

<footer>
  
  <script type="text/javascript">
    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
  </script>

  <script type="text/javascript">
    

    $(document).ready(function() {
      
      $("#upload").click(function () {
        var url = "/joyManager/npcImage/upload";
        var remark = $("#remark").val();
        var s = $('#summernote');
        var content = s.summernote('code');
    
        var jsonOutput = {
          remark:remark,
          content:content
        };
    
        var resultSpan = document.getElementById("iconUploadResult");
        resultSpan.innerHTML = "";
    
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
            resultSpan.innerHTML = datas.message;
            if(datas.result == "0") {
              document.getElementById("remark").disabled = true;
              document.getElementById("summernote").disabled = true;
              document.getElementById("upload").disabled = true;
            }
          },  
          error: function(datas) {              
          }  
        });  
      });

      $("#upload").click(function () {
        document.getElementById("remark").disabled = false;
        document.getElementById("summernote").disabled = false;
        document.getElementById("upload").disabled = false;
      });

    });
  </script>
</footer>
</html>