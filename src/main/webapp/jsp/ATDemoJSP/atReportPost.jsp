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
    <div class="overlay" id="readArticleLong">
    </div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="post-heading">
            <h1>${reportVO.flowTypeName}</h1>
            <%-- <h2 class="subheading">ID: ${reportVO.id}</h2> --%>
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
        <div class="col-md-12 mx-auto">
          <table class="table table-hover table-bordered table-striped table-light">
            <thead class="">
              <tr class="table-primary">
                <th style="text-align: center; vertical-align: middle;">
                  <span class="badge badge-primary">Total</span>
                </th>
                <th style="text-align: center; vertical-align: middle;">
                  <span class="badge badge-success">Pass</span>
                </th>
                <th style="text-align: center; vertical-align: middle;">
                  <span class="badge badge-danger">Failed</span>
                </th>
                <th style="text-align: center; vertical-align: middle;">
                  <span class="badge badge-warning">Block</span>
                </th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td style="text-align: center; vertical-align: middle;">
                  ${reportVO.passCount + reportVO.failedCount + reportVO.blockedCount}
                </td>
                <td style="text-align: center; vertical-align: middle;">
                  ${reportVO.passCount}
                </td>
                <td style="text-align: center; vertical-align: middle;">
                  ${reportVO.failedCount}
                </td>
                <td style="text-align: center; vertical-align: middle;">
                  ${reportVO.blockedCount}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <c:forEach items="${reportVO.caseReportList}" var="caseReport">
        <div class="row">
          <div class="col-md-12 mx-auto">
            <table class="table table-hover table-bordered table-striped table-light">
              <thead class="">
                <tr class="table-primary">
                  <th colspan="2" style="text-align: center; vertical-align: middle;">
                    Case: ${caseReport.caseTypeName}
                  </th>
                </tr>
                <tbody>
                  <c:forEach items="${caseReport.reportElementList}" var="reportElement">
                    <tr>
                      <td style="text-align: center; vertical-align: middle;">
                        ${reportElement.marktime}
                      </td>
                      <c:choose>
                        <c:when test="${reportElement.content == 'pass'}">
                          <td style="text-align: center; vertical-align: middle;">
                            <span class="badge badge-success">${reportElement.content}</span>
                          </td>
                        </c:when>
                        <c:when test="${reportElement.content == 'failed'}">
                          <td style="text-align: center; vertical-align: middle;">
                            <span class="badge badge-danger">${reportElement.content}</span>
                          </td>
                        </c:when>
                        <c:when test="${reportElement.content == 'blocked'}">
                          <td style="text-align: center; vertical-align: middle;">
                            <span class="badge badge-warning">${reportElement.content}</span>
                          </td>
                        </c:when>
                        <c:otherwise>
                          <td style="text-align: center; vertical-align: middle;">
                            ${reportElement.content}
                            <img class="img-fluid" src="${reportElement.imgUrl}">
                          </td>
                        </c:otherwise>
                      </c:choose>
                    </tr>
                  </c:forEach>
                </tbody>
              </thead>
            </table>
          </div>
        </div>
      </c:forEach>
    </div>
  </article>

  <hr>

  <!-- Footer -->
  <%@ include file="../cleanBlogJSP/footer.jsp" %>

  <%@ include file="../cleanBlogJSP/cleanBlogNormalFooter.jsp" %>
  <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/readArticleLongV4.js'/>"></script>
  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">

  </sec:authorize>

</body>

</html>
