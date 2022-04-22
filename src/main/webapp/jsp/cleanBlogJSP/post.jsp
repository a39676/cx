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
            <span class="meta">
              ${articleLongVO.createDateString}
            </span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <header class="masthead">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
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
              <a href="/article/editArticleLong?pk=${articleLongVO.privateKey}" target="_blank"><span class=" badge badge-info">编辑</span></a>
              </c:if>
              <span pk="${articleLongVO.privateKey}" name="reviewResult"></span>
            </c:if>
            <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
            <%@ include file="../articleJSP/articleReview.jsp" %>
            <%@ include file="../articleJSP/articleManager.jsp" %>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
            </sec:authorize>
          </div><!-- post-top-area -->
        </div>
      </div>
    </div>
  </header>

  <!-- Post Content -->
  <article>
    <div class="container">
      <%-- content --%>
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
          <c:if test="${articleLongVO.editDateString != null}">
            <span style="text-transform: capitalize">Last edited time: ${articleLongVO.editDateString}</span>
          </c:if>
          </sec:authorize>
          <p class="para" style="word-break:break-word;">${articleLongVO.contentLines}</p>

        </div>
      </div>

      <div class="row">
        <div class="col-md-3">
        </div>
        <div class="col-md-9">
          <span style="color: rgb(255, 156, 0); background-color: rgb(255, 255, 255);">打赏Doge coin</span>
          <img src="/image/getImage/?imgPK=rLq%2B%2FRjDi7s3Ddt3xHE1DZfoftiFB%2B7vm4AtaQtR59o%3D" alt="" style="width:100px; height:100px;">
        </div>
      </div>

      <hr>
      <%-- comment list start --%>
      <div class="container" id="commentArea">
        <div class="row">
          <div class="container" pk="${articleLongVO.privateKey}" id="commentList"
          markTime="${articleLongVO.createDateString}" loadingFlag="">

          </div>
        </div>

        <%-- search comment start --%>
        <sec:authorize access="!hasRole('ROLE_SUPER_ADMIN')">
          <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
              <div class="spinner-border text-warning" role="status" id="articleAreaLoadingImg">
                <span class="sr-only">Loading...</span>
              </div>
              <button class="btn btn-sm btn-primary" id="loadMoreButton"><b>Load more comment</b></button>
            </div>
          </div>
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
          <div class="row">
            <div class="col-lg-8 col-md-10 mx-auto">
              <%@ include file="../articleJSP/articleCommentSearchV2.jsp" %>
            </div>
          </div>
        </sec:authorize>
        <%-- search comment end --%>

        <%-- create comment start --%>
        <div class="row">
          <div class="col-lg-8 col-md-10 mx-auto">
            <div class="page-heading">
              <span class="subheading">欢迎留言</span>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-8 col-md-10 mx-auto">
            <form name="sentMessage" novalidate>
              <div class="control-group">
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text">您的称呼</span>
                  </div>
                  <input type="text" class="form-control" placeholder="您的称呼" id="nickname" required   data-validation-required-message="Please enter your name." value="${nickName}">
                </div>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text">Email</span>
                  </div>
                  <input type="text" class="form-control" placeholder="Email(不会展示给其他用户)" id="email" required data-validation-required-message="Please enter your  email address.  " value="${email}">
                </div>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text">Phone</span>
                  </div>
                  <input type="text" class="form-control" placeholder="手机号(选填,不会展示给其他用户)" id="mobile">
                </div>
                <div class="form-group col-xs-12 floating-label-form-group controls">
                  <label>Message</label>
                  <textarea rows="5" class="form-control" placeholder="期待你的留言" id="message"  required data-validation-required-message="Please enter a message."></textarea>
                  <p class="help-block text-danger"></p>
                </div>
              </div>
              <br>
              <div class="btn-group">
                <span class="badge badge-warning" name="comment" pk="${articleLongVO.privateKey}">
                  <span style="cursor:pointer;"><提交留言></span>
                </span>
              </div>
              <span class="badge badge-warning" pk="${articleLongVO.privateKey}" name="commentResult">
              </span>
            </form>
          </div>
        </div>
        <%-- create comment end --%>
      </div>
      <%-- comment list end --%>

    </div>
  </article>

  <hr>

  <!-- Footer -->
  <%@ include file="./footer.jsp" %>

  <%@ include file="./cleanBlogNormalFooter.jsp" %>
  <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/readArticleLongV4.js'/>"></script>
  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleManager.js'/>">
    </script>
    <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/loadArticleCommentForAdmin.js'/>">
    </script>
  </sec:authorize>
  <sec:authorize access="hasRole('ROLE_USER')">
    <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleLongUserV3.js'/>">
    </script>
  </sec:authorize>
  <sec:authorize access="!hasRole('ROLE_SUPER_ADMIN')">
    <%-- 搜索栏脚本冲突 --%>
    <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/loadArticleCommentForUser.js'/>">
    </script>
  </sec:authorize>

  <script type="text/javascript">
  </script>

</body>

</html>
