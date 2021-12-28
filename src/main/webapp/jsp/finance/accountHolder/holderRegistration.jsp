<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  <div class="testArea">${testArea}</div>
  <form class="col-md-8" method="post" id="holderRegisterForm">
  	
  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  	<label>${ message }</label>
  	
    <div class="form-group row">
      <label class="control-label col-md-1" for="accountHolderName">Holder name:</label>
      <div class="col-md-6">
        <input type="text" class="form-control" name="accountHolderName" id="accountHolderName">
      </div>
    </div>

    <div class="form-group row ">
      <div class="col-md-1"></div>
      <label class="radio-inline col-md-1">
        <input type="radio" name="gender" value="M"/>Male</label>
      <label class="radio-inline col-md-1">
        <input type="radio" name="gender" value="F"/>Fmale</label>
    </div>
    
    <div class="form-group row checkbox ">
      <div class="col-md-1"></div>
      <label><input type="checkbox" >something</label>
    </div>
    
    <div class="form-group row">
      <label class="control-label col-md-1" for="birth">Birth:</label>
      <div class="col-md-6">
        <input type="text" class="form-control" name="birth" id="birth">
      </div>
    </div>
    
    <span onclick="holderFormSubmit();" class="label label-primary">提交</span>
  </form>
  
   
<script type="text/javascript">



  
  function holderFormSubmit(){

    $.ajax({
      type: "POST",
      url:"/holder/holderRegister",
      data: $("#holderRegisterForm").serialize(),
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      timeout: 15000,  
      success: function (data) {
        console.log(data);
        $('#subBody').html(data);
      },  
      error:function(e){
        // console.log("error");
        // console.log(e);
      }
    });
  };
	


</script> 
