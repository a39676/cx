<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<sec:csrfMetaTags />
<title>${ title }</title>
</head>
<body>
<div class="container-fluid">

  <div class="row">
    <div class="container-fluid" name="createArticleLong" >
      <sec:authorize access="hasRole('ROLE_USER')">
      <div class="row">
        <div class="col-md-12" >
          <span class="badge badge-primary">请选择提交频道</span>
          <select class="" name="channelList" style="">
            <c:forEach items="${channelList}" var="subChannel">
              <c:if test="${articleVO.channelId != null && articleVO.channelId == subChannel.channelId}">
                <option value="${subChannel.channelId}">${subChannel.channelName}</option>
              </c:if>
            </c:forEach>
            <c:forEach items="${channelList}" var="subChannel">
              <option value="${subChannel.channelId}">${subChannel.channelName}</option>
            </c:forEach>
          </select>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12" >
          <textarea class="input form-control" id="articleTitle" rows="1" cols="50" placeholder="请输入标题~">${articleVO.articleTitle}</textarea>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12" >
          <%@ include file="../summernote/summernote.jsp" %>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12" >
          <span>有效时间</span>
          <input type="Date" id="validDate" value="${articleVO.validDateStr}">
          <c:if test="${createNew == true}">
            <input type="time" name="" id="validTime" value="23:59:59" step="1">
          </c:if>
          <c:if test="${edit == true}">
            <input type="time" name="" id="validTime" value="${articleVO.validTimeStr}" step="1">
          </c:if>
        </div>
      </div>

      <div class="row">
        <div class="col-md-12" >
          <div class="btn-group">
            <sec:authorize access="hasRole('ROLE_POSTER')">
              <textarea class="input form-control" type="text"
              name="superAdminKey" placeholder="please insert key"></textarea>
            </sec:authorize>
            <c:if test="${createNew == true}">
              <button class="btn  btn-primary btn-sm"
                id="createNew">
                <span class="badge badge-primary">提交</span>
              </button>
              <button class="btn  btn-primary btn-sm"
                id="editorAgain">
                <span class="badge badge-primary">继续编辑</span>
              </button>
            </c:if>
            <c:if test="${edit == true}">
              <button class="btn  btn-primary btn-sm"
                id="edit">
                <span class="badge badge-primary">提交编辑</span>
              </button>
            </c:if>
          </div>
        </div>
      </div>

      <div id="sourceArticleVO" pk="${articleVO.privateKey}" disabled="disabled" style="display: none;" contentLines='${articleVO.contentLines}'></div>

      <div class="row">
        <div class="col-md-12" >
          <span id="createArticleResult" badge badge-primary></span>
        </div>
      </div>
      </sec:authorize>
  </div> <%-- createArticleLong container --%>

</div> <%-- main row --%>
</body>

<footer>

  <script type="text/javascript">
    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
  </script>

  <script type="text/javascript">


    $(document).ready(function() {

      <c:if test="${createNew == true}">
      $("#createNew").click(function () {
        var url = "/article/createArticleLong";
        var title = $("#articleTitle").val();
        var s = $('#summernote');
        var content = s.summernote('code');
        var channelId = $("select[name='channelList'] option:selected").val();
        var validDate = $("#validDate").val();
        var validTime = $("#validTime").val();

        if(validDate.length > 0){
          validTime = validDate + " " + validTime;
        } else {
          validTime = "";
        }

        var jsonOutput = {
          channelId:channelId,
          title:title,
          content:content,
          validTime:validTime,
        };

        var resultSpan = document.getElementById("createArticleResult");
        resultSpan.innerHTML = "";

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
            resultSpan.innerHTML = datas.message;
            if(datas.result == "0") {
              document.getElementById("articleTitle").disabled = true;
              document.getElementById("summernote").disabled = true;
              document.getElementById("createNew").disabled = true;
            }
          },
          error: function(datas) {
          }
        });
      });

      $("#createNew").click(function () {
        document.getElementById("articleTitle").disabled = false;
        document.getElementById("summernote").disabled = false;
        document.getElementById("createNew").disabled = false;
      });
      </c:if>

      <c:if test="${edit == true}">
      var contentLines = $("#sourceArticleVO").attr("contentLines");
      $("#summernote").summernote("code", contentLines);

      $("#edit").click(function () {
        var url = "/article/editArticleLong";
        var title = $("#articleTitle").val();
        var s = $('#summernote');
        var content = s.summernote('code');
        var channelId = $("select[name='channelList'] option:selected").val();
        var pk = $("#sourceArticleVO").attr("pk");
        var validTime = $("#validTime").val();
        var validDate = $("#validDate").val();


        var jsonOutput = {
          channelId:channelId,
          title:title,
          content:content,
          pk:pk,
          validTime:validDate + " " + validTime,
        };

        var resultSpan = document.getElementById("createArticleResult");
        resultSpan.innerHTML = "";

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
            resultSpan.innerHTML = datas.message;
            if(datas.result == "0") {
              document.getElementById("articleTitle").disabled = true;
              document.getElementById("summernote").disabled = true;
              document.getElementById("edit").disabled = true;
            }
          },
          error: function(datas) {
          }
        });
      });

      </c:if>

    });
  </script>
</footer>
</html>
