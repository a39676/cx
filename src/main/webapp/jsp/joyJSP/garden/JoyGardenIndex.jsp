<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid" style="background-image: url('data:image/png;base64,${backgroundImgSrc}'); background-attachment: inherit;">
  <div class="row">
    <div class="col-md-12">
      ${gardenInfo}
      <span>欢迎光临${nickname}的${gardenInfo.gardenName}</span>
    </div>
  </div>

  <div class="row" id="fieldLandRow">
  </div>

  <div class="row">
    <div class="col-md-11">
      <span id="createFieldLandResult"></span>
    </div>
    <div class="col-md-1">
      <button type="button" name="" class="" id="shopNpc">
        <span>商店</span>
        <img src='data:image/png;base64,${gardenShopNpcImgSrc}' alt=""
          style="-moz-transform:scaleX(-1);-webkit-transform:scaleX(-1);-o-transform:scaleX(-1);transform:scaleX(-1);">
      </button>
    </div>
  </div>
</div>
</body>

<footer>
  <%@ include file="../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      getFieldLandView();

      function getFieldLandView() {
        var url = "/joy/garden/getFieldLandView";

        var jsonOutput = {};

        $.ajax({
          type : "POST",
          url : url,
          data: JSON.stringify(jsonOutput),
          // dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            $("#fieldLandRow").html(data);
          },
          error:function(e){
          }
        });
      }
    });

  </script>
</footer>
</html>
