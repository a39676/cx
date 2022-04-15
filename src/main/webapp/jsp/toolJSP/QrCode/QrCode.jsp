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
        <div class="col-sm-12" >
          <%@ include file="../../summernote/summernote.jsp" %>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-12" >
          <div class="btn-group">
            <button class="btn  btn-primary btn-sm" id="Decode">
              <span class="badge badge-primary">Decode</span>
            </button>
            <button class="btn  btn-primary btn-sm" id="Generator">
              <span class="badge badge-primary">Generator</span>
            </button>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-12" >
          <span id="outputResult" badge badge-primary></span>
        </div>
      </div>

      <div class="row">
        <div class="col-sm-12">
          <img id="qrcodeResult" src="" alt="">
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

      function base64Encode(str) {
        var CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        var out = "", i = 0, len = str.length, c1, c2, c3;
        while (i < len) {
          c1 = str.charCodeAt(i++) & 0xff;
          if (i == len) {
            out += CHARS.charAt(c1 >> 2);
            out += CHARS.charAt((c1 & 0x3) << 4);
            out += "==";
            break;
          }
          c2 = str.charCodeAt(i++);
          if (i == len) {
            out += CHARS.charAt(c1 >> 2);
            out += CHARS.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
            out += CHARS.charAt((c2 & 0xF) << 2);
            out += "=";
            break;
          }
          c3 = str.charCodeAt(i++);
          out += CHARS.charAt(c1 >> 2);
          out += CHARS.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
          out += CHARS.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
          out += CHARS.charAt(c3 & 0x3F);
        }
        return out;
      }

      $("#Decode").click(function () {
        var url = "/tool/qrcode/decode";
        var s = $('#summernote');
        var content = s.summernote('code');

        var jsonOutput = {
          content:content
        };

        var resultSpan = document.getElementById("outputResult");
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
            resultSpan.innerHTML = datas;
          },
          error: function(datas) {
          }
        });
      });

      $("#Generator").click(function () {
        var url = "/tool/qrcode/generator";
        var s = $('#summernote');
        var content = s.summernote('code');

        var jsonOutput = {
          content:content
        };

        $.ajax({
          type : "POST",
          async : true,
          url : url,
          data: JSON.stringify(jsonOutput),
          cache : false,
          contentType: "application/json",
          // dataType: "image/jpg",
          timeout:50000,
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            $('#qrcodeResult').attr('src', "data:image/png;base64," + datas);
          },
          error: function(datas) {
          }
        });
      });


    });
  </script>
</footer>
</html>
