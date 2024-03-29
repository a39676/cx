<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
  <div class="container">
    <%-- <a class="navbar-brand" href="#">_</a> --%>
    <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
      <a class="navbar-brand" href="/admin/manager" target="_blank">Admin</a>
    </sec:authorize>

    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link" id="homePage" href="/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/aboutMe">关于我/联系方式</a>
        </li>
        <li class="nav-item" id="autoTestDemo">
        </li>
        <li class="nav-item" id="articleBurn">
        </li>
        <li class="nav-item">
          <div id="dynamicLoginDiv"></div>
        </li>
      </ul>

      <c:if test="${isHomePage == true}">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <div class="input-group">
            <input type="text" class="form-control" placeholder="根据标题搜索" aria-label="" id="searchKeyWord" aria-describedby="searchByTitle">
              <div class="input-group-append">
              <button class="input-group-text" id="searchByTitle"><svg class="octicon octicon-search" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M15.7 13.3l-3.81-3.83A5.93 5.93 0 0013 6c0-3.31-2.69-6-6-6S1 2.69 1 6s2.69 6 6 6c1.3 0 2.48-.41 3.47-1.11l3.83 3.81c.19.2.45.3.7.3.25 0 .52-.09.7-.3a.996.996 0 000-1.41v.01zM7 10.7c-2.59 0-4.7-2.11-4.7-4.7 0-2.59 2.11-4.7  4.7-4.7 2.59 0 4.7 2.11 4.7 4.7 0 2.59-2.11 4.7-4.7 4.7z"></path></svg></button>
              </div>
            </div>
          </li>
        </ul>
      </c:if>
      <%-- main-menu --%>

    </div>

    <%-- conatiner --%>


  </div>
</nav>


<%--
<div class="container-fluid position-relative no-side-padding">
  <label class="badge badge-primary" id="homepageRollingAnnouncement"></label>
</div>
 --%>
