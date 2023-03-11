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

<div class="cf-turnstile" data-sitekey="${siteKey}"
  data-callback="javascriptCallback"></div>

<footer> </footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script
  src="https://challenges.cloudflare.com/turnstile/v0/api.js?compat=recaptcha"
  async defer></script>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js"
  async defer></script>
<script type="text/javascript">

  // window.onloadTurnstileCallback = function() {
  //   turnstile.render('#example-container', {
  //     sitekey : '${siteKey}',
  //     callback : function(token) {
  //       console.log(`Challenge Success ${token}`);
  //     },
  //   });
  // };


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


    function cfVerify() {
      turnstile.render('#example-container', {
        sitekey : '${siteKey}',
        callback : function(token) {
          console.log("Challenge Success token: " + token);
          sendToken(token);
        },
      });
    };

    cfVerify();

    function sendToken(token) {
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
