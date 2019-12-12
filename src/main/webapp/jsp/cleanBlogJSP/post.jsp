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
    </div>
  </header>

  <header class="masthead">
    <div class="container">
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
              <a href="/article/editArticleLong?pk=${articleLongVO.privateKey}" target="_blank"><span class="badge badge-info">编辑</span></a>
            </c:if>
            <span pk="${articleLongVO.privateKey}" name="reviewResult"></span>
          </c:if>              
          <sec:authorize access="hasRole('ROLE_ADMIN')">
          <%@ include file="../articleJSP/articleReview.jsp" %>
          <%@ include file="../articleJSP/articleManager.jsp" %>
          </sec:authorize>
          <sec:authorize access="hasRole('ROLE_ADMIN')">
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
          
          <p class="para" style="word-break:break-word;">${articleLongVO.contentLines}</p>

        </div>
      </div>
      <hr>
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="page-heading">
            <h2>需要反馈?</h2>
            <span class="subheading">我将尽快给您回复</span>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <form name="sentMessage" novalidate>
            <div class="control-group">
              <div class="form-group floating-label-form-group controls">
                <label>您的称呼</label>
                <input type="text" class="form-control" placeholder="您的称呼" id="nickname" required data-validation-required-message="Please enter your name.">
                <p class="help-block text-danger"></p>
              </div>
            </div>
            <div class="control-group">
              <div class="form-group floating-label-form-group controls">
                <label>Email</label>
              <input type="email" class="form-control" placeholder="Email Address" id="email" required data-validation-required-message="Please enter your  email address.">
                <p class="help-block text-danger"></p>
              </div>
            </div>
            <div class="control-group">
              <div class="form-group col-xs-12 floating-label-form-group controls">
                <label>Phone Number</label>
                <input type="tel" class="form-control" placeholder="Phone Number" id="mobile">
              </div>
            </div>
            <div class="control-group">
              <div class="form-group col-xs-12 floating-label-form-group controls">
                <label>Message</label>
                <textarea rows="5" class="form-control" placeholder="请输入您想说的" id="message" required data-validation-required-message="Please enter a message."></textarea>
                <p class="help-block text-danger"></p>
              </div>
            </div>
            <br>
            <div class="btn-group">
              <span class="badge badge-warning" name="feedback" pk="${articleLongVO.privateKey}">
                <span>确定</span>
              </span>
            </div>
            <span class="badge badge-warning" pk="${articleLongVO.privateKey}" name="feedbackResult"></span>
          </form>
        </div>
      </div>
    </div>
  </article>

  <hr>

  <!-- Footer -->
  <%@ include file="./footer.jsp" %>

  <%@ include file="./cleanBlogNormalFooter.jsp" %>
  <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/readArticleLongV4.js'/>"></script>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleManager.js'/>"></script>
  </sec:authorize>
  <sec:authorize access="hasRole('ROLE_USER')">
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleLongUserV3.js'/>"></script>
  </sec:authorize>

</body>

</html>
