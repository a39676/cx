<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<html>

<head>
  <%@ include file="../../../baseElementJSP/normalHeader.jsp" %>
</head>

<body>
  <h1>Message : ${message}</h1>
  <div class="testArea">${testArea}</div>
  <form class="col-md-8" method="post" action="/admin/userManager"  >

    <div class="form-group row">
      <label class="control-label col-md-1" for="userName">user name:</label>
      <div class="col-md-6">
        <input type="text" class="form-control" name="userName" id="userName">
        <label name="userNameP"></label>
      </div>
    </div>


    <div class="form-group row checkbox ">
      <label class="control-label col-md-1">
        <input type="checkbox" name="enable" value="true" checked="checked"> enable
        <input type="hidden" name="enable" value="false">
      </label>
      <label class="control-label col-md-1">
        <input type="checkbox" name="accountNonLocked" value="true" checked="checked"> Account non locked
        <input type="hidden" name="accountNonLocked" value="false">
      </label>
      <label class="control-label col-md-1">
        <input type="checkbox" name="accountNonExpired" value="true" checked="checked"> account non expired
        <input type="hidden" name="accountNonExpired" value="false">
      </label>
      <label class="control-label col-md-1">
        <input type="checkbox" name="credentialsNonExpired" value="true" checked="checked"> credentialsn non expired
        <input type="hidden" name="credentialsNonExpired" value="false">
      </label>
    </div>

    <button type="submit" class="btn btn-default">Submit</button>
  </form>

</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      // userName exist check
      $(function(){  
        $("input[name='userName']").blur(function(){ 

          //$(this).css("background-color","red");

          var userName = $(this).val();
          var jsonOutput = {};
          
          jsonOutput['userName'] = userName;

          $.ajax({               
            type: "POST",  
            url: "/user/userNameExistCheck",   
            data: JSON.stringify(jsonOutput), 
            dataType: 'json',
            beforeSend: function(xhr) {
              xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            contentType: "application/json",
            timeout: 15000,
            success:function(data){  
              if (data.code === '0') {
                var tmpV = $("label[name='userNameP']");
                $("label[name='userNameP']").css("background-color","yellow");
                $("label[name='userNameP']").text("exist");
              } else {
                $("label[name='userNameP']").css("background-color","red");
                $("label[name='userNameP']").text(data.message);
              }
            }, 
            error:function(e){
            }
          });
        });  
      });


      // user eraser
      $(function(){  
        $("input[name='userName']").blur(function(){ 

          var userName = $(this).val();
          var jsonOutput = {};
          
          jsonOutput['userName'] = userName;

          $.ajax({               
            type: "POST",  
            url: "/admin/userEraser",   
            data: JSON.stringify(jsonOutput), 
            dataType: 'json',
            beforeSend: function(xhr) {
              xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            contentType: "application/json",
            timeout: 15000,
            success:function(data){  
              if (data.code === '0') {
                var tmpV = $("label[name='userNameP']");
                $("label[name='userNameP']").css("background-color","yellow");
                $("label[name='userNameP']").text(userName + " had eraser ");
              } else {
                $("label[name='userNameP']").css("background-color","red");
                $("label[name='userNameP']").text(data.message);
              }
            }, 
            error:function(e){
            }
          });
        });  
      });

    });
  </script>
</footer>
</html>