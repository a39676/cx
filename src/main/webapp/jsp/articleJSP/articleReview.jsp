<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row border border-danger rounded">
  <div class="col-sm-12">
    <c:if test="${articleLongVO.isPass == false and articleLongVO.isReject == false}">
      <button class="btn btn-success btn-sm" name="passByAdmin" pk="${articleLongVO.privateKey}">
        <span class="badge badge-success">通过</span>
      </button>
    </c:if>
    <c:if test="${articleLongVO.isPass == true}">
      <button class="btn btn-success btn-sm" disabled="disabled">
        <span class="badge badge-success">已通过</span>
      </button>
    </c:if>
    <c:if test="${articleLongVO.isPass == false and articleLongVO.isReject == false}">
      <button class="btn btn-warning btn-sm" name="rejectByAdmin" pk="${articleLongVO.privateKey}">
        <span class="badge badge-warning">拒绝</span>
      </button>
    </c:if>
    <c:if test="${articleLongVO.isReject == true}">
      <button class="btn btn-warning btn-sm" disabled="disabled">
        <span class="badge badge-warning">已通过</span>
      </button>
    </c:if>
    <c:if test="${articleLongVO.isDelete == false}">
      <button class="btn btn-danger btn-sm" name="deleteByAdmin" pk="${articleLongVO.privateKey}">
        <span class="badge badge-danger">删除</span>
      </button>
    </c:if>
    <c:if test="${articleLongVO.isDelete == true}">
      <button class="btn btn-danger btn-sm" disabled="disabled">
        <span class="badge badge-danger">已删除</span>
      </button>
    </c:if>
    <span pk="${articleLongVO.privateKey}" name="reviewResult"></span>
  </div>
</div>
<div class="row border border-danger rounded" name="changeChannelDiv">
  <div class="col-sm-12">
    <c:if test="${articleLongVO.isReject == false}">
      <button class="btn btn-primary btn-sm" name="findChannel" pk="${articleLongVO.privateKey}">
        <span style="font-size: small;">转投频道</span>
      </button>
      <select class="" name="findChannel" pk="${articleLongVO.privateKey}" style="display: none; width: 150px;">
        
      </select>
      <button class="btn btn-primary btn-sm" name="changeChannel" style="display: none;" pk="${articleLongVO.privateKey}">
        <span style="font-size: small;">←就投到这里啦!</span>
      </button>
    </c:if>
  </div>
</div>