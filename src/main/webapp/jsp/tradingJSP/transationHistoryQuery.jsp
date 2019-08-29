<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
  <style type="text/css">
    .table {
        max-width: none;
        table-layout: fixed;
        word-wrap: break-word;
    }

  </style>
</head>
<body>
<div class="container">

  <div class="row">
    <div class="col-md-offset-1 col-md-9">
      <form class="col-md-8" method="post" action="${pageContext.request.contextPath}/accountInfo/transationHistory">

        <div class="form-group row">
          <div><label>query form 01</label></div>
        </div>

        <!-- 下拉菜单, 列出登录者名下账户, 动态, 与数据库同步 -->
        <div class="form-group row">
          <select>
            
          </select>
        </div>

        <!-- 基础查询条件 开始日期 结束日期 -->
        <!-- 考虑date picker中 -->
        <div class="form-group row">
          <label class="control-label col-md-2" for="startTime">startTime</label>
          <div class="col-md-6">
            <input type="text" class="form-control" name="startTime" id="startTimePicker" data-date-format="yyyy-mm-dd">
          </div>
        </div>

        <div class="form-group row">
          <label class="control-label col-md-2" for="endTime">endTime</label>
          <div class="col-md-6">
            <input type="text" class="form-control" name="endTime" id="">
          </div>
        </div>

        <div class="form-group row">
          <label class="control-label col-md-2" for=""></label>
          <div class="col-md-6">
            <input type="text" class="form-control" name="" id="">
          </div>
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
    </div>
  </div>


</div>

</body>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">
    $(document).ready(function() {
    });
  </script>
</footer>
</html>