<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="container-fluid">

<div class="row">
  <div class="col-sm-4">
    <div class="input-group">
      <div class="input-group-prepend">
        <button class="btn btn-outline-secondary">
          <span class="" style="font-size: small;">从</span>
        </button>
        <input type="text" class="" placeholder="" 
          name="startTime" data-date-format="yyyy-mm-dd HH:mm:ss"/>
        <button class="btn btn-outline-secondary" name="setStartTimeDefault">
          <span class="" style="font-size: small;">(初始)</span>
        </button>
      </div>
    </div>

    <div class="input-group">
      <div class="input-group-prepend">
        <button class="btn btn-outline-secondary">
          <span class="" style="font-size: small;">到</span>
        </button>
        <input type="text" class="" placeholder="" 
          name="endTime" data-date-format="yyyy-mm-dd HH:mm:ss"/>
        <button class="btn btn-outline-secondary" name="setEndTimeNow">
          <span class="" style="font-size: small;">(现在)</span>
        </button>
      </div>
    </div>
  </div>

  <div class="col-sm-4">
    <div class="btn-group">
      <span class="btn btn-success" style="font-size: small">选择已通过
        <input type="checkbox" class="form-control" name="isPass">
      </span>
      <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
      <span class="btn btn-danger" style="font-size: small">选择已删除
        <input type="checkbox" class="form-control" name="isDelete">
      </span>
      <span class="btn btn-warning" style="font-size: small">选择已编辑
        <input type="checkbox" class="form-control" name="isEdited">
      </span>
      </sec:authorize>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-sm-4">
    <button class="btn  btn-info btn-sm" name="adminButtomMark">
      <span style="font-size: small;" name="bottomMarkSpan">审核更多</span>
    </button>
  </div>
</div>

</div>
