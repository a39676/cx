<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
 
<sec:csrfMetaTags />
<title>${ title }</title>

<div>
  <p id="message">${message}</p>
</div>

<div>
  <input type="text" name="" id="testInput"> 
  <button id="send">su</button>
</div>


<footer>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.1.1/jquery.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.3/js/tether.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.js"></script>

<!-- csrf part -->
<script type="text/javascript">
  var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
  var csrfHeader = $("meta[name='_csrf_header']").attr("content");
  var csrfToken = $("meta[name='_csrf']").attr("content");
</script>

<script type='text/javascript'>
$(document).ready(function() {

 
});
</script>
</footer>
