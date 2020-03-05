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
      <label>orgName</label>
      <input class="orgSearchConditionInput" type="text" name="orgName" id="orgName" placeholder="orgName">
    </div>
    <div class="col-md-4" >
      <label>orgPk</label>
      <input class="orgSearchConditionInput" type="text" name="orgPk" id="orgPk" placeholder="orgPk">
    </div>
    <div class="col-md-4" >
      <label>creatorPk</label>
      <input class="orgSearchConditionInput" type="text" name="creatorPk" id="creatorPk" placeholder="creatorPk">
    </div>
  </div>

  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <div class="row">
      <div class="col-md-4" >
        <label>orgId</label>
        <input class="orgSearchConditionInput" type="text" name="orgId" id="orgId" placeholder="orgId">
      </div>
      <div class="col-md-4" >
        <label>belongTo</label>
        <input class="orgSearchConditionInput" type="text" name="belongTo" id="belongTo" placeholder="belongTo">
      </div>
      <div class="col-md-4" >
        <label>topOrg</label>
        <input class="orgSearchConditionInput" type="text" name="topOrg" id="topOrg" placeholder="topOrg">
      </div>
    </div>
  </sec:authorize>

  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <div class="row">
      <div class="col-md-4" >
        <label>creatorName</label>
      <input class="orgSearchConditionInput" type="text" name="creatorName" id="creatorName" placeholder="creatorName  ">
      </div>
    </div>
  </sec:authorize>

  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <div class="row" id="orgConditionValue2" isDelete="false">
      <div class="col-md-4" >
        <label class="badge">isDelete</label>
        <div class="form-check form-check-inline">
          <input class="form-check-input orgSearchConditionInput" type="radio" id="orgIsDeleteTrue" name="orgIsDeleteType" value="true">
          <label class="form-check-label badge" for="orgIsDeleteTrue" name="orgIsDeleteValue" value="true">True</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input orgSearchConditionInput" type="radio" id="orgIsDeleteFalse" name="orgIsDeleteType"  checked="checked" value="false">
          <label class="form-check-label badge" for="orgIsDeleteFalse" name="orgIsDeleteValue" value="false">False</label>
        </div>
      </div>
    </div>
  </sec:authorize>
  
  <hr>

  <div class="row">
    <div class="col-md-4" >
      <button id="cleanOrgCondition" class="btn btn-sm btn-warning">clean org search condition</button>
    </div>
  </div>
  
  <div class="row">
    <div class="col-md-12">
      <div id="orgNameDiv" selectedOrgPk=""></div>
    </div>
  </div>

  <hr>

</div>
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      var orgIsDelete = false;
      $("input[name='orgIsDeleteType']").click(function () {
        orgIsDeleteValue($(this).val());
      });
      function orgIsDeleteValue(e) {
        $("#orgConditionValue2").attr("isDelete", e);
        orgIsDelete = $("#orgConditionValue2").attr("isDelete");
      }

      $("#cleanOrgCondition").click(function () {
        cleanOrgCondition();
      });

      function cleanOrgCondition() {
        $("#orgName").val("");
        $("#orgPk").val("");
        $("#creatorPk").val("");
        $("#orgId").val("");
        $("#belongTo").val("");
        $("#topOrg").val("");
        $("#creatorName").val("");
        $("#orgIsDeleteTrue").prop("checked", false);
        $("#orgIsDeleteFalse").prop("checked", true);
        orgIsDeleteValue("false");

        var orgNameDiv = $("#orgNameDiv");
        orgNameDiv.empty();

        $("#orgNameDiv").attr("selectedOrgPk", "");
      }

      $(".orgSearchConditionInput").change(function () {
        $("#orgNameDiv").attr("selectedOrgPk", "");

        var orgName = $("#orgName").val();
        var orgPk = $("#orgPk").val();
        var creatorPk = $("#creatorPk").val();
        var orgId = $("#orgId").val();
        var belongTo = $("#belongTo").val();
        var topOrg = $("#topOrg").val();
        var creatorName = $("#creatorName").val();
        var isDelete = orgIsDelete;

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