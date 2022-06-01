<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="../../baseElementJSP/normalHeader.jsp" %>
  <script type="text/javascript" async="" src="https://ssl.google-analytics.com/ga.js"></script>
  <style>
    a:link {
      color: black;
      background-color: transparent;
      text-decoration: none;
    }

    a:visited {
      color: black;
      background-color: transparent;
      text-decoration: none;
    }

    a:hover {
      color: red;
      background-color: transparent;
      text-decoration: underline;
    }

    a:active {
      color: yellow;
      background-color: transparent;
      text-decoration: underline;
    }
  </style>
</head>

<body>
  <!-- Page Header -->
  <header class="" style="background-image: url('${headerImg}'); height:150px;">
    <nav class="navbar fixed-top navbar-expand-lg navbar-success bg-light " style="opacity: 0.85;">

      <a class="navbar-brand" href="#"></a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse">
        <ul class="navbar-nav mr-auto">
          <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
            <li class="nav-item active">
              <a class="nav-link" href="/admin/manager" target="_blank">Admin</a>
            </li>
          </sec:authorize>
          <li class="nav-item active font-weight-bold">
            <a class="nav-link" href="/">Home</a>
          </li>
          <li class="nav-item active font-weight-bold">
            <a class="nav-link" href="/aboutMe">关于我/联系方式</a>
          </li>
          <li class="nav-item active font-weight-bold" id="autoTestDemo">
          </li>
          <li class="nav-item active font-weight-bold" id="articleBurn">
          </li>
          <li class="nav-item active font-weight-bold">
            <a class="nav-link" target="_blank" href="/publicTool/qrcode/">QR Code 生成 / 解码</a>
          </li>
        </ul>

        <c:if test="${isHomePage == true}">
          <form class="form-inline my-2 my-lg-0">
            <input type="text" class="form-control" placeholder="根据标题搜索" aria-label="" id="searchKeyWord" aria-describedby="searchByTitle">
            <button class="btn btn-outline-success my-2 my-sm-0" id="searchByTitle" type="submit">
              <svg class="octicon octicon-search" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true">
                <path fill-rule="evenodd" d="M15.7 13.3l-3.81-3.83A5.93 5.93 0 0013 6c0-3.31-2.69-6-6-6S1 2.69 1 6s2.69 6 6 6c1.3 0 2.48-.41 3.47-1.11l3.83 3.81c.19.2.45.3.7.3.25 0 .52-.09.7-.3a.996.996 0 000-1.41v.01zM7 10.7c-2.59 0-4.7-2.11-4.7-4.7 0-2.59 2.11-4.7  4.7-4.7 2.59 0 4.7 2.11 4.7 4.7 0 2.59-2.11 4.7-4.7 4.7z">
                </path>
              </svg>
            </button>
          </form>
        </c:if>
      </div>
    </nav>
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-md-12 mx-auto">
          <br>
          <br>
          <br>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12 mx-auto">
          <div class="site-heading">
            <h2>${title}</h2>
            <span class="subheading">${subheading}</span>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Main Content -->
  <div class="container-fluid" style="margin-top:20px; margin-bottom:20px;">
    <div class="row">
      <!-- channels -->
      <div class="col-md-2 mx-auto">
        <sec:authorize access="hasRole('ROLE_USER')">
          <button class="btn btn-sm btn-warning" id="createNewArticle"><b>Create new</b></button>
          <div class="row"><hr style="color: rgba(55, 66, 250,1.0)"></div>
        </sec:authorize>
        <div class="btn-group-vertical" id="articleChannels"></div>
      </div>

      <%-- post title table --%>
      <div class="col-md-8 mx-auto">
        <div class="container-fluid">
          <div class="row" id="blogArea" markTime="" loadingFlag="" articleChannel="" keyword="">
            <div class="col-md-12 mx-auto">
              <table id="blogRowArea" class="table table-hover table-bordered table-striped table-light">

              </table>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 mx-auto">
              <div class="spinner-border text-warning" role="status" id="articleAreaLoadingImg">
                <span class="sr-only">Loading...</span>
              </div>
              <button class="btn btn-sm btn-success" id="loadMoreButton"><b>LOAD MORE</b></button>
            </div>
            <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
            <%-- 管理员专用搜索框 --%>
              <%@ include file="../../articleJSP/articleSearchV3.jsp" %>
            </sec:authorize>
          </div>
        </div>
      </div>
      <div class="col-md-2 mx-auto">
        <span style="color: rgb(255, 156, 0); background-color: rgb(255, 255, 255);">打赏 Doge coin</span>
        <img src="${donateImgUrl}" alt="" style="width:100px; height:100px;">
      </div>

    </div>
  </div>

  <hr>

  <!-- buttom -->
  <%@ include file="./footer.jsp" %>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <!-- SCIPTS -->
  <script type="text/javascript" src="/static_resources/cleanBlog/js/articleNormal.js"></script>
  <script type="text/javascript" src="/static_resources/js/autotest/autotestLinkFillToBlogHome.js"></script>
  <script type="text/javascript" src="/static_resources/js/article/articleBurnLinkFillToBlogHome.js"></script>
  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
  <script type="text/javascript" src="<c:url value='/static_resources/cleanBlog/js/articleSearch.js'/>"></script>
  </sec:authorize>

  <script type="text/javascript">
    $(document).ready(function() {

      var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;
        for (i = 0; i < sURLVariables.length; i++) {
          sParameterName = sURLVariables[i].split('=');
          if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
          }
        }
      };

      var getLocation = function(href) {
          var location = document.createElement("a");
          location.href = href;
          return location;
      };

      function fillFootMarker() {
        var location = getLocation(window.location.href);
        var footMarker = document.getElementById("footMarker");
        var visitCount = footMarker.getAttribute("visitCount");
        var now = new Date();
        var year = now.getFullYear()
        footMarker.innerHTML = ('Copyright &copy; ' + location.hostname + ' ' + year + ' | 访问统计: ' + visitCount);
      };

      fillFootMarker();
    })
  </script>
</body>

</html>
