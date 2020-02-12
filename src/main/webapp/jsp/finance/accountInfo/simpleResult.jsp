<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

  <form>
    
  </form>
  
  <div>
    <label>new account number</label>
    <label>${accountNumber}</label>
  </div>

  <div>
    <label>${emptyValue}</label>
  </div>
  
  <div>
    <label>${accountNumber}</label>
  </div>



  <!-- <table class="table">
  	<thead>
  		<tr>
  			<td>id</td>
  			<td>name</td>
  			<td>gender</td>
  			<td>birth</td>
  		</tr>
  	</thead>

  	<body>
  		<c:forEach var="holder" items="${holderList}">
  			<tr>
  				<td>${holder.accountHolderId}</td>	
  				<td>${holder.accountHolderName}</td>	
  				<td>${holder.gender}</td>	
  				<td>${holder.birth}</td>	
  			</tr>
  		</c:forEach>
  	</body>
  </table> -->
   
</body>
<footer>
<%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
</footer>
</html>