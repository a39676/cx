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
  <a href="/optionConstant/refreshSystemOption" target="_blank">refreshSystemOption</a><br>
  <a href="/optionConstant/refreshToolOption" target="_blank">refreshToolOption</a><br>
  <a href="/optionConstant/refreshArticleOption" target="_blank">refreshArticleOption</a><br>
  <a href="/optionConstant/refreshArticleCommentOption" target="_blank">refreshArticleCommentOption</a><br>
  <a href="/optionConstant/refreshMailOption" target="_blank">refreshMailOption</a><br>
  <a href="/optionConstant/refreshCloudinaryOption" target="_blank">refreshCloudinaryOption</a><br>
  <a href="/optionConstant/refreshAutomationTestOption" target="_blank">refreshAutomationTestOption</a><br>
  
  <a href="/optionConstant/refreshCryptoCoinOption" target="_blank">refreshCryptoCoinOption</a><br>
  <a href="/optionConstant/refreshCurrencyExchangeRateOption" target="_blank">refreshCurrencyExchangeRateOption</a><br>
  
  <a href="/optionConstant/refreshTelegramOption" target="_blank">refreshTelegramOption</a><br>
  <a href="/optionConstant/refreshEducateOption" target="_blank">refreshEducateOption</a><br>
  
  <a href="/optionConstant/refreshJoyOption" target="_blank">refreshJoyOption</a><br>
  <a href="/optionConstant/refreshJoyGardenOption" target="_blank">refreshJoyGardenOption</a><br>
  <a href="/optionConstant/refreshOpenAiOption" target="_blank">refreshOpenAiOption</a><br>
  <a href="/optionConstant/refreshAiCommonOption" target="_blank">refreshAiCommonOption</a><br>
  <a href="/optionConstant/refreshAiChatOption" target="_blank">refreshAiChatOption</a><br>
  <a href="/optionConstant/refreshAiArtOption" target="_blank">refreshAiArtOption</a><br>
  <a href="/optionConstant/refreshCloudFlareOption" target="_blank">refreshCloudFlareOption</a><br>
  
  <a href="/optionConstant/refreshWechatOption" target="_blank">refreshWechatOption</a><br>
  <a href="/optionConstant/refreshWechatPayOption" target="_blank">refreshWechatPayOption</a><br>

  <a href="/optionConstant/refreshPromoteOption" target="_blank">refreshPromoteOption</a><br>

  <a href="/optionConstant/refreshZulipOption" target="_blank">refreshZulipOption</a><br>
  
  <a href="/optionConstant/refreshCnStockMarketOption" target="_blank">refreshCnStockMarketOption</a><br>
  
  <a href="/optionConstant/refreshTaobaoProductSourceOptionService" target="_blank">refreshTaobaoProductSourceOptionService</a><br>
  
  
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
      var url = "/admin/deleteUserIpRecord";
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
      var url = "/articleAdmin/batchUpdatePrivateKey";
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
          $("#result").val(datas.message);
        },
        error: function(datas) {
          $("#result").val(datas.message);
        }
      });
    };

  });

</script>
</footer>
</html>
