<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container-fluid" style="">
  <div class="row">
    <div class="col-md-1">
      <!-- channels -->
      <div class="btn-group-vertical" id="articleChannels"></div>
    </div>
    <div class="col-md-1">
    </div>
    <div class="col-md-10">
      <div id="blogArea" markTime="" loadingFlag="" articleChannel="">
        <sec:authorize access="hasRole('ROLE_USER')">
          <button class="btn btn-sm btn-outline-light" id="createNewArticle"><b>Create new</b></button>
          <div class="row"><hr style="color: rgba(55, 66, 250,1.0)"></div>
        </sec:authorize>
        <div class="container-fluid" id="blogRowArea"></div>
      </div>
      <div>
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
    <!-- dynamic area --> 
  </div>    
</div>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

  </script>
</footer>
