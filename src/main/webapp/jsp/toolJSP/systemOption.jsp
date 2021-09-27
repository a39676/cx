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

<div>
  <input type="text" name="customStartDate" value="2018-01-01 00:00:00"><br>
  <input type="text" name="customEndDate" value="2018-01-01 00:00:00"><br>
  <button id="deleteUserIpRecord">deleteUserIpRecord</button><br>
  <button id="batchUpdatePrivateKey">batchUpdatePrivateKey</button><br>
  <button id="createFakeEvaluationStore">createFakeEvaluationStore</button><br>
</div>

<hr>

<div>
  <input type="text" name="setHomepageAnnouncementStr" placeholder="刷新主页公告位"><br>
  <button id="setHomepageAnnouncementStr">setHomepageAnnouncementStr</button><br>
</div>

<hr>

<div>
  <button id="refreshSystemConstant">refreshSystemConstant</button>
  <button id="refreshArticleConstant">refreshArticleConstant</button>
  <button id="refreshArticleCommentConstant">refreshArticleCommentConstant</button>
  <button id="refreshMailConstant">refreshMailConstant</button>
  <button id="refreshCloudinaryConstant">refreshCloudinaryConstant</button>
  <button id="refreshAutomationTestConstant">refreshAutomationTestConstant</button>
  <button id="refreshCryptoCoinConstant">refreshCryptoCoinConstant</button>
</div>

<hr>

<div id="resultView">
  resultView
  <textarea id="result" disabled rows="4" cols="50"></textarea>
</div>

</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#deleteUserIpRecord").click( function() {
      deleteUserIpRecord();
    });

    function deleteUserIpRecord() {

      var url = "${pageContext.request.contextPath}/admin/deleteUserIpRecord";
      var startDate = $("input[name='customStartDate']").val();
      var endDate = $("input[name='customEndDate']").val();
      var jsonOutput = {
        startDate:startDate,
        endDate:endDate
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
          console.log(datas);
          $("#result").val(datas.message);
        },
        error: function(datas) {
          $("#result").val(datas.message);
        }
      });
    };

    $("#batchUpdatePrivateKey").click( function() {
      batchUpdatePrivateKey();
    });

    function batchUpdatePrivateKey() {

      var url = "${pageContext.request.contextPath}/articleAdmin/batchUpdatePrivateKey";
      var startDate = $("input[name='customStartDate']").val();
      var endDate = $("input[name='customEndDate']").val();
      var jsonOutput = {
        startDate:startDate,
        endDate:endDate
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
          console.log(datas);
          $("#result").val(datas.message);
        },
        error: function(datas) {
          $("#result").val(datas.message);
        }
      });
    };

    $("#setHomepageAnnouncementStr").click( function() {
      setHomepageAnnouncementStr();
    });

    function setHomepageAnnouncementStr() {

      var url = "/admin/setHomepageAnnouncementStr";
      var homepageAnnouncementStr = $("input[name='setHomepageAnnouncementStr']").val();
      var jsonOutput = {
        homepageAnnouncementStr:homepageAnnouncementStr
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
          $("#result").val(datas.homepageAnnouncementStr);
        },
        error: function(datas) {
          $("#result").val(datas.message);
        }
      });
    };


    $("#createFakeEvaluationStore").click( function() {
      createFakeEvaluationStore();
    });

    function createFakeEvaluationStore() {

      var url = "/admin/createFakeEvaluationStore";
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
          console.log(datas);
          $("#result").val(datas.message);
        },
        error: function(datas) {
          $("#result").val(datas.message);
        }
      });
    };

    function refreshSystemConstant() {

      var url = "/optionConstant/refreshSystemConstant/";

      $.ajax({
        type : "GET",
        url : url,
        // data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          $("#result").val(data + ", refreshSystemConstant");
        },
        error:function(e){
        }
      });
    };

    function refreshArticleConstant() {

      var url = "/optionConstant/refreshArticleConstant/";

      $.ajax({
        type : "GET",
        url : url,
        // data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          $("#result").val(data + ", refreshArticleConstant");
        },
        error:function(e){
        }
      });
    };

    function refreshArticleCommentConstant() {

      var url = "/optionConstant/refreshArticleCommentConstant";

      $.ajax({
        type : "GET",
        url : url,
        // data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          $("#result").val(data + ", refreshArticleCommentConstant");
        },
        error:function(e){
        }
      });
    };

    function refreshMailConstant() {

      var url = "/optionConstant/refreshMailConstant/";

      $.ajax({
        type : "GET",
        url : url,
        // data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          $("#result").val(data + ", refreshMailConstant");
        },
        error:function(e){
        }
      });
    };

    function refreshCloudinaryConstant() {

      var url = "/optionConstant/refreshCloudinaryConstant";

      $.ajax({
        type : "GET",
        url : url,
        // data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          $("#result").val(data + ", refreshCloudinaryConstant");
        },
        error:function(e){
        }
      });
    };

    function refreshAutomationTestConstant() {

      var url = "/optionConstant/refreshAutomationTestConstant";

      $.ajax({
        type : "GET",
        url : url,
        // data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          $("#result").val(data + ", refreshAutomationTestConstant");
        },
        error:function(e){
        }
      });
    };

    function refreshCryptoCoinConstant() {

      var url = "/optionConstant/refreshCryptoCoinConstant";

      $.ajax({
        type : "GET",
        url : url,
        // data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          $("#result").val(data + ", refreshCryptoCoinConstant");
        },
        error:function(e){
        }
      });
    };

    function refreshTelegramConstant() {

      var url = "/optionConstant/refreshTelegramConstant";

      $.ajax({
        type : "GET",
        url : url,
        // data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout: 15000,
        success:function(data){
          $("#result").val(data + ", refreshTelegramConstant");
        },
        error:function(e){
        }
      });
    };

    $("#refreshArticleConstant").click(function () {
      console.log("get in refreshArticleConstant");
      refreshArticleConstant();
    })

    $("#refreshArticleCommentConstant").click(function () {
      refreshArticleCommentConstant();
    })

    $("#refreshMailConstant").click(function () {
      refreshMailConstant();
    })

    $("#refreshCloudinaryConstant").click(function () {
      refreshCloudinaryConstant();
    })

    $("#refreshAutomationTestConstant").click(function () {
      refreshAutomationTestConstant();
    })

    $("#refreshCryptoCoinConstant").click(function () {
      refreshCryptoCoinConstant();
    })

    $("#refreshTelegramConstant").click(function () {
      refreshTelegramConstant();
    })
  });

</script>
</footer>
</html>
