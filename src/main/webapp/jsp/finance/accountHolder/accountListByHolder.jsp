<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../../baseElementJSP/normalHeader.jsp" %>
  <style type="text/css">
    .table {
        max-width: none;
        table-layout: fixed;
        word-wrap: break-word;
    }

  </style>
<title>账户列表</title>
</head>
<body>
<div class="container">
  <c:forEach items="${accountHolderCustomList}" var="accountHolderCustom">
  <c:set value="${accountHolderCustom.accountInfoCustomList}" var="accountInfoCustomList" />
  <div class="row">
    <div class="col-md-offset-1 col-md-9">
      <table class="table table-bordered holderInfoTable" > <!-- holderInfo here -->
        <thead> 
          <tr>
            <td>Holder name</td>
            <td>Gender</td>
            <td>Birth</td>
          </tr>
        </thead>
        <body>  
          <tr>
            <td><label>${accountHolderCustom.accountHolderName}</label></td>
            <td><label>${accountHolderCustom.gender}</label></td>
            <td><label><fmt:formatDate value="${accountHolderCustom.birth}" pattern="yyyy-MM-dd" /></label></td>
          </tr>
        </body>
      </table>
    </div>
  </div>  

  <div class="row">
    <div class="col-md-offset-1 col-md-9">
    <table class="table table-bordered col-md-2 accountInfoTable" > <!-- accountInfo here -->
      <thead>
        <tr>
          <td><lable>account number</lable></td>
          <td><lable>account type</lable></td>
          <td><lable>account affiliation</lable></td>
          <td><lable>vaild date</lable></td>
          <td><lable>bank name</lable></td>
          <td><lable>bank union name</lable></td>
          <td><lable>account balance</lable></td>
          <td><lable>credits quota</lable></td>
          <td><lable>temprorary credits quota</lable></td>
          <td><lable>temprorary credits vaild date</lable></td>
          <td><lable>remark</lable></td>
        </tr>
      </thead>
      <body>
        <c:forEach items="${accountInfoCustomList}" var="accountInfoCustom">
        <c:set value="${accountInfoCustom.bankInfoCustom}" var="bankInfoCustom" />
        <c:set value="${bankInfoCustom.bankUnion}" var="bankUnion" />
          <tr>
            <td><label>${accountInfoCustom.accountNumber}&nbsp;</label></td>
            <td><label>${accountInfoCustom.accountType}&nbsp;</label></td>
            <td><label>${accountInfoCustom.accountAffiliation}&nbsp;</label></td>
            <td><label><fmt:formatDate value="${accountInfoCustom.vaildDate}" pattern="yyyy-MM"/></label></td>
            <td><label>${bankInfoCustom.bankEnameShort}&nbsp;</label></td>
            <td><label>${bankUnion.bankUnionEnameShort}&nbsp;</label></td>
            <td><label>${accountInfoCustom.accountBalance}&nbsp;</label></td>
            <td><label>${accountInfoCustom.creditsQuota}&nbsp;</label></td>
            <td><label>${accountInfoCustom.temproraryCreditsQuota}&nbsp;</label></td>
            <td><label>${accountInfoCustom.temproraryCreditsVaildDate}&nbsp;</label></td>
            <td><label>${accountInfoCustom.remark}&nbsp;</label></td>
          </tr>
        </c:forEach>
      </body>
    </table>
    </div>
  </div>
  </c:forEach>
</div>
   
</body>

<footer>
  <%@ include file="../../../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">
    $(document).ready(function() {
      $("label.dateWithTime").css("background", "yellow");
      var tmpDate = $("label.dateWithTime").text();      
    });
  </script>
</footer>
</html>