<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp"%>
</head>
<sec:csrfMetaTags />
<title>${ title }</title>

<body>

  <label id="msg"></label>

  <div>
    <div class="row">
      <div class="col-md-12">
        <%@ include file="./userSelector.jsp"%>
      </div>
    </div>


    <div class="row">
      <div class="col-md-12">
        <button id="getPositionInfo">getPositionInfo</button>
      </div>
      <div class="col-md-12">
        <button id="getOpenOrders">getOpenOrders</button>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div id="positionInfoResult">
        </div>
      </div>
      <div class="col-md-12">
        <div id="openOrdersResult">
        </div>
      </div>
    </div>
  
</body>



<footer>
  
</footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp"%>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js?" async defer></script>

<script type="text/javascript">
  $(document).ready(function() {
    $("#getPositionInfo").click(function() {
      getPositionInfo();
    });

    function getPositionInfo(){
      var url = "/cryptoTradingFutureCm/positionInfoCm";

      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      
      var jsonOutput = {
        userId:selectedUserId,
        userNickname:selectedUserNickname,
      };

      $("#msg").text("sending");
      $("#positionInfoResult").html("");
      $.ajax({
        type : "POST",
        async : true,
        url : url,
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        <%-- dataType: "json", --%>
        timeout:50000,
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#positionInfoResult").html(datas);
          $("#msg").text("");
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }

    $("#getOpenOrders").click(function() {
      openOrdersResult();
    });

    function openOrdersResult(){
      var url = "/cryptoTradingFutureCm/getOpenOrdersCm";

      var selectedUser = $('#userSelector').find(":selected");
      var selectedUserId = selectedUser.val();
      var selectedUserNickname = selectedUser.attr("userNickname");
      
      var jsonOutput = {
        userId:selectedUserId,
        userNickname:selectedUserNickname,
      };

      $("#msg").text("sending");
      $("#openOrdersResult").html("");
      $.ajax({
        type : "POST",
        async : true,
        url : url,
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        <%-- dataType: "json", --%>
        timeout:50000,
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#openOrdersResult").html(datas);
          $("#msg").text("");
        },
        error: function(datas) {
          $("#msg").text(datas.message);
        }
      });
    }
  });
</script>