<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="../../baseElementJSP/normalHeader.jsp" %>
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
      background-color: yellow;
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
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-1">
        <a href="/">Home</a>
      </div>
      <div class="col-md-1" id="articleBurn">
      </div>
      <div class="col-md-1">
        <a href="/publicTool/qrcode/" target="_blank">QR Code 生成 / 解码</a>
      </div>
    </div>
  </div>

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

      var toolDropdownMenu = $("#toolDropdownMenu");
      var toolDropdownMenuContent = $("#toolDropdownMenuContent");

      toolDropdownMenu.mouseenter(function() {
        toolDropdownMenuContent.show();
      }).mouseleave(function() {
        toolDropdownMenuContent.hide();
      });

      toolDropdownMenuContent.mouseenter(function() {
        toolDropdownMenuContent.show();
      }).mouseleave(function() {
        toolDropdownMenuContent.hide();
      });

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
