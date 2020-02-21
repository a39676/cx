<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <%@ include file="../baseElementJSP/normalHeader.jsp" %>
  <title>${title}</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta charset="UTF-8">

  <link href="/static_resources/css/article/article.css" rel="stylesheet">

</head>
<body>
  <%@ include file="../baseElementJSP/headerCustom.jsp" %>
  <section class="post-area">
    <div class="container" id="readArticleLong" pk="${articleLongVO.privateKey}">
  
      <div class="row">
        <div class="col-lg-1 col-md-0"></div>
        <div class="col-lg-10 col-md-12">
          <div class="main-post">
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
                </c:if>
                <span pk="${articleLongVO.privateKey}" name="reviewResult"></span>
              </c:if>
              
              <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
              <%@ include file="../articleJSP/articleReview.jsp" %>
              <%@ include file="../articleJSP/articleManager.jsp" %>
              </sec:authorize>
            </div><!-- post-top-area -->
  
            <%-- <div class="post-image"><img src="images/blog-1-1000x600.jpg" alt="Blog Image"></div> --%>
  
            <div class="post-bottom-area">
              <h3 class="title"><b>${articleLongVO.articleTitle}</b></h3>
              <div class="post-info">
                <div class="left-area">
                  <a class="avatar" href="#"><img src="${articleLongVO.headIamgeUrl}" alt="Profile Image"></a>
                </div>
                <div class="middle-area">
                  <div class="name" ><b>${articleLongVO.nickName}</b></div>
                  <h6 class="date">on ${articleLongVO.createDateString}</h6>
                  <c:if test="${!empty articleLongVO.editDateString}">
                    <h5 class="pre-title">最后一次编辑时间: ${articleLongVO.editDateString}</h5>
                  </c:if>
                </div>
              </div><!-- post-info -->
              <PRE><p class="para">${articleLongVO.contentLines}</p></PRE>
  
              <button class="btn btn-danger btn-sm" name="showComplaint" pk="${articleLongVO.privateKey}">
                <span style="font-size: small;">需要投诉?.</span>
              </button>
              <div style="display: none;" pk="${articleLongVO.privateKey}" name="complaintDiv">
                <input type="text" name="complaintReason" pk="${articleLongVO.privateKey}" placeholder="请输入投诉原因">
                <div class="btn-group">
                  <button class="btn btn-danger btn-sm" name="complaint" pk="${articleLongVO.privateKey}">
                    <span style="font-size: small;">投诉</span>
                  </button>
                  <button class="btn btn-success btn-sm" name="cancelComplaint" pk="${articleLongVO.privateKey}">
                    <span style="font-size: small;">取消</span>
                  </button>
                </div>
                <span class="badge badge-warning" pk="${articleLongVO.privateKey}" name="complaintResult"></span>
              </div>
            </div>
            <%-- 
            old bottom area
            <div class="post-bottom-area">
              <ul class="tags">
                <li><a href="#">Mnual</a></li>
                <li><a href="#">Liberty</a></li>
                <li><a href="#">Recommendation</a></li>
                <li><a href="#">Inspiration</a></li>
              </ul>
              <div class="post-icons-area">
                <ul class="post-icons">
                  <li><a href="#"><i class="ion-heart"></i>57</a></li>
                  <li><a href="#"><i class="ion-chatbubble"></i>6</a></li>
                  <li><a href="#"><i class="ion-eye"></i>138</a></li>
                </ul>
                <ul class="icons">
                  <li>SHARE : </li>
                  <li><a href="#"><i class="ion-social-facebook"></i></a></li>
                  <li><a href="#"><i class="ion-social-twitter"></i></a></li>
                  <li><a href="#"><i class="ion-social-pinterest"></i></a></li>
                </ul>
              </div>
              <div class="post-footer post-info">
                <div class="left-area">
                  <a class="avatar" href="#"><img src="images/avatar-1-120x120.jpg" alt="Profile Image"></a>
                </div>
                <div class="middle-area">
                  <a class="name" href="#"><b>Katy Liu</b></a>
                  <h6 class="date">on Sep 29, 2017 at 9:48 am</h6>
                </div>
              </div><!-- post-info -->
            </div><!-- post-bottom-area -->
            --%>
          </div><!-- main-post -->
        </div><!-- col-lg-8 col-md-12 -->
      </div><!-- row -->
    </div><!-- container -->
  </section><!-- post-area -->


</body>

<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript" src="<c:url value='/static_resources/js/article/readArticleLongV3.js'/>"></script>
<sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
<script type="text/javascript" src="<c:url value='/static_resources/js/article/articleManager.js'/>"></script>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
<script type="text/javascript" src="<c:url value='/static_resources/js/article/articleLongUserV3.js'/>"></script>
</sec:authorize>

</html>