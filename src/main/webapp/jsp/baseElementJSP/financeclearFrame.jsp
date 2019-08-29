<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

<div class="container-fluid">

<div class="row">
  <!-- as navigation -->
  <sec:authorize access="hasRole('ROLE_USER')">
  <div class="col-md-1">
    <jsp:include page="${pageContext.request.contextPath}/jsp/financeclearNavigationRoleUser" flush="true"/>
  </div>
  </sec:authorize>
  
  <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
  <div class="col-md-1">
    <jsp:include page="${pageContext.request.contextPath}/jsp/financeclearNavigationNotLogin" flush="true"/>
  </div>
  </sec:authorize>

  <sec:authorize access="hasRole('ROLE_ADMIN')">
<!--   <div class="col-md-1">
  </div> -->
  </sec:authorize>

  <!-- as webBody -->
  <div class="col-md-11">
    <div id="subBody"></div>
  </div>
</div>

</div>

<div class="row">
  <form class="col-md-3" name="headForm">
  <label id="mark"></label>
  <div class="form-group">
    <!-- 预留隐藏字段 -->
    <input type="hidden" name="" value="${message}" />
  </div>
  <div class="form-group">
      <label>${exception}</label>
      <label>${emptyValue}</label>
  </div>
  </form>
</div>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {
      
      $(".fakeButton").click(function(){

        var $tmp = $(this);
        var $url = $tmp.data().url
        ajaxGetView($url);
      });


      function fakeRefresh(input) {
        $("#subBody").html(input)
      }

  
      function ajaxGetView(inputUrl){ 
        var url = inputUrl;
        $.ajax({  
            type : "GET",  
            async : true,
            // async : false,  //同步请求  
            url : url,  
            // data : data,   附带在url路径中的参数   ?type=1
            // timeout:5000,  
            success:function(datas){  
                //alert(datas);  
                $("#subBody").html(datas);//要刷新的div  
            },  
            error: function(datas) {  
                // alert("失败，请稍后再试！");  
                $("#subBody").html(datas);
            }  
        });  
      };


    });


  </script>
</footer>
</html>