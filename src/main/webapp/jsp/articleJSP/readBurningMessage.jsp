<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">

<div class="row">
  <div class="col-md-12" >
    <label>此信息内容为其他用户产生,切莫轻信.</label><br>
  </div>
</div>

<hr>

<div class="row">
  <div class="col-md-12" >
    ${content}
  </div>
</div>

<hr>

<div class="row">
  <div class="col-md-12" >
    <span style="font-size: small;" name="comment"></span>
  </div>
</div>

<hr>

<div class="row">
  <div class="col-md-12">
    <span style="font-size: small;" name="burnUri"></span>
  </div>
</div>

<div class="row">
  <div class="col-md-2">
    <button class="btn btn-danger btn-sm" name="destoryButton" style="display: none">
      <span style="font-size: small;" >马上销毁本信息</span>
    </button>
  </div>
</div>

<hr>

<div class="row">
  <div class="col-md-2">
    <a href="/articleBurn/createBurnMessage">点此新建另一条信息</a>
  </div>
</div>

<hr>

<div class="row">
  <div class="col-md-12">
    <div id="createBurnMessage"></div>
  </div>
</div>


</div>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      var urlPerfix = window.location.host;
      var burnUri = "${burnUri}";
      
      var remainingReadCount = ${remainingReadCount};
      if(remainingReadCount > 0) {
        $("span[name='comment']").text("再读( " + remainingReadCount + " )次后销毁");
        $("span[name='burnUri']").append("访问此地址销毁信息: ");
        $("span[name='burnUri']").append("<br>");
        $("span[name='burnUri']").append(urlPerfix);
        $("span[name='burnUri']").append(burnUri);
        $("button[name='destoryButton']").show();
      } else if (remainingReadCount == 0) {
        $("span[name='comment']").text("这是最后一次阅读");
      } 

      $("button[name='destoryButton']").click(function() {
        destoryButton();
      });

      function destoryButton() {
      
        var url = burnUri;
  
        $.ajax({  
          type : "GET",  
          async : true,
          url : url,  
          cache : false,
          timeout:50000,  
          success:function(datas){
            $("span[name='burnUri']").text("信息已销毁");
            $("input[name='content']").val("");
            $("span[name='comment']").text("");
          },  
          error: function(datas) {  
            
          }  
        });  
      };

    });

  </script>
</footer>
</html>