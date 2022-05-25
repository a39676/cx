<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
<style type="text/css">
.fakePanel{
  background-color: #4169E1;
  color: #C0C0C0;
  border-style: solid;
  border-color: #FA8072 #87CEEB;
}
a {
    color: hotpink;
}
</style>
</head>

<body>

<div style="display:none;" name="hiddenMessage" bankId="" bankUnionId="" accountId="" ></div>

<table>

  <thead>
    <tr>
      <td class="fakePanel" name="basePart">
        <a href="/">Homepage中文标记</a>
      </td>
    </tr>
    <tr>
      <td>
        <c:if test="${pageContext.request.userPrincipal.name != null}">

        Welcome! ${pageContext.request.userPrincipal.name}

        </c:if>

        <a href="/login/logout"/>Logout</a>
      </td>
    </tr>
  </thead>


  <thead>
    <tr><td class="fakePanel" name="accountInfoPart" some="something">Summary</td></tr>
  </thead>
  <tbody style="display: none" name="accountInfoPart">
    <tr><td>
          <label class="fakeButton"
            data-url="/accountInfo/accountStatistics">
            All cards
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton"
            data-url="/accountInfo/insertNewTransationV4">
            Add new trading record V4
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton"
            data-url="/accountInfo/transationHistory">
            Tmp tarding record query
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton"
            data-url="/trading/importTradingRecordFromFiles">
            Import tarding from document
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton"
            data-url="/accountInfo/transationHistoryQuery">
            Trading record query
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton"
            data-url="/accountInfo/accountRegist">
            Add new card
          </label>
    </td></tr>
  </tbody>

  <thead>
    <tr><td class="fakePanel" name="toolPart" some="something">Tools</td></tr>
  </thead>
  <tbody style="display: none" name="toolPart">
    <tr><td>
<%--
          <label class="fakeButton"
            data-url="tool/image/imageSave">
            imageSave
          </label>
           --%>
    </td></tr>
  </tbody>

  <thead>
    <tr>
      <td class="fakePanel" name="holder">Personal block</td>
    </tr>
  </thead>
  <tbody style="display: none" name="holder">
    <tr>
      <td>holder2</td>
    </tr>
  </tbody>

  <thead>
    <tr>
      <td class="fakePanel" name="testing">Testing block</td>
    </tr>
  </thead>
  <tbody style="display: none" name="testing">
    <tr><td>
          <label class="fakeButton"
            data-url="/accountInfo/insertNewTransationV4">
            Add new trading record V4
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton"
            data-url="/bankInfo/bankSelectorV4">
            Bank selector V4
          </label>
    </td></tr>
    <tr><td>
          <label class="fakeButton"
            data-url="/accountInfo/accountSelectorV1">
            Account selector V4
          </label>
    </td></tr>
  </tbody>

  <thead>
    <tr>
      <td class="fakePanel" name="something">something</td>
    </tr>
  </thead>
  <tbody style="display: none" name="something">
    <tr>
      <td>something1</td>
    </tr>
    <tr>
      <td>something2</td>
    </tr>
    <tr>
      <td class="fakePanel" name="something_1">somethingSub</td>
      <tbody style="display: none" name="something_1">
        <tr>
          <td>sub1</td>
        </tr>
        <tr>
          <td>sub2</td>
        </tr>
      </tbody>
    </tr>
  </tbody>
</table>
</body>

<footer>
  <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      $("td[class='fakePanel']").click(function(){

        var $panelName = $(this).attr('name');

        if($("tbody[name='" + $panelName + "']").is(":visible")) {
          $("tbody[name^='" + $panelName + "']").hide();
        } else {
        // if($("tbody[name='" + $panelName + "']").is(":hidden")) {
          $("tbody[name='" + $panelName + "']").show();
        }

      });

    });
  </script>
</footer>
</html>
