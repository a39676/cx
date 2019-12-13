<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row border border-danger rounded">
  <div class="col-lg-3 trySetArticleHot" pk="${articleLongVO.privateKey}">
    <button class="btn btn-primary btn-sm" name="trySetArticleHot" pk="${articleLongVO.privateKey}" 
      <c:if test="${articleLongVO.isPass == false or articleLongVO.isReject == true or articleLongVO.isDelete == true}">
      style="display : none;" 
      </c:if>
    >
      <span class="badge badge-primary">需要置顶?</span>
    </button>
  </div>

  <div class="col-lg-3 form-group setArticleHotOption" pk="${articleLongVO.privateKey}" style="display: none;">
    <span class="badge badge-primary">置顶时长</span>
    <select class="form-control form-control-sm" name="setArticleHotMinutes" pk="${articleLongVO.privateKey}">
      <option value="1440"><span class="badge badge-light">1天</span></option>
      <option value="4320"><span class="badge badge-light">3天</span></option>
      <option value="10080"><span class="badge badge-light">7天</span></option>
      <option value="43200"><span class="badge badge-light">30天</span></option>
    </select>
  </div>

  <div class="col-lg-3 form-group setArticleHotOption" pk="${articleLongVO.privateKey}" style="display: none;">
    <span class="badge badge-primary">置顶等级</span>
    <input class="form-control" type="number" name="setArticleHotLevel" pk="${articleLongVO.privateKey}" min="0" max="10" value="0">
  </div>

  <div class="col-lg-3 form-group setArticleHotOption" pk="${articleLongVO.privateKey}" style="display: none;">
    <button class="btn btn-primary" name="setArticleHot" pk="${articleLongVO.privateKey}">
      <span class="badge badge-primary">确定置顶</span>
    </button>
    <span class="badge badge-primary" pk="${articleLongVO.privateKey}" name="manageResult"></span>
  </div>
</div>