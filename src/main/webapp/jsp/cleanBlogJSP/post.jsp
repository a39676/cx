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
  <header class="masthead" style="background-image: url('/static_resources/cleanBlog/img/fog-4597348_1920.jpg')">
    <div class="overlay" id="readArticleLong" pk="${articleLongVO.privateKey}">
      
    </div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="post-heading">
            <h1>${articleLongVO.articleTitle}</h1>
            <h2 class="subheading"></h2>
            <span class="meta">Posted by
              <a href="#">${articleLongVO.nickName}</a>
              on ${articleLongVO.createDateString}</span>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="post-top-area">
          <c:if test="${articleLongVO.iWroteThis == true}">
            <c:if test="${articleLongVO.isDelete == true}">
              <button class="btn btn-danger btn-sm" disabled="disabled">
                <span class="badge badge-danger">已删除.</span>
              </button>
            </c:if>
            <c:if test="${articleLongVO.isDelete == false}">
              <button class="btn btn-danger btn-sm" name="delete" pk="${articleLongVO.privateKey}">
                <span style="font-size: small;">删除</span>
              </button>
            </c:if>
            <span pk="${articleLongVO.privateKey}" name="reviewResult"></span>
          </c:if>              
          <sec:authorize access="hasRole('ROLE_ADMIN')">
          <%@ include file="../articleJSP/articleReview.jsp" %>
          <%@ include file="../articleJSP/articleManager.jsp" %>
          </sec:authorize>
        </div><!-- post-top-area -->
      </div>
    </div>
  </header>

  <!-- Post Content -->
  <article>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          
          <PRE><p class="para">${articleLongVO.contentLines}</p></PRE>

          <button class="btn btn-warning btn-sm" name="showComplaint" pk="${articleLongVO.privateKey}">
            <span style="font-size: small;">需要反馈?</span>
          </button>
          <div style="display: none;" pk="${articleLongVO.privateKey}" name="complaintDiv">
            <input type="text" name="complaintReason" pk="${articleLongVO.privateKey}" placeholder="请输入">
            <div class="btn-group">
              <button class="btn btn-warning btn-sm" name="complaint" pk="${articleLongVO.privateKey}">
                <span style="font-size: small;">确定</span>
              </button>
              <button class="btn btn-primary btn-sm" name="cancelComplaint" pk="${articleLongVO.privateKey}">
                <span style="font-size: small;">取消</span>
              </button>
            </div>
            <span class="badge badge-warning" pk="${articleLongVO.privateKey}" name="complaintResult"></span>
          </div>
        </div>
      </div>
    </div>
  </article>

  <hr>

  <!-- Footer -->
  <%@ include file="./footer.jsp" %>

  <%@ include file="./cleanBlogNormalFooter.jsp" %>
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/readArticleLongV3.js'/>"></script>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleManager.js'/>"></script>
  </sec:authorize>
  <sec:authorize access="hasRole('ROLE_USER')">
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleLongUserV3.js'/>"></script>
  </sec:authorize>

</body>

</html>
