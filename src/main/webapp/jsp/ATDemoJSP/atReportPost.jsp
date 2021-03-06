<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="../cleanBlogJSP/cleanBlogNormalHeader.jsp" %>
</head>

<body>

  <!-- Navigation -->
  <%@ include file="./nav.jsp" %>

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('/static_resources/cleanBlog/img/fog-4597348_1920.jpg')">
    <div class="overlay" id="readArticleLong" reportId="${reportVO.id}">
    </div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="post-heading">
            <h1>${reportVO.title}</h1>
            <h2 class="subheading">ID: ${reportVO.id}</h2>
            <span class="meta">
              任务创建时间: ${reportVO.createTimeStr}
            </span>
            <br>
            <span class="meta">
              任务执行时间: ${reportVO.startTimeStr}
            </span>
            <br>
            <span class="meta">
              任务结束时间: ${reportVO.endTimeStr}
            </span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <header class="masthead">
    <div class="container">
      <div class="row">
        <div class="post-top-area">
        </div><!-- post-top-area -->
      </div>
    </div>
  </header>

  <!-- Post Content -->
  <article>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <c:forEach items="${reportVO.contentLines}" var="subLine">
            <c:if test="${subLine.lineArrtibute == 'strKey'}">
              <p class="para" style="word-break:break-word;">${subLine.content}</p>
            </c:if>
            <c:if test="${subLine.lineArrtibute == 'imgKey'}">
              <img class="img-fluid" src="${subLine.content}">
            </c:if>
          </c:forEach>

        </div>
      </div>
    </div>
  </article>

  <hr>

  <!-- Footer -->
  <%@ include file="../cleanBlogJSP/footer.jsp" %>

  <%@ include file="../cleanBlogJSP/cleanBlogNormalFooter.jsp" %>
  <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/readArticleLongV4.js'/>"></script>
  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
  <%-- <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleManager.js'/>"></script> --%>
  </sec:authorize>

</body>

</html>
