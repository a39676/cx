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
            <span class="badge badge-light">从</span>
          </button>
          <button class="btn btn-outline-secondary" name="resetCommomSearchStartTime">
            <span class="badge badge-light">(reset)</span>
          </button>
          <input type="text" class="" placeholder="" value="2000-01-01 00:00:00"
            name="commentStartTime" data-date-format="yyyy-mm-dd HH:mm:ss"/>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-sm-4">
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="commentIsPass" checked="checked">
        <label class="form-check-label badge badge-success" for="commentIsPass">pass</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="commentIsDelete">
        <label class="form-check-label badge badge-danger" for="commentIsDelete">delete</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="commentIsReject">
        <label class="form-check-label badge badge-danger" for="commentIsReject">reject</label>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-sm-4">
      <button class="btn  btn-info btn-sm" id="reviewMoreComment">
        <span style="font-size: small;" id="bottomMarkSpan">Search More Comment</span>
      </button>
    </div>
  </div>

</div>

<footer>
  <script type="text/javascript">
  </script>
</footer>
