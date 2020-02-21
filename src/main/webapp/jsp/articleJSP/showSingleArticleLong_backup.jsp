<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- backup time: 2019-12-13 --%>
<%-- 旧式页面, 基于富文本器启用之前, 随时丢弃 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid articleLongShow" name="readArticleLong" pk="${articleLongVO.privateKey}">

<c:if test="${!empty articleLongVO.editDateString}">
<div class="row">
  <div class="col-sm-12" >
    <label style="font-size: small;color: gray;">最后一次编辑时间: ${articleLongVO.editDateString}</label>
  </div>
</div>
</c:if>

<div class="row">
  <div class="col-sm-12" >
    <c:if test="${articleLongVO.iWroteThis == true}">
      <button class="btn btn-danger btn-sm" name="delete" pk="${articleLongVO.privateKey}">
        <span style="font-size: small;">删除</span>
      </button>
      <span pk="${articleLongVO.privateKey}" name="reviewResult"></span>
    </c:if>
    <button class="btn btn-danger btn-sm" name="showComplaint" pk="${articleLongVO.privateKey}">
      <span style="font-size: small;">投诉</span>
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
    </div>
    <span class="badge badge-warning" pk="${articleLongVO.privateKey}" name="complaintResult"></span>
  </div>
</div>


<sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
<%@ include file="../articleJSP/articleReview.jsp" %>
<%@ include file="../articleJSP/articleManager.jsp" %>
</sec:authorize>

<div class="row">
  <div class="col-sm-12" >
    <c:forEach items="${articleLongVO.contentLines}" var="line">
      <%-- <label name="articleLine" pk="${articleLongVO.privateKey}">${line}</label> --%>
      <span name="articleLine" pk="${articleLongVO.privateKey}" style="font-size: small;">${line}</span><br>
    </c:forEach>
  </div>
</div>

<br>

<sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
<%@ include file="../articleJSP/articleReview.jsp" %>
<%@ include file="../articleJSP/articleManager.jsp" %>
</sec:authorize>

<div class="row">
  <div class="container-fluid" pk="${pk}">
    <div class="row" pk="${pk}" name="commentDetailList">
      <div class="col-sm-12">
        <button class="btn btn-success btn-sm" pk="${articleLongVO.privateKey}" name="findComment">展开评论..</button>
      </div>
    </div>
  </div>
  <div class="col-sm-12" pk="${articleLongVO.privateKey}" name="commentDiv">
  </div>
</div>

</div>
</body>

<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">
$(document).ready(function() {

  var pk="${articleLongVO.privateKey}";

  $("div[name='readArticleLong'][pk='${articleLongVO.privateKey}']").dblclick(function () {
    $(".readArticleLong").hide();
  });
  $("div[name='readArticleLong'][pk='${articleLongVO.privateKey}']").dblclick(function () {
    $(".readArticleLong").hide();
  });
  

  $("span[name='evaluation'][pk='${articleLongVO.privateKey}']").hover(function() {
    $(this).css('cursor','pointer');
  });

  $("button[name='showComplaint'][pk='${articleLongVO.privateKey}']").click(function () {
    $("div[name='complaintDiv'][pk='${articleLongVO.privateKey}']").fadeIn(150);
    $(this).fadeOut(150);
  });
  $("button[name='cancelComplaint'][pk='${articleLongVO.privateKey}']").click(function () {
    $("div[name='complaintDiv'][pk='${articleLongVO.privateKey}']").fadeOut(150);
    $("button[name='showComplaint'][pk='${articleLongVO.privateKey}']").fadeIn(150);
  });

  $("button[name='complaint'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = "${articleLongVO.privateKey}";
    var complaintReason = $("input[name='complaintReason'][pk='"+pk+"']").val();

    var url = "${pageContext.request.contextPath}/article/articleLongComplaint";
    var jsonOutput = {
      pk:pk,
      complaintReason:complaintReason
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        if(datas.result == "0") {
          $("button[name='complaint'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("button[name='cancelComplaint'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("input[name='complaintReason'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("span[name='complaintResult'][pk='${articleLongVO.privateKey}']").text(datas.message);
        } else {
          $("span[name='complaintResult'][pk='${articleLongVO.privateKey}']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });
  


  $("img[name='articleImage']").click(function () {
    var fold = $(this).attr("fold");

    if (fold == 0) {
      foldImage($(this));
    } else {
      unfoldImage($(this));
    }
  });

  function foldImage(imageTag) {
    var fold = imageTag.attr("fold");
    var imgMaxWidth = 700;
    var imgMaxHeight = 700;

    if (fold == 1) {
      return;
    } else {
      var tmpImg = new Image();
      tmpImg.src = imageTag.attr("src");
  
      var imgWidth = tmpImg.width;
      var imgHeight = tmpImg.height;

      var width = 0;
      var height = 0;
      
      if(imgWidth < imgMaxWidth && imgHeight < imgMaxHeight) {
        width = imgWidth;
        height = imgHeight;
      } else {
        if(imgWidth > imgHeight) {
          width = imgMaxWidth;
          var imgSizeCoefficient = imgMaxWidth / imgWidth;
          height = imgHeight * imgSizeCoefficient;
        } else {
          height = imgMaxHeight;
          var imgSizeCoefficient = imgMaxHeight / imgHeight;
          width = imgWidth * imgSizeCoefficient;
        }
      }
  
      imageTag.width(width);
      imageTag.height(height);
      imageTag.attr("fold", 1);
      imageTag.css("cursor", "zoom-out");
    }
  }

  function unfoldImage(imageTag) {
    var fold = imageTag.attr("fold");
    var imgMinWidth = 300;
    var imgMinHeight = 300;

    if (fold == 0) {
      return;
    } else {
      var tmpImg = new Image();
      tmpImg.src = imageTag.attr("src");
  
      var imgWidth = tmpImg.width;
      var imgHeight = tmpImg.height;

      var width = 50;
      var height = 50;
      
      if(imgWidth < imgMinWidth && imgHeight < imgMinHeight) {
        if(imgWidth > 0 && imgHeight > 0) {
          width = imgWidth;
          height = imgHeight;
        }
      } else {
        if(imgWidth > imgHeight) {
          width = imgMinWidth;
          var imgSizeCoefficient = imgMinWidth / imgWidth;
          height = imgHeight * imgSizeCoefficient;
        } else {
          height = imgMinHeight;
          var imgSizeCoefficient = imgMinHeight / imgHeight;
          width = imgWidth * imgSizeCoefficient;
        }
      }
  
      imageTag.width(width);
      imageTag.height(height);
      imageTag.attr("fold", 0);
      imageTag.css("cursor", "zoom-in");
    }
  }

  $("img[name='articleImage'][pk='"+pk+"']").each(function() {
    unfoldImage($(this));
  });

  $("button[name='findComment'][pk='${articleLongVO.privateKey}']").click(function () {
    if($("div[name='commentDetailList'][pk='${articleLongVO.privateKey}']").length) {
      $("div[name='commentDiv'][pk='${articleLongVO.privateKey}']").show();
      $("div[name='commentDetailList'][pk='${articleLongVO.privateKey}']").show();
      return;
    }

    var pk = "${articleLongVO.privateKey}";

    var url = "${pageContext.request.contextPath}/articleComment/findArticleCommentPage";
    var jsonOutput = {
      pk:pk
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      // dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        $("div[name='commentDiv'][pk='${articleLongVO.privateKey}']").append(datas);
      },  
      error: function(datas) {

      }  
    });  
  });

  $("span[name='evaluation'][pk='${articleLongVO.privateKey}']").click(function() {
    if($("span[name='evaluation'][pk='${articleLongVO.privateKey}']").attr("voted") == "1") {
      $("span[name='evaluationResult'][pk='${articleLongVO.privateKey}']").text("已评...请勿重复操作...");
      return;
    }
    var url = "${pageContext.request.contextPath}/article/insertArticleLongEvaluation";
    var pk = "${articleLongVO.privateKey}";
    var evaluationCode = $(this).attr("evaluationCode");
    var evaluationCountSpan = $("span[name='evaluationCount'][pk='${articleLongVO.privateKey}'][evaluationCode='"+evaluationCode+"']");

    var jsonOutput = {
      pk:pk,
      evaluationCode:evaluationCode
    }

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        if(datas.result == "0") {
          $("span[name='evaluation'][pk='${articleLongVO.privateKey}']").attr("voted", "1");
          evaluationCountSpan.text(parseInt(evaluationCountSpan.text()) + 1);
        } 
        $("span[name='evaluationResult'][pk='${articleLongVO.privateKey}']").text(datas.message);

      },  
      error: function(datas) {

      }  
    }); 
  });

  <sec:authorize access="hasRole('ROLE_USER')">
  $("button[name='delete'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = "${articleLongVO.privateKey}";

    var url = "${pageContext.request.contextPath}/article/deleteArticle";
    var jsonOutput = {
      pk:pk
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        if(datas.result == "0") {
          $("button[name='delete'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text("已删除");
        } else {
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });
  </sec:authorize>

  

  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">

  $("button[name='trySetArticleHot'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = $(this).attr("pk");

    $("button[name='trySetArticleHot'][pk='"+pk+"']").fadeOut(300);
    $(".setArticleHotOption[pk='"+pk+"']").fadeIn(300);
  });

  $("button[name='setArticleHot'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = $(this).attr("pk");
    var url = "${pageContext.request.contextPath}/articleAdmin/setArticleHot";

    var jsonOutput = {};
    jsonOutput.pk = pk;
    jsonOutput.hotMinutes = $("select[name='setArticleHotMinutes'][pk='"+pk+"'] option:selected").val();
    jsonOutput.hotLevel = $("input[name='setArticleHotLevel'][pk='"+pk+"']").val();

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        // if(datas.result == 0) {
        //   $("button[name='setArticleHot'][pk='"+pk+"']").prop("disabled",true);
        // }
        $("span[name='manageResult'][pk='"+pk+"']").text(datas.message);
      },  
      error: function(datas) {

      }  
    }); 
  });

  $("button[name='passByAdmin'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = "${articleLongVO.privateKey}";

    var url = "${pageContext.request.contextPath}/articleAdmin/passArticle";
    var jsonOutput = {
      pk:pk
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        if(datas.result == "0") {
          $("button[name='passByAdmin'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("button[name='rejectByAdmin'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("div[name='changeChannelDiv']").remove();
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text("已通过");
        } else {
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });
  $("button[name='rejectByAdmin'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = "${articleLongVO.privateKey}";

    var url = "${pageContext.request.contextPath}/articleAdmin/rejectArticle";
    var jsonOutput = {
      pk:pk
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        if(datas.result == "0") {
          $("button[name='passByAdmin'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("button[name='rejectByAdmin'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("div[name='changeChannelDiv']").remove();
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text("已拒绝");
        } else {
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });
  $("button[name='deleteByAdmin'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = "${articleLongVO.privateKey}";

    var url = "${pageContext.request.contextPath}/articleAdmin/deleteArticle";
    var jsonOutput = {
      pk:pk
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        if(datas.result == "0") {
          $("button[name='deleteByAdmin'][pk='${articleLongVO.privateKey}']").prop("disabled",true);
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text("已删除");
        } else {
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text(datas.message);
        }

      },  
      error: function(datas) {

      }  
    });  
  });

  $("button[name='findChannel'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = "${articleLongVO.privateKey}";
    var url = "${pageContext.request.contextPath}/articleAdmin/findArticleChannel";
    var jsonOutput = {
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        var channelSelector = $("select[name='findChannel'][pk='"+pk+"']");
        channelSelector.empty();
        channelSelector.append($("<option></option>").attr("value", "").text("请问要转到哪里?"));
        channelSelector.show();
        $("button[name='changeChannel'][pk='"+pk+"']").show();
        $.each(datas, function(index, channel) {
          channelSelector.append($("<option></option>").attr("value", channel.uuid).text(channel.channelName));
        });
        $("button[name='findChannel'][pk='${articleLongVO.privateKey}']").hide();
      },  
      error: function(datas) {

      }  
    });  
  });

  $("button[name='changeChannel'][pk='${articleLongVO.privateKey}']").click(function () {
    var pk = "${articleLongVO.privateKey}";
    var uuid = $("select[name='findChannel'][pk='"+pk+"'] option:selected").val();

    var url = "${pageContext.request.contextPath}/articleAdmin/changeChannel";
    var jsonOutput = {
      pk:pk,
      uuid:uuid
    };

    $.ajax({  
      type : "POST",  
      async : true,
      url : url,  
      data: JSON.stringify(jsonOutput),
      cache : false,
      contentType: "application/json",
      dataType: "json",
      timeout:50000,  
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      success:function(datas){
        if(datas.result == "0") {
          $(".row[pk='${articleLongVO.privateKey}']").remove();  
        } else {
          $("span[name='reviewResult'][pk='${articleLongVO.privateKey}']").text(datas.message);
        }
      },  
      error: function(datas) {

      }  
    });  
  });
  </sec:authorize>

});
</script>
</footer>
</html>