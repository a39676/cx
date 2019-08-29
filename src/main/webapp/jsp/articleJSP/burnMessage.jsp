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
    <span style="font-size: small;">信息已销毁</span>
  </div>
</div>
<hr>

<div class="row">
  <div class="col-md-2">
    <button class="btn btn-primary  btn-sm" name="createBurnMessage">
      <span style="font-size: small;" >点此新建另一条信息</span>
    </button>
  </div>
</div>

<div class="row">
  <div class="col-md-12">
    <div id="createBurnMessage"></div>
  </div>
</div>

</div>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("button[name='createBurnMessage']").click(function() {
        var url="${pageContext.request.contextPath}/article/createBurnMessage"
        $.ajax({  
          type : "GET",  
          async : true,
          url : url,  
          success:function(datas){  
              $("#createBurnMessage").html(datas);
          },  
          error: function(datas) {  
              
          }  
        });  
      });
      
    });

  </script>
</footer>
</html>