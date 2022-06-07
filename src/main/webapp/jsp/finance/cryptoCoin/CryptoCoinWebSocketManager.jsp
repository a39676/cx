<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">

<div class="row">
  <input type="text" id="allSubscription" value="">
</div>

<div class="row">
  <input type="text" name="" id="addSubscription" value="" placeholder="addSubscription">
  <button id="addSubscriptionButton">addSubscription</button>
</div>

<div class="row">
  <input type="text" name="" id="removeSubscription" value="" placeholder="removeSubscription">
  <button id="removeSubscriptionButton">removeSubscription</button>
</div>

<div class="row">
  <button id="refreshSubscription">refreshSubscription</button>
  <button id="removeAllSubscription">removeAllSubscription</button>
</div>

<div class="row">
  <button id="restartCoinCompareWS">restartCoinCompareWS</button>
  <button id="restartBinanceWS">restartBinanceWS</button>
</div>

<div class="row">
  <textarea id="result" disabled rows="4" cols="50"></textarea>
</div>

</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#refreshSubscription").click(function () {
        getAllSubscription();
      });

      $("#addSubscriptionButton").click(function () {
        var coinName = $("#addSubscription").val();
        addSubscription(coinName);
      })

      $("#removeSubscriptionButton").click(function () {
        var coinName = $("#removeSubscription").val();
        removeSubscription(coinName);
      })

      $("#removeAllSubscription").click(function () {
        removeAllSubscription();
      })

      $("#restartCoinCompareWS").click(function () {
        restartCoinCompareWS();
      })

      $("#restartBinanceWS").click(function () {
        restartBinanceWS();
      })

      getAllSubscription();

      function getAllSubscription() {

        var url = "/cryptoCoin/getSubscriptionCatalog";

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
            var result = "";
            data.forEach(element => result = result + element["enShortname"] + ", ");
            $("#allSubscription").val(result);
          },
          error:function(e){
          }
        });
      };

      function addSubscription(coinName) {

        var url = "/cryptoCoinManager/addSubscriptionCatalog" + "?coinName=" + coinName;

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
            getAllSubscription();
            $("#addSubscription").val("");
          },
          error:function(e){
          }
        });
      };

      function removeSubscription(coinName) {

        var url = "/cryptoCoinManager/removeSubscriptionCatalog" + "?coinName=" + coinName;

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
            getAllSubscription();
            $("#removeSubscription").val("");
          },
          error:function(e){
          }
        });
      };

      function removeAllSubscription() {

        var url = "/cryptoCoinManager/removeAllSubscriptionCatalog";

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
            getAllSubscription();
          },
          error:function(e){
          }
        });
      };

      function restartCoinCompareWS() {

        var url = "/cryptoCoinManager/restartCoinCompareWS";

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
            $("#result").val(data + ", restartCoinCompareWS");
          },
          error:function(e){
          }
        });
      };

      function restartBinanceWS() {

        var url = "/cryptoCoinManager/restartBinanceWS";

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
            $("#result").val(data + ", restartBinanceWS");
          },
          error:function(e){
          }
        });
      };
    });

  </script>
</footer>
</html>
