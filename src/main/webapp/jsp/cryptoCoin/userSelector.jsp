<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<table class="table">
  <tr>
    <td>
      <label>user:</label>
      <select id="userSelector">
        <option value="">Please select user</option>
        <c:forEach items="${userList}" var="subUser" varStatus="loop">
          <option value="${subUser.localUserId}" userNickname="${subUser.nickname}">
            ${subUser.nickname}
          </option>
        </c:forEach>
      </select>
    </td>
    <td>
      <label>exchange:</label>
      <select id="exchangeSelector">
        <c:forEach items="${exchangeList}" var="exchange" varStatus="loop">
          <option value="${exchange.code}" userNickname="${exchange.name}">
            ${exchange.name}
          </option>
        </c:forEach>
      </select>
    </td>
  </tr>
</table>