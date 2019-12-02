<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
  <div class="container">
    <a class="navbar-brand" href="#">_</a>
    <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
      <a class="navbar-brand" href="/admin/manager" target="_blank">Admin</a>
    </sec:authorize>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <a class="nav-link" id="homePage" href="/">首页</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/aboutMe">关于我/联系方式</a>
        </li>
        <c:if test="${isJobView == true}">
          <li class="nav-item">
            <a class="nav-link" href="/atDemo/index">自动化测试示例</a>
          </li>
        </c:if>
        <sec:authorize access="!hasRole('ROLE_USER')">
          <li class="nav-item" name="login">
            <a class="nav-link" href="#" id="loginTag">[登录]</a>
          </li>
          <%-- 
          <li class="btn btn-sm btn-default" name="userRegist" url="/user/userRegist">
            <a href="">[注册]</a>
          </li>
            --%>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_USER')">
          <li class="nav-item" name="login" style="display: none;">
            <a class="nav-link" href="#" id="loginTag">[登录]</a>
          </li>
          <li class="nav-item" name="logout">
            <a class="nav-link" href="/login/logout">[登出]</a>
          </li>
          <li class="nav-item" name="userInfo" url="/user/userInfo">
            <a class="nav-link" href="">[个人中心]</a>
          </li>
        </sec:authorize>
      </ul>
      <!-- main-menu -->
      <!-- 
      <div class="src-area">
        <form>
          <button class="src-btn" type="submit"><i class="ion-ios-search-strong"></i></button>
          <input class="src-input" type="text" placeholder="Type of search">
        </form>
      </div>
        -->
      <div id="dynamicLoginDiv"></div>
    </div>
    <!-- conatiner -->
    
      
  </div>
</nav>


<!-- 
<div class="container-fluid position-relative no-side-padding">
  <label class="badge badge-primary" id="homepageRollingAnnouncement"></label>
</div>

 -->