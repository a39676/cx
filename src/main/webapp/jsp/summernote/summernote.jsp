<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 因需要使用富文本编辑器, 特别使用指定的库 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="/static_resources/js/jquery/v3_5_1/jquery.min.js"></script>
<script type="text/javascript" src="/static_resources/js/popper/v1_16_0/popper.min.js"></script>
<link rel="stylesheet" href="/static_resources/css/bootstrap/v4_0_0_beta/bootstrap.min.css">
<script type="text/javascript" src="/static_resources/js/bootstrap/v4_0_0_beta/bootstrap.min.js"></script>
<link href="/static_resources/summernote/v0_8_18/summernote-bs4.min.css" rel="stylesheet" type="text/css">
<script src="/static_resources/js/summernote/v0_8_18/summernote-bs4.min.js"></script>

<div id="summernote"></div>
<script>
  $('#summernote').summernote({
    tabsize: 2,
    height: 100
  });
</script>
