<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- 备份于2019-06-19 因下方有频道的 like or hate 古老代码  -->
<div class="container-fluid" style="">
  <div class="row">
    <div class="col-md-1">
      <div class="btn-group-vertical" id="articleChannels"></div>
    </div>
    <div class="col-md-1">
    </div>
    <!-- channels -->
    <div class="col-md-10">
      <div id="blogArea" markTime="" loadingFlag="" articleChannel="">
        <sec:authorize access="hasRole('ROLE_USER')">
          <button class="btn btn-sm btn-outline-light" id="createNewArticle"><b>Create new</b></button>
          <div class="row"><hr style="color: rgba(55, 66, 250,1.0)"></div>
        </sec:authorize>
        <div class="container-fluid" id="blogRowArea"></div>
      </div>
      <div>
        <img src="https://wx1.sinaimg.cn/mw690/0062ci2oly1ftzyk5dlejg303k03k40e.gif" 
          style="width: 30px; height: 30px; display: none;"
          id="articleAreaLoadingImg">
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

<!-- 
<body>
  <div class="container-fluid" name="articleMainFrame" style="display: none;">
    
    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3" >
          <div class="btn-group">
            <button class="btn  btn-info btn-sm" name="showArticleCreator" style="">
              <span class="badge badge-info">想说些什么...</span>
            </button>
          </div>
        </div>
        <sec:authorize access="hasRole('ROLE_USER')">
        <c:if test="${showLikeOrHate == true}">
        <div class="col-sm-3">
          <div class="btn-group">
            <button class="btn btn-success likeOrHateThisChannel" 
              name="likeThisChannel" likeOrHate="1" style="">
              <span class="badge badge-pill badge-success">喜欢这频道??</span>
            </button>
            <button class="btn btn-light likeOrHateThisChannel" 
              name="likeThisChannel" likeOrHate="0" style="">
              <span class="badge badge-pill badge-light">不知道,先别问</span>
            </button>
            <button class="btn btn-danger likeOrHateThisChannel" 
              name="hateThisChannel" likeOrHate="-1" style="">
              <span class="badge badge-pill badge-danger">讨厌这频道..</span>
            </button>
          </div>
          <div class="btn-group">
            <button class="btn btn-primary likeOrHateEnsure" 
              name="wrongClick" style="display: none;">
              <span class="badge badge-pill badge-primary">点错了...</span>
            </button>
            <button class="btn btn-danger likeOrHateEnsure" 
              name="likeOrHateEnsure" likeOrHate="" style="display: none;">
              <span class="badge badge-pill badge-danger" name="likeOrHateEnsure"></span>
            </button>
          </div>
        </div>
        </c:if>
        </sec:authorize>
      </div>
    </div>
    
    <div class="container-fluid" name="createArticleLong" >
      <sec:authorize access="hasRole('ROLE_USER')">
      <div class="row articleLongCreatingSubArea" style="display: none">
        <div class="col-sm-12" >
        <textarea class="input form-control" name="articleTitle" rows="1" cols="50" placeholder="请输入标题~"></textarea > 
        </div>
      </div>
      
      <div class="row articleLongCreatingSubArea" style="display: none">
        <div class="col-sm-12" >
    <textarea class="input form-control" name="creatingArticleLong" rows="4" cols="50" placeholder="请输入内容,      图片请直接粘帖图片链接"></textarea> 
        </div>
      </div>
      
      <div class="row articleLongCreatingSubArea" style="display: none">
        <div class="col-sm-12" >
          <div class="btn-group">
            <sec:authorize access="hasRole('ROLE_POSTER')">
              <textarea class="input form-control" type="text" 
              name="superAdminKey" placeholder="please insert key"></textarea>
            </sec:authorize>
            <button class="btn  btn-primary btn-sm" 
              name="createArticleLong">
              <span class="badge badge-primary">提交</span>
            </button>
            <button class="btn  btn-primary btn-sm" 
              name="closeArticleCreator">
              <span class="badge badge-primary">不想说了...</span>
            </button>
          </div>
        </div>
      </div>
      
      <div class="row articleLongCreatingSubArea" style="display: none">
        <div class="col-sm-12" >
          <span name="createArticleResult" badge badge-primary></span>
        </div>
      </div>
      </sec:authorize>
    
      <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
      <div class="row articleLongCreatingSubArea" style="display: none">
        <div class="col-sm-12" >
          <span class="badge badge-primary">不好意思...请先登陆或者注册...</span>
        </div>
      </div>
      </sec:authorize>
    </div>
    
    <hr>
    
    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-12" name="articleChannelPrefix">
          ${articleChannelPrefix}
        </div>
      </div>
    
      <hr>
    
      <%-- 此处改成接口,请求管理员专用搜索框? --%>
      <div class="row">
        <div class="col-sm-12" name="articleSearch">
        </div>
      </div>
    
      <div class="row" name="pageNoRow" style="display: none;">
        <%-- mark --%>
        <div class="container-fluid">
          <div class="row">
            <div class="col-sm-4">
              <ul class="pagination">
                <li class="page-item"><a class="page-link" href="#">--</a></li>
                <li class="page-item"><a class="page-link" href="#">-</a></li>
                <input type="number" name="pageNo" min="1" value="1" style="width: 50px;">
                <li class="page-item"><a class="page-link" href="#">+</a></li>
                <li class="page-item"><a class="page-link" href="#">++</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      
      <div class="row">
        <div class="col-sm-12" name="summaryList">
        </div>
      </div>
  
  
    </div>
  
  </div>
</body> 
-->