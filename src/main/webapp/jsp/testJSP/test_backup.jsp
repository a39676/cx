<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
  uri="http://www.springframework.org/security/tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp"%>
</head>
<sec:csrfMetaTags />
<title>${ title }</title>

<button id="t2">t2</button>
<button id="printToken" onclick="printCfToken()">printToken</button>
<button id="tokenVerify">tokenVerify</button>

<div id="captcha" class="cf-turnstile" 
  data-sitekey="${siteKey}" data-callback="cfCallBack" 
  error-callback="errorCallBack" token="">
  <br>
  captcha
  <br>
</div>

<footer> </footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>
<script type="text/javascript">

  function cfCallBack() {
    turnstile.render('#captcha', {
      sitekey : '${siteKey}',
      callback : function(token) {
        document.getElementById("captcha").setAttribute("token", token);
      },
    });
  };

  function errorCallBack() {
    console.log("errorCallBack");
  };

  function printCfToken(){
    var token = document.getElementById("captcha").getAttribute("token");
    console.log("token: " + token);
  }

  $(document).ready(function() {

    $("#t2").click(function() {
      var url = "/test2/t2";
      var jsonOutput = {};
      console.log(jsonOutput);
      $.ajax({
        type : "POST",
        url : url,
        data : JSON.stringify(jsonOutput),
        dataType : 'json',
        contentType : "application/json",
        beforeSend : function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout : 15000,
        success : function(data) {
          console.log(data);
          // $("#searchResult").html(data);
        },
        error : function(e) {
          console.log(data);
          // $("#result").text(e);
        }
      });
    });

    $("#tokenVerify").click(function() {
      tokenVerify();
    });

    function tokenVerify() {

      var token = document.getElementById("captcha").getAttribute("token");

      var url = "/test2/t4?token="+token;
      // var jsonOutput = {};
      // console.log(jsonOutput);
      $.ajax({
        type : "GET",
        url : url,
        // data : JSON.stringify(jsonOutput),
        dataType : 'json',
        contentType : "application/json",
        beforeSend : function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        timeout : 15000,
        success : function(data) {
          console.log(data);
          // $("#searchResult").html(data);
        },
        error : function(e) {
          console.log(data);
          // $("#result").text(e);
        }
      });
    }
  });
</script>
