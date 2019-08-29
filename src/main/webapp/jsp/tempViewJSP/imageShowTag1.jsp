<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  <c:forEach items="${imageShowList}" var="imageShow">
    <div><label>${imageShow.imageId}</label></div>
    <div><span style="font-size: smaller">点击图片查看原图</span></div>
    <div><img src="${imageShow.imageUrl}" name="imageShow" style="width: 120px; height: 120px" fold="0"></div>
    <hr>
  </c:forEach>
  </div>
</div>

<div class="row">
  <div class="col-md-12" >
  
  </div>
</div>

</div>

</body>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {
      
      $("img[name='imageShow']").click( function() {
        var fold = $(this).attr("fold");
  
        if (fold == 0) {
          var tmpImg = new Image();
          tmpImg.src = $(this).attr("src");
          var width = tmpImg.width;
          var height = tmpImg.height;
          $(this).width(width);
          $(this).height(height);
          $(this).attr("fold", 1);
        } else {
          $(this).width(120);
          $(this).height(120);
          $(this).attr("fold", 0);
        }
      });

    });

  </script>
</footer>
</html> --%>