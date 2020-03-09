<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container-fluid">

  <div class="row">
    <div class="col-md-12">
      <span class="badge">机构名称:</span><span class="badge badge-primary">${orgVO.orgName}</span>
    </div>
  </div>

  <hr>

  <!-- auth search start-->
  <div class="row">
    <div class="col-md-12">
      <%@ include file="../../auth/authManager/authSearch.jsp" %>
    </div>
  </div>
  <!-- auth search end-->

  <hr>

  <div class="row">
    <div class="col-md-12">
      <button class="btn btn-sm btn-success" id="addAuth">add new auth</button>
      <button class="btn btn-sm btn-primary" id="editAuth">edit this auth</button>
      <button class="btn btn-sm btn-danger" id="delAuth">delete auth</button>
    </div>
  </div>

  <hr>

  <!-- result row start-->
  <div class="row">
    <div class="col-md-12">
      <input type="" name="" id="userAuthOperatorResultMsg" disabled="disabled" style="width: 800px;">
    </div>
  </div>
  <!-- result row end-->

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#delAuth").click(function () {
        var url = "/auth/deleteAuth";
        var orgPk = "${orgPk}";
        
        var authPkList = [];

        var targetAuthEleList = document.querySelectorAll('.authButton.btn-primary');
        for (var i = targetAuthEleList.length - 1; i >= 0; i--) {
          authPkList.push(targetAuthEleList[i].getAttribute("pk"));
        }

        var jsonOutput = {
          orgPk : orgPk,
          authPkList : authPkList
        };

        var userAuthOperatorResultMsg = $("#userAuthOperatorResultMsg");
    
        $.each(authPkList, function(index, authPk) {
          var targetAuthList = document.querySelectorAll('.authButton[pk="'+authPk+'"]');
          if(targetAuthList != null) {
            var targetAuth = targetAuthList[0];
            if(targetAuth != null) {
              targetAuth.parentNode.removeChild(targetAuth);
            }
          }
          userAuthOperatorResultMsg.val("edit success: ");  
        });
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
              $.each(authPkList, function(index, authPk) {
                var targetAuthList = document.querySelectorAll('.authButton[pk="'+authPk+'"]');
                if(targetAuthList != null) {
                  var targetAuth = targetAuthList[0];
                  if(targetAuth != null) {
                    targetAuth.parentNode.removeChild(targetAuth);
                  }
                }
                userAuthOperatorResultMsg.val("edit success: ");  
              });
            } else {
              userAuthOperatorResultMsg.val("edit fail: " + data.message);
            }
          },  
          error: function(datas) {              
          }  
        });  
      });

      $("#editAuth").click(function () {

        var authPkList = [];

        var targetAuthEleList = document.querySelectorAll('.authButton.btn-primary');
        for (var i = targetAuthEleList.length - 1; i >= 0; i--) {
          authPkList.push(targetAuthEleList[i].getAttribute("pk"));
        }

        var authPK = authPkList[0];
        
        var url = "/auth/authEdit?authPK=" + encodeURIComponent(authPK);
        window.open(url, '_blank');
      });

      $("#addAuth").click(function () {
        var url = "/auth/insertAuth?orgPK=" + encodeURIComponent("${orgPk}");
        window.open(url, '_blank');
      });

    });

  </script>
</footer>
</html>