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
    <div class="col-md-4" >
      <label>authName</label>
      <input class="conditionInput" type="text" name="authName" id="authName" placeholder="authName">
    </div>
    <div class="col-md-4" >
      <label>authPk</label>
      <input class="conditionInput" type="text" name="authPk" id="authPk" placeholder="authPk">
    </div>
  </div>

  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <div class="row" id="conditionValue2" isDelete="false">
      <div class="col-md-4" >
        <label class="badge">isDelete</label>
        <div class="form-check form-check-inline">
          <input class="form-check-input conditionInput" type="radio" id="isDeleteTrue" name="isDeleteType" value="true">
          <label class="form-check-label badge" for="isDeleteTrue" name="isDeleteValue" value="true">True</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input conditionInput" type="radio" id="isDeleteFalse" name="isDeleteType" checked="checked" value="false">
          <label class="form-check-label badge" for="isDeleteFalse" name="isDeleteValue" value="false">False</label>
        </div>
      </div>
    </div>
  </sec:authorize>
  
  <hr>
  
  <div class="row">
    <div class="col-md-12">
      <div id="orgNameDiv" selectedOrgPk=""></div>
    </div>
  </div>

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("input[name='isDeleteType']").click(function () {
        isDeleteValue($(this).val());
      });
      function isDeleteValue(e) {
        $("#conditionValue2").attr("isDelete", e);
      }

      $(".conditionInput").change(function () {
        $("#orgNameDiv").attr("selectedOrgPk", "");

        var orgName = $("#orgName").val();
        var orgPk = $("#orgPk").val();
        var creatorPk = $("#creatorPk").val();
        var orgId = $("#orgId").val();
        var belongTo = $("#belongTo").val();
        var topOrg = $("#topOrg").val();
        var creatorName = $("#creatorName").val();
        var isDelete = $("#conditionValue2").attr("isDelete");

        var url = "/org/findOrgList";

        var jsonOutput = {
          orgName : orgName,
          orgPk : orgPk,
          orgId : creatorPk,
          belongTo : orgId,
          topOrg : belongTo,
          creatorName : topOrg,
          creatorPk : creatorName,
          isDelete : isDelete
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
            var orgVOList = data.orgVOList;
            var orgNameDiv = $("#orgNameDiv");
            orgNameDiv.empty();
            $.each(orgVOList, function(index, orgVOInfo) {
              orgNameDiv.append($("<button class='btn btn-sm btn-light'></button>").attr("pk", orgVOInfo.pk).text(orgVOInfo.orgName));
              orgNameDiv.append($("<label>&nbsp;&nbsp;|&nbsp;&nbsp;</label>"));
              $("button[pk='"+orgVOInfo.pk+"']").addClass("orgButton");
              $("button[pk='"+orgVOInfo.pk+"']").bind("click", orgButtonClick);
            });
          }, 
          error:function(e){
          }
        });  
      });

      function orgButtonClick() {
        var pk = $(this).attr("pk");
        $(".orgButton").addClass("btn-light")
        $(".orgButton").removeClass("btn-primary")
        $(this).removeClass("btn-light")
        $(this).addClass("btn-primary")
        $("#orgNameDiv").attr("selectedOrgPk", pk);
      };

    });

  </script>
</footer>
</html>