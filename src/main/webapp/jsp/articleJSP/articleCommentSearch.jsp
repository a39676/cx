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
        <button class="btn btn-outline-secondary" pk="${pk}">
          <span class="badge badge-light">从</span>
        </button>
        <button class="btn btn-outline-secondary" pk="${pk}" name="resetCommomSearchStartTime">
          <span class="badge badge-light">(reset)</span>
        </button>
        <input type="text" class="" pk="${pk}" placeholder="" 
          name="commentStartTime" data-date-format="yyyy-mm-dd HH:mm:ss"/>
      </div>
    </div>
  </div>

  <div class="col-sm-4">
    <div class="btn-group">
      <span class="btn btn-success" style="font-size: small">选择已通过
        <input type="checkbox" pk="${pk}" class="form-control" name="commentIsPass">
      </span>
      <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
      <span class="btn btn-danger" style="font-size: small">选择已删除
        <input type="checkbox" pk="${pk}" class="form-control" name="commentIsDelete">
      </span>
      </sec:authorize>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-sm-4">
    <button class="btn  btn-info btn-sm" name="reviewMoreComment" 
      pk="${pk}" startTime="${startTime}">
      <span style="font-size: small;" name="bottomMarkSpan">审核更多</span>
    </button>
  </div>
</div>

</div>

<footer>
  <script type="text/javascript">
    $(document).ready(function() {

      $("button[name='resetCommomSearchStartTime'][pk='${pk}']").click(function () {
        $("input[name='commentStartTime'][pk='${pk}']").val("2000-01-01");
        $("button[name='showMoreArticleComment'][pk='${pk}']").attr("startTime", "2000-01-01");
      });
    });
  </script>
</footer>
