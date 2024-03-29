<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>

<div class="container-fluid">
  <div class="row">
    <div class="input-group col-md-4">
      文章频道管理__
    </div>
  </div>
</div>

<div class="container-fluid">
  <div class="row">
    <div class="col-md-12">
      <table class="table table-striped">
        <tbody>
          <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
              <tr>
                <td>
                  <span class="btn btn-sm btn-warning" id="showAllChannelKeyHostnameTable" showFlag="0">展开/折叠所有域名挂靠</span>
                </td>
              </tr>
            <c:forEach items="${channelList}" var="channel">
              <tr>
                <td>
                  <c:if test="${channel.isDelete == true}">
                    <span class="badge badge-secondary">${channel.channelName}</span>
                    <span class="badge badge-secondary">${channel.channelId}</span>
                  </c:if>
                  <c:if test="${channel.isDelete == false}">
                    <span class="badge badge-primary">${channel.channelName}</span>
                    <span class="badge badge-primary">${channel.channelId}</span>
                  </c:if>
                </td>
                <td>
                  <c:if test="${channel.isDelete == true}">
                    <button class="badge badge-danger" name="reuseChannel" channelId="${channel.channelId}">reuse</button>
                  </c:if>
                  <c:if test="${channel.isDelete == false}">
                    <button class="badge badge-danger" name="deleteChannel" channelId="${channel.channelId}">delete</button>
                  </c:if>
                </td>
                <td>
                  <span class="badge badge-primary">weights</span>
                  <input type="text" name="channelWeight" channelId="${channel.channelId}" style="width: 50px;" value="${channel.weights}">
                </td>
                <td>
                  <span class="badge badge-primary">channelType</span>
                  <select class="" name="channelType" channelId="${channel.channelId}">
                    <option value="${channel.channelType}">${channel.channelTypeName}</option>
                    <c:forEach items="${channelTypeList}" var="channelType">
                      <option value="${channelType.code}">${channelType.name}</option>
                    </c:forEach>
                  </select>
                </td>
                <td>
                  <span class="badge badge-primary">channel name</span>
                  <input type="text" name="channelNewName" style="width: 100px;" channelId="${channel.channelId}" value="${channel.channelName}">
                </td>
                <td>
                  <button class="btn btn-sm btn-warning" name="modifyChannel" channelId="${channel.channelId}">modify</button>
                </td>
                <td>
                  <textarea id="${channel.channelId}_editResult"></textarea>
                </td>
                <td>
                  <span class="channelKeyHostnameBtn btn btn-sm btn-warning" channelId="${channel.channelId}">域名挂靠</span>
                </td>
              </tr>
              <tr>
                <td>
                  <table class="table table-striped channelKeyHostnameTable" channelId="${channel.channelId}" style="display: none;">
                    <c:forEach items="${channel.channelIdKeyHostnameId}" var="channelKeyHostnameIdDTO">
                      <tr>
                        <td>
                          <span class="badge badge-light">${channelKeyHostnameIdDTO.hostname}</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div class="form-check form-check-inline">
                            <c:if test="${channelKeyHostnameIdDTO.articleChannelKeyHostnameType == 1}">
                              <input class="form-check-input channelKeyHostnameRadio" type="radio"
                                id="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}_pass"
                                channelId="${channel.channelId}"
                                hostnameId="${channelKeyHostnameIdDTO.hostnameId}"
                                name="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}"
                                checked="checked"
                                value="1">
                            </c:if>
                            <c:if test="${channelKeyHostnameIdDTO.articleChannelKeyHostnameType == 2}">
                              <input class="form-check-input channelKeyHostnameRadio" type="radio"
                                id="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}_pass"
                                channelId="${channel.channelId}"
                                hostnameId="${channelKeyHostnameIdDTO.hostnameId}"
                                name="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}"
                                value="1">
                            </c:if>
                            <label class="form-check-label badge badge-success" for="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}_pass">pass</label>
                          </div>
                        </td>
                        <td>
                          <div class="form-check form-check-inline">
                            <c:if test="${channelKeyHostnameIdDTO.articleChannelKeyHostnameType == 2}">
                              <input class="form-check-input channelKeyHostnameRadio" type="radio"
                                id="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}_ban"
                                channelId="${channel.channelId}"
                                hostnameId="${channelKeyHostnameIdDTO.hostnameId}"
                                name="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}"
                                checked="checked"
                                value="2">
                            </c:if>
                            <c:if test="${channelKeyHostnameIdDTO.articleChannelKeyHostnameType == 1}">
                              <input class="form-check-input channelKeyHostnameRadio" type="radio"
                                id="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}_ban"
                                channelId="${channel.channelId}"
                                hostnameId="${channelKeyHostnameIdDTO.hostnameId}"
                                name="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}"
                                value="2">
                            </c:if>
                            <label class="form-check-label badge badge-danger" for="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}_ban">ban</label>
                          </div>
                        </td>
                        <td>
                          <textarea id="${channel.channelId}_${channelKeyHostnameIdDTO.hostnameId}_editResult"></textarea>
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </td>
              </tr>
            </c:forEach>
            <tr>
              <td>
                <span>add new channel</span>
              </td>
              <td>
                <button class="badge badge-danger">------</button>
              </td>
              <td>
                <span class="badge badge-primary" id="newChannelWeights">weights</span>
                <input type="text" name="" style="width: 50px;" value="0">
              </td>
              <td>
                <span class="badge badge-primary">channel name</span>
                <input type="text" name="" style="width: 100px;" id="newChannelNameInput" placeholder="newChannelName">
              </td>
              <td>
                <button class="btn btn-sm btn-warning" id="addNewChannel">add new channel</button>
              </td>
            </tr>
          </sec:authorize>
        </tbody>
      </table>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <span class="badge badge-primary" id="resultSpan">结果</span>
    </div>
  </div>
</div>


<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">
  $(document).ready(function() {

    $("button[name='modifyChannel']").click(function () {
      modifyChannel($(this).attr("channelId"));
    });

    function modifyChannel(channelId) {
      var url = "/articleAdmin/articleChannelManager";

      var channelNewName = $("input[name='channelNewName'][channelId='"+channelId+"']").val();
      var channelWeight = $("input[name='channelWeight'][channelId='"+channelId+"']").val();
      var channelType = $("select[name='channelType'][channelId='"+channelId+"']").val();

      var jsonOutput = {
        channelId:channelId,
        channelName:channelNewName,
        weights:channelWeight,
        channelType:channelType,
        operationalType:"1"
      };

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
          $("#"+channelId+"_editResult").val("" + datas.code + ":" + datas.message + ":" + new Date().toLocaleString());
        },
        error: function(datas) {

        }
      });
    };

    $("#addNewChannel").click(function () {
      addNewChannel();
    });

    function addNewChannel() {
      var url = "/articleAdmin/articleChannelManager";

      var channelNewName = $("#newChannelNameInput").val();
      var channelWeight = $("#newChannelWeights").val();

      var jsonOutput = {
        channelName:channelNewName,
        weights:channelWeight,
        channelType:"0",
        operationalType:"0"
      };

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
          $("#resultSpan").text("" + datas.code + " : " + datas.message + " : " + new Date().toLocaleString());
        },
        error: function(datas) {

        }
      });
    };

    $("button[name='deleteChannel']").click(function () {
      updateDelete($(this).attr("channelId"), "true");
    });

    $("button[name='reuseChannel']").click(function () {
      updateDelete($(this).attr("channelId"), "false");
    });

    function updateDelete(channelId, isDelete) {
      var url = "/articleAdmin/articleChannelManager";

      var jsonOutput = {
        channelId:channelId,
        isDelete:isDelete,
        operationalType:"2"
      };

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
          JSON.stringify(datas);
          $("#"+channelId+"_editResult").val("" + datas.code + ":" + datas.message + ":" + new Date().toLocaleString());
        },
        error: function(datas) {

        }
      });
    };

    $(".channelKeyHostnameRadio").click(function () {
      var channelId = $(this).attr("channelId");
      var hostnameId = $(this).attr("hostnameId");
      var keyType = $(this).attr("value");
      updateKeyHostname(channelId, hostnameId, keyType);
    });

    function updateKeyHostname(channelId, hostnameId, keyType) {
      var url = "/articleAdmin/editChannelKeyHostname";

      var jsonOutput = {
        channelId:channelId,
        hostnameId:hostnameId,
        articleChannelKeyHostnameType:keyType
      };

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
          JSON.stringify(datas);
          $("#"+channelId+"_"+hostnameId+"_editResult").val("" + datas.code + ":" + datas.message + ":" + new Date().toLocaleString());
        },
        error: function(datas) {

        }
      });
    }

    $(".channelKeyHostnameBtn").click(function () {
      var channelId = $(this).attr("channelId");
      var thisTable = $(".channelKeyHostnameTable[channelId='"+channelId+"']");
      if(!thisTable.is(':visible')) {
        thisTable.show();
      } else {
        thisTable.hide();
      }
    });

    $("#showAllChannelKeyHostnameTable").click(function () {
      var showFlag = $(this).attr("showFlag");
      if(showFlag == "0") {
        $(".channelKeyHostnameTable").show();
        $(this).attr("showFlag", "1");
      } else {
        $(".channelKeyHostnameTable").hide();
        $(this).attr("showFlag", "0");
      }
    });


  });

</script>
</footer>
