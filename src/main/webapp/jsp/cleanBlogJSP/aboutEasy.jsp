<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="./cleanBlogNormalHeader.jsp" %>
</head>

<body>

  <!-- Navigation -->
  <%@ include file="./nav.jsp" %>

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('${headerImg}')"> 
  <!-- <header class="masthead" style="background-image: url('/static_resources/cleanBlog/img/cat-4481566_1920.jpg')"> -->
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="page-heading">
            <h1>关于我</h1>
            <span class="subheading">码海无涯,回头是岸</span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Main Content -->
  <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
        <p>本站建设中, 还没有灵魂, 希望能收集您宝贵的意见!</p>
        <p>欢迎联系 ${email} </p>
      </div>
    </div>
  </div>

  <hr>

  <!-- Footer -->
  <%@ include file="./footer.jsp" %>
  
  <%@ include file="./cleanBlogNormalFooter.jsp" %>

</body>

</html>
