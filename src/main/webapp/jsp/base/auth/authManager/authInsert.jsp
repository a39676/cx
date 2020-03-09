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
      <span class="badge">机构名称:</span><span class="badge badge-primary">${orgName}</span>
    </div>
  </div>
  
  <div class="row">
    <div class="col-md-4" >
      <input type="text" id="newAuthName" placeholder="new auth name">
      <div class="btn-group" id="submitNewAuthNameBtnGroup">        
        <button class="btn btn-sm btn-primary" id="submitNewAuthName">submit</button>
        <button class="btn btn-sm btn-secondary" id="cancelSubmitNewAuthName">cancel</button>
      </div>
    </div>
  </div>

  <hr>

  <!-- result row start-->
  <div class="row">
    <div class="col-md-12">
      <input type="" name="" id="authOperatorResultMsg" disabled="disabled" style="width: 800px;">
    </div>
  </div>
  <!-- result row end-->

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("#submitNewAuthName").click(function () {
        authNameEdit();
      });

      function authNameEdit() {
        var url = "/auth/insertAuth"
        var belongOrgPK = "${orgPK}";
        var authName = $("#newAuthName").val();

        var jsonOutput = {
          authName:authName,
          belongOrgPK:belongOrgPK
        };

        $.ajax({               
          type: "POST",  
          url: url,
          data: JSON.stringify(jsonOutput),
          dataType: 'json',
          contentType: "application/json",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          timeout: 15000,
          success:function(data){
            if(data.code == "0") {
              $("#authOperatorResultMsg").val("auth name edit success ");
            } else {
              $("#authOperatorResultMsg").val("edit fail: " + data.message);
            }
          }, 
          error:function(e){
          }
        });  
      }

      $("#cancelSubmitNewAuthName").click(function () {
        window.close();
      });

    });

  </script>
</footer>
</html>