<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div class="container">

  <div class="row">
    <div class="col-md-12">
      <%@ include file="../accountInfoJSP/accountSelectorV1.jsp" %>
    </div>
  </div>
  
  <div class="row">
    <div class="col-md-12">
      <div class="btn-group" name="">
        <button type="button" name="" id="query" class="btn btn-sm btn-light">查询</button>
        <label>&nbsp;|&nbsp;</label>
        <button type="button" name="" class="btn btn-sm btn-light">nothing</button>
      </div>
    </div>
  </div>


  <div class="row">
    <div class="col-md-12">
      <table class="table table-bordered transantionHistoryTable">
        <thead>
          <tr>
            <td>交易日期</td>
            <td>交易金额</td>
            <td>交易方</td>
            <td>remark</td>
            <td>account number</td>
            <td>account alias</td>
            <td>account type</td>
            <td>balance</td>
          </tr>
        </thead>
        <c:set value="${tradingRecorderList}" var="tradingRecorderList" />
        <body>
        <c:forEach items="${tradingRecorderList}" var="tradingRecorder">
          <tr>
            <td>
              <fmt:formatDate value="${tradingRecorder.transationDate}" pattern="yyyy-MM-dd HH:mm:ss" />
            </td>
            <td>
              <c:choose>
                <c:when test="${tradingRecorder.amount > 0}">
                  <label style="color:green">${tradingRecorder.amount}</label>
                </c:when>
                <c:otherwise>
                  <label style="color:red">${tradingRecorder.amount}</label>
                </c:otherwise>
              </c:choose>
            </td>
            <td>${tradingRecorder.transationParties}</td>
            <td>${tradingRecorder.remark}</td>
            <td>${tradingRecorder.accountNumber}</td>
            <td>${tradingRecorder.accountInfo.accountAlias}</td>
            <td>
              <c:choose>
                <c:when test="${tradingRecorder.accountInfo.accountType == 1}">
                  <label style="color:green">借记卡</label>
                </c:when>
                <c:when test="${tradingRecorder.accountInfo.accountType == 2}">
                  <label style="color:blue">贷记卡</label>
                </c:when>
                <c:otherwise>
                  未知
                </c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:choose>
                <c:when test="${tradingRecorder.accountInfo.accountBalance > 0}">
                  <label style="color:green">${tradingRecorder.accountInfo.accountBalance}</label>
                </c:when>
                <c:otherwise>
                  <label style="color:red">${tradingRecorder.accountInfo.accountBalance}</label>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
        </body>
      </table>
    </div>
  </div>
  
</div>

</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
</footer>
</html>