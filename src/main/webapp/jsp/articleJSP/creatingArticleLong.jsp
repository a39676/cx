<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <div class="container-fluid" name="createArticleLong" >
      <sec:authorize access="hasRole('ROLE_USER')">
      <div class="row">
        <div class="col-sm-12" >
          <span class="badge badge-primary">请选择提交频道</span>
          <select class="" name="channelList" style="">
            <c:forEach items="${channelList}" var="subChannel">
              <option value="${subChannel.uuid}">${subChannel.channelName}</option>
            </c:forEach>
          </select>
        </div>
      </div>


      <div class="row">
        <div class="col-sm-12" >
          <textarea class="input form-control" name="articleTitle" rows="1" cols="50" placeholder="请输入标题~"></textarea>
        </div>
      </div>
      
      <div class="row">
        <div class="col-sm-12" >
          <textarea class="input form-control" name="creatingArticleLong" rows="4" cols="50" placeholder="请输入内容, 图片请直接粘帖图片链接"></textarea> 
        </div>
      </div>

      <div class="row">
        <div class="col-sm-12" >
          <textarea class="input form-control" id="editor" placeholder="富文本编辑器"></textarea> 
        </div>
      </div>
      
      <div class="row">
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
              name="createArticleLongEditor">
              <span class="badge badge-primary">提交富文本</span>
            </button>
            <button class="btn  btn-primary btn-sm" 
              name="closeArticleCreator">
              <span class="badge badge-primary">不想说了...</span>
            </button>
          </div>
        </div>
      </div>
      
      <div class="row">
        <div class="col-sm-12" >
          <span name="createArticleResult" badge badge-primary></span>
        </div>
      </div>
      </sec:authorize>
  </div> <%-- createArticleLong container --%>

</div> <%-- main row --%>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <sec:authorize access="hasRole('ROLE_USER')">
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/creatingArticleLongV3.js'/>"></script>
  <script src="https://cdn.ckeditor.com/4.13.0/standard/ckeditor.js"></script>
  </sec:authorize>
</footer>
</html>