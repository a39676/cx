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
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h2>${title}</h2>
            <span class="subheading">${subheading}</span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Main Content -->
  <div class="container-fluid">
    <div class="row">
      <!-- channels -->
      <div class="col-md-2 mx-auto">
        <sec:authorize access="hasRole('ROLE_USER')">
          <button class="btn btn-sm btn-warning" id="createNewArticle"><b>Create new</b></button>
          <div class="row"><hr style="color: rgba(55, 66, 250,1.0)"></div>
        </sec:authorize>
        <div class="btn-group-vertical" id="articleChannels"></div>
      </div>
      <div class="col-md-8 mx-auto">
        <div class="container-fluid">
          <div class="row" id="blogArea" markTime="" loadingFlag="" articleChannel="" keyword="">
            <div class="col-md-12 mx-auto" id="blogRowArea"></div>
          </div>
          <div class="row">
            <div class="col-md-12 mx-auto">
              <div class="spinner-border text-warning" role="status" id="articleAreaLoadingImg">
                <span class="sr-only">Loading...</span>
              </div>
              <button class="btn btn-sm btn-success" id="loadMoreButton"><b>LOAD MORE</b></button>
            </div>
            <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
            <%-- 管理员专用搜索框 --%>
            <%@ include file="../articleJSP/articleSearchV3.jsp" %>
            </sec:authorize>
          </div>
        </div>
      </div>
      <div class="col-md-2 mx-auto">
        <span style="color: rgb(255, 156, 0); background-color: rgb(255, 255, 255);">Donate打赏</span>
        <img src="${donateImgUrl}" alt="" style="width:100px; height:100px;">
      </div>

    </div>
  </div>

  <hr>

  <!-- Footer -->
  <%@ include file="./footer.jsp" %>

  <!-- SCIPTS -->
  <%@ include file="./cleanBlogNormalFooter.jsp" %>
  <script type="text/javascript" src="/static_resources/cleanBlog/js/articleNormal.js"></script>
  <script type="text/javascript" src="/static_resources/js/autotest/autotestLinkFillToBlogHome.js"></script>
  <script type="text/javascript" src="/static_resources/js/article/articleBurnLinkFillToBlogHome.js"></script>
  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
  <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/articleSearch.js'/>"></script>
  </sec:authorize>

  <script type="text/javascript">
    $(document).ready(function() {

    })
  </script>
</body>

</html>
